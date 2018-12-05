package cn.bmob.data.test.bean;

import cn.bmob.data.bean.table.BmobUser;
import lombok.Data;


@Data
public class TestUser extends BmobUser {

    /**
     * 昵称
     */
    private String nickname;


}
