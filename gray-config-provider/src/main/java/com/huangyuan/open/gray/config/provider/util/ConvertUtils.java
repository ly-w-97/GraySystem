package com.huangyuan.open.gray.config.provider.util;

import com.google.common.collect.Lists;
import com.huangyuan.open.gray.config.provider.constant.GrayConstant;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huangy on 2019-09-12
 */
public class ConvertUtils {

    /**
     * 将IP字符串转换成IP地址列表
     *
     * @param ipAddress ip地址，如：10.21.0.7;10.32.1.1;
     * @return List<String>
     */
    public static List<String> getIpAddress(String ipAddress) {
        String[] arr = ipAddress.split(GrayConstant.IP_ADDRESS_MARGIN);

        if (arr.length == 0) {
            return Lists.newArrayList();

        } else {
            List<String> ipList = new ArrayList<>(arr.length);
            CollectionUtils.addAll(ipList, arr);
            return ipList;
        }
    }

}
