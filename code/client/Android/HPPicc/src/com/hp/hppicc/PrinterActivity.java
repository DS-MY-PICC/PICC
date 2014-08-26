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
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class PrinterActivity extends Activity{
	
	private String filePath;
	
	ListView list;
	String[] printer = {
		"HP Officejet 6600 from Local",
		"HP Photosmart 7520",
		"Epson WorkForce WF-3520",
		"Canon Pixma MG8220",
		"HP DesignJet 30"
	} ;
	Integer[] imageId = {
			R.drawable.oofficejet6600,
			R.drawable.tphotosmart7520,
			R.drawable.thepsonwf3520,
			R.drawable.focanonmg8220,
			R.drawable.fidesignjet30
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_printer);
		
		ActionBar ab = getActionBar();
		ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0096d6")));
		
		filePath = getIntent().getStringExtra("filePath");
		Log.d("hp File Path", "HPPICC file Path in Printer " + filePath);
		
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		int width = displayMetrics.widthPixels / 2;
		int height = displayMetrics.heightPixels;
		Log.d("samsung heigh", "samsung height " + height);
		Log.d("samsung width", "samsung width " + displayMetrics.widthPixels);
		
		
		String[] printerList = getIntent().getStringArrayExtra("printer");
		if(printerList != null){
			//Log.d("printer", printerList.toString());			
			printer = printerList;
		}
		
		CustomList adapter = new CustomList(PrinterActivity.this, printer, imageId);
		list=(ListView)findViewById(R.id.list);
				list.setAdapter(adapter);
				list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

		            @Override
		            public void onItemClick(AdapterView<?> parent, View view,
		                                    int position, long id) {
		            	int pos = position + 1;
		            	
		            	StringBuilder sb = new StringBuilder();
		            	sb.append(pos);
		            	String printerId = sb.toString();
		            	
		            	Log.d("printer", "printer Choose" + printer [+ position] + " position " + position + " printerId " + printerId);
//		                Toast.makeText(PrinterActivity.this, "You Clicked at " +printer[+ position] + " go to option", Toast.LENGTH_SHORT).show();
		                Intent options = new Intent(getApplicationContext(), OptionsActivity.class);
		                options.putExtra("printer", printerId);
		                options.putExtra("filePath", filePath);
		        		startActivity(options);

		            }
		        });
	}		
}
