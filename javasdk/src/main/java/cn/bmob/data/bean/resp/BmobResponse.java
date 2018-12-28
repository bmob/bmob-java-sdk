package cn.bmob.data.bean.resp;

import java.util.List;

public class BmobResponse<T> {


    /**
     * 错误码
     */
    private int code;
    /**
     * 错误信息
     */
    private String error;
    
    /**
     * 数据唯一标志
     */
    private String objectId;

    /**
     * 数据创建时间
     */
    private String createdAt;


    /**
     * 数据更新时间
     */
    private String updatedAt;


    /**
     * 成功信息
     */
    private String msg;


    /**
     * 登录令牌
     */
    private String sessionToken;


    /**
     * 短信ID
     */
    private String smsId;


    /**
     *
     */
    private List<T> results;


    /**
     *
     */
    private int count;


    /**
     * CDN平台
     */
    private String cdn;


    /**
     * 文件名称
     */
    private String filename;

    /**
     * 文件地址
     */
    private String url;


    /**
     * 删除失败的CDN和对应地址列表
     */
    private String fail;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getSmsId() {
        return smsId;
    }

    public void setSmsId(String smsId) {
        this.smsId = smsId;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public String getCdn() {
        return cdn;
    }

    public void setCdn(String cdn) {
        this.cdn = cdn;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getFail() {
        return fail;
    }

    public void setFail(String fail) {
        this.fail = fail;
    }



}
