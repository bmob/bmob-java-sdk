﻿package com.ericsoft.demo;

import java.util.Date;

import com.ericsoft.bmob.bson.BSON;
import com.ericsoft.bmob.bson.BSONObject;
import com.ericsoft.bmob.restapi.Bmob;

public class TestDemo {
    
	public static void main(String[] args) {
		//BSONObject 简单使用
//		CreateClassBSONObject();
		
		initBmob();//初始化
//		Search();//查询
//		update();//修改
//		delete();//删除
		insert();//新增
//		callFunction();//调用云代码
//		findPayOrder();//查询支付订单
//		count();//计数
//		upload();//上传文件
		// requestSms();//发送短信
	}
	
	//使用RestAPI前必须先初始化，KEY可在Bmob应用信息里查询。
	private static void initBmob(){
		Bmob.initBmob("Your Bmob-Application-Id",
		"Your Bmob-REST-API-Key");		
			
		//用到超级权限需要注册该Key
//		Bmob.initMaster("Your Bmob-Master-Key");
	}
	
	private static void upload(){
		String res=Bmob.uploadFile2("/tmp/myPicture.jpg");
		System.out.println(res);
	}
	  
	private static void requestSms(){
		String res=Bmob.requestSms("13168391111","您的验证码是：222222, 有效期是10分钟。");
		System.out.println(res);		
	}
	
	
	
	private static void Search(){
		//where方法很多，可参考官网RestAPI文档
		BSONObject where1 = new BSONObject(Bmob.whereLess(10));
		BSONObject where = new BSONObject();
		where.put("score", where1);
		//find方法很多，可自行尝试
		String result = Bmob.find("Your TableName", where.toString(), 0, 50, "order");
		Bmob.findBQL("BQL");
		Bmob.findBQL("BQL","value");
		//可使用JSON 或者 BSON 转换成Object
//		BSONObject bson = new BSONObject(result);
	}
	
	private static void update(){
		BSONObject bson = new BSONObject();
		bson.put("score", 100);
		//score 修改为100
		Bmob.update("Your TableName", "Your objectId", bson.toString());
	}
	
	private static void delete(){
		Bmob.delete("Your TableName", "Your objectId");
	}
	
	private static void insert(){
		BSONObject bson = new BSONObject();
		bson.put("student", "zhangsan");
		Bmob.insert("Your TableName", bson.toString());
	}
	
	private static void callFunction(){
		BSONObject bson = new BSONObject();
		bson.put("param1", "a");
		bson.put("param2", 0);
		bson.put("param3", true);
		
		Bmob.callFunction("Your functionName", bson.toString());
	}
	
	private static void findPayOrder(){
		Bmob.findPayOrder("Your PayId");
	}
	
	private static void count(){
		BSONObject where = new BSONObject();
		where.put("score", 100);
		Bmob.count("Your TableName", where.toString());
	}
	
	private static void CreateClassBSONObject(){
		BSONObject class1, teacher, students;
		BSONObject zhangsan, lisi, wangwu;
		
		class1 = new BSONObject();
		class1.put("name", "Class 1");
		class1.put("build", new Date());
		
		teacher = new BSONObject();
		teacher.put("name", "Miss Wang");
		teacher.put("sex", "female");
		teacher.put("age", 30);
		teacher.put("offer", true);
		
		students = new BSONObject();
		students.put("number", 45).put("boy", 23).put("girl", 22);
		
		zhangsan = new BSONObject().put("name", "ZhangSan");
		zhangsan.put("age", 12).put("sex", "male");

		lisi = new BSONObject();
		lisi.put("name", "LiSi");
		lisi.put("age", 12);
		lisi.put("sex", "female");
		
		wangwu = new BSONObject();
		wangwu.put("name", "WangWu");
		wangwu.put("age", 13);
		wangwu.put("sex", "male");
		
		students.put("student", new BSONObject[]{zhangsan, lisi, wangwu});
		class1.put("teacher", teacher);
		class1.put("students", students);
		
		BSONObject bson_class = new BSONObject(class1.toString());
		BSON.Log("BSON:"+bson_class+"\n");
		BSON.Log("Build date:"+bson_class.getDate("build"));
		BSON.Log("Is teacher offer? "+bson_class.getBSONObject("teacher").getBoolean("offer"));
		BSON.Log("Teacher's age:"+bson_class.getBSONObject("teacher").getInt("age"));
		BSON.Log("First student's name:"+bson_class.getBSONObject("students").getBSONArray("student")[0].getString("name"));
		
	}
	
}