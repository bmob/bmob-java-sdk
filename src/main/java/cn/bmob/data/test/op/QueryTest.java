package cn.bmob.data.test.op;

import cn.bmob.data.Bmob;
import cn.bmob.data.bean.op.BmobQuery;
import cn.bmob.data.callback.object.CountListener;
import cn.bmob.data.callback.object.GetListener;
import cn.bmob.data.callback.object.GetsListener;
import cn.bmob.data.exception.BmobException;
import cn.bmob.data.test.bean.TestObject;

import java.util.List;

import static cn.bmob.data.test.bean.TestConfig.apiKey;
import static cn.bmob.data.test.bean.TestConfig.appId;

public class QueryTest {



    public static void main(String[] args) {
        //TODO Application Entrance
        Bmob.getInstance().init(appId, apiKey);

//        getObject();
        getObjects();

//        getCount();
    }

    private static void getCount() {
        BmobQuery bmobQuery = new BmobQuery();
        bmobQuery.setSum("integer");
        bmobQuery.getCount(new CountListener<TestObject>() {
            @Override
            public void onSuccess(Integer count) {
                System.out.println("count "+count);
            }


            @Override
            public void onFailure(BmobException ex) {
                System.err.println(ex.getMessage());
            }
        });
    }

    private static void getObjects() {
        BmobQuery bmobQuery = new BmobQuery();
        bmobQuery.addWhereNotEqualTo("integer", 10);
        bmobQuery.setKeys("str");
        bmobQuery.setSum("integer");
        bmobQuery.getObjects(new GetsListener<TestObject>() {
            @Override
            public void onSuccess(List<TestObject> array) {
                System.out.println("size "+array.size());
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println(ex.getMessage());
            }
        });
    }

    private static void getObject() {
        BmobQuery bmobQuery = new BmobQuery();
        bmobQuery.setKeys("str");
        bmobQuery.getObject("f926a9b287", new GetListener<TestObject>() {
            @Override
            public void onSuccess(TestObject object) {
                System.out.println(object.getStr());
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println(ex.getMessage());
            }
        });
    }
}
