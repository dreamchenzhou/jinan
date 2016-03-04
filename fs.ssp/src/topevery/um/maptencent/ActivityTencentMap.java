package topevery.um.maptencent;

import java.util.ArrayList;
import java.util.List;

import topevery.um.map.MapManager;
import topevery.um.map.UmLocation;
import topevery.um.map.UmLocationClient;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.ab.model.AbMenuItem;
import com.tencent.tencentmap.streetviewsdk.StreetViewPanorama;
import com.tencent.tencentmap.streetviewsdk.StreetViewPanorama.OnStreetViewPanoramaCameraChangeListener;
import com.tencent.tencentmap.streetviewsdk.StreetViewPanorama.OnStreetViewPanoramaChangeListener;
import com.tencent.tencentmap.streetviewsdk.StreetViewPanorama.OnStreetViewPanoramaFinishListner;
import com.tencent.tencentmap.streetviewsdk.StreetViewPanoramaCamera;
import com.tencent.tencentmap.streetviewsdk.StreetViewPanoramaView;

import topevery.um.jinan.manager.R;
import global.ListPopAdapter;

@SuppressLint("InflateParams")
@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
public class ActivityTencentMap extends ActivityTencentMapBase implements OnStreetViewPanoramaChangeListener, OnStreetViewPanoramaFinishListner, OnStreetViewPanoramaCameraChangeListener
{
	private StreetViewPanoramaView mPanoramaView;
	private StreetViewPanorama mPanorama;

	// private ActivityTencentMap wThis = this;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	}

	@Override
	public void activation()
	{
		super.activation();

		findStreet();
		initTitleBar();

		isActivation = true;
	}

	private void initTitleBar()
	{
		mAbTitleBar.clearRightView();

		final View popView = mInflater.inflate(R.layout.list_pop, null);
		ListView popListView = (ListView) popView.findViewById(R.id.pop_list);
		List<AbMenuItem> list = new ArrayList<AbMenuItem>();
		list.add(new AbMenuItem("  平面  "));
		list.add(new AbMenuItem("  卫星  "));
		list.add(new AbMenuItem("  交通  "));
		list.add(new AbMenuItem("  街景  "));
		ListPopAdapter mListPopAdapter = new ListPopAdapter(this, list, R.layout.item_list_pop);
		popListView.setAdapter(mListPopAdapter);

		popListView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				mAbTitleBar.hideWindow();

				switch (position)
				{
					case 0:
						mMapView.getMap().setSatelliteEnabled(false);
						mMapView.setVisibility(View.VISIBLE);
						mPanoramaView.setVisibility(View.GONE);
						break;
					case 1:
						mMapView.setVisibility(View.VISIBLE);
						mPanoramaView.setVisibility(View.GONE);
						mMapView.getMap().setSatelliteEnabled(true);
						break;
					case 2:
						// mMapView.setVisibility(View.VISIBLE);
						// mPanoramaView.setVisibility(View.GONE);
						//
						// boolean boTraffic = mMapView.isTraffic();
						// if (boTraffic == false)
						// {
						// int iCurrentLevel = mMapView.getZoomLevel();
						// if (iCurrentLevel < 10) // 实时交通在10级以上才显示
						// {
						// mMapView.getController().setZoom(10);
						// }
						// mMapView.setTraffic(true);
						//
						// MsgBox.makeTextSHORT(wThis, "打开实时交通");
						//
						// }
						// else
						// {
						// mMapView.setTraffic(false);
						// MsgBox.makeTextSHORT(wThis, "关闭实时交通");
						// }
						break;
					case 3:
						mPanoramaView.setVisibility(View.VISIBLE);
						mMapView.setVisibility(View.GONE);
						showStreet();
						break;
					default:
						break;
				}
			}
		});

		View moreBtn = addRightView(false, R.layout.btn_more, R.id.moreBtn);
		moreBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				mAbTitleBar.showWindow(v, popView, true);
			}
		});
	}

	private void findStreet()
	{
		mPanoramaView = (StreetViewPanoramaView) findViewById(R.id.panorama_view);
		mPanorama = mPanoramaView.getStreetViewPanorama();
		mPanorama.setOnStreetViewPanoramaChangeListener(this);
		mPanorama.setOnStreetViewPanoramaFinishListener(this);
		mPanorama.setOnStreetViewPanoramaCameraChangeListener(this);
		mPanoramaView.setVisibility(View.GONE);
	}

	public void showStreet()
	{
		UmLocation location = UmLocationClient.getLocation();
		if (location != null)
		{
			mPanorama.setPosition(location.getLatitude(), location.getLongitude());
			mPanoramaView.invalidate();
		}
	}

	@Override
	public void onStreetViewPanoramaCameraChange(StreetViewPanoramaCamera arg0)
	{

	}

	@Override
	public void OnStreetViewPanoramaFinish(boolean arg0)
	{

	}

	@Override
	public void onStreetViewPanoramaChange(String arg0)
	{

	}
}
