package com.vikash.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import com.vikash.constants.PDFConstant;

public class FileUtils {
	
	private FileUtils() {}
	
	public static List<String> getFileName(String directoryPath){
		File fileObject=new File(directoryPath);
		File[] listOfFiles=fileObject.listFiles();
		Arrays.sort(listOfFiles);
		List<String> fileNames = new ArrayList<>();
		for (int fileIndex = 0; fileIndex < listOfFiles.length; fileIndex++) {
			if (listOfFiles[fileIndex].isFile()) {
				fileNames.add(listOfFiles[fileIndex].getName());
			}
		}   
		return fileNames ;
	}
	public static Properties getData(String fileName){
		Properties prop = null;
		try (
			FileInputStream fis = new FileInputStream(fileName)){
			prop = new Properties();
			prop.load(fis);
		} catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} 
		return prop;
	}
	
	public static void createDirectory() {
		File file=new File(PDFConstant.RESULT_PATH);
		if(file.exists()) {
			System.out.println("File Already Exist");
		}else {
			file.mkdir();
		}
	}
	
	public static void deleteAllHtml() {
		File file=new File(PDFConstant.RESULT_PATH);
		Arrays.stream(file.listFiles((f, p) -> p.endsWith("html"))).forEach(File::delete);
	}
}
