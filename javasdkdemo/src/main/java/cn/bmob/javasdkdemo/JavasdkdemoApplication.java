package cn.bmob.javasdkdemo;

import cn.bmob.data.Bmob;
import cn.bmob.data.config.BmobConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavasdkdemoApplication {

    public static String appId = "12784168944a56ae41c4575686b7b332";
    public static String apiKey = "9e8ffb8e0945092d1a6b3562741ae564";

    public static void main(String[] args) {
        SpringApplication.run(JavasdkdemoApplication.class, args);
        Bmob.getInstance().init(appId,apiKey);
    }
}
