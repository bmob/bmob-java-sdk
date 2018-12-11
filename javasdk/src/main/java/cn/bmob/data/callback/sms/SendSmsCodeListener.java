package cn.bmob.data.callback.sms;

import cn.bmob.data.callback.base.BmobCallback;

public abstract class SendSmsCodeListener extends BmobCallback {
    /**
     * 发送成功，返回短信ID
     * @param smsId
     */
    public abstract void onSuccess(String smsId);
}
