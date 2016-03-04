package topevery.um.map;

import android.content.Context;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

class LocationClientTencent extends LocationClientBase implements TencentLocationListener
{
	private static LocationClientTencent _Instance = new LocationClientTencent();

	private TencentLocationManager mLocationClient;

	public static synchronized LocationClientTencent getInstance()
	{
		return _Instance;
	}

	@Override
	public void setContext(Context context)
	{
		mLocationClient = TencentLocationManager.getInstance(context);
		// 设置坐标系为 gcj-02, 缺省坐标为 gcj-02, 所以通常不必进行如下调用
		mLocationClient.setCoordinateType(TencentLocationManager.COORDINATE_TYPE_GCJ02);
	}

	@Override
	public void start()
	{
		// 创建定位请求
		TencentLocationRequest request = TencentLocationRequest.create();
		request = request.setInterval(1000); // 设置定位周期
		request = request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_NAME); // 设置定位level

		// 开始定位
		mLocationClient.requestLocationUpdates(request, this);
	}

	@Override
	public void stop()
	{
		mLocationClient.removeUpdates(this);
	}

	@Override
	public boolean isStarted()
	{
		return true;
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
	public void onLocationChanged(TencentLocation location, int error, String reason)
	{
		if (error == TencentLocation.ERROR_OK)
		{
			// 定位成功
			lastLocation = new UmLocation(location);
			onReceiveUmLocation(lastLocation);
		}
		else
		{
			// 定位失败
		}
	}

	@Override
	public void onStatusUpdate(String name, int status, String desc)
	{

	}
}
