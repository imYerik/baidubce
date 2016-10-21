package com.baidu.cloudservice.vod;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import org.apache.commons.codec.binary.Hex;

public class WebToken {
    static final Charset CHARSET_UTF8 = Charset.forName("UTF-8");

    public static String sha256Hex(String signingKey, String stringToSign) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(signingKey.getBytes(CHARSET_UTF8), "HmacSHA256"));
            return new String(Hex.encodeHex(mac.doFinal(stringToSign.getBytes(CHARSET_UTF8))));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        String gTestUserKey = "ef90e14f8x38c9oa";        //User Key，目前console还没有获取方式，需要提工单获取
//        String gTestMediaId = "mda-ghm06t1fcrdzcgav"; //如果是VOD，则使用mediaId
        String gTestMediaId = "job-gjwz2c1rugvjzdys";  //如果是MCT则使用job id；
        String userId = "3x9137178965428xa8db7636a7c25e8d";  //账号的用户userid，通过console右上角的账号信息获取
        String expirationTime = Long.toString(System.currentTimeMillis
        		()/1000 + 3600);
        
        System.out.println("System.currentTimeMillis:"+System.currentTimeMillis());
        
        String signature = sha256Hex(gTestUserKey, "/" + gTestMediaId + "/" + expirationTime);
        System.out.println("Signature:" + signature); //过期时间以秒为单位
        
        
        String token = String.format("%s_%s_%s", signature, userId,expirationTime);
        
        System.out.println("Token:"+token);
        
    }
}
