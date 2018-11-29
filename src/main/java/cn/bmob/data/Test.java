package cn.bmob.data;

import cn.bmob.data.bean.BmobUser;
import cn.bmob.data.callback.*;
import cn.bmob.data.callback.get.GetListener;
import cn.bmob.data.callback.get.LoginListener;
import cn.bmob.data.callback.save.SaveListener;
import cn.bmob.data.callback.save.SignUpListener;
import cn.bmob.data.config.HttpConfig;
import cn.bmob.data.exception.BmobException;

public class Test {


    public static void main(String[] args) {
        //TODO Application Entrance


        Bmob.getInstance().init(HttpConfig.appId, HttpConfig.apiKey);


        signUp();
        saveObject();
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
                System.out.println("sign up "+objectId + "-" + createdAt);
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
        BmobUser bmobUser = new BmobUser();
        bmobUser.setUsername(username);
        bmobUser.setPassword(password);
        bmobUser.login(new LoginListener<BmobUser>() {
            public void onSuccess(BmobUser user) {
                System.out.println("login "+user.getUsername() + user.getSessionToken());
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

        GameScore gameScore = new GameScore();
        gameScore.setObjectId(objectId);
        gameScore.get(new GetListener<GameScore>() {
            @Override
            public void onSuccess(GameScore gameScore) {
                System.out.println("get：" + gameScore.getPlayerName() + "-" + gameScore.getScore());

                delete(objectId);
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
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
