package cn.bmob.javasdkdemo.test.type;

import cn.bmob.data.bean.table.BmobUser;
import cn.bmob.data.callback.object.SaveListener;
import cn.bmob.data.exception.BmobException;
import cn.bmob.javasdkdemo.test.bean.TestObject;

public class PointerTest {


    public static void main(String[] args){
        //TODO Application Entrance




        addPointer();
    }


    /**
     * 保存pointer类型
     */
    private static void addPointer() {
        //TODO 设置一对多和多对多关系前，需确认该对象不为空，需先登录
        BmobUser bmobUser = BmobUser.getInstance().getCurrentUser(BmobUser.class);
        //一对多关系
        TestObject testObject = new TestObject();
        testObject.setUser(bmobUser);
        testObject.save(new SaveListener() {
            @Override
            public void onSuccess(String objectId, String createdAt) {
                System.out.println("save：" + objectId + "-" + createdAt);
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
            }
        });
    }
}
