package topevery.um.maptencent;

import topevery.um.map.UmLocation;

import com.tencent.mapsdk.raster.model.GeoPoint;

public class TencentMapAPI
{
	public static final double default_latitude = 23.035095;
	public static final double default_longitude = 113.134026;

	// public static final double default_latitude = 22.537048;
	// public static final double default_longitude = 113.985607;

	public static final GeoPoint default_point = new GeoPoint((int) (default_latitude * 1E6), (int) (default_longitude * 1E6));

	public static GeoPoint getGeoPoint(double latitude, double longitude)
	{
		int latitudeE6 = (int) (latitude * 1E6);
		int longitudeE6 = (int) (longitude * 1E6);
		GeoPoint geoPoint = new GeoPoint(latitudeE6, longitudeE6);
		return geoPoint;
	}

	public static GeoPoint getGeoPoint(UmLocation location)
	{
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		return getGeoPoint(latitude, longitude);
	}
}
