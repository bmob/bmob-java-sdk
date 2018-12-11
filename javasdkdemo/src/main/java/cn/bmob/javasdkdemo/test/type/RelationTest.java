package cn.bmob.javasdkdemo.test.type;

import cn.bmob.data.bean.table.BmobUser;
import cn.bmob.data.bean.type.BmobRelation;
import cn.bmob.data.callback.object.SaveListener;
import cn.bmob.data.exception.BmobException;
import cn.bmob.javasdkdemo.test.bean.TestObject;

public class RelationTest {

    public static void main(String[] args) {
        //TODO Application Entrance

        addRelation();
    }

    /**
     * 添加多对多关系
     */
    private static void addRelation() {
        //TODO 设置一对多和多对多关系前，需确认该对象不为空，需先登录
        BmobUser bmobUser = BmobUser.getInstance().getCurrentUser(BmobUser.class);
        TestObject testObject = new TestObject();
        //多对多关系
        BmobRelation bmobRelation = new BmobRelation();
        bmobRelation.add(bmobUser);
        testObject.setRelation(bmobRelation);
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
