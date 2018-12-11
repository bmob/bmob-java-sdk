package cn.bmob.javasdkdemo.test.table;

import cn.bmob.data.Bmob;
import cn.bmob.data.bean.op.BmobQuery;
import cn.bmob.data.bean.table.BmobRole;
import cn.bmob.data.bean.table.BmobUser;
import cn.bmob.data.callback.object.GetsListener;
import cn.bmob.data.callback.object.SaveListener;
import cn.bmob.data.callback.object.UpdateListener;
import cn.bmob.data.callback.user.SignUpListener;
import cn.bmob.data.exception.BmobException;
import cn.bmob.javasdkdemo.test.bean.TestConfig;

import java.util.List;

public class RoleTest {


    public static void main(String[] args) {
        //TODO Application Entrance
        Bmob.getInstance().init(TestConfig.appId, TestConfig.apiKey);


        singUp();
    }

    private static void singUp() {
        BmobUser bmobUser = new BmobUser();
        bmobUser.setUsername("username");
        bmobUser.setPassword("password");
        bmobUser.signUp(new SignUpListener() {
            @Override
            public void onSuccess(String objectId, String createdAt) {
                System.out.println(objectId);
                queryRole("role-name");
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println(ex.getMessage());
            }
        });
    }

    /**
     * 查询角色
     * @param name
     */
    private static void queryRole(final String name) {
        BmobQuery bmobQuery = new BmobQuery();
        bmobQuery.addWhereEqualTo("name", name);
        bmobQuery.getObjects(new GetsListener<BmobRole>() {
            @Override
            public void onSuccess(List<BmobRole> array) {
                if (array.size() < 1) {
                    saveRole(name);
                } else {
                    updateAddRole(array.get(0).getObjectId());
                    updateRemoveRole(array.get(0).getObjectId());
                }
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println(ex.getMessage());
            }
        });
    }

    /**
     *
     * 保存角色
     * @param name
     */
    private static void saveRole(String name) {
        BmobRole bmobRole = new BmobRole();
        //TODO Role names must be restricted to alphanumeric characters, dashes(-), underscores(_), and spaces.
        bmobRole.setName(name);
        BmobUser bmobUser = BmobUser.getInstance().getCurrentUser(BmobUser.class);
        bmobRole.getUsers().add(bmobUser);
        bmobRole.save(new SaveListener() {
            @Override
            public void onSuccess(String objectId, String createdAt) {
                System.out.println(objectId + "-" + createdAt);
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println(ex.getMessage());
            }
        });
    }


    /**
     * 添加角色中的用户
     * @param objectId
     */
    private static void updateAddRole(String objectId) {
        BmobRole bmobRole = new BmobRole();
        //TODO Role names must be restricted to alphanumeric characters, dashes(-), underscores(_), and spaces.
        BmobUser bmobUser = BmobUser.getInstance().getCurrentUser(BmobUser.class);
        bmobRole.getUsers().add(bmobUser);
        bmobRole.setObjectId(objectId);
        bmobRole.update(new UpdateListener() {
            @Override
            public void onSuccess(String updatedAt) {
                System.out.println(updatedAt);
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println(ex.getMessage());
            }
        });
    }

    /**
     * 删除角色中的用户
     * @param objectId
     */
    private static void updateRemoveRole(String objectId) {
        BmobRole bmobRole = new BmobRole();
        //TODO Role names must be restricted to alphanumeric characters, dashes(-), underscores(_), and spaces.
        BmobUser bmobUser = BmobUser.getInstance().getCurrentUser(BmobUser.class);
        bmobRole.getUsers().remove(bmobUser);
        bmobRole.setObjectId(objectId);
        bmobRole.update(new UpdateListener() {
            @Override
            public void onSuccess(String updatedAt) {
                System.out.println(updatedAt);
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println(ex.getMessage());
            }
        });
    }

}
