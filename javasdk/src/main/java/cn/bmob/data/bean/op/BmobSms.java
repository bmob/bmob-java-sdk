package cn.bmob.data.bean.op;

import cn.bmob.data.Bmob;
import cn.bmob.data.callback.sms.SendSmsCodeListener;
import cn.bmob.data.callback.sms.VerifySmsCodeListener;
import cn.bmob.data.exception.BmobException;
import cn.bmob.data.utils.Utils;
import com.google.gson.JsonObject;
import retrofit2.Call;

public class BmobSms {


    private String mobilePhoneNumber;

    private String template;


    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }


    public BmobSms() {
    }


    /**
     * 发送自定义模板短信
     *
     * @param sendSmsCodeListener 发送短信监听
     */
    public void sendSmsCode(SendSmsCodeListener sendSmsCodeListener) {
        if (Utils.isStringEmpty(mobilePhoneNumber)) {
            sendSmsCodeListener.onFailure(new BmobException("Please setMobilePhoneNumber first.", 9015));
            return;
        }

        Call<JsonObject> call = Bmob.getInstance().api().sendSmsCode(this);
        Utils.request(call, sendSmsCodeListener);
    }


    /**
     * 验证短信验证码
     *
     * @param verifySmsCodeListener 验证短信监听
     */
    public void verifySmsCode(String smsCode, VerifySmsCodeListener verifySmsCodeListener) {
        if (Utils.isStringEmpty(smsCode)) {
            verifySmsCodeListener.onFailure(new BmobException("Please input smsCode first.", 9015));
            return;
        }
        Call<JsonObject> call = Bmob.getInstance().api().verifySmsCode(smsCode, this);
        Utils.request(call, verifySmsCodeListener);
    }


}
