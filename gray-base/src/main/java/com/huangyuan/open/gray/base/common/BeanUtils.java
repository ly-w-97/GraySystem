package com.huangyuan.open.gray.base.common;

/**
 * @author huangy on 2019-09-10
 */
public class BeanUtils {

    public static <D,S> void copeProperties(D d,S s){

        try {
            org.apache.commons.beanutils.BeanUtils.copyProperties(d,s);
        } catch (Exception e) {
            new RuntimeException("Copy Properties failed,DestClass:"+d.getClass().getName()
                    + " SrcClass:"+s.getClass().getName());
        }
    }

}
