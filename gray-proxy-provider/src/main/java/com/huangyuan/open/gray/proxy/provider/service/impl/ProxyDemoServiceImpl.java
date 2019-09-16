package com.huangyuan.open.gray.proxy.provider.service.impl;

import com.huangyuan.open.gray.base.result.EserviceResult;
import com.huangyuan.open.gray.proxy.api.service.ProxyDemoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author huangy on 2019-09-05
 */
@Service
public class ProxyDemoServiceImpl implements ProxyDemoService {

    @Override
    public EserviceResult proxyInterfaceDemo() {

        // TODO by huangy on 2019-09-10. 调用内部接口，透传参数，调用dubbo

        return null;
    }
}
