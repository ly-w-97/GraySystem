# 灰度系统

## 概要及背景
为了保证线上业务质量，因此做了一套后台灰度系统。通过简易的配置即可将系统进行灰度。该系统已在线上生产环境正式运行1年<br/><br/>

## 功能点
- 可以基于单个服务灰度（也可以整体控制灰度）
- 基于接口灰度
- 外部依赖接口的灰度
- mq灰度
- 接入层与服务之间的灰度调用
- 服务于服务之间的灰度调用
<br/><br/>

## 系统组成
整体分为：灰度配置服务、代理服务、灰度组件（核心）
![Snip20180920_1](http://ww2.sinaimg.cn/large/006y8mN6gy1g6ua2h29mdj30z20h075i.jpg)


## 使用方法：

### 服务引入灰度配置
1、创建 META-INF.dubbo文件夹
  
2、配置filter <br/>
 （1）创建 com.alibaba.dubbo.rpc.Filter文件
  内容是 grayFilter=com.facishare.wechat.union.common.component.DubboGrayFilter
  如果需要自定义filter，可以继承com.facishare.wechat.union.common.component.DubboGrayFilter，
  然后在等号后面写上实现类
  
3、配置loadBalance
 （1）创建 com.alibaba.dubbo.rpc.cluster.LoadBalance文件
  内容是 grayLoadBalance=com.facishare.xxx.xxx.xxxLoadBalance
  普通项目只要进行步骤（1）就可以了
  
 （2）在web、fcp项目中，需要创建子类xxxLoadBalance，并且把实现类路径写在等号后面，
  主要是获取fsEa
  
4、spring配置文件中添加：<br/>
  `<import resource="classpath*:/spring/wechat-union-common.xml"/>`
  
  
PS：简单来说，除了web接入层的项目，其他项目只要<br/>
 （1）创建com.alibaba.dubbo.rpc.Filter、com.alibaba.dubbo.rpc.cluster.LoadBalance文件
 （2）导入`<import resource="classpath*:/spring/wechat-union-common.xml"/>`



### 机器准备
灰度机器2台（如果外部依赖的都是旧接口，1台就好了），正式机器1台

灰度机器，需要部署2台。正常机器一台即可（之所以灰度机器需要两台以上，是因为只部署一台的话，那么新的接口service只有1个invoker，
因此不经过loadBalance，因此没有给group赋值，因此去provider侧寻找invoker的时候，会找不到）
<br/><br/>


## 实现原理

### 划分灰度环境
在服务启动时，根据该服务的灰度机器列表，判断该服务下这个台机器是否需要灰度，如果是，则设置为灰度环境配置（该服务设置为灰度group）。
                   通过使用GrayBeanPostProcessor初始化之后，通过group的来划分，dubbo服务被划分灰度环境、正式环境。这里需要注意的是，该灰度系统的核心之一就是通过group进行灰度环境的划分。

具体流程如图：
![Snip20180920_1](http://ww4.sinaimg.cn/large/006y8mN6gy1g6tirria2mj31m60i6aem.jpg)
<br/><br/>


### 动态路由
通过上面的步骤，已经将服务划分为灰度环境、正式环境。接下来就是利用dubbo的loadBalance、Filter扩展，达到动态调用灰度/正式服务的效果。调用的顺序为loadBalance——》filter。

具体要怎么做呢？
在consumer侧，我们为了在LoadBalance中获取到所有provder对应的invoker，所以设置group为“*”。然后我们根据首先根据请求的身份信息，判断该用户走灰度还是正式环境，然后调用灰度/正式服务。
在provider侧，所有invoker都被存储在map中，等待consumer进行获取调用，并且key的里面包含了group，所以consume进行调用的时候，必须要携带group，才能把group作为key的一部分，去正常的获取到invoker。

如果consumer调用的时候不携带group，那么dubbo默认会把ConsumerConfig的group属性值当做参数传递给provider方。而在在provider侧，会从一个Map获取接口的exporter，key类似于 */com.xxxx.xxService.queryMethod这种格式，因此，这样子是拿不到invoker的。

![Snip20180920_1](http://ww3.sinaimg.cn/large/006y8mN6gy1g6tiy57mkhj30vs07dmyr.jpg)
![Snip20180920_1](http://ww1.sinaimg.cn/large/006y8mN6gy1g6tiyy5xeuj31dr0opqf1.jpg)

另外，对于灰度服务的调用，我们会将“*”替换成灰度的group，这样子能正常调用成功。但是对于正常服务的调用，是没有group的，因此dubbo直接把group=“*“传递给provider，调用失败。

因此我们做了一个兼容性的错误，通过provider侧的filter去把非灰度group（也就是”*“）设置为空，那么非灰度服务也能正常调用了。

通过查看源代码，在provider侧获取exporter进行调用时，使用invocation获取group值，利用该值组成key，因此，如果在consumer消费之前，可以通过invocation设置group的值（调用灰度服务，则设置为对应的灰度group；调用正式服务，则设置为”“，因为”“不会被添加到key中），就可以获取得到exporter，从而达到调用灰度/正式服务的效果

![Snip20180920_5](http://ww4.sinaimg.cn/large/006y8mN6gy1g6tk21ugc2j30te0zon2x.jpg)
<br/><br/>


### 代理层实现外部调用灰度
说明：外部服务调用interface.method1的时候，调用proxy项目的interface.method1，然后获取用户信息（fsEa、wxAppId、appId...），
从而判断是走灰度服务，还是正常服务，选择完毕，调用project的同名服务
![Snip20180920_1](http://ww4.sinaimg.cn/large/006y8mN6gy1g6ubrnme3vj30t604rglu.jpg)


### mq灰度处理
每一个服务定时拉取灰度配置，判读该服务所处的机器是否灰度机器。
灰度机器则初始化mq，可以处理mq消息；正式机器则关闭mq，不处理mq消息。