package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class FileUtils {
	public static List<String> ListOfFileNames(String directoryPath){
		File fileObject=new File(directoryPath);
		File[] listOfFiles=fileObject.listFiles();
		Arrays.sort(listOfFiles);
		List<String> fileNames = new ArrayList<String>();
		for (int fileIndex = 0; fileIndex < listOfFiles.length; fileIndex++) {
			if (listOfFiles[fileIndex].isFile()) {
				fileNames.add(listOfFiles[fileIndex].getName());
			}
		}   
		return fileNames ;
	}
	public Properties getData(String fileName) throws Exception {
		FileInputStream fis = null;
		Properties prop = null;
		try {
			fis = new FileInputStream(fileName);
			prop = new Properties();
			prop.load(fis);
		} catch(FileNotFoundException fnfe) {
			fnfPrintData.printErrorString(e.getClass().getSimpleName());
		} catch(IOException ioe) {
			ioPrintData.printErrorString(e.getClass().getSimpleName());
		} finally {
			fis.close();
		}
		return prop;
	}
}
