package com.baidu.cloudservice.bfr;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class BFRMain {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

//		工程（App）
		BFRApp bfrApp = new BFRApp();
		bfrApp.listAllApp();
//		bfrApp.createApp();
		
//		组（Group）
		String appId = new String("40004");
		String groupName = new String("jobsgroup");
		BFRGroup bfrGroup = new BFRGroup();	
//		bfrGroup.deleteGroup(appId,groupName);		
//		bfrGroup.createGroup(appId, groupName);
		bfrGroup.searchGroup(appId, groupName);
		bfrGroup.listGroups(appId);

// 成员（Person）		
		List <String>bosPath = new ArrayList<String>();
		bosPath.add("yerik-bfr/1.jpg");
		bosPath.add("yerik-bfr/2.jpg");		
		bosPath.add("yerik-bfr/3.jpg");		
		bosPath.add("yerik-bfr/4.jpg");		
		bosPath.add("yerik-bfr/5.jpg");
		bosPath.add("yerik-bfr/6.jpg");		
		bosPath.add("yerik-bfr/7.jpg");		
		bosPath.add("yerik-bfr/8.jpg");
		
		
		BFRPerson bfrPerson = new BFRPerson();
//		创建Person
		String personName = new String("Jobs");
//		bfrPerson.createPerson(appId, groupName, personName, bosPath);

//		修改Person
//		bosPath.remove("yerik-bfr/7.jpg");
//		bosPath.remove("yerik-bfr/8.jpg");
//		bfrPerson.modifyPerson(appId, personName, bosPath);
//		
//		查找Person
//		bfrPerson.searchPerson(appId, personName);
		
//		列出工程下所有Person
//		bfrPerson.listPersons(appId);
		
//		按组列出所有Person
//		bfrPerson.listByGroup(appId, groupName);
		
//删除Person
//		bfrPerson.deletePerson(appId, personName);

		
//		人脸识别
		BFRVerify bfrVerify = new BFRVerify();
//		通过BOS Path识别		
		bfrVerify.verifyFromBosFile(appId, personName,"yerik-bfr/test1.jpg" );		
		bfrVerify.identifyFromBosFile(appId, groupName, "yerik-bfr/test1.jpg");
	
//通过BASE64识别
		String base64Str = BFRBase64.GetImageStr(new File("/Users/Yerik/Downloads/test1.jpg"));
		bfrVerify.verifyFromBase64(appId, personName, base64Str);	
		bfrVerify.identifyFromBase64(appId, groupName, base64Str);
	}

}
