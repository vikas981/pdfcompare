 package com.bean;

public class BeanClass {
	private String pdftext1;  
	private String pdftext2;
	private int number; 
	
	
	

	public BeanClass(String pdftext1,int number) {
		super(); 
		this.pdftext1 = pdftext1;
		this.number = number ;
		
	}
	public BeanClass(String pdftext2) {
		super();
		this.pdftext2 = pdftext2;
		
	}
	public String getPdftext1() {
		return pdftext1;
	}

	public void setPdftext1(String pdftext1) {
		this.pdftext1 = pdftext1;
	}

	public String getPdftext2() {
		return pdftext2;
	}

	public void setPdftext2(String pdftext2) {
		this.pdftext2 = pdftext2;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	

}
