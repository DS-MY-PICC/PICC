package com.hp.hppicc;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MultipleCustomPrinter extends ArrayAdapter<String> {

	private Activity context;
	private ArrayList<String> printer = new ArrayList<String>();
	private ArrayList<Integer> imageId;
	private ArrayList<Integer> printerId =  new ArrayList<Integer>();
	
	public MultipleCustomPrinter(Activity context, ArrayList<String> printer, ArrayList<Integer> imageId, ArrayList<Integer> printerId) {
		super(context, R.layout.activity_list, printer);
		// TODO Auto-generated constructor stub
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
		imageView.setImageResource(imageId.get(position));
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

}
