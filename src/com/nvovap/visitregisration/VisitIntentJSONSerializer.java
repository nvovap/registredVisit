package com.nvovap.visitregisration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;
import android.util.Log;

public class VisitIntentJSONSerializer 
{
	private Context mContext;
	private String mFilename;
	
	private final String TAG = "JSON.serializer";
	
	
	public VisitIntentJSONSerializer(Context c,  String filename)
	{
		mContext = c;
		mFilename = filename;
	}
	
	public ArrayList<Visit> loadVisits() 
			throws IOException, JSONException
	{
		ArrayList<Visit> visits = new ArrayList<Visit>();
		BufferedReader reader = null;
		
		try
		{
			InputStream in = mContext.openFileInput(mFilename);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			
			while ((line = reader.readLine()) != null)
			{
				jsonString.append(line);
			}
			
			JSONArray array = (JSONArray) new JSONTokener(jsonString.toString())
			.nextValue();
			
			for(int i = 0; i<array.length(); i++)
			{
				visits.add(new Visit(array.getJSONObject(i)));
			}
			
		}
		catch (Exception e)
		{
			Log.d(TAG, "Error read Visit.", e);
			
		}
		finally
		{
			if(reader != null)
				reader.close();
		};

		
		
		return visits;
		
	}
	
	public void saveVisits(ArrayList<Visit> visits)
		throws JSONException, IOException
	{
		JSONArray array = new JSONArray();
		for(Visit v: visits)
		{
			array.put(v.toJSON());
		}
		
		Writer writer = null;
		try
		{
			OutputStream out = mContext.openFileOutput(mFilename,Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(array.toString());
			
		}
		catch (Exception e)
		{
			Log.d(TAG, "Error write file ", e);
		}
		finally
		{
			if(writer != null)
				writer.close();			
		}
	}
	
	
	public ArrayList<Outlet> loadOutlets() 
			throws IOException, JSONException
	{
		ArrayList<Outlet> outlets = new ArrayList<Outlet>();
		BufferedReader reader = null;
		
		try
		{
			InputStream in = mContext.openFileInput(mFilename);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			
			while ((line = reader.readLine()) != null)
			{
				jsonString.append(line);
			}
			
			JSONArray array = (JSONArray) new JSONTokener(jsonString.toString())
			.nextValue();
			
			for(int i = 0; i<array.length(); i++)
			{
				outlets.add(new Outlet(array.getJSONObject(i)));
			}
			
		}
		catch (Exception e)
		{
			
		}
		finally
		{
			if(reader != null)
				reader.close();
		};

		
		
		return outlets;
		
	}
	
	public void saveOutlets(ArrayList<Outlet> outlets)
		throws JSONException, IOException
	{
		JSONArray array = new JSONArray();
		for(Outlet o: outlets)
		{
			array.put(o.toJSON());
		}
		
		Writer writer = null;
		try
		{
			OutputStream out = mContext.openFileOutput(mFilename,Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(out);
			writer.write(array.toString());
			
		}
		finally
		{
			if(writer != null)
				writer.close();			
		}
	}
	
	
	public void saveUserAndPassword(Preference preference)
			throws JSONException, IOException
		{
			JSONArray array = new JSONArray();
			
			array.put(preference.toJSON());
			
			
			Writer writer = null;
			try
			{
				OutputStream out = mContext.openFileOutput(mFilename,Context.MODE_PRIVATE);
				writer = new OutputStreamWriter(out);
				writer.write(array.toString());
				
			}
			catch (Exception e)
			{
				Log.d(TAG, "Error write file ", e);
			}
			finally
			{
				if(writer != null)
					writer.close();			
			}
		}
	
	public Preference loadUserAndPassword() 
			throws IOException, JSONException
	{
		Preference preference = null;
		BufferedReader reader = null;
		
		try
		{
			InputStream in = mContext.openFileInput(mFilename);
			reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder jsonString = new StringBuilder();
			String line = null;
			
			while ((line = reader.readLine()) != null)
			{
				jsonString.append(line);
			}
			
			JSONArray array = (JSONArray) new JSONTokener(jsonString.toString())
			.nextValue();
			
			
			preference = (new Preference(array.getJSONObject(0)));
			
			
		}
		catch (Exception e)
		{
			
		}
		finally
		{
			if(reader != null)
				reader.close();
		};

		
		
		return preference;
		
	}

}
