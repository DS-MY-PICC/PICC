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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends Activity {
	
	String filePath;
	String printer;
	String pageSize;
	String imageResolution;
	String printMode;
	int printVolume;
	int printPeriod;
	int printerId;
	
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
	
	ResultData resultDd;
	
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.lefttorightvisible, R.anim.lefttorightinvisible);
		setContentView(R.layout.activity_result);
		
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
		
		ActionBar ab = getActionBar();
		ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0096d6")));
		
		this.printer = getIntent().getStringExtra("printer");
		this.filePath = getIntent().getStringExtra("filePath");
		this.pageSize = getIntent().getStringExtra("pageSize");
		this.imageResolution = getIntent().getStringExtra("imageResolution");
		this.printMode = getIntent().getStringExtra("printMode");
		this.printVolume = getIntent().getIntExtra("printVolume", 1);
		this.printPeriod = getIntent().getIntExtra("printPeriod", 1);
		this.printerId = getIntent().getIntExtra("printerImage", R.drawable.oofficejet6600);
		ivRsSelectedImage.setImageResource(printerId);
		
		TextView tvRsPageSize = (TextView)findViewById(R.id.tvRsPageSize);
		if(pageSize.equals("-1"))
			tvRsPageSize.setText("Actual Size");
		else
			tvRsPageSize.setText("A" + String.valueOf(pageSize));
		
		TextView tvRsImageResolution = (TextView)findViewById(R.id.tvRsImageResolution);
		tvRsImageResolution.setText(imageResolution + " DPI");
		
		TextView tvRsPrintMode = (TextView)findViewById(R.id.tvRsPrintMode);
		tvRsPrintMode.setText(printMode);
		
		final TextView tvRsPrintVolume = (TextView)findViewById(R.id.tvRsPrintVolume);
		if(printVolume == 1)
			tvRsPrintVolume.setText(String.valueOf( printVolume ) + " pc");
		else
			tvRsPrintVolume.setText(String.valueOf( printVolume ) + " pcs");
		
		final TextView tvRsPrintPeriod = (TextView)findViewById(R.id.tvRsPrintPeriod);
		if(printPeriod == 1)
			tvRsPrintPeriod.setText(String.valueOf(printPeriod) + " day");
		else
			tvRsPrintPeriod.setText(String.valueOf(printPeriod) + " days");
		
		ImageView ivResultImage = (ImageView)findViewById(R.id.ivResultImage);
		if(filePath != null && !filePath.isEmpty())
		{		
			//display Picture
			// bimatp factory
	        BitmapFactory.Options options = new BitmapFactory.Options();
	        // downsizing image as it throws OutOfMemory Exception for larger
	        // images
	        options.inSampleSize = 8;
	        final Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
	        ivResultImage.setImageBitmap(bitmap);
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
					tvRsPrintVolume.setText(String.valueOf(printVolume) + " pc");
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
					tvRsPrintPeriod.setText(String.valueOf(printPeriod) + " day");
				calculateCost();
			}
		});
		
		dialog = ProgressDialog.show(ResultActivity.this, "Processing Image Print Cost...",
                "Please wait...", true);
        new ImageUploadTask().execute();
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
		if(v.getId() == R.id.ivVolome)
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
		else if(v.getId() == R.id.ivPeriod)
		{
			if(trPeriodSeekBar.getVisibility() == View.VISIBLE)
			{
				trPeriodSeekBar.setVisibility(View.GONE);
			}
			else
			{
				trVolumeSeekBar.setVisibility(View.GONE);
				trPeriodSeekBar.setVisibility(View.VISIBLE);
			}				
		}
	}
	
	public void onClickComparePrinters(View v)
	{
		Toast.makeText(getApplicationContext(), "Will compare prices with few printers in coming release", Toast.LENGTH_LONG).show();
		Intent comparePrinter = new Intent(getApplicationContext(), GetPrintersActivity.class);
		comparePrinter.putExtra("isUpload", false);
		comparePrinter.putExtra("printerId", printer);
		startActivity(comparePrinter);
	}
	
	class ImageUploadTask extends AsyncTask <Void, Void, String>{
        @Override
        protected String doInBackground(Void... unsued) {
            try {
            	
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://15.125.96.127:8080/picc/cmyk/upload/image");
 
                MultipartEntityBuilder multipartEntity = MultipartEntityBuilder.create();
                multipartEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
 
                File imgFile = new File(filePath);
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
                if (dialog.isShowing())
                    dialog.dismiss();
 
                if (sResponse != null) {
                	
                	JSONArray json = new JSONArray( sResponse ); 
                	resultDd = new ResultData( json.getJSONObject(0) );
                		
                	gTcpp = resultDd.getTcpp();
                	tvRsIcpp.setText("$ " + String.valueOf(gTcpp));
                	
                	double tpc = gTcpp * (double)printVolume * (double)printPeriod;
                	BigDecimal tpcD = new BigDecimal(tpc).setScale(2, BigDecimal.ROUND_HALF_UP);
                	tvRsTPC.setText( "$ " + tpcD.toString());
                	Log.d("tpcd", "tpcd " + tpcD);
                	
                	double cyan = resultDd.getCyanV();
                	tvCyan.setText(String.valueOf(cyan) + "%");                	
                	double magenta = resultDd.getMagentaV();
                	tvMagenta.setText(String.valueOf(magenta) + "%");                	
                	double yellow = resultDd.getYellowV();
                	tvYellow.setText(String.valueOf(yellow) + "%");                	
                	double black = resultDd.getBlackV();
                	tvBlack.setText(String.valueOf(black) + "%");
                	
					Log.d("SelectImageActivity", "tcpp: " + gTcpp + "; cyan: " + cyan + "; magenta: " + magenta + "; yellow: " + yellow + "; black: " + black);
					
					double pricePrinter = resultDd.getPrice();
					gPrinterPrice = pricePrinter;
					tvRsPrice.setText("$ " + String.valueOf( pricePrinter ));					
					double tco = pricePrinter + tpc;
					BigDecimal tcoD = new BigDecimal(tco).setScale(2, BigDecimal.ROUND_HALF_UP);
					tvRsTco.setText("$ " + tcoD.toString());					
					String printerTitle = resultDd.getTitle();
					tvRsName.setText(printerTitle);
					
					Log.d("SelectImageActivity", "title: " + printerTitle + "; price: " + pricePrinter);
					
                }
            } catch (Exception e) {
//                Toast.makeText(getApplicationContext(), e.getClass().getName(),
//                        Toast.LENGTH_LONG).show();
                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }
    }
	
}
