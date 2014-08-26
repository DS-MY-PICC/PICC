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
import org.json.JSONObject;

import com.hp.picc.utils.JsonHelper;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
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
	
	TextView tvCyan;
	TextView tvMagenta;
	TextView tvYellow;
	TextView tvBlack;
	TextView tvRsTPC;
	TextView tvRsIcpp;
	TextView tvRsTco;
	TextView tvRsName;
	TextView tvRsPrice;
	
	ImageView ivRsSelectedImage;
	
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		
		TextView tvRsPrintVolume = (TextView)findViewById(R.id.tvRsPrintVolume);
		if(printVolume == 1)
			tvRsPrintVolume.setText(String.valueOf( printVolume ) + " pc");
		else
			tvRsPrintVolume.setText(String.valueOf( printVolume ) + " pcs");
		
		TextView tvRsPrintPeriod = (TextView)findViewById(R.id.tvRsPrintPeriod);
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
		
		dialog = ProgressDialog.show(ResultActivity.this, "Processing Image Print Cost...",
                "Please wait...", true);
        new ImageUploadTask().execute();
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
                	
                	JSONObject json = new JSONObject(sResponse); 
                	if(json.isNull("error")) {
                		
                    	Map<String, Object> resultMap = JsonHelper.getMap(json, "result");                    	
                    	double tcpp = (Double) resultMap.get("tcpp");
                    	tvRsIcpp.setText("$ " + String.valueOf(tcpp));
                    	
                    	double tpc = tcpp * (double)printVolume * (double)printPeriod;
                    	BigDecimal tpcD = new BigDecimal(tpc).setScale(2, BigDecimal.ROUND_HALF_UP);
                    	tvRsTPC.setText( "$ " + tpcD.toString());
                    	Log.d("tpcd", "tpcd " + tpcD);
                    	
                    	double cyan = (Double) resultMap.get("cyan");
                    	tvCyan.setText(String.valueOf(cyan) + "%");
                    	
                    	double magenta = (Double) resultMap.get("magenta");
                    	tvMagenta.setText(String.valueOf(magenta) + "%");
                    	
                    	double yellow = (Double) resultMap.get("yellow");
                    	tvYellow.setText(String.valueOf(yellow) + "%");
                    	
                    	double black = (Double) resultMap.get("black");
                    	tvBlack.setText(String.valueOf(black) + "%");
    					Log.d("SelectImageActivity", "tcpp: " + tcpp + "; cyan: " + cyan + "; magenta: " + magenta + "; yellow: " + yellow + "; black: " + black);
    					
//    					JSONObject printer = 
    					
    					JSONObject printerOB = json.getJSONObject("printer");
    					
    					double pricePrinter = printerOB.getDouble("price");
    					tvRsPrice.setText("$ " + String.valueOf( pricePrinter ));
    					
    					double tco = pricePrinter + tpc;
    					BigDecimal tcoD = new BigDecimal(tco).setScale(2, BigDecimal.ROUND_HALF_UP);
    					tvRsTco.setText("$ " + tcoD.toString());
    					
    					String printerTitle = printerOB.getString("title");
    					tvRsName.setText(printerTitle);
    					
//    					Map<String, Object> printerMap = JsonHelper.getMap(json, "printer");
//    					String title = (String) resultMap.get("title");
//    					double price = (Double) resultMap.get("price");
    					Log.d("SelectImageActivity", "title: " + printerTitle + "; price: " + pricePrinter);
    					
//    					Map<String, Object> optionsMap = JsonHelper.getMap(json, "options");
//    					String paper = (String) resultMap.get("paper");
//    					String dpi = (String) resultMap.get("dpi");
//    					String printer = (String) resultMap.get("printer");
//    					String mode = (String) resultMap.get("mode");
//    					Log.d("SelectImageActivity", "paper: " + paper + "; dpi: " + dpi);
    					
    					
                        Toast.makeText(getApplicationContext(), "Total Cost Per Print:" + tcpp,
                                Toast.LENGTH_LONG).show();
                		
                	} else {
                		
                		String error = json.getString("error");
                		Toast.makeText(getApplicationContext(),error, Toast.LENGTH_LONG).show();
                		Log.d("SelectImageActivity", "error=" + error);
                    
                	}
                }
            } catch (Exception e) {
//                Toast.makeText(getApplicationContext(), e.getClass().getName(),
//                        Toast.LENGTH_LONG).show();
                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }
    }
	
}
