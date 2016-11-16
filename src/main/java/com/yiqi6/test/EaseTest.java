package com.yiqi6.test;

import com.yiqi6.util.HttpSSLClient;

import java.util.HashMap;
import java.util.Map;

public class EaseTest  {
	
	public static final String org_name="1178161108115568";
	
	public static final String app_name="ydgj";

	private static final String CONTENT_TYPE_JSON = "application/json;charset=UTF-8";

	public static void main(String[] args) {
		testAddUser();
	}
	
	public static  void testAddUser(){
		String url="https://a1.easemob.com/"+org_name+"/"+app_name;
		Map<String,String> map = new HashMap<String,String>();
		map.put("username","789456");
		map.put("password","71b3b26aaa319e0cdf6fdb8429c112b0");
		map.put("nickname","邓集海");
		String responseStr= HttpSSLClient.doSSLPost(url,map,"UTF-8");
		System.out.println(responseStr);
	}
	
	
	//public void testGetToken(){
	//	String url="https://a1.easemob.com/"+org_name+"/"+app_name;
	//	String requestBody="{\"grant_type\":\"client_credentials\",\"client_id\":\"YXA6XTnroKWBEeaI0kd5uMSR6Q\",\"client_secret\":\"YXA6p3ehDLT_Aqy3kkJJsdkNfrSAH_8\"}";
	//	String responseStr=HttpSSLClient.doSSLPost(url, requestBody);
	//	System.out.println(responseStr);
	//}
}
