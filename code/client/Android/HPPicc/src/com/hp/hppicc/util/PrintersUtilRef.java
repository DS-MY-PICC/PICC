/**
 * 
 */
package com.hp.hppicc.util;

import java.util.HashMap;

import org.json.JSONObject;

import com.hp.hppicc.R;
import com.hp.hppicc.dataDefinition.ResultData;

/**
 * @author yiptu
 *
 */
public final class PrintersUtilRef {

	private static PrintersUtilRef instance = null;
	
	// Define static elements
	private static HashMap<Integer, Object> printerList = new HashMap<Integer, Object>();
	private static HashMap<Integer, ResultData> printerResultData = new HashMap<Integer, ResultData>();
	private static HashMap<Integer, String> printerImages = new HashMap<Integer, String>();
	private static HashMap<Integer, Integer> printerIds = new HashMap<Integer, Integer>();
	private static HashMap<Integer, String> printerNames = new HashMap<Integer, String>();
	
	private static String imageResolution;
	private static String pageSize;
	private static String printMode;
	private static String imagePath;
	private static String selectedPrinter;
	private static int printVolume;
	private static int printPeriod;
	private static int selectedPrinterId;
	private static int selectedPrinterImage;
	
	public static final int[] imageId = {
			R.drawable.oofficejet6600,
			R.drawable.tphotosmart7520,
			R.drawable.thepsonwf3520,
			R.drawable.focanonmg8220,
			R.drawable.fidesignjet30,
			R.drawable.silaserjetcm6040
	};
	
	public static void addPrinterResultData(int key, ResultData value)
	{
		PrintersUtilRef.printerResultData.put(key, value);
	}
	
	public static int getPrintVolume() {
		return printVolume;
	}



	public static void setPrintVolume(int printVolume) {
		PrintersUtilRef.printVolume = printVolume;
	}



	public static int getPrintPeriod() {
		return printPeriod;
	}



	public static void setPrintPeriod(int printPeriod) {
		PrintersUtilRef.printPeriod = printPeriod;
	}



	public static ResultData getPrinterResultData(int key)
	{
		return printerResultData.get(key);
	}
	
	public static String getImageResolution() {
		return imageResolution;
	}

	public static void setImageResolution(String imageResolution) {
		PrintersUtilRef.imageResolution = imageResolution;
	}

	public static String getPageSize() {
		return pageSize;
	}

	public static void setPageSize(String pageSize) {
		PrintersUtilRef.pageSize = pageSize;
	}

	public static String getPrintMode() {
		return printMode;
	}

	public static void setPrintMode(String printMode) {
		PrintersUtilRef.printMode = printMode;
	}

	public void setSelectedPrinterImage(int selectedPrinterImage)
	{
		PrintersUtilRef.selectedPrinterImage = selectedPrinterImage;
	}
	
	public int getSelectedPrinterImage()
	{
		return selectedPrinterImage;
	}
	
	public void setImagePath(String imagePath)
	{
		PrintersUtilRef.imagePath = imagePath;
	}
	
	public static String getImagePath()
	{
		return imagePath;
	}
	
	public void setSelectedPrinter(String selectedPrinter)
	{
		PrintersUtilRef.selectedPrinter = selectedPrinter;
	}
	
	public String getSelectedPrinter()
	{
		return selectedPrinter;
	}
	
	public static void setSelectedPrinterId(int selectedPrinterId)
	{
		PrintersUtilRef.selectedPrinterId = selectedPrinterId;
	}
	
	public static int getSelectedPrinterId()
	{
		return selectedPrinterId;
	}
	
	
	protected PrintersUtilRef(){
		// do nothing. 
	}
	
	public static PrintersUtilRef getInstance(){
		if(instance == null) {
	         instance = new PrintersUtilRef();
	      }
	      return instance;
	}
	
	public static void addPrinterIds(int key, int printerId)
	{
		printerIds.put(key, printerId);
	}
	
	public static int getPrinterIds(int key){
		if(printerIds.containsKey(key)){
			return printerIds.get(key);				
		}
		return -1;
	}
	
	public static void addPrinter(int key, Object value){
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
	
	public static void removePrinterLists(int key)
	{
		printerList.remove(key);
	}
	
	public static void addPrinterImage(int key, String imgUrl){
		printerImages.put(key, imgUrl);
	}
	
	public static void removePrinterName(int key) {
		printerNames.remove(key);
	}
	
	public static void removePrinterId(int key)
	{
		printerIds.remove(key);
	}
	
	public static void addPrinterName(int key, String printerName)
	{
		printerNames.put(key, printerName);
	}
	
	public static String getPrinterName(int key){
		if(printerNames.containsKey(key)){
			return printerNames.get(key);				
		}
		return "";
	}
	
	public static HashMap<Integer, String> getPrinterNames(){
		return printerNames;
	}
	
	public static String getPrinterImagePath(String key){
		if(printerImages.containsKey(key)){
			return printerImages.get(key).toString();				
		}
		return "";
	}
	
	public static HashMap<Integer, Object> getPrinterList(){
		return printerList;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PrintersUtilRef pu = PrintersUtilRef.getInstance();
		if(pu.printerListIsEmpty()){
			pu.addPrinter(1, new JSONObject()); // put your printer jason object
			pu.addPrinterImage(1, "xxx/xxx/xxx.png");
		}
		
		System.out.println("PrinterList Size: " + pu.getPrinterList().size());
		System.out.println("Printer Object: " + pu.getPrinter("1"));
		System.out.println("Printer Image Path: " + pu.getPrinterImagePath("1"));

	}

}

