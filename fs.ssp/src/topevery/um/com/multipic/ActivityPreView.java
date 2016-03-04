package topevery.um.com.multipic;

import java.util.ArrayList;
import java.util.List;

import topevery.um.com.base.BaseActivity;
import topevery.um.com.multipic.zoom.PhotoView;
import topevery.um.com.utils.BitmapUtils;
import topevery.um.com.utils.DisplayUtils;
import topevery.um.jinan.manager.R;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class ActivityPreView extends BaseActivity implements OnClickListener,OnPageChangeListener {

	private ViewPager mPager;
	private GalleryAdapter mAdapter;
	private TextView title;
	private Context mContext;
	private Button btn_bottom_left;
	private Button btn_bottom_right;
	
	private List<String> pathList;
	/**
	 * 要删除的图片
	 */
	private ArrayList<String> deleteList;
	private int screenWidth = 0;
	private int screenHeight = 0;
	private int total = 0;
	private int currentIndex =1;
	private boolean fromCamera = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);
		screenWidth = DisplayUtils.widthPixels;
		screenHeight = DisplayUtils.heightPixels;
		mContext = this;
		pathList = getIntent().getStringArrayListExtra("paths");
		fromCamera = getIntent().getBooleanExtra("from", false);
		deleteList = new ArrayList<String>();
		total = pathList.size();
		initBothBar();
		mPager = (ViewPager) findViewById(R.id.view_pager);
		mAdapter = new GalleryAdapter(getViewByPath(pathList));
		mPager.setAdapter(mAdapter);
		mPager.setOnPageChangeListener(this);
	}

	private void initBothBar() {
		mAbTitleBar.setVisibility(View.VISIBLE);
		mAbTitleBar.setBackgroundResource(R.drawable.main_title_bar_bg);
		View view_title = LayoutInflater.from(this).inflate(
				R.layout.titile_bar_normal, null);
		view_title.findViewById(R.id.btn_left).setOnClickListener(this);
		Button btn_right = (Button) view_title.findViewById(R.id.btn_right);
		if(fromCamera){
			btn_right.setVisibility(View.VISIBLE);
			btn_right.setText(R.string.confirm);
			btn_right.setOnClickListener(this);
		}else{
			btn_right.setVisibility(View.INVISIBLE);
		}
		title = (TextView) view_title.findViewById(R.id.txt_title);
		title.setText(String.format("%s/%s", currentIndex,total));
		mAbTitleBar.addView(view_title, 0);

		mAbBottomBar.setVisibility(View.VISIBLE);
		mAbBottomBar.setBackgroundResource(R.drawable.main_title_bar_bg);
		View view_bottom = LayoutInflater.from(this).inflate(
				R.layout.bottom_bar_choose, null);
		btn_bottom_left = (Button) view_bottom
				.findViewById(R.id.btn_bottom_left);
		btn_bottom_left.setVisibility(View.INVISIBLE);
		btn_bottom_right = (Button) view_bottom
				.findViewById(R.id.btn_bottom_right);
		btn_bottom_right.setOnClickListener(this);
		btn_bottom_right.setSelected(true);
		TextView title_bottom = (TextView) view_bottom
				.findViewById(R.id.txt_bottom_title);
		title_bottom.setVisibility(View.INVISIBLE);
		mAbBottomBar.addView(view_bottom, 0);
	}

	class GalleryAdapter extends PagerAdapter {

		private List<PhotoView> views;

		public GalleryAdapter(List<PhotoView> photoViews) {
			this.views = photoViews;
		}

		@Override
		public int getCount() {
			return views == null ? 0 : views.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(views.get(position));
			return views.get(position);
		}

		@Override
		public int getItemPosition(Object object) {
			return POSITION_NONE;
		}

	}

	private List<PhotoView> getViewByPath(List<String> pathList) {
		List<PhotoView> views = new ArrayList<PhotoView>();
		if (pathList == null || pathList.size() == 0) {
			return views;
		}
		for (String path : pathList) {
			PhotoView photoView = new PhotoView(mContext);
			Bitmap bitmap = BitmapUtils.getPressedSolidBitmapByPath(path,
					screenWidth - DisplayUtils.dip2px(mContext, 10), 520);
			photoView.setImageBitmap(bitmap);
			photoView.setScaleType(ScaleType.CENTER);
			LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			photoView.setLayoutParams(params);
			views.add(photoView);
		}
		return views;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_bottom_left:

			break;
		case R.id.btn_bottom_right:
			if(v.isSelected()){
				v.setSelected(false);
				deleteList.add(pathList.get(currentIndex-1));
			}else{
				v.setSelected(true);
				deleteList.remove(pathList.get(currentIndex-1));
			}
			break;
		case R.id.btn_left:
			if(!fromCamera){
				Intent data = new Intent(this,ActivityChooseMultiPics.class);
				data.putStringArrayListExtra("datas", deleteList);
				setResult(RESULT_OK, data);
			}
			finish();
			break;
		case R.id.btn_right:
			//TODO 把照相结果返回给上报界面
			if(deleteList.size()<=0){
				ActivityChooseMultiPics.value.finish();
				PictureUtils.cameraFinished(pathList.get(0));
			}
			finish();
			break;
			
		default:
			break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int state) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int position) {
		currentIndex = position+1;
		title.setText(String.format("%s/%s", currentIndex,total));
		if(deleteList.contains(pathList.get(position))){
			btn_bottom_right.setSelected(false);
		}else{
			btn_bottom_right.setSelected(true);
		}
	}
}
