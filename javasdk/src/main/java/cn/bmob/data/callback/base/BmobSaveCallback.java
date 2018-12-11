package cn.bmob.data.callback.base;

public abstract class BmobSaveCallback extends BmobCallback {
    /**
     * 注册成功，返回注册对象的唯一标志和注册时间
     *
     * @param objectId
     * @param createdAt
     */
    public abstract void onSuccess(String objectId, String createdAt);
}
