package com.baidu.cloudservice.bfr;

public class BFRVerify {

//	验证某个图片是否属于某个用户
	public void verifyFromBosFile(String appId, String personName, String bosPath) throws Exception{
//		POST /v{version}/app/{appId}/person/{personName}?verify
		
		String requestMethod = new String("POST");
		String uri = new String("/v1/app/"+appId+"/person/"+personName);
		String queryString = new String("verify");	
		String httpBody = new String("{ \"bosPath\": \"" + bosPath + "\"}");
		
		BFRSign bfrSign = new BFRSign(requestMethod, BFRConfig.host, uri,
				BFRConfig.expiresInSeconds, queryString);
		String authorization = bfrSign.getAuthorization();

		BFRHttpRequest bfrReq = new BFRHttpRequest(requestMethod,
				BFRConfig.host, uri, authorization, queryString,httpBody);
		bfrReq.sendRequest();
			
	}
	
	
	public void verifyFromBase64(String appId, String personName, String base64) throws Exception{
//		POST /v{version}/app/{appId}/person/{personName}?verify

		String requestMethod = new String("POST");
		String uri = new String("/v1/app/"+appId+"/person/"+personName);
		String queryString = new String("verify");	
		String httpBody = new String("{ \"base64\": \"" + base64 + "\"}");
		
		BFRSign bfrSign = new BFRSign(requestMethod, BFRConfig.host, uri,
				BFRConfig.expiresInSeconds, queryString);
		String authorization = bfrSign.getAuthorization();

		BFRHttpRequest bfrReq = new BFRHttpRequest(requestMethod,
				BFRConfig.host, uri, authorization, queryString,httpBody);
		bfrReq.sendRequest();
		
	}

//	验证某个图片是否属于某个组
	public void identifyFromBosFile(String appId, String groupName, String bosPath) throws Exception{
//		POST /v{version}/app/{appId}/group/{groupName}?identify HTTP/1.1
		String requestMethod = new String("POST");
		String uri = new String("/v1/app/"+appId+"/group/"+groupName);
		String queryString = new String("identify");	
		String httpBody = new String("{ \"bosPath\": \"" + bosPath + "\"}");
		
		BFRSign bfrSign = new BFRSign(requestMethod, BFRConfig.host, uri,
				BFRConfig.expiresInSeconds, queryString);
		String authorization = bfrSign.getAuthorization();

		BFRHttpRequest bfrReq = new BFRHttpRequest(requestMethod,
				BFRConfig.host, uri, authorization, queryString,httpBody);
		bfrReq.sendRequest();
		
	}
	public void identifyFromBase64(String appId, String groupName, String base64) throws Exception{
		
//		POST /v{version}/app/{appId}/group/{groupName}?identify HTTP/1.1
		String requestMethod = new String("POST");
		String uri = new String("/v1/app/"+appId+"/group/"+groupName);
		String queryString = new String("identify");	
		String httpBody = new String("{ \"base64\": \"" + base64 + "\"}");
		
		BFRSign bfrSign = new BFRSign(requestMethod, BFRConfig.host, uri,
				BFRConfig.expiresInSeconds, queryString);
		String authorization = bfrSign.getAuthorization();

		BFRHttpRequest bfrReq = new BFRHttpRequest(requestMethod,
				BFRConfig.host, uri, authorization, queryString,httpBody);
		bfrReq.sendRequest();
		
	}

	
}
