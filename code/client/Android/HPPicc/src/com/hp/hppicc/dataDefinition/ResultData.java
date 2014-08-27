package com.hp.hppicc.dataDefinition;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class ResultData implements Parcelable {
	
	private int id;
	private String brand;
	private String type;
	private String title;
	private String image;
	private double price;
	private String url;
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
	
	private double cyanV;
	private double magentaV;
	private double yellowV;
	private double blackV;
	private double tcpp;
	
	private String paper;
	private String dpi;
	private String printer;
	private String mode;

	public ResultData ()
	{
		
	}
	
	public ResultData(JSONObject json)
	{
		try
		{
			JSONObject cmykResult = json.getJSONObject("result");
			this.cyanV = cmykResult.getDouble("cyan");
			this.magentaV = cmykResult.getDouble("magenta");
			this.yellowV = cmykResult.getDouble("yellow");
			this.blackV = cmykResult.getDouble("black");
			this.tcpp = cmykResult.getDouble("tcpp");
			
			JSONObject printerResult = json.getJSONObject("printer");
			this.id = printerResult.getInt("id");
			this.brand = printerResult.getString("brand");
			this.type = printerResult.getString("type");
			this.title = printerResult.getString("title");
			this.image = printerResult.getString("image");
			this.price = printerResult.getDouble("price");
			this.url = printerResult.getString("url");
			this.functions = printerResult.getString("functions");
			this.iccFile = printerResult.getString("icc_file");
			this.printSpeedBlack = printerResult.getDouble("print_speed_black");
			this.printSpeedColor = printerResult.getDouble("print_speed_color");
			this.maxInputCapacity = printerResult.getInt("max_input_capacity");
			this.maxMonthlyDutyCycle = printerResult.getInt("max_monthly_duty_cycle");
			this.autoDuplex = printerResult.getString("auto_duplex");
			this.inkCyanPrice = printerResult.getDouble("ink_cyan_price");
			this.inkCyanYield = printerResult.getInt("ink_cyan_yield");
			this.inkMagentaPrice = printerResult.getDouble("ink_magenta_price");
			this.inkMagentaYield = printerResult.getInt("ink_magenta_yield");
			this.inkYellowPrice = printerResult.getDouble("ink_yellow_price");
			this.inkYellowYield = printerResult.getInt("ink_yellow_yield");
			this.inkBlackPrice = printerResult.getDouble("ink_black_price");
			this.inkBlackYield = printerResult.getInt("ink_black_yield");
			
			JSONObject options = json.getJSONObject("options");
			this.paper = options.getString("paper");
			this.dpi = options.getString("dpi");
			this.printer = options.getString("printer");
			this.mode = options.getString("mode");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
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

	public double getCyanV() {
		return cyanV;
	}

	public double getMagentaV() {
		return magentaV;
	}

	public double getYellowV() {
		return yellowV;
	}

	public double getBlackV() {
		return blackV;
	}

	public double getTcpp() {
		return tcpp;
	}

	public String getPaper() {
		return paper;
	}

	public String getDpi() {
		return dpi;
	}

	public String getPrinter() {
		return printer;
	}

	public String getMode() {
		return mode;
	}

	Parcelable.Creator<ResultData> CREATOR = new Creator<ResultData>() {
		
		@Override
		public ResultData[] newArray(int size) {
			// TODO Auto-generated method stub
			return new ResultData[size];
		}
		
		@Override
		public ResultData createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			ResultData parcelDd = new ResultData();
			
			parcelDd.cyanV = source.readDouble();
			parcelDd.magentaV = source.readDouble();
			parcelDd.yellowV = source.readDouble();
			parcelDd.blackV = source.readDouble();
			parcelDd.tcpp = source.readDouble();
			
			parcelDd.id = source.readInt();
			parcelDd.brand = source.readString();
			parcelDd.type = source.readString();
			parcelDd.title = source.readString();
			parcelDd.image = source.readString();
			parcelDd.price = source.readDouble();
			parcelDd.url = source.readString();
			parcelDd.functions = source.readString();
			parcelDd.iccFile = source.readString();
			parcelDd.printSpeedBlack = source.readDouble();
			parcelDd.printSpeedColor = source.readDouble();
			parcelDd.maxInputCapacity = source.readInt();
			parcelDd.maxMonthlyDutyCycle = source.readInt();
			parcelDd.autoDuplex = source.readString();
			parcelDd.inkCyanPrice = source.readDouble();
			parcelDd.inkCyanYield = source.readInt();
			parcelDd.inkMagentaPrice = source.readDouble();
			parcelDd.inkMagentaYield = source.readInt();
			parcelDd.inkYellowPrice = source.readDouble();
			parcelDd.inkYellowYield = source.readInt();
			parcelDd.inkBlackPrice = source.readDouble();
			parcelDd.inkBlackYield = source.readInt();

			parcelDd.paper = source.readString();
			parcelDd.dpi = source.readString();
			parcelDd.printer = source.readString();
			parcelDd.mode = source.readString();
			
			return parcelDd;
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
		dest.writeDouble(this.cyanV);
		dest.writeDouble(this.magentaV);
		dest.writeDouble(this.yellowV);
		dest.writeDouble(this.blackV);
		dest.writeDouble(this.tcpp);
		
		dest.writeInt(this.id);
		dest.writeString(this.brand);
		dest.writeString(this.type);
		dest.writeString(this.title);
		dest.writeString(this.image);
		dest.writeDouble(this.price);
		dest.writeString(this.url);
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
		
		dest.writeString(this.paper);
		dest.writeString(this.dpi);
		dest.writeString(this.printer);
		dest.writeString(this.mode);
	}

}
