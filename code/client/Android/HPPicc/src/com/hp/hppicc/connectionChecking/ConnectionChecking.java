package com.hp.hppicc.connectionChecking;

import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicBoolean;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionChecking {
	
	private Context context;
    
    public ConnectionChecking(Context context){
        this.context = context;
    }
 
    public boolean isConnectingToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
          if (connectivity != null)
          {
              NetworkInfo[] info = connectivity.getAllNetworkInfo();
              if (info != null)
                  for (int i = 0; i < info.length; i++)
                      if (info[i].getState() == NetworkInfo.State.CONNECTED)
                      {
                          return true;
                      }
 
          }
          return false;
    }
    
    public boolean isInternetAvailable() {
    	
    	final AtomicBoolean internetConn = new AtomicBoolean();
    	Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
		            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name

		            if (ipAddr.equals("")) {
		            	internetConn.set(true);
		            } else {
		                internetConn.set(false);
		            }

		        } catch (Exception e) {
		        	e.printStackTrace();
		        	internetConn.set(false);
		        }
			}
		});
        
    	t.start();
    	try
    	{
    		t.join();
    	}
    	catch(InterruptedException e)
    	{
    		e.printStackTrace();
    	}
    	
    	return internetConn.get();

    }
}
