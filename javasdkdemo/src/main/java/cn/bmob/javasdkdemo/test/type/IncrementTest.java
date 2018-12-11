package cn.bmob.javasdkdemo.test.type;

import cn.bmob.data.Bmob;
import cn.bmob.data.callback.object.SaveListener;
import cn.bmob.data.callback.object.UpdateListener;
import cn.bmob.data.exception.BmobException;
import cn.bmob.javasdkdemo.test.bean.TestConfig;
import cn.bmob.javasdkdemo.test.bean.TestObject;


/**
 * 示例：自增字段的值，若要自减，则取负值
 */
public class IncrementTest {


    public static void main(String[] args) {
        //TODO Application Entrance
        Bmob.getInstance().init(TestConfig.appId, TestConfig.apiKey);
        saveInteger();
    }


    /**
     * 保存
     */
    private static void saveInteger() {
        TestObject testObject = new TestObject();
        testObject.setInteger(1);
        testObject.save(new SaveListener() {
            @Override
            public void onSuccess(String objectId, String createdAt) {
                System.out.println(objectId + "-" + createdAt);
                /**
                 * 保存之后执行自增修改
                 */
                increment(objectId);
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println(ex.getMessage());
            }
        });

    }

    /**
     * 自增
     *
     * @param objectId
     */
    private static void increment(String objectId) {
        TestObject testObject = new TestObject();
        /**
         * 自增1
         */
        testObject.increment("integer");
        /**
         * 自增n
         */
        testObject.increment("integer", -10);
        testObject.setObjectId(objectId);
        testObject.update(new UpdateListener() {
            @Override
            public void onSuccess(String updatedAt) {
                System.out.println(updatedAt);
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println(ex.getMessage());
            }
        });
    }
}
