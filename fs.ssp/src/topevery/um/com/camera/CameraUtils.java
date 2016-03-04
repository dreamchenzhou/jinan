package topevery.um.com.camera;

import android.app.Activity;
import android.content.Intent;

public class CameraUtils
{
	public OnCameraListener onCameraListener = null;

	public static int newValue = 512;
	
	public static CameraUtils value = new CameraUtils();

	public void camera(Activity activity, OnCameraListener listener)
	{
		if (activity != null && listener != null)
		{
			onCameraListener = listener;
			Intent intent = new Intent();
			intent.setClass(activity, CameraHolder.class);
			activity.startActivityForResult(intent, 1);
		}
	}

	public void localcamera(Activity activity, OnCameraListener listener)
	{
		if (activity != null && listener != null)
		{
			onCameraListener = listener;
			Intent intent = new Intent();
			intent.setClass(activity, CameraLocal.class);
			activity.startActivityForResult(intent, 1);
		}
	}

	public void onCamera(String fileName)
	{
		if (onCameraListener != null)
		{
			onCameraListener.onCamera(fileName);
			onCameraListener = null;
		}
	}
}
