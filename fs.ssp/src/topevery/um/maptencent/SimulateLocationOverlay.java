package topevery.um.maptencent;

import com.tencent.mapsdk.raster.model.BitmapDescriptor;
import com.tencent.mapsdk.raster.model.BitmapDescriptorFactory;
import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.mapsdk.raster.model.Marker;
import com.tencent.mapsdk.raster.model.MarkerOptions;
import com.tencent.tencentmap.mapsdk.map.MapView;
import com.tencent.tencentmap.mapsdk.map.TencentMap;

import topevery.um.jinan.manager.R;

/**
 * gps位置图层
 * 
 * @author martin.zheng
 * 
 */
public class SimulateLocationOverlay
{
	private TencentMap tencentMap;
	private Marker marker;

	public SimulateLocationOverlay(ActivityTencentMapBase activity, MapView mapView)
	{
		tencentMap = mapView.getMap();
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
			BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.mark_location);
			marker = tencentMap.addMarker(new MarkerOptions().draggable(false).icon(bitmapDescriptor).position(latLng).snippet(latLng.toString()));
		}
		else
		{
			marker.setPosition(latLng);
			marker.setSnippet(latLng.toString());
		}
	}
}
