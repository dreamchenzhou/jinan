package topevery.um.map;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import topevery.framework.system.DateTime;

import com.baidu.location.BDLocation;
import com.tencent.map.geolocation.TencentLocation;

@SuppressWarnings("serial")
public class UmLocation implements Serializable
{
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public double absX;
	public double absY;
	public double geoX;
	public double geoY;
	public DateTime collectTime;
	public DateTime gpsTime;
	public String address = "";

	public float radius;
	public float mAccuracy;

	public LocationTypeEnum locationType = LocationTypeEnum.Baidu;
	public BDLocation bdLocation;
	public TencentLocation tencentLocation;

	public UmLocation()
	{

	}

	public UmLocation(BDLocation bdLocation)
	{
		this.bdLocation = bdLocation;

		this.absX = bdLocation.getLongitude();
		this.absY = bdLocation.getLatitude();
		this.geoX = bdLocation.getLongitude();
		this.geoY = bdLocation.getLatitude();
		this.radius = bdLocation.getRadius();
		this.address = bdLocation.getAddrStr();
		this.collectTime = DateTime.getNow();
		this.gpsTime = getDateTime(bdLocation);
		this.locationType = LocationTypeEnum.Baidu;
	}

	public UmLocation(TencentLocation tencentLocation)
	{
		this.tencentLocation = tencentLocation;
		this.absX = tencentLocation.getLongitude();
		this.absY = tencentLocation.getLatitude();
		this.geoX = tencentLocation.getLongitude();
		this.geoY = tencentLocation.getLatitude();
		this.address = tencentLocation.getAddress();
		this.mAccuracy = tencentLocation.getAccuracy();
		this.collectTime = DateTime.getNow();
		this.gpsTime = DateTime.fromJavaDate(new Date(tencentLocation.getTime()));
		this.locationType = LocationTypeEnum.Tencent;
	}

	public double getLatitude()
	{
		return geoY;
	}

	public double getLongitude()
	{
		return geoX;
	}

	public float getRadius()
	{
		return radius;
	}

	public static Date getDate(BDLocation location)
	{
		Date date = new Date();
		try
		{
			date = sdf.parse(location.getTime());
		}
		catch (ParseException e)
		{
			e.printStackTrace();
			date = new Date();
		}
		return date;
	}

	public static DateTime getDateTime(BDLocation location)
	{
		Date date = getDate(location);
		DateTime dateTime = DateTime.fromJavaDate(date);
		return dateTime;
	}
}