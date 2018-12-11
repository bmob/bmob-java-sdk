package cn.bmob.data.bean.type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BmobRelation implements Serializable {


    private String __op;
    private List<BmobPointer> objects;

    /**
     * 创建关联数据类型
     *
     * @param value 关联数据对象
     */
    public BmobRelation(Object value) {
        objects = new ArrayList<>();
        objects.add(new BmobPointer(value));
    }

    public BmobRelation() {
        objects = new ArrayList<>();
    }


    /**
     * 加入多对多关联中的某个关联
     * @param value
     */
    public void add(Object value) {
        this.__op = "AddRelation";
        objects.add(new BmobPointer(value));
    }


    /**
     * 删除多对多关联中的某个关联
     * @param value
     */
    public void remove(Object value) {
        this.__op = "RemoveRelation";
        objects.add(new BmobPointer(value));
    }

    @Deprecated
    public void isRemove(boolean state) {
        if (state) {
            this.__op = "RemoveRelation";
        }
    }

    public String get__op() {
        return __op;
    }

    public List<BmobPointer> getObjects() {
        return Collections.unmodifiableList(objects);
    }

    public void setObjects(List<BmobPointer> objects) {
        this.objects = objects;
    }


}
