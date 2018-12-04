package cn.bmob.data.bean;

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



    private List<T> results;


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
}
