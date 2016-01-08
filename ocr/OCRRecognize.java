package com.baidu.cloudservice.ocr;

public class OCRRecognize {

	public void recognizeText(String base64Str,String language) throws Exception{
		
//		POST /v1/recognize/text HTTP/1.1
		String requestMethod = new String("POST");
		String uri = new String("/v1/recognize/text");
		String queryString = new String("");			
		String httpBody = new String("{\"base64\":\""+base64Str+"\""+","+"\"language\":\""+language+"\"}");
		OCRSign ocrSign = new OCRSign(requestMethod, OCRConfig.host, uri,
				OCRConfig.expiresInSeconds, queryString);
		String authorization = ocrSign.getAuthorization();

		OCRHttpRequest bfrReq = new OCRHttpRequest(requestMethod,
				OCRConfig.host, uri, authorization, queryString,httpBody);
		bfrReq.sendRequest();			
		
	}
	public void recognizeLine(String base64Str,String language) throws Exception{
		
//		POST /v{version}/recognize/line HTTP/1.1
		String requestMethod = new String("POST");
		String uri = new String("/v1/recognize/line");
		String queryString = new String("");			
		String httpBody = new String("{\"base64\":\""+base64Str+"\""+","+"\"language\":\""+language+"\"}");
		OCRSign ocrSign = new OCRSign(requestMethod, OCRConfig.host, uri,
				OCRConfig.expiresInSeconds, queryString);
		String authorization = ocrSign.getAuthorization();

		OCRHttpRequest bfrReq = new OCRHttpRequest(requestMethod,
				OCRConfig.host, uri, authorization, queryString,httpBody);
		bfrReq.sendRequest();			
	}
	public void recognizeCharacter(String base64Str,String language) throws Exception{
//		POST /v{version}/recognize/character HTTP/1.1
		String requestMethod = new String("POST");
		String uri = new String("/v1/recognize/character");
		String queryString = new String("");			
		String httpBody = new String("{\"base64\":\""+base64Str+"\""+","+"\"language\":\""+language+"\"}");
		OCRSign ocrSign = new OCRSign(requestMethod, OCRConfig.host, uri,
				OCRConfig.expiresInSeconds, queryString);
		String authorization = ocrSign.getAuthorization();

		OCRHttpRequest bfrReq = new OCRHttpRequest(requestMethod,
				OCRConfig.host, uri, authorization, queryString,httpBody);
		bfrReq.sendRequest();	
		
	}
}
