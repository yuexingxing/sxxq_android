package com.exam.commonbiz.net;

/**
 * 服务器地址配置
 *
 * @Author yuexingxing
 * @time 2020/8/6
 */
public class HostUrl {

    public interface DEV {
        String JAVA = "http://t2wxapi.sancell.top/api/";
        String NODE = "http://t2wxapi.sancell.top/ssxq/";
    }

    public interface PRE {
        String JAVA = "https://apin.sancell.top/api/";
        String NODE = "https://apin.sancell.top/ssxq/";
    }

    public interface PRO {
        String JAVA = "https://api.sancellvarymay.com/api/";
        String NODE = "https://api.sancellvarymay.com/ssxq/";
    }


    public interface PRO_VERIFY {
        String JAVA = "https://miniapp1.sanshaoxingqiu.cn/api/";
        String NODE = "https://miniapp1.sanshaoxingqiu.cn/ssxq/";
    }

}
