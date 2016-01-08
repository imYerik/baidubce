package com.baidu.cloudservice.bfr;

import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class BFRPerson {
	String uri ;
	String queryString = new String("");
	
//	创建成员
	public void createPerson(String appId, String groupName, String personName, List<String> faces) throws Exception{
	
//		构造成员JSON bosPath
		JSONArray faceArray = new JSONArray();
		 Iterator<String> iter = faces.iterator();
		while(iter.hasNext() ){
			JSONObject bosPath = new JSONObject();
			bosPath.put("bosPath",iter.next());
			faceArray.put(bosPath);	
		}		
//		添加JSON对象
		JSONObject person = new JSONObject();
		person.put("personName", personName);
		person.put("groupName",groupName);
		person.put("faces", faceArray);
		
//		发送JSON Body
		String requestMethod = new String("POST");
		uri = new String("/v1/app/"+appId+"/person");
		String httpBody = person.toString();
		
//		System.out.println(person.toString());	
		BFRSign bfrSign = new BFRSign(requestMethod, BFRConfig.host, uri,
				BFRConfig.expiresInSeconds, queryString);
		String authorization = bfrSign.getAuthorization();

		BFRHttpRequest bfrReq = new BFRHttpRequest(requestMethod,
				BFRConfig.host, uri, authorization, queryString, httpBody);
		bfrReq.sendRequest();
	}
//	删除成员
	public void deletePerson(String appId, String personName) throws Exception{
		
		String requestMethod = new String("DELETE");
		uri = new String("/v1/app/"+appId+"/person/"+personName);
		
//		System.out.println(person.toString());	
		BFRSign bfrSign = new BFRSign(requestMethod, BFRConfig.host, uri,
				BFRConfig.expiresInSeconds, queryString);
		String authorization = bfrSign.getAuthorization();

		BFRHttpRequest bfrReq = new BFRHttpRequest(requestMethod,
				BFRConfig.host, uri, authorization, queryString);
		bfrReq.sendRequest();
	}
	
//	修改成员
	public void modifyPerson(String appId, String personName, List <String> faces) throws Exception{
//		构造成员JSON bosPath
		JSONArray faceArray = new JSONArray();
		 Iterator<String> iter = faces.iterator();
		while(iter.hasNext() ){
			JSONObject bosPath = new JSONObject();
			bosPath.put("bosPath",iter.next());
			faceArray.put(bosPath);	
		}		
//		添加JSON对象
		JSONObject person = new JSONObject();
		person.put("faces", faceArray);
		
//		发送JSON Body
		String requestMethod = new String("PUT");
		uri = new String("/v1/app/"+appId+"/person/"+personName);
		String httpBody = person.toString();
		
//		System.out.println(person.toString());	
		BFRSign bfrSign = new BFRSign(requestMethod, BFRConfig.host, uri,
				BFRConfig.expiresInSeconds, queryString);
		String authorization = bfrSign.getAuthorization();

		BFRHttpRequest bfrReq = new BFRHttpRequest(requestMethod,
				BFRConfig.host, uri, authorization, queryString, httpBody);
		bfrReq.sendRequest();
		
	}
//	查找成员
	public void searchPerson(String appId, String personName) throws Exception{
		String requestMethod = new String("GET");
		uri = new String("/v1/app/"+appId+"/person/"+personName);
		
		BFRSign bfrSign = new BFRSign(requestMethod, BFRConfig.host, uri,
				BFRConfig.expiresInSeconds, queryString);
		String authorization = bfrSign.getAuthorization();

		BFRHttpRequest bfrReq = new BFRHttpRequest(requestMethod,
				BFRConfig.host, uri, authorization, queryString);
		bfrReq.sendRequest();	
		
	}
//	列出所有工程下成员
	public void listPersons(String appId) throws Exception{
		String requestMethod = new String("GET");
		uri = new String("/v1/app/"+appId+"/person");
		
		BFRSign bfrSign = new BFRSign(requestMethod, BFRConfig.host, uri,
				BFRConfig.expiresInSeconds, queryString);
		String authorization = bfrSign.getAuthorization();

		BFRHttpRequest bfrReq = new BFRHttpRequest(requestMethod,
				BFRConfig.host, uri, authorization, queryString);
		bfrReq.sendRequest();	
	}
	
//	按组列出成员
	public void listByGroup(String appId, String groupName) throws Exception{
		
//		/v1/app/40000/person?groupName=student
		String requestMethod = new String("GET");
		uri = new String("/v1/app/"+appId+"/person");
		String queryString = new String("groupName="+groupName);
		
		BFRSign bfrSign = new BFRSign(requestMethod, BFRConfig.host, uri,
				BFRConfig.expiresInSeconds, queryString);
		String authorization = bfrSign.getAuthorization();

		BFRHttpRequest bfrReq = new BFRHttpRequest(requestMethod,
				BFRConfig.host, uri, authorization, queryString);
		bfrReq.sendRequest();	
		
	}
}
