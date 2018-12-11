package cn.bmob.javasdkdemo.test.bean;

import cn.bmob.data.bean.table.BmobUser;
import lombok.Data;


@Data
public class TestUser extends BmobUser {

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private Integer gender;
}
