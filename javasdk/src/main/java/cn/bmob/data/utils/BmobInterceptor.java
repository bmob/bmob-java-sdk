package cn.bmob.data.utils;

import cn.bmob.data.Bmob;
import cn.bmob.data.bean.table.BmobUser;
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
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("X-Bmob-Application-Id", Bmob.getInstance().getAppId());
                builder.addHeader("X-Bmob-REST-API-Key", Bmob.getInstance().getApiKey());
                if (Bmob.getInstance().getMasterKey()!=null){
                    builder.addHeader("X-Bmob-Master-Key", Bmob.getInstance().getMasterKey());
                }
                builder.addHeader("Content-Type", BmobConfig.getContentType());
                String session = BmobUser.getInstance().getSessionToken();
                if (!Utils.isStringEmpty(session)) {
                    builder.addHeader("X-Bmob-Session-Token", session);
                    System.out.println(session);
                }
                Request request = builder.build();
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
