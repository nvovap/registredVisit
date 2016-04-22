package com.nvovap.visitregisration;

import java.util.UUID;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class OutletFragment extends Fragment {
	private static final String TAG = "OutletFragment";
	public static final String EXTRA_OUTLET_ID = "com.nvovap.android.outlet.outlet_id";
	
	
	
	private Outlet mOutlet;
	private Boolean mItIsNew;
	
	
	
	public static OutletFragment newInstance(UUID outletId) 
	{
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_OUTLET_ID, outletId);
				
		OutletFragment fragment = new OutletFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setHasOptionsMenu(true);
		
		UUID outletId = (UUID)getArguments().getSerializable(EXTRA_OUTLET_ID);
		
		if(outletId == null)
		{
			mItIsNew = true;
			mOutlet = new Outlet();
			
		}
		else
		{
			mOutlet = OutletLab.get(getActivity()).getOutlet(outletId);
			mItIsNew = false;
		}
		
		
		
		Log.d(TAG, "onCreate");
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) 
		{
		case android.R.id.home:
			
			
			if (NavUtils.getParentActivityName(getActivity()) != null) 
			{
				getActivity().finish();				
			}
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
		
	}
	
	 @SuppressLint("NewApi") @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		
		Log.d(TAG, "onCreateView");
		
		
		View v = inflater.inflate(R.layout.fragment_outlet, container, false);
		
		
		if(Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB)
		{
			if(NavUtils.getParentActivityName(getActivity()) != null)
			{
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			}
		}
		
		EditText mNameField = (EditText)v.findViewById(R.id.outlet_name);	
		mNameField.setText(mOutlet.getName());
		
		mNameField.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mOutlet.setName(s.toString());
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		EditText mAdresField = (EditText)v.findViewById(R.id.outlet_address);	
		mAdresField.setText(mOutlet.getAddress());
		
		mAdresField.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mOutlet.setAddress(s.toString());
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		EditText mClientField = (EditText)v.findViewById(R.id.outlet_client);	
		mClientField.setText(mOutlet.getClient());
		
		mClientField.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mOutlet.setClient(s.toString());
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		Button mSave = (Button)v.findViewById(R.id.saveOutlet_Button);
		mSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getActivity().finish();				
			}
		});
		
		return v;
		
	}
	 
	 @Override
	public void onPause() {
		super.onPause();
		
		
		if (mOutlet.getName()!=null)
		{
			if (mOutlet.getName().length()>0)
			{
				if(mItIsNew )
				{
				
					OutletLab.get(getActivity()).addOutlet(mOutlet);
					mItIsNew = false;
				}
			
				OutletLab.get(getActivity()).saveOutlets();
			}
		}
		
			
		OutletLab.get(getActivity()).saveOutlets();	
		
		Log.d(TAG, "onPause");
	}

}
