package cn.bmob.data.callback;

public abstract class DeleteListener extends BmobCallback {

    /**
     * 删除，返回删除结果信息
     *
     * @param msg
     */
    public abstract void onSuccess(String msg);


}
