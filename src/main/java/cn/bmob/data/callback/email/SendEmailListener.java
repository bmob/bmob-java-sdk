package cn.bmob.data.callback.email;

import cn.bmob.data.callback.base.BmobOkCallback;

public abstract class SendEmailListener extends BmobOkCallback {


    /**
     * 发送邮件成功，返回结果
     *
     * @param msg
     */
    public abstract void onSuccess(String msg);


}
