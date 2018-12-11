package cn.bmob.javasdkdemo.test;

import cn.bmob.data.Bmob;
import cn.bmob.data.bean.op.BmobQuery;
import cn.bmob.data.bean.table.BmobUser;
import cn.bmob.data.callback.object.GetsListener;
import cn.bmob.data.config.BmobConfig;
import cn.bmob.data.exception.BmobException;
import cn.bmob.javasdkdemo.test.bean.TestConfig;

import java.util.List;

public class SynchronousAsynchronousTest {

    public static void main(final String[] args){
        //TODO Application Entrance

        Bmob.getInstance().init(TestConfig.appId,TestConfig.apiKey);

        BmobConfig.setSynchronous(false);

        System.out.println("---------------1----------------");
        BmobQuery bmobQuery = new BmobQuery();
        bmobQuery.getObjects(new GetsListener<BmobUser>() {
            @Override
            public void onSuccess(List<BmobUser> array) {
                System.out.println("size "+array.size());
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println("ex "+ex.getMessage());
            }
        });
        System.out.println("---------------2----------------");
        BmobQuery bmobQuery1 = new BmobQuery();
        bmobQuery1.getObjects(new GetsListener<BmobUser>() {
            @Override
            public void onSuccess(List<BmobUser> array) {
                System.out.println("size1 "+array.size());
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println("ex1 "+ex.getMessage());
            }
        });
        System.out.println("---------------3----------------");




    }
}
