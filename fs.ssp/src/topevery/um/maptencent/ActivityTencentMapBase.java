package topevery.um.maptencent;

import global.BaseActivity;
import topevery.android.framework.map.MapValue;
import topevery.um.jinan.manager.R;
import topevery.um.map.MapManager;
import topevery.um.map.UmLocation;
import topevery.um.map.UmLocationClient;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.tencent.mapsdk.raster.model.LatLng;
import com.tencent.tencentmap.mapsdk.map.MapView;

public class ActivityTencentMapBase extends BaseActivity implements OnClickListener
{
	private Runnable mRunnable = new Runnable()
	{
		@Override
		public void run()
		{

		}
	};

	/**
	 * 打点
	 */
	public MapView mMapView;
	public ImageButton btnLocation;
	public ImageButton btnPano;
	
	public SimulateLocationOverlay mSimulateLocationOverlay;
	public SimulateMarkerOverlay mSimulateMarkerOverlay;

	public RouteSearchHolder mRouteSearchHolder;

	public MapValue selectResult = null;
	public boolean _isPositionChanged = false;

	public boolean isActivation = false;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basic_map);
		mMapView = (MapView) findViewById(R.id.map);
		btnLocation = (ImageButton) findViewById(R.id.btnLocation);
		btnPano = (ImageButton) findViewById(R.id.btn_pano);
		btnLocation.setOnClickListener(this);
		btnPano.setOnClickListener(this);
		// mMapView.setBuiltInZoomControls(true);
		/** 如果没有继承MapActivity，则必须调用 */
		// mMapView.onCreate(savedInstanceState);

		mSimulateLocationOverlay = new SimulateLocationOverlay(this, mMapView);
		mSimulateMarkerOverlay = new SimulateMarkerOverlay(this, mMapView);

		mRouteSearchHolder = new RouteSearchHolder(this, mMapView);
	}

	public void activation()
	{
		this.selectResult = (MapValue) getIntent().getExtras().getSerializable("SelectResult");
		if (this.selectResult != null && selectResult.absX > 0 && selectResult.absY > 0)
		{
			MapPositionChanged(selectResult.absY, selectResult.absX);

			LatLng latLng = mSimulateMarkerOverlay.setValue(selectResult.absY, selectResult.absX);
			animateTo(latLng);

			if (selectResult.tag != null && selectResult.tag.toString().equalsIgnoreCase("navigation"))
			{
				mMapView.postDelayed(new Runnable()
				{
					@Override
					public void run()
					{
						navigation();
					}
				}, 2000);
			}
		}
		else
		{
			currLocation();
		}
	}

	public void navigation()
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
			latitudeBegin = selectResult.absY;
			longitudeBegin = selectResult.absX;
		}

		latitudeEnd = selectResult.absY;
		longitudeEnd = selectResult.absX;

		// 中国
		mRouteSearchHolder.searchBusRoute(latitudeBegin, longitudeBegin, latitudeEnd, longitudeEnd);
	}

	private void currLocation()
	{
		UmLocation location = UmLocationClient.getLocation();

		// location.geoX = 113.985607;
		// location.geoY = 22.537048;

		if (location != null)
		{
			LatLng latLng = mSimulateLocationOverlay.setValue(location.absY, location.geoX);
			animateTo(latLng);
		}
	}

	private int level = 15;

	@SuppressWarnings("deprecation")
	public void animateTo(LatLng latLng)
	{
		if (latLng != null)
		{
			int zoom = mMapView.getMap().getZoomLevel() < level ? level : mMapView.getMap().getZoomLevel();
			mMapView.getMap().setZoom(zoom);
			mMapView.getMap().animateTo(latLng, mRunnable);
		}
	}

	@Override
	protected void onPause()
	{
		super.onPause();

		mMapView.onPause();
	}

	@Override
	protected void onResume()
	{
		super.onResume();

		if (!isActivation)
		{
			activation();
		}

		mMapView.onResume();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		mMapView.onDestroy();
		mMapView = null;
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId()) {
		case R.id.btnLocation:
			currLocation();
			
			break;
		case R.id.btn_pano:
			UmLocation location = UmLocationClient.getLocation();
			if(location==null){
				return;
			}
			Intent intent =new Intent(this,ActivityTencentPanoMap.class);
			intent.putExtra("lat", location.getLatitude());
			intent.putExtra("lng", location.getLongitude());
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	@Override
	public void finish()
	{
		super.finish();
		this.setResultData();
	}

	private void setResultData()
	{
		if (selectResult != null && _isPositionChanged)
		{
			MapManager.onCompleted(selectResult);
		}
	}

	public void MapPositionChanged(double latitude, double longitude)
	{
		// mPanorama.setPosition(latitude, longitude);
		// mPanoramaView.invalidate();
	}
}
