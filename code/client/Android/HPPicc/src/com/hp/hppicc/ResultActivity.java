package com.hp.hppicc;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
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
		int printVolume = getIntent().getIntExtra("printVolume", 1);
		int printPeriod = getIntent().getIntExtra("printPeriod", 1);
		
		Log.d("REsult", "Print Volume " + printVolume);
		Log.d("REsult", "Print period " + printPeriod);
		
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
		
//		TextView tvResult = (TextView)findViewById(R.id.tvResult);
//		tvResult.setText("printer: " + printer + "\n" +
//							"filePath: " + filePath + "\n" + 
//							"page Size: " + pageSize + "\n" +
//							"image Resolution: " + imageResolution + "\n" + 
//							"printmode: " + printMode + "\n" 
//							);
	}
}
