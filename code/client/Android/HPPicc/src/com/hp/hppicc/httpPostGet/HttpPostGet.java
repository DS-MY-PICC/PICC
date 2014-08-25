package com.hp.hppicc.httpPostGet;

import android.util.Log;

public class HttpPostGet {
	
	private String pageSize;
	private String paperType;
	private String imageResolution;
	private String printMode;
	private String printVolume;
	private String printPeriod;
	
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
		this.pageSize = pageSize;
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
		this.imageResolution = imageResolution;
		Log.d("http", "http imageResolution " + this.imageResolution);
	}

	public String getPrintType() {
		return printMode;
	}

	public void setPrintMode(String printMode) {
		this.printMode = printMode;
		Log.d("http", "http printType " + this.printMode);
	}

	public String getPrintVolume() {
		return printVolume;
	}

	public void setPrintVolume(String printVolume) {
		this.printVolume = printVolume;
		Log.d("http", "http printVolume " + this.printVolume);
	}

	public String getPrintPeriod() {
		return printPeriod;
	}

	public void setPrintPeriod(String printPeriod) {
		this.printPeriod = printPeriod;
		Log.d("http", "http printPeriod " + this.printPeriod);
	}
	
	
}
