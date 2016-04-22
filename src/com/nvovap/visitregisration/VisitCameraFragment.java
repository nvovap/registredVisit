package com.nvovap.visitregisration;

import java.io.FileOutputStream;

import java.util.List;
import java.util.UUID;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;

import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
//import android.view.WindowManager;

public class VisitCameraFragment extends Fragment 
{
private static final String TAG = "VisitCameraFragment";
	
	public static final String EXTRA_PHOTO_FILENAME = "com.nvovap.android.visit.photo_filename";
	
	
	private Camera mCamera;
	private SurfaceView mSurfaceView;
	
	private View mProgressContainer;
	
	
	
	private Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback(){
		public void onShutter(){
			
			mProgressContainer.setVisibility(View.VISIBLE);
		}
	};
	
	
	private Camera.PictureCallback mJpegCallback = new PictureCallback() {
		
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			
			String filename = UUID.randomUUID().toString()+".jpg";
			
			FileOutputStream os = null;
			
			boolean success = true;
			
			
			
			
		
			try {
				os = getActivity().openFileOutput(filename,
						Context.MODE_PRIVATE);
				
				os.write(data);

			} catch (Exception e) {
				Log.e(TAG, "Error writing to file " + filename, e);
				success = false;
			} finally {
				try {
					if (os != null) {
						os.close();
					}
				} catch (Exception e) {
					Log.e(TAG, "Error closing file " + filename, e);
					success = false;
				}
			}

			if (success) {
				Log.i(TAG, "JPEG saved at " + filename);

				Intent i = new Intent();

				i.putExtra(EXTRA_PHOTO_FILENAME, filename);
				getActivity().setResult(Activity.RESULT_OK, i);
			} else
				getActivity().setResult(Activity.RESULT_CANCELED);

			getActivity().finish();

		}
	};
	
	
	
	
 	private Size getBestSupportedSize(List<Size> sizes, int width, int height)
	{
		Size bestSize = sizes.get(0);
		
		int largestArea = bestSize.width*bestSize.height;
		
		for (Size s : sizes) {
			int area = s.width*s.height;
			if(area > largestArea)
			{
				bestSize = s;
				largestArea = area;
			}
		}
		
		return bestSize;
	}
 	
 	
 
	
	//@SuppressLint("NewApi") @Override
	public void onResume() {
		super.onResume();
		
	}
	
	@Override
	public void onPause() {
		super.onPause();
		
		
		/*if (mCamera != null)
		{
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}*/
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
	
		View v = inflater.inflate(R.layout.frame_camera_layout, container, false);
		
		mProgressContainer = v.findViewById(R.id.visit_camera_progressContainer);
		mProgressContainer.setVisibility(View.INVISIBLE);
		
		
		
		ImageButton takePictureButton = (ImageButton)v.findViewById(R.id.visit_camera_takePictureButton);
		
		takePictureButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mCamera != null)
					mCamera.takePicture(mShutterCallback, null, mJpegCallback);
				
			}
		});
		
		mSurfaceView = (SurfaceView)v.findViewById(R.id.visit_camera_surfaceView);
		SurfaceHolder holder  = mSurfaceView.getHolder();
		
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		
	
		holder.addCallback(new SurfaceHolder.Callback()

		 {			
			
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				if(mCamera!= null)
				{
					mCamera.stopPreview();
					mCamera.release();
					mCamera = null;
				}
				
			}
			
			@SuppressLint("NewApi") @Override
			public void surfaceCreated(SurfaceHolder holder) {
				
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) 
				{
					mCamera = Camera.open(0);
				}
				else
				{
					mCamera = Camera.open();
				};
				
				mCamera.setDisplayOrientation(90);
				
				
				Camera.Parameters parameters = mCamera.getParameters(); //6
			  
			    parameters.setRotation(90);
			    mCamera.setParameters(parameters);
			    
				
				/*try{
					if (mCamera != null)
					{
						mCamera.setPreviewDisplay(holder);
					
					}
				}catch(IOException exception)
				{
					Log.e(TAG, "Error up preview display", exception);
				}*/
				
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {
				if(mCamera==null) return;
				
				
				mCamera.stopPreview();
				
				Camera.Parameters parameters = mCamera.getParameters();
				
				Size s = getBestSupportedSize(parameters.getSupportedPreviewSizes(), width, height);
				parameters.setPreviewSize(s.width,s.height);
				
				//parameters.setJpegQuality(60);
				
	
				/*s = getMinSupportedSize(parameters.getSupportedPreviewSizes());
				parameters.setPictureSize(s.width,s.height);
				*/
				
				//parameters.setRotation(90);
				
				 List <Camera.Size> sizes = parameters.getSupportedPictureSizes();
				 if(sizes != null && sizes.size()>0)
				 {
					 Camera.Size minSize = sizes.get (0);
					 for(Camera.Size si: sizes)
					 {
						 if(minSize.height<481) break;
						 if(si.height<minSize.height) minSize = si;
					 
					 }
					 parameters.setPictureSize (minSize.width, minSize.height);
					 
				 };	
				
				mCamera.setParameters(parameters);
				
				
				try {
					mCamera.setPreviewDisplay(holder);
					mCamera.startPreview();					
				} catch (Exception e) {
					Log.e(TAG, "Could not start preview",e);
					mCamera.release();
					mCamera = null;
				}
				
			}
		});
		
		
		
		return v;
		
	}


}
