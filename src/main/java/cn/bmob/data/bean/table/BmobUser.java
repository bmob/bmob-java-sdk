package cn.bmob.data.bean.table;

import cn.bmob.data.Bmob;
import cn.bmob.data.callback.object.GetListener;
import cn.bmob.data.callback.user.CheckUserSessionListener;
import cn.bmob.data.callback.user.LoginListener;
import cn.bmob.data.callback.user.SignUpListener;
import cn.bmob.data.callback.user.SignUpOrLoginSmsCodeListener;
import cn.bmob.data.exception.BmobException;
import cn.bmob.data.utils.Utils;
import com.google.gson.JsonObject;
import retrofit2.Call;

import static cn.bmob.data.utils.Utils.request;

public class BmobUser extends BmobObject {


    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 邮箱是否已经认证
     */
    private Boolean emailVerified;
    /**
     * session token 是否已经登录
     */
    private String sessionToken;
    /**
     * 手机号码
     */
    private String mobilePhoneNumber;
    /**
     * 手机号码是否已认证
     */
    private Boolean mobilePhoneNumberVerified;

    /**
     * 手机短信验证码
     */
    private String smsCode;

    /**
     * 无参构造
     */
    public BmobUser() {
        super("_User");
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public Boolean getMobilePhoneNumberVerified() {
        return mobilePhoneNumberVerified;
    }

    public void setMobilePhoneNumberVerified(Boolean mobilePhoneNumberVerified) {
        this.mobilePhoneNumberVerified = mobilePhoneNumberVerified;
    }


    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    /**
     * 用户登录
     *
     * @param loginListener
     * @param <T>
     */
    public <T extends BmobUser> void login(final LoginListener<T> loginListener) {
        Call<JsonObject> call = Bmob.getInstance().api().login(username, password);
        request(call, loginListener);
    }


    /**
     * 注册
     *
     * @param signUpListener
     */
    public void signUp(final SignUpListener signUpListener) {
        Call<JsonObject> call = Bmob.getInstance().api().signUp(Utils.getJsonObjectRequest(this,data));
        request(call, signUpListener);
    }


    /**
     * 通过短信验证码一键登录或注册
     * 如果是新用户，则进行注册；且如果不设置username，则默认username将是手机号码，且默认mobilePhoneVerified将是true。
     * 如果是旧用户，则自动登陆。
     *
     * @param signUpOrLoginSmsCodeListener
     */
    public <T extends BmobUser> void signUpOrLoginSmsCode(final SignUpOrLoginSmsCodeListener<T> signUpOrLoginSmsCodeListener) {
        if (Utils.isStringEmpty(smsCode)) {
            signUpOrLoginSmsCodeListener.onFailure(new BmobException("Please input smsCode first.", 9015));
            return;
        }

        Call<JsonObject> call = Bmob.getInstance().api().signUp(Utils.getJsonObjectRequest(this,data));
        request(call, signUpOrLoginSmsCodeListener);
    }


    /**
     * 获取某个用户的信息
     *
     * @param objectId
     * @param getListener
     * @param <T>
     */
    public static <T extends BmobUser> void getUserInfo(String objectId, GetListener<T> getListener) {
        if (Utils.isStringEmpty(objectId)) {
            getListener.onFailure(new BmobException("Please input objectId first.", 9015));
            return;
        }
        Call<JsonObject> call = Bmob.getInstance().api().getUserInfo(objectId);
        request(call, getListener);
    }

    /**
     * 获取某个用户的信息
     *
     * @param checkUserSessionListener
     */
    public void checkUserSession(CheckUserSessionListener checkUserSessionListener) {
        if (!isLogin()) {
            checkUserSessionListener.onFailure(new BmobException("Please login first.", 9015));
            return;
        }
        BmobUser bmobUser = getCurrentUser(BmobUser.class);
        Call<JsonObject> call = Bmob.getInstance().api().checkUserSession(bmobUser.getObjectId());
        request(call, checkUserSessionListener);
    }


    private static BmobUser instance;


    public static BmobUser getInstance() {
        if (instance == null) {
            instance = new BmobUser();
        }
        return instance;
    }

    private String currUser;


    public String getCurrUser() {
        return currUser;
    }

    public void setCurrUser(String currUser) {
        this.currUser = currUser;
    }

    /**
     * 获取当前登录用户
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public <T extends BmobUser> T getCurrentUser(Class<T> clazz) {
        if (isLogin()) {
            return Utils.getObjectFromJson(getCurrUser(), clazz);
        }
        return null;
    }


    /**
     * 当前是否已经有用户登录
     *
     * @return
     */
    public boolean isLogin() {
        return !Utils.isStringEmpty(getCurrUser());
    }


}
