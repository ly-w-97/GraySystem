package com.huangyuan.open.gray.proxy.provider.service.impl;

import com.facishare.eservice.base.result.EserviceResult;
import com.facishare.eservice.cases.api.model.arg.knowledge.ServiceKnowledgeConfigArg;
import com.facishare.eservice.cases.api.model.result.knowledge.ServiceKnowledgeConfigResult;
import com.facishare.eservice.cases.api.service.knowledge.ServiceKnowledgeService;
import com.facishare.eservice.proxy.api.service.knowledge.ServiceKnowledgeProxyService;
import com.huangyuan.open.gray.base.result.EserviceResult;
import com.huangyuan.open.gray.proxy.api.service.ProxyDemoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author huangy on 2019-09-05
 */
@Service
public class ProxyDemoServiceImpl implements ProxyDemoService {

    @Resource
    private ServiceKnowledgeService serviceKnowledgeService;

    @Override
    public EserviceResult proxyInterfaceDemo() {

        // TODO by huangy on 2019-09-10. 透传参数，调用dubbo

        return null;
    }
}
