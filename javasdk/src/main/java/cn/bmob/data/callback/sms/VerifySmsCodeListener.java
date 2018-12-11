package cn.bmob.data.callback.sms;

import cn.bmob.data.callback.base.BmobOkCallback;

public abstract class VerifySmsCodeListener extends BmobOkCallback {
    /**
     * 验证成功，返回结果信息
     *
     * @param msg
     */
    public abstract void onSuccess(String msg);
}
