package com.nvovap.visitregisration;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;
import android.util.Log;

public class OutletLab {
	
	private static final String TAG = "OutletLab";
	public static final String FILENAME = "outlets.json";
	
	private ArrayList<Outlet> mOutlets;
	
	private VisitIntentJSONSerializer mSerializer;
	
	private static OutletLab sOutletLab;
	
	private Context mAppContext;
	
	
	
	public static OutletLab get(Context c) 
	{
		if (sOutletLab == null)
		{
			sOutletLab = new OutletLab(c.getApplicationContext());
		}
		return sOutletLab;		
	}
	
	public static OutletLab getNew(Context c) 
	{
		
		sOutletLab = new OutletLab(c.getApplicationContext());
		
		return sOutletLab;		
	}
	
	
	private OutletLab(Context appContext)
	{
		mAppContext = appContext;
		
		mOutlets = new ArrayList<Outlet>();
		
		mSerializer = new VisitIntentJSONSerializer(mAppContext,FILENAME);
		
		try 
		{
			mOutlets = mSerializer.loadOutlets();
		} catch (Exception e) {
			Log.d(TAG, "Error loading outlets: ", e);
		}
	}
	
	
	public boolean saveOutlets() 
	{
		try
		{
			
			mSerializer.saveOutlets(mOutlets);
			Log.d(TAG, "Outlets saved to file");
			return true;
		}catch(Exception e)
		{
			Log.d(TAG, "Error saving outlets:", e);
			return false;
		}
		
	}
	
	
	public void addOutlet(Outlet o) 
	{
		mOutlets.add(o);
	}
	
	public void deleteOutlet(Outlet o) 
	{
		mOutlets.remove(o);
	}


	public ArrayList<Outlet> getOutlets() {
		return mOutlets;
	}

	public Outlet getOutlet(UUID id) 
	{
		for(Outlet o: mOutlets)
		{
			if(o.getId().equals(id))
			{
				return o;
			}
		}
		
		return null;
	}

}
