package com.hp.hppicc.dataDefinition;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class PrinterData implements Parcelable{
	private int id;
	private String brand;
	private String type;
	private String title;
	private String image;
	private double price;
	private String url;
	private String ppi;
	private String functions;
	private String iccFile;
	private double printSpeedBlack;
	private double printSpeedColor;
	private int maxInputCapacity;
	private int maxMonthlyDutyCycle;
	private String autoDuplex;
	private double inkCyanPrice;
	private int inkCyanYield;
	private double inkMagentaPrice;
	private int inkMagentaYield;
	private double inkYellowPrice;
	private int inkYellowYield;
	private double inkBlackPrice;
	private int inkBlackYield;
	
	public PrinterData()
	{
		
	}
	
	public PrinterData(JSONObject jsonPrinter) {
		try {
			this.id = jsonPrinter.getInt("id");
			this.brand = jsonPrinter.getString("brand");
			this.type = jsonPrinter.getString("type");
			this.title = jsonPrinter.getString("title");
			this.image = jsonPrinter.getString("image");
			this.price = jsonPrinter.getDouble("price");
			this.url = jsonPrinter.getString("url");
			this.ppi = jsonPrinter.getString("ppi");
			this.functions = jsonPrinter.getString("functions");
			this.iccFile = jsonPrinter.getString("iccFile");
			this.printSpeedBlack = jsonPrinter.getDouble("printSpeedBlack");
			this.printSpeedColor = jsonPrinter.getDouble("printSpeedColor");
			this.maxInputCapacity = jsonPrinter.getInt("maxInputCapacity");
			this.maxMonthlyDutyCycle = jsonPrinter.getInt("maxMonthlyDutyCycle");
			this.autoDuplex = jsonPrinter.getString("autoDuplex");
			this.inkCyanPrice = jsonPrinter.getDouble("inkCyanPrice");
			this.inkCyanYield = jsonPrinter.getInt("inkCyanYield");
			this.inkMagentaPrice = jsonPrinter.getDouble("inkMagentaPrice");
			this.inkMagentaYield = jsonPrinter.getInt("inkMagentaYield");
			this.inkYellowPrice = jsonPrinter.getDouble("inkYellowPrice");
			this.inkYellowYield = jsonPrinter.getInt("inkYellowYield");
			this.inkBlackPrice = jsonPrinter.getDouble("inkBlackPrice");
			this.inkBlackYield = jsonPrinter.getInt("inkBlackYield");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public int getId() {
		return id;
	}

	public String getBrand() {
		return brand;
	}

	public String getType() {
		return type;
	}

	public String getTitle() {
		return title;
	}

	public String getImage() {
		return image;
	}

	public double getPrice() {
		return price;
	}

	public String getUrl() {
		return url;
	}

	public String getPpi() {
		return ppi;
	}

	public String getFunctions() {
		return functions;
	}

	public String getIccFile() {
		return iccFile;
	}

	public double getPrintSpeedBlack() {
		return printSpeedBlack;
	}

	public double getPrintSpeedColor() {
		return printSpeedColor;
	}

	public int getMaxInputCapacity() {
		return maxInputCapacity;
	}

	public int getMaxMonthlyDutyCycle() {
		return maxMonthlyDutyCycle;
	}

	public String getAutoDuplex() {
		return autoDuplex;
	}

	public double getInkCyanPrice() {
		return inkCyanPrice;
	}

	public int getInkCyanYield() {
		return inkCyanYield;
	}

	public double getInkMagentaPrice() {
		return inkMagentaPrice;
	}

	public int getInkMagentaYield() {
		return inkMagentaYield;
	}

	public double getInkYellowPrice() {
		return inkYellowPrice;
	}

	public int getInkYellowYield() {
		return inkYellowYield;
	}

	public double getInkBlackPrice() {
		return inkBlackPrice;
	}

	public int getInkBlackYield() {
		return inkBlackYield;
	}
	
	public static final Parcelable.Creator<PrinterData> CREATOR = new Creator<PrinterData>() {
		
		@Override
		public PrinterData[] newArray(int size) {
			// TODO Auto-generated method stub
			return new PrinterData[size];
		}
		
		@Override
		public PrinterData createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			PrinterData parcelPrinterData = new PrinterData();
			
			parcelPrinterData.id = source.readInt();
			parcelPrinterData.brand = source.readString();
			parcelPrinterData.type = source.readString();
			parcelPrinterData.title = source.readString();
			parcelPrinterData.image = source.readString();
			parcelPrinterData.price = source.readDouble();
			parcelPrinterData.url = source.readString();
			parcelPrinterData.ppi = source.readString();
			parcelPrinterData.functions = source.readString();
			parcelPrinterData.iccFile = source.readString();
			parcelPrinterData.printSpeedBlack = source.readDouble();
			parcelPrinterData.printSpeedColor = source.readDouble();
			parcelPrinterData.maxInputCapacity = source.readInt();
			parcelPrinterData.maxMonthlyDutyCycle = source.readInt();
			parcelPrinterData.autoDuplex = source.readString();
			parcelPrinterData.inkCyanPrice = source.readDouble();
			parcelPrinterData.inkCyanYield = source.readInt();
			parcelPrinterData.inkMagentaPrice = source.readDouble();
			parcelPrinterData.inkMagentaYield = source.readInt();
			parcelPrinterData.inkYellowPrice = source.readDouble();
			parcelPrinterData.inkYellowYield = source.readInt();
			parcelPrinterData.inkBlackPrice = source.readDouble();
			parcelPrinterData.inkBlackYield = source.readInt();
			
			return parcelPrinterData;
		}
	};

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(this.id);
		dest.writeString(this.brand);
		dest.writeString(this.type);
		dest.writeString(this.title);
		dest.writeString(this.image);
		dest.writeDouble(this.price);
		dest.writeString(this.url);
		dest.writeString(this.ppi);
		dest.writeString(this.functions);
		dest.writeString(this.iccFile);
		dest.writeDouble(this.printSpeedBlack);
		dest.writeDouble(this.printSpeedColor);
		dest.writeDouble(this.maxInputCapacity);
		dest.writeDouble(this.maxMonthlyDutyCycle);
		dest.writeString(this.autoDuplex);
		dest.writeDouble(this.inkCyanPrice);
		dest.writeInt(this.inkCyanYield);
		dest.writeDouble(this.inkMagentaPrice);
		dest.writeInt(this.inkMagentaYield);
		dest.writeDouble(this.inkYellowPrice);
		dest.writeInt(this.inkYellowYield);
		dest.writeDouble(this.inkBlackPrice);
		dest.writeInt(this.inkBlackYield);
	}
	
	
	
}
