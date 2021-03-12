package com.application;
import java.io.File;
import java.util.Properties;

import com.util.FileUtils;
import com.util.PdfUtil;


public class Main {
	public static void main(String[] args) throws Exception {
		String path = System.getProperty("user.dir");
		PdfUtil util=new PdfUtil();
		Properties prop=new FileUtils().getData("config.properties");
		String folder1=prop.getProperty("FOLDERNAME1");
		String folder2=prop.getProperty("FOLDERNAME2");
		System.out.println(folder1);
		int sizeFolder=FileUtils.ListOfFileNames(path+"/"+"Folder2").size();
		int n=FileUtils.ListOfFileNames(path+"/"+"Folder1").size();
		System.out.println(sizeFolder+" "+n);
		for(int i=0;i<sizeFolder;i++) {
			util.DSS(util.pdfToTxt(path+"/"+folder1+"/"+FileUtils.ListOfFileNames(path+"/"+folder1).get(i)), util.pdfToTxt(path+"/"+folder2+"/"+FileUtils.ListOfFileNames(path+"/"+folder2).get(i)),FileUtils.ListOfFileNames(path+"/"+folder2).get(i));
			util.convertToHtml();
		}
		
	}
}

