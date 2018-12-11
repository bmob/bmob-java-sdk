package cn.bmob.javasdkdemo.test.type;

import cn.bmob.data.Bmob;
import cn.bmob.data.bean.type.BmobDate;
import cn.bmob.data.callback.object.SaveListener;
import cn.bmob.data.exception.BmobException;
import cn.bmob.javasdkdemo.test.bean.TestConfig;
import cn.bmob.javasdkdemo.test.bean.TestObject;

import java.util.Date;

public class DateTest {


    public static void main(String[] args) {
        //TODO Application Entrance


        Bmob.getInstance().init(TestConfig.appId, TestConfig.apiKey);


        saveDate();
    }


    /**
     * 保存date类型到表中
     */
    private static void saveDate() {
        TestObject testObject = new TestObject();
        BmobDate bmobDate = new BmobDate(new Date());
        testObject.setDate(bmobDate);
        testObject.save(new SaveListener() {
            @Override
            public void onSuccess(String objectId, String createdAt) {
                System.out.println(objectId + "-" + createdAt);
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println(ex.getMessage());
            }
        });
    }


}
