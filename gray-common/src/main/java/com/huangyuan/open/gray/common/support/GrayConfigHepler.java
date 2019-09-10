package com.huangyuan.open.gray.common.support;

import com.github.autoconf.ConfigFactory;
import com.github.autoconf.api.IChangeableConfig;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huangy on 2018/9/18
 */
@Component
public class GrayConfigHepler {

    private IChangeableConfig config = ConfigFactory.getInstance().getConfig("fs-eservice-gray-config");

    public List<String> getGrayInterfaceList() {
        String grayInterfaceStr = config.get("gray.interface.list");

        String[] grayInterfaceArr = grayInterfaceStr.split(";");

        List<String> grayInterfaces = new ArrayList<>(grayInterfaceArr.length);

        CollectionUtils.addAll(grayInterfaces, grayInterfaceArr);

        return grayInterfaces;
    }
}
