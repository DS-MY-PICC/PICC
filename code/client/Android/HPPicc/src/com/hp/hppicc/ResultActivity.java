package com.hp.hppicc;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.hp.hppicc.dataDefinition.ResultData;
import com.hp.hppicc.util.PrintersUtilRef;
import com.hp.picc.utils.JsonHelper;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends Activity {
	
	String printer;
	String pageSize;
	String imageResolution;
	String printMode;
	int printVolume = 1;
	int printPeriod = 1;
	int printerImage;
	
	ScrollView scrollView1;
	
	double gTcpp;
	double gTpc;
	double gPrinterPrice;
	
	TextView tvCyan;
	TextView tvMagenta;
	TextView tvYellow;
	TextView tvBlack;
	TextView tvRsTPC;
	TextView tvRsIcpp;
	TextView tvRsTco;
	TextView tvRsName;
	TextView tvRsPrice;
	
	TableRow trVolumeSeekBar;
	TableRow trPeriodSeekBar;
	SeekBar sbVolume;
	
	ImageView ivRsSelectedImage;
	
	ResultData rd;
	
	String[] jsonLocal = {
			"[ { \"result\": { \"magenta\": 15.5, \"cyan\": 17.39, \"yellow\": 29.78, \"tcpp\": 1.51, \"black\": 32.88 }, \"printer\": { \"id\": 1, \"brand\": \"HP\", \"type\": \"Color Inkjet\", \"title\": \"HP Officejet 6600\", \"image\": \"http://15.125.96.127:8080/upload/printer/oofficejet6600.png\", \"price\": 129, \"url\": \"\", \"ppi\": null, \"functions\": \"Print,Copy,Scan,Fax,Web\", \"iccFile\": \"adobe/CMYK/WebCoatedFOGRA28.icc\", \"printSpeedBlack\": 18, \"printSpeedColor\": 13, \"maxInputCapacity\": 250, \"maxMonthlyDutyCycle\": 12000, \"autoDuplex\": \"Yes\", \"inkCyanPrice\": 10.99, \"inkCyanYield\": 330, \"inkMagentaPrice\": 10.99, \"inkMagentaYield\": 330, \"inkYellowPrice\": 10.99, \"inkYellowYield\": 330, \"inkBlackPrice\": 19.99, \"inkBlackYield\": 400 }, \"options\": { \"paper\": \"-1\", \"dpi\": \"100\", \"printer\": \"1\", \"mode\": \"grayscale\" } } ]",
			"[ { \"result\": { \"magenta\": 15.5, \"cyan\": 17.39, \"yellow\": 29.78, \"tcpp\": 1.1, \"black\": 32.88 }, \"printer\": { \"id\": 2, \"brand\": \"HP\", \"type\": \"Color Inkjet\", \"title\": \"HP Photosmart 7520\", \"image\": \"http://15.125.96.127:8080/upload/printer/tphotosmart7520.png\", \"price\": 199.99, \"url\": \"\", \"ppi\": null, \"functions\": \"Print,Copy,Scan,Fax\", \"iccFile\": \"adobe/CMYK/WebCoatedFOGRA28.icc\", \"printSpeedBlack\": 15, \"printSpeedColor\": 9.3, \"maxInputCapacity\": 500, \"maxMonthlyDutyCycle\": 12000, \"autoDuplex\": \"Yes\", \"inkCyanPrice\": 15.3, \"inkCyanYield\": 750, \"inkMagentaPrice\": 15.3, \"inkMagentaYield\": 750, \"inkYellowPrice\": 15.3, \"inkYellowYield\": 750, \"inkBlackPrice\": 28.85, \"inkBlackYield\": 800 }, \"options\": { \"paper\": \"-1\", \"dpi\": \"100\", \"printer\": \"2\", \"mode\": \"grayscale\" } } ]",
			"[ { \"result\": { \"magenta\": 15.5, \"cyan\": 17.39, \"yellow\": 29.78, \"tcpp\": 1.46, \"black\": 32.88 }, \"printer\": { \"id\": 3, \"brand\": \"Epson\", \"type\": \"Color Inkjet\", \"title\": \"Epson WorkForce WF-3520\", \"image\": \"http://15.125.96.127:8080/upload/printer/thepsonwf3520.png\", \"price\": 119.99, \"url\": \"\", \"ppi\": null, \"functions\": \"Print,Copy,Scan,Fax\", \"iccFile\": \"adobe/CMYK/WebCoatedFOGRA28.icc\", \"printSpeedBlack\": 15, \"printSpeedColor\": 9.3, \"maxInputCapacity\": 500, \"maxMonthlyDutyCycle\": 12000, \"autoDuplex\": \"Yes\", \"inkCyanPrice\": 17.09, \"inkCyanYield\": 470, \"inkMagentaPrice\": 17.09, \"inkMagentaYield\": 470, \"inkYellowPrice\": 17.09, \"inkYellowYield\": 470, \"inkBlackPrice\": 18.99, \"inkBlackYield\": 385 }, \"options\": { \"paper\": \"-1\", \"dpi\": \"100\", \"printer\": \"3\", \"mode\": \"grayscale\" } } ]",
			"[ { \"result\": { \"magenta\": 15.5, \"cyan\": 17.39, \"yellow\": 29.78, \"tcpp\": 1.55, \"black\": 32.88 }, \"printer\": { \"id\": 4, \"brand\": \"Canon\", \"type\": \"Color Inkjet\", \"title\": \"Canon Pixma MG8220\", \"image\": \"http://15.125.96.127:8080/upload/printer/focanonmg8220.png\", \"price\": 245.39, \"url\": \"\", \"ppi\": null, \"functions\": \"Print,Copy,Scan,Fax\", \"iccFile\": \"adobe/CMYK/WebCoatedFOGRA28.icc\", \"printSpeedBlack\": 15, \"printSpeedColor\": 9.3, \"maxInputCapacity\": 500, \"maxMonthlyDutyCycle\": 12000, \"autoDuplex\": \"Yes\", \"inkCyanPrice\": 13.33, \"inkCyanYield\": 466, \"inkMagentaPrice\": 13.33, \"inkMagentaYield\": 447, \"inkYellowPrice\": 13.33, \"inkYellowYield\": 478, \"inkBlackPrice\": 15.99, \"inkBlackYield\": 311 }, \"options\": { \"paper\": \"-1\", \"dpi\": \"100\", \"printer\": \"4\", \"mode\": \"grayscale\" } } ]",
			"[ { \"result\": { \"magenta\": 15.5, \"cyan\": 17.39, \"yellow\": 29.78, \"tcpp\": 0.69, \"black\": 32.88 }, \"printer\": { \"id\": 5, \"brand\": \"HP\", \"type\": \"Color Inkjet\", \"title\": \"HP DesignJet 30\", \"image\": \"http://15.125.96.127:8080/upload/printer/fidesignjet30.png\", \"price\": 1399.99, \"url\": \"\", \"ppi\": null, \"functions\": \"Print,Copy,Scan,Fax\", \"iccFile\": \"adobe/CMYK/WebCoatedFOGRA28.icc\", \"printSpeedBlack\": 15, \"printSpeedColor\": 9.3, \"maxInputCapacity\": 500, \"maxMonthlyDutyCycle\": 12000, \"autoDuplex\": \"Yes\", \"inkCyanPrice\": 32.78, \"inkCyanYield\": 1200, \"inkMagentaPrice\": 32.78, \"inkMagentaYield\": 1200, \"inkYellowPrice\": 32.78, \"inkYellowYield\": 1200, \"inkBlackPrice\": 45.99, \"inkBlackYield\": 2000 }, \"options\": { \"paper\": \"-1\", \"dpi\": \"100\", \"printer\": \"5\", \"mode\": \"grayscale\" } } ]",
			"[ { \"result\": { \"magenta\": 15.5, \"cyan\": 17.39, \"yellow\": 29.78, \"tcpp\": 0.14, \"black\": 32.88 }, \"printer\": { \"id\": 6, \"brand\": \"HP\", \"type\": \"Color Laserjet\", \"title\": \"HP Color LaserJet CM6040 MFP\", \"image\": \"http://15.125.96.127:8080/upload/printer/laserjet_cm6040.png\", \"price\": 11190.5, \"url\": \"\", \"ppi\": null, \"functions\": \"Print,Copy,Scan\", \"iccFile\": \"adobe/CMYK/WebCoatedFOGRA28.icc\", \"printSpeedBlack\": 15, \"printSpeedColor\": 15, \"maxInputCapacity\": 500, \"maxMonthlyDutyCycle\": 12000, \"autoDuplex\": \"Yes\", \"inkCyanPrice\": 90, \"inkCyanYield\": 21000, \"inkMagentaPrice\": 90, \"inkMagentaYield\": 21000, \"inkYellowPrice\": 90, \"inkYellowYield\": 21000, \"inkBlackPrice\": 90, \"inkBlackYield\": 19500 }, \"options\": { \"paper\": \"-1\", \"dpi\": \"100\", \"printer\": \"6\", \"mode\": \"grayscale\" } } ]"
	};
	
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.lefttorightvisible, R.anim.lefttorightinvisible);
		setContentView(R.layout.activity_result);
		
		PrintersUtilRef pu = PrintersUtilRef.getInstance();
		rd = (ResultData)getIntent().getParcelableExtra("rd");
		printer = String.valueOf(pu.getSelectedPrinterId());
		
		tvMagenta = (TextView)findViewById(R.id.tvMagenta);
		tvCyan = (TextView)findViewById(R.id.tvCyan);
		tvYellow = (TextView)findViewById(R.id.tvYellow);
		tvBlack = (TextView)findViewById(R.id.tvBlack);
		tvRsTPC = (TextView)findViewById(R.id.tvRsTPC);
		tvRsTco = (TextView)findViewById(R.id.tvRsTco);
		tvRsName = (TextView)findViewById(R.id.tvRsName);
		tvRsIcpp = (TextView)findViewById(R.id.tvRsIcpp);
		tvRsPrice = (TextView)findViewById(R.id.tvRsPrice);
		
		trVolumeSeekBar = (TableRow)findViewById(R.id.trVolumeSeekBar);
		trPeriodSeekBar = (TableRow)findViewById(R.id.trPeriodSeekBar);
		
		ivRsSelectedImage = (ImageView)findViewById(R.id.ivRsSelectedImage);
		ivRsSelectedImage.setImageResource(pu.getSelectedPrinterImage());
		
		scrollView1 = (ScrollView) findViewById(R.id.scrollView1);
		
		ActionBar ab = getActionBar();
		ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0096d6")));
		
		TextView tvRsPageSize = (TextView)findViewById(R.id.tvRsPageSize);
		tvRsPageSize.setText(pu.getPageSize());
		Log.d("paper", pu.getPageSize());
		if(pu.getPageSize().equals("Actual Size"))
		{
			pageSize = String.valueOf(-1);
		}
		else
		{
			pageSize = pu.getPageSize().replace("A", "");
			Log.d("options", "selected PrintMode:" + pu.getPageSize());
		}
		
		TextView tvRsImageResolution = (TextView)findViewById(R.id.tvRsImageResolution);
		tvRsImageResolution.setText(pu.getImageResolution());
		imageResolution = pu.getImageResolution().replace(" DPI", "");
		Log.d("options", "selected IM:" + pu.getImageResolution());
		
		TextView tvRsPrintMode = (TextView)findViewById(R.id.tvRsPrintMode);
		tvRsPrintMode.setText(pu.getPrintMode());
		printMode = pu.getPrintMode().toLowerCase();
		Log.d("options", "selected PM:" + pu.getPrintMode());
		
		final TextView tvRsPrintVolume = (TextView)findViewById(R.id.tvRsPrintVolume);
		tvRsPrintVolume.setText(String.valueOf( printVolume ) + " pc");
		
		final TextView tvRsPrintPeriod = (TextView)findViewById(R.id.tvRsPrintPeriod);
		tvRsPrintPeriod.setText(String.valueOf(printPeriod) + " day");
		
		ImageView ivResultImage = (ImageView)findViewById(R.id.ivResultImage);
		if(pu.getImagePath() != null && !pu.getImagePath().isEmpty())
		{		
			//display Picture
			// bimatp factory
	        BitmapFactory.Options options = new BitmapFactory.Options();
	        // downsizing image as it throws OutOfMemory Exception for larger
	        // images
	        options.inSampleSize = 8;
	        final Bitmap bitmap = BitmapFactory.decodeFile(pu.getImagePath(), options);
	        ivResultImage.setImageBitmap(bitmap);
		}
		else
		{
	        ivResultImage.setImageResource(R.drawable.aaimagetesting);
		}
		
		sbVolume = (SeekBar)findViewById(R.id.sbVolume);
		sbVolume.setProgress(printVolume);
		sbVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {				
				// TODO Auto-generated method stub
				printVolume = progress;
				if(printVolume >= 2)
					tvRsPrintVolume.setText(String.valueOf(printVolume) + " pcs");
				else
				{
					printVolume = 1;
					tvRsPrintVolume.setText(String.valueOf(printVolume) + " pc");
				}
				calculateCost();
			}
		});
		
		SeekBar sbPeriod = (SeekBar)findViewById(R.id.sbPeriod);
		sbPeriod.setProgress(printPeriod);
		sbPeriod.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				printPeriod = progress;
				if(printPeriod >= 2)
					tvRsPrintPeriod.setText(String.valueOf(printPeriod) + " days");
				else
				{
					printPeriod = 1;
					tvRsPrintPeriod.setText(String.valueOf(printPeriod) + " day");
				}
				calculateCost();
			}
		});
