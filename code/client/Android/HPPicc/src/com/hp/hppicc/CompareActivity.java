package com.hp.hppicc;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URI;
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
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils.StringSplitter;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
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
		
		WindowManager.LayoutParams attrs = getWindow().getAttributes();
		attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
		getWindow().setAttributes(attrs);
		
		rd = (ResultData)getIntent().getParcelableExtra("rd");
		
		String[] choicesArray;
		
		if(choices != null)
		{
			choices = rd.getChoices();
			choicesArray = choices.split("\\|");
		}
		
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
		if(pu.getPageSize().equals("-1"))
			tvCcPageSize.setText("Actual Size");
		else
			tvCcPageSize.setText("A" + pu.getPageSize());
		
		TextView tvCcImageResolution = (TextView)findViewById(R.id.tvCcImageResolution);
		tvCcImageResolution.setText(pu.getImageResolution()+" DPI");
		
		TextView tvCcPrintMode = (TextView)findViewById(R.id.tvCcPrintMode);
		if(pu.getPrintMode().equals("color"))
			tvCcPrintMode.setText("Color");
		else
			tvCcPrintMode.setText("GrayScale");
		
		final TextView tvRsPrintVolume = (TextView)findViewById(R.id.tvRsPrintVolume);
		if(pu.getPrintVolume() > 1)
			tvRsPrintVolume.setText(String.valueOf(pu.getPrintVolume()) + " pcs");
		else
			tvRsPrintVolume.setText(String.valueOf(pu.getPrintVolume()) + " pc");
		
		final TextView tvRsPrintPeriod = (TextView)findViewById(R.id.tvRsPrintPeriod);
		if(pu.getPrintPeriod() > 1)
			tvRsPrintPeriod.setText(String.valueOf(pu.getPrintPeriod()) + " Days");
		else
			tvRsPrintPeriod.setText(String.valueOf(pu.getPrintPeriod()) + " Day");
		
		sbVolume = (SeekBar)findViewById(R.id.sbVolume);
		sbVolume.setProgress(pu.getPrintVolume());
		sbVolume.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				calculateCost();
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
					tvRsPrintVolume.setText(String.valueOf(progress) + " pcs");
				else
				{
					volume = 1;
					tvRsPrintVolume.setText(String.valueOf((int)volume) + " pc");
				}
			}
		});
		sbPeriod = (SeekBar)findViewById(R.id.sbPeriod);
		sbPeriod.setProgress(pu.getPrintVolume());
		sbPeriod.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				calculateCost();
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
					tvRsPrintPeriod.setText(String.valueOf(progress) + " days");
				else
				{
					period = 1;
					tvRsPrintPeriod.setText(String.valueOf((int)period) + " day");
				}
			}
		});
		
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
        		
        		TableLayout PrintersMul = new TableLayout(this);
        		PrintersMul.setLayoutParams(tableParams);
        		
        		TableRow trIvPrinter = new TableRow(this);
        		trIvPrinter.setLayoutParams(tableRowParams);
        		TextView tvIvRef = new TextView(this);
        		tvIvRef.setText("");
        		tvIvRef.setTextColor(Color.parseColor("#000000"));
        		trIvPrinter.addView(tvIvRef, 0);
        		
        		TableRow trName = new TableRow(this);
        		trName.setLayoutParams(tableRowParams);
        		TextView tvNameRef = new TextView(this);
        		tvNameRef.setText("Name:");
        		tvNameRef.setTextColor(Color.parseColor("#FFFFFF"));
        		trName.addView(tvNameRef, 0);
        		tvNameRef.setGravity(Gravity.CENTER);
        		trName.setGravity(Gravity.CENTER);
        		
        		TableRow trPrice = new TableRow(this);
        		trPrice.setLayoutParams(tableRowParams);
        		TextView tvPriceRef = new TextView(this);
        		tvPriceRef.setText("Price:");
        		tvPriceRef.setTextColor(Color.parseColor("#FFFFFF"));
        		trPrice.addView(tvPriceRef, 0);
        		tvPriceRef.setGravity(Gravity.CENTER);
        		trPrice.setGravity(Gravity.CENTER);
        		
        		TableRow trIcpp = new TableRow(this);
        		trIcpp.setLayoutParams(tableRowParams);
        		TextView tvIcppRef = new TextView(this);
        		tvIcppRef.setText("ICPP:");
        		tvIcppRef.setTextColor(Color.parseColor("#FFFFFF"));
        		trIcpp.addView(tvIcppRef, 0);
        		tvIcppRef.setGravity(Gravity.CENTER);
        		trIcpp.setGravity(Gravity.CENTER);
        		
        		TableRow trTpc = new TableRow(this);
        		trTpc.setLayoutParams(tableRowParams);
        		TextView tvTpcRef = new TextView(this);
        		tvTpcRef.setText("TPC:");
        		tvTpcRef.setTextColor(Color.parseColor("#FFFFFF"));
        		trTpc.addView(tvTpcRef, 0);
        		tvTpcRef.setGravity(Gravity.CENTER);
        		trTpc.setGravity(Gravity.CENTER);
        		
        		TableRow trTco = new TableRow(this);
        		trTco.setLayoutParams(tableRowParams);
        		TextView tvTcoRef = new TextView(this);
        		tvTcoRef.setText("TCO:");
        		tvTcoRef.setTextColor(Color.parseColor("#FFFFFF"));
        		trTco.addView(tvTcoRef, 0);
        		tvTcoRef.setGravity(Gravity.CENTER);
        		trTco.setGravity(Gravity.CENTER);
            	
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
            		trIvPrinter.addView(ivPrinter, i + 1);
            		
            		tvName.setPadding(10, 0, 10, 0);
            		tvName.setWidth(400);
            		tvName.setText(resultDd.getTitle());
            		trName.addView(tvName, i + 1);
            		tvName.setGravity(Gravity.CENTER);
            		
            		BigDecimal printerPrice = new BigDecimal(resultDd.getPrice()).setScale(2,BigDecimal.ROUND_HALF_UP);
            		tvPrice.setText("$ " + printerPrice.toString());
            		trPrice.addView(tvPrice, i + 1);
            		tvPrice.setGravity(Gravity.CENTER);
            		
            		BigDecimal icpp = new BigDecimal(resultDd.getTcpp()).setScale(6,BigDecimal.ROUND_HALF_UP);
            		tvIcpp.setText("$ " + icpp.toString());
            		trIcpp.addView(tvIcpp, i + 1);
            		tvIcpp.setGravity(Gravity.CENTER);
            		
            		double tpc = resultDd.getTcpp() * PrintersUtilRef.getPrintPeriod() * PrintersUtilRef.getPrintVolume();
            		BigDecimal tpcD = new BigDecimal(tpc).setScale(2, BigDecimal.ROUND_HALF_UP);
            		tvTpc.setText("$ " + tpcD.toString());
            		trTpc.addView(tvTpc, i + 1);
            		tvTpc.setGravity(Gravity.CENTER);
            		tipccArray.add(tvTpc);
            		
            		double totalCost = tpc + resultDd.getPrice();
            		BigDecimal totalCostD = new BigDecimal(totalCost).setScale(2, BigDecimal.ROUND_HALF_UP); 
            		tvTco.setText("$ " + totalCostD.toString());
            		trTco.addView(tvTco, i + 1);
            		tvTco.setGravity(Gravity.CENTER);
            		totalCostArray.add(tvTco);
            		
            		PrintersUtilRef.addPrinterResultData(i, resultDd);
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
		for(int i = 0; i < tipccArray.size(); i ++)
		{
			ResultData rd = PrintersUtilRef.getPrinterResultData(i);
			
			double tpc = rd.getTcpp() * period * volume;
			BigDecimal tpcd = new BigDecimal(tpc).setScale(2, BigDecimal.ROUND_HALF_UP);
			tipccArray.get(i).setText("$ " + tpcd.toString());
			
			double toc = tpc + rd.getPrice();
			BigDecimal tocD = new BigDecimal(toc).setScale(2, BigDecimal.ROUND_HALF_UP);
			totalCostArray.get(i).setText("$ " + tocD.toString());
		}
				
	}
	
	public void calculatePrintCost(View v)
	{
		if(v.getId() == R.id.ivVolome  || v.getId() == R.id.trRsVolome)
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
            	
            	String url = String.valueOf(R.string.RetrievePictureInfo);
            	URI retrieveData  = new URI(url);
            	
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost("http://15.125.96.127/picc/cmyk/image");
 
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

	