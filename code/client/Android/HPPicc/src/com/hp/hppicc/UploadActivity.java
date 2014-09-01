package com.hp.hppicc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.hp.hppicc.connectionChecking.ConnectionChecking;
import com.hp.hppicc.dataDefinition.ResultData;
import com.hp.hppicc.util.PrintersUtilRef;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HeterogeneousExpandableList;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UploadActivity extends Activity {
	
	ResultData dd = new ResultData();
	
	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	private static final int SELECT_PICTURE = 101;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final String IMAGE_DIRECTORY_NAME = "Picture1";
	
	static final int REQUEST_IMAGE_CAPTURE = 1;
	private Uri fileUri;
	private String filePath;
	
	ImageView ivUploadImage;
	TextView tvAddPicture;
	TextView tvYourImage;
	
	ConnectionChecking cc;
	Handler checkConnection;
	Runnable internetConn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload);
		
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		int width = displayMetrics.widthPixels / 2;
		int height = displayMetrics.heightPixels;
		Log.d("samsung heigh", "samsung height " + height);
		Log.d("samsung width", "samsung width " + displayMetrics.widthPixels);
		
		ivUploadImage = (ImageView)findViewById(R.id.ivUploadImage);
		tvAddPicture = (TextView)findViewById(R.id.tvAddPicture);
		tvYourImage = (TextView)findViewById(R.id.tvYourImage);
		
		ActionBar ab = getActionBar();
		ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0096d6")));
		
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	public void cameraUpload(View v)
	{
		captureImage();
	}	
	
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		fileUri = savedInstanceState.getParcelable("file_uri");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		outState.putParcelable("file_uri", fileUri);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
		//from camera
		if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) 
            {
                // successfully captured the image
                // display it in image view
                previewCapturedImage();
                changeTextView();
                filePath = fileUri.getPath();
            } 
            
            else if (resultCode == RESULT_CANCELED) 
            {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "Camera Cancelled", Toast.LENGTH_SHORT)
                        .show();
            } 
            
            else 
            {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
		//from gallery
		else if(requestCode == SELECT_PICTURE)
		{
			if (resultCode == RESULT_OK)
			{
				String[] filePathColumn = { MediaStore.Images.Media.DATA };
				Uri selectedImage = data.getData();
				Log.d("file URI", "File URI Gallery: " + selectedImage.getPath());
				Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
				int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		        cursor.moveToFirst();
		        String s=cursor.getString(column_index);
		        cursor.close();
		        Log.d("file URI", "file URI Picture Path: " + s);

		        // bimatp factory
	            BitmapFactory.Options options = new BitmapFactory.Options();
	 
	            // downsizing image as it throws OutOfMemory Exception for larger
	            // images
	            options.inSampleSize = 8;
	 
	            final Bitmap bitmap = BitmapFactory.decodeFile(s, options);
	            ivUploadImage.setImageBitmap(bitmap);
	            changeTextView();
	            filePath = s;
			}
			else if (resultCode == RESULT_CANCELED) 
			{
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "Image Gallery cancelled", Toast.LENGTH_SHORT)
                        .show();
            } 
			else 
			{
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
		}
	}
	
	private void changeTextView()
	{
		tvAddPicture.setText("Image picked:");
		tvYourImage.setVisibility(View.GONE);
	}
	
	private void previewCapturedImage()
	{
		try { 
 
            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();
 
            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;
 
            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);
            
            Log.d("HPPICc", "HPPICC fileUri: " + fileUri.getPath());
            ivUploadImage.setImageBitmap(bitmap);
            
        } 
		
		catch (NullPointerException e) 
		{
            e.printStackTrace();
        }
	}

	private void captureImage() {
		
	    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);	 
	    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); 
	    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);	 
	    // start the image capture Intent
	    startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
	}
	
	public Uri getOutputMediaFileUri(int type) {
	    return Uri.fromFile(getOutputMediaFile(type));
	}
	 
	/*
	 * returning image / video
	 */
	private static File getOutputMediaFile(int type) {
	 
	    // External sdcard location
	    File mediaStorageDir = new File(
	            Environment
	                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
	            IMAGE_DIRECTORY_NAME);
	 
	    // Create the storage directory if it does not exist
	    if (!mediaStorageDir.exists()) {
	        if (!mediaStorageDir.mkdirs()) {
	            Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
	                    + IMAGE_DIRECTORY_NAME + " directory");
	            return null;
	        }
	    }
	 
	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
	            Locale.getDefault()).format(new Date());
	    File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE) 
	    {
	    	Log.d("hppicc", "file URI " + mediaStorageDir.getPath());
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator
	                + "IMG_" + timeStamp + ".jpg");
	    } 
	    else 
	    {
	        return null;
	    }
	 
	    return mediaFile;
	}
	
	private void checkInternetConnection()
	{
		cc = new ConnectionChecking(getApplicationContext());
		
		checkConnection = new Handler();
		internetConn = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				boolean statusConn = cc.isConnectingToInternet();
				boolean internetConn = false;
				
				if(statusConn)
					internetConn = cc.isInternetAvailable();
				
				if(statusConn && internetConn)
				{
					showToast(true);
					Log.d("starting", "conn success");
				}
				else
				{
//					showAlertDialog(getApplicationContext());
//					showToast(false);
					Log.d("starting", "connfaild");
				}
			}
		};
		
		checkConnection.post(internetConn);
	}
	
	public void galleryUpload(View v)
	{
		Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
         
        startActivityForResult(i, SELECT_PICTURE);
	}
	
	public void onClickNext(View v)
	{
		Log.d("HPPICC", "onClickNext " + filePath);
		if( filePath != null && !filePath.isEmpty() )
			continuePrinter(filePath);
		else
			showAlertDialog("Image Not Found!", "Please choose a picture from Gallery or Camera.");
	}
	
	public void continuePrinter(String filePath)
	{
		Intent printerSelection = new Intent(getApplicationContext(), GetPrintersActivity.class);
//		Intent printerSelection = new Intent(getApplicationContext(), PrinterActivity.class);
		
		ResultData rd = new ResultData(filePath);
		printerSelection.putExtra("rd", (Parcelable)rd);
		
		PrintersUtilRef pu = PrintersUtilRef.getInstance();
		pu.setImagePath(filePath);
		
		Log.d("hp File Path", "Upload filePath " + filePath);
		startActivity(printerSelection);
		overridePendingTransition(R.anim.lefttorightvisible, R.anim.lefttorightinvisible);
	}
	
	private void showToast(boolean status)
	{
		if(status)
			Toast.makeText(getApplicationContext(), "conencted", Toast.LENGTH_SHORT);
		else
			Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT);
	}
	
	public void showAlertDialogSingleButton(String title, String message) 
	{	
		AlertDialog.Builder ad = new AlertDialog.Builder(this);
		ad.setCancelable(true);
		ad.setTitle(title);
		ad.setMessage(message);
		ad.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	
            	return;
		        
            }
        });
		
		ad.show();

    }
	
	public void showAlertDialog(String title, String message) 
	{	
		AlertDialog.Builder ad = new AlertDialog.Builder(this);
		ad.setCancelable(true);
		ad.setTitle(title);
		ad.setMessage(message);
		ad.setNegativeButton("Gallery", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	
            	Intent i = new Intent( Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		        startActivityForResult(i, SELECT_PICTURE);
		        
            }
        });
		ad.setPositiveButton("Camera", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);	 
        	    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); 
        	    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);	 
        	    // start the image capture Intent
        	    startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
				
			}
		});
		
		ad.show();

    }
	
	public void onClickSpinner(View v)
	{
		Intent i = new Intent(this, OptionsActivity.class);
		startActivity(i);
	}
	
	public void Compare(View v)
	{
		Intent i = new Intent(this, OptionsActivity.class);
		PrintersUtilRef.setImageResolution("100 DPI");
		PrintersUtilRef.setPageSize("Actual Size");
		PrintersUtilRef.setPrintMode("Color");
		startActivity(i);
	}
}
