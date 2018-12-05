package cn.bmob.data.bean.table;

import cn.bmob.data.Bmob;
import cn.bmob.data.bean.type.BmobACL;
import cn.bmob.data.callback.object.DeleteListener;
import cn.bmob.data.callback.object.SaveListener;
import cn.bmob.data.callback.object.UpdateListener;
import cn.bmob.data.exception.BmobException;
import cn.bmob.data.utils.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.oracle.javafx.jmx.json.JSONException;
import retrofit2.Call;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import static cn.bmob.data.utils.Utils.request;


public class BmobObject implements Serializable {


    private JsonObject data;

    /**
     * 表名
     */
    private transient String tableName;


    /**
     * 类名
     */
    private transient String className;

    /**
     * 数据唯一标志
     */
    private String objectId;

    /**
     * 数据创建时间
     */
    private String createdAt;

    /**
     * 数据更新时间
     */
    private String updatedAt;

    /**
     * 数据访问控制权限
     */
    private BmobACL ACL;


    /**
     * 构造函数
     */
    public BmobObject() {
        this.tableName = this.getClass().getSimpleName();
        this.className = this.getClass().getSimpleName();
        data = new JsonObject();
    }


    /**
     * 构造函数
     */
    public BmobObject(String tableName) {
        if (tableName != null) {
            this.tableName = tableName;
        }
        this.className = this.getClass().getSimpleName();
        data = new JsonObject();
    }


    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }

    /**
     * @return
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * @param objectId
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    /**
     * @return
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }


    /**
     * @return
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    /**
     * 更新一行数据
     *
     * @param updateListener
     */
    public void update(final UpdateListener updateListener) {
        Call<JsonObject> call = Bmob.getInstance().api().update(tableName, objectId, Utils.removeEssentialAttribute(this));
        request(call, updateListener);
    }

    /**
     * 保存一行数据
     *
     * @param saveListener
     */
    public void save(final SaveListener saveListener) {

        if ("_User".equals(tableName)) {
            saveListener.onFailure(new BmobException("Table _User is not support save operation.", 9015));
            return;
        }

        if ("_Article".equals(tableName)) {
            saveListener.onFailure(new BmobException("Table _Article is not support save operation.", 9015));
            return;
        }


        setObjectId(null);
        setCreatedAt(null);
        setUpdatedAt(null);
        Call<JsonObject> call = Bmob.getInstance().api().insert(tableName, Utils.removeEssentialAttribute(this));
        request(call, saveListener);
    }

    /**
     * 删除一行数据
     *
     * @param deleteListener
     */
    public void delete(final DeleteListener deleteListener) {
        Call<JsonObject> call = Bmob.getInstance().api().deleteRow(tableName, objectId);
        request(call, deleteListener);
    }


    /**
     * 子类是泛型的类型强转：父类转为子类
     *
     * @param object
     * @param clazz
     * @param <T>
     * @return
     */
    protected static <T extends BmobObject> T bmobObjectCastSubClass(BmobObject object, Class<T> clazz) {
        return (T) object;
    }







    //TODO======================================数组操作==================================================
    /**
     * 在一个数组字段中添加一个值
     *
     * @param key   字段名称(数组类型)
     * @param value 要添加的值
     */
    public void add(String key, Object value) {
        addAll(key, Arrays.asList(new Object[]{value}));
    }

    /**
     * 在一个数组字段中添加多个值
     *
     * @param key    字段名称(数组类型)
     * @param values 要添加的多个值
     */
    public void addAll(String key, Collection<?> values) {
        try {
            data.add(key, addFieldOperation("Add", values));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 在一个数组字段中添加一个值(只有在原本数组字段中不存在该值的情形下才会添加入数组，插入数组的位置不固定的)
     *
     * @param key   字段名称(数组类型)
     * @param value 要添加的值
     */
    public void addUnique(String key, Object value) {
        addAllUnique(key, Arrays.asList(new Object[]{value}));
    }

    /**
     * 在一个数组字段中添加多个值(只会在原本数组字段中不存在这些值的情形下才会添加入数组，插入数组的位置不固定的)
     *
     * @param key    字段名称(数组类型)
     * @param values 要添加的多个值
     */
    public void addAllUnique(String key, Collection<?> values) {
        try {
            data.add(key, addFieldOperation("AddUnique", values));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从一个数组字段的值内移除指定的多个值
     *
     * @param key    字段名称(数组类型)
     * @param values 要移除的多个值
     */
    public void removeAll(String key, Collection<?> values) {
        try {
            data.add(key, addFieldOperation("Remove", values));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除一个字段
     *
     * @param key 字段名称
     */
    public void remove(String key) {
        JsonObject operation = new JsonObject();
        operation.addProperty("__op", "Delete");
        data.add(key, operation);
    }


    /**
     * 组装操作字段的结构体
     *
     * @param type   操作类型
     * @param values 操作值
     * @return 返回组装后的结构体
     */
    private JsonObject addFieldOperation(String type, Collection<?> values) {
        JsonObject operation = new JsonObject();
        operation.addProperty("__op", type);
        JsonArray array = new JsonArray();
        for (Object object : values) {
            Utils.add2Array(array, object);
        }
        operation.add("objects", array);
        return operation;
    }
}
