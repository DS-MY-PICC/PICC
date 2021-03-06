package com.hp.hppicc.httpPostGet;

import java.util.List;

import android.util.Log;

public class HttpPostGet {
	
	private String pageSize;
	private String paperType;
	private String imageResolution;
	private String printMode;
	private int printVolume;
	private int printPeriod;
	
	private String httpLink;
	
	public HttpPostGet()
	{
		
	}

	public String getHttpLink() {
		return httpLink;
	}



	public void setHttpLink() {
//		this.httpLink = ;
	}



	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		if(pageSize.toLowerCase().equals("actual size"))
			this.pageSize = "-1";
		else
		{
			this.pageSize = pageSize.substring(1);
		}
		Log.d("http", "http pageSize " + this.pageSize);
	}

	public String getPaperType() {
		return paperType;
	}

	public void setPaperType(String paperType) {
		this.paperType = paperType;
		Log.d("http", "http paperType " + this.paperType);
	}

	public String getImageResolution() {
		return this.imageResolution;
	}

	public void setImageResolution(String imageResolution) {
		String[] ir = imageResolution.split(" ");
		this.imageResolution = ir[0];
		Log.d("http", "http imageResolution " + this.imageResolution);
	}

	public String getPrintMode() {
		return printMode;
	}

	public void setPrintMode(String printMode) {
		this.printMode = printMode.toLowerCase();
		Log.d("http", "http printType " + this.printMode);
	}

	public int getPrintVolume() {
		return printVolume;
	}

	public void setPrintVolume(int printVolume) {
		this.printVolume = printVolume;
		Log.d("http", "http printVolume " + this.printVolume);
	}

	public int getPrintPeriod() {
		return printPeriod;
	}

	public void setPrintPeriod(String printPeriod) {
		String days = printPeriod.split(" ")[0];
		this.printPeriod = Integer.parseInt(days);
		Log.d("http", "http printPeriod " + this.printPeriod);
	}
	
	
}
