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
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class OptionsActivity extends Activity {
	
	TextView tvPrinter;
	Spinner spinner2;
	
	ResultData rd = new ResultData();
	
	SeekBar sbPageSize;
	SeekBar sbImageResolution;
	SeekBar sbPrintVolume;
	SeekBar sbPrintPeriod;
	
	TextView tvTotalPrint;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		try{
			super.onCreate(savedInstanceState);
			overridePendingTransition(R.anim.lefttorightvisible, R.anim.lefttorightinvisible);
			setContentView(R.layout.activity_options);
			
			ActionBar ab = getActionBar();
			ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0096d6")));
			
			final PrintersUtilRef pu = PrintersUtilRef.getInstance();
			pu.setPageSize("-1");
			pu.setImageResolution("100");
			pu.setPrintPeriod(1);
			pu.setPrintVolume(1);
			pu.setPrintMode("color");
			
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
			
			final TextView tvOpPageSize = (TextView)findViewById(R.id.tvOpPageSize);
			final TextView tvOpImageResolution = (TextView)findViewById(R.id.tvOpImageResolution);
			final TextView tvOpPrintVolume = (TextView)findViewById(R.id.tvOpPrintVolume);
			final TextView tvOpPrintPeriod = (TextView)findViewById(R.id.tvOpPrintPeriod);
			tvTotalPrint = (TextView)findViewById(R.id.tvTotalPrint);
			
			SeekBar.OnSeekBarChangeListener sbCl = new OnSeekBarChangeListener() {
				
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					// TODO Auto-generated method stub
					switch (seekBar.getId()) {
					case R.id.sbPageSize:
						
						int ps = progress - 1;
						if(ps < 0)
						{
							pu.setPageSize("-1");
							tvOpPageSize.setText("Actual Size");
						}
						else
						{
							pu.setPageSize(String.valueOf(ps));
							tvOpPageSize.setText("A" + pu.getPageSize());
						}
						break;
						
					case R.id.sbImageResolution:
						int im = progress * 10;
						tvOpImageResolution.setText(String.valueOf(im) + " DPI");
						pu.setImageResolution(String.valueOf(im));
						break;
						
					case R.id.sbPrintVolume:
						if(progress > 1)
						{
							pu.setPrintVolume(progress);
							tvOpPrintVolume.setText( pu.getPrintVolume() + " pcs");
						}
						else
						{
							pu.setPrintVolume(1);
							tvOpPrintVolume.setText( pu.getPrintVolume() + " pc");
						}
						calculateTotalPrint();
						break;
						
					case R.id.sbPrintPeriod:
						if(progress > 1)
						{
							pu.setPrintPeriod(progress);
							tvOpPrintPeriod.setText(pu.getPrintPeriod() + " Days");
						}
						else
						{
							pu.setPrintPeriod(1);
							tvOpPrintPeriod.setText( pu.getPrintPeriod() + " Day");
						}
						calculateTotalPrint();
						break;

					default:
						break;
					}
				}
			};
			
			sbPageSize = (SeekBar)findViewById(R.id.sbPageSize);
			sbPageSize.setOnSeekBarChangeListener(sbCl);
			sbImageResolution = (SeekBar)findViewById(R.id.sbImageResolution);
			sbImageResolution.setOnSeekBarChangeListener(sbCl);
			sbPrintVolume = (SeekBar)findViewById(R.id.sbPrintVolume);
			sbPrintVolume.setOnSeekBarChangeListener(sbCl);
			sbPrintPeriod = (SeekBar)findViewById(R.id.sbPrintPeriod);
			sbPrintPeriod.setOnSeekBarChangeListener(sbCl);
			
			RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroup1);
			rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					Log.d("options", "optionsradio");
					switch (checkedId) {
					case R.id.rbColor:
						pu.setPrintMode("color");
						break;
						
					case R.id.rbGrayscale:
						pu.setPrintMode("grayscale");
						break;

					default:
						break;
					}
					
					Log.d("options", "optionsradio: " + pu.getPrintMode());
				}
			});
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	private void calculateTotalPrint()
	{
		int totalPrint = PrintersUtilRef.getPrintPeriod() * PrintersUtilRef.getPrintVolume();
		String days = " Days";
		String pcs = " pcs";
		
		if(PrintersUtilRef.getPrintPeriod() == 1)
			days = " Day";
		if(totalPrint == 1 )
			pcs = " pc";
		
		tvTotalPrint.setText( String.valueOf(totalPrint) + pcs + " in \n" + PrintersUtilRef.getPrintPeriod() + days);
	}
	
	public void onClickNext(View v)
	{
		Intent ResultItems = new Intent(getApplicationContext(), ResultActivity.class);
		startActivity(ResultItems);
		overridePendingTransition(R.anim.lefttorightvisible, R.anim.lefttorightinvisible);
	}
	
	public void seekBarView(View v)
	{
		switch (v.getId()) {
		case R.id.ivOpPageSize:
		case R.id.trOpPageSize:
			sbPageSize.setVisibility(View.VISIBLE);
			sbImageResolution.setVisibility(View.GONE);
			sbPrintVolume.setVisibility(View.GONE);
			sbPrintPeriod.setVisibility(View.GONE);
			break;
			
		case R.id.ivOpImageResolution:
		case R.id.trOpImageResolution:
			sbPageSize.setVisibility(View.GONE);
			sbImageResolution.setVisibility(View.VISIBLE);
			sbPrintVolume.setVisibility(View.GONE);
			sbPrintPeriod.setVisibility(View.GONE);
			break;
			
		case R.id.ivOpPrintVolume:
		case R.id.trOpPrintVolume:
			sbPageSize.setVisibility(View.GONE);
			sbImageResolution.setVisibility(View.GONE);
			sbPrintVolume.setVisibility(View.VISIBLE);
			sbPrintPeriod.setVisibility(View.GONE);
			break;
			
		case R.id.ivOpPrintPeriod:
		case R.id.trOpPrintPeriod:
			sbPageSize.setVisibility(View.GONE);
			sbImageResolution.setVisibility(View.GONE);
			sbPrintVolume.setVisibility(View.GONE);
			sbPrintPeriod.setVisibility(View.VISIBLE);
			break;

		default:
			break;
		}
	}
}
