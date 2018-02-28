package cn.bmob.api;

import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface BmobApiService {
    @POST("/1/classes/{tableName}")
    Call<JsonObject> insert(@Path("tableName")String tableName, @Body Object object);

    @DELETE("/1/classes/{tableName}/{objectId}")
    Call<JsonObject> deleteRow(@Path("tableName")String tableName, @Path("objectId")String objectId);

    @PUT("/1/classes/{tableName}/{objectId}")
    Call<JsonObject> deleteColumn(@Path("tableName")String tableName, @Path("objectId")String objectId);

    @PUT("/1/classes/{tableName}/{objectId}")
    Call<JsonObject> update(@Path("tableName")String tableName,
                            @Path("objectId")String objectId,
                            @Body Object object);




    @GET("/1/classes/{tableName}")
    Call<JsonObject> find(@Path("tableName")String tableName);

    @GET("/1/classes/{tableName}")
    Call<JsonObject> find(@Path("tableName")String tableName, @Query("where") JsonObject where);

    @GET("/1/classes/{tableName}")
    Call<JsonObject> find(@Path("tableName")String tableName,
                          @Query("where") JsonObject where,
                          @Query("limit") int limit,
                          @Query("skip") int skip);

    @GET("/1/classes/{tableName}")
    Call<JsonObject> find(@Path("tableName")String tableName,
                          @Query("where") JsonObject where,
                          @Query("limit") int limit,
                          @Query("skip") int skip,
                          @Query("order") String order);

    @GET("/1/classes/{tableName}")
    Call<JsonObject> find(@Path("tableName")String tableName, @QueryMap Map<String, String> options);
}
