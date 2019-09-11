package com.huangyuan.open.gray.config.provider.manager;

import com.huangyuan.open.gray.config.provider.model.vo.GrayApplicationVO;

/**
 * @author huangy
 */
public interface GrayApplicationHandlerManager {
    /**
     * 查询灰度服务配置信息
     *  todo 之前设计的 一个应用绑定一个分组，如果一个应用要绑定多个分组，就要该一下表了
     * @param applicationName 服务名称 【必填】
     * @return GrayApplicationVO
     */
    GrayApplicationVO getGrayApplicationConfig(String applicationName);

    /**
     * 检查灰度企业是否灰度
     *
     * @param applicationName 服务名称               【必填】
     * @param fsEa            企业id                 【必填】
     * @return Boolean true-存在，为灰度企业 ，false-不存在，非灰度企业
     */
    Boolean checkGrayFsEa(String applicationName, String fsEa);
}
