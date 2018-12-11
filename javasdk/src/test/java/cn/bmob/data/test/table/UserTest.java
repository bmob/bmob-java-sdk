package cn.bmob.data.test.table;

import cn.bmob.data.Bmob;
import cn.bmob.data.bean.op.BmobQuery;
import cn.bmob.data.bean.table.BmobUser;
import cn.bmob.data.callback.email.SendEmailListener;
import cn.bmob.data.callback.object.DeleteListener;
import cn.bmob.data.callback.object.GetListener;
import cn.bmob.data.callback.object.GetsListener;
import cn.bmob.data.callback.object.UpdateListener;
import cn.bmob.data.callback.user.*;
import cn.bmob.data.exception.BmobException;
import cn.bmob.data.test.bean.TestConfig;
import cn.bmob.data.test.bean.TestUser;

import java.util.List;

public class UserTest {


    public static void main(String[] args) {
        //TODO Application Entrance
        Bmob.getInstance().init(TestConfig.appId, TestConfig.apiKey);
        /**
         * 用户名密码注册
         */
//        signUp();
//        /**
//         * 手机短信验证码注册
//         */
//        signUpOrLoginSmsCode("", "");

        /**
         * 获取多个用户信息
         */
//        getUsers();


        login("1544174390195", "1544174390195");


    }


    /**
     * 注册
     */
    private static void signUp() {
        final String username = System.currentTimeMillis() + "";
        final String password = System.currentTimeMillis() + "";
        TestUser testUser = new TestUser();
        testUser.setNickname("用户昵称");
        testUser.setAge(20);
        testUser.setGender(1);
        testUser.setUsername(username);
        testUser.setPassword(password);
        testUser.signUp(new SignUpListener() {
            @Override
            public void onSuccess(String objectId, String createdAt) {
                System.out.println("sign up " + objectId + "-" + createdAt);
                /**
                 * 注册后登录
                 */
                login(username, password);
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
            }
        });
    }


    /**
     * 登录
     *
     * @param username
     * @param password
     */
    private static void login(String username, String password) {
        TestUser testUser = new TestUser();
        testUser.setUsername(username);
        testUser.setPassword(password);
        testUser.login(new LoginListener<TestUser>() {
            public void onSuccess(TestUser user) {
                System.out.println("login " + user.getUsername() + "-" + user.getSessionToken());
//                /**
//                 * 获取用户信息
//                 */
//                getUserInfo(user.getObjectId());
//                /**
//                 * 查询用户信息
//                 */
//                queryUserInfo(user.getObjectId());
//                /**
//                 * 检查登录是否失效
//                 */
//                checkUserSession();
//                /**
//                 * 更新用户信息
//                 */
//                updateUserInfo();
//                /**
//                 * 删除用户
//                 */
//                deleteUser();


                bind();
//                unbind();

            }


            @Override
            public void onFailure(BmobException ex) {
                System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
            }
        });
    }


    /**
     * 获取用户信息
     *
     * @param objectId
     */
    private static void getUserInfo(String objectId) {
        BmobUser.getUserInfo(objectId, new GetListener<TestUser>() {
            @Override
            public void onSuccess(TestUser user) {
                System.out.println("get user info " + user.getUsername() + "-" + user.getObjectId());
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
            }
        });
    }

    /**
     * 查询用户信息
     *
     * @param objectId
     */
    private static void queryUserInfo(String objectId) {
        BmobQuery bmobQuery = new BmobQuery();
        bmobQuery.getObject(objectId, new GetListener<TestUser>() {
            @Override
            public void onSuccess(TestUser user) {
                System.out.println("query user info " + user.getUsername() + "-" + user.getObjectId());
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
            }
        });
    }


    /**
     * 检查登录是否失效
     */
    private static void checkUserSession() {
        TestUser testUser = BmobUser.getInstance().getCurrentUser(TestUser.class);
        testUser.checkUserSession(new CheckUserSessionListener() {
            @Override
            public void onSuccess(String msg) {
                System.out.println(msg);
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println(ex.getMessage());
            }
        });
    }

    /**
     * 手机短信验证码注册/登录，需先发送短信验证码
     *
     * @param phoneNumber
     * @param smsCode
     */
    private static void signUpOrLoginSmsCode(String phoneNumber, String smsCode) {
        TestUser testUser = new TestUser();
        testUser.setMobilePhoneNumber(phoneNumber);
        testUser.signUpOrLoginSmsCode(smsCode, new SignUpOrLoginSmsCodeListener<TestUser>() {
            @Override
            public void onSuccess(TestUser user) {
                System.out.println("sms code sign up login" + user.getUsername() + "-" + user.getObjectId());
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
            }
        });
    }


