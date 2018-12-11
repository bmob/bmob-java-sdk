package cn.bmob.data.callback.object;

import cn.bmob.data.callback.base.BmobCallback;


/**
 * 结果计数，此处的T不能去掉
 * @param <T>
 */
public abstract class CountListener<T> extends BmobCallback {


    /**
     * 计数，返回计数结果
     *
     * @param count
     */
    public abstract void onSuccess(Integer count);


}
