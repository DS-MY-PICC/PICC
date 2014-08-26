package com.hp.hppicc;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ResultActivity extends Activity {
	
	String filePath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		
		ActionBar ab = getActionBar();
		ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0096d6")));
		
		String printer = getIntent().getStringExtra("printer");
		filePath = getIntent().getStringExtra("filePath");
		String pageSize = getIntent().getStringExtra("pageSize");
		String imageResolution = getIntent().getStringExtra("imageResolution");
		String printMode = getIntent().getStringExtra("printMode");
		
		TextView tvResult = (TextView)findViewById(R.id.tvResult);
		tvResult.setText("printer: " + printer + "\n" +
							"filePath: " + filePath + "\n" + 
							"page Size: " + pageSize + "\n" +
							"image Resolution: " + imageResolution + "\n" + 
							"printmode: " + printMode + "\n" 
							);
	}
}
