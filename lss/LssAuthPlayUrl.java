package com.baidu.cloudservice.lss_v2;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;
import com.baidubce.BceClientConfiguration;
import com.baidubce.BceClientException;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.lss.LssClient;
import com.baidubce.services.lss.model.GetSecurityPolicyResponse;
import com.baidubce.services.lss.model.GetSessionResponse;
import com.baidubce.util.DateUtils;

public class LssAuthPlayUrl{

	public static void main(String[] args) throws MalformedURLException {
		// TODO Auto-generated method stub

/*
 * 播放验证URL格式：  protocol://domainname/uri?token=xxxxx?expire=2016-03-23T13:17:59Z
 * 例如：
 * rtmp://lss-gb3hgrn1ic4thgh4.play.bcelive.com/live/lss-gb3hgrn1ic4thgh4?token=b8855078bf57c174df3fd133a2a3c812e8373c3ac670eb14db508655bf2315b9&expire=2016-03-23T13:17:59Z
 * http://bktmga4kjr6jcjrgi63m.exp.bcelive.com/lss-gb3hgrn1ic4thgh4/live.m3u8?token=57a1476b7651f63e3ae39bd8ae358eb7bcb8833193d5a681e23646ff937e5cc7&expire=2016-03-23T13:17:59Z
		
		
		protocol://domainname/uri 部分通过SDK中的client.getSession(sessionId).getPlay().getRtmpUrl()方法获取；
			
	    token的计算方式：token = HMAC-SHA256-HEX("key", "uri;expire")
	    key：直播会话所绑定的安全策略的auth参数下的key值，可以查看安全策略相关的API文档了解；
	    	uri: RTMP则uri为sessionId，如果是HLS协议则uri为Hls播放地址的path部分（例：/live/eo6h2hwtv5tmk56bs1），不带参数。	    
	    expire: 过期时间戳(expire)的格式为：2016-03-23T13:17:59Z
	    （1）表示日期一律采用YYYY-MM-DD方式，例如2014-06-01表示2014年6月1日
	    （2）表示时间一律采用hh:mm:ss方式，并在最后加一个大写字母Z表示UTC
	    时间。 例如23:00:10Z表示UTC时间23点0分10秒。
	    （3）凡涉及日期和时间合并表示时，在两者中间加大写字母T，例如2014-06-01T23:00:10Z表示UTC时间2014年6月1日23点0分10秒。
*/
		
		String ACCESS_KEY_ID = "your ACCESS_KEY_ID";
		String SECRET_ACCESS_KEY = "your SECRET_ACCESS_KEY";
		String ENDPOINT = "http://lss.bj.baidubce.com";		//如果是gz region替换成http://lss.gz.baidubce.com

// 初始化一个LssClient
	    BceClientConfiguration config = new BceClientConfiguration();
	    config.setCredentials(new DefaultBceCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY));
	    config.setEndpoint(ENDPOINT);
	    LssClient client = new LssClient(config);
	      
//	获取直播会话的播放地址 
	   int expireSeconds = 3600;	   //播放地址有效期（秒）
	   String sessionId = new String("lss-sessionid"); 	//会话ID

	   String authRtmpPlayUrl = getRtmpAuthPlayUrl(client,expireSeconds, sessionId);
	   System.out.println(authRtmpPlayUrl);
	   
	   String authHlsPlayUrl = getHlsAuthPlayUrl(client,expireSeconds, sessionId);
	   System.out.println(authHlsPlayUrl);   
	}

	
//生成RTMP播放验证URL
public static String getRtmpAuthPlayUrl(LssClient client,int expireSeconds, String sessionId) throws MalformedURLException{
//	 如果是RTMP播放，则uri为sessionId
	 String playUrl = client.getSession(sessionId).getPlay().getRtmpUrl();
	 String uri = sessionId;
	 
//	   生成过期时间戳,格式2016-03-23T08:00:27Z
	   Date expireDate = new Date(System.currentTimeMillis() + expireSeconds*1000 );	   
	   String expireStamp = DateUtils.formatAlternateIso8601Date(expireDate);	 
	   String token = getToken(client, sessionId, uri, expireStamp);

//	   生成验证URL
	   String authPlayUrl = new String(playUrl+"?token="+token+"&expire="+expireStamp);
	   return authPlayUrl;
}	

//生成HLS播放验证URL
public static String getHlsAuthPlayUrl(LssClient client,int expireSeconds, String sessionId) throws MalformedURLException{
//	 HLS协议则uri为Hls播放地址的path部分
	 String playUrl = client.getSession(sessionId).getPlay().getHlsUrl();;
	 String uri = new URL(playUrl).getPath();

//	   生成过期时间戳,格式2016-03-23T08:00:27Z
	   Date expireDate = new Date(System.currentTimeMillis() + expireSeconds*1000 );	   
	   String expireStamp = DateUtils.formatAlternateIso8601Date(expireDate);	 
	   String token = getToken(client, sessionId, uri, expireStamp);

//	   生成验证URL
	   String authHlsPlayUrl = new String(playUrl+"?token="+token+"&expire="+expireStamp);
	   return authHlsPlayUrl;
}	

//生成token
public static String getToken(LssClient client, String sessionId, String uri, String expireStamp){
//	   获取会话绑定的安全策略key
	   String securityPolicyKey = getSecurityPolicyKey(client,sessionId);
//	   生成token（使用SHA256算法，将securityPolicyKey与字符串uri+";"+expireStamp加密；）
	   String token = sha256Hex(securityPolicyKey,uri+";"+expireStamp);   
	   return token;
}

//获取会话绑定的安全策略的key
public static String getSecurityPolicyKey(LssClient client,String sessionId){
	   GetSessionResponse getSessionResp = client.getSession(sessionId);
	   String policyName = getSessionResp.getSecurityPolicy();
	   GetSecurityPolicyResponse getPolicyResp = client.getSecurityPolicy(policyName);  
	   String securityPolicyKey =  getPolicyResp.getAuth().getKey();
	   return securityPolicyKey;
}

//	HMAC-SHA256-HEX算法
	public static String sha256Hex(String signingKey, String stringToSign) {
		try {
			final String DEFAULT_ENCODING = "UTF-8";
			final Charset UTF8 = Charset.forName(DEFAULT_ENCODING);	
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(new SecretKeySpec(signingKey.getBytes(UTF8), "HmacSHA256"));
			return new String(Hex.encodeHex(mac.doFinal(stringToSign
					.getBytes(UTF8))));
		} catch (Exception e) {
			throw new BceClientException("Fail to generate the signature", e);
		}
	}
	

}
