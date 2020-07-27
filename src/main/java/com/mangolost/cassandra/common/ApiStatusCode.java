package com.mangolost.cassandra.common;

/**
 * 422之前是统一错误码，
 * 自定义业务错误码从430开始
 * 自定义系统错误码从510开始
 */
public class ApiStatusCode {

    /**
     * 成功
     */
    public static final int SUCCESS = 200;

    /**
     * 系统错误
     */
    public static final int FAIL_SYSTEM_ERROR = 500;

}
