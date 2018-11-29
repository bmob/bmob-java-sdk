package cn.bmob.java;

import cn.bmob.data.Bmob;
import cn.bmob.data.config.BaseConfig;
import cn.bmob.data.api.BmobApiService;
import com.google.gson.JsonObject;
import org.junit.*;
import org.junit.runners.MethodSorters;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BmobTest {

    BmobApiService bmobApiService;
    Call<JsonObject> call;

    String mTableName = "StudentScore";
    private static String objectId = "x";


    @Before
    public void setUp() throws Exception {
        // 从系统变量中获取appid和apikey
        BaseConfig.appId = System.getenv().get("BmobAppId");
        BaseConfig.apiKey = System.getenv().get("BmobApiKey");
        bmobApiService = Bmob.getInstance().api();
    }

    @After
    public void tearDown() throws Exception {
        call.cancel();
    }


    @Test
    public void testAInsert(){
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("playerName","二麻子3");
//        jsonObject.addProperty("score", 59.5);

        JsonObject ret = commit(call);
        Assert.assertNotNull(ret);
        Assert.assertTrue(ret.has("objectId"));
        objectId = ret.get("objectId").getAsString();
    }

    @Test
    public void testBUpdate(){
        JsonObject object = new JsonObject();
        object.addProperty("score", 100);
        call = bmobApiService.update(mTableName, objectId, object);
        JsonObject ret = commit(call);
        Assert.assertNotNull(ret);
        Assert.assertTrue(ret.has("updatedAt"));
    }

    @Test
    public void testCDelete(){
        call = bmobApiService.deleteRow(mTableName, objectId);
        JsonObject ret = commit(call);
        Assert.assertNotNull(ret);
        Assert.assertTrue(ret.has("msg"));
        Assert.assertTrue("ok".equals(ret.get("msg").getAsString()));
    }

    private JsonObject commit(Call<JsonObject> call){
        try {
            Response<JsonObject> ret = call.execute();
            return ret.body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}