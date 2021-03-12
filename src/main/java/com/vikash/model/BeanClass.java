 package com.vikash.model;

public class BeanClass {
	private String first;  
	private String second;
	private int number; 
	
	
	

	public BeanClass(String pdftext1,int number) {
		super(); 
		this.first = pdftext1;
		this.number = number ;
		
	}
	public BeanClass(String pdftext2) {
		super();
		this.second = pdftext2;
		
	}
	public String getFirst() {
		return first;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public String getSecond() {
		return second;
	}

	public void setPdftext2(String second) {
		this.second = second;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	

}
