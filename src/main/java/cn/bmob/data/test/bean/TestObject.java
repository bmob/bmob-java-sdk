package cn.bmob.data.test.bean;


import cn.bmob.data.bean.table.BmobObject;
import cn.bmob.data.bean.table.BmobUser;
import cn.bmob.data.bean.type.BmobDate;
import cn.bmob.data.bean.type.BmobFile;
import cn.bmob.data.bean.type.BmobGeoPoint;
import cn.bmob.data.bean.type.BmobRelation;
import lombok.Data;

import java.util.List;

@Data
public class TestObject extends BmobObject {
    /**
     * String类型
     */
    private String str;
    /**
     * Boolean类型
     */
    private Boolean boo;
    /**
     * Integer类型
     */
    private Integer integer;
    /**
     * Long类型
     */
    private Long lon;
    /**
     * Double类型
     */
    private Double dou;
    /**
     * Float类型
     */
    private Float flt;
    /**
     * Short类型
     */
    private Short sht;
    /**
     * Byte类型
     */
    private Byte byt;
    /**
     * Character
     */
    private Character cht;
    /**
     * 地理位置类型
     */
    private BmobGeoPoint geo;
    /**
     * 文件类型
     */
    private BmobFile file;
    /**
     * 时间类型
     */
    private BmobDate date;
    /**
     * 数组类型
     */
    private List<String> array;
    /**
     * Pointer 一对多关系
     */
    private BmobUser user;
    /**
     * Relation 多对多关系类型
     */
    private BmobRelation relation;
}
