package cn.bmob.data.test;

import cn.bmob.data.Bmob;
import cn.bmob.data.bean.BmobFile;
import cn.bmob.data.callback.file.DeleteFileListener;
import cn.bmob.data.callback.file.UploadFileListener;
import cn.bmob.data.exception.BmobException;

import java.io.File;

public class FileTest {


    public static String appId = "12784168944a56ae41c4575686b7b332";
    public static String apiKey = "9e8ffb8e0945092d1a6b3562741ae564";

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


//        File file2 = new File("/Users/zhangchaozhou/Documents/Android/BmobDocs/README.md");
//
//        BmobFile bmobFile2 = new BmobFile(file2);
//        bmobFile2.uploadFile(new UploadFileListener() {
//            @Override
//            public void onSuccess(String fileName, String fileUrl) {
//                System.out.println(fileName + "-" + fileUrl);
//            }
//
//            @Override
//            public void onFailure(BmobException ex) {
//
//                System.err.println(ex.getMessage());
//            }
//        });




        
    }
}
