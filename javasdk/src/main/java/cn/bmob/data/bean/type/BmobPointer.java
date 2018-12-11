package cn.bmob.data.bean.type;

import cn.bmob.data.utils.Utils;

import java.io.Serializable;


/**
 * 一对一、一对多关联关系
 */
public class BmobPointer implements Serializable {

    private String __type = "Pointer";
    private String className;
    private String objectId;

    //为反序列化，添加空构造方法
    public BmobPointer() {
    }

    public BmobPointer(String className, String objectId) {
        setClassName(className);
        setObjectId(objectId);
    }

    /**
     * @param value
     */
    public BmobPointer(Object value) {
        setClassName(Utils.getTableNameFromObject(value));
        setObjectId(Utils.getObjectIdFromObject(value));
    }

    private String get__type() {
        return __type;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }


}
