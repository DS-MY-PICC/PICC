package com.hp.hppicc;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.w3c.dom.Text;

import com.hp.hppicc.ResultActivity.ImageUploadTask;
import com.hp.hppicc.dataDefinition.ResultData;
import com.hp.hppicc.util.PrintersUtilRef;

import android.R.color;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils.StringSplitter;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class CompareActivity extends Activity {
	
	ResultData rd;
	String choices;
	
	ProgressDialog dialog;
	
	String[] jsonLocal = {
			"{ \"result\": { \"magenta\": 15.5, \"cyan\": 17.39, \"yellow\": 29.78, \"tcpp\": 1.51, \"black\": 32.88 }, \"printer\": { \"id\": 1, \"brand\": \"HP\", \"type\": \"Color Inkjet\", \"title\": \"HP Officejet 6600\", \"image\": \"http://15.125.96.127:8080/upload/printer/oofficejet6600.png\", \"price\": 129, \"url\": \"\", \"ppi\": null, \"functions\": \"Print,Copy,Scan,Fax,Web\", \"iccFile\": \"adobe/CMYK/WebCoatedFOGRA28.icc\", \"printSpeedBlack\": 18, \"printSpeedColor\": 13, \"maxInputCapacity\": 250, \"maxMonthlyDutyCycle\": 12000, \"autoDuplex\": \"Yes\", \"inkCyanPrice\": 10.99, \"inkCyanYield\": 330, \"inkMagentaPrice\": 10.99, \"inkMagentaYield\": 330, \"inkYellowPrice\": 10.99, \"inkYellowYield\": 330, \"inkBlackPrice\": 19.99, \"inkBlackYield\": 400 }, \"options\": { \"paper\": \"-1\", \"dpi\": \"100\", \"printer\": \"1\", \"mode\": \"grayscale\" } }",
			"{ \"result\": { \"magenta\": 15.5, \"cyan\": 17.39, \"yellow\": 29.78, \"tcpp\": 1.1, \"black\": 32.88 }, \"printer\": { \"id\": 2, \"brand\": \"HP\", \"type\": \"Color Inkjet\", \"title\": \"HP Photosmart 7520\", \"image\": \"http://15.125.96.127:8080/upload/printer/tphotosmart7520.png\", \"price\": 199.99, \"url\": \"\", \"ppi\": null, \"functions\": \"Print,Copy,Scan,Fax\", \"iccFile\": \"adobe/CMYK/WebCoatedFOGRA28.icc\", \"printSpeedBlack\": 15, \"printSpeedColor\": 9.3, \"maxInputCapacity\": 500, \"maxMonthlyDutyCycle\": 12000, \"autoDuplex\": \"Yes\", \"inkCyanPrice\": 15.3, \"inkCyanYield\": 750, \"inkMagentaPrice\": 15.3, \"inkMagentaYield\": 750, \"inkYellowPrice\": 15.3, \"inkYellowYield\": 750, \"inkBlackPrice\": 28.85, \"inkBlackYield\": 800 }, \"options\": { \"paper\": \"-1\", \"dpi\": \"100\", \"printer\": \"2\", \"mode\": \"grayscale\" } }",
			"{ \"result\": { \"magenta\": 15.5, \"cyan\": 17.39, \"yellow\": 29.78, \"tcpp\": 1.46, \"black\": 32.88 }, \"printer\": { \"id\": 3, \"brand\": \"Epson\", \"type\": \"Color Inkjet\", \"title\": \"Epson WorkForce WF-3520\", \"image\": \"http://15.125.96.127:8080/upload/printer/thepsonwf3520.png\", \"price\": 119.99, \"url\": \"\", \"ppi\": null, \"functions\": \"Print,Copy,Scan,Fax\", \"iccFile\": \"adobe/CMYK/WebCoatedFOGRA28.icc\", \"printSpeedBlack\": 15, \"printSpeedColor\": 9.3, \"maxInputCapacity\": 500, \"maxMonthlyDutyCycle\": 12000, \"autoDuplex\": \"Yes\", \"inkCyanPrice\": 17.09, \"inkCyanYield\": 470, \"inkMagentaPrice\": 17.09, \"inkMagentaYield\": 470, \"inkYellowPrice\": 17.09, \"inkYellowYield\": 470, \"inkBlackPrice\": 18.99, \"inkBlackYield\": 385 }, \"options\": { \"paper\": \"-1\", \"dpi\": \"100\", \"printer\": \"3\", \"mode\": \"grayscale\" } }",
			"{ \"result\": { \"magenta\": 15.5, \"cyan\": 17.39, \"yellow\": 29.78, \"tcpp\": 1.55, \"black\": 32.88 }, \"printer\": { \"id\": 4, \"brand\": \"Canon\", \"type\": \"Color Inkjet\", \"title\": \"Canon Pixma MG8220\", \"image\": \"http://15.125.96.127:8080/upload/printer/focanonmg8220.png\", \"price\": 245.39, \"url\": \"\", \"ppi\": null, \"functions\": \"Print,Copy,Scan,Fax\", \"iccFile\": \"adobe/CMYK/WebCoatedFOGRA28.icc\", \"printSpeedBlack\": 15, \"printSpeedColor\": 9.3, \"maxInputCapacity\": 500, \"maxMonthlyDutyCycle\": 12000, \"autoDuplex\": \"Yes\", \"inkCyanPrice\": 13.33, \"inkCyanYield\": 466, \"inkMagentaPrice\": 13.33, \"inkMagentaYield\": 447, \"inkYellowPrice\": 13.33, \"inkYellowYield\": 478, \"inkBlackPrice\": 15.99, \"inkBlackYield\": 311 }, \"options\": { \"paper\": \"-1\", \"dpi\": \"100\", \"printer\": \"4\", \"mode\": \"grayscale\" } }",
			"{ \"result\": { \"magenta\": 15.5, \"cyan\": 17.39, \"yellow\": 29.78, \"tcpp\": 0.69, \"black\": 32.88 }, \"printer\": { \"id\": 5, \"brand\": \"HP\", \"type\": \"Color Inkjet\", \"title\": \"HP DesignJet 30\", \"image\": \"http://15.125.96.127:8080/upload/printer/fidesignjet30.png\", \"price\": 1399.99, \"url\": \"\", \"ppi\": null, \"functions\": \"Print,Copy,Scan,Fax\", \"iccFile\": \"adobe/CMYK/WebCoatedFOGRA28.icc\", \"printSpeedBlack\": 15, \"printSpeedColor\": 9.3, \"maxInputCapacity\": 500, \"maxMonthlyDutyCycle\": 12000, \"autoDuplex\": \"Yes\", \"inkCyanPrice\": 32.78, \"inkCyanYield\": 1200, \"inkMagentaPrice\": 32.78, \"inkMagentaYield\": 1200, \"inkYellowPrice\": 32.78, \"inkYellowYield\": 1200, \"inkBlackPrice\": 45.99, \"inkBlackYield\": 2000 }, \"options\": { \"paper\": \"-1\", \"dpi\": \"100\", \"printer\": \"5\", \"mode\": \"grayscale\" } }",
			"{ \"result\": { \"magenta\": 15.5, \"cyan\": 17.39, \"yellow\": 29.78, \"tcpp\": 0.14, \"black\": 32.88 }, \"printer\": { \"id\": 6, \"brand\": \"HP\", \"type\": \"Color Laserjet\", \"title\": \"HP Color LaserJet CM6040 MFP\", \"image\": \"http://15.125.96.127:8080/upload/printer/laserjet_cm6040.png\", \"price\": 11190.5, \"url\": \"\", \"ppi\": null, \"functions\": \"Print,Copy,Scan\", \"iccFile\": \"adobe/CMYK/WebCoatedFOGRA28.icc\", \"printSpeedBlack\": 15, \"printSpeedColor\": 15, \"maxInputCapacity\": 500, \"maxMonthlyDutyCycle\": 12000, \"autoDuplex\": \"Yes\", \"inkCyanPrice\": 90, \"inkCyanYield\": 21000, \"inkMagentaPrice\": 90, \"inkMagentaYield\": 21000, \"inkYellowPrice\": 90, \"inkYellowYield\": 21000, \"inkBlackPrice\": 90, \"inkBlackYield\": 19500 }, \"options\": { \"paper\": \"-1\", \"dpi\": \"100\", \"printer\": \"6\", \"mode\": \"grayscale\" } }"
	};
	
	ArrayList<TextView> totalCostArray = new ArrayList<TextView>();
	ArrayList<TextView> tipccArray = new ArrayList<TextView>();
	
	SeekBar sbVolume;
	SeekBar sbPeriod;
	double volume = 100;
	double period = 10;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compare);
		ActionBar ab = getActionBar();
		ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0096d6")));
		
		rd = (ResultData)getIntent().getParcelableExtra("rd");
		choices = rd.getChoices();
		String[] choicesArray = choices.split("\\|");
		
		PrintersUtilRef pu = PrintersUtilRef.getInstance();		
		
		if(pu.getImagePath() != null && !pu.getImagePath().isEmpty())
		{		
			//display Picture
			// bimatp factory
	        BitmapFactory.Options options = new BitmapFactory.Options();
	        // downsizing image as it throws OutOfMemory Exception for larger
	        // images
	        options.inSampleSize = 8;
	        final Bitmap bitmap = BitmapFactory.decodeFile(PrintersUtilRef.getImagePath(), options);
	        ImageView ivPic = (ImageView)findViewById(R.id.ivCcImage);
	        ivPic.setImageBitmap(bitmap);
		}
		
		TextView tvCcPageSize = (TextView)findViewById(R.id.tvCcPageSize);
		if(rd.getPaper().equals("-1"))
			tvCcPageSize.setText("Actual Size");
		else
			tvCcPageSize.setText("A" + rd.getPaper());
		
		TextView tvCcImageResolution = (TextView)findViewById(R.id.tvCcImageResolution);
		tvCcImageResolution.setText(rd.getDpi() + " DPI");
		TextView tvCcPrintMode = (TextView)findViewById(R.id.tvCcPrintMode);
		
		tvCcPrintMode.setText(rd.getMode().substring(0).toUpperCase());
		
		final TextView tvRsPrintVolume = (TextView)findViewById(R.id.tvRsPrintVolume);
		final TextView tvRsPrintPeriod = (TextView)findViewById(R.id.tvRsPrintPeriod);
		
		sbVolume = (SeekBar)findViewById(R.id.sbVolume);
		sbVolume.setProgress(100);
		sbVolume.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
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
				volume = (double)progress;
				if(volume > 1)
					tvRsPrintVolume.setText(String.valueOf(progress) + "days");
				else
				{
					volume = 1;
					tvRsPrintVolume.setText(String.valueOf(progress) + " day");
				}
				calculateCost();
			}
		});
		sbPeriod = (SeekBar)findViewById(R.id.sbPeriod);
		sbPeriod.setProgress(10);
		sbPeriod.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
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
				period =(double)progress;
				if(progress > 1)
					tvRsPrintPeriod.setText(String.valueOf(progress) + " pcs");
				else
				{
					period = 1;
					tvRsPrintPeriod.setText(String.valueOf(progress) + " pcs");
				}
				calculateCost();
			}
		});
		
		String sResponse = "";
		if(choicesArray.length > 0)
		{
			String s = "";
			for(int i = 0; i < choicesArray.length; i ++)
			{
				int l = Integer.parseInt(choicesArray[i]) - 1;
				Log.d("Compare", "l is " + l);
				String  json = jsonLocal[l];
				s += json + ",";
			}
			s = "[" + s;
			StringBuilder sb = new StringBuilder(s);
			sb = sb.replace(sb.lastIndexOf(","), sb.length(), "]");
			sResponse = sb.toString();					
			Log.d("Compare", "Compare String " + sResponse);
		}
		
		dialog = ProgressDialog.show(CompareActivity.this, "Comparing in Progress...",
	                "Please wait...", true);
        new CompareCalculateTask().execute();
	}
	
	
	
	protected void texttableLayout(String sResponse) {
        try { 
            if (sResponse != null) {
            	Log.d("Compare", " Compare result sresponse" + sResponse);
            	JSONArray json = new JSONArray( sResponse );
            	LinearLayout ll = (LinearLayout)findViewById(R.id.llCcMultiplePrinters);
            	
            	TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        		TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
        		tableRowParams.setMargins(30, 20, 30, 0);
        		
        		TableLayout PrintersMul = new TableLayout(this);
        		PrintersMul.setLayoutParams(tableParams);
        		
        		TableRow trIvPrinter = new TableRow(this);
        		trIvPrinter.setLayoutParams(tableRowParams);
        		
        		TableRow trName = new TableRow(this);
        		trName.setLayoutParams(tableRowParams);
        		
        		
        		TableRow trPrice = new TableRow(this);
        		trPrice.setLayoutParams(tableRowParams);
        		
        		TableRow trIcpp = new TableRow(this);
        		trIcpp.setLayoutParams(tableRowParams);
        		
        		TableRow trTpc = new TableRow(this);
        		trTpc.setLayoutParams(tableRowParams);
        		
        		TableRow trTco = new TableRow(this);
        		trTco.setLayoutParams(tableRowParams);
            	
            	for(int i = 0; i < json.length(); i ++)
            	{
            		ResultData resultDd = new ResultData( json.getJSONObject(i) );
            		
            		TextView tvName = new TextView(this);
            		TextView tvPrice = new TextView(this);
            		TextView tvIcpp = new TextView(this);
            		TextView tvTpc = new TextView(this);
            		TextView tvTco = new TextView(this);
            		
            		if(i % 2 == 0)
            		{
            			tvName.setTextColor(Color.parseColor("#0096d6"));
            			tvPrice.setTextColor(Color.parseColor("#0096d6"));
            			tvIcpp.setTextColor(Color.parseColor("#0096d6"));
            			tvTpc.setTextColor(Color.parseColor("#0096d6"));            			
            			tvTco.setTextColor(Color.parseColor("#0096d6"));
            		}
            		else
            		{
            			tvName.setTextColor(Color.parseColor("#FFFFFF"));
            			tvPrice.setTextColor(Color.parseColor("#FFFFFF"));
            			tvIcpp.setTextColor(Color.parseColor("#FFFFFF"));
            			tvTpc.setTextColor(Color.parseColor("#FFFFFF"));
            			tvTco.setTextColor(Color.parseColor("#FFFFFF"));
            		}
            		
            		ImageView ivPrinter = new ImageView(this);
            		ivPrinter.setImageResource(PrintersUtilRef.imageId[resultDd.getId() - 1]);
            		trIvPrinter.addView(ivPrinter, i);
            		
//            		tvName.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            		tvName.setText(resultDd.getTitle());
            		trName.addView(tvName, i);
            		
            		tvPrice.setText("$ " + String.valueOf(resultDd.getPrice()));
            		trPrice.addView(tvPrice, i);
            		
            		tvIcpp.setText("$ " + String.valueOf(resultDd.getTcpp()));
            		trIcpp.addView(tvIcpp, i);
            		
            		double tpc = resultDd.getPrice() * 100 * 10;
            		BigDecimal tpcD = new BigDecimal(100.09).setScale(2, BigDecimal.ROUND_HALF_UP);
            		tvTpc.setText("$ " + tpcD.toString());
            		trTpc.addView(tvTpc, i);
            		tipccArray.add(tvIcpp);
            		
            		double totalCost = tpc + rd.getPrice();
            		BigDecimal totalCostD = new BigDecimal(totalCost).setScale(2, BigDecimal.ROUND_HALF_UP); 
            		tvTco.setText("$ " + totalCostD.toString());
            		trTco.addView(tvTco, i);
            		totalCostArray.add(tvTco);
            	}
            	
            	PrintersMul.addView(trIvPrinter, 0);
            	PrintersMul.addView(trName, 1);
            	PrintersMul.addView(trPrice, 2);
            	PrintersMul.addView(trIcpp, 3);
            	PrintersMul.addView(trTpc, 4);            	
            	PrintersMul.addView(trTco, 5);
            	
        		ll.addView(PrintersMul );
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getClass().getName(),
                    Toast.LENGTH_LONG).show();
            Log.e(e.getClass().getName(), e.getMessage(), e);
        }
    }
	
	private void calculateCost()
	{
		double tpc = rd.getTcpp() * volume * period;
		BigDecimal tpcd = new BigDecimal(tpc).setScale(2, BigDecimal.ROUND_HALF_UP);
		double totalCost = rd.getPrice() + tpc;
		BigDecimal totalCostd = new BigDecimal(totalCost).setScale(2, BigDecimal.ROUND_HALF_UP);
		
		for(int i = 0; i < tipccArray.size(); i ++)
		{
			tipccArray.get(i).setText("$ " + tpcd.toString());
			totalCostArray.get(i).setText("$ " + totalCostd.toString());
		}
				
	}
	
	public void calculatePrintCost(View v)
	{
		if(v.getId() == R.id.ivVolome)
		{
			if(sbVolume.getVisibility() == View.GONE)
			{
				sbVolume.setVisibility(View.VISIBLE);
				sbPeriod.setVisibility(View.GONE);
			}
			else
				sbVolume.setVisibility(View.GONE);
		}
		else
		{
			if(sbPeriod.getVisibility() == View.GONE)
			{
				sbPeriod.setVisibility(View.VISIBLE);
				sbVolume.setVisibility(View.GONE);
			}
			else
				sbPeriod.setVisibility(View.GONE);
		}
	}
	
	class CompareCalculateTask extends AsyncTask <Void, Void, String>{
        @Override
        protected String doInBackground(Void... unsued) {
            try {
            	
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://15.125.96.127:8080/picc/cmyk/image");
 
                MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create();
                multipartEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                
                BitmapFactory.Options options = new BitmapFactory.Options();
    	        // downsizing image as it throws OutOfMemory Exception for larger
    	        // images
                options.inJustDecodeBounds = true;
    	        final Bitmap bitmap = BitmapFactory.decodeFile(PrintersUtilRef.getImagePath(), options);
    	        String imageWidth = String.valueOf(options.outWidth);
    	        String imageHeight = String.valueOf(options.outHeight);
    	        
    	        Log.d("Multiple", "imageheight:" + imageHeight);
    	        Log.d("Multiple", "imageWidth:" + imageWidth);
    	        Log.d("Multiple", "rd.getCyanV():" + rd.getCyanV());
    	        Log.d("Multiple", "rd.getMagentaV():" + rd.getMagentaV());
    	        Log.d("Multiple", "rd.getBlackV():" + rd.getBlackV());
    	        Log.d("Multiple", "rd.getPaper():" + rd.getPaper());
    	        Log.d("Multiple", "rd.getDpi():" + rd.getDpi());
    	        Log.d("Multiple", "rd.getMode():" + rd.getMode());
    	        Log.d("Multiple", "rd.getChoices():" + rd.getChoices());
 
                multipartEntity.addPart("cyan", new StringBody(String.valueOf(rd.getCyanV()), ContentType.TEXT_PLAIN));
                multipartEntity.addPart("magenta", new StringBody(String.valueOf(rd.getMagentaV()), ContentType.TEXT_PLAIN));
                multipartEntity.addPart("yellow", new StringBody(String.valueOf(rd.getYellowV()), ContentType.TEXT_PLAIN));
                multipartEntity.addPart("black", new StringBody(String.valueOf(rd.getBlackV()), ContentType.TEXT_PLAIN));
                multipartEntity.addPart("width", new StringBody(imageWidth, ContentType.TEXT_PLAIN));
                multipartEntity.addPart("height", new StringBody(imageHeight, ContentType.TEXT_PLAIN));
                multipartEntity.addPart("paper", new StringBody(String.valueOf(rd.getPaper()), ContentType.TEXT_PLAIN));
                multipartEntity.addPart("dpi", new StringBody(String.valueOf(rd.getDpi()), ContentType.TEXT_PLAIN));
                multipartEntity.addPart("mode", new StringBody(String.valueOf(rd.getMode()), ContentType.TEXT_PLAIN));
                multipartEntity.addPart("printer", new StringBody(String.valueOf(rd.getChoices()), ContentType.TEXT_PLAIN));
                
                Log.d("Multiple", "multiPart" + multipartEntity.toString());
                
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
				if (dialog.isShowing())
					dialog.dismiss();
				
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
                if (sResponse != null) {
                	Log.d("Compare", " Compare result sresponse" + sResponse);
                	texttableLayout(sResponse);
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.getClass().getName(),
                        Toast.LENGTH_LONG).show();
                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
            finally
            {
            	if (dialog.isShowing())
                    dialog.dismiss();
            }
        }
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
}

	