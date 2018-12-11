package cn.bmob.data.callback.user;

import cn.bmob.data.callback.base.BmobOkCallback;

public abstract class CheckUserSessionListener extends BmobOkCallback {


    /**
     * 删除，返回删除结果信息
     *
     * @param msg
     */
    public abstract void onSuccess(String msg);
}
