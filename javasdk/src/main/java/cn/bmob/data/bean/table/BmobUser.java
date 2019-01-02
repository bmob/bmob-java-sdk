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

import static cn.bmob.data.utils.Utils.*;

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
     * @return 单例
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
     * @return 当前用户JSON格式信息
     */
    public String getCurrUser() {
        return currUser;
    }

    /**
     * @param currUser 当前用户JSON格式信息
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
     * @param loginListener 登录监听
     * @param <T>           泛型
     */
    public <T extends BmobUser> void login(final LoginListener<T> loginListener) {
        Call<JsonObject> call = Bmob.getInstance().api().login(username, password);
        request(call, loginListener);
    }


    /**
     * 注册
     *
     * @param signUpListener 注册监听
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
     * @param smsCode                      短信验证码
     * @param signUpOrLoginSmsCodeListener 一键登录注册监听
     * @param <T> 泛型
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
     * @param objectId    唯一标志
     * @param getListener 获取监听
     * @param <T>         泛型
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
     *
     * @param updateListener 更新监听
     */
    public void updateUserInfo(UpdateListener updateListener) {
        /**
         * {
         *     "code": 202,
         *     "error": "username '' already taken."
         * }
         */
        data.remove("username");
        Call<JsonObject> call = Bmob.getInstance().api().updateUserInfo(getObjectId(), Utils.getJsonObjectRequest(this, data));
        request(call, updateListener);
    }

    /**
     * 检查登录是否失效
     *
     * @param checkUserSessionListener 检查token监听
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
     * @param clazz 类
     * @param <T>   泛型
     * @return 当前用户
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
     * @return 是否登录
     */
    public boolean isLogin() {
        return !Utils.isStringEmpty(getCurrUser());
    }


    /**
     * 发送重置密码的邮件
     *
     * @param email             邮箱
     * @param sendEmailListener 发送邮件监听
     */
    public static void sendEmailForResetPassword(String email, SendEmailListener sendEmailListener) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        Call<JsonObject> call = Bmob.getInstance().api().sendEmailForResetPassword(jsonObject);
        request(call, sendEmailListener);
    }

    /**
     * 发送验证用户邮箱的邮件
     *
     * @param email             邮箱
     * @param sendEmailListener 发送邮件监听
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
     * @param oldPassword           旧密码
     * @param newPassword           新密码
     * @param resetPasswordListener 重置密码监听
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
     * @param smsCode               短信验证码
     * @param newPassword           新密码
     * @param resetPasswordListener 重置密码监听
     */
    public static void resetPasswordBySmsCode(String smsCode, String newPassword, ResetPasswordListener resetPasswordListener) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("password", newPassword);
        Call<JsonObject> call = Bmob.getInstance().api().resetUserPasswordBySmsCode(smsCode, jsonObject);
        request(call, resetPasswordListener);
    }


    /**
     * 未登录，进行授权登录/注册
     *
     * @param platform           平台
     * @param thirdLoginListener 第三方登录监听
     */
    private static void thirdSignUpLogin(JsonObject platform, ThirdLoginListener thirdLoginListener) {
        JsonObject auth = new JsonObject();
        auth.add("authData", platform);
        Call<JsonObject> call = Bmob.getInstance().api().thirdSignUpLogin(auth);
        request(call, thirdLoginListener);
    }

    /**
     * 未登录，进行微博登录
     *
     * @param uid                uid
     * @param access_token       access_token
     * @param expires_in         expires_in
     * @param thirdLoginListener 第三方登录监听
     */
    public static void loginWeibo(String uid, String access_token, String expires_in, ThirdLoginListener thirdLoginListener) {
        thirdSignUpLogin(getWeiboObject(uid, access_token, expires_in), thirdLoginListener);
    }

    /**
     * 未登录，进行qq登录
     *
     * @param openid             openid
     * @param access_token       access_token
     * @param expires_in         expires_in
     * @param thirdLoginListener 第三方登录监听
     */
    public static void loginQQ(String openid, String access_token, String expires_in, ThirdLoginListener thirdLoginListener) {
        thirdSignUpLogin(getQQObject(openid, access_token, expires_in), thirdLoginListener);
    }

    /**
     * 未登录，进行微信登录
     *
     * @param openid             openid
     * @param access_token       access_token
     * @param expires_in         expires_in
     * @param thirdLoginListener 第三方登录监听
     */
    public static void loginWeixin(String openid, String access_token, String expires_in, ThirdLoginListener thirdLoginListener) {
        thirdSignUpLogin(getWeixinObject(openid, access_token, expires_in), thirdLoginListener);
    }

    /**
     * 未登录，进行匿名登录
     *
     * @param thirdLoginListener 第三方登录监听
     */
    public static void loginAnonymous(ThirdLoginListener thirdLoginListener) {

        thirdSignUpLogin(getAnonymousJsonObject(), thirdLoginListener);
    }


    /**
     * 已登录，进行授权绑定
     *
     * @param platform          平台
     * @param thirdBindListener 第三方绑定监听
     */
    private void thirdBind(JsonObject platform, ThirdBindListener thirdBindListener) {
        JsonObject auth = new JsonObject();
        auth.add("authData", platform);
        Call<JsonObject> call = Bmob.getInstance().api().thirdBind(getObjectId(), auth);
        request(call, thirdBindListener);
    }

    /**
     * 已登录，进行微博授权绑定
     *
     * @param uid               uid
     * @param access_token      access_token
     * @param expires_in        expires_in
     * @param thirdBindListener 第三方绑定监听
     */
    public void bindWeibo(String uid, String access_token, String expires_in, ThirdBindListener thirdBindListener) {
        thirdBind(getWeiboObject(uid, access_token, expires_in), thirdBindListener);
    }

    /**
     * 已登录，进行qq授权绑定
     *
     * @param openid            openid
     * @param access_token      access_token
     * @param expires_in        expires_in
     * @param thirdBindListener 第三方绑定监听
     */
    public void bindQQ(String openid, String access_token, String expires_in, ThirdBindListener thirdBindListener) {
        thirdBind(getQQObject(openid, access_token, expires_in), thirdBindListener);
    }

    /**
     * 已登录，进行微信授权绑定
     *
     * @param openid            openid
     * @param access_token      access_token
     * @param expires_in        expires_in
     * @param thirdBindListener 第三方绑定监听
     */
    public void bindWeixin(String openid, String access_token, String expires_in, ThirdBindListener thirdBindListener) {
        thirdBind(getWeixinObject(openid, access_token, expires_in), thirdBindListener);
    }


    /**
     * 已登录，进行匿名授权绑定
     *
     * @param thirdBindListener 第三方绑定监听
     */
    private void bindAnonymous(ThirdBindListener thirdBindListener) {
        thirdBind(getAnonymousJsonObject(), thirdBindListener);
    }


    /**
     * 已登录，进行授权绑定
     *
     * @param platform            平台
     * @param thirdUnBindListener 第三方解绑监听
     */
    private void thirdUnBind(JsonObject platform, ThirdUnBindListener thirdUnBindListener) {
        JsonObject auth = new JsonObject();
        auth.add("authData", platform);
        Call<JsonObject> call = Bmob.getInstance().api().thirdUnBind(getObjectId(), auth);
        request(call, thirdUnBindListener);
    }


    /**
     * 已登录，进行匿名解除绑定
     *
     * @param thirdUnBindListener 第三方解绑监听
     */
    private void unBindAnonymous(ThirdUnBindListener thirdUnBindListener) {
        JsonObject platform = new JsonObject();
        platform.addProperty("anonymous", "null");
        thirdUnBind(platform, thirdUnBindListener);
    }

    /**
     * 已登录，进行微博解除绑定
     *
     * @param thirdUnBindListener 第三方解绑监听
     */
    public void unBindWeibo(ThirdUnBindListener thirdUnBindListener) {
        JsonObject platform = new JsonObject();
        platform.addProperty("weibo", "null");
        thirdUnBind(platform, thirdUnBindListener);
    }


    /**
     * 已登录，进行微信解除绑定
     *
     * @param thirdUnBindListener 第三方解绑监听
     */
    public void unBindWeixin(ThirdUnBindListener thirdUnBindListener) {
        JsonObject platform = new JsonObject();
        platform.addProperty("weixin", "null");
        thirdUnBind(platform, thirdUnBindListener);
    }


    /**
     * 已登录，进行QQ解除绑定
     *
     * @param thirdUnBindListener 第三方解绑监听
     */
    public void unBindQQ(ThirdUnBindListener thirdUnBindListener) {
        JsonObject platform = new JsonObject();
        platform.addProperty("qq", "null");
        thirdUnBind(platform, thirdUnBindListener);
    }


}
