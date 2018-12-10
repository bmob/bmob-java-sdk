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
     * 时间类型
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

_User

|属性|说明|
|----|----|
|username|用户名，可以是邮箱、手机号码、第三方平台的用户唯一标志|
|password|用户密码|
|email|用户邮箱|
|emailVerified|用户邮箱认证状态|
|mobilePhoneNumber|用户手机号码|
|mobilePhoneNumberVerified|用户手机号码认证状态|


## 自定义实体类
```
@Data
public class TestUser extends BmobUser {

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private Integer gender;
}
```

## 注册
```
/**
 * 注册
 */
private static void signUp() {
    final String username = System.currentTimeMillis() + "";
    final String password = System.currentTimeMillis() + "";
    TestUser testUser = new TestUser();
    testUser.setNickname("用户昵称");
    testUser.setAge(20);
    testUser.setGender(1);
    testUser.setUsername(username);
    testUser.setPassword(password);
    testUser.signUp(new SignUpListener() {
        @Override
        public void onSuccess(String objectId, String createdAt) {
            System.out.println("sign up " + objectId + "-" + createdAt);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
        }
    });
}
```

## 登录
```
/**
 * 登录
 *
 * @param username
 * @param password
 */
private static void login(String username, String password) {
    TestUser testUser= new TestUser();
    testUser.setUsername(username);
    testUser.setPassword(password);
    testUser.login(new LoginListener<TestUser>() {
        public void onSuccess(TestUser user) {
            System.out.println("login " + user.getUsername() + user.getSessionToken());
        }


        @Override
        public void onFailure(BmobException ex) {
            System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
        }
    });
}
```

## 手机短信验证码注册/登录
1、发送短信验证码，见文档短信验证的发送短信验证码。
2、验证短信验证码后注册/登录。
```
/**
 * 手机短信验证码注册/登录
 *
 * @param phoneNumber
 * @param smsCode
 */
private static void signUpOrLoginSmsCode(String phoneNumber, String smsCode) {
    BmobUser bmobUser = new BmobUser();
    bmobUser.setMobilePhoneNumber(phoneNumber);
    bmobUser.setSmsCode(smsCode);
    bmobUser.signUpOrLoginSmsCode(new SignUpOrLoginSmsCodeListener<BmobUser>() {
        @Override
        public void onSuccess(BmobUser user) {
            System.out.println("sign up " + user.getUsername() + "-" + user.getObjectId());
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
        }
    });
}
```

## 获取用户信息

```
/**
 * 获取用户信息
 * @param objectId
 */
private static void getUserInfo(String objectId) {
    BmobUser.getUserInfo(objectId, new GetListener<TestUser>() {
        @Override
        public void onSuccess(TestUser user) {
            System.out.println("get user info " + user.getUsername() + "-" + user.getObjectId());
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
        }
    });
}
```
或
```
/**
 * 查询用户信息
 * @param objectId
 */
private static void queryUserInfo(String objectId){
    BmobQuery bmobQuery = new BmobQuery();
    bmobQuery.getObject(objectId, new GetListener<TestUser>() {
        @Override
        public void onSuccess(TestUser user) {
            System.out.println("query user info " + user.getUsername() + "-" + user.getObjectId());
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println("ex：" + ex.getCode() + "-" + ex.getMessage());
        }
    });
}
```
## 登录是否失效
```
/**
 * 查询登录是否失效
 */
private static void checkUserSession(){
    TestUser testUser = BmobUser.getInstance().getCurrentUser(TestUser.class);
    testUser.checkUserSession(new CheckUserSessionListener() {
        @Override
        public void onSuccess(String msg) {
            System.out.println(msg);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });
}
```
## 修改用户信息

1、修改用户信息，需确保objectId不为空，确保用户已经登录。
2、在更新用户信息时，若用户邮箱有变更并且在管理后台打开了邮箱验证选项，Bmob云后端会自动发送一封验证邮件给用户。

```
/**
 * 修改用户信息，需确保objectId不为空，确保用户已经登录。
 * 在更新用户信息时，若用户邮箱有变更并且在管理后台打开了邮箱验证选项，Bmob云后端会自动发送一封验证邮件给用户。
 */
private static void updateUserInfo() {
    if (!BmobUser.getInstance().isLogin()) {
        System.err.println("尚未登录");
        return;
    }
    TestUser testUser = BmobUser.getInstance().getCurrentUser(TestUser.class);
    testUser.setNickname("修改用户昵称");
    testUser.setGender(2);
    testUser.setAge(30);
    testUser.updateUserInfo(new UpdateListener() {
        @Override
        public void onSuccess(String updatedAt) {
            System.out.println(updatedAt);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });
}
```

## 删除用户

