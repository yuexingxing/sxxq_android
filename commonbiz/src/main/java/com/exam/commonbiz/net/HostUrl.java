package com.exam.commonbiz.net;

/**
 * 服务器地址配置
 *
 * @Author yuexingxing
 * @time 2020/8/6
 */
public class HostUrl {

    public interface DEV {
        String JAVA = "https://t2javaapi.sancell.top/api/ssxq/";
        String NODE = "https://t2wxapi.sancell.top/ssxq/";
    }

    public interface PRE {
        String JAVA = "https://apij.sancell.top/api/ssxq/";
        String NODE = "https://apin.sancell.top/ssxq/";
    }

    public interface PRO {
        String JAVA = "https://apij.sancell.top/api/ssxq/";
        String NODE = "https://apin.sancell.top/ssxq/";
    }
}
