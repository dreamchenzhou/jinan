package topevery.um.map;

import topevery.android.framework.map.MapValue;
import topevery.um.maptencent.GeoCoderHolder;
import android.app.Activity;
import android.content.Context;
import android.widget.EditText;

public class UmLocationClient
{
	public static LocationTypeEnum mLocationType = LocationTypeEnum.Baidu;
	private static LocationClientBase mLocationClient;

	public static void setContext(Context context, LocationTypeEnum locationType)
	{
		mLocationType = locationType;

		switch (locationType)
		{
			case Baidu:
				mLocationClient = new LocationClientBaidu();
				break;
			case Tencent:
				mLocationClient = new LocationClientTencent();
				break;
			default:
				break;
		}
		if (mLocationClient != null)
		{
			mLocationClient.setContext(context);
		}
	}

	public static void start()
	{
		if (mLocationClient != null)
		{
			mLocationClient.start();
		}
	}

	public static void stop()
	{
		if (mLocationClient != null)
		{
			mLocationClient.stop();
		}
	}

	public static boolean isStarted()
	{
		if (mLocationClient != null)
		{
			return mLocationClient.isStarted();
		}
		return false;
	}

	public static UmLocation getLocation()
	{
		if (mLocationClient != null)
		{
			return mLocationClient.getLocation();
		}
		return null;
	}

	public static void addLocationListener(ReceiveUmLocationListener listener)
	{
		if (mLocationClient != null)
		{
			mLocationClient.addLocationListener(listener);
		}
	}

	public static void removeLocationListener(ReceiveUmLocationListener listener)
	{
		if (mLocationClient != null)
		{
			mLocationClient.removeLocationListener(listener);
		}
	}

	public static void getAddr(MapValue mapValue, EditText editText)
	{
		double latitude = mapValue.absY;
		double longitude = mapValue.absX;
		getAddr(latitude, longitude, editText);
	}

	public static void getAddr(UmLocation location, EditText editText)
	{
		double latitude = location.absY;
		double longitude = location.absX;
		getAddr(latitude, longitude, editText);
	}

	public static void getCurAddr(EditText editText)
	{
		UmLocation location = UmLocationClient.getLocation();
		if (location != null)
		{
			double latitude = location.absY;
			double longitude = location.absX;
			getAddr(latitude, longitude, editText);
		}
	}

	public static void getAddr(double latitude, double longitude, EditText editText)
	{
		// new PosAsyncTask(latitude, longitude, editText).execute((Void) null);
		Activity wThis = (Activity) editText.getContext();
		new GeoCoderHolder(wThis, editText).searchFromLocation(latitude, longitude);
	}
}