1、删除用户，需确保objectId不为空，确保用户已经登录。

```
/**
 * 删除用户，需确保objectId不为空，确保用户已经登录。
 */
private static void deleteUser(){
    if (!BmobUser.getInstance().isLogin()) {
        System.err.println("尚未登录");
        return;
    }
    TestUser testUser = BmobUser.getInstance().getCurrentUser(TestUser.class);
    testUser.delete(new DeleteListener() {
        @Override
        public void onSuccess(String msg) {
            System.out.println(msg);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });
}
```

## 获取多个用户信息
```
/**
 * 获取多个用户信息
 */
private static void getUsers(){
    BmobQuery bmobQuery = new BmobQuery();
    bmobQuery.getObjects(new GetsListener<BmobUser>() {
        @Override
        public void onSuccess(List<BmobUser> array) {
            System.out.println("get users "+array.size());
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });
}
```
## 用户邮箱验证

```
/**
 * 发送验证用户邮箱的邮件
 *
 * @param email
 */
private static void sendEmailForVerifyUserEmail(String email) {

    BmobUser.sendEmailForVerifyUserEmail(email, new SendEmailListener() {
        @Override
        public void onSuccess(String msg) {
            System.out.println(msg);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });
}
```
## 邮箱重置密码
1、发送重置密码的邮件后，在邮件中进行密码的重置。
```
/**
 * 发送重置密码的邮件
 *
 * @param email
 */
private static void sendEmailForResetPassword(String email) {

    BmobUser.sendEmailForResetPassword(email, new SendEmailListener() {
        @Override
        public void onSuccess(String msg) {
            System.out.println(msg);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });
}
```
## 手机短信验证码重置密码

1、发送短信验证码，见文档短信验证的发送短信验证码。
2、验证手机短信验证码后重置密码。

```
/**
 * 短信验证码重置密码
 *
 * @param smsCode
 * @param newPassword
 */
private static void resetPasswordBySmsCode(String smsCode, String newPassword) {
    BmobUser.resetPasswordBySmsCode(smsCode, newPassword, new ResetPasswordListener() {
        @Override
        public void onSuccess(String msg) {
            System.out.println(msg);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });
}
```

## 旧密码重置密码
1、旧密码重置密码，需要登录。
```
/**
 * 旧密码重置密码，需要登录
 *
 * @param oldPassword
 * @param newPassword
 */
private static void resetPasswordByOldPassword(String oldPassword, String newPassword) {
    if (!BmobUser.getInstance().isLogin()) {
        System.err.println("尚未登录");
        return;
    }
    TestUser testUser = BmobUser.getInstance().getCurrentUser(TestUser.class);
    testUser.resetPasswordByOldPassword(oldPassword, newPassword, new ResetPasswordListener() {
        @Override
        public void onSuccess(String msg) {
            System.out.println(msg);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });
}
```

## 第三方账号系统关联

### 第三方账号注册/登录
```
/**
 * 登录
 */
private static void login() {
    BmobUser.loginQQ("", "", "", new ThirdLoginListener<TestUser>() {
        @Override
        public void onSuccess(TestUser user) {
            System.out.println(user.getObjectId());
        }


        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });
}
```

### 已有用户与第三方绑定
```
/**
 * 绑定
 */
private static void bind() {
    if (!BmobUser.getInstance().isLogin()) {
        System.err.println("尚未登录");
        return;
    }
    TestUser testUser = BmobUser.getInstance().getCurrentUser(TestUser.class);
    testUser.bindQQ("", "", "", new ThirdBindListener() {
        @Override
        public void onSuccess(String updatedAt) {
            System.out.println(updatedAt);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });
}

```

### 已有用户与第三方解绑
```
/**
 * 解绑
 */
private static void unbind() {
    if (!BmobUser.getInstance().isLogin()) {
        System.err.println("尚未登录");
        return;
    }
    TestUser testUser = BmobUser.getInstance().getCurrentUser(TestUser.class);
    testUser.unBindQQ(new ThirdUnBindListener() {
        @Override
        public void onSuccess(String updatedAt) {
            System.out.println(updatedAt);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });
}

```
# 角色表

_Role

|属性|说明|
|----|----|
|name|角色名称|
|roles|角色的子角色|
|title|角色包含的用户|
|users|图文消息注释|




