package cn.bmob.javasdkdemo.test.table;

import cn.bmob.data.Bmob;
import cn.bmob.data.bean.op.BmobQuery;
import cn.bmob.data.bean.table.BmobUser;
import cn.bmob.data.bean.type.BmobDate;
import cn.bmob.data.bean.type.BmobFile;
import cn.bmob.data.bean.type.BmobGeoPoint;
import cn.bmob.data.bean.type.BmobRelation;
import cn.bmob.data.callback.file.UploadListener;
import cn.bmob.data.callback.object.DeleteListener;
import cn.bmob.data.callback.object.GetListener;
import cn.bmob.data.callback.object.SaveListener;
import cn.bmob.data.callback.object.UpdateListener;
import cn.bmob.data.callback.user.LoginListener;
import cn.bmob.data.exception.BmobException;
import cn.bmob.javasdkdemo.test.bean.TestConfig;
import cn.bmob.javasdkdemo.test.bean.TestObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 示例：自定义数据类型的的增删改查操作
 */
public class ObjectTest {


    public static void main(String[] args) {
        //TODO Application Entrance
        Bmob.getInstance().init(TestConfig.appId, TestConfig.apiKey);
        login();
    }


    /**
     * 登录
     */
    private static void login() {
        BmobUser bmobUser = new BmobUser();
        bmobUser.setUsername("username");
        bmobUser.setPassword("password");
        bmobUser.login(new LoginListener<BmobUser>() {
            @Override
            public void onSuccess(BmobUser user) {
                System.out.println(user.getUsername());
                /**
                 * 登录后上传文件
                 */
                uploadFile();
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println(ex.getMessage());
            }
        });
    }

    /**
     * 上传文件
     */
    private static void uploadFile() {
        File file = new File("/Users/zhangchaozhou/Desktop/F7C519D27247E8CE4EC6310472F5E47D.png");
        final BmobFile bmobFile = new BmobFile(file);
        bmobFile.uploadFile(new UploadListener() {
            @Override
            public void onSuccess() {
                System.out.println(bmobFile.getCdnName() + "-" + bmobFile.getFilename() + "-" + bmobFile.getUrl());
                /**
                 * 上传文件后新增一条数据
                 */
                saveObject(bmobFile);
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println(ex.getMessage());
            }
        });
    }

    /**
     * 新增一条数据
     */
    private static void saveObject(BmobFile bmobFile) {
        TestObject testObject = new TestObject();
        //字符串
        testObject.setStr("michael");
        //布尔值
        // true false
        testObject.setBoo(false);
        //数字类型
        //64位 双精度 浮点
        testObject.setDou(64.2D);
        //64位
        testObject.setLon(System.currentTimeMillis());
        //32位 单精度 浮点
        testObject.setFlt(32.1F);
        //32位
        testObject.setInteger(32);
        //16位
        testObject.setSht(Short.valueOf("16"));
        //8位
        testObject.setByt(Byte.valueOf("8"));
        //（\u0000 - \uffff) —>(0 - 65535);
        testObject.setCht('t');
        //时间
        BmobDate bmobDate = new BmobDate(new Date());
        testObject.setDate(bmobDate);
        //位置
        //经度 纬度
        BmobGeoPoint bmobGeoPoint = new BmobGeoPoint(115.25, 39.26);
        testObject.setGeo(bmobGeoPoint);
        //数组
        List<String> strings = new ArrayList<>();
        strings.add("Item0");
        strings.add("Item1");
        strings.add("Item2");
        testObject.setArray(strings);
        //TODO 设置一对多和多对多关系前，需确认该对象不为空，需先登录
        BmobUser bmobUser = BmobUser.getInstance().getCurrentUser(BmobUser.class);
        //一对多关系
        testObject.setUser(bmobUser);
        //多对多关系
        BmobRelation bmobRelation = new BmobRelation();
        bmobRelation.add(bmobUser);
        testObject.setRelation(bmobRelation);
        //TODO 保存文件到表中前，需确认该文件已经上传到后台
        testObject.setFile(bmobFile);
        testObject.save(new SaveListener() {
            @Override
            public void onSuccess(String objectId, String createdAt) {
                System.out.println("save：" + objectId + "-" + createdAt);
                /**
                 * 新增一条数据后修改该条数据
                 */
//                update(objectId);
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
            }
        });
    }

    /**
     * 获取一条数据
     *
     * @param objectId
     */
    private static void get(final String objectId) {
        BmobQuery bmobQuery = new BmobQuery();
        bmobQuery.getObject(objectId, new GetListener<TestObject>() {
            @Override
            public void onSuccess(TestObject testObject) {
                System.out.println("get：" + testObject.getStr() + "-" + testObject.getInteger() + testObject.getObjectId() + "-" + testObject.getCreatedAt() + "-" + testObject.getUpdatedAt());
                /**
                 * 查询一条数据后，删除该条数据
                 */
                delete(objectId);
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
            }
        });
    }


    /**
     * 删除一条数据
     *
     * @param objectId
     */
    private static void delete(String objectId) {
        TestObject testObject = new TestObject();
        testObject.setObjectId(objectId);
        testObject.delete(new DeleteListener() {
            @Override
            public void onSuccess(String msg) {
                System.out.println("delete： " + msg);
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
            }
        });
    }


    /**
     * 更新一条数据
     *
     * @param objectId
     */
    private static void update(final String objectId) {
        TestObject testObject = new TestObject();
        testObject.setObjectId(objectId);
        testObject.setStr("jenny");
        testObject.update(new UpdateListener() {
            @Override
            public void onSuccess(String updatedAt) {
                System.out.println("update：" + "-" + updatedAt);
                /**
                 * 修改一条数据后查询该条数据
                 */
                get(objectId);
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
            }
        });
    }
}
