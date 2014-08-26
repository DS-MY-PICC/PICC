package com.hp.hppicc;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.IvParameterSpec;

import org.apache.http.client.methods.HttpGet;

import com.hp.hppicc.httpPostGet.HttpPostGet;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class OptionsActivity extends Activity {
	
	HttpPostGet httpPG;
	private String filePath;
	private String printerId;
		
	TextView tvPrinter;
	Spinner spinner2;
	int printerImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		try{
		httpPG = new HttpPostGet();
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
		
		ActionBar ab = getActionBar();
		ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0096d6")));
		
		printerId = getIntent().getStringExtra("printer");
		filePath = getIntent().getStringExtra( "filePath" );
		printerImage = getIntent().getIntExtra("printImage", R.drawable.oofficejet6600);
		
		Log.d("Optionsitems", "printer: " + printerId + "\n" + "filePath: " + filePath );
		
		if(filePath != null && !filePath.isEmpty())
		{		
			//display Picture
			// bimatp factory
	        BitmapFactory.Options options = new BitmapFactory.Options();
	        // downsizing image as it throws OutOfMemory Exception for larger
	        // images
	        options.inSampleSize = 8;
	        final Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
	        ImageView ivPic = (ImageView)findViewById(R.id.ivPic);
	        ivPic.setImageBitmap(bitmap);
		}
		
		final Spinner spPageSize = (Spinner)findViewById(R.id.spPageSize);
		spPageSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				httpPG.setPageSize( spPageSize.getSelectedItem().toString() );
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
		
		final Spinner spImageResoultion = (Spinner)findViewById(R.id.spImageResolution);
		spImageResoultion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				httpPG.setImageResolution(spImageResoultion.getSelectedItem().toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		final Spinner spPrintVolume = (Spinner)findViewById(R.id.spPrintVolume);
		spPrintVolume.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				httpPG.setPrintVolume(Integer.parseInt( spPrintVolume.getSelectedItem().toString() ) );
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		final Spinner spPrintMode = (Spinner)findViewById(R.id.spPrintMode);
		spPrintMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				httpPG.setPrintMode(spPrintMode.getSelectedItem().toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		
		final Spinner spPrintPeriod = (Spinner)findViewById(R.id.spPrintPeriod);
		spPrintPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				httpPG.setPrintPeriod(spPrintPeriod.getSelectedItem().toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void onClickNext(View v)
	{
		continuePrinter(filePath);
	}
	
	private void continuePrinter(String filePath)
	{
		Intent ResultItems = new Intent(getApplicationContext(), ResultActivity.class);
		ResultItems.putExtra("printer", printerId);
		ResultItems.putExtra("filePath", filePath);
		ResultItems.putExtra("pageSize", httpPG.getPageSize());
		ResultItems.putExtra("imageResolution", httpPG.getImageResolution());
		ResultItems.putExtra("printMode", httpPG.getPrintMode());
		ResultItems.putExtra("printVolume", httpPG.getPrintVolume());
		ResultItems.putExtra("printPeriod", httpPG.getPrintPeriod());
		ResultItems.putExtra("printerImage", printerImage);
		
		Log.d("hp File Path", "HPPICC file Path " + filePath);
		startActivity(ResultItems);
	}
}
