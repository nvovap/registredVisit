package com.nvovap.visitregisration;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.UUID;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;





public class VisitFragment extends Fragment 
{
	private static final String TAG = "VisitFragment";
	//private static final int REQUEST_PREFERENCE = 2;
	public static final String EXTRA_VISIT_ID = "com.nvovap.android.visit.visit_id";
	
	private static final int REQUEST_PHOTO1 = 0;
	private static final int REQUEST_SELECT_OUTLET = 1;
	private static final int REQUEST_PHOTO2 = 2;
	private static final int REQUEST_PHOTO3 = 3;
	private static final int REQUEST_PHOTO4 = 4;
	
	private Visit mVisit;
	
	private boolean mItIsNew; 
	
	private ImageView mPhotoView1;
	private View mProgress1;
	private View mProgress2;
	private View mProgress3;
	private View mProgress4;
	private ImageView mPhotoView2;
	private ImageView mPhotoView3;
	private ImageView mPhotoView4;
	
	private TextView mOutletField; 
	private Button mSendFTP;
	private Button mSave;
	private Button mOutletButton;

	
	
	class SendFTP extends AsyncTask<String, Integer, Boolean> {
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			
			mProgress1.setVisibility(View.INVISIBLE);
			mProgress2.setVisibility(View.INVISIBLE);
			mProgress3.setVisibility(View.INVISIBLE);
			mProgress4.setVisibility(View.INVISIBLE);
			
			switch (values[0]) {
			case 1:
				mProgress1.setVisibility(View.VISIBLE);
				break;
			case 2:
				mProgress2.setVisibility(View.VISIBLE);
				break;
			case 3:
				mProgress3.setVisibility(View.VISIBLE);
				break;
			case 4:
				mProgress4.setVisibility(View.VISIBLE);
				break;
				
			default:
				break;
			}
			
			
			mSendFTP.setEnabled(false);
			mSave.setEnabled(false);
			mOutletButton.setEnabled(false);
		}
		
		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			mProgress1.setVisibility(View.INVISIBLE);
			mProgress2.setVisibility(View.INVISIBLE);
			mProgress3.setVisibility(View.INVISIBLE);
			mProgress4.setVisibility(View.INVISIBLE);
			mSendFTP.setEnabled(true);
			mSave.setEnabled(true);
			mOutletButton.setEnabled(true);
			
