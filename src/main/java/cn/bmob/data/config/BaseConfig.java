package cn.bmob.data.config;

import lombok.Data;

/**
 * SDK的默认配置
 */
@Data
public class BaseConfig {

    public static String appId = "12784168944a56ae41c4575686b7b332";
    public static String apiKey = "9e8ffb8e0945092d1a6b3562741ae564";

    /* 是否调试 */
    private boolean debug = false;

    /*是否需要缓存处理*/
    private boolean cache;
    /*基础url*/
    public static String baseUrl = "https://api2.bmob.cn";
    /*方法-如果需要缓存必须设置这个参数；不需要不用設置*/
    private String method="";
    /*超时时间-默认6秒*/
    public static int connectionTime = 6;
    /*有网情况下的本地缓存时间默认60秒*/
    private int cookieNetWorkTime = 60;
    /*无网络的情况下本地缓存时间默认30天*/
    private int cookieNoNetWorkTime = 24 * 60 * 60 * 30;
    /* 失败后retry次数*/
    private int retryCount = 1;
    /*失败后retry延迟*/
    private long retryDelay = 100;
    /*失败后retry叠加延迟*/
    private long retryIncreaseDelay = 10;
    /*缓存url-可手动设置*/
    private String cacheUrl;

    public String getUrl() {
        /*在没有手动设置url情况下，简单拼接*/
        if (null == getCacheUrl() || "".equals(getCacheUrl())) {
            return baseUrl + getMethod();
        }
        return getCacheUrl();
    }

    /**
     * 设置参数
     *
     * @param retrofit
     * @return
     */
//    public abstract Observable getObservable(Retrofit retrofit);
}
