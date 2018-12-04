package cn.bmob.data.callback.base;

import cn.bmob.data.bean.BmobObject;

public abstract class BmobGetCallback<T extends BmobObject> extends BmobCallback {

    /**
     * 获取成功，返回对象的信息
     * @param object
     */
    public abstract void onSuccess(T object);
}
