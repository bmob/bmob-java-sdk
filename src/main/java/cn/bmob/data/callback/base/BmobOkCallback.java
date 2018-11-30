package cn.bmob.data.callback.base;

public abstract class BmobOkCallback extends BmobCallback {

    /**
     * 操作完成，返回信息
     *
     * @param msg
     */
    public abstract void onSuccess(String msg);
}
