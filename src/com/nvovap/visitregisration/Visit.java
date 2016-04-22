package com.nvovap.visitregisration;

import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.format.DateFormat;

/**
 * @author Комп
 *
 */
public class Visit 
{
	private static final String JSON_ID = "id";
	private static final String JSON_VISIT_DATE = "visitDate";
	private static final String JSON_OUTLATE = "outlet";
	private static final String JSON_FILENAME_1 = "fileName1";
	private static final String JSON_FILENAME_2 = "fileName2";
	private static final String JSON_FILENAME_3 = "fileName3";
	private static final String JSON_FILENAME_4 = "fileName4";
	
	private static final String JSON_TRANSFERRED_1 = "transferred1";
	private static final String JSON_TRANSFERRED_2 = "transferred2";
	private static final String JSON_TRANSFERRED_3 = "transferred3";
	private static final String JSON_TRANSFERRED_4 = "transferred4";
	
	private UUID mId;
	private Date mVisitDate;
	private Outlet mOutlet; // Торговая точка 
	private String mFileName1;
	private String mFileName2;
	private String mFileName3;
	private String mFileName4;
	private Boolean mTransferred1;
	private Boolean mTransferred2;
	private Boolean mTransferred3;
	private Boolean mTransferred4;
	
	public Visit()
	{
		mId = UUID.randomUUID();
		mVisitDate = new Date();
		
		mOutlet = null;
		
		mFileName1 = null;
		mFileName2 = null;
		mFileName3 = null;
		mFileName4 = null;
		
		mTransferred1 = false;
		mTransferred2 = false;
		mTransferred3 = false;
		mTransferred4 = false;
		
	}
	
	
	
	public Visit(JSONObject json)throws JSONException
	{
		mId = UUID.fromString(json.getString(JSON_ID));
		
		if (json.has(JSON_FILENAME_1)) mFileName1 = json.getString(JSON_FILENAME_1);
		if (json.has(JSON_FILENAME_2)) mFileName2 = json.getString(JSON_FILENAME_2);
		
		if (json.has(JSON_FILENAME_3)) mFileName3 = json.getString(JSON_FILENAME_3);
		if (json.has(JSON_FILENAME_4)) mFileName4 = json.getString(JSON_FILENAME_4);
		
		mTransferred1 = json.getBoolean(JSON_TRANSFERRED_1);
		mTransferred2 = json.getBoolean(JSON_TRANSFERRED_2);
		mTransferred3 = json.getBoolean(JSON_TRANSFERRED_3);
		mTransferred4 = json.getBoolean(JSON_TRANSFERRED_4);
		
		mVisitDate = new Date(json.getLong(JSON_VISIT_DATE));
		
		if (json.has(JSON_OUTLATE)) mOutlet = new Outlet(json.getJSONObject(JSON_OUTLATE));
		
		
	}
	
	public JSONObject toJSON() throws JSONException
	{
		JSONObject json = new JSONObject();
		json.put(JSON_ID, mId.toString());
		json.put(JSON_FILENAME_1, mFileName1);
		json.put(JSON_FILENAME_2, mFileName2);
		json.put(JSON_FILENAME_3, mFileName3);
		json.put(JSON_FILENAME_4, mFileName4);
		
		json.put(JSON_TRANSFERRED_1, mTransferred1);
		json.put(JSON_TRANSFERRED_2, mTransferred2);
		json.put(JSON_TRANSFERRED_3, mTransferred3);
		json.put(JSON_TRANSFERRED_4, mTransferred4);
		
		json.put(JSON_VISIT_DATE, mVisitDate.getTime());
				
		
		json.put(JSON_OUTLATE, mOutlet.toJSON());
		
				
		return json;
	}



	public Date getVisitDate() {
		return mVisitDate;
	}


	public String getVisitDateToString() {
		
		String dateString = (String)DateFormat.format("dd.MM.yyy", mVisitDate); 
		return dateString;
	}

	
	public String getVisitDateTimeToString() {
		
		String dateString = (String)DateFormat.format("dd.MM.yyy hh:mm:ss", mVisitDate); 
		return dateString;
	}

	
	public void setVisitDate(Date visitDate) {
		mVisitDate = visitDate;
	}



	public Outlet getOutlet() {
		return mOutlet;
	}



	public void setOutlet(Outlet outlet) {
		mOutlet = outlet;
	}



	public String getFileName1() {
		return mFileName1;
	}



	public void setFileName1(String fileName) {
		mFileName1 = fileName;
	}
	
	
	
	public String getFileName2() {
		return mFileName2;
	}



	public void setFileName2(String fileName) {
		mFileName2 = fileName;
	}
	
	
	
	public String getFileName3() {
		return mFileName3;
	}



	public void setFileName3(String fileName) {
		mFileName3 = fileName;
	}
	
	
	public String getFileName4() {
		return mFileName4;
	}



	public void setFileName4(String fileName) {
		mFileName4 = fileName;
	}
	



	public Boolean getTransferred1() {
		return mTransferred1;
	}



	public void setTransferred1(Boolean transferred) {
		mTransferred1 = transferred;
	}



	public UUID getId() {
		return mId;
	}



	public Boolean getTransferred2() {
		return mTransferred2;
	}



	public void setTransferred2(Boolean mTransferred2) {
		this.mTransferred2 = mTransferred2;
	}



	public Boolean getTransferred3() {
		return mTransferred3;
	}



	public void setTransferred3(Boolean mTransferred3) {
		this.mTransferred3 = mTransferred3;
	}



	public Boolean getTransferred4() {
		return mTransferred4;
	}



	public void setTransferred4(Boolean mTransferred4) {
		this.mTransferred4 = mTransferred4;
	}
	
	
	
	

}
