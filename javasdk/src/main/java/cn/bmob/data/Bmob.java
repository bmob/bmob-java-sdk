package cn.bmob.data;

import cn.bmob.data.api.BmobApiService;
import cn.bmob.data.config.BmobConfig;
import cn.bmob.data.utils.BmobInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class Bmob {


    /**
     * APP ID
     */
    private String appId;
    /**
     * REST API KEY
     */
    private String apiKey;


    /**
     * MASTER KEY
     */
    private String masterKey;


    /**
     * @return appId
     */
    public String getAppId() {
        return appId;
    }

    /**
     * @param appId
     */
    private void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * @return apiKey
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * @param apiKey
     */
    private void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }


    /**
     * @return master key
     */
    public String getMasterKey() {
        return masterKey;
    }

    /**
     * @param masterKey
     * @return
     */
    public Bmob setMasterKey(String masterKey) {
        this.masterKey = masterKey;
        return this;
    }

    /**
     *
     */
    private volatile static Bmob INSTANCE;
    /**
     *
     */
    private volatile static BmobApiService mBmobApiService;

    /**
     *
     */
    private Bmob() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(BmobConfig.connectionTime, TimeUnit.SECONDS);
        // 使用拦截器在request中添加统一header内容
        builder.addInterceptor(BmobInterceptor.headerInterceptor());
        if (BmobConfig.isDebug()) {
            // 添加日志拦截器
            builder.addInterceptor(BmobInterceptor.logInterceptor());
        }
        Retrofit mRetrofit = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(BmobConfig.DEFAULT_REST_API_HOST)
                // 添加GSON转换器
                .addConverterFactory(GsonConverterFactory.create())
                // 添加RxJava转换器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mBmobApiService = mRetrofit.create(BmobApiService.class);
    }


    /**
     * @return 单例
     */
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

    /**
     * @return API
     */
    public BmobApiService api() {
        return mBmobApiService;
    }


    /**
     * 初始化应用
     *
     * @param appId  appId
     * @param apiKey apiKey
     */
    public void init(String appId, String apiKey) {
        setAppId(appId);
        setApiKey(apiKey);
    }


    public void init(String appId, String apiKey, String masterKey) {
        init(appId,apiKey);
        setMasterKey(masterKey);
    }
}
