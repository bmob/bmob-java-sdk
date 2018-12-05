package cn.bmob.data.utils;

import cn.bmob.data.bean.resp.BmobResponse;
import cn.bmob.data.bean.table.*;
import cn.bmob.data.callback.base.BmobCallback;
import cn.bmob.data.callback.base.BmobGetCallback;
import cn.bmob.data.callback.base.BmobOkCallback;
import cn.bmob.data.callback.base.BmobSaveCallback;
import cn.bmob.data.callback.file.UploadFileListener;
import cn.bmob.data.callback.object.CountListener;
import cn.bmob.data.callback.object.GetsListener;
import cn.bmob.data.callback.object.UpdateListener;
import cn.bmob.data.callback.sms.SendSmsCodeListener;
import cn.bmob.data.callback.user.LoginListener;
import cn.bmob.data.callback.user.SignUpOrLoginSmsCodeListener;
import cn.bmob.data.exception.BmobException;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static <T extends BmobObject> T getObjectFromResponseAndBmobCallback(Response<JsonObject> response, BmobCallback bmobCallback) throws IOException {
        Class<T> clazz = Utils.getClassFromBmobCallback(bmobCallback);
        Gson gson = new Gson();
        return gson.fromJson(getJsonFromResponse(response), clazz);
    }


    /**
     * 获取对象
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> T getObjectFromJson(String json, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
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
                //登录、短信验证码一键注册或登录
                if (bmobCallback instanceof LoginListener || bmobCallback instanceof SignUpOrLoginSmsCodeListener) {
                    //TODO 保存user的json信息到本地，还是内存中？B/S系统怎么存？C/S系统怎么存？

                    String json = Utils.getJsonFromResponse(response);
                    BmobUser.getInstance().setCurrUser(json);
                }
                ((BmobGetCallback) bmobCallback).onSuccess(Utils.getObjectFromResponseAndBmobCallback(response, bmobCallback));
            } else if (bmobCallback instanceof BmobSaveCallback) {
                ((BmobSaveCallback) bmobCallback).onSuccess(bmobResponse.getObjectId(), bmobResponse.getCreatedAt());
            } else if (bmobCallback instanceof BmobOkCallback) {
                ((BmobOkCallback) bmobCallback).onSuccess(bmobResponse.getMsg());
            } else if (bmobCallback instanceof UpdateListener) {
                ((UpdateListener) bmobCallback).onSuccess(bmobResponse.getUpdatedAt());
            } else if (bmobCallback instanceof SendSmsCodeListener) {
                ((SendSmsCodeListener) bmobCallback).onSuccess(bmobResponse.getSmsId());
            } else if (bmobCallback instanceof GetsListener) {
                ((GetsListener) bmobCallback).onSuccess(bmobResponse.getResults());
            } else if (bmobCallback instanceof CountListener) {
                ((CountListener) bmobCallback).onSuccess(bmobResponse.getCount());
            } else if (bmobCallback instanceof UploadFileListener) {
                ((UploadFileListener) bmobCallback).onSuccess(bmobResponse.getCdn(), bmobResponse.getFilename(), bmobResponse.getUrl());
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


    /**
     * 将由服务器生成的基础属性的值置空
     * 使用transient：没办法使得反序列化包含这些属性值
     * 使用Expose：没办法使得序列化去除这些属性值
     *
     * @param bmobObject
     * @return
     */
    public static BmobObject removeEssentialAttribute(BmobObject bmobObject) {
        bmobObject.setObjectId(null);
        bmobObject.setCreatedAt(null);
        bmobObject.setUpdatedAt(null);
        return bmobObject;
    }


    /**
     * @param className
     * @return
     */
    public static Class<? extends BmobObject> getBmobObjectClassByClassName(String className) {
        if (className.equals(BmobRole.class.getSimpleName())) {
            return BmobRole.class;
        } else if (className.equals(BmobUser.class.getSimpleName())) {
            return BmobUser.class;
        } else {
            return null;
        }
    }


    /**
     * 从对象中获取对应的表名
     *
     * @param object
     * @return
     */
    public static String getTableNameFromObject(Object object) {
        if (object instanceof BmobUser) {
            return "_User";
        } else if (object instanceof BmobRole) {
            return "_Role";
        } else if (object instanceof BmobArticle) {
            return "_Article";
        } else if (object instanceof BmobInstallation) {
            return "_Installation";
        } else if (object instanceof BmobObject) {
            BmobObject bmobObject = (BmobObject) object;
            return bmobObject.getClass().getSimpleName();
        } else {
            return null;
        }
    }


    /**
     * 从对象中获取objectId
     *
     * @param value
     * @return
     */
    public static String getObjectIdFromObject(Object value) {
        if (value instanceof BmobObject) {
            BmobObject bmobObject = (BmobObject) value;
            return bmobObject.getObjectId();
        } else {
            return null;
        }
    }


    /**
     * @param jsonObject
     * @param key
     * @param value
     * @return
     */
    public static JsonObject add2Object(JsonObject jsonObject, String key, Object value) {
        if (value instanceof Number) {
            jsonObject.addProperty(key, (Number) value);
        } else if (value instanceof String) {
            jsonObject.addProperty(key, (String) value);
        } else if (value instanceof Character) {
            jsonObject.addProperty(key, (Character) value);
        } else if (value instanceof Boolean) {
            jsonObject.addProperty(key, (Boolean) value);
        } else {
            jsonObject.add(key, (JsonElement) value);
        }
        return jsonObject;
    }

    /**
     * @param jsonArray
     * @param value
     * @return
     */
    public static JsonArray add2Array(JsonArray jsonArray, Object value) {
        if (value instanceof Number) {
            jsonArray.add((Number) value);
        } else if (value instanceof String) {
            jsonArray.add((String) value);
        } else if (value instanceof Character) {
            jsonArray.add((Character) value);
        } else if (value instanceof Boolean) {
            jsonArray.add((Boolean) value);
        } else {
            jsonArray.add((JsonElement) value);
        }
        return jsonArray;
    }


    /**
     * @param origin
     * @param target
     * @param count
     * @return
     */
    public static int indexOf(String origin, String target, int count) {
        Pattern pattern = Pattern.compile(target);
        Matcher findMatcher = pattern.matcher(origin);
        int number = 0;
        while (findMatcher.find()) {
            number++;
            if (number == count) {
                break;
            }
        }
        return findMatcher.start();
    }


    /**
     * 获取域名之后的一段文件地址
     *
     * @param url
     * @return
     */
    public static String getTargetUrl(String url) {
        return url.substring(Utils.indexOf(url, "/", 3) + 1, url.length());
    }
}
