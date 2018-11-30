package cn.bmob.data.bean;

import android.os.Parcel;
import android.os.Parcelable;
import cn.bmob.data.Bmob;
import cn.bmob.data.callback.object.DeleteListener;
import cn.bmob.data.callback.object.GetListener;
import cn.bmob.data.callback.object.SaveListener;
import cn.bmob.data.callback.object.UpdateListener;
import com.google.gson.JsonObject;
import retrofit2.Call;

import static cn.bmob.data.utils.Utils.request;


public class BmobObject implements Parcelable {


    public static final String CREATED_AT = "createdAt";
    public static final String UPDATED_AT = "updatedAt";
    public static final String OBJECT_ID = "objectId";


    /**
     * 表名
     */
    private transient String _c_;

    /**
     * 数据唯一标志
     */
    private transient String objectId;

    /**
     * 数据创建时间
     */
    private transient String createdAt;

    /**
     * 数据更新时间
     */
    private transient String updatedAt;

    /**
     * 数据访问控制权限
     */
    private BmobACL ACL;


    /**
     * 构造函数
     */
    public BmobObject() {
        this._c_ = this.getClass().getSimpleName();
    }


    /**
     * 构造函数
     */
    public BmobObject(String _c_) {
        if (_c_ != null) {
            this._c_ = _c_;
        }
    }

    /**
     *
     * @return
     */
    public String getObjectId() {
        return objectId;
    }

    /**
     *
     * @param objectId
     */
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    /**
     *
     * @return
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }


    /**
     *
     * @return
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {

    }


    /**
     * 更新一行数据
     *
     * @param updateListener
     */
    public void update(final UpdateListener updateListener) {
        Call<JsonObject> call = Bmob.getInstance().api().update(_c_, objectId, this);
        request(call, updateListener);
    }

    /**
     * 保存一行数据
     *
     * @param saveListener
     */
    public void save(final SaveListener saveListener) {
        Call<JsonObject> call = Bmob.getInstance().api().insert(_c_, this);
        request(call, saveListener);
    }

    /**
     * 删除一行数据
     *
     * @param deleteListener
     */
    public void delete(final DeleteListener deleteListener) {
        Call<JsonObject> call = Bmob.getInstance().api().deleteRow(_c_, objectId);
        request(call, deleteListener);
    }


    /**
     * 获取一条数据
     *
     * @param getListener
     */
    public <T> void get(final GetListener<T> getListener) {
        Call<JsonObject> call = Bmob.getInstance().api().get(_c_, objectId);
        request(call, getListener);
    }

}
