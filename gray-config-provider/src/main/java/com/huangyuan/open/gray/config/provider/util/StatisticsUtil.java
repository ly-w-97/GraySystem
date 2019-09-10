package com.huangyuan.open.gray.config.provider.util;

import com.facishare.wechat.proxy.common.utils.BeanUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * @author suxq
 */
@Slf4j
public class StatisticsUtil<T> {

    private static ThreadLocal<Map<String, Object>> statisticsInfo = null;

    static {
        statisticsInfo = new ThreadLocal<Map<String, Object>>();
    }

    public static <T> void set(String key, T t) {
        Map<String, Object> map = statisticsInfo.get();
        if (null == map) {
            map = Maps.newHashMap();
            statisticsInfo.set(map);
        }
        map.put(key, t);
    }

    public static Map<String, Object> getMap() {
        return statisticsInfo.get();
    }

    public static <T> T get(String key) {
        Map<String, Object> map = statisticsInfo.get();
        if (null == map.get(key)) {
            return null;
        }
        return (T) map.get(key);
    }

    public static void remove(String key) {
        Map<String, Object> map = statisticsInfo.get();
        if (null == map.get(key)) {
            return;
        }
        map.remove(key);
    }

    public static void clear() {
        Map<String, Object> map = statisticsInfo.get();
        map = null;
        statisticsInfo.remove();
    }

    public static List<Map<String, Object>> uploadLog() {
        List<Map<String, Object>> list = Lists.newArrayList();
        Map<String, Object> map = statisticsInfo.get();
        if (map != null) {
            for (Object v : map.values()) {
                if (null != v) {
                    Map<String, Object> statParamMap = BeanUtil.transBean2Map(v);
                    list.add(statParamMap);
                }
            }
        }
        clear();
        return list;
    }
}
