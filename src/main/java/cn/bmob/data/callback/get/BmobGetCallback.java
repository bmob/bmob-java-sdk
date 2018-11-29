package cn.bmob.data.callback.get;

import cn.bmob.data.callback.BmobCallback;

public abstract class BmobGetCallback<T> extends BmobCallback {

    /**
     * 获取成功，返回对象的信息
     * @param object
     */
    public abstract void onSuccess(T object);
}
