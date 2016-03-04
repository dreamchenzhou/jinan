package topevery.um.maptencent;

import topevery.android.framework.map.MapValue;
import topevery.um.map.UmLocation;
import topevery.um.map.UmLocationClient;

import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;
import com.tencent.tencentmap.mapsdk.map.TencentMap.OnMapClickListener;

/**
 * 打点图层(地图上选择位置)
 * 
 * @author martin.zheng
 * 
 */
public class SimulateMarkerOverlay implements OnMapClickListener
{
	private ActivityTencentMapBase mActivity;
	private TencentMap tencentMap;
	private Marker marker;

	public SimulateMarkerOverlay(ActivityTencentMapBase activity, MapView mapView)
	{
		this.mActivity = activity;

		tencentMap = mapView.getMap();
		tencentMap.setOnMapClickListener(this);
	}

	public LatLng setValue(double latitude, double longitude)
	{
		LatLng latLng = new LatLng(latitude, longitude);
		setValue(latLng);
		return latLng;
	}

	public void setValue(LatLng latLng)
	{
		if (marker == null)
		{
			marker = tencentMap.addMarker(new MarkerOptions().draggable(false).icon(BitmapDescriptorFactory.defaultMarker()).position(latLng).snippet(latLng.toString()));
		}
		else
		{
			marker.setPosition(latLng);
			marker.setSnippet(latLng.toString());
		}

		mActivity._isPositionChanged = true;
		if (mActivity.selectResult == null)
		{
			mActivity.selectResult = new MapValue();
		}
		mActivity.selectResult.absX = latLng.getLongitude();
		mActivity.selectResult.absY = latLng.getLatitude();

		mActivity.MapPositionChanged(mActivity.selectResult.absY, mActivity.selectResult.absX);

		mActivity.animateTo(latLng);

		// test();
	}

	@Override
	public void onMapClick(LatLng latLng)
	{
		setValue(latLng);
	}

	private void test()
	{
		double latitudeBegin = 0;
		double longitudeBegin = 0;
		double latitudeEnd = 0;
		double longitudeEnd = 0;

		UmLocation location = UmLocationClient.getLocation();

		if (location != null)
		{
			latitudeBegin = location.absY;
			longitudeBegin = location.absX;
		}
		else
		{
			latitudeBegin = mActivity.selectResult.absY;
			longitudeBegin = mActivity.selectResult.absX;
		}

		latitudeEnd = mActivity.selectResult.absY;
		longitudeEnd = mActivity.selectResult.absX;

		mActivity.mRouteSearchHolder.searchBusRoute(latitudeBegin, longitudeBegin, latitudeEnd, longitudeEnd);
	}
}
