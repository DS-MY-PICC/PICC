package com.hp.hppicc;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class SpinnerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_spinner);
		
		TextView textView = (TextView)findViewById(R.id.textView1);
		Spinner spinner = (Spinner) findViewById(R.id.spinner1);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.pageSize, android.R.layout.simple_spinner_dropdown_item);
		
		// Bind Array Adapter
		spinner.setAdapter(adapter);
		
//		spinner.setOnItemSelectedListener(this);
	}
}
