package cn.bmob.data.bean;

import cn.bmob.data.Bmob;
import cn.bmob.data.callback.file.DeleteFileBatchListener;
import cn.bmob.data.callback.file.DeleteFileListener;
import cn.bmob.data.callback.file.UploadFileListener;
import cn.bmob.data.exception.BmobException;
import cn.bmob.data.utils.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

import java.io.File;

public class BmobFile {


    private String cdnName;

    private String url;

    private String fileName;


    private File file;

    public BmobFile(File file) {
        this.file = file;
    }


    public String getCdnName() {
        return cdnName;
    }

    public void setCdnName(String cdnName) {
        this.cdnName = cdnName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    /**
     *
     * 上传一个文件
     * @param uploadFileListener
     */
    public void uploadFile(UploadFileListener uploadFileListener) {
        if (file == null) {
            uploadFileListener.onFailure(new BmobException("Please create BmobFile first.", 9015));
            return;
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        Call<JsonObject> call = Bmob.getInstance().api().upload(file.getName(), requestBody);
        Utils.request(call, uploadFileListener);
    }


    /**
     * 删除一个文件
     * @param deleteFileListener
     */
    public void deleteFile(DeleteFileListener deleteFileListener) {
        System.out.println(Utils.getTargetUrl(getUrl()));
        Call<JsonObject> call = Bmob.getInstance().api().delete(getCdnName(), Utils.getTargetUrl(getUrl()));
        Utils.request(call, deleteFileListener);
    }



    /**
     * 删除一个文件
     * @param deleteFileBatchListener
     */
    public void deleteFileBatch(String cdnName,String[] urls,DeleteFileBatchListener deleteFileBatchListener) {
        System.out.println(Utils.getTargetUrl(getUrl()));
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonArray = new JsonArray();
        for (int i=0;i<urls.length;i++){
            jsonArray.add(Utils.getTargetUrl(urls[i]));
        }
        jsonObject.add(cdnName,jsonArray);
        Call<JsonObject> call = Bmob.getInstance().api().deleteBatch(jsonObject);
        Utils.request(call, deleteFileBatchListener);
    }
}
