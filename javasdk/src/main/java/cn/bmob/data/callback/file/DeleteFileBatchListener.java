package cn.bmob.data.callback.file;

import cn.bmob.data.callback.base.BmobOkCallback;

public abstract class DeleteFileBatchListener extends BmobOkCallback {


    /**
     * 删除，返回删除结果信息
     *
     * @param msg
     */
    public abstract void onSuccess(String msg);


}
