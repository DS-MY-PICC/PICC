package com.hp.hppicc.dataDefinition;

import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class DataDefinition implements Parcelable {
	
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
	
	public DataDefinition ()
	{
		
	}
	
	public DataDefinition(JSONObject json)
	{
		try
		{
			this.id = json.getInt("id");
			this.brand = json.getString("brand");
			this.type = json.getString("type");
			this.title = json.getString("title");
			this.image = json.getString("image");
			this.price = json.getDouble("price");
			this.url = json.getString("url");
			this.ppi = json.getString("ppi");
			this.functions = json.getString("functions");
			this.iccFile = json.getString("iccFile");
			this.printSpeedBlack = json.getDouble("printSpeedBlack");
			this.printSpeedColor = json.getDouble("printSpeedColor");
			this.maxInputCapacity = json.getInt("maxInputCapacity");
			this.maxMonthlyDutyCycle = json.getInt("maxMonthlyDutyCycle");
			this.autoDuplex = json.getString("autoDuplex");
			this.inkCyanPrice = json.getDouble("inkCyanPrice");
			this.inkCyanYield = json.getInt("inkCyanYield");
			this.inkMagentaPrice = json.getDouble("inkMagentaPrice");
			this.inkMagentaYield = json.getInt("inkMagentaYield");
			this.inkYellowPrice = json.getDouble("inkYellowPrice");
			this.inkYellowYield = json.getInt("inkYellowYield");
			this.inkBlackPrice = json.getDouble("inkBlackPrice");
			this.inkBlackYield = json.getInt("inkBlackYield");
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

	public Parcelable.Creator<DataDefinition> getCREATOR() {
		return CREATOR;
	}



	Parcelable.Creator<DataDefinition> CREATOR = new Creator<DataDefinition>() {
		
		@Override
		public DataDefinition[] newArray(int size) {
			// TODO Auto-generated method stub
			return new DataDefinition[size];
		}
		
		@Override
		public DataDefinition createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			DataDefinition dd = new DataDefinition();
			dd.id = source.readInt();
			dd.brand = source.readString();
			dd.type = source.readString();
			dd.title = source.readString();
			dd.image = source.readString();
			dd.price = source.readDouble();
			dd.url = source.readString();
			dd.ppi = source.readString();
			dd.functions = source.readString();
			dd.iccFile = source.readString();
			dd.printSpeedBlack = source.readDouble();
			dd.printSpeedColor = source.readDouble();
			dd.maxInputCapacity = source.readInt();
			dd.maxMonthlyDutyCycle = source.readInt();
			dd.autoDuplex = source.readString();
			dd.inkCyanPrice = source.readDouble();
			dd.inkCyanYield = source.readInt();
			dd.inkMagentaPrice = source.readDouble();
			dd.inkMagentaYield = source.readInt();
			dd.inkYellowPrice = source.readDouble();
			dd.inkYellowYield = source.readInt();
			dd.inkBlackPrice = source.readDouble();
			dd.inkBlackYield = source.readInt();


			return dd;
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
