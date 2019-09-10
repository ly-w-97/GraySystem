package com.huangyuan.open.gray.common.support;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huangy on 2018/9/18
 */
@Component
public class GrayConfigHepler {

    /**
     * 获取需要走灰度的接口列表，形如：
     * com.facishare.eservice.cases.api.service.MigrateService#addWorkOrderField
     */
    public List<String> getGrayInterfaceList() {

        // todo 从配置中心获取你们的灰度接口列表
        List<String> grayInterfaces = new ArrayList<>();

        return grayInterfaces;
    }
}
