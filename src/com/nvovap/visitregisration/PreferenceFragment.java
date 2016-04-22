package com.nvovap.visitregisration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;


import org.apache.commons.net.ftp.FTPClient;




import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PreferenceFragment extends Fragment
{
	Preference mPreference;
	
	private View mProgressContainer;
	
	//class SendFTP extends AsyncTask<String, Integer, Boolean> {
	
	class TestFTP extends AsyncTask<String, Void, Integer>
	{
		
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			
			Toast toast = Toast.makeText(getActivity(),  result, Toast.LENGTH_LONG);
			toast.show();
			
		}
		
		
		@Override
		protected Integer doInBackground(String... params) {
			FTPClient ftpClient = new FTPClient();
			
			try {
				ftpClient.connect("ftp.mlife.dp.ua", 21);
			} catch (SocketException e) {
				
				
				e.printStackTrace();
				
				
				return  R.string.preferenceNoConnect;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {
				if (ftpClient.login(mPreference.getUser(), mPreference.getPassword()))
				{
					return   R.string.preferenceTestOK;
					
				}
				else
				{
					return  R.string.preferenceWrongUserOrPassword;
					
				}    
				
				
				} catch (IOException e) 
				{
					e.printStackTrace();
				}
			
			
			return R.string.preferenceWrongUserOrPassword;
		}
		
	}
	
	
	class SendOutletFTP extends AsyncTask<String, Void, Boolean>
	{
		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			
			if (result){
				Toast toast = Toast.makeText(getActivity(),  R.string.sendOutletOk, Toast.LENGTH_LONG);
				toast.show();
			}
			else{
				Toast toast = Toast.makeText(getActivity(),  R.string.failure, Toast.LENGTH_LONG);
				toast.show();
			}
			
			mProgressContainer.setVisibility(View.INVISIBLE);		
			
		}
		
		
		@Override
		protected Boolean doInBackground(String... params) {
			FTPClient ftpClient = new FTPClient();
			
				
			String user = params[0];
			String password = params[1];
			String filePath = params[2];
			String fileName = params[3];
			
			try {
				ftpClient.connect("ftp.mlife.dp.ua", 21);
			} catch (SocketException e) {
				
				
				e.printStackTrace();
				
				
				return  false;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			try {
				if (ftpClient.login(user, password))
				{
					
					ftpClient.enterLocalPassiveMode();					
					Log.i("FTP", "Connect OK");			
					ftpClient.setAutodetectUTF8(true);				
					
					
					
					File file = null;
					InputStream in = null;
					
					if (filePath != null)
					{
						file = new File(filePath);
						in = new FileInputStream(file);
					//ByteArrayInputStream in = new ByteArrayInputStream("8899877".getBytes());
						try 
						{
							//publishProgress(1);
							String tempFilename = new String((fileName).getBytes("windows-1251"),"SCSU");
							ftpClient.storeFile(tempFilename, in);				
							
						} catch (Exception e) 
						{
							Log.d("myFTP","Error",e);
						};
					
						in.close();						
					}
					
										
					ftpClient.logout();
					ftpClient.disconnect();    
					}
					else
					{
						return  false;
						
					}  
					
					
					} catch (IOException e) 
					{
						e.printStackTrace();
				}
						
			
			return true;
		}
		
	}
	
	
	class GetOutletFTP extends AsyncTask<String, Void, Boolean>
	{
		
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			
			if (result){
				

				Toast toast = Toast.makeText(getActivity(),  R.string.getOutletOk, Toast.LENGTH_LONG);
				toast.show();
	
			}
			else{
				Toast toast = Toast.makeText(getActivity(),  R.string.failure, Toast.LENGTH_LONG);
				toast.show();
			}
				
			
			mProgressContainer.setVisibility(View.INVISIBLE);	
			
		}
		
		
		@Override
		protected Boolean doInBackground(String... params) {
			FTPClient ftpClient = new FTPClient();
			
				
			String user = params[0];
			String password = params[1];
			String filePath = params[2];
			String fileName = params[3];
			
			try {
				ftpClient.connect("ftp.mlife.dp.ua", 21);
			} catch (SocketException e) {
				
				
				e.printStackTrace();
				
				
				return  false;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			try {
				if (ftpClient.login(user, password))
				{
					
					ftpClient.enterLocalPassiveMode();					
					Log.i("FTP", "Connect OK");			
					ftpClient.setAutodetectUTF8(true);				
					
				
					try
					{
						//OutputStream out = mContext.openFileOutput(fileName,Context.MODE_PRIVATE);
						
						OutputStream out = new FileOutputStream(filePath);
						
						
						ftpClient.retrieveFile(fileName, out);
				
						out.close();
					}
					catch (Exception e)
					{
						Log.d("My FTP", "Error write file ", e);
					}
					
					
					
				
										
					ftpClient.logout();
					ftpClient.disconnect();    
					}
					else
					{
						return  false;
						
					}  
					
					
					} catch (IOException e) 
					{
						e.printStackTrace();
				}
						
			
			return true;
		}
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	//	setHasOptionsMenu(true);
		mPreference = Preference.get(getActivity());
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View v = inflater.inflate(R.layout.fragment_preference, container, false);
		
		/*if(Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB)
		{
			if(NavUtils.getParentActivityName(getActivity()) != null)
			{
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			}
		}*/
		
		
		mProgressContainer = v.findViewById(R.id.visit_preference_progressContainer);
		mProgressContainer.setVisibility(View.INVISIBLE); 
		
		Button btnSendOutlet = (Button)v.findViewById(R.id.sendOutlet_button);
		
		btnSendOutlet.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mProgressContainer.setVisibility(View.VISIBLE);
				
				String filePath = getActivity().getFileStreamPath(OutletLab.FILENAME).getAbsolutePath();
				
				new SendOutletFTP().execute(mPreference.getUser(), mPreference.getPassword(),filePath,OutletLab.FILENAME);
				
			}
		});
		
		
		Button btnGetOutlet = (Button)v.findViewById(R.id.getOutlet_button);
		
		btnGetOutlet.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				mProgressContainer.setVisibility(View.VISIBLE);
				
				String filePath = getActivity().getFileStreamPath(OutletLab.FILENAME).getAbsolutePath();
				
				new GetOutletFTP().execute(mPreference.getUser(), mPreference.getPassword(),filePath,OutletLab.FILENAME);
				
			}
		});
		
		
		TextView user = (TextView)v.findViewById(R.id.user_EditText);
		user.setText(mPreference.getUser());
		
		user.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mPreference.setUser(s.toString());
				
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
		
		TextView password = (TextView)v.findViewById(R.id.password_EditText);
		password.setText(mPreference.getPassword());
		
		password.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mPreference.setPassword(s.toString());
				
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
		
		
		Button savePreference = (Button)v.findViewById(R.id.savePreference_button);
		savePreference.setOnClickListener(new OnClickListener() 
		{			
			@Override
			public void onClick(View v) {
				getActivity().finish();
				
			}
		});
		
		Button testConnectFTP = (Button)v.findViewById(R.id.testConnectFTP_button);
		
		testConnectFTP.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				new TestFTP().execute(mPreference.getUser(), mPreference.getPassword());
				
			}
		});
		
		
		
		
		return v;
	}
	
	
	@Override
	public void onPause() {
		super.onPause();
		
		mPreference.savePreference();
	}
	
	
	/*@Override
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
		
	}*/

}
