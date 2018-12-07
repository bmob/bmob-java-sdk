package cn.bmob.data.bean.table;

import cn.bmob.data.Bmob;
import cn.bmob.data.callback.email.SendEmailListener;
import cn.bmob.data.callback.object.GetListener;
import cn.bmob.data.callback.object.UpdateListener;
import cn.bmob.data.callback.user.*;
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
     * 无参构造
     */
    public BmobUser() {
        super("_User");
    }

    /**
     * 实例
     */
    private static BmobUser instance;


    /**
     * 获取实例
     *
     * @return
     */
    public static BmobUser getInstance() {
        if (instance == null) {
            instance = new BmobUser();
        }
        return instance;
    }

    /**
     * 当前用户信息，JSON格式
     */
    private String currUser;


    /**
     * @return
     */
    public String getCurrUser() {
        return currUser;
    }

    /**
     * @param currUser
     */
    public void setCurrUser(String currUser) {
        this.currUser = currUser;
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
        Call<JsonObject> call = Bmob.getInstance().api().signUp(Utils.getJsonObjectRequest(this, data));
        request(call, signUpListener);
    }


    /**
     * 通过短信验证码一键登录或注册
     * 如果是新用户，则进行注册；且如果不设置username，则默认username将是手机号码，且默认mobilePhoneVerified将是true。
     * 如果是旧用户，则自动登陆。
     *
     * @param smsCode
     * @param signUpOrLoginSmsCodeListener
     */
    public <T extends BmobUser> void signUpOrLoginSmsCode(String smsCode, final SignUpOrLoginSmsCodeListener<T> signUpOrLoginSmsCodeListener) {
        if (Utils.isStringEmpty(smsCode)) {
            signUpOrLoginSmsCodeListener.onFailure(new BmobException("Please input smsCode first.", 9015));
            return;
        }

        Call<JsonObject> call = Bmob.getInstance().api().signUp(Utils.getJsonObjectRequest(this, data));
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
     * 更新某个用户的信息
     */
    public void updateUserInfo(UpdateListener updateListener) {
        Call<JsonObject> call = Bmob.getInstance().api().updateUserInfo(getObjectId(), Utils.getJsonObjectRequest(this, data));
        request(call, updateListener);
    }

    /**
     * 检查登录是否失效
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


    /**
     * 发送重置密码的邮件
     * @param email
     * @param sendEmailListener
     */
    public static void sendEmailForResetPassword(String email, SendEmailListener sendEmailListener) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        Call<JsonObject> call = Bmob.getInstance().api().sendEmailForResetPassword(jsonObject);
        request(call, sendEmailListener);
    }

    /**
     * 发送验证用户邮箱的邮件
     * @param email
     * @param sendEmailListener
     */
    public static void sendEmailForVerifyUserEmail(String email, SendEmailListener sendEmailListener) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        Call<JsonObject> call = Bmob.getInstance().api().sendEmailForVerifyUserEmail(jsonObject);
        request(call, sendEmailListener);
    }




    /**
     * 使用旧密码重置密码，需要用户登录
     *
     * @param oldPassword
     * @param newPassword
     * @param resetPasswordListener
     */
    public void resetPasswordByOldPassword(String oldPassword, String newPassword, ResetPasswordListener resetPasswordListener) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("oldPassword", oldPassword);
        jsonObject.addProperty("newPassword", newPassword);
        Call<JsonObject> call = Bmob.getInstance().api().resetUserPasswordByOldPassword(getObjectId(), jsonObject);
        request(call, resetPasswordListener);
    }


    /**
     * 使用短信验证码重置密码
     *
     * @param smsCode
     * @param newPassword
     * @param resetPasswordListener
     */
    public static void resetPasswordBySmsCode(String smsCode, String newPassword, ResetPasswordListener resetPasswordListener) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("password", newPassword);
        Call<JsonObject> call = Bmob.getInstance().api().resetUserPasswordByOldPassword(smsCode, jsonObject);
        request(call, resetPasswordListener);
    }


}
