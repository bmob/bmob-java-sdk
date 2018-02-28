package cn.bmob;

import cn.bmob.config.HttpConfig;
import cn.bmob.api.BmobApiService;
import cn.bmob.utils.InterceptorUtil;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class Bmob {
    private volatile static Bmob INSTANCE;
    private volatile static BmobApiService mBmobApiService;

    private Bmob(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(HttpConfig.connectionTime, TimeUnit.SECONDS);
        builder.addInterceptor(InterceptorUtil.headerInterceptor());   // 使用拦截器在request中添加统一header内容
        if(HttpConfig.debug){
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

    public static Bmob getInstance(){
        if (null == INSTANCE){
            synchronized (Bmob.class){
                if (null == INSTANCE){
                    INSTANCE = new Bmob();
                }
            }
        }
        return INSTANCE;
    }

    public BmobApiService api(){
        return mBmobApiService;
    }
}
