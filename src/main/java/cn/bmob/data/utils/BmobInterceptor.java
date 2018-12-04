package cn.bmob.data.utils;

import cn.bmob.data.Bmob;
import cn.bmob.data.config.BmobConfig;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.IOException;

/**
 * 拦截器工具类
 */
public class BmobInterceptor {
    /**
     * 在Request中添加Header内容
     *
     * @return
     */
    public static Interceptor headerInterceptor() {
        return new Interceptor() {
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", BmobConfig.getContentType())
                        .addHeader("X-Bmob-Application-Id", Bmob.getInstance().getAppId())
                        .addHeader("X-Bmob-REST-API-Key", Bmob.getInstance().getApiKey())
                        .build();
                return chain.proceed(request);
            }
        };
    }

    /**
     * 日志拦截器
     *
     * @return
     */
    public static HttpLoggingInterceptor logInterceptor() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            public void log(String message) {
                HttpLoggingInterceptor.Logger logger = HttpLoggingInterceptor.Logger.DEFAULT;
                logger.log(message);
            }
        }).setLevel(level);
    }
}
