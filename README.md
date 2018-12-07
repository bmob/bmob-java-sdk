# bmob-java-sdk
Bmob Java Rest API SDK

# 基本数据类型

BmobObject

|属性|含义|
|----|----|
|objectId|数据唯一标志|
|createdAt|数据创建时间|
|updatedAt|数据更新时间|
|ACL|数据访问权限|

# 自定义数据类型

1、继承自BmobObject

2、可用的字段数据类型

|控制台|类型|含义|
|----|----|----|
|String|String|字符串|
|Number|Double、Long、Integer、Float、Short、Byte、Character|数字|
|Boolean|Boolean|布尔值|
|Date|BmobDate|时间|
|File|BmobFile|文件|
|GeoPoint|BmobGeoPoint|位置|
|Pointer|继承BmobObject的类型|一对多关系|
|Relation|BmobRelation|多对多关系|
|Array|List|数组|

# 特殊数据类型

1、继承自BmobObject

|类型|解释|功能|
|-----|-----|----|
|BmobUser|对应控制台_User用户表|可以实现用户的注册、登录、短信验证、邮箱验证等功能。|
|BmobRole|对应控制台_Role角色表|可以配合ACL进行权限访问控制和角色管理。|
|BmobArticle|对应控制台_Article图文消息表|可以进行静态网页加载。|

# 自定义数据类型操作

## 自定义实体类
```
@Data
public class TestObject extends BmobObject {
    /**
     * String类型
     */
    private String str;
    /**
     * Boolean类型
     */
    private Boolean boo;
    /**
     * Integer类型
     */
    private Integer integer;
    /**
     * Long类型
     */
    private Long lon;
    /**
     * Double类型
     */
    private Double dou;
    /**
     * Float类型
     */
    private Float flt;
    /**
     * Short类型
     */
    private Short sht;
    /**
     * Byte类型
     */
    private Byte byt;
    /**
     * Character类型
     */
    private Character cht;
    /**
     * 地理位置类型
     */
    private BmobGeoPoint geo;
    /**
     * 文件类型
     */
    private BmobFile file;
    /**
     */
    private BmobDate date;
    /**
     * 数组类型
     */
    private List<String> array;
    /**
     * Pointer 一对多关系
     */
    private BmobUser user;
    /**
     * Relation 多对多关系类型
     */
    private BmobRelation relation;
}
```
## 新增一条数据


```
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
        //TODO 设置一对多和多对多关系前，需确认该对象不为空
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
            }

            @Override
            public void onFailure(BmobException ex) {
                System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
            }
        });
    }
```

## 更新一条数据
```
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
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
        }
    });
}
```
## 获取一条数据
```
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
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
        }
    });
}
```
## 删除一条数据

```
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

```


# 用户表

# 角色表

# 图文表



