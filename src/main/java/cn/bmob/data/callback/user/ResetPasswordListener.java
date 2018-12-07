package cn.bmob.data.callback.user;

import cn.bmob.data.callback.base.BmobOkCallback;

public abstract class ResetPasswordListener extends BmobOkCallback {

    /**
     * 重置密码成功，返回结果信息
     *
     * @param msg
     */
    public abstract void onSuccess(String msg);


}
