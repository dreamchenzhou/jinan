package topevery.um.maptencent;

import global.BaseActivity;
import topevery.um.jinan.manager.R;
import android.os.Bundle;

import com.tencent.tencentmap.streetviewsdk.StreetViewPanorama;
import com.tencent.tencentmap.streetviewsdk.StreetViewPanorama.OnStreetViewPanoramaCameraChangeListener;
import com.tencent.tencentmap.streetviewsdk.StreetViewPanorama.OnStreetViewPanoramaChangeListener;
import com.tencent.tencentmap.streetviewsdk.StreetViewPanorama.OnStreetViewPanoramaFinishListner;
import com.tencent.tencentmap.streetviewsdk.StreetViewPanoramaCamera;
import com.tencent.tencentmap.streetviewsdk.StreetViewPanoramaView;

public class ActivityTencentPanoMap extends BaseActivity {
	private StreetViewPanoramaView mPanoramaView;
	private StreetViewPanorama mPanorama;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tencent_pano);
		double lat = (double) getIntent().getDoubleExtra("lat", 0d);
		double lng = getIntent().getDoubleExtra("lng", 0d);
		mPanoramaView = (StreetViewPanoramaView) findViewById(R.id.panorama_view);
		mPanorama = mPanoramaView.getStreetViewPanorama();
		mPanorama.setPosition(lat, lng);
		mPanorama.setOnStreetViewPanoramaCameraChangeListener(cameraChangeListener);
		//设置新的街景加载的监听函数
		mPanorama.setOnStreetViewPanoramaChangeListener(changeListener);
		//设置街景加载完成的监听函数
		mPanorama.setOnStreetViewPanoramaFinishListener(finishListner);
		settting();
	}

	private OnStreetViewPanoramaCameraChangeListener cameraChangeListener =
			new OnStreetViewPanoramaCameraChangeListener() {

		@Override
		public void onStreetViewPanoramaCameraChange(
				StreetViewPanoramaCamera arg0) {
			// TODO 自动生成的方法存根

		}
	};

	private OnStreetViewPanoramaChangeListener changeListener =
			new OnStreetViewPanoramaChangeListener() {

		@Override
		public void onStreetViewPanoramaChange(String arg0) {
			// TODO 自动生成的方法存根

		}
	};

	private OnStreetViewPanoramaFinishListner finishListner =
			new OnStreetViewPanoramaFinishListner() {

		@Override
		public void OnStreetViewPanoramaFinish(boolean arg0) {
			// TODO 自动生成的方法存根

		}
	};
	
	private void settting(){
		//获取当前街景的街景信息
		mPanorama.getCurrentStreetViewInfo();
		//获取街景视角的水平偏转角（正北为0度）
		mPanorama.getPanoramaBearing();
		//获取街景的俯仰角,仰视为90度
		mPanorama.getPanoramaTilt();
		//获取是否显示控制街景切换室内图的路标
		mPanorama.isIndoorGuidanceEnabled();
		//获取是否允许手势拖动街景
		mPanorama.isPanningGesturesEnabled();
		//获取是否显示街道名字
		mPanorama.isStreetNamesEnabled();
		//获取是否显示控制街景切换的路标
		mPanorama.isUserNavigationEnabled();
		//获取是否允许手势放缩街景
		mPanorama.isZoomGesturesEnabled();
		//设置是否显示控制街景切换室内图的路标;
		mPanorama.setIndoorGuidanceEnabled(false);
		//设置是否允许手势拖动街景
		mPanorama.setPanningGesturesEnabled(true);
		//设置街景视角的水平偏转角（正北为0度）
		mPanorama.setPanoramaBearing(0);
		//设置街景的俯仰角,仰视为90度
		mPanorama.setPanoramaTilt(0);
		//设置是否显示街道名字
		mPanorama.setStreetNamesEnabled(true);
		//设置是否显示控制街景切换的路标
		mPanorama.setUserNavigationEnabled(true);
		//设置是否允许手势放缩街景
		mPanorama.setZoomGesturesEnabled(true);
	}
}
