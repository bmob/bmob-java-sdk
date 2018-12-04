package cn.bmob.data.test;

import cn.bmob.data.Bmob;
import cn.bmob.data.bean.BmobQuery;
import cn.bmob.data.callback.object.CountListener;
import cn.bmob.data.callback.object.GetListener;
import cn.bmob.data.callback.object.GetsListener;
import cn.bmob.data.exception.BmobException;

import java.util.List;

public class QueryTest {

    public static String appId = "12784168944a56ae41c4575686b7b332";
    public static String apiKey = "9e8ffb8e0945092d1a6b3562741ae564";


    public static void main(String[] args) {
        //TODO Application Entrance
        Bmob.getInstance().init(appId, apiKey);

//        getObject();
        getObjects();

//        getCount();
    }

    private static void getCount() {
        BmobQuery bmobQuery = new BmobQuery();
        bmobQuery.setSum("score");
        bmobQuery.getCount(new CountListener<GameScore>() {
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
        bmobQuery.addWhereNotEqualTo("score", 10);
        bmobQuery.setKeys("playerName");
        bmobQuery.setSum("score");
        bmobQuery.getObjects(new GetsListener<GameScore>() {
            @Override
            public void onSuccess(List<GameScore> array) {
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
        bmobQuery.setKeys("playerName");
        bmobQuery.getObject("f926a9b287", new GetListener<GameScore>() {
            @Override
            public void onSuccess(GameScore object) {
                System.out.println(object.getPlayerName());
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println(ex.getMessage());
            }
        });
    }
}
