package cn.bmob.data.test.type;

import cn.bmob.data.Bmob;
import cn.bmob.data.bean.type.BmobFile;
import cn.bmob.data.callback.file.DeleteFileListener;
import cn.bmob.data.callback.file.UploadFileListener;
import cn.bmob.data.exception.BmobException;

import java.io.File;

import static cn.bmob.data.test.bean.TestConfig.apiKey;
import static cn.bmob.data.test.bean.TestConfig.appId;

public class FileTest {


    public static void main(String[] args) {
        //TODO Application Entrance


        Bmob.getInstance().init(appId, apiKey);

        File file = new File("/Users/zhangchaozhou/Desktop/F7C519D27247E8CE4EC6310472F5E47D.png");
        final BmobFile bmobFile = new BmobFile(file);
        bmobFile.uploadFile(new UploadFileListener() {
            @Override
            public void onSuccess(String cdnName, String fileName, String fileUrl) {
                System.out.println(fileName + "-" + fileUrl);
                bmobFile.setCdnName(cdnName);
                bmobFile.setUrl(fileUrl);
                bmobFile.setFileName(fileName);
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

            @Override
            public void onFailure(BmobException ex) {

                System.err.println(ex.getMessage());
            }
        });


    }
}
