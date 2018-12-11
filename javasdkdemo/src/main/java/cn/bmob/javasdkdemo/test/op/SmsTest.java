package cn.bmob.javasdkdemo.test.op;

import cn.bmob.data.Bmob;
import cn.bmob.data.bean.op.BmobSms;
import cn.bmob.data.callback.sms.SendSmsCodeListener;
import cn.bmob.data.callback.sms.VerifySmsCodeListener;
import cn.bmob.data.exception.BmobException;

import static cn.bmob.javasdkdemo.test.bean.TestConfig.apiKey;
import static cn.bmob.javasdkdemo.test.bean.TestConfig.appId;

public class SmsTest {


    public static void main(String[] args) {
        //TODO Application Entrance
        Bmob.getInstance().init(appId, apiKey);
        sendSms("", "");
        verifySmsCode("", "");
    }


    /**
     * 验证短信验证码
     *
     * @param phoneNumber
     * @param smsCode
     */
    private static void verifySmsCode(String phoneNumber, String smsCode) {
        BmobSms bmobSms = new BmobSms();
        bmobSms.setMobilePhoneNumber(phoneNumber);
        bmobSms.verifySmsCode(smsCode, new VerifySmsCodeListener() {
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

    /**
     * 发送验证短信，如果使用默认模板，则设置template为空字符串或不设置
     *
     * @param phoneNumber
     * @param template
     */
    private static void sendSms(String phoneNumber, String template) {
        BmobSms bmobSms = new BmobSms();
        bmobSms.setMobilePhoneNumber(phoneNumber);
        bmobSms.setTemplate(template);
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





}
