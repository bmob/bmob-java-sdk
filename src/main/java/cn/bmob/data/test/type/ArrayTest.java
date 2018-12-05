package cn.bmob.data.test.type;

import cn.bmob.data.Bmob;
import cn.bmob.data.callback.object.SaveListener;
import cn.bmob.data.exception.BmobException;
import cn.bmob.data.test.bean.TestConfig;
import cn.bmob.data.test.bean.TestObject;

import java.util.ArrayList;
import java.util.List;

public class ArrayTest {



    public static void main(String[] args){
        //TODO Application Entrance


        Bmob.getInstance().init(TestConfig.appId,TestConfig.apiKey);





        addArray();
    }

    private static void addArray() {


        List<String> strings = new ArrayList<>();

        for (int i=0;i<10;i++){
            strings.add("元素"+i);
        }



        TestObject testObject = new TestObject();
        testObject.setArray(strings);

        testObject.save(new SaveListener() {
            @Override
            public void onSuccess(String objectId, String createdAt) {

            }

            @Override
            public void onFailure(BmobException ex) {

            }
        });
    }
}
