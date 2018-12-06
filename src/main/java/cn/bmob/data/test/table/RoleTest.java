package cn.bmob.data.test.table;

import cn.bmob.data.Bmob;
import cn.bmob.data.bean.table.BmobRole;
import cn.bmob.data.bean.table.BmobUser;
import cn.bmob.data.callback.object.SaveListener;
import cn.bmob.data.callback.user.LoginListener;
import cn.bmob.data.exception.BmobException;
import cn.bmob.data.test.bean.TestConfig;

public class RoleTest {


    public static void main(String[] args) {
        //TODO Application Entrance

        Bmob.getInstance().init(TestConfig.appId, TestConfig.apiKey);


        BmobUser bmobUser = new BmobUser();
        bmobUser.setUsername("username");
        bmobUser.setPassword("password");
        bmobUser.login(new LoginListener<BmobUser>() {
            @Override
            public void onSuccess(BmobUser user) {
                System.out.println(user.getUsername());


                BmobRole bmobRole = new BmobRole();
                //TODO Role names must be restricted to alphanumeric characters, dashes(-), underscores(_), and spaces.
                bmobRole.setName("role-name");
                bmobRole.getUsers().add(user);
                bmobRole.save(new SaveListener() {
                    @Override
                    public void onSuccess(String objectId, String createdAt) {
                        System.out.println(objectId + "-" + createdAt);
                    }

                    @Override
                    public void onFailure(BmobException ex) {
                        ex.getMessage();
                    }
                });
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println(ex.getMessage());
            }
        });



    }
}
