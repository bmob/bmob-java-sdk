package cn.bmob.data.callback.object;

import cn.bmob.data.callback.base.BmobCallback;

public abstract class UpdateListener extends BmobCallback {
    /**
     * 更新成功，返回更新对象的时间
     * @param updatedAt
     */
    public abstract void onSuccess(String updatedAt);

}