    /**
     * 修改用户信息，需确保objectId不为空，确保用户已经登录。
     * 在更新用户信息时，若用户邮箱有变更并且在管理后台打开了邮箱验证选项，Bmob云后端会自动发送一封验证邮件给用户。
     */
    private static void updateUserInfo() {
        if (!BmobUser.getInstance().isLogin()) {
            System.err.println("尚未登录");
            return;
        }
        TestUser testUser = BmobUser.getInstance().getCurrentUser(TestUser.class);
        testUser.setNickname("修改用户昵称");
        testUser.setGender(2);
        testUser.setAge(30);
        testUser.updateUserInfo(new UpdateListener() {
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
     * 删除用户，需确保objectId不为空，确保用户已经登录。
     */
    private static void deleteUser() {
        if (!BmobUser.getInstance().isLogin()) {
            System.err.println("尚未登录");
            return;
        }
        TestUser testUser = BmobUser.getInstance().getCurrentUser(TestUser.class);
        testUser.delete(new DeleteListener() {
            @Override
            public void onSuccess(String msg) {
                System.out.println(msg);
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println(ex.getMessage());
            }
        });
    }


    /**
     * 获取多个用户信息
     */
    private static void getUsers() {
        BmobQuery bmobQuery = new BmobQuery();
        bmobQuery.getObjects(new GetsListener<BmobUser>() {
            @Override
            public void onSuccess(List<BmobUser> array) {
                System.out.println("get users " + array.size());
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println(ex.getMessage());
            }
        });
    }


    /**
     * 发送重置密码的邮件
     *
     * @param email
     */
    private static void sendEmailForResetPassword(String email) {

        BmobUser.sendEmailForResetPassword(email, new SendEmailListener() {
            @Override
            public void onSuccess(String msg) {
                System.out.println(msg);
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println(ex.getMessage());
            }
        });
    }


    /**
     * 发送验证用户邮箱的邮件
     *
     * @param email
     */
    private static void sendEmailForVerifyUserEmail(String email) {

        BmobUser.sendEmailForVerifyUserEmail(email, new SendEmailListener() {
            @Override
            public void onSuccess(String msg) {
                System.out.println(msg);
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println(ex.getMessage());
            }
        });
    }

    /**
     * 旧密码重置密码，需要登录
     *
     * @param oldPassword
     * @param newPassword
     */
    private static void resetPasswordByOldPassword(String oldPassword, String newPassword) {
        if (!BmobUser.getInstance().isLogin()) {
            System.err.println("尚未登录");
            return;
        }
        TestUser testUser = BmobUser.getInstance().getCurrentUser(TestUser.class);
        testUser.resetPasswordByOldPassword(oldPassword, newPassword, new ResetPasswordListener() {
            @Override
            public void onSuccess(String msg) {
                System.out.println(msg);
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println(ex.getMessage());
            }
        });
    }


    /**
     * 短信验证码重置密码
     *
     * @param smsCode
     * @param newPassword
     */
    private static void resetPasswordBySmsCode(String smsCode, String newPassword) {
        BmobUser.resetPasswordBySmsCode(smsCode, newPassword, new ResetPasswordListener() {
            @Override
            public void onSuccess(String msg) {
                System.out.println(msg);
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println(ex.getMessage());
            }
        });
    }


    /**
     * 登录
     */
    private static void login() {
        BmobUser.loginQQ("", "", "", new ThirdLoginListener<TestUser>() {
            @Override
            public void onSuccess(TestUser user) {
                System.out.println(user.getObjectId());
            }


            @Override
            public void onFailure(BmobException ex) {
                System.err.println(ex.getMessage());
            }
        });
    }

    /**
     * 绑定
     */
    private static void bind() {
        if (!BmobUser.getInstance().isLogin()) {
            System.err.println("尚未登录");
            return;
        }
        TestUser testUser = BmobUser.getInstance().getCurrentUser(TestUser.class);
        testUser.bindQQ("", "", "", new ThirdBindListener() {
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
     * 解绑
     */
    private static void unbind() {
        if (!BmobUser.getInstance().isLogin()) {
            System.err.println("尚未登录");
            return;
        }
        TestUser testUser = BmobUser.getInstance().getCurrentUser(TestUser.class);
        testUser.unBindQQ(new ThirdUnBindListener() {
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
