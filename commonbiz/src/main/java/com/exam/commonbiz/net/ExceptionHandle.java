package com.exam.commonbiz.net;

import android.net.ParseException;

import com.exam.commonbiz.cache.ACache;
import com.google.gson.JsonParseException;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;

import java.net.ConnectException;

import retrofit2.HttpException;

/**
 * @Author yuexingxing
 * @time 2020/6/11
 */
public class ExceptionHandle {

    public static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static final String NETWORK_ERROR = "网络错误";
    private static final String PARSING_ERROR = "Parsing error";
    public static final String CONNECTION_FAILED = "服务器连接失败";
    private static final String CERTIFICATE_VALIDATION_FAILED = "Certificate validation failed";
    public static final String CONNECTION_TIMEOUT = "连接超时";
    public static final String UNKNOW_ERROR = "服务器连接异常,请检查网络设置";

    public static ResponeThrowable handleException(Throwable e) {

        e.printStackTrace();
        ResponeThrowable ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponeThrowable(e, ERROR.HTTP_ERROR);
            switch (httpException.code()) {
                case UNAUTHORIZED:
                    ex.message = "请重新登录";
                    break;
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.message = NETWORK_ERROR;
                    break;
            }
            return ex;
        } else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            ex = new ResponeThrowable(resultException, resultException.code);
            ex.message = resultException.message;
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ResponeThrowable(e, ERROR.PARSE_ERROR);
            ex.message = PARSING_ERROR;
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ResponeThrowable(e, ERROR.NETWORD_ERROR);
            ex.message = CONNECTION_FAILED;
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ResponeThrowable(e, ERROR.SSL_ERROR);
            ex.message = CERTIFICATE_VALIDATION_FAILED;
            return ex;
        } else if (e instanceof ConnectTimeoutException) {
            ex = new ResponeThrowable(e, ERROR.TIMEOUT_ERROR);
            ex.message = CONNECTION_TIMEOUT;
            return ex;
        } else if (e instanceof java.net.SocketTimeoutException) {
            ex = new ResponeThrowable(e, ERROR.TIMEOUT_ERROR);
            ex.message = CONNECTION_TIMEOUT;
            return ex;
        } else if (e instanceof com.jakewharton.retrofit2.adapter.rxjava2.HttpException) {
            ex = new ResponeThrowable(e, ExceptionHandle.UNAUTHORIZED);
            ex.message = "身份过期，请重新登录";
        } else {
            ex = new ResponeThrowable(e, ERROR.UNKNOWN);
            ex.message = UNKNOW_ERROR;
        }
        return ex;
    }


    /**
     * 约定异常
     */
    public class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
        public static final int NETWORD_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1005;

        /**
         * 连接超时
         */
        public static final int TIMEOUT_ERROR = 1006;
        /**
         * 业务逻辑错误
         */
        public static final int SERVICE_ERROR = 1007;

        /**
         * 身份认证失败，或登录过期
         */
        public static final int UNAUTHORIZED = 1008;
    }

    public static class ResponeThrowable extends Exception {
        public int code;
        public String message;

        public ResponeThrowable(Throwable throwable, int code) {
            super(throwable);
            this.code = code;
        }
    }

    public class ServerException extends RuntimeException {
        public int code;
        public String message;
    }
}

