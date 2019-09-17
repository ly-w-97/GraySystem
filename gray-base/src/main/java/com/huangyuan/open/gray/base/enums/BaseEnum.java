package com.huangyuan.open.gray.base.enums;

/**
 * 通用枚举类， 实现这个枚举 就可以方便的使用通用的EserviceException 和 EserviceResult
 * Create by guom on 2019/3/21.
 */
public interface BaseEnum<E extends Enum<E>> {

    /**
     * @return
     */
    public String getErrorCode();

    /**
     * @return
     */
    public String getErrorMessage();

    /**
     * @return
     */
    public String getDescription();

    /**
     * @return
     */
    public String getI18Code();

    /**
     *
     * @return
     */
    public String i18Text(Object... args);

    /**
     *
     * @return
     */
    public String i18Text();


}