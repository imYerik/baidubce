package com.baidu.cloudservice.bfr;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;

import sun.misc.BASE64Encoder;

public class BFRBase64 {

	public static String GetImageStr(File imgFile)  {
		
		ByteArrayOutputStream outputStream = null;
	    try {
	      BufferedImage bufferedImage = ImageIO.read(imgFile);
	      outputStream = new ByteArrayOutputStream();
	      ImageIO.write(bufferedImage, "jpg", outputStream);
	    } catch (MalformedURLException e1) {
	      e1.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    // 对字节数组Base64编码
	    BASE64Encoder encoder = new BASE64Encoder();
	 // 替换换行符（BCE不接受带换行符的BASE64编码），返回Base64编码过的字节数组字符	
	    return encoder.encode(outputStream.toByteArray()).replaceAll("(?:\\r\\n|\\n\\r|\\n|\\r)", "");	   
	}  	
}
