package com.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.bean.BeanClass;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import io.github.jonathanlink.PDFLayoutTextStripper;

public class PdfUtil {
	static List<BeanClass> bean=new ArrayList<>();
	static List<BeanClass> bean1=new ArrayList<>();
	static List<BeanClass> fileList=new ArrayList<>();
	static List<String> fileName=new ArrayList<>();
	static boolean isDiff=false;
	public static void txtToHtml(String value1) throws Exception {
		String[] arr1 =value1.split("\\n");
		for(int i=0;i<arr1.length;i++) {
			bean1.add(new BeanClass(arr1[i]));
		}
	}
	public static String getTextFromPDF(String fileName) throws IOException {
		String string = null;
		try {
			PDFParser pdfParser = new PDFParser(new RandomAccessFile(new File(fileName), "r"));
			pdfParser.parse();
			PDDocument pdDocument = new PDDocument(pdfParser.getDocument());
			PDFTextStripper pdfTextStripper = new PDFLayoutTextStripper();
			string = pdfTextStripper.getText(pdDocument);
		} catch (FileNotFoundException e) {
			PrintData.printErrorString(e.getClass().getSimpleName());
		} catch (IOException e) {
			PrintData.printErrorString(e.getClass().getSimpleName());
		};
		return string;
	}
	public String pdfToTxt(String string) throws IOException {
		StringBuilder builder1=new StringBuilder();
		for(String str: getTextFromPDF(string).split("\n")) {
			if(!str.replaceAll("\\s\\s", "").isEmpty() && !str.contains("Powered"))
			{
				builder1.append(str);
				builder1.append("\n");
			}
		}
		return builder1.toString();
	}
	public void DSS(String value1,String value2,String value3) throws Exception {
		PdfUtil util=new PdfUtil();
		String[] arr1 =value1.split("\\n");
		String[] arr2 =value2.split("\\n");
		List<String> list1 = new ArrayList<>();
		List<String> list2 = new ArrayList<>();

		for(int i=0;i<arr1.length;i++) {
			list1.add(arr1[i]);
		}
		for(int i=0;i<arr2.length;i++) {
			list2.add(arr2[i]);	
		}
		int count=0; 
		if(list2.size()>=list1.size()) {
			for(int i=0;i<list2.size();i++) {
				if(!list2.contains(list1.get(i))) {
					bean.add(new BeanClass("<mark>"+list1.get(i)+"</mark>",count));
					count++;
					isDiff=true;
				} 
				else {
					bean.add(new BeanClass(list1.get(i),count));
					count++;
				}
			}
		}else {
			for(int i=0;i<list1.size();i++) {
				if(!list2.contains(list1.get(i))) {
					bean.add(new BeanClass("<mark>"+list1.get(i)+"</mark>",count));
					count++;
					isDiff=true;
				} 
				else {
					bean.add(new BeanClass(list1.get(i),count));
					count++;
				}
			}
		}
	
		txtToHtml(pdfToTxt(System.getProperty("user.dir")+"/Folder2/"+value3));
		String extension=value3.substring(value3.indexOf("."));
		String file=value3.replace(extension, "").replace(" ", "");
		if(isDiff==true) {
			createDirectory();
			util.writeToHtml("Result/"+file+".html","template.mustache");	
			isDiff=false;
		}
		
		bean.clear();
		bean1.clear();
	}
	public void writeToHtml(String name,String mustacheFile) throws IOException {
		MustacheFactory mf = new DefaultMustacheFactory();
		Mustache mustache = mf.compile(mustacheFile);
		mustache.execute(new PrintWriter(new FileWriter(name,false)), new PdfUtil()).flush();
		System.out.println("done");
		bean.clear();
		bean1.clear();
	}
	public void createDirectory() {
		File file=new File(System.getProperty("user.dir")+"/Result");
		if(file.exists()) {
			return;
		}else {
			file.mkdir();
		}
	}
	public void convertToHtml() throws Exception {
		StringBuilder builder=new StringBuilder();
		String path=System.getProperty("user.dir")+"/Result/";
		int size=FileUtils.ListOfFileNames(path).size();
		System.out.println(size);
		System.out.println(FileUtils.ListOfFileNames(path).get(0));
		
		BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter("Result.html"));
		builder.append("<!DOCTYPE html>\n" + 
				"<html lang=\"en\">\n" + 
				"<head>\n" + 
				"	<title>PDF COMPARE REPORT</title>\n" + 
				"	<meta charset=\"UTF-8\">\n" + 
				"	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" + 
				"<!--===============================================================================================-->	\n" + 
				"	<link rel=\"icon\" type=\"image/png\" href=\"images/icons/favicon.ico\"/>\n" + 
				"<!--===============================================================================================-->\n" + 
				"	<link rel=\"stylesheet\" type=\"text/css\" href=\"vendor/bootstrap/css/bootstrap.min.css\">\n" + 
				"<!--===============================================================================================-->\n" + 
				"	<link rel=\"stylesheet\" type=\"text/css\" href=\"fonts/font-awesome-4.7.0/css/font-awesome.min.css\">\n" + 
				"<!--===============================================================================================-->\n" + 
				"	<link rel=\"stylesheet\" type=\"text/css\" href=\"vendor/animate/animate.css\">\n" + 
				"<!--===============================================================================================-->\n" + 
				"	<link rel=\"stylesheet\" type=\"text/css\" href=\"vendor/select2/select2.min.css\">\n" + 
				"<!--===============================================================================================-->\n" + 
				"	<link rel=\"stylesheet\" type=\"text/css\" href=\"vendor/perfect-scrollbar/perfect-scrollbar.css\">\n" + 
				"<!--===============================================================================================-->\n" + 
				"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/util.css\">\n" + 
				"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/main.css\">\n" + 
				"<!--===============================================================================================-->\n" + 
				"</head>\n" + 
				"<body>\n" + 
				"<h2 id='header'>"+"PDF REPORT COMPARE"+"</h2>\n" +
				"	\n" + 
				"	<div class=\"limiter\">\n" + 
				"		<div class=\"container-table100\">\n" + 
				"			<div class=\"wrap-table100\">\n" + 
				"				<div class=\"table100 ver1 m-b-110\">\n" + 
				"					<div class=\"table100-head\">\n" + 
				"						<table>\n" + 
				"							<thead>\n" + 
				"								<tr class=\"row100 head\">\n" + 
				"									<th class=\"cell100 column1\">PDF Name</th>\n" + 
				"									<th class=\"cell100 column2\">REPORT URL</th>\n" + 
				"								</tr>\n" + 
				"							</thead>\n" + 
				"						</table>\n" + 
				"					</div>\n" + 
				"\n" + 
				"					<div class=\"table100-body js-pscroll\">\n" + 
				"						<table>\n" + 
				"							<tbody>");
		for(int i=0;i<size;i++) {
			builder.append("<tr class=\"row100 body\">\n" + 
					"            <td class=\"cell100 column1\">"+FileUtils.ListOfFileNames(path).get(i)+"</td>\n" + 
					"            <td class=\"cell100 column2\"><a href="+path+FileUtils.ListOfFileNames(path).get(i)+" "+"target"+"="+"_blank"+">"+"CLICK HERE"+"</a></td>\n" + 
					"         </tr>");
		}
		builder.append("\n" + 
				"							</tbody>\n" + 
				"						</table>\n" + 
				"					</div>\n" + 
				"				</div>\n" + 
				"			</div>\n" + 
				"		</div>\n" + 
				"	</div>\n" + 
				"\n" + 
				"\n" + 
				"<!--===============================================================================================-->	\n" + 
				"	<script src=\"vendor/jquery/jquery-3.2.1.min.js\"></script>\n" + 
				"<!--===============================================================================================-->\n" + 
				"	<script src=\"vendor/bootstrap/js/popper.js\"></script>\n" + 
				"	<script src=\"vendor/bootstrap/js/bootstrap.min.js\"></script>\n" + 
				"<!--===============================================================================================-->\n" + 
				"	<script src=\"vendor/select2/select2.min.js\"></script>\n" + 
				"<!--===============================================================================================-->\n" + 
				"	<script src=\"vendor/perfect-scrollbar/perfect-scrollbar.min.js\"></script>\n" + 
				"	<script>\n" + 
				"		$('.js-pscroll').each(function(){\n" + 
				"			var ps = new PerfectScrollbar(this);\n" + 
				"\n" + 
				"			$(window).on('resize', function(){\n" + 
				"				ps.update();\n" + 
				"			})\n" + 
				"		});\n" + 
				"			\n" + 
				"		\n" + 
				"	</script>\n" + 
				"<!--===============================================================================================-->\n" + 
				"	<script src=\"js/main.js\"></script>\n" + 
				"\n" + 
				"</body>\n" + 
				"</html>");
		bufferedWriter.append(builder);
		bufferedWriter.flush();
		bufferedWriter.close();
		
	}
}