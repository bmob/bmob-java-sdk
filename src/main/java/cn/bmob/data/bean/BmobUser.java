package cn.bmob.data.bean;

import cn.bmob.data.Bmob;
import cn.bmob.data.callback.get.LoginListener;
import cn.bmob.data.callback.save.SignUpListener;
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
    public <T> void login(final LoginListener<T> loginListener) {
        Call<JsonObject> call = Bmob.getInstance().api().login(username, password);
        request(call, loginListener);
    }


    /**
     * 注册
     *
     * @param signUpListener
     */
    public void signUp(final SignUpListener signUpListener) {
        Call<JsonObject> call = Bmob.getInstance().api().signUp(this);
        request(call, signUpListener);
    }


}
