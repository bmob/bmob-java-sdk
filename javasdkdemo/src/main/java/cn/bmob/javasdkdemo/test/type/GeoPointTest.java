package cn.bmob.javasdkdemo.test.type;

import cn.bmob.data.Bmob;
import cn.bmob.data.bean.type.BmobGeoPoint;
import cn.bmob.data.callback.object.SaveListener;
import cn.bmob.data.exception.BmobException;
import cn.bmob.javasdkdemo.test.bean.TestObject;

import static cn.bmob.javasdkdemo.test.bean.TestConfig.apiKey;
import static cn.bmob.javasdkdemo.test.bean.TestConfig.appId;

public class GeoPointTest {


    public static void main(String[] args) {
        //TODO Application Entrance


        Bmob.getInstance().init(appId, apiKey);


        saveGeoPoint();


    }


    /**
     * 保存地理位置信息到表中
     */
    private static void saveGeoPoint() {
        TestObject testObject = new TestObject();
        BmobGeoPoint bmobGeoPoint = new BmobGeoPoint();
        bmobGeoPoint.setLatitude(12.35345);
        bmobGeoPoint.setLongitude(13.23232);
        testObject.setGeo(bmobGeoPoint);
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
