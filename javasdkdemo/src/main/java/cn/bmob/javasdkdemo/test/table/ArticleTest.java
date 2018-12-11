package cn.bmob.javasdkdemo.test.table;

import cn.bmob.data.Bmob;
import cn.bmob.data.bean.op.BmobQuery;
import cn.bmob.data.bean.table.BmobArticle;
import cn.bmob.data.callback.object.GetsListener;
import cn.bmob.data.exception.BmobException;
import cn.bmob.javasdkdemo.test.bean.TestConfig;

import java.util.List;

public class ArticleTest {

    public static void main(String[] args){
        //TODO Application Entrance
        Bmob.getInstance().init(TestConfig.appId,TestConfig.apiKey);



        getArticles();
    }

    /**
     * 获取图文消息
     */
    private static void getArticles() {
        BmobQuery bmobQuery = new BmobQuery();
        bmobQuery.getObjects(new GetsListener<BmobArticle>() {
            @Override
            public void onSuccess(List<BmobArticle> array) {
                System.out.println("size "+array.size());
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println(ex.getMessage());
            }
        });
    }
}