			getActivity().finish();
		}
		
		
	    @Override
	    protected Boolean doInBackground(String... params) {
	    	
	    	//Integer mProgress = 0;
	        //publishProgress(mProgress);
	        
	    	
	      
			FTPClient ftpClient = new FTPClient();
			
			FTPClientConfig config = new FTPClientConfig();
			
			
			//config.setServerTimeZoneId(serverTimeZoneId);
			//Log.i("TEST", config.getServerLanguageCode());
			//config.setServerLanguageCode("ru");
			
			ftpClient.configure(config);		
			String user = params[0];
			String password = params[1];
			String dir = params[2];
			
			String fileName = params[3];
			String filePath1 = params[4];
			String filePath2 = params[5];
			String filePath3 = params[6];
			String filePath4 = params[7];
			
			//byte[] data;		
			
			
			try {
				ftpClient.connect("ftp.mlife.dp.ua", 21);
			} catch (SocketException e) {
				
				e.printStackTrace();
			} catch (IOException e) 
			{
				
				e.printStackTrace();
			}
			
			try {
				if (ftpClient.login(user, password))
				{
					
					ftpClient.setFileType(FTP.BINARY_FILE_TYPE);//до этого приходили картинки с искажениями
					ftpClient.enterLocalPassiveMode();

					
					Log.i("FTP", "Connect OK");
					
					//ftpClient.enterRemotePassiveMode();				
					ftpClient.setAutodetectUTF8(true);	
				
					//SortedMap<String, Charset> charsetsMap = Charset.availableCharsets();		        
					//Log.i("TEST", "count charset = "+charsetsMap.size());				
					//int i = 0;
					//for(String name : charsetsMap.keySet()){				
					//	Charset charset = charsetsMap.get("KOI8-U");
					//	ftpClient.setCharset(charset);
					
				
					
						
						//Кодировки те что попались
						//ISO-8859-1 
						//ISO-8859-15  
						//SCSU  
						//windows-1252 
						//Больше всего подошли две
						//SCSU
						//ISO-8859-1
						
						//ftpClient.setControlEncoding("utf-8");
						/*String rs[] = ftpClient.listNames();
						for(String s:rs)
						{
							
							//test = "Кодировки те что попались"
							String test  = new String(s.getBytes("SCSU"),"UTF8");
							//Log.i("TEST",  test);
							
		
							//Вот так я искал кодировку
							SortedMap<String, Charset> charsetsMap = Charset.availableCharsets();
					
							for(String name : charsetsMap.keySet()){
							      
								Log.i("TEST", name+"  ");
							
								test  = new String(s.getBytes("SCSU"),name);
							
								Log.i("TEST",  test);
							}
												
						};*/
				
					
					ftpClient.changeWorkingDirectory(user);
					
					ftpClient.makeDirectory(dir);
					//ftpClient.makeDirectory(new String("Привет мир FTP".getBytes("UTF8"),"SCSU"));
					ftpClient.changeWorkingDirectory(dir);
					//ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
					
					File file = null;
					InputStream in = null;
					
					if (filePath1 != null)
					{
						file = new File(filePath1);
						in = new FileInputStream(file);
					//ByteArrayInputStream in = new ByteArrayInputStream("8899877".getBytes());
						try 
						{
							publishProgress(1);
							String tempFilename = new String((fileName+"_1.jpg").getBytes("windows-1251"),"SCSU");
							ftpClient.storeFile(tempFilename, in);
							
							mVisit.setTransferred1(true);
							
						} catch (Exception e) 
						{
							
							
							mVisit.setTransferred1(false);
							Log.d("myFTP","Error",e);
						};
					
						in.close();						
					}
					
					if (filePath2 != null)
					{
						file = new File(filePath2);
						in = new FileInputStream(file);
					//ByteArrayInputStream in = new ByteArrayInputStream("8899877".getBytes());
						try 
						{
							publishProgress(2);
							String tempFilename = new String((fileName+"_2.jpg").getBytes("windows-1251"),"SCSU");
							ftpClient.storeFile(tempFilename, in);
							
							mVisit.setTransferred2(true);
						} catch (Exception e) 
						{
							mVisit.setTransferred2(false);
							Log.d("myFTP","Error",e);
						};
					
						in.close();						
					}
					
					if (filePath3 != null)
					{
						file = new File(filePath3);
						in = new FileInputStream(file);
					//ByteArrayInputStream in = new ByteArrayInputStream("8899877".getBytes());
						try 
						{
							publishProgress(3);
							String tempFilename = new String((fileName+"_3.jpg").getBytes("windows-1251"),"SCSU");
							ftpClient.storeFile(tempFilename, in);
							mVisit.setTransferred3(true);
							
						} catch (Exception e) 
						{
							mVisit.setTransferred3(false);
							Log.d("myFTP","Error",e);
						};
					
						in.close();						
					}
					
					if (filePath4 != null)
					{
						file = new File(filePath4);
						in = new FileInputStream(file);
					//ByteArrayInputStream in = new ByteArrayInputStream("8899877".getBytes());
						try 
						{
							publishProgress(4);
							String tempFilename = new String((fileName+"_4.jpg").getBytes("windows-1251"),"SCSU");
							ftpClient.storeFile(tempFilename, in);	
							mVisit.setTransferred4(true);
						} catch (Exception e) 
						{
							mVisit.setTransferred4(false);
							Log.d("myFTP","Error",e);
						};
					
						in.close();						
					}
					
				ftpClient.logout();
				ftpClient.disconnect();    
				
				}
				} catch (IOException e) 
				{
					e.printStackTrace();
				}
			        
	        return true;
	    }
	}
	
	
	public static VisitFragment newInstance(UUID visitId) 
	{
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_VISIT_ID, visitId);
				
		VisitFragment fragment = new VisitFragment();
		fragment.setArguments(args);
		
		return fragment;
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode != Activity.RESULT_OK) return;
		
		if (requestCode == REQUEST_PHOTO1)
		{
			
			
			String filename = (String)data.getSerializableExtra(VisitCameraFragment.EXTRA_PHOTO_FILENAME);
			
			if (filename != null) 
			{
				mSendFTP.setEnabled(true);
				
				Log.i(TAG, "file name : "+filename);
				
				if(mVisit.getFileName1() != null)
				{
					if (mVisit.getFileName1().length()>0)
					{
						String path = getActivity().getFileStreamPath(mVisit.getFileName1()).getAbsolutePath();
						try
						{
							File file = new File(path);
							if (file.exists())
								file.delete();
							mVisit.setFileName1(null);
						}
						catch (Exception e) {
							Log.d(TAG,"No delete file",e);
						}
					}				
				}
				
				mVisit.setFileName1(filename);
				
				setImageView(filename, mPhotoView1);
				
				/*if (mVisit.getOutlet() != null)
				{
					LocationManager locationManager = (LocationManager) 
							getActivity().getSystemService(getActivity().LOCATION_SERVICE);
				  
					Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
					mVisit.getOutlet().setLocation(loc);
					
					 OutletLab.get(getActivity()).saveOutlets();
				}*/
			}
			setEnabledButtonSendFTP();
			
		}
		else if (requestCode == REQUEST_PHOTO2)
		{
			String filename = (String)data.getSerializableExtra(VisitCameraFragment.EXTRA_PHOTO_FILENAME);
			
			if (filename != null) 
			{
				
				mSendFTP.setEnabled(true);
				
				Log.i(TAG, "file name : "+filename);
				
				if(mVisit.getFileName2() != null)
				{
					if (mVisit.getFileName2().length()>0)
					{
						String path = getActivity().getFileStreamPath(mVisit.getFileName2()).getAbsolutePath();
						try
						{
							File file = new File(path);
							if (file.exists())
								file.delete();
							mVisit.setFileName2(null);
						}
						catch (Exception e) {
							Log.d(TAG,"No delete file",e);
						}
					}				
				}
				
				mVisit.setFileName2(filename);
				
				setImageView(filename, mPhotoView2);
				
			}
			setEnabledButtonSendFTP();
		}
		else if (requestCode == REQUEST_PHOTO3)
		{
			String filename = (String)data.getSerializableExtra(VisitCameraFragment.EXTRA_PHOTO_FILENAME);
			
			if (filename != null) 
			{
				
				mSendFTP.setEnabled(true);
				
				Log.i(TAG, "file name : "+filename);
				
				if(mVisit.getFileName3() != null)
				{
					if (mVisit.getFileName3().length()>0)
					{
						String path = getActivity().getFileStreamPath(mVisit.getFileName3()).getAbsolutePath();
						try
						{
							File file = new File(path);
							if (file.exists())
								file.delete();
							mVisit.setFileName3(null);
						}
						catch (Exception e) {
							Log.d(TAG,"No delete file",e);
						}
					}				
				}
				
				mVisit.setFileName3(filename);
				
				setImageView(filename, mPhotoView3);
				
			}
			setEnabledButtonSendFTP();
		}	
		else if (requestCode == REQUEST_PHOTO4)
		{
			String filename = (String)data.getSerializableExtra(VisitCameraFragment.EXTRA_PHOTO_FILENAME);
			
			if (filename != null) 
			{
				
				mSendFTP.setEnabled(true);
				
				Log.i(TAG, "file name : "+filename);
				
				if(mVisit.getFileName4() != null)
				{
					if (mVisit.getFileName4().length()>0)
					{
						String path = getActivity().getFileStreamPath(mVisit.getFileName4()).getAbsolutePath();
						try
						{
							File file = new File(path);
							if (file.exists())
								file.delete();
							mVisit.setFileName4(null);
						}
						catch (Exception e) {
							Log.d(TAG,"No delete file",e);
						}
					}				
				}
				
				mVisit.setFileName4(filename);
				
				setImageView(filename, mPhotoView4);
			
				setEnabledButtonSendFTP();
			}
		}
		else if (requestCode == REQUEST_SELECT_OUTLET)
		{	
			
			
			UUID idOutlet = (UUID)data.getSerializableExtra(OutletListFragment.EXTRA_SELECT_OUTLET);
			
			Outlet outlet = OutletLab.get(getActivity()).getOutlet(idOutlet);
			
			
			mVisit.setOutlet(outlet);
			
			if (mVisit.getOutlet() != null)
			{
				mOutletField.setText(mVisit.getOutlet().getName());
				///mPhotoButton.setEnabled(true);
			}
			//else
			//	mPhotoButton.setEnabled(false);
		}
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		
		UUID visitId = (UUID)getArguments().getSerializable(EXTRA_VISIT_ID);
		
		if(visitId == null)
		{
			mItIsNew = true;
			mVisit = new Visit();
			
		}
		else
		{
			mVisit = VisitLab.get(getActivity()).getVisit(visitId);
			mItIsNew = false;
		}
		
		setHasOptionsMenu(true);
		
		
		
		Log.d(TAG, "onCreate");
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) 
		{
		case android.R.id.home:
			
			
			if (NavUtils.getParentActivityName(getActivity()) != null) 
			{
				NavUtils.navigateUpFromSameTask(getActivity());
				
			}
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	
	private void setImageView(String fileName, ImageView photoView)
	{
		if (fileName != null) 
		{		
			String path =getActivity().getFileStreamPath(fileName).getAbsolutePath();
		
			if (path != null && path.length()>0)
			{		
				Bitmap bitmap = BitmapFactory.decodeFile(path);
				if (bitmap != null)	photoView.setImageBitmap(bitmap);
			};
			
		}
		
	}
	
	
	private void setEnabledButtonSendFTP()
	{
		String fileName1 = mVisit.getFileName1();
		String fileName2 = mVisit.getFileName2();
		String fileName3 = mVisit.getFileName3();
		String fileName4 = mVisit.getFileName4();
		
		
		if (fileName1 == null && fileName2 == null 
			&& fileName3 == null && fileName4 == null || mVisit.getOutlet()==null) 
		{
			mSendFTP.setEnabled(false);
		}
		else
		{
		mSendFTP.setEnabled(true);
		}
	}
	
	
	@SuppressLint("NewApi") @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		Log.d(TAG, "onCreateView");
		
		
		
		View v = inflater.inflate(R.layout.fragment_visit, container, false);
		
		
		
		mProgress1 = (View)v.findViewById(R.id.progressImage1);
		mProgress1.setVisibility(View.INVISIBLE);
		
		
		mProgress2 = (View)v.findViewById(R.id.progressImage2);
		mProgress2.setVisibility(View.INVISIBLE);
		
		mProgress3 = (View)v.findViewById(R.id.progressImage3);
		mProgress3.setVisibility(View.INVISIBLE);
		
		mProgress4 = (View)v.findViewById(R.id.progressImage4);
		mProgress4.setVisibility(View.INVISIBLE);
		
		
		mSave = (Button)v.findViewById(R.id.saveVisit_Button);
		mSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getActivity().finish();				
			}
		});
		//mSave.setVisibility(View.INVISIBLE);
		
		
		
		mSendFTP = (Button)v.findViewById(R.id.sendFTP_Button);
		
		setEnabledButtonSendFTP();
		
		mSendFTP.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(mVisit.getOutlet() != null)
				{
					if(mVisit.getFileName1()!=null || mVisit.getFileName2()!=null || mVisit.getFileName3()!=null || mVisit.getFileName4()!=null)
					{
						String filePath1 = null; 
						boolean allTransferred =  mVisit.getTransferred1()&& mVisit.getTransferred2() && mVisit.getTransferred3()&& mVisit.getTransferred4();
						
						if(mVisit.getFileName1()!=null && (!mVisit.getTransferred1() || allTransferred) ) 										
							filePath1 = getActivity().getFileStreamPath(mVisit.getFileName1()).getAbsolutePath();
					
						String filePath2 = null; 
						if(mVisit.getFileName2()!=null && (!mVisit.getTransferred2() || allTransferred)) 										
							filePath2 = getActivity().getFileStreamPath(mVisit.getFileName2()).getAbsolutePath();
						
						String filePath3 = null; 
						if(mVisit.getFileName3()!=null && (!mVisit.getTransferred3() || allTransferred)) 										
							filePath3 = getActivity().getFileStreamPath(mVisit.getFileName3()).getAbsolutePath();
						
						String filePath4 = null; 
						if(mVisit.getFileName4()!=null && (!mVisit.getTransferred4() || allTransferred)) 										
							filePath4 = getActivity().getFileStreamPath(mVisit.getFileName4()).getAbsolutePath();
						
						
						//mProgress1.setVisibility(View.VISIBLE);
						
						/*new DownloadImageTask().execute("Chornopyska", "V0LWR3xzb46l",mVisit.getVisitDateToString()
								,filePath
								, mVisit.getOutlet().getName()+"_1.jpg");*/
						
						
						
						String user = Preference.get(getActivity()).getUser();
						String pass = Preference.get(getActivity()).getPassword();
				
						if (user == null || user.trim().length() == 0) 
						{
							Intent i = new Intent(getActivity(), PreferenceActivity.class);
					
							startActivity(i);
							
							return ;
						}
						
						//"baranovskiy"
						//"Mctqx99ZMWGu"
						
						new SendFTP().execute(user, pass,mVisit.getVisitDateToString()
							, mVisit.getOutlet().getName(),filePath1,filePath2,filePath3,filePath4);
					}
			
				}
				
				//http://www.sql.ru/forum/216369/zagruzka-fayla-na-ftp
				//http://habrahabr.ru/post/124484/ 
				//http://habrahabr.ru/post/145092/				
			}
		});
		
		
		mOutletButton = (Button)v.findViewById(R.id.buttonSelectOutlet);
		
		mOutletButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), OutletListActivity.class);			
				
				startActivityForResult(i, REQUEST_SELECT_OUTLET);
				
			}
		});
		
		if (mVisit.getTransferred1() || mVisit.getTransferred2() 
				|| mVisit.getTransferred3() || mVisit.getTransferred4() ) 
		{
			mOutletButton.setEnabled(false);
		}	
		else mOutletButton.setEnabled(true);
		
		
		if(Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB)
		{
			if(NavUtils.getParentActivityName(getActivity()) != null)
			{
				getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
			}
		}
		
		
		
		
		
		ImageButton mPhotoButton1 = (ImageButton)v.findViewById(R.id.visit_ImageButton1);
		
		mPhotoButton1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent(getActivity(), VisitCameraActivity.class);			
				
				startActivityForResult(i, REQUEST_PHOTO1);
				
			}
		});
		
		
		CheckBox send1 = (CheckBox)v.findViewById(R.id.checkBoxTransferred1);
		if(mVisit.getTransferred1())
		{
			send1.setChecked(true);
			send1.setVisibility(View.VISIBLE);
			mPhotoButton1.setVisibility(View.INVISIBLE);
		}
		else send1.setVisibility(View.INVISIBLE);
			
		
		ImageButton mPhotoButton2 = (ImageButton)v.findViewById(R.id.visit_ImageButton2);
		
		mPhotoButton2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent(getActivity(), VisitCameraActivity.class);			
				
				startActivityForResult(i, REQUEST_PHOTO2);
				
			}
		});
		
		
		CheckBox send2 = (CheckBox)v.findViewById(R.id.checkBoxTransferred2);
		if(mVisit.getTransferred2())
		{
			send2.setChecked(true);
			send2.setVisibility(View.VISIBLE);
			mPhotoButton2.setVisibility(View.INVISIBLE);
		}else send2.setVisibility(View.INVISIBLE);
		
		
		
		ImageButton mPhotoButton3 = (ImageButton)v.findViewById(R.id.visit_ImageButton3);
		
		mPhotoButton3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent(getActivity(), VisitCameraActivity.class);			
				
				startActivityForResult(i, REQUEST_PHOTO3);
				
			}
		});
		
		CheckBox send3 = (CheckBox)v.findViewById(R.id.checkBoxTransferred3);
		if(mVisit.getTransferred3())
		{
			send3.setChecked(true);
			send3.setVisibility(View.VISIBLE);
			mPhotoButton3.setVisibility(View.INVISIBLE);
		}else send3.setVisibility(View.INVISIBLE);
		
		ImageButton mPhotoButton4 = (ImageButton)v.findViewById(R.id.visit_ImageButton4);
		
		mPhotoButton4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent i = new Intent(getActivity(), VisitCameraActivity.class);			
				
				startActivityForResult(i, REQUEST_PHOTO4);
				
			}
		});
		

		CheckBox send4 = (CheckBox)v.findViewById(R.id.checkBoxTransferred4);
		if(mVisit.getTransferred4())
		{
			send4.setChecked(true);
			send4.setVisibility(View.VISIBLE);
			mPhotoButton4.setVisibility(View.INVISIBLE);
		}else send4.setVisibility(View.INVISIBLE);
		
		PackageManager pm = getActivity().getPackageManager();
		
		if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)
			&& !pm.hasSystemFeature(PackageManager.FEATURE_CAMERA))
		{
			mPhotoButton1.setEnabled(false);
			mPhotoButton2.setEnabled(false);
			mPhotoButton3.setEnabled(false);
			mPhotoButton4.setEnabled(false);
		}
		
		
	
		
		
		mPhotoView1 = (ImageView)v.findViewById(R.id.visit_imageView1);
		String fileName1 = mVisit.getFileName1();
		setImageView(fileName1, mPhotoView1);
		
		mPhotoView1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mVisit.getFileName1()==null) return;
				
				FragmentManager fm = getActivity().getSupportFragmentManager();
				
				String path = getActivity().getFileStreamPath(mVisit.getFileName1()).getAbsolutePath();
				
				ImageFragment.newInstance(path).show(fm, "image");
				
			}
		});
		
		
		mPhotoView2 = (ImageView)v.findViewById(R.id.visit_imageView2);
		String fileName2 = mVisit.getFileName2();
		setImageView(fileName2, mPhotoView2);
		
		
		
		mPhotoView2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(mVisit.getFileName2()==null) return;
				FragmentManager fm = getActivity().getSupportFragmentManager();
				
				String path = getActivity().getFileStreamPath(mVisit.getFileName2()).getAbsolutePath();
				
				ImageFragment.newInstance(path).show(fm, "image");
				
			}
		});
		
		mPhotoView3 = (ImageView)v.findViewById(R.id.visit_imageView3);
		String fileName3 = mVisit.getFileName3();
		setImageView(fileName3, mPhotoView3);
		
		
		mPhotoView3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mVisit.getFileName3()==null) return;
				FragmentManager fm = getActivity().getSupportFragmentManager();
				
				String path = getActivity().getFileStreamPath(mVisit.getFileName3()).getAbsolutePath();
				
				ImageFragment.newInstance(path).show(fm, "image");
				
			}
		});
		
		mPhotoView4 = (ImageView)v.findViewById(R.id.visit_imageView4);
		String fileName4 = mVisit.getFileName4();
		setImageView(fileName4, mPhotoView4);
		
		mPhotoView4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mVisit.getFileName4()==null) return;
				
				FragmentManager fm = getActivity().getSupportFragmentManager();
				
				String path = getActivity().getFileStreamPath(mVisit.getFileName4()).getAbsolutePath();
				
				ImageFragment.newInstance(path).show(fm, "image");
				
			}
		});
		
		
		
	
		mOutletField = (TextView)v.findViewById(R.id.visit_outlet_textView);	
		if (mVisit.getOutlet() != null)
		{
			mOutletField.setText(mVisit.getOutlet().getName());
		}
		
		
		TextView mVisitDateField = (TextView)v.findViewById(R.id.visit_date_textView);	
		mVisitDateField.setText(mVisit.getVisitDateTimeToString());
		
		return v;
		
	}
	
	
	@Override
	public void onPause() {
		super.onPause();
		
		if(mVisit.getOutlet() != null)
		{
			if(mItIsNew)
			{
				VisitLab.get(getActivity()).addVisit(mVisit);
				mItIsNew = false;
			}
			
			VisitLab.get(getActivity()).saveVisits();
		}
		
		
		
		Log.d(TAG, "onPause");
	}
	
	
	

}
