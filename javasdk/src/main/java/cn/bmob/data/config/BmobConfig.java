package cn.bmob.data.config;

/**
 * SDK的默认配置
 */
public class BmobConfig {


    /**
     * 默认的REST API域名
     */
    public static final String DEFAULT_REST_API_HOST = "https://api2.bmob.cn";

    /**
     * KEY_APP_ID
     */
    public static final String KEY_APP_ID = "X-Bmob-Application-Id";

    /**
     * KEY_REST_API_KEY
     */
    public static final String KEY_REST_API_KEY = "X-Bmob-REST-API-Key";


    /**
     * KEY_MASTER_KEY
     */
    public static final String KEY_MASTER_KEY = "X-Bmob-Master-Key";

    /**
     * 请求的内容类型
     */
    private static String contentType = "application/json; charset=UTF-8";

    /**
     * 是否调试模式
     */
    private static boolean debug = true;

    /**
     * 同步或异步
     */
    private static boolean synchronous =false;



    /**
     * 是否需要缓存处理
     */
    private boolean cache;

    /**
     * 如果需要缓存必须设置这个参数
     */
    private String method = "";
    /**
     * 默认超时时间
     */
    public static int connectionTime = 6;
    /**
     * 有网情况下的本地缓存时间默认60秒
     */
    private int cookieNetWorkTime = 60;
    /**
     * 无网络的情况下本地缓存时间默认30天
     */
    private int cookieNoNetWorkTime = 24 * 60 * 60 * 30;
    /**
     * 失败后retry次数
     */
    private int retryCount = 1;
    /**
     * 失败后retry延迟
     */
    private long retryDelay = 100;
    /**
     * 失败后retry叠加延迟
     */
    private long retryIncreaseDelay = 10;
    /**
     * 缓存url，可手动设置
     */
    private String cacheUrl;

    public String getUrl() {
        /**
         * 在没有手动设置url情况下，简单拼接
         */
        if (null == getCacheUrl() || "".equals(getCacheUrl())) {
            return DEFAULT_REST_API_HOST + getMethod();
        }
        return getCacheUrl();
    }


    public static String getContentType() {
        return contentType;
    }

    public static void setContentType(String contentType) {
        BmobConfig.contentType = contentType;
    }

    public static boolean isDebug() {
        return debug;
    }

    public static void setDebug(boolean debug) {
        BmobConfig.debug = debug;
    }

    public static boolean isSynchronous() {
        return synchronous;
    }

    public static void setSynchronous(boolean synchronous) {
        BmobConfig.synchronous = synchronous;
    }

    public boolean isCache() {
        return cache;
    }

    public BmobConfig setCache(boolean cache) {
        this.cache = cache;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public BmobConfig setMethod(String method) {
        this.method = method;
        return this;
    }

    public static int getConnectionTime() {
        return connectionTime;
    }

    public static void setConnectionTime(int connectionTime) {
        BmobConfig.connectionTime = connectionTime;
    }

    public int getCookieNetWorkTime() {
        return cookieNetWorkTime;
    }

    public BmobConfig setCookieNetWorkTime(int cookieNetWorkTime) {
        this.cookieNetWorkTime = cookieNetWorkTime;
        return this;
    }

    public int getCookieNoNetWorkTime() {
        return cookieNoNetWorkTime;
    }

    public BmobConfig setCookieNoNetWorkTime(int cookieNoNetWorkTime) {
        this.cookieNoNetWorkTime = cookieNoNetWorkTime;
        return this;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public BmobConfig setRetryCount(int retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    public long getRetryDelay() {
        return retryDelay;
    }

    public BmobConfig setRetryDelay(long retryDelay) {
        this.retryDelay = retryDelay;
        return this;
    }

    public long getRetryIncreaseDelay() {
        return retryIncreaseDelay;
    }

    public BmobConfig setRetryIncreaseDelay(long retryIncreaseDelay) {
        this.retryIncreaseDelay = retryIncreaseDelay;
        return this;
    }

    public String getCacheUrl() {
        return cacheUrl;
    }

    public BmobConfig setCacheUrl(String cacheUrl) {
        this.cacheUrl = cacheUrl;
        return this;
    }
}
