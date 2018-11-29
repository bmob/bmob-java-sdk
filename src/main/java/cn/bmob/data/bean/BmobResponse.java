package cn.bmob.data.bean;

public class BmobResponse {

    /**
     * createdAt : 2018-11-29 11:42:55
     * objectId : 32e462b07c
     */
    /**
     * 数据创建时间
     */
    private String createdAt;


    /**
     * 数据更新时间
     */
    private String updatedAt;


    /**
     * 数据唯一标志
     */
    private String objectId;
    /**
     * code : 105
     * error : invalid field name: _c_.
     */
    /**
     * 错误码
     */
    private int code;
    /**
     * 错误信息
     */
    private String error;


    /**
     * 成功信息
     */
    private String msg;


    /**
     * 登录令牌
     */
    private String sessionToken;


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

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
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
}
