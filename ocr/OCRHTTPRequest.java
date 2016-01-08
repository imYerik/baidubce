package com.baidu.cloudservice.ocr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

 class OCRHttpRequest {

	 String requestMethod;
	 String host;
	 String uri;
	 String authorization;
	 String canonicalQueryString;
	 String httpBody ;

	
	public OCRHttpRequest(String requestMethod, String host, String uri,
			String authorization, String queryString, String httpBody) throws Exception {
		super();
		this.requestMethod = requestMethod;
		this.host = host;
		this.uri = uri;
		this.authorization = authorization;
		this.canonicalQueryString = OCRSign.canonicalQueryString(queryString);
		this.httpBody = httpBody;
	}

	public OCRHttpRequest(String requestMethod, String host, String uri,
			String authorization, String queryString) throws Exception {
		super();
		this.requestMethod = requestMethod;
		this.host = host;
		this.uri = uri;
		this.authorization = authorization;
		this.canonicalQueryString = OCRSign.canonicalQueryString(queryString);
	}
	
	public  void sendRequest(){
		CloseableHttpClient client = HttpClients.createDefault();
		HttpResponse response = null ;
		try{
					switch(requestMethod){
							case "POST":
								//设置HTTP POST请求头
								 HttpPost postRequest = new HttpPost("http://" + host + uri + ("".equals(canonicalQueryString) ? "" : "?" + canonicalQueryString));
								postRequest.setHeader("host", host);
								postRequest.setHeader("authorization", authorization);		
								postRequest.setEntity(new StringEntity(httpBody));
								response = client.execute(postRequest);
								break;
							case "DELETE":
								 HttpDelete deleteRequest = new HttpDelete("http://" + host + uri + ("".equals(canonicalQueryString) ? "" : "?" + canonicalQueryString));
								 deleteRequest.setHeader("host", host);
								 deleteRequest.setHeader("authorization", authorization);		
								response = client.execute(deleteRequest);
								break;
							case "PUT":
								HttpPut putRequest = new HttpPut("http://" + host + uri + ("".equals(canonicalQueryString) ? "" : "?" + canonicalQueryString));
								putRequest.setHeader("host", host);
								putRequest.setHeader("authorization", authorization);		
								putRequest.setEntity(new StringEntity(httpBody));
								response = client.execute(putRequest);
								break;
								
							case "GET":
								HttpGet  getRequest = new HttpGet("http://" + host + uri + ("".equals(canonicalQueryString) ? "" : "?" + canonicalQueryString));				
								//设置HTTP GET请求头				
								getRequest.setHeader("host", host);
								getRequest.setHeader("authorization", authorization);		
								
								//执行GET请求
								response = client.execute(getRequest);
								break;
					} 
					
					//输出HTTP响应结果
					System.out.println(response.getStatusLine());
					BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
					String result = "";
					String line = "";
					while ((line = rd.readLine()) != null) {
						result += line;
					}
					System.err.println(result);

					HttpEntity entity1 = response.getEntity();
					// do something useful with the response body
					// and ensure it is fully consumed
					EntityUtils.consume(entity1);
		
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}
 
 
