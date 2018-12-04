package cn.bmob.data;

import cn.bmob.data.bean.BmobQuery;
import cn.bmob.data.bean.BmobSms;
import cn.bmob.data.bean.BmobUser;
import cn.bmob.data.callback.object.*;
import cn.bmob.data.callback.user.LoginListener;
import cn.bmob.data.callback.user.SignUpListener;
import cn.bmob.data.callback.sms.SendSmsCodeListener;
import cn.bmob.data.callback.sms.VerifySmsCodeListener;
import cn.bmob.data.callback.user.SignUpOrLoginSmsCodeListener;
import cn.bmob.data.config.HttpConfig;
import cn.bmob.data.exception.BmobException;

import java.util.List;

public class Test {


    public static void main(String[] args) {
        //TODO Application Entrance


        Bmob.getInstance().init(HttpConfig.appId, HttpConfig.apiKey);


//        signUp();
//        saveObject();

//        sendSms();
//        verifySmsCode();
//        signUpOrLoginSmsCode();


        gets();


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
        bmobQuery.get(objectId, new GetListener<BmobUser>() {
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
//        bmobSms.setTemplate("template");
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


    /**
     * 新增一条数据
     */
    private static void saveObject() {
        GameScore gameScore = new GameScore();
        gameScore.setCheatMode(false);
        gameScore.setPlayerName("michael");
        gameScore.setScore(100);
        gameScore.save(new SaveListener() {
            @Override
            public void onSuccess(String objectId, String createdAt) {
                System.out.println("save：" + objectId + "-" + createdAt);
                update(objectId);
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
            }
        });
    }

    /**
     * 获取一条数据
     *
     * @param objectId
     */
    private static void get(final String objectId) {
        BmobQuery bmobQuery = new BmobQuery();
        bmobQuery.get(objectId, new GetListener<GameScore>() {
            @Override
            public void onSuccess(GameScore gameScore) {
                System.out.println("user：" + gameScore.getPlayerName() + "-" + gameScore.getScore() + gameScore.getObjectId() + "-" + gameScore.getCreatedAt() + "-" + gameScore.getUpdatedAt());

                delete(objectId);
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
            }
        });
    }


    /**
     * 获取一条数据
     */
    private static void gets() {
        BmobQuery bmobQuery = new BmobQuery();
        bmobQuery.addWhereEqualTo("score", 10);
        bmobQuery.getsWhere(new GetsListener<GameScore>() {
            @Override
            public void onSuccess(List<GameScore> array) {


                System.out.println(array.size() + " gets");
            }

            @Override
            public void onFailure(BmobException ex) {


                System.err.println(ex.getMessage());
            }
        });
    }

    /**
     * 删除一条数据
     *
     * @param objectId
     */
    private static void delete(String objectId) {
        GameScore gameScore = new GameScore();
        gameScore.setObjectId(objectId);
        gameScore.delete(new DeleteListener() {
            @Override
            public void onSuccess(String msg) {
                System.out.println("delete： " + msg);
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
            }
        });
    }


    /**
     * 更新一条数据
     *
     * @param objectId
     */
    private static void update(final String objectId) {
        GameScore gameScore = new GameScore();
        gameScore.setObjectId(objectId);
        gameScore.setPlayerName("jenny");
        gameScore.update(new UpdateListener() {
            @Override
            public void onSuccess(String updatedAt) {
                System.out.println("update：" + "-" + updatedAt);
                get(objectId);
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
            }
        });
    }
}