```
/**
 * 查询角色
 * @param name
 */
private static void queryRole(final String name) {
    BmobQuery bmobQuery = new BmobQuery();
    bmobQuery.addWhereEqualTo("name", name);
    bmobQuery.getObjects(new GetsListener<BmobRole>() {
        @Override
        public void onSuccess(List<BmobRole> array) {
            if (array.size() < 1) {
                saveRole(name);
            } else {
                updateAddRole(array.get(0).getObjectId());
                updateRemoveRole(array.get(0).getObjectId());
            }
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });
}
```
```
/**
 * 
 * 保存角色
 * @param name
 */
private static void saveRole(String name) {
    BmobRole bmobRole = new BmobRole();
    //TODO Role names must be restricted to alphanumeric characters, dashes(-), underscores(_), and spaces.
    bmobRole.setName(name);
    BmobUser bmobUser = BmobUser.getInstance().getCurrentUser(BmobUser.class);
    bmobRole.getUsers().add(bmobUser);
    bmobRole.save(new SaveListener() {
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
```
```
/**
 * 添加角色中的用户
 * @param objectId
 */
private static void updateAddRole(String objectId) {
    BmobRole bmobRole = new BmobRole();
    //TODO Role names must be restricted to alphanumeric characters, dashes(-), underscores(_), and spaces.
    BmobUser bmobUser = BmobUser.getInstance().getCurrentUser(BmobUser.class);
    bmobRole.getUsers().add(bmobUser);
    bmobRole.setObjectId(objectId);
    bmobRole.update(new UpdateListener() {
        @Override
        public void onSuccess(String updatedAt) {
            System.out.println(updatedAt);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });
}
```
```
/**
 * 删除角色中的用户
 * @param objectId
 */
private static void updateRemoveRole(String objectId) {
    BmobRole bmobRole = new BmobRole();
    //TODO Role names must be restricted to alphanumeric characters, dashes(-), underscores(_), and spaces.
    BmobUser bmobUser = BmobUser.getInstance().getCurrentUser(BmobUser.class);
    bmobRole.getUsers().remove(bmobUser);
    bmobRole.setObjectId(objectId);
    bmobRole.update(new UpdateListener() {
        @Override
        public void onSuccess(String updatedAt) {
            System.out.println(updatedAt);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });
}
```




# 图文表

_Article

创建图文消息只能在控制台创建。

|属性|说明|
|----|----|
|url|控制台生成的图文消息的地址|
|type|图文消息类型|
|title|图文消息的标题|
|desc|图文消息注释|
|content|图文消息内容|

获取图文消息：
```
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
```

# 短信验证

## 发送短信验证码
```
/**
 * 发送验证短信，如果使用默认模板，则设置template为空字符串或不设置
 * @param phoneNumber
 * @param template 
 */
private static void sendSms(String phoneNumber,String template) {
    BmobSms bmobSms = new BmobSms();
    bmobSms.setMobilePhoneNumber(phoneNumber);
    bmobSms.setTemplate(template);
    bmobSms.sendSmsCode(new SendSmsCodeListener() {
        @Override
        public void onSuccess(String smsId) {
            System.out.println("发送短信成功：" + smsId);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println("发送短信失败：" + ex.getCode() + "-" + ex.getMessage());
        }
    });
}
```
## 验证短信验证码
```
/**
 * 验证短信验证码
 * @param phoneNumber
 * @param smsCode
 */
private static void verifySmsCode(String phoneNumber,String smsCode) {
    BmobSms bmobSms = new BmobSms();
    bmobSms.setMobilePhoneNumber(phoneNumber);
    bmobSms.verifySmsCode(smsCode, new VerifySmsCodeListener() {
        @Override
        public void onSuccess(String msg) {
            System.out.println("验证短信验证码成功：" + msg);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println("验证短信验证码失败：" + ex.getMessage());
        }
    });
}
```






# 数据查询

查询单个数据：

```
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

```

查询多个数据：

```
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

```

查询结果计数：
```
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
```

## 查询条件

比较查询：

|方法|功能|
|-----|-----|
|addWhereEqualTo|等于|
|addWhereNotEqualTo|不等于|
|addWhereLessThan|小于|
|addWhereLessThanOrEqualTo|小于等于|
|addWhereGreaterThan|大于|
|addWhereGreaterThanOrEqualTo|大于等于|





# 文件管理

上传文件：
```
/**
 * 上传文件
 */
private static void uploadFile() {
    File file = new File("/Users/zhangchaozhou/Desktop/F7C519D27247E8CE4EC6310472F5E47D.png");
    final BmobFile bmobFile = new BmobFile(file);
    bmobFile.uploadFile(new UploadListener() {
        @Override
        public void onSuccess() {
            System.out.println("success");
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });
}
```

删除文件：
```
/**
 * 删除文件
 *
 * @param cdnName
 * @param url
 */
private static void deleteFile(String cdnName, String url) {
    BmobFile bmobFile = new BmobFile();
    bmobFile.setCdnName(cdnName);
    bmobFile.setUrl(url);
    bmobFile.deleteFile(new DeleteFileListener() {
        @Override
        public void onSuccess(String msg) {
            System.out.println(msg);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });
}
```

