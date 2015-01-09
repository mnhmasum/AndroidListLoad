package com.example.androidlistload;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


/**
 * @author Nazmul Hasan Masum
 */
public class MainActivity extends ActionBarActivity implements OnTouchListener{
	
	private TextView txtViewFooter;
	private TextView txtViewHeader;
	private ListView listView;
	private ArrayAdapter<String> adapter;
	private View footerView;
	private View headerView;
	private boolean isloading = false;
	private boolean isFirstTime = true;
	private boolean isVisibleOne = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listView1);
		listView.setOnTouchListener(this);
		
		footerView =  ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.footer_view, null, false);
		txtViewFooter = (TextView) footerView.findViewById(R.id.textView1);
		txtViewFooter.setVisibility(View.GONE);
		listView.addFooterView(footerView);
		
		headerView =  ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.header_view, null, false);
		txtViewHeader = (TextView) headerView.findViewById(R.id.textViewHeader);
		txtViewHeader.setVisibility(View.GONE);
		listView.addHeaderView(headerView);
		
		
		String[] countries = new String[] { "Bangladesh", "Bhutan", "Canada",
				"Denmark", "England", "France", "Holland", "India", "Korea" };
	     
	    ArrayList<String> planetList = new ArrayList<String>();  
	    planetList.addAll( Arrays.asList(countries) );

	    adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, planetList);
		adapter.add("Firefox");
		adapter.add("Tizen");
		adapter.add("Bada");
		listView.setAdapter(adapter);
		
		listView.setOnScrollListener(new OnScrollListener() {
		    @Override
		    public void onScrollStateChanged(AbsListView view, int scrollState) {
		    	//Log.e("SCROOL STATE", "****" + scrollState);
		    	if (scrollState == 2) {
		    		isFirstTime = false;
		    	}
		    }

		    @Override
		    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		    	if (isFirstTime) {
		    		return;
		    	}
		       
		       if(firstVisibleItem+visibleItemCount == totalItemCount && totalItemCount!=0) {
		    	   if (firstVisibleItem == 0) {
		    		   isVisibleOne = true;
		    	   } else {
		    		   isVisibleOne = false;
		    	   }
	                if(isloading == false) {
	                    isloading = true;
	                    txtViewFooter.setVisibility(View.VISIBLE);
	                    //Log.e("SCROOL STATE DOWN", "****" + "OK");
	                    new getAsyncTask().execute();
	                }
	            }
		       
		    }
		    
		});
		
		/*@Override
		public boolean onTouchEvent(MotionEvent event) {
		    
		}*/
		
		
	}
	
	
	private class getAsyncTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String[] result) {
			adapter.add("new item add after loading...");
			adapter.notifyDataSetChanged();
			isloading = false;
			txtViewFooter.setVisibility(View.GONE);
			super.onPostExecute(result);
		}
		
	}
	
	private class getAsyncTask1 extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e) {
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(String[] result) {
			adapter.add("new item add after loading...");
			adapter.notifyDataSetChanged();
			isloading = false;
			txtViewHeader.setVisibility(View.GONE);
			super.onPostExecute(result);
		}
		
	}

	 float y = 0.0f;
	 float x = 0.0f;
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		//return false;
		//System.out.println("First ["+this.getFirstVisiblePosition()+"]");
	     
	   
	   // Log.e("Start", "*****" + y);
	 
	    switch (event.getAction()) {
	        case MotionEvent.ACTION_MOVE: {
	        	Log.e("Move:", "****" + event.getY());
	        	x = event.getY();
	            /*if ( ( y - startY) > THRESHOLD && STATE_REFRESH_ENABLED && !STATE_REFRESHING ) {
	            }*/
	        }
	        break;
	        case MotionEvent.ACTION_DOWN: {
	            //startY = y;
	           // STATE_REFRESH_ENABLED = getFirstVisiblePosition() == 0; // We are on the first element so we can enable refresh
	            //System.out.println("First ["+this.getFirstVisiblePosition()+"]");
	        	y = event.getY();
	            Log.e("current_Down", "****" + event.getY());
	        }
	        case MotionEvent.ACTION_UP: {
	            //STATE_REFRESHING = false;
	        	if (x + 100 > y) {
	        		if (isVisibleOne) {
	        			txtViewHeader.setVisibility(View.VISIBLE);
		        		y = 0.0f;
		        		x = 0.0f;
		        		new getAsyncTask1().execute();
	        		}
	        		
	        	}
	        	
	        	Log.e("current_Up", "****" + event.getY());
	        }
	       
	        break;
	 
	    }
	    return super.onTouchEvent(event);
	}
	
}
