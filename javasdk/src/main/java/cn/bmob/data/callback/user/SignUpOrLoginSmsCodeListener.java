package cn.bmob.data.callback.user;

import cn.bmob.data.bean.table.BmobUser;
import cn.bmob.data.callback.base.BmobGetCallback;

public abstract class SignUpOrLoginSmsCodeListener<T extends BmobUser> extends BmobGetCallback<T> {

    /**
     * 获取成功，返回对象的信息
     * @param user
     */
    public abstract void onSuccess(T user);

}
