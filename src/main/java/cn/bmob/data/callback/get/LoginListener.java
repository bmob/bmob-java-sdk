package cn.bmob.data.callback.get;

public abstract class LoginListener<T> extends BmobGetCallback<T> {


    /**
     * 获取成功，返回对象的信息
     * @param object
     */
    public abstract void onSuccess(T object);


}
