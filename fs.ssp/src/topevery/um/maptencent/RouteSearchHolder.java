package topevery.um.maptencent;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.tencent.lbssearch.TencentSearch;
import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.object.Location;
import com.tencent.lbssearch.object.param.DrivingParam;
import com.tencent.lbssearch.object.param.RoutePlanningParam.DrivingPolicy;
import com.tencent.lbssearch.object.result.DrivingResultObject;
import com.tencent.lbssearch.object.result.RoutePlanningObject;
import com.tencent.mapsdk.raster.model.BitmapDescriptor;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.mapsdk.raster.model.PolylineOptions;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;

import topevery.um.jinan.manager.R;

public class RouteSearchHolder implements HttpResponseListener
{
	private ActivityTencentMapBase mActivity;
	private TencentMap tencentMap;
	private double latitudeBegin = 0;
	private double longitudeBegin = 0;
	private double latitudeEnd = 0;
	private double longitudeEnd = 0;

	public Marker naviMarkBegin;
	public Marker naviMarkEnd;

	public RouteSearchHolder(ActivityTencentMapBase activity, MapView mapView)
	{
		this.mActivity = activity;
		tencentMap = mapView.getMap();
	}

	public void searchBusRoute(double latitudeBegin, double longitudeBegin, double latitudeEnd, double longitudeEnd)
	{
		this.latitudeBegin = latitudeBegin;
		this.longitudeBegin = longitudeBegin;
		this.latitudeEnd = latitudeEnd;
		this.longitudeEnd = longitudeEnd;

		Location[] locations = new Location[2];

		locations[0] = new Location().lat((float) latitudeBegin).lng((float) longitudeBegin);
		locations[1] = new Location().lat((float) latitudeEnd).lng((float) longitudeEnd);

		TencentSearch tencentSearch = new TencentSearch(mActivity);
		DrivingParam drivingParam = new DrivingParam();
		drivingParam.from(locations[0]);
		drivingParam.to(locations[1]);
		// 策略
		drivingParam.policy(DrivingPolicy.LEAST_DISTANCE);
		// 途经点
		// drivingParam.addWayPoint(new Location(39.898938f, 116.348648f));
		tencentSearch.getDirection(drivingParam, this);
	}

	@Override
	public void onSuccess(int statusCode, Header[] headers, BaseObject object)
	{
		if (object != null)
		{
			RoutePlanningObject obj = (RoutePlanningObject) object;

			DrivingResultObject drivingObj = (DrivingResultObject) obj;
			List<DrivingResultObject.Route> driveRoutes = drivingObj.result.routes;

			if (driveRoutes != null && driveRoutes.size() > 0)
			{
				tencentMap.clearAllOverlays();
				drawSolidLine(driveRoutes.get(0).polyline);

				onBeginEnd();

				zoomToSpan(driveRoutes.get(0).polyline);
			}
		}
	}

	@Override
	public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
	{

	}

	private void onBeginEnd()
	{
		LatLng latLngBeing = new LatLng(latitudeBegin, longitudeBegin);
		LatLng latLngEnd = new LatLng(latitudeEnd, longitudeEnd);

		BitmapDescriptor bitmapBeing = BitmapDescriptorFactory.fromResource(R.drawable.pin_start);
		BitmapDescriptor bitmapEnd = BitmapDescriptorFactory.fromResource(R.drawable.pin_end);

		naviMarkBegin = tencentMap.addMarker(new MarkerOptions().snippet("导航").draggable(false).icon(bitmapBeing).position(latLngBeing).snippet("导航"));
		naviMarkEnd = tencentMap.addMarker(new MarkerOptions().draggable(false).icon(bitmapEnd).position(latLngEnd).snippet("终点"));
		naviMarkBegin.showInfoWindow();
	}

	/**
	 * 把地图视图缩放到刚好显示路线
	 * 
	 * @param listPts
	 */
	private void zoomToSpan(List<Location> listPts)
	{
		if (listPts == null)
		{
			return;
		}
		int iPtSize = listPts.size();
		if (iPtSize <= 0)
		{
			return;
		}

		Location geoPtLeftUp = null;
		Location geoPtRightDown = null; // 获取路径点的左上角点，和右下角点

		Location geoPt = null;
		for (int i = 0; i < iPtSize; i++)
		{
			geoPt = listPts.get(i);
			if (geoPt == null)
			{
				continue;
			}

			if (geoPtLeftUp == null)
			{
				geoPtLeftUp = new Location(geoPt.lat, geoPt.lng);
			}
			else
			{
				if (geoPtLeftUp.lat < geoPt.lat)
				{
					geoPtLeftUp.lat = geoPt.lat;
				}
				if (geoPtLeftUp.lng > geoPt.lng)
				{
					geoPtLeftUp.lng = geoPt.lng;
				}
			}

			if (geoPtRightDown == null)
			{
				geoPtRightDown = new Location(geoPt.lat, geoPt.lng);
			}
			else
			{
				if (geoPtRightDown.lat > geoPt.lat)
				{
					geoPtRightDown.lat = geoPt.lat;
				}
				if (geoPtRightDown.lng < geoPt.lng)
				{
					geoPtRightDown.lng = geoPt.lng;
				}
			}

		}

		if (geoPtLeftUp == null || geoPtRightDown == null)
		{
			return;
		}
		tencentMap.zoomToSpan(new LatLng(geoPtLeftUp.lat, geoPtLeftUp.lng), new LatLng(geoPtRightDown.lat, geoPtRightDown.lng));
	}

	/**
	 * 将路线以实线画到地图上
	 * 
	 * @param locations
	 */
	private void drawSolidLine(List<Location> locations)
	{
		tencentMap.addPolyline(new PolylineOptions().addAll(getLatLngs(locations)).color(0xff2200ff));
	}

	protected List<LatLng> getLatLngs(List<Location> locations)
	{
		List<LatLng> latLngs = new ArrayList<LatLng>();
		for (Location location : locations)
		{
			latLngs.add(new LatLng(location.lat, location.lng));
		}
		return latLngs;
	}
}
