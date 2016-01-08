package com.baidu.cloudservice.bfr;

public class BFRApp {
	 String uri = new String("/v1/app");
	 String queryString = new String("");	
	
	public void listAllApp() throws Exception{
//		列出所有工程
		String requestMethod= new String("GET");		 	 	
		BFRSign bfrSign = new BFRSign( requestMethod,  BFRConfig.host,  uri, BFRConfig.expiresInSeconds,queryString);
		String authorization = bfrSign.getAuthorization();		
		BFRHttpRequest bfrReq = new BFRHttpRequest( requestMethod, BFRConfig.host, uri,authorization,  queryString);
		bfrReq.sendRequest();	
	}
	
	public void createApp() throws Exception{
//		创建工程
		String requestMethod= new String("POST");		
		String httpBody = new String("");
		
		BFRSign bfrSign = new BFRSign( requestMethod,  BFRConfig.host,  uri, BFRConfig.expiresInSeconds,queryString);
		String authorization = bfrSign.getAuthorization();			
		
		BFRHttpRequest bfrReq = new BFRHttpRequest(requestMethod,  BFRConfig.host,  uri,authorization,  queryString,  httpBody) ;
		bfrReq.sendRequest();		
		
	}
	
	public void deleteApp(){
//		BCE暂不提供delete 工程的接口
		
	}
	
	
}
