package com.hp.hppicc;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.IvParameterSpec;

import org.apache.http.client.methods.HttpGet;

import com.hp.hppicc.dataDefinition.ResultData;
import com.hp.hppicc.httpPostGet.HttpPostGet;
import com.hp.hppicc.util.PrintersUtilRef;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.os.Parcelable;
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
	
	TextView tvPrinter;
	Spinner spinner2;
	
	ResultData rd = new ResultData();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		try{
			super.onCreate(savedInstanceState);
			overridePendingTransition(R.anim.lefttorightvisible, R.anim.lefttorightinvisible);
			setContentView(R.layout.activity_options);
			
			ActionBar ab = getActionBar();
			ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0096d6")));
			
			PrintersUtilRef pu = PrintersUtilRef.getInstance();
			
			if(pu.getImagePath() != null && !pu.getImagePath().isEmpty())
			{		
				//display Picture
				// bimatp factory
		        BitmapFactory.Options options = new BitmapFactory.Options();
		        // downsizing image as it throws OutOfMemory Exception for larger
		        // images
		        options.inSampleSize = 8;
		        final Bitmap bitmap = BitmapFactory.decodeFile(pu.getImagePath(), options);
		        ImageView ivPic = (ImageView)findViewById(R.id.ivPic);
		        ivPic.setImageBitmap(bitmap);
			}
			else
			{
				ImageView ivPic = (ImageView)findViewById(R.id.ivPic);
		        ivPic.setImageResource(R.drawable.aaimagetesting);
			}
			
			final Spinner spPageSize = (Spinner)findViewById(R.id.spPageSize);
			spPageSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
	
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					rd.setPaper( spPageSize.getSelectedItem().toString() );
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
					rd.setDpi(spImageResoultion.getSelectedItem().toString());
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
					rd.setMode(spPrintMode.getSelectedItem().toString());
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
		continuePrinter(rd.getFilePath());
	}
	
	private void continuePrinter(String filePath)
	{
		Intent ResultItems = new Intent(getApplicationContext(), ResultActivity.class);
		ResultItems.putExtra("rd", (Parcelable)rd);
		Log.d("hp File Path", "HPPICC file Path " + filePath);
		startActivity(ResultItems);
		overridePendingTransition(R.anim.lefttorightvisible, R.anim.lefttorightinvisible);
	}
}
