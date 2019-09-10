package com.huangyuan.open.gray.common.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 *
 * @author archerda
 * @date 2018/12/14
 */
public class GsonUtil {

    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

    public static Gson getGson() {
        return GSON;
    }

}
