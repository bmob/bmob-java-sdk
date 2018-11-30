package cn.bmob.data.utils;

import cn.bmob.data.bean.BmobResponse;
import cn.bmob.data.callback.base.BmobCallback;
import cn.bmob.data.callback.base.BmobOkCallback;
import cn.bmob.data.callback.object.UpdateListener;
import cn.bmob.data.callback.base.BmobGetCallback;
import cn.bmob.data.callback.base.BmobSaveCallback;
import cn.bmob.data.callback.sms.SendSmsCodeListener;
import cn.bmob.data.exception.BmobException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;

public class Utils {


    /**
     * 获取响应的JSON信息，包括成功和失败
     *
     * @param response
     * @return
     * @throws IOException
     */
    public static String getJsonFromResponse(Response<JsonObject> response) throws IOException {
        //请求成功
        String json;
        if (response.isSuccessful()) {
            //操作成功：2XX
            //Response{protocol=http/1.1, code=201, message=Created, url=https://api2.bmob.cn/1/classes/GameScore}
            //201 Created https://api2.bmob.cn/1/classes/GameScore
            json = response.body().toString();
            //{"createdAt":"2018-11-29 14:22:36","objectId":"ef960f742e"}
        } else {
            //操作失败：非2XX
            //Response{protocol=http/1.1, code=400, message=Bad Request, url=https://api2.bmob.cn/1/classes/GameScore}
            //400 Bad Request https://api2.bmob.cn/1/classes/GameScore
            /**
             * 400
             * bad request "错误的请求"
             * invalid hostname "不存在的域名"
             */
            json = response.errorBody().string();
            //{"code":105,"error":"invalid field name: _c_."}
        }
        return json;
    }


    /**
     * 获取响应对象
     *
     * @param json
     * @return
     */
    public static BmobResponse getBmobResponseFromJson(String json) {
        Gson gson = new Gson();
        BmobResponse bmobResponse = gson.fromJson(json, BmobResponse.class);
        return bmobResponse;
    }

    /**
     * 获取响应对象
     *
     * @param response
     * @return
     * @throws IOException
     */
    public static BmobResponse getBmobResponseFromResponse(Response<JsonObject> response) throws IOException {
        return getBmobResponseFromJson(getJsonFromResponse(response));
    }


    /**
     * 获取类
     *
     * @param bmobCallback
     * @param <T>
     * @return
     */
    public static <T> Class<T> getClassFromBmobCallback(BmobCallback bmobCallback) {
        Class<T> clazz = (Class<T>) ((ParameterizedType) bmobCallback.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return clazz;
    }


    /**
     * 获取对象
     *
     * @param response
     * @param bmobCallback
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T getObjectFromResponseAndBmobCallback(Response<JsonObject> response, BmobCallback bmobCallback) throws IOException {
        Class<T> clazz = Utils.getClassFromBmobCallback(bmobCallback);
        Gson gson = new Gson();
        return gson.fromJson(getJsonFromResponse(response), clazz);
    }


    /**
     * 处理错误信息
     *
     * @param t
     * @param bmobCallback
     */
    public static void handleThrowable(Throwable t, BmobCallback bmobCallback) {
        BmobException bmobException = new BmobException(t, 9015);
        bmobCallback.onFailure(bmobException);
    }


    /**
     * 处理请求结果
     *
     * @param response
     * @param bmobCallback
     */
    public static void handleResponse(Response<JsonObject> response, BmobCallback bmobCallback) throws IOException {

        BmobResponse bmobResponse = Utils.getBmobResponseFromResponse(response);
        if (bmobResponse.getCode() == 0) {
            //操作成功
            if (bmobCallback instanceof BmobGetCallback) {
                ((BmobGetCallback) bmobCallback).onSuccess(Utils.getObjectFromResponseAndBmobCallback(response, bmobCallback));
            } else if (bmobCallback instanceof BmobSaveCallback) {
                ((BmobSaveCallback) bmobCallback).onSuccess(bmobResponse.getObjectId(), bmobResponse.getCreatedAt());
            } else if (bmobCallback instanceof BmobOkCallback) {
                ((BmobOkCallback) bmobCallback).onSuccess(bmobResponse.getMsg());
            } else if (bmobCallback instanceof UpdateListener) {
                ((UpdateListener) bmobCallback).onSuccess(bmobResponse.getUpdatedAt());
            } else if (bmobCallback instanceof SendSmsCodeListener) {
                ((SendSmsCodeListener) bmobCallback).onSuccess(bmobResponse.getSmsId());
            }

        } else {
            //操作失败
            BmobException bmobException = new BmobException(bmobResponse.getError(), bmobResponse.getCode());
            bmobCallback.onFailure(bmobException);
        }
    }


    /**
     * @param call
     * @param bmobCallback
     */
    public static void request(Call<JsonObject> call, final BmobCallback bmobCallback) {
        call.enqueue(new Callback<JsonObject>() {
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    handleResponse(response, bmobCallback);
                } catch (IOException e) {
                    handleThrowable(new BmobException(e, 9015), bmobCallback);
                }
            }

            public void onFailure(Call<JsonObject> call, Throwable t) {
                handleThrowable(t, bmobCallback);
            }
        });
    }


    /**
     * 判断字符串是否为空
     *
     * @param string
     * @return
     */
    public static boolean isStringEmpty(String string) {
        boolean result = string == null || string.length() == 0;
        return result;
    }
}
