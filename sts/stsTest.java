package com.baidu.cloudservice.sts;

import org.json.JSONArray;
import org.json.JSONObject;

import com.baidubce.BceClientConfiguration;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.auth.DefaultBceSessionCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.baidubce.services.bos.model.BosObjectSummary;
import com.baidubce.services.bos.model.ListObjectsResponse;
import com.baidubce.services.sts.StsClient;
import com.baidubce.services.sts.model.GetSessionTokenRequest;
import com.baidubce.services.sts.model.GetSessionTokenResponse;

public class stsTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String ACCESS_KEY_ID = "";
		String SECRET_ACCESS_KEY = "";
		
		BceClientConfiguration config = new BceClientConfiguration();
		config.setEndpoint("http://sts.bj.baidubce.com");
		config.setCredentials(new DefaultBceCredentials(ACCESS_KEY_ID,SECRET_ACCESS_KEY));
		StsClient stsClient = new StsClient(config);
		
		/*构造STS HTTP请求Json体
		* 
		 {
		 	"id": "testACL",
		 	"accessControlList": [
		    		{
		      		"eid": "eid01",
		      		"service": "bce:bos",
		      		"region": "bj",
		      		"effect": "Allow",
		      		"resource": [ "yerikbucket007", "yerikbucket008"],
		      		"permission": ["READ","LIST"]
		    		}
		  	]
		}
		 */
		
		
		
		JSONObject stsBody = new JSONObject();
		JSONArray accessControlList = new JSONArray();
		
		JSONObject acl = new JSONObject();
		acl.put("eid", "eid01");
		acl.put("service", "bce:bos");
		acl.put("region", "bj");
		acl.put("effect", "Allow");
		
		JSONArray bucketList = new JSONArray();
		bucketList.put("yerikbucket007");
		bucketList.put("yerikbucket008");
		acl.put("resource", bucketList);

		JSONArray permissionList = new JSONArray();
		permissionList.put("READ");
		permissionList.put("LIST");
		acl.put("permission", permissionList);
		
		accessControlList.put(acl);
		
		stsBody.put("id","testACL");
		stsBody.put("accessControlList",accessControlList);
		
		
		//发送请求STS 临时AK、SK以及Token
		GetSessionTokenRequest request = new GetSessionTokenRequest();
		request.setAcl(stsBody.toString());
		request.setDurationSeconds(3600);
		GetSessionTokenResponse stsCredentials = stsClient.getSessionToken(request);
		System.out.println(stsCredentials.toString());
		
		
//		BOS测试STS验证；
		
		BosClientConfiguration bosconfig = new BosClientConfiguration();    // 初始化一个带有STS验证的BosClient
		bosconfig.setEndpoint("http://bj.bcebos.com");
		bosconfig.setCredentials(new DefaultBceSessionCredentials(stsCredentials.getAccessKeyId(),stsCredentials.getSecretAccessKey(), stsCredentials.getSessionToken()));    //STS返回的临时AK/SK及Token
		BosClient bosClient = new BosClient(bosconfig);
		
//		测试LIST Bucket权限
		
		ListObjectsResponse listObjResp = bosClient.listObjects("yerikbucket007");
		for (BosObjectSummary objectSummary : listObjResp.getContents()) {
	        System.out.println("ObjectKey: " + objectSummary.getKey());
	    }	

	}

}
