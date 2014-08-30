package com.hp.hppicc;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String>{
	
	private final Activity context;
	private ArrayList<String> printer = new ArrayList<String>();
	private final int[] imageId;
	private ArrayList<Integer> printerId =  new ArrayList<Integer>();

	public CustomList(Activity context, ArrayList<String> printer, int[] imageId, ArrayList<Integer> printerId) 
	{
		super(context, R.layout.activity_list, printer);
		this.context = context;
		this.printer = printer;
		this.imageId = imageId;
		this.printerId = printerId;
	
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) 
	{
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView= inflater.inflate(R.layout.activity_list, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
		TextView txtPrinterId = (TextView) rowView.findViewById(R.id.tvliId);
		
		ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
		
		txtTitle.setText(printer.get(position));
		imageView.setImageResource(imageId[position]);
		txtPrinterId.setText(String.valueOf(printerId.get(position)));
		
//		BitmapWorkerTask imageTask = new BitmapWorkerTask(imageView);
//		imageTask.execute(imageId[position]);
		
		return rowView;
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return String.valueOf(printerId.get(position));
	}
	
	private class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {

		ProgressDialog progressDialog;
		private final WeakReference<ImageView> imageViewReference;
		private String data;

		public BitmapWorkerTask(ImageView imageView) {
			// Use a WeakReference to ensure the ImageView can be garbage
			// collected
			imageViewReference = new WeakReference<ImageView>(imageView);
		}

		@Override
		protected void onPreExecute() {
			progressDialog = ProgressDialog.show(context, "",
					"Loading...", true);
		}

		// Decode image in background.
		@Override
		protected Bitmap doInBackground(String... params) {
			data = params[0];
			try {
				return BitmapFactory.decodeStream((InputStream) new URL(data)
						.getContent());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		// Once complete, see if ImageView is still around and set bitmap.
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			progressDialog.dismiss();
			if (imageViewReference != null && bitmap != null) {
				final ImageView imageView = imageViewReference.get();
				if (imageView != null) {
					imageView.setImageBitmap(bitmap);

				}
			}
		}
	}
	
}