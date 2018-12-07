package cn.bmob.data.callback.user;

import cn.bmob.data.callback.base.BmobCallback;

public abstract class ThirdBindListener extends BmobCallback {

    /**
     * 绑定成功，返回绑定成功的时间
     * @param updatedAt
     */
    public abstract void onSuccess(String updatedAt);

}
