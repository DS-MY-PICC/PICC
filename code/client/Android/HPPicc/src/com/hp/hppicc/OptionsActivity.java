package com.hp.hppicc;

import com.hp.hppicc.httpPostGet.HttpPostGet;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class OptionsActivity extends Activity {
	
	HttpPostGet httpPG;
	
	static String[] pageSize = {
			"A4",
			"A0",
			"A1",
			"A2",
			"A3"
	};
	
	static String[] paperType = {
		"Normal Paper",
		"Glossy Paper"
	};
	
	static String[] imageResolution = {
		"Actual Size",
		"300 DPI"
	};
	
	static String[] printMode={
		"Color",
		"Grey Scale"
	};
	
	static String[] printVolume= {
		"1",
		"10",
		"50",
		"100"
	};
	
	static String[] printPeriod = {
		"2 days",
		"15 days",
		"30 days",
		"60 days"
	};
	
	TextView tvPrinter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		httpPG = new HttpPostGet();
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_options);
		
		ActionBar ab = getActionBar();
		ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0096d6")));
		
		String printer = getIntent().getStringExtra("printer");
		tvPrinter = (TextView)findViewById(R.id.tvPrinter);
		tvPrinter.setText(printer);
		
		final Spinner spPageSize = (Spinner)findViewById(R.id.spPageSize);
		ArrayAdapter<String> adpPageSize = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, pageSize);
		spPageSize.setAdapter(adpPageSize);
		spPageSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(),"You have selected "+pageSize[+position],Toast.LENGTH_LONG).show();
				httpPG.setPageSize( spPageSize.getSelectedItem().toString() );
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				httpPG.setPageSize( spPageSize.getSelectedItem().toString() );
//				Toast.makeText(getApplicationContext(),"You have selected " + spPageSize.getSelectedItem().toString() ,Toast.LENGTH_LONG).show();
			}
		});
		
		final Spinner spPaperType = (Spinner)findViewById(R.id.spPaperType);
		ArrayAdapter<String> adpPaperType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, paperType);
		spPaperType.setAdapter(adpPaperType);
		spPaperType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(),"You have selected "+paperType[+position],Toast.LENGTH_LONG).show();
				httpPG.setPaperType( spPaperType.getSelectedItem().toString() );
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				httpPG.setPaperType( spPaperType.getSelectedItem().toString() );
			}
		});
		
		final Spinner spImageResolution = (Spinner)findViewById(R.id.spImageResolution);
		ArrayAdapter<String> adpImageResolution = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, imageResolution);
		spImageResolution.setAdapter(adpImageResolution);
		spImageResolution.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				httpPG.setImageResolution( spImageResolution.getSelectedItem().toString() );
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				httpPG.setImageResolution( spImageResolution.getSelectedItem().toString() );
			}
		});
		
		final Spinner spPrintMode = (Spinner)findViewById(R.id.spPrintMode);
		ArrayAdapter<String> adpPrintMode = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, printMode);
		spPrintMode.setAdapter(adpPrintMode);
		spPrintMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				httpPG.setPrintMode( spPrintMode.getSelectedItem().toString() );
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				httpPG.setPrintMode( spPrintMode.getSelectedItem().toString() );
			}
		});
		
		final Spinner spPrintVolume = (Spinner)findViewById(R.id.spPrintVolume);
		ArrayAdapter<String> adpPrintVolume = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, printVolume);
		spPrintVolume.setAdapter(adpPrintVolume);
		spPrintVolume.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				httpPG.setPrintVolume( spPrintVolume.getSelectedItem().toString() );
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				httpPG.setPrintVolume( spPrintVolume.getSelectedItem().toString() );
			}
		});
		
		final Spinner spPrintPeriod = (Spinner)findViewById(R.id.spPrintPeriod);
		ArrayAdapter<String> adpPrintPeriod = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, printPeriod);
		spPrintPeriod.setAdapter(adpPrintPeriod);
		spPrintPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(),"You have selected "+printPeriod[+position],Toast.LENGTH_LONG).show();
				httpPG.setPrintPeriod( spPrintPeriod.getSelectedItem().toString() );
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				httpPG.setPrintPeriod( spPrintPeriod.getSelectedItem().toString() );
				
			}
		});
		 
	}
	
	public void onClickSubmit(View v)
	{
		
	}
}
