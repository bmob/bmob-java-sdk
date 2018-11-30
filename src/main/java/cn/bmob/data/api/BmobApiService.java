package cn.bmob.data.api;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface BmobApiService {


    /**
     * 新增一行数据
     *
     * @param tableName
     * @param object
     * @return
     */
    @POST("/1/classes/{tableName}")
    Call<JsonObject> insert(@Path("tableName") String tableName, @Body Object object);


    /**
     * 删除一行数据
     *
     * @param tableName
     * @param objectId
     * @return
     */
    @DELETE("/1/classes/{tableName}/{objectId}")
    Call<JsonObject> deleteRow(@Path("tableName") String tableName, @Path("objectId") String objectId);


    @PUT("/1/classes/{tableName}/{objectId}")
    Call<JsonObject> deleteColumn(@Path("tableName") String tableName, @Path("objectId") String objectId);

    @PUT("/1/classes/{tableName}/{objectId}")
    Call<JsonObject> update(@Path("tableName") String tableName,
                            @Path("objectId") String objectId,
                            @Body Object object);

    @GET("/1/classes/{tableName}/{objectId}")
    Call<JsonObject> get(@Path("tableName") String tableName, @Path("objectId") String objectId);


    @GET("/1/classes/{tableName}")
    Call<JsonObject> find(@Path("tableName") String tableName);

    @GET("/1/classes/{tableName}")
    Call<JsonObject> find(@Path("tableName") String tableName, @Query("where") JsonObject where);

    @GET("/1/classes/{tableName}")
    Call<JsonObject> find(@Path("tableName") String tableName,
                          @Query("where") JsonObject where,
                          @Query("limit") int limit,
                          @Query("skip") int skip);

    @GET("/1/classes/{tableName}")
    Call<JsonObject> find(@Path("tableName") String tableName,
                          @Query("where") JsonObject where,
                          @Query("limit") int limit,
                          @Query("skip") int skip,
                          @Query("order") String order);

    @GET("/1/classes/{tableName}")
    Call<JsonObject> find(@Path("tableName") String tableName, @QueryMap Map<String, String> options);


    @POST("/1/users")
    Call<JsonObject> signUp(@Body Object object);


    @GET("/1/login")
    Call<JsonObject> login(@Query("username") String username, @Query("password") String password);


    /**
     * 发送短信
     *
     * @param object
     * @return
     */
    @POST("/1/requestSmsCode")
    Call<JsonObject> sendSmsCode(@Body Object object);


    /**
     * 验证短信验证码
     *
     * @param smsCode
     * @param object
     * @return
     */
    @POST("/1/verifySmsCode/{smsCode}")
    Call<JsonObject> verifySmsCode(@Path("smsCode") String smsCode, @Body Object object);


}
