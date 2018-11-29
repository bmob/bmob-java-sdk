package cn.bmob.data.callback;

import cn.bmob.data.exception.BmobException;

public abstract class BmobCallback {

    /**
     * 保存失败，返回异常信息
     * @param ex
     */
    public abstract void onFailure(BmobException ex);


}