package cn.bmob.data;

import cn.bmob.data.config.*;
import cn.bmob.data.api.*;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import cn.bmob.data.utils.*;
import java.util.concurrent.TimeUnit;

public class Bmob {


    private String appId;
    private String apiKey;


    public String getAppId() {
        return appId;
    }

    private void setAppId(String appId) {
        this.appId = appId;
    }

    public String getApiKey() {
        return apiKey;
    }

    private void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    private volatile static Bmob INSTANCE;
    private volatile static BmobApiService mBmobApiService;

    private Bmob() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(HttpConfig.connectionTime, TimeUnit.SECONDS);
        builder.addInterceptor(InterceptorUtil.headerInterceptor());   // 使用拦截器在request中添加统一header内容
        if (HttpConfig.debug) {
            builder.addInterceptor(InterceptorUtil.logInterceptor());   // 添加日志拦截器
        }
        Retrofit mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(HttpConfig.baseUrl)
                .addConverterFactory(GsonConverterFactory.create()) // 添加gson转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())   // 添加rxjava转换器
                .build();
        mBmobApiService = mRetrofit.create(BmobApiService.class);
    }

    public static Bmob getInstance() {
        if (null == INSTANCE) {
            synchronized (Bmob.class) {
                if (null == INSTANCE) {
                    INSTANCE = new Bmob();
                }
            }
        }
        return INSTANCE;
    }

    public BmobApiService api() {
        return mBmobApiService;
    }


    /**
     * 初始化应用
     *
     * @param appId
     * @param apiKey
     */
    public void init(String appId, String apiKey) {
        setAppId(appId);
        setApiKey(apiKey);
    }
}
