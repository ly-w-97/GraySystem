package com.huangyuan.open.gray.config.admin.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by wangtao on 2016/1/25.
 */
public class NumberUtils {

    public static String getFormatMoney(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return "0";
        }
        DecimalFormat myformat = new DecimalFormat();
        myformat.applyPattern("##,##0.00");
        return myformat.format(bigDecimal.setScale(2));
    }
}
