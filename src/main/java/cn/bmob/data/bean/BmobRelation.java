package cn.bmob.data.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BmobRelation implements Serializable {



    /**
     *
     */
    private static final long serialVersionUID = 7419229244419967901L;
    private String __op = "AddRelation";
    private List<BmobPointer> objects;

    /**
     * 创建关联数据类型
     * @param value 关联数据对象
     */
    public BmobRelation(Object value){
        objects = new ArrayList<BmobPointer>();
        objects.add(new BmobPointer(value));
    }

    public BmobRelation(){
        objects = new ArrayList<BmobPointer>();
    }

    public void add(Object value){
        objects.add(new BmobPointer(value));
    }

    public void remove(Object value){
        this.__op = "RemoveRelation";
        objects.add(new BmobPointer(value));
    }

    @Deprecated
    public void isRemove(boolean state){
        if(state){
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
