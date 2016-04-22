package com.nvovap.visitregisration;

import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;

public class Outlet {
	
	private final String JSON_ID = "id";
	private final String JSON_NAME = "name";
	private final String JSON_ADDRESS = "address";
	private final String JSON_CLIENT = "Client";
	
	private UUID mId;
	private String mName;
	private String mAddress;
	private String mClient;
	private Location  mLocation;
	
	public Outlet()
	{
		mId = UUID.randomUUID();
		mName = "";
	}
	
	public Outlet(JSONObject json)throws JSONException
	{
		mId = UUID.fromString(json.getString(JSON_ID));
		if (json.has(JSON_NAME)) mName = json.getString(JSON_NAME);
		if (json.has(JSON_ADDRESS)) mAddress = json.getString(JSON_ADDRESS);
		if (json.has(JSON_CLIENT)) mClient = json.getString(JSON_CLIENT);
	}
	
	public JSONObject toJSON() throws JSONException
	{
		JSONObject json = new JSONObject();
		json.put(JSON_ID, mId.toString());
		json.put(JSON_NAME, mName);
		json.put(JSON_ADDRESS, mAddress);
		json.put(JSON_CLIENT, mClient);
		
		return json;
	}

	
	
	public String getClient() {
		return mClient;
	}

	public void setClient(String client) {
		mClient = client;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		mName = name;
	}

	public UUID getId() {
		return mId;
	}

	public String getAddress() {
		return mAddress;
	}

	public void setAddress(String address) {
		mAddress = address;
	}

	public Location getLocation() {
		return mLocation;
	}

	public void setLocation(Location location) {
		mLocation = location;
	}
	
	
	
	

}
