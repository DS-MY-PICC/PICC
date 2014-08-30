/**
 * 
 */
package com.hp.hppicc.util;

import java.util.HashMap;

import org.json.JSONObject;

/**
 * @author yiptu
 *
 */
public final class PrintersUtil {

	private static PrintersUtil instance = null;
	
	// Define static elements
	private static HashMap<String, Object> printerList = new HashMap<String, Object>();
	private static HashMap<String, String> printerImages = new HashMap<String, String>();
	
	protected PrintersUtil(){
		// do nothing. 
	}
	
	public static PrintersUtil getInstance(){
		if(instance == null) {
	         instance = new PrintersUtil();
	      }
	      return instance;
	}
	
	public static void addPrinter(String key, Object value){
		printerList.put(key, value);
	}
	
	public static boolean printerExist(String key){
		if(printerList.containsKey(key)){
			return true;
		}
		return false;
	}
	
	public static Object getPrinter(String key){
		Object obj = null;		
		if(printerExist(key)){
			obj = printerList.get(key);
		}
		return obj;		
	}
	
	public static boolean printerListIsEmpty(){
		if(printerList == null || printerList.isEmpty()){
			return true;
		}
		return false;
	}
	
	public static void addPrinterImage(String key, String imgUrl){
		printerImages.put(key, imgUrl);
	}
	
	public static String getPrinterImagePath(String key){
		if(printerImages.containsKey(key)){
			return printerImages.get(key).toString();				
		}
		return "";
	}
	
	public static HashMap<String, Object> getPrinterList(){
		return printerList;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PrintersUtil pu = PrintersUtil.getInstance();
		if(pu.printerListIsEmpty()){
			pu.addPrinter("1", new JSONObject()); // put your printer jason object
			pu.addPrinterImage("1", "xxx/xxx/xxx.png");
		}
		
		System.out.println("PrinterList Size: " + pu.getPrinterList().size());
		System.out.println("Printer Object: " + pu.getPrinter("1"));
		System.out.println("Printer Image Path: " + pu.getPrinterImagePath("1"));

	}

}
