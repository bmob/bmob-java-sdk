package cn.bmob.data.callback.file;

import cn.bmob.data.callback.base.BmobCallback;

public abstract class UploadFileListener extends BmobCallback {

    /**
     * 上传文件，返回文件名和文件地址
     *
     * @param fileName
     * @param fileUrl
     * @param cdnName
     */
    public abstract void onSuccess(String cdnName,String fileName,String fileUrl);
}