//		calculateOffLine(jsonLocal[rd.getId() - 1]);
		dialog = ProgressDialog.show(ResultActivity.this, "Processing Image Print Cost...",
                "Please wait...", true);
        new ImageUploadTask().execute();
	}
	
	protected void calculateOffLine(String sResponse) {
        try {

            if (sResponse != null) {
            	Log.d("resutl", "result sresponse" + sResponse);
            	JSONArray json = new JSONArray( sResponse ); 
            	rd = new ResultData( json.getJSONObject(0) );
            		
            	gTcpp = rd.getTcpp();
            	BigDecimal tcpp = new BigDecimal(rd.getTcpp()).setScale(2, BigDecimal.ROUND_HALF_UP);
            	tvRsIcpp.setText("$ " + tcpp.toString());
            	
            	double tpc = gTcpp * (double)printVolume * (double)printPeriod;
            	BigDecimal tpcD = new BigDecimal(tpc).setScale(2, BigDecimal.ROUND_HALF_UP);
            	tvRsTPC.setText( "$ " + tpcD.toString());
            	Log.d("tpcd", "tpcd " + tpcD);
            	
            	double cyan = rd.getCyanV();
            	tvCyan.setText(String.valueOf(cyan) + "%");                	
            	double magenta = rd.getMagentaV();
            	tvMagenta.setText(String.valueOf(magenta) + "%");                	
            	double yellow = rd.getYellowV();
            	tvYellow.setText(String.valueOf(yellow) + "%");                	
            	double black = rd.getBlackV();
            	tvBlack.setText(String.valueOf(black) + "%");
            	
				Log.d("SelectImageActivity", "tcpp: " + gTcpp + "; cyan: " + cyan + "; magenta: " + magenta + "; yellow: " + yellow + "; black: " + black);
				
				double pricePrinter = rd.getPrice();
				gPrinterPrice = pricePrinter;
				BigDecimal ppD = new BigDecimal(pricePrinter).setScale(2, BigDecimal.ROUND_HALF_UP);
				tvRsPrice.setText("$ " + String.valueOf( ppD.toString() ));					
				double tco = pricePrinter + tpc;
				BigDecimal tcoD = new BigDecimal(tco).setScale(2, BigDecimal.ROUND_HALF_UP);
				tvRsTco.setText("$ " + tcoD.toString());					
				String printerTitle = rd.getTitle();
				tvRsName.setText(printerTitle);
				
				Log.d("SelectImageActivity", "title: " + printerTitle + "; price: " + pricePrinter);
				
            }
        } catch (Exception e) {
//            Toast.makeText(getApplicationContext(), e.getClass().getName(),
//                    Toast.LENGTH_LONG).show();
            Log.e(e.getClass().getName(), e.getMessage(), e);
        }
    }
	
	private void calculateCost()
	{
		double totalCost = 0;
		double totalCostnPrinter = 0;
		totalCost = gTcpp * (double)printPeriod * (double)printVolume;
		Log.d("calculate", "total Cost " + totalCost);
		Log.d("printPeriod", "printperiod " + printPeriod);
		Log.d("calculate", "Print Volume " + printVolume);
		
		totalCostnPrinter = totalCost + gPrinterPrice;
		
		BigDecimal totalCostD = new BigDecimal(totalCost).setScale(2, BigDecimal.ROUND_HALF_UP);
		BigDecimal totalCostPrinterD = new BigDecimal(totalCostnPrinter).setScale(2, BigDecimal.ROUND_HALF_UP);
		tvRsTPC.setText("$ " + totalCostD.toString());
		tvRsTco.setText("$ " + totalCostPrinterD.toString());
	}
	
	public void calculatePrintCost(View v) {
		if(v.getId() == R.id.ivVolome || v.getId() == R.id.trVolume)
		{
			if(trVolumeSeekBar.getVisibility() == View.VISIBLE)
			{
				trVolumeSeekBar.setVisibility(View.GONE);
			}
			else
			{
				trPeriodSeekBar.setVisibility(View.GONE);
				trVolumeSeekBar.setVisibility(View.VISIBLE);
			}
		}
		else
		{
			if(trPeriodSeekBar.getVisibility() == View.VISIBLE)
			{
				trPeriodSeekBar.setVisibility(View.GONE);
			}
			else
			{
				trVolumeSeekBar.setVisibility(View.GONE);
				trPeriodSeekBar.setVisibility(View.VISIBLE);
				scrollView1.post( new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						scrollView1.fullScroll(View.FOCUS_DOWN);
					}
				});
			}				
		}
	}
	
	public void onClickComparePrinters(View v)
	{
		Intent comparePrinter = new Intent(getApplicationContext(), MultiplePrinterActivity.class);
		comparePrinter.putExtra("rd", (Parcelable)rd);
		startActivity(comparePrinter);
		overridePendingTransition(R.anim.lefttorightvisible, R.anim.lefttorightinvisible);
	}
	
	class ImageUploadTask extends AsyncTask <Void, Void, String>{
        @Override
        protected String doInBackground(Void... unsued) {
            try {
            	
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://15.125.96.127:8080/picc/cmyk/upload/image");
 
                MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create();
                multipartEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
 
                File imgFile = new File(PrintersUtilRef.getImagePath());
                multipartEntity.addBinaryBody("img", imgFile, ContentType.create("image/jpeg"), imgFile.getName());
               
                multipartEntity.addPart("paper", new StringBody(pageSize, ContentType.TEXT_PLAIN));
                multipartEntity.addPart("dpi", new StringBody(imageResolution, ContentType.TEXT_PLAIN));
                multipartEntity.addPart("mode", new StringBody(printMode, ContentType.TEXT_PLAIN));
                multipartEntity.addPart("printer", new StringBody(printer, ContentType.TEXT_PLAIN));
                
                httpPost.setEntity(multipartEntity.build());

                Log.d("SelectImageActivity", "httpPost=" + httpPost.getURI());
                HttpResponse response = httpClient.execute(httpPost);
                int statusCode = response.getStatusLine().getStatusCode();
                Log.d("SelectImageActivity", "statusCode=" + statusCode);
                
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                response.getEntity().getContent(), "UTF-8"));
 
                String sResponse = reader.readLine();
                Log.d("JSON", "Response: " + sResponse);
                return sResponse;
                
			} catch (Exception e) {
				
				Log.e(e.getClass().getName(), e.getMessage(), e);
				return null;
            }
 
            // (null);
        }
 
        @Override
        protected void onProgressUpdate(Void... unsued) {
 
        }
 
        @Override
        protected void onPostExecute(String sResponse) {
            try {
                if (dialog.isShowing())
                    dialog.dismiss();
 
                if (sResponse != null) {
                	Log.d("resutl", "result sresponse" + sResponse);
                	calculateOffLine(sResponse);
                	JSONArray json = new JSONArray( sResponse ); 					
                }
            } catch (Exception e) {
//                Toast.makeText(getApplicationContext(), e.getClass().getName(),
//                        Toast.LENGTH_LONG).show();
                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }
    }
	
}
