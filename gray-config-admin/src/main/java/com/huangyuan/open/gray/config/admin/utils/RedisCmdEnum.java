package com.huangyuan.open.gray.config.admin.utils;

/**
 * Created by wangtao on 2017/1/3.
 */
public enum RedisCmdEnum {

     TTL(0, "ttl"),
     HGET(1, "hget"),
     HGETALL(2, "hgetAll"),
     GET(3, "get"),
     EXISTS(4, "exists"),
     KEYS(5, "keys"),
     LRANGE(6, "lrange"),
     ECHO(9, "echo"),
     DEL(10, "del"),

         ;
     private int index;
     private String desc;


    RedisCmdEnum(int index, String desc) {
        this.index = index;
        this.desc = desc;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
