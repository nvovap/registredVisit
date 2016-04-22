package com.nvovap.visitregisration;

import java.util.ArrayList;
import java.util.UUID;

import android.app.Activity;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

public class OutletListFragment extends ListFragment {
	private static final String TAG = "OutletListFragment";
	
	public static final String EXTRA_SELECT_OUTLET  = "com.nvovap.android.outlet.select_outlet";
	
	//private static final int REQUEST_OUTLET = 1;
	
	private ArrayList<Outlet> mOutlets;	
	private ArrayList<Outlet> mStaticOutlets;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setHasOptionsMenu(true);
		
		mOutlets = OutletLab.get(getActivity()).getOutlets();
		
		mStaticOutlets = new ArrayList<Outlet>();
		
		for(Outlet cOtlet :mOutlets)
			mStaticOutlets.add(cOtlet);
		
		OutletAdapter adapter = new OutletAdapter(mStaticOutlets);
		setListAdapter(adapter);
	}
	
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_outlet_list, menu);	
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			MenuItem searchItem = menu.findItem(R.id.menu_item_search);
			SearchView mSearchView = (SearchView)searchItem.getActionView();
			
			SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
			
			ComponentName name = getActivity().getComponentName();
			SearchableInfo searchInfo = searchManager.getSearchableInfo(name);
			
			
			
			mSearchView.setSearchableInfo(searchInfo);
			
			
			mSearchView.setOnQueryTextListener(new OnQueryTextListener() {
				
				@Override
				public boolean onQueryTextSubmit(String query) {
					// TODO Auto-generated method stub
					return false;
				}
				
				@Override
				public boolean onQueryTextChange(String newText) {
					((OutletAdapter)getListAdapter()).getFilter().filter(newText);
					return false;
				}
			});
			
		
			
			
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		
		switch (item.getItemId()) {
		/*case R.id.menu_item_new_outlet:
			
			
			Intent intent = new Intent(getActivity(),OutletActivity.class);
			startActivityForResult(intent, REQUEST_OUTLET);
			
			return true;*/
		case R.id.menu_item_search:
			getActivity().onSearchRequested();
			return true;
		case R.id.menu_item_clear:
			
			PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putString("SEARCH_QUERY", null).commit();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.i(TAG, "Resume");
		((OutletAdapter)getListAdapter()).notifyDataSetChanged();
		
		
		
		//String query = PreferenceManager.getDefaultSharedPreferences(getActivity())
		//		.getString("SEARCH_QUERY", null);
		
		//((OutletAdapter)getListAdapter()).getFilter().filter(query);
	
	}
	

	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
	    Outlet outlet = ((OutletAdapter)getListAdapter()).getItem(position);
		
		/*Intent intent = new Intent(getActivity(),VisitActivity.class);
		intent.putExtra(OutletFragment.EXTRA_OUTLET_ID, outlet.getId());
		
		startActivityForResult(intent, REQUEST_OUTLET);*/
		
		///////////////////////////////////
		Intent i = new Intent();
		
		UUID idOutlet = outlet.getId();
		
		i.putExtra(EXTRA_SELECT_OUTLET, idOutlet);
		getActivity().setResult(Activity.RESULT_OK, i);
		getActivity().finish();
	}
	
	private class OutletAdapter extends ArrayAdapter<Outlet>
	{
		public OutletAdapter(ArrayList<Outlet> Outlets)
		{
			super(getActivity(),0,Outlets);
		}
		
		@Override
		public Filter getFilter() 
		{
			return new Filter() 
			{

				@Override
				protected FilterResults performFiltering(CharSequence constraint) {
					FilterResults results = new FilterResults();

					ArrayList<Outlet> list = new ArrayList<Outlet>();	
				
					
					
					
					
		            if(constraint == null || constraint.length() == 0)
		            {	
		            	results.values = mOutlets;
	                    results.count  = mOutlets.size();
		            }
		            else
		            {
		            	
		            	 for (Outlet outlet : mOutlets) {
		            		 
		            		 String s = outlet.getClient();
		                     //String[] name = s.split(" ");
		 
		                      //for (String aName : name) {
		                        if (s.toUpperCase().startsWith(constraint.toString().toUpperCase())){
		                              list.add(outlet);
		                         }		 
		                       // }
		                        
		            	 }
		            	 
		            	 results.values = list;
	                     results.count = list.size();
		            }

					return results;
				}

				@Override
				protected void publishResults(CharSequence constraint,
						FilterResults results) {
					
					@SuppressWarnings("unchecked")
					ArrayList<Outlet> tempOutlets = (ArrayList<Outlet>) results.values;
					
					
					clear();
					
					
					for(Outlet currentOutlet : tempOutlets)
					{				
						add(currentOutlet);
					}	
					
					
				
					notifyDataSetChanged();
					Log.i(TAG, mOutlets.toString());
				}

				
			};			
		}
		
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if (convertView==null) 
			{
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_outlet, null);
				
			}
			
			
			Outlet o = getItem(position);
			
			TextView name = (TextView)convertView.findViewById(R.id.outlet_list_item_nameTextView);
			name.setText(o.getClient());
			
			TextView adres = (TextView)convertView.findViewById(R.id.outlet_list_item_adresTextView);
			adres.setText(o.getAddress());
			
			
		
			/*if (o.getLocation() != null)
			{
				TextView loc = (TextView)convertView.findViewById(R.id.outlet_list_item_locationTextView);
				loc.setText(o.getLocation().toString());
				
				
			}*/
			
			
			
			
			return convertView;
		}
	}
}
