package cn.bmob.data.callback.user;

import cn.bmob.data.callback.base.BmobSaveCallback;

public abstract class SignUpListener extends BmobSaveCallback {


    /**
     * 注册成功，返回注册对象的唯一标志和注册时间
     * @param objectId
     * @param createdAt
     */
    public abstract void onSuccess(String objectId,String createdAt);


}
