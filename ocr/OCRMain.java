package com.baidu.cloudservice.ocr;

import java.io.File;

import com.baidu.cloudservice.bfr.BFRBase64;

public class OCRMain {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		String base64Str = OCRBase64.GetImageStr(new File("/Users/Yerik/Downloads/1.jpg"));
		String language = new String("CHN_ENG");
		OCRRecognize ocrRecoginize = new OCRRecognize();
		ocrRecoginize.recognizeText(base64Str, language);
		ocrRecoginize.recognizeLine(base64Str, language);
		ocrRecoginize.recognizeCharacter(base64Str, language);
	}

}
