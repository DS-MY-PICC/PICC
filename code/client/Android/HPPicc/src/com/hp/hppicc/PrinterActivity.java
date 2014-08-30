package com.hp.hppicc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.hp.hppicc.dataDefinition.PrinterData;
import com.hp.hppicc.dataDefinition.ResultData;
import com.hp.hppicc.util.PrintersUtilRef;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class PrinterActivity extends Activity{
	
	ListView list;
	
	String[] printerLocal = {
		"HP Officejet 6600 from Local",
		"HP Photosmart 7520",
		"Epson WorkForce WF-3520",
		"Canon Pixma MG8220",
		"HP DesignJet 30",
		"HP Color LaserJet CM6040 MFP"
	} ;
	
	String[] imageUrl = {
			"url1",
			"url2",
			"url3",
			"url4",
			"url5",
			"url6"
		};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_printer);
		
		ActionBar ab = getActionBar();
		ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0096d6")));
		
		String printer[] = getIntent().getStringArrayExtra("printer");
		int printerId[] = getIntent().getIntArrayExtra("printerId");
		ArrayList<String> localPrinterNames = this.getIntent().getStringArrayListExtra("localPrinterNames");
		
		final ArrayList<String> printerAl = new ArrayList<String>();
		final ArrayList<Integer> printerIdAl = new ArrayList<Integer>();
		
		final PrintersUtilRef pu = PrintersUtilRef.getInstance();
		if(pu.getPrinterList().size() != 0)
		{
			for(int i = 0; i < pu.getPrinterList().size(); i ++)
			{
				System.out.println("Printer Name: " + pu.getPrinterName(i));
				printerAl.add(pu.getPrinterName(i));
				printerIdAl.add( i + 1);
			}
		}
		
		CustomList adapter = new CustomList(PrinterActivity.this, printerAl, PrintersUtilRef.imageId, printerIdAl);
		list=(ListView)findViewById(R.id.list);
		list.setAdapter(adapter);
		list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
            	
            	String selectedPrinterId = (String) parent.getItemAtPosition(position);
            	Log.d("Printers", "parentItem: " + selectedPrinterId);
            	Log.d("printers", "more printers: " + list.getSelectedItem());
                
                pu.setSelectedPrinterId(Integer.parseInt(selectedPrinterId));
                
                pu.setSelectedPrinter(pu.getPrinterName(Integer.parseInt(selectedPrinterId)));
                
                pu.setSelectedPrinterImage(PrintersUtilRef.imageId[position]);
                
                for(int i = 0; i < pu.getPrinterNames().size(); i++)
                {
                	Log.d("value", "cleared:" + pu.getPrinterName(i));
                }
                
                Log.d("pu", "selectedPRinter:" + pu.getSelectedPrinter());
                Log.d("pu", "selectedPRinter:" + pu.getSelectedPrinterId());
                
                Intent options = new Intent(getApplicationContext(), OptionsActivity.class);
                startActivity(options);
            }
        });
	}		
}
