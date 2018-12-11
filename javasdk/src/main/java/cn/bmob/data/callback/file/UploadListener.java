package cn.bmob.data.callback.file;

import cn.bmob.data.callback.base.BmobCallback;

public abstract class UploadListener extends BmobCallback {
    /**
     * 上传文件成功
     */
    public abstract void onSuccess();
}
