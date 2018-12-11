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
import retrofit2.Call;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;

import static cn.bmob.data.utils.Utils.request;


public class BmobObject implements Serializable {


    /**
     * 额外数据，对字段的操作
     */
    public static transient JsonObject data;

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


    /**
     * @return  数据唯一标志
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     * @param objectId 数据唯一标志
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    /**
     * @return 数据创建时间
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt 数据创建时间
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }


    /**
     * @return 数据更新时间
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt 数据更新时间
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }


    /**
     * @return 表名
     */
    public String getTableName() {
        return tableName;
    }


    /**
     * @param tableName 表名
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }


    /**
     * @return 类名
     */
    public String getClassName() {
        return className;
    }


    /**
     * @param className 类名
     */
    public void setClassName(String className) {
        this.className = className;
    }


    /**
     * @return 同一访问控制
     */
    public BmobACL getACL() {
        return ACL;
    }

    /**
     * @param ACL 同一访问控制
     */
    public void setACL(BmobACL ACL) {
        this.ACL = ACL;
    }


    //TODO======================================数组字段操作==================================================

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
        data.add(key, addFieldOperation("Add", values));
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
        data.add(key, addFieldOperation("AddUnique", values));
    }

    /**
     * 从一个数组字段的值内移除指定的多个值
     *
     * @param key    字段名称(数组类型)
     * @param values 要移除的多个值
     */
    public void removeAll(String key, Collection<?> values) {
        data.add(key, addFieldOperation("Remove", values));
    }

    /**
     * 删除一个字段
     *
     * @param key 字段名称
     */
    public void remove(String key) {
        JsonObject delete = new JsonObject();
        delete.addProperty("__op", "Delete");
        data.add(key, delete);
    }


    /**
     * 删除一个字段
     *
     * @param key   字段名称
     * @param value 值
     */
    public void remove(String key, Object value) {
        removeAll(key, Arrays.asList(new Object[]{value}));
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


    //TODO================================================自增字段操作=======================================

    /**
     * 给指定的字段自增1
     *
     * @param key 要增量的字段名
     */
    public void increment(String key) {
        increment(key, 1);
    }


    /**
     * 给指定的字段自动增加指定的数量，如果需要自减则取负值
     *
     * @param key    要增量的字段名
     * @param amount 要增量的数字
     */
    public void increment(String key, Number amount) {
        JsonObject increment = new JsonObject();
        increment.addProperty("__op", "Increment");
        increment.addProperty("amount", amount);
        data.add(key, increment);
    }


    //TODO=======================================================数据操作==============================================

    /**
     * 更新一行数据
     *
     * @param updateListener 更新监听
     */
    public void update(final UpdateListener updateListener) {


        Call<JsonObject> call = Bmob.getInstance().api().update(tableName, objectId, Utils.getJsonObjectRequest(this, data));
        request(call, updateListener);
    }


    /**
     * 保存一行数据
     *
     * @param saveListener 保存监听
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


        Call<JsonObject> call = Bmob.getInstance().api().insert(tableName, Utils.getJsonObjectRequest(this, data));
        request(call, saveListener);
    }

    /**
     * 删除一行数据
     *
     * @param deleteListener 删除监听
     */
    public void delete(final DeleteListener deleteListener) {
        Call<JsonObject> call = Bmob.getInstance().api().deleteRow(tableName, objectId);
        request(call, deleteListener);
    }


    /**
     * 子类是泛型的类型强转：父类转为子类
     *
     * @param object 对象
     * @param clazz 类型
     * @param <T> 泛型
     * @return 对象
     */
    protected static <T extends BmobObject> T bmobObjectCastSubClass(BmobObject object, Class<T> clazz) {
        return (T) object;
    }

}
