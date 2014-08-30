package com.hp.hppicc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import com.hp.hppicc.dataDefinition.PrinterData;
import com.hp.hppicc.dataDefinition.ResultData;
import com.hp.hppicc.util.PrintersUtilRef;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MultiplePrinterActivity extends Activity {
	
	ListView multipleListView;
	private HttpAsyncTask mTask = null;
	
	ResultData rd;
	
	ArrayList<String> printerMulAl = new ArrayList<String>();
	ArrayList<Integer> printerMulIdAl = new ArrayList<Integer>();
	ArrayList<Integer> imageMulIdAl = new ArrayList<Integer>();
	
	final ArrayList<String> passMultiple = new ArrayList<String>();
	
	ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multiple_printer);
		overridePendingTransition(R.anim.lefttorightvisible, R.anim.lefttorightinvisible);
		
		ActionBar ab = getActionBar();
		ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0096d6")));
		
		rd = (ResultData)getIntent().getParcelableExtra("rd");
		
//		passMultiple.add(String.valueOf(resultDd.getId()));
		
		ImageView ivMpChoosen = (ImageView)findViewById(R.id.ivMpChoosen);
		int imgChoosen = rd.getId() - 1;
		ivMpChoosen.setImageResource(PrintersUtilRef.imageId[imgChoosen]);
		
		TextView tvMpPrChoosen = (TextView)findViewById(R.id.tvMpPrChoosen);
		tvMpPrChoosen.setText(rd.getTitle());
		
		multipleListView = (ListView)findViewById(R.id.lvOthers);
		
		String result = "[{\"id\":1,\"brand\":\"HP\",\"type\":\"Color Inkjet\",\"title\":\"HP Officejet 6600\",\"image\":\"http://15.125.96.127:8080/upload/printer/oofficejet6600.png\",\"price\":129.0,\"url\":\"\",\"ppi\":\"300|600\",\"functions\":\"Print,Copy,Scan,Fax,Web\",\"iccFile\":\"adobe/CMYK/WebCoatedFOGRA28.icc\",\"printSpeedBlack\":18.0,\"printSpeedColor\":13.0,\"maxInputCapacity\":250,\"maxMonthlyDutyCycle\":12000,\"autoDuplex\":\"Yes\",\"inkCyanPrice\":10.99,\"inkCyanYield\":330,\"inkMagentaPrice\":10.99,\"inkMagentaYield\":330,\"inkYellowPrice\":10.99,\"inkYellowYield\":330,\"inkBlackPrice\":19.99,\"inkBlackYield\":400},{\"id\":2,\"brand\":\"HP\",\"type\":\"Color Inkjet\",\"title\":\"HP Photosmart 7520\",\"image\":\"http://15.125.96.127:8080/upload/printer/tphotosmart7520.png\",\"price\":199.99,\"url\":\"\",\"ppi\":\"300|600|900\",\"functions\":\"Print,Copy,Scan,Fax\",\"iccFile\":\"adobe/CMYK/WebCoatedFOGRA28.icc\",\"printSpeedBlack\":15.0,\"printSpeedColor\":9.3,\"maxInputCapacity\":500,\"maxMonthlyDutyCycle\":12000,\"autoDuplex\":\"Yes\",\"inkCyanPrice\":15.3,\"inkCyanYield\":750,\"inkMagentaPrice\":15.3,\"inkMagentaYield\":750,\"inkYellowPrice\":15.3,\"inkYellowYield\":750,\"inkBlackPrice\":28.85,\"inkBlackYield\":800},{\"id\":3,\"brand\":\"Epson\",\"type\":\"Color Inkjet\",\"title\":\"Epson WorkForce WF-3520\",\"image\":\"http://15.125.96.127:8080/upload/printer/thepsonwf3520.png\",\"price\":119.99,\"url\":\"\",\"ppi\":\"300|600|900\",\"functions\":\"Print,Copy,Scan,Fax\",\"iccFile\":\"adobe/CMYK/WebCoatedFOGRA28.icc\",\"printSpeedBlack\":15.0,\"printSpeedColor\":9.3,\"maxInputCapacity\":500,\"maxMonthlyDutyCycle\":12000,\"autoDuplex\":\"Yes\",\"inkCyanPrice\":17.09,\"inkCyanYield\":470,\"inkMagentaPrice\":17.09,\"inkMagentaYield\":470,\"inkYellowPrice\":17.09,\"inkYellowYield\":470,\"inkBlackPrice\":18.99,\"inkBlackYield\":385},{\"id\":4,\"brand\":\"Canon\",\"type\":\"Color Inkjet\",\"title\":\"Canon Pixma MG8220\",\"image\":\"http://15.125.96.127:8080/upload/printer/focanonmg8220.png\",\"price\":245.39,\"url\":\"\",\"ppi\":\"300|600|900\",\"functions\":\"Print,Copy,Scan,Fax\",\"iccFile\":\"adobe/CMYK/WebCoatedFOGRA28.icc\",\"printSpeedBlack\":15.0,\"printSpeedColor\":9.3,\"maxInputCapacity\":500,\"maxMonthlyDutyCycle\":12000,\"autoDuplex\":\"Yes\",\"inkCyanPrice\":13.33,\"inkCyanYield\":466,\"inkMagentaPrice\":13.33,\"inkMagentaYield\":447,\"inkYellowPrice\":13.33,\"inkYellowYield\":478,\"inkBlackPrice\":15.99,\"inkBlackYield\":311},{\"id\":5,\"brand\":\"HP\",\"type\":\"Color Inkjet\",\"title\":\"HP DesignJet 30\",\"image\":\"http://15.125.96.127:8080/upload/printer/fidesignjet30.png\",\"price\":1399.99,\"url\":\"\",\"ppi\":\"300|600|900\",\"functions\":\"Print,Copy,Scan,Fax\",\"iccFile\":\"adobe/CMYK/WebCoatedFOGRA28.icc\",\"printSpeedBlack\":15.0,\"printSpeedColor\":9.3,\"maxInputCapacity\":500,\"maxMonthlyDutyCycle\":12000,\"autoDuplex\":\"Yes\",\"inkCyanPrice\":32.78,\"inkCyanYield\":1200,\"inkMagentaPrice\":32.78,\"inkMagentaYield\":1200,\"inkYellowPrice\":32.78,\"inkYellowYield\":1200,\"inkBlackPrice\":45.99,\"inkBlackYield\":2000},{\"id\":6,\"brand\":\"HP\",\"type\":\"Color Laserjet\",\"title\":\"HP Color LaserJet CM6040 MFP\",\"image\":\"http://15.125.96.127:8080/upload/printer/laserjet_cm6040.png\",\"price\":11190.5,\"url\":\"\",\"ppi\":\"75|150|200|300\",\"functions\":\"Print,Copy,Scan\",\"iccFile\":\"adobe/CMYK/WebCoatedFOGRA28.icc\",\"printSpeedBlack\":15.0,\"printSpeedColor\":15.0,\"maxInputCapacity\":500,\"maxMonthlyDutyCycle\":12000,\"autoDuplex\":\"Yes\",\"inkCyanPrice\":90.0,\"inkCyanYield\":21000,\"inkMagentaPrice\":90.0,\"inkMagentaYield\":21000,\"inkYellowPrice\":90.0,\"inkYellowYield\":21000,\"inkBlackPrice\":90.0,\"inkBlackYield\":19500}]";
		PrintersUtilRef pu = PrintersUtilRef.getInstance();
		
		if(pu.getPrinterNames().size() < 1)
			getPrinters();
		else
			populateFromPrintersUtil();
		
