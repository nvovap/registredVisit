package com.nvovap.visitregisration;



import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class Preference {
	
	private static final String TAG = "Preference";
	public static final String FILENAME = "user.json";
	
	private static final String JSON_USER = "user";
	private static final String JSON_PSSWORD = "password";
	
	private String mUser;
	private String mPassword;
	
	private Context mAppContext;
	private VisitIntentJSONSerializer mSerializer;
	
	private static Preference sPreference;
	
	
	public static Preference get(Context c) 
	{
		if (sPreference == null)
		{
			sPreference = new Preference(c.getApplicationContext());
		}
		return sPreference;		
	}
	
	
	
	
	
	public Preference(JSONObject json)throws JSONException
	{	
		
		if (json.has(JSON_USER)) mUser = json.getString(JSON_USER);
		if (json.has(JSON_PSSWORD)) mPassword = json.getString(JSON_PSSWORD);
	}
	
	private Preference(Context appContext)
	{
		mAppContext = appContext;
		
		
		mSerializer = new VisitIntentJSONSerializer(mAppContext,FILENAME);
		
		try 
		{
			Preference temp;
			temp = mSerializer.loadUserAndPassword();
			
			if (temp != null)
			{
				mUser = temp.mUser;
				mPassword = temp.mPassword;
				
				temp = null;
				
			}
			else
			{
				mUser = null;
				mPassword = null;
				
			}
			
			
		} catch (Exception e) {
			Log.d(TAG, "Error loading visits: ", e);
		}
	}
	
	
	public JSONObject toJSON() throws JSONException
	{
		JSONObject json = new JSONObject();
		
		json.put(JSON_USER, mUser);
		json.put(JSON_PSSWORD, mPassword);
		
				
		return json;
	}
	
	
	public boolean savePreference() 
	{
		try
		{
			
			mSerializer.saveUserAndPassword(this);
			Log.d(TAG, "Preference saved to file");
			return true;
		}catch(Exception e)
		{
			Log.d(TAG, "Error saving Preference:", e);
			return false;
		}
		
	}
	
	
	
	public void setUser(String user) {
		mUser = user;
	}
	
	
	public void setPassword(String password) {
		mPassword = password;
	}
	
	public String getPassword() {
		return mPassword;
	}
	
	public String getUser() {
		return mUser;
	}
	
	
	//для тестов
/*	public String getUser() {
		return "Deh";
	}
	
	public String getPassword() {
		return "G28pH3UtpiD2";
	}*/
	
	
	
	
}
