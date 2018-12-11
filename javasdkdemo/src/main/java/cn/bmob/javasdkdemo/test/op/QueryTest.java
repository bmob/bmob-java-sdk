package cn.bmob.javasdkdemo.test.op;

import cn.bmob.data.Bmob;
import cn.bmob.data.bean.op.BmobQuery;
import cn.bmob.data.callback.object.CountListener;
import cn.bmob.data.callback.object.GetListener;
import cn.bmob.data.callback.object.GetsListener;
import cn.bmob.data.exception.BmobException;
import cn.bmob.javasdkdemo.test.bean.TestObject;

import java.util.List;

import static cn.bmob.javasdkdemo.test.bean.TestConfig.apiKey;
import static cn.bmob.javasdkdemo.test.bean.TestConfig.appId;

public class QueryTest {



    public static void main(String[] args) {
        //TODO Application Entrance
        Bmob.getInstance().init(appId, apiKey);


    }


    /**
     * 查询结果计数
     */
    private static void getCount() {
        BmobQuery bmobQuery = new BmobQuery();
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

    /**
     * 根据查询条件查询多个数据
     */
    private static void getObjects() {
        BmobQuery bmobQuery = new BmobQuery();
        //TODO 次数增加查询条件
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

    /**
     * 查询单个数据
     * @param objectId
     */
    private static void getObject(String objectId) {
        BmobQuery bmobQuery = new BmobQuery();
        bmobQuery.getObject(objectId, new GetListener<TestObject>() {
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
