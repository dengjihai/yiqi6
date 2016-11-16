package com.yiqi6.test;

import com.yiqi6.util.HttpClientUtil;
import com.yiqi6.util.RandomCodeUtils;
import com.yiqi6.util.UtilDate;

/**
 * 测试发送短信
 *
 * @author dengjh
 * @create 2016-11-16 17:14
 **/
public class SmsTest {

    static String SEND_SMS_URL="http://61.174.50.42:8080/sms/ylSend3.do";

    static String SEND_SMS_ACCOUNT="161116_djh_egy";

    static String SEND_SMS_PASS="586202";




    public static void main(String[] args){
        String responseStr=sendTest();
        String response[]=responseStr.split(":");
        String nid=response[1];
        String smsStatus=getSmsStatus("205082758");
        System.out.println("发送状态："+ smsStatus);
        queryCount();
    }


    /**
     *
     */
    public static String sendTest(){
        String msg="您好，您的验证码是"+ RandomCodeUtils.getRandNum(6);
        String params="uid="+SEND_SMS_ACCOUNT+"&pwd="+SEND_SMS_PASS;
        String rev="13918702240,";
        String url=SEND_SMS_URL+"?"+params+"&rev="+rev+"&msg="+msg+"&snd=101&sdt=";
        System.out.println("发送参数："+ url);
        String returnStr=HttpClientUtil.getRequest(url,"");
        System.out.println("发送时间："+ UtilDate.getDateFormatter()+"返回结果："+returnStr);
        return  returnStr;
    }

    /**
     * 查询条数
     */
    public static  void queryCount(){
        String url="http://61.174.50.42:8080/sms/ylGetSubFee.do?uid="+SEND_SMS_ACCOUNT+"&pwd="+SEND_SMS_PASS;
        String returnStr=HttpClientUtil.getRequest(url,"");
        System.out.println("请求时间："+ UtilDate.getDateFormatter()+"返回结果："+returnStr);
    }

    public static String  getSmsStatus(String nid){
        String url="http://61.174.50.42:8080/sms/ylGetState.do?uid="+SEND_SMS_ACCOUNT+"&pwd="+SEND_SMS_PASS+"&nid="+nid;
        return HttpClientUtil.getRequest(url,"");
    }

}
