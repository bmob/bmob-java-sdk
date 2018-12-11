package cn.bmob.javasdkdemo.test.type;

import cn.bmob.data.Bmob;
import cn.bmob.data.bean.type.BmobFile;
import cn.bmob.data.callback.file.DeleteFileListener;
import cn.bmob.data.callback.file.UploadListener;
import cn.bmob.data.exception.BmobException;

import java.io.File;

import static cn.bmob.javasdkdemo.test.bean.TestConfig.apiKey;
import static cn.bmob.javasdkdemo.test.bean.TestConfig.appId;

public class FileTest {


    public static void main(String[] args) {
        //TODO Application Entrance


        Bmob.getInstance().init(appId, apiKey);


        uploadFile();
    }

    /**
     * 上传文件
     */
    private static void uploadFile() {
        File file = new File("/Users/zhangchaozhou/Desktop/F7C519D27247E8CE4EC6310472F5E47D.png");
        final BmobFile bmobFile = new BmobFile(file);
        bmobFile.uploadFile(new UploadListener() {
            @Override
            public void onSuccess() {
                System.out.println(bmobFile.getCdnName() + "-" + bmobFile.getFilename() + "-" + bmobFile.getUrl());
                deleteFile(bmobFile.getCdnName(),bmobFile.getUrl());
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println(ex.getMessage());
            }
        });
    }

    /**
     * 删除文件
     *
     * @param cdnName
     * @param url
     */
    private static void deleteFile(String cdnName, String url) {
        BmobFile bmobFile = new BmobFile();
        bmobFile.setCdnName(cdnName);
        bmobFile.setUrl(url);
        bmobFile.deleteFile(new DeleteFileListener() {
            @Override
            public void onSuccess(String msg) {
                System.out.println(msg);
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println(ex.getMessage());
            }
        });
    }
}
