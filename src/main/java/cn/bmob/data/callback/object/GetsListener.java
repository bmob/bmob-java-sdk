package cn.bmob.data.callback.object;

import cn.bmob.data.callback.base.BmobCallback;

import java.util.List;

public abstract class GetsListener<T> extends BmobCallback {

    /**
     * 获取成功，返回对象列表
     *
     * @param array
     */
    public abstract void onSuccess(List<T> array);
}
