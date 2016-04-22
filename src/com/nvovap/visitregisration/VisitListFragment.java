package com.nvovap.visitregisration;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class VisitListFragment extends ListFragment 
{
	private static final String TAG = "VisitListFragment";
	
	private static final int REQUEST_VISIT = 1;
	//private static final int REQUEST_PREFERENCE = 2;
	
	private ArrayList<Visit> mVisits;
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setHasOptionsMenu(true);
		
		mVisits = VisitLab.get(getActivity()).getVisits();
		
		VisitAdapter adapter = new VisitAdapter(mVisits);
		setListAdapter(adapter);		
	}
	
	
	@Override
	public void onResume() {
		super.onResume();
		Log.i(TAG, "Resume");
		((VisitAdapter)getListAdapter()).notifyDataSetChanged();
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Visit visit = ((VisitAdapter)getListAdapter()).getItem(position);
		
		Intent intent = new Intent(getActivity(),VisitActivity.class);
		intent.putExtra(VisitFragment.EXTRA_VISIT_ID, visit.getId());
		
		startActivityForResult(intent, REQUEST_VISIT);
		
		
	}
	
	private class VisitAdapter extends ArrayAdapter<Visit>
	{
		public VisitAdapter(ArrayList<Visit> visits)
		{
			super(getActivity(),0,visits);
		}
		
		
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			if (convertView==null) 
			{
				convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_visit, null);	
			}
			
			
			Visit v = getItem(position);
			
			TextView outlet = (TextView)convertView.findViewById(R.id.visit_list_item_outletTextView);
			if (v.getOutlet() != null) {
				outlet.setText(v.getOutlet().getName());
			}
			
			
			TextView visitDate = (TextView)convertView.findViewById(R.id.visit_list_item_visitDateTextView);
			visitDate.setText(v.getVisitDateToString());
			
			CheckBox transferred = (CheckBox)convertView.findViewById(R.id.visit_list_item_transferredCheckBox);
			
			Boolean transferred1 = true;
			if(v.getFileName1()!= null) transferred1 = v.getTransferred1();
			
			Boolean transferred2 = true;
			if(v.getFileName2()!= null) transferred2 = v.getTransferred2();
			
			Boolean transferred3 = true;
			if(v.getFileName3()!= null) transferred3 = v.getTransferred3();
			
			Boolean transferred4 = true;
			if(v.getFileName4()!= null) transferred4 = v.getTransferred4();
				
			Boolean transferred_all = transferred1&&transferred2&&transferred3&&transferred4;
			
			if( v.getFileName1()== null 
					&& v.getFileName2()== null 
					&& v.getFileName3()== null 
					&& v.getFileName4()== null)
				transferred_all = false;
			
			
			transferred.setChecked(transferred_all);
			
			
			
			return convertView;
		}
	}
	
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		
		inflater.inflate(R.menu.fragment_visit_list, menu);		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i;
		switch (item.getItemId()) {
		case R.id.menu_item_new_visit:
			
			//Visit visit = new Visit();
			//VisitLab.get(getActivity()).addVisit(visit);
			
			i = new Intent(getActivity(), VisitActivity.class);
			//i.putExtra(VisitFragment.EXTRA_VISIT_ID, );
			startActivityForResult(i, REQUEST_VISIT);
			
			return true;	
			
		case R.id.menu_item_preference:
			
			//Visit visit = new Visit();
			//VisitLab.get(getActivity()).addVisit(visit);
			
			i = new Intent(getActivity(), PreferenceActivity.class);
			//i.putExtra(VisitFragment.EXTRA_VISIT_ID, );
			startActivity(i);
			
			return true;
		
		default:
			return super.onOptionsItemSelected(item);
		}
		
	}
	
	
	

}
