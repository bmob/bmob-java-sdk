package cn.bmob.data.test.table;

import cn.bmob.data.Bmob;
import cn.bmob.data.bean.op.BmobQuery;
import cn.bmob.data.bean.table.BmobArticle;
import cn.bmob.data.callback.object.GetsListener;
import cn.bmob.data.callback.object.SaveListener;
import cn.bmob.data.exception.BmobException;
import cn.bmob.data.test.bean.TestConfig;

import java.util.List;

public class ArticleTest {

    public static void main(String[] args){
        //TODO Application Entrance
        Bmob.getInstance().init(TestConfig.appId,TestConfig.apiKey);



        getArticle();
    }

    private static void getArticle() {




        BmobArticle article  =new BmobArticle();
        article.setContent("test");
        article.save(new SaveListener() {
            @Override
            public void onSuccess(String objectId, String createdAt) {

            }

            @Override
            public void onFailure(BmobException ex) {

            }
        });

        BmobQuery bmobQuery = new BmobQuery();
        bmobQuery.getObjects(new GetsListener<BmobArticle>() {
            @Override
            public void onSuccess(List<BmobArticle> array) {

            }

            @Override
            public void onFailure(BmobException ex) {

            }
        });




    }
}
