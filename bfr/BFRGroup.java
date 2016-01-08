package com.baidu.cloudservice.bfr;

public class BFRGroup {

	String uri ;
	String queryString = new String("");
	
	
//创建Group
	public void createGroup(String appId, String groupName) throws Exception {
		String requestMethod = new String("POST");
		uri = new String("/v1/app/"+appId+"/group");
		String httpBody = new String("{ \"groupName\": \"" + groupName + "\"}");

		BFRSign bfrSign = new BFRSign(requestMethod, BFRConfig.host, uri,
				BFRConfig.expiresInSeconds, queryString);
		String authorization = bfrSign.getAuthorization();

		BFRHttpRequest bfrReq = new BFRHttpRequest(requestMethod,
				BFRConfig.host, uri, authorization, queryString, httpBody);
		bfrReq.sendRequest();
	}

//	删除Group
	public void deleteGroup(String appId, String groupName) throws Exception {
		String requestMethod = new String("DELETE");
		uri = new String("/v1/app/"+appId+"/group/"+groupName);

		BFRSign bfrSign = new BFRSign(requestMethod, BFRConfig.host, uri,
				BFRConfig.expiresInSeconds, queryString);
		String authorization = bfrSign.getAuthorization();

		BFRHttpRequest bfrReq = new BFRHttpRequest(requestMethod,
				BFRConfig.host, uri, authorization, queryString);
		bfrReq.sendRequest();
	}

//	查询Group
	public void searchGroup(String appId, String groupName) throws Exception{
		String requestMethod = new String("GET");
		uri = new String("/v1/app/"+appId+"/group/"+groupName);

		BFRSign bfrSign = new BFRSign(requestMethod, BFRConfig.host, uri,
				BFRConfig.expiresInSeconds, queryString);
		String authorization = bfrSign.getAuthorization();

		BFRHttpRequest bfrReq = new BFRHttpRequest(requestMethod,
				BFRConfig.host, uri, authorization, queryString);
		bfrReq.sendRequest();
		
	}
//	列出相应工程下所有Group
	public void listGroups(String appId) throws Exception {
		String requestMethod = new String("GET");
		uri = new String("/v1/app/"+appId+"/group");

		BFRSign bfrSign = new BFRSign(requestMethod, BFRConfig.host, uri,
				BFRConfig.expiresInSeconds, queryString);
		String authorization = bfrSign.getAuthorization();

		BFRHttpRequest bfrReq = new BFRHttpRequest(requestMethod,
				BFRConfig.host, uri, authorization, queryString);
		bfrReq.sendRequest();
	}
}
