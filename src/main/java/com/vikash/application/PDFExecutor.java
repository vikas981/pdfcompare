package com.vikash.application;
import java.util.List;
import java.util.Properties;

import com.vikash.constants.PDFConstant;
import com.vikash.utility.FileUtils;
import com.vikash.utility.PdfUtil;

public class PDFExecutor {
	public static void main(String[] args) throws Exception {
		
		FileUtils.deleteAllHtml();
		FileUtils.createDirectory();
		Properties prop=FileUtils.getData("config.properties");
		String folder1=prop.getProperty("FOLDERNAME1");
		String folder2=prop.getProperty("FOLDERNAME2");

		List<String> files1=FileUtils.getFileName(folder1);
		List<String> files2=FileUtils.getFileName(folder2);
		try {
			if(files1.size()>files2.size()) {
				for(String fileName:files1) {
					if(files2.contains(fileName)) {
						PdfUtil.textToHtml(PdfUtil.pdfToTxt(folder1+PDFConstant.FORWARD_SLASH+fileName),
								PdfUtil.pdfToTxt(folder2+PDFConstant.FORWARD_SLASH+fileName),folder1+PDFConstant.FORWARD_SLASH+fileName,folder2);	
					}
				}
			}else {
				for(String fileName:files2) {
					if(files1.contains(fileName)) {
						PdfUtil.textToHtml(PdfUtil.pdfToTxt(folder1+PDFConstant.FORWARD_SLASH+fileName),
								PdfUtil.pdfToTxt(folder2+PDFConstant.FORWARD_SLASH+fileName),fileName,folder1);
					
					}
				}
			}
		}catch (Exception e) {
			e.getMessage();
		}
		PdfUtil.convertToHtml();
		
	}
}

