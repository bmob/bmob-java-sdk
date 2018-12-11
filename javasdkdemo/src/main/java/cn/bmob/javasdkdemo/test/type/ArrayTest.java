package cn.bmob.javasdkdemo.test.type;

import cn.bmob.data.Bmob;
import cn.bmob.data.callback.object.SaveListener;
import cn.bmob.data.callback.object.UpdateListener;
import cn.bmob.data.exception.BmobException;
import cn.bmob.javasdkdemo.test.bean.TestConfig;
import cn.bmob.javasdkdemo.test.bean.TestObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 示例：数组字段的增删
 */
public class ArrayTest {


    public static void main(String[] args) {
        //TODO Application Entrance


        Bmob.getInstance().init(TestConfig.appId, TestConfig.apiKey);

        saveArray();

    }


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
}