# 数组

```
/**
 * 保存数组值
 */
private static void saveArray() {
    List<String> strings = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
        strings.add("元素" + i);
    }
    TestObject testObject = new TestObject();
    testObject.setArray(strings);
    testObject.save(new SaveListener() {
        @Override
        public void onSuccess(String objectId, String createdAt) {
            System.out.println(objectId + "-" + createdAt);


            //添加值
            addArray(objectId);

            //删除值
            removeArray(objectId);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });
}
```
```
/**
 * 从字段的数组中删除值
 *
 * @param objectId
 */
private static void removeArray(String objectId) {
    TestObject testObject = new TestObject();
    testObject.setObjectId(objectId);


    //TODO 1、删除字段的数组中的某个值
    testObject.remove("array", "元素0");


    //TODO 2、删除字段的数组中的多个值
    List<String> allStrings = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
        allStrings.add("元素" + i);
    }
    testObject.removeAll("array", allStrings);


    //TODO 3、删除字段的数组中的所有值
    testObject.remove("array");


    testObject.update(new UpdateListener() {
        @Override
        public void onSuccess(String updatedAt) {
            System.out.println(updatedAt);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });
}
```
```
/**
 * 往字段的数组中添加值
 *
 * @param objectId
 */
private static void addArray(String objectId) {

    TestObject testObject = new TestObject();
    testObject.setObjectId(objectId);

    //TODO 1、往字段的数组中添加一个元素
    testObject.add("array", "添加一个元素到array字段的列表中");


    //TODO 2、往字段的数组中添加多个元素
    List<String> allStrings = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
        allStrings.add("添加多个元素" + i);
    }
    testObject.addAll("array", allStrings);


    //TODO 3、如果原数组已存在该元素，则不添加
    testObject.addUnique("array", "元素0");


    //TODO 4、如果原数组已存在其中某些元素，则不增加其中已存在的元素
    List<String> allUniqueStrings = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
        allUniqueStrings.add("元素" + i);
    }
    testObject.addAllUnique("array", allUniqueStrings);

    testObject.update(new UpdateListener() {
        @Override
        public void onSuccess(String updatedAt) {
            System.out.println(updatedAt);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });
}

```

# 时间
```
/**
 * 保存date类型到表中
 */
private static void saveDate() {
    TestObject testObject = new TestObject();
    BmobDate bmobDate = new BmobDate(new Date());
    testObject.setDate(bmobDate);
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

```
# 地理位置

```
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

```
# 自增/自减
```
/**
 * 保存
 */
private static void saveInteger() {
    TestObject testObject = new TestObject();
    testObject.setInteger(1);
    testObject.save(new SaveListener() {
        @Override
        public void onSuccess(String objectId, String createdAt) {
            System.out.println(objectId + "-" + createdAt);
            /**
             * 保存之后执行自增修改
             */
            increment(objectId);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });

}

/**
 * 自增
 *
 * @param objectId
 */
private static void increment(String objectId) {
    TestObject testObject = new TestObject();
    /**
     * 自增1
     */
    testObject.increment("integer");
    /**
     * 自增n
     */
    testObject.increment("integer", -10);
    testObject.setObjectId(objectId);
    testObject.update(new UpdateListener() {
        @Override
        public void onSuccess(String updatedAt) {
            System.out.println(updatedAt);
        }

        @Override
        public void onFailure(BmobException ex) {
            System.err.println(ex.getMessage());
        }
    });
}
```

# 一对多关联 pointer
```
/**
 * 保存pointer类型
 */
private static void addPointer() {
    //TODO 设置一对多和多对多关系前，需确认该对象不为空，需先登录
    BmobUser bmobUser = BmobUser.getInstance().getCurrentUser(BmobUser.class);
    //一对多关系
    TestObject testObject = new TestObject();
    testObject.setUser(bmobUser);
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

# 多对多关联 relation

```
/**
 * 添加多对多关系
 */
private static void addRelation() {
    //TODO 设置一对多和多对多关系前，需确认该对象不为空，需先登录
    BmobUser bmobUser = BmobUser.getInstance().getCurrentUser(BmobUser.class);
    TestObject testObject = new TestObject();
    //多对多关系
    BmobRelation bmobRelation = new BmobRelation();
    bmobRelation.add(bmobUser);
    testObject.setRelation(bmobRelation);
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
# 同步异步

SDK使用回调的方式返回请求的数据或者错误信息，回调可分为同步回调和异步回调。

设置回调模式，true为同步，false为异步，默认为异步回调：
```
BmobConfig.setSynchronous(true);
```

```
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

```