//		getPrinters();
	}
	
	private void populateFromPrintersUtil()
	{
		PrintersUtilRef pu = PrintersUtilRef.getInstance();
		for (int i = 0; i < pu.getPrinterList().size(); i++) {
			Log.d("multiplePrinter", "printerId:" + pu.getPrinterIds( i ));
			Log.d("multiplePrinter", "printerIdSelected:" + pu.getSelectedPrinterId());
			if(pu.getPrinterIds( i ) != pu.getSelectedPrinterId())
			{
				printerMulAl.add(pu.getPrinterName(i));
				printerMulIdAl.add(pu.getPrinterIds(i));
				imageMulIdAl.add(PrintersUtilRef.imageId[i]);
			}
		}
		populateMultipleList();
	}
	
	private void populateMultiplePrinters(String result) {
		try {
			Log.d("getPrinters", "printersValue Json: " + result);
			int[] image = new int[5];
			JSONArray printers = new JSONArray(result);
			ArrayList<String> localPrinterNames = new ArrayList<String>();
			
			for (int i = 0; i < printers.length(); i++) {
				PrinterData pd = new PrinterData(printers.getJSONObject(i));
				Log.d("getprinters", "printerChoosen: " + rd.getId());
				Log.d("getprinters", "printerChoosen pd: " + pd.getId());
				if(pd.getId() != rd.getId())
				{
					PrintersUtilRef.addPrinterName(i, pd.getTitle());
					PrintersUtilRef.addPrinterIds(i, pd.getId());
					printerMulAl.add(pd.getTitle());
					printerMulIdAl.add(pd.getId());
					imageMulIdAl.add(PrintersUtilRef.imageId[i]);
//						image[i] = imageId[i];
				}
			}
			
			populateMultipleList();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			Log.e("error", ex.getMessage());
		}
	}
	
	
	
	private void populateMultipleList()
	{
		MultipleCustomPrinter adapter = new MultipleCustomPrinter(MultiplePrinterActivity.this, printerMulAl, imageMulIdAl, printerMulIdAl);
		multipleListView.setAdapter(adapter);
		multipleListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		multipleListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.d("list", "list.getCheckedItemPositions() " + multipleListView.getCheckedItemPositions());
            	Log.d("list", "list.getCheckedItemPositions().valueAt " + multipleListView.getCheckedItemPositions().get(position));
            	Log.d("list", "listcount" + multipleListView.getCheckedItemCount());
        		Log.d("list", "counte more 1");
        		
            	if(multipleListView.getCheckedItemPositions().get(position))
            	{
            		view.setBackgroundColor(Color.rgb(176, 200, 243));
            		
            		if(!passMultiple.contains(String.valueOf(parent.getItemAtPosition(position))))
            			passMultiple.add(String.valueOf(parent.getItemAtPosition(position)));
            		
            	}
            	else
            	{
            		view.setBackgroundColor(Color.BLACK);
            		if(passMultiple.contains(String.valueOf(parent.getItemAtPosition(position))))
            			passMultiple.remove(String.valueOf(parent.getItemAtPosition(position)));
            	}
			}
		});
	}
	
	
	
	public void onClickNext(View v)
	{
		if(passMultiple.size() > 0)
		{
			Log.d("list", "passMuliple: " + passMultiple.size());
			Collections.sort(passMultiple);
	    	String choices = String.valueOf(rd.getId());
	    	for(String pass:passMultiple)
	    	{
	    		choices += "|" + pass;
	    		if(choices.startsWith("|"))
	    			choices = choices.replace("|", "");
	    		Log.d("list", "passMuliple contains: " + choices);
	    	}
	    	Intent compare = new Intent(this, CompareActivity.class);
	    	rd.setChoices(choices);
	    	compare.putExtra("rd", (Parcelable)rd);
	    	startActivity(compare);
		}
		else
		{
			AlertDialog.Builder ad = new AlertDialog.Builder(this);
			ad.setCancelable(true);
			ad.setTitle("No Printers Choosen!");
			ad.setMessage("Please Choose 1 or more Printers from the printer list.");
			ad.setNegativeButton("OK", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	            	
	            	return;
			        
	            }
	        });
			
			ad.show();
		}
	}
	
	public static String GET(String url) {
		InputStream inputStream = null;
		String result = "";
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
			inputStream = httpResponse.getEntity().getContent();
			if (inputStream != null)
				result = convertInputStreamToString(inputStream);
			else
				result = "Did not work!";

		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}
		return result;
	}

	private static String convertInputStreamToString(InputStream inputStream) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	}

	public boolean isConnected() {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected())
			return true;
		else
			return false;
	}

	private class HttpAsyncTask extends AsyncTask<String, Integer, String> {
		// Before running code in separate thread
		@Override
		protected void onPreExecute() {
			// Create a new progress dialog
			progressDialog = new ProgressDialog(MultiplePrinterActivity.this);
			// Set the progress dialog to display a horizontal progress bar
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			// Set the dialog title to 'Loading...'
			progressDialog.setMessage("Retrieving Printers' Information");
			// This dialog can't be canceled by pressing the back key
			progressDialog.setCancelable(false);
			// This dialog isn't indeterminate
			progressDialog.setIndeterminate(false);
			progressDialog.show();
		}

		@Override
		protected String doInBackground(String... urls) {
			String result = "";
			try {
				result = GET(urls[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(String result) {
			if (isConnected()) {
				try {
					Log.d("getPrinters", "printersValue Json: " + result);
					populateMultiplePrinters(result);				
				} catch (Exception ex) {
					ex.printStackTrace();
					Log.e("Server Connection", ex.getMessage());
					Toast.makeText(getBaseContext(),
							"Error In Making Request to Server",
							Toast.LENGTH_LONG).show();
				}
				finally
				{
					if(progressDialog.isShowing())
						progressDialog.dismiss();
				}
			}
		}
	}

	private void getPrinters() {
		if (mTask != null && mTask.getStatus() != HttpAsyncTask.Status.FINISHED) {
			mTask.cancel(true);
		}
		mTask = (HttpAsyncTask) new HttpAsyncTask().execute("http://15.125.96.127:8080/picc/printer/query");
	}
}
