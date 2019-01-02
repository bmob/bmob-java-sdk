package cn.bmob.data.utils;

import cn.bmob.data.bean.resp.BmobResponse;
import cn.bmob.data.bean.table.BmobArticle;
import cn.bmob.data.bean.table.BmobObject;
import cn.bmob.data.bean.table.BmobRole;
import cn.bmob.data.bean.table.BmobUser;
import cn.bmob.data.callback.base.BmobCallback;
import cn.bmob.data.callback.base.BmobGetCallback;
import cn.bmob.data.callback.base.BmobOkCallback;
import cn.bmob.data.callback.base.BmobSaveCallback;
import cn.bmob.data.callback.file.UploadFileListener;
import cn.bmob.data.callback.object.CountListener;
import cn.bmob.data.callback.object.GetsListener;
import cn.bmob.data.callback.object.GetsStringListener;
import cn.bmob.data.callback.object.UpdateListener;
import cn.bmob.data.callback.sms.SendSmsCodeListener;
import cn.bmob.data.callback.user.*;
import cn.bmob.data.config.BmobConfig;
import cn.bmob.data.exception.BmobException;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
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
     * 从类名称获取表名称
     *
     * @param clazz
     * @return
     */
    public static String getTableNameFromClass(Class clazz) {
        String tableName;

        String s = clazz.getGenericSuperclass().getTypeName();
        if (s.equals(BmobUser.class.getTypeName())) {
            tableName = "_User";
        } else if (s.equals(BmobRole.class.getTypeName())) {
            tableName = "_Role";
        } else if (s.equals(BmobArticle.class.getTypeName())) {
            tableName = "_Article";
        } else {
            tableName = clazz.getSimpleName();
        }



        System.out.println(s);

        return tableName;




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
     * @param json  json
     * @param clazz 类
     * @param <T>   泛型
     * @return 对象
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
     * @throws IOException
     */
    public static void handleResponse(Response<JsonObject> response, BmobCallback bmobCallback) throws IOException {

        BmobResponse bmobResponse = Utils.getBmobResponseFromResponse(response);
        if (bmobResponse.getCode() == 0) {
            //操作成功
            if (bmobCallback instanceof BmobGetCallback) {
                //登录、短信验证码一键注册或登录
                if (bmobCallback instanceof LoginListener || bmobCallback instanceof SignUpOrLoginSmsCodeListener || bmobCallback instanceof ThirdLoginListener) {
                    //TODO 保存user的json信息到本地，还是内存中？B/S系统怎么存？C/S系统怎么存？

                    String json = Utils.getJsonFromResponse(response);
                    /**
                     * 设置当前用户信息
                     */
                    BmobUser.getInstance().setCurrUser(json);
                    /**
                     * 设置登录token
                     */
                    BmobUser.getInstance().setSessionToken(bmobResponse.getSessionToken());
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
            } else if (bmobCallback instanceof GetsStringListener) {
                String json = Utils.getJsonFromResponse(response);
                ((GetsStringListener) bmobCallback).onSuccess(json);
            } else if (bmobCallback instanceof CountListener) {
                ((CountListener) bmobCallback).onSuccess(bmobResponse.getCount());
            } else if (bmobCallback instanceof UploadFileListener) {
                ((UploadFileListener) bmobCallback).onSuccess(bmobResponse.getCdn(), bmobResponse.getFilename(), bmobResponse.getUrl());
            } else if (bmobCallback instanceof ThirdBindListener) {
                ((ThirdBindListener) bmobCallback).onSuccess(bmobResponse.getUpdatedAt());
            } else if (bmobCallback instanceof ThirdUnBindListener) {
                ((ThirdUnBindListener) bmobCallback).onSuccess(bmobResponse.getUpdatedAt());
            }

        } else {
            //操作失败
            BmobException bmobException = new BmobException(bmobResponse.getError(), bmobResponse.getCode());
            bmobCallback.onFailure(bmobException);
        }
    }


    /**
     * 网络请求回调
     *
     * @param call
     * @param bmobCallback
     */
    public static void request(Call<JsonObject> call, final BmobCallback bmobCallback) {
        if (BmobConfig.isSynchronous()) {
            execute(call, bmobCallback);
        } else {
            enqueue(call, bmobCallback);
        }
    }


    /**
     * 网络请求回调
     *
     * @param synchronous
     * @param call
     * @param bmobCallback
     */
    public static void request(boolean synchronous, Call<JsonObject> call, final BmobCallback bmobCallback) {
        if (synchronous) {
            execute(call, bmobCallback);
        } else {
            enqueue(call, bmobCallback);
        }
    }


    /**
     * 异步请求，异步回调
     *
     * @param call
     * @param bmobCallback
     */
    public static void enqueue(Call<JsonObject> call, final BmobCallback bmobCallback) {
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
     * 同步请求，同步回调
     *
     * @param call
     * @param bmobCallback
     */
    public static void execute(Call<JsonObject> call, final BmobCallback bmobCallback) {
        try {
            Response<JsonObject> response = call.execute();
            handleResponse(response, bmobCallback);
            return;
        } catch (IOException e) {
            bmobCallback.onFailure(new BmobException(e, 9015));
            e.printStackTrace();
        }
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
        if (bmobObject instanceof BmobUser) {
            ((BmobUser) bmobObject).setSessionToken(null);
        }
        return bmobObject;
    }


    /**
     * @param jsonObject
     * @param data
     */
    public static void getDataObject(JsonObject jsonObject, JsonObject data) {
        if (data.size() > 0) {
            Set<Map.Entry<String, JsonElement>> entrySet = data.entrySet();
            Iterator<Map.Entry<String, JsonElement>> iterator = entrySet.iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String key = entry.getKey();
                JsonElement jsonElement = entry.getValue();
                jsonObject.add(key, jsonElement);
            }
        }
    }

    /**
     * @param bmobObject
     * @return
     */
    public static JsonObject getJsonObjectFromObject(BmobObject bmobObject) {
        String json = GsonUtil.toJson(bmobObject);
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        jsonObject = disposePointerType(bmobObject, jsonObject);
        return jsonObject;
    }


    /**
     * 处理Pointer类型
     *
     * @param jsonObject
     * @return JsonObject
     */
    public static JsonObject disposePointerType(BmobObject bmobObject, JsonObject jsonObject) {
        Field[] fields = bmobObject.getClass().getDeclaredFields();
        for (Field f : fields) {
            if (BmobUser.class.isAssignableFrom(f.getType())) {

                if (jsonObject.get(f.getName()) != null) {
                    JsonObject obj = new JsonObject();
                    obj.addProperty("__type", "Pointer");
                    obj.addProperty("objectId", jsonObject.get(f.getName()).getAsJsonObject().get("objectId").getAsString());
                    obj.addProperty("className", "_User");
                    jsonObject.add(f.getName(), obj);
                }
                continue;
            } else if (BmobRole.class.isAssignableFrom(f.getType())) {
                if (jsonObject.get(f.getName()) != null) {
                    JsonObject obj = new JsonObject();
                    obj.addProperty("__type", "Pointer");
                    obj.addProperty("objectId", jsonObject.get(f.getName()).getAsJsonObject().get("objectId").getAsString());
                    obj.addProperty("className", "_Role");
                    jsonObject.add(f.getName(), obj);
                }
                continue;
            } else if (BmobObject.class.isAssignableFrom(f.getType())) {
                if (jsonObject.get(f.getName()) != null) {
                    JsonObject obj = new JsonObject();
                    obj.addProperty("__type", "Pointer");
                    obj.addProperty("objectId", jsonObject.get(f.getName()).getAsJsonObject().get("objectId").getAsString());
                    obj.addProperty("className", f.getType().getSimpleName());
                    jsonObject.add(f.getName(), obj);
                }
            }
        }
        return jsonObject;
    }


    /**
     * @param bmobObject
     * @param data
     * @return
     */
    public static JsonObject getJsonObjectRequest(BmobObject bmobObject, JsonObject data) {
        JsonObject jsonObject = Utils.getJsonObjectFromObject(Utils.removeEssentialAttribute(bmobObject));
        Utils.getDataObject(jsonObject, data);
        return jsonObject;
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


    /**
     * @return
     */
    public static JsonObject getAnonymousJsonObject() {
        JsonObject anonymous = new JsonObject();
        anonymous.addProperty("id", UUID.randomUUID().toString().toLowerCase().replace("-", ""));
        JsonObject platform = new JsonObject();
        platform.add("anonymous", anonymous);
        return platform;
    }

    /**
     * @param openid
     * @param access_token
     * @param expires_in
     */
    public static JsonObject getWeixinObject(String openid, String access_token, String expires_in) {
        JsonObject weixin = new JsonObject();
        weixin.addProperty("openid", openid);
        weixin.addProperty("access_token", access_token);
        weixin.addProperty("expires_in", expires_in);
        JsonObject platform = new JsonObject();
        platform.add("weixin", weixin);
        return platform;
    }


    /**
     * @param openid
     * @param access_token
     * @param expires_in
     * @return
     */
    public static JsonObject getQQObject(String openid, String access_token, String expires_in) {
        JsonObject qq = new JsonObject();
        qq.addProperty("openid", openid);
        qq.addProperty("access_token", access_token);
        qq.addProperty("expires_in", expires_in);
        JsonObject platform = new JsonObject();
        platform.add("qq", qq);
        return platform;
    }

    /**
     * @param uid
     * @param access_token
     * @param expires_in
     * @return
     */
    public static JsonObject getWeiboObject(String uid, String access_token, String expires_in) {
        JsonObject weibo = new JsonObject();
        weibo.addProperty("uid", uid);
        weibo.addProperty("access_token", access_token);
        weibo.addProperty("expires_in", expires_in);
        JsonObject platform = new JsonObject();
        platform.add("weibo", weibo);
        return platform;
    }
}
