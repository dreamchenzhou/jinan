package topevery.um.map;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

class LocationClientBaidu extends LocationClientBase implements BDLocationListener
{
	public static final LocationMode tempMode = LocationMode.Hight_Accuracy;
	public static final String tempcoor = "bd09ll";

	private static LocationClientBaidu _Instance = new LocationClientBaidu();

	private LocationClient mLocationClient;

	public static synchronized LocationClientBaidu getInstance()
	{
		return _Instance;
	}

	@Override
	public void setContext(Context context)
	{
		mLocationClient = new LocationClient(context);
		mLocationClient.registerLocationListener(this);

		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);
		option.setCoorType(tempcoor);
		int span = 1000;
		option.setScanSpan(span);
		option.setIsNeedAddress(true);
		option.setOpenGps(true);
		option.setAddrType("all");
		mLocationClient.setLocOption(option);
	}

	@Override
	public void start()
	{
		mLocationClient.start();
	}

	@Override
	public void stop()
	{
		mLocationClient.stop();
	}

	@Override
	public boolean isStarted()
	{
		return mLocationClient.isStarted();
	}

	@Override
	public UmLocation getLocation()
	{
		synchronized (this)
		{
			return lastLocation;
		}
	}

	@Override
	public void onReceiveLocation(BDLocation location)
	{
		synchronized (this)
		{
			lastLocation = new UmLocation(location);
			onReceiveUmLocation(lastLocation);
		}
	}
}
