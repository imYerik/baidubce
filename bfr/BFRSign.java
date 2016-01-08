package com.baidu.cloudservice.bfr;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Date;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Hex;
import com.baidubce.BceClientException;
import com.baidubce.util.DateUtils;

public class BFRSign {

	private static final String DEFAULT_ENCODING = "UTF-8";
	private static final Charset UTF8 = Charset.forName(DEFAULT_ENCODING);	
	 String requestMethod;
	 String host;
	 String uri;
	 int expiresInSeconds;
	 String queryString;	
	

//	 传入签名参数
public BFRSign(String requestMethod, String host, String uri,int expiresInSeconds, String queryString) {
		super();
		this.requestMethod = requestMethod;
		this.host = host;
		this.uri = uri;
		this.expiresInSeconds = expiresInSeconds;
		this.queryString = queryString;
	}

//生成签名
	public String getAuthorization () throws Exception{
 
    		String timestamp = DateUtils.formatAlternateIso8601Date(new Date());	 

		String CanonicalURI = URLEncoder.encode(uri, "UTF-8").replace("%2F","/");
//        System.out.println("CanonicalURI: "+CanonicalURI);
      
        String canonicalQueryString =canonicalQueryString(queryString);

		String canonicalHeaders = "host:"+host;
		String CanonicalRequest = joinN("\n", new String[] { requestMethod,CanonicalURI, canonicalQueryString, canonicalHeaders });
//		System.out.println("CanonicalRequest: "+CanonicalRequest);
				
		//通过AK+时间戳+过期时间拼接成signKeyInfo
		String signKeyInfo = "bce-auth-v1" + "/" + BFRConfig.accessKey + "/" + timestamp+ "/" + expiresInSeconds;
//		System.out.println("signKeyInfo: "+signKeyInfo);
		
		//将signKeyInfo与SK加密生成signingKey
		String signingKey = sha256Hex(BFRConfig.secretKey, signKeyInfo);
//		System.out.println("signingKey: "+signingKey);
		
		//将signingKey与CanonicalRequest加密生成signature
		String signature = sha256Hex(signingKey, CanonicalRequest);

		//通过signature合成authorization
		String authorization = joinN("/", new String[] { "bce-auth-v1",BFRConfig.accessKey, timestamp, String.valueOf(expiresInSeconds), "host",signature });
//		System.out.println("authorization: \n"+authorization);
//		System.out.println("URL: \nhttp://"+host+CanonicalURI+"?"+canonicalQueryString);
    		
		return authorization;  		
    }
	

	private static String joinN(String split, String[] args) {
		String ret = args[0];
		for (int i = 1; i < args.length; i++)
			ret = ret + split + args[i];
		return ret;
	}

	public static String sha256Hex(String signingKey, String stringToSign) {
		try {
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(new SecretKeySpec(signingKey.getBytes(UTF8), "HmacSHA256"));
			return new String(Hex.encodeHex(mac.doFinal(stringToSign
					.getBytes(UTF8))));
		} catch (Exception e) {
			throw new BceClientException("Fail to generate the signature", e);
		}
	}
	
	//canonicalQueryString转换
	public static String canonicalQueryString(String queryString) throws Exception{
		
				String canonicalQueryString="";
				String[] keyValueList =queryString.split("&");
				int position;
				String key=null;
				String value=null;
				if (queryString.isEmpty()){
//					System.out.println("canonicalQueryString1: "+canonicalQueryString);
					return canonicalQueryString;		
				}else{
					for (int i=0;i<keyValueList.length;i++){
						position=keyValueList[i].indexOf("=");
						if (position<0){
							key=URLEncoder.encode(keyValueList[i], "UTF-8");
							value="";
						}else{
							key=URLEncoder.encode(keyValueList[i].substring(0,position), "UTF-8");
							value=URLEncoder.encode(keyValueList[i].substring(position+1,keyValueList[i].length()), "UTF-8");
						}
			//			System.out.println("Key"+i+": "+key+"----"+"Value"+i+": "+value);
						keyValueList[i]=key+"="+value;
						if(i==keyValueList.length-1){
							canonicalQueryString=canonicalQueryString+keyValueList[i];
						}else{
							canonicalQueryString=canonicalQueryString+keyValueList[i]+"&";
						}
					}
//					System.out.println("canonicalQueryString2: "+canonicalQueryString);
					}
				return canonicalQueryString; 				
	}

}
