package com.nvovap.visitregisration;

import java.util.ArrayList;
import java.util.UUID;




import android.content.Context;
import android.util.Log;

public class VisitLab 
{
	private static final String TAG = "VisitLab";
	public static final String FILENAME = "visits.json";
	
	private ArrayList<Visit> mVisits;
	
	private VisitIntentJSONSerializer mSerializer;
	
	private static VisitLab sVisitLab;
	
	private Context mAppContext;
	
	
	
	public static VisitLab get(Context c) 
	{
		if (sVisitLab == null)
		{
			sVisitLab = new VisitLab(c.getApplicationContext());
		}
		return sVisitLab;		
	}
	
	
	private VisitLab(Context appContext)
	{
		mAppContext = appContext;
		
		mVisits = new ArrayList<Visit>();
		
		mSerializer = new VisitIntentJSONSerializer(mAppContext,FILENAME);
		
		try 
		{
			mVisits = mSerializer.loadVisits();
		} catch (Exception e) {
			Log.d(TAG, "Error loading visits: ", e);
		}
	}
	
	
	public boolean saveVisits() 
	{
		try
		{
			
			mSerializer.saveVisits(mVisits);
			Log.d(TAG, "Visits saved to file");
			return true;
		}catch(Exception e)
		{
			Log.d(TAG, "Error saving visits:", e);
			return false;
		}
		
	}
	
	
	public void addVisit(Visit v) 
	{
		mVisits.add(v);
	}

	
	public void deleteVisit(Visit v)  
	{
		mVisits.remove(v);
	}

	public ArrayList<Visit> getVisits() {
		return mVisits;
	}

	public Visit getVisit(UUID id) 
	{
		for(Visit v: mVisits)
		{
			if(v.getId().equals(id))
			{
				return v;
			}
		}
		
		return null;
	}
	
	
}
