package cn.bmob.data.callback.object;

import cn.bmob.data.callback.base.BmobCallback;

public abstract class GetsStringListener extends BmobCallback {

    /**
     * 获取成功，返回对象列表
     *
     * @param array
     */
    public abstract void onSuccess(String array);
}
