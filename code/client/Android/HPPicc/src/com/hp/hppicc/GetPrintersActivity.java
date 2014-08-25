package com.hp.hppicc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class GetPrintersActivity extends Activity {

	private ProgressDialog progressDialog;
	private HttpAsyncTask mTask = null;

	String[] printer = { "HP Officejet 6600 from Local", "HP Photosmart 7520",
			"Epson WorkForce WF-3520", "Canon Pixma MG8220", "HP DesignJet 30" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_printers);

		if (!isConnected()) {
			Toast.makeText(this, "Not Connected to Internet", Toast.LENGTH_LONG).show();
			Intent options = new Intent(getApplicationContext(),PrinterActivity.class);
			options.putExtra("printer", printer);
			startActivity(options);
			finish();
		} else {
			getPrinters();
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
			progressDialog = new ProgressDialog(GetPrintersActivity.this);
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
					JSONArray printers = new JSONArray(result);
					for (int i = 0; i < printers.length(); i++) {
						// Log.d("Printer Name",
						// String.valueOf(printers.length()));
						printer[i] = printers.getJSONObject(i).getString("title");
						Log.d("Printer Name: ", printer[i]);
					}

					progressDialog.dismiss();
					setContentView(R.layout.activity_get_printers);
					Intent options = new Intent(getApplicationContext(),PrinterActivity.class);
					options.putExtra("printer", printer);
					startActivity(options);
					finish();

				} catch (Exception ex) {
					Log.e("Server Connection", ex.getMessage());
					Toast.makeText(getBaseContext(),
							"Error In Making Request to Server",
							Toast.LENGTH_LONG).show();
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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mTask != null && mTask.getStatus() != HttpAsyncTask.Status.FINISHED) {
			mTask.cancel(true);
			mTask = null;
		}
	}
}
