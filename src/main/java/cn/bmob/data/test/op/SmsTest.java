package cn.bmob.data.test.op;

import cn.bmob.data.Bmob;
import cn.bmob.data.bean.op.BmobQuery;
import cn.bmob.data.bean.op.BmobSms;
import cn.bmob.data.bean.table.BmobUser;
import cn.bmob.data.callback.object.GetListener;
import cn.bmob.data.callback.sms.SendSmsCodeListener;
import cn.bmob.data.callback.sms.VerifySmsCodeListener;
import cn.bmob.data.callback.user.LoginListener;
import cn.bmob.data.callback.user.SignUpListener;
import cn.bmob.data.callback.user.SignUpOrLoginSmsCodeListener;
import cn.bmob.data.exception.BmobException;

import static cn.bmob.data.test.bean.TestConfig.apiKey;
import static cn.bmob.data.test.bean.TestConfig.appId;

public class SmsTest {




    public static void main(String[] args) {
        //TODO Application Entrance


        Bmob.getInstance().init(appId, apiKey);


        signUp();
        sendSms();
        verifySmsCode();
        signUpOrLoginSmsCode();


    }

    private static void getUserInfo(String objectId) {
        BmobUser.getUserInfo(objectId, new GetListener<BmobUser>() {
            @Override
            public void onSuccess(BmobUser user) {
                System.out.println("user info " + user.getUsername() + "-" + user.getObjectId());
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
            }
        });
        //相当于

        BmobQuery bmobQuery = new BmobQuery();
        bmobQuery.getObject(objectId, new GetListener<BmobUser>() {
            @Override
            public void onSuccess(BmobUser user) {
                System.out.println("user info " + user.getUsername() + "-" + user.getObjectId());
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
            }
        });
    }

    private static void verifySmsCode() {

        BmobSms bmobSms = new BmobSms();
        bmobSms.setMobilePhoneNumber("13760289294");
        bmobSms.verifySmsCode("090235", new VerifySmsCodeListener() {
            @Override
            public void onSuccess(String msg) {
                System.out.println("验证短信验证码成功：" + msg);
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println("验证短信验证码失败：" + ex.getMessage());
            }
        });
    }

    private static void sendSms() {

        BmobSms bmobSms = new BmobSms();
        bmobSms.setMobilePhoneNumber("13760289294");
        bmobSms.sendSmsCode(new SendSmsCodeListener() {
            @Override
            public void onSuccess(String smsId) {
                System.out.println("发送短信成功：" + smsId);
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println("发送短信失败：" + ex.getCode() + "-" + ex.getMessage());
            }
        });
    }


    /**
     * 注册
     */
    private static void signUpOrLoginSmsCode() {
        BmobUser bmobUser = new BmobUser();
        bmobUser.setMobilePhoneNumber("13760289294");
        bmobUser.setSmsCode("692199");
        bmobUser.signUpOrLoginSmsCode(new SignUpOrLoginSmsCodeListener<BmobUser>() {
            @Override
            public void onSuccess(BmobUser user) {
                System.out.println("sign up " + user.getUsername() + "-" + user.getObjectId());
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
            }
        });
    }

    /**
     * 注册
     */
    private static void signUp() {
        final String username = System.currentTimeMillis() + "";
        final String password = System.currentTimeMillis() + "";
        BmobUser bmobUser = new BmobUser();
        bmobUser.setUsername(username);
        bmobUser.setPassword(password);
        bmobUser.signUp(new SignUpListener() {


            @Override
            public void onSuccess(String objectId, String createdAt) {
                System.out.println("sign up " + objectId + "-" + createdAt);
                getUserInfo(objectId);
//                login(username, password);
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
        BmobUser bmobUser = new BmobUser();
        bmobUser.setUsername(username);
        bmobUser.setPassword(password);
        bmobUser.login(new LoginListener<BmobUser>() {
            public void onSuccess(BmobUser user) {
                System.out.println("login " + user.getUsername() + user.getSessionToken());
            }


            @Override
            public void onFailure(BmobException ex) {
                System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
            }
        });
    }



}
