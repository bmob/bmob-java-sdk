package cn.bmob.data.utils;

import cn.bmob.data.bean.type.BmobACL;
import cn.bmob.data.bean.type.BmobRelation;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;


/**
 * @author smile
 *         Created by Administrator on 2016/5/5.
 *         注：此类不混淆，防止as出现
 */
public class GsonUtil {

    /**
     * json to list
     *
     * @param value
     * @param <T>
     * @return
     */
    public static <T> List<T> toList(String value) {
        Gson gson = new Gson();
        return gson.fromJson(value, List.class);
    }

    /**
     * json to object
     *
     * @param value
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Object toObject(String value, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(value, clazz);
    }

    /**
     * json to object
     *
     * @param value
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> Object toObject(JsonElement value, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(value, clazz);
    }

    /**
     * object 2 json
     *
     * @param value
     * @return
     */
    public static String toJson(Object value) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        // 主要解决遇到BmobACL对象时，不对整个对象解析，只解析对象中的acl属性(接口要求的格式是acl属性的内容)
        gsonBuilder.registerTypeAdapter(BmobACL.class,
                new JsonSerializer<BmobACL>() {
                    @Override
                    public JsonElement serialize(BmobACL src, Type typeOfSrc,
                                                 JsonSerializationContext context) {
                        Gson gson = new Gson();
                        return gson.toJsonTree(src.getAcl());
                    }
                });
        // 解决BmobRelation中的objects没有数据时不进行解析
        gsonBuilder.registerTypeAdapter(BmobRelation.class,
                new JsonSerializer<BmobRelation>() {

                    @Override
                    public JsonElement serialize(BmobRelation src,
                                                 Type typeOfSrc, JsonSerializationContext context) {
                        if (src.getObjects().size() == 0) {
                            return null;
                        }
                        return new Gson().toJsonTree(src);
                    }
                });
        Gson gson = gsonBuilder.create();
        return gson.toJson(value);
    }

    /**
     * @param map
     * @return
     */
    public static <T> String mapToJson(Map<String, T> map) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(map);
        return jsonStr;
    }

    /**
     * 根据json字符串返回Map对象
     *
     * @param json
     * @return
     */
    public static Map<String, Object> toMap(String json) {
        return new Gson().fromJson(json, new TypeToken<Map<String, Object>>() {
        }.getType());
    }
}
