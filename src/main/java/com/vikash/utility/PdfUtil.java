package com.vikash.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.vikash.constants.PDFConstant;
import com.vikash.model.BeanClass;
import com.vikash.model.HtmlReport;

import io.github.jonathanlink.PDFLayoutTextStripper;

public class PdfUtil {
	private PdfUtil() {}
	static List<BeanClass> bean=new ArrayList<>();
	static List<HtmlReport> htmlReport=new ArrayList<>();
	static List<BeanClass> textValue=new ArrayList<>();
	static List<BeanClass> fileList=new ArrayList<>();
	static List<String> fileName=new ArrayList<>();
	static List<String> missingFiles=new ArrayList<>();
	public static boolean isDiff=false;



	public static void txtToHtml(String value1) {
		String[] arr1 =value1.split("\\n");
		for(int i=0;i<arr1.length;i++) {
			textValue.add(new BeanClass(arr1[i]));
		}
	}
	public static String getTextFromPDF(String fileName) throws IOException {
		StringBuilder builder=new StringBuilder();
		try {
			PDFParser pdfParser = new PDFParser(new RandomAccessFile(new File(fileName), "r"));
			pdfParser.parse();
			PDDocument pdDocument = new PDDocument(pdfParser.getDocument());
			PDFTextStripper pdfTextStripper = new PDFLayoutTextStripper();
			builder.append(pdfTextStripper.getText(pdDocument));
			pdDocument.close();
		} catch (FileNotFoundException e) {
			missingFiles.add(fileName);
			System.out.println("May same pdf not present");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
	public static String pdfToTxt(String string) throws IOException {
		StringBuilder builder=new StringBuilder();
		for(String str: getTextFromPDF(string).split("\n")) {
			if(!str.replaceAll("\\s\\s", "").isEmpty() && !str.contains("Powered"))
			{
				builder.append(str);
				builder.append("\n");
			}
		}
		return builder.toString();
	}
	
	
	public static void textToHtml(String filename1,String filename2,String file,String folderName) throws Exception {
		List<String> arrayList1 = new ArrayList<>();
		List<String> arrayList2 = new ArrayList<>();
		arrayList1.addAll(Arrays.asList(filename1.split("\\n")));
		arrayList2.addAll(Arrays.asList(filename2.split("\\n")));
		int count=0; 
		if(arrayList1.size()>arrayList2.size()) {
			for(String textLine:arrayList1) {
				if(!arrayList2.contains(textLine)) {
					bean.add(new BeanClass("<span style=\"background-color:#ffcccb\">"+textLine+"</span>",count));
					++count;
					isDiff=true;
				} 
				else {
					bean.add(new BeanClass(textLine,count));
					++count;
				}
			}
		}
		if(arrayList1.size()==arrayList2.size()) {
			for(String textLine:arrayList1) {
				if(!arrayList2.contains(textLine)) {
					bean.add(new BeanClass("<span style=\"background-color:#ffcccb\">"+textLine+"</span>",count));
					++count;
					isDiff=true;
				} 
				else {
					bean.add(new BeanClass(textLine,count));
					++count;
				}
			}
		}

		else {
			for(String textLine:arrayList2) {
				if(!arrayList1.contains(textLine)) {
					bean.add(new BeanClass("<span style=\"background: #ffcccb\">"+textLine+"</span>",count));
					count++;
					isDiff=true;
				} 
				else {
					bean.add(new BeanClass(textLine,count));
					count++;
				}
			}
		}

		txtToHtml(pdfToTxt(folderName+PDFConstant.FORWARD_SLASH+file));
		String extension=file.substring(file.indexOf("."));
		String fileName=file.replace(extension, "").replace(" ", "");
		if(isDiff) {
			writeToHtml(PDFConstant.RESULT_PATH+PDFConstant.FORWARD_SLASH+fileName+".html","template.mustache");	
			isDiff=false;
		}
		bean.clear();
		textValue.clear();
	}
	public static void writeToHtml(String name,String mustacheFile){
		try {
			MustacheFactory mf = new DefaultMustacheFactory();
			Mustache mustache = mf.compile(mustacheFile);
			mustache.execute(new PrintWriter(new FileWriter(name,false)), new PdfUtil()).flush();
			bean.clear();
			textValue.clear();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void convertToHtml() {
		String resultPath=PDFConstant.RESULT_PATH;
		int size=FileUtils.getFileName(resultPath).size();
		if(size>0){
			for (String fileName:FileUtils.getFileName(resultPath)){
				htmlReport.add(new HtmlReport(resultPath+PDFConstant.FORWARD_SLASH+fileName));
			}
		}
		writeToHtml("Result.html","html.mustache");
	}
}