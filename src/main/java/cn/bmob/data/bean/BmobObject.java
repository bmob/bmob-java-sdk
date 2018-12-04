package cn.bmob.data.bean;

import cn.bmob.data.Bmob;
import cn.bmob.data.callback.object.DeleteListener;
import cn.bmob.data.callback.object.SaveListener;
import cn.bmob.data.callback.object.UpdateListener;
import cn.bmob.data.utils.Utils;
import com.google.gson.JsonObject;
import retrofit2.Call;

import java.io.Serializable;

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




}
