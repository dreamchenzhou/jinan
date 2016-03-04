package topevery.um.com.multipic;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import topevery.um.com.activity.CaseReportNew;
import topevery.um.com.base.BaseActivity;
import topevery.um.com.camera.CameraHolder;
import topevery.um.com.multipic.album.ImageBucket;
import topevery.um.com.multipic.album.ImageItem;
import topevery.um.com.multipic.album.MediaHelper;
import topevery.um.com.multipic.interfaces.LoadLocalInterface;
import topevery.um.com.multipic.interfaces.SwitchInterface;
import topevery.um.com.utils.DisplayUtils;
import topevery.um.jinan.manager.R;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class ActivityChooseMultiPics extends BaseActivity implements
		OnClickListener, LoadLocalInterface, OnItemClickListener {
	private static final int REQUEST_CODE_PREVIEW = 10;

	private GridView mGridView;

	private AlbumAdapter mAdapter;

	private ActivityChooseMultiPics mContext;

	private List<ImageItem> mData;
	/**
	 * 最大能够选择的相片数量
	 */
	private int maxNum = 0;

	private int count = 0;

	private ArrayList<String> pathList = new ArrayList<String>();

	private List<String> idList = new ArrayList<String>();

	private Button btn_right;

	private Button btn_bottom_left;

	private Button btn_bottom_right;

	private SwitchInterface switcher;

	private View layout_bottom;

	private Stack<Integer> animStack = new Stack<Integer>();

	private boolean isAnim = false;

	private PopupWindow bucketWindow;

	private ListView popListView;

	private List<ImageBucket> buckets;

	private TextView title;
	
	public static ActivityChooseMultiPics value;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_album);
		initBothBar();
		switcher = new DecoderSwitch();
		switcher.on();
		maxNum = getIntent().getIntExtra("max", 0);
		mGridView = (GridView) findViewById(R.id.grid_album);
		layout_bottom = findViewById(R.id.layout_bottom);
		btn_right = (Button) findViewById(R.id.btn_right);
		btn_right.setText("确定");
		btn_right.setOnClickListener(this);
		mContext = this;
		MediaHelper.init(mContext);
		mData = MediaHelper.getAllImageList(true);
		setAddIcon();
		buckets = MediaHelper.getListBuckets(false);
		mAdapter = new AlbumAdapter();
		mGridView.setAdapter(mAdapter);
		value = this;
	}

	private void initBothBar() {
		mAbTitleBar.setVisibility(View.VISIBLE);
		mAbTitleBar.setBackgroundResource(R.drawable.main_title_bar_bg);
		View view_title = LayoutInflater.from(this).inflate(
				R.layout.titile_bar_normal, null);
		view_title.findViewById(R.id.btn_left).setOnClickListener(this);
		view_title.findViewById(R.id.btn_right).setOnClickListener(this);
		title = (TextView) view_title.findViewById(R.id.txt_title);
		title.setText(R.string.all_pic);
		mAbTitleBar.addView(view_title, 0);

		mAbBottomBar.setVisibility(View.VISIBLE);
		mAbBottomBar.setBackgroundResource(R.drawable.main_title_bar_bg);
		View view_bottom = LayoutInflater.from(this).inflate(
				R.layout.bottom_bar_normal, null);
		btn_bottom_left = (Button) view_bottom
				.findViewById(R.id.btn_bottom_left);
		btn_bottom_left.setOnClickListener(this);
		btn_bottom_right = (Button) view_bottom
				.findViewById(R.id.btn_bottom_right);
		btn_bottom_right.setOnClickListener(this);
		btn_bottom_right.setEnabled(false);
		TextView title_bottom = (TextView) view_bottom
				.findViewById(R.id.txt_bottom_title);
		title_bottom.setVisibility(View.INVISIBLE);
		mAbBottomBar.addView(view_bottom, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQUEST_CODE_PREVIEW:
				ArrayList<String> deleteList = data
						.getStringArrayListExtra("datas");
				for (String path : deleteList) {
					for (int i = 0; i < pathList.size(); i++) {
						if (pathList.get(i).equals(path)) {
							// 删除对应的id和路径
							idList.remove(i);
							pathList.remove(i);
							count--;
							resetUI();
						}
					}
				}
				mAdapter.notifyDataSetChanged();
				break;
			}
		}
	}

	private void setAddIcon() {
		// 添加点击按钮
		ImageItem item = new ImageItem();
		item.setType(1);
		mData.add(0, item);
	}
	
	private void resetUI() {
		btn_right.setText(String.format("%s/%s确定", count, maxNum));
		if (count <= 0) {
			btn_bottom_right.setEnabled(false);
		} else {
			btn_bottom_right.setEnabled(true);
			btn_bottom_right.setText(String.format("(%s)预览", count));
		}
	}

	class AlbumAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mData.size();
		}

		@Override
		public ImageItem getItem(int position) {
			return mData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.grid_item_album, null);
				viewHolder.img_pic = (ImageView) convertView
						.findViewById(R.id.img_pic);
				viewHolder.img_selector = (ImageButton) convertView
						.findViewById(R.id.img_selector);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			// 重新初事化
			{
				viewHolder.img_pic.setImageBitmap(null);
				viewHolder.img_selector.setSelected(false);
				viewHolder.img_selector.setVisibility(View.VISIBLE);
				viewHolder.img_pic.setOnClickListener(null);
				convertView.setOnClickListener(null);
			}
			viewHolder.img_selector.setTag(position);
			viewHolder.img_selector.setOnClickListener(mContext);
			if (idList.contains(getItem(position).getId())) {
				viewHolder.img_selector.setSelected(true);
			} else {
				viewHolder.img_selector.setSelected(false);
			}
			viewHolder.img_pic.setTag(R.id.img_selector,
					viewHolder.img_selector);
			if (TextUtils.isEmpty(getItem(position).getThumbNail())) {
				// viewHolder.img_pic.setImageBitmap(BitmapCache.getInstance().
				// getBitmap(getItem(position).getPath()));
				viewHolder.img_pic.setTag(getItem(position).getPath());
				BitmapLoader.loadImage(getItem(position).getPath(),
						viewHolder.img_pic, switcher, mContext);
			} else {
				// viewHolder.img_pic.setImageBitmap(BitmapCache.getInstance().
				// getBitmap(getItem(position).getThumbNail()));
				viewHolder.img_pic.setTag(getItem(position).getThumbNail());
				BitmapLoader.loadImage(getItem(position).getThumbNail(),
						viewHolder.img_pic, switcher, mContext);
			}
			
			if(getItem(position).getType()==1){
				viewHolder.img_selector.setVisibility(View.GONE);
				viewHolder.img_pic.setImageResource(R.drawable.button_icon_picker_camera);
				viewHolder.img_pic.setOnClickListener(ActivityChooseMultiPics.this);
			}
			LayoutParams params = viewHolder.img_pic.getLayoutParams();
			params.height = DisplayUtils.dip2px(mContext, 100);
			viewHolder.img_pic.setLayoutParams(params);
			return convertView;
		}

		class ViewHolder {
			public ImageView img_pic;

			public ImageButton img_selector;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.img_selector:
			ImageButton view = (ImageButton) v;
			int position = (Integer) v.getTag();
			ImageItem item = mData.get(position);
			if (count >= maxNum) {
				if (view.isSelected()) {
					view.setSelected(false);
					count--;
					pathList.remove(item.getPath());
					idList.remove(item.getId());
				} else {
					return;
				}
			} else {
				if (view.isSelected()) {
					view.setSelected(false);
					count--;
					pathList.remove(item.getPath());
					idList.remove(item.getId());
				} else {
					view.setSelected(true);
					pathList.add(item.getPath());
					idList.add(item.getId());
					count++;
				}
			}
			if (count > 0) {
				btn_right.setText(String.format("%s/%s确定", count, maxNum));
			} else {
				btn_right.setText("确定");
			}

			if (count <= 0) {
				btn_bottom_right.setEnabled(false);
				btn_bottom_right.setText("预览");
			} else {
				btn_bottom_right.setEnabled(true);
				btn_bottom_right.setText(String.format("(%s)预览", count));
			}
			break;

		case R.id.btn_bottom_left:
			initPopUpWindow();
			showPopWindow();
			break;
		case R.id.btn_bottom_right:
			Intent preview_Intent = new Intent(this, ActivityPreView.class);
			preview_Intent.putStringArrayListExtra("paths", pathList);
			startActivityForResult(preview_Intent, REQUEST_CODE_PREVIEW);
			break;
		case R.id.btn_left:
			finish();
			break;
		case R.id.btn_right:
			if (pathList.size() > 0) {
				Intent intent = new Intent(this, CaseReportNew.class);
				intent.putExtra("datas", pathList);
				setResult(RESULT_OK, intent);
			}
			finish();
			break;
		case R.id.img_pic:
			Intent intent = new Intent(this, CameraHolder.class);
			startActivity(intent);
			break;
		default:
			break;
		}

	}

	@Override
	public void finish() {
		super.finish();
		switcher.off();
	}

	@Override
	public void onLoadLocalFailed(ImageView imageView) {
		ImageButton checkBox = (ImageButton) imageView
				.getTag(R.id.img_selector);
		checkBox.setVisibility(View.GONE);
	}

	@Override
	public void onLoadLocalSuccess(ImageView imageView) {

	}

	private void initPopUpWindow() {
		bucketWindow = new PopupWindow(this);
		View popView = LayoutInflater.from(this).inflate(R.layout.pop_list,
				null);
		bucketWindow.setContentView(popView);
		bucketWindow.setOutsideTouchable(true);
		bucketWindow.setFocusable(true);
		bucketWindow.update();
		popListView = (ListView) popView.findViewById(R.id.list_pop);
		popListView.setAdapter(new PopAdapter());
		popListView.setOnItemClickListener(this);
		bucketWindow.setWidth(DisplayUtils.dip2px(this, 100));
		bucketWindow.setHeight(DisplayUtils.dip2px(this, 200));
	}

	public void showPopWindow() {
		// int[] location = new int[2];
		// btn_buckets.getLocationOnScreen(location);
		// bucketWindow.showAtLocation(btn_buckets, Gravity.TOP, location[0],
		// location[1]+DisplayUtils.dip2px(this, 48));
		bucketWindow.showAsDropDown(btn_bottom_left);
	}

	class PopAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return buckets.size();
		}

		@Override
		public ImageBucket getItem(int position) {
			return buckets.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View contentView, ViewGroup parent) {
			contentView = LayoutInflater.from(mContext).inflate(
					android.R.layout.simple_list_item_1, null);
			TextView txt_name = (TextView) contentView
					.findViewById(android.R.id.text1);
			txt_name.setText(getItem(position).getName());
			txt_name.setTextColor(Color.WHITE);
			return contentView;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		ImageBucket bucket = buckets.get(position);
		if (!bucket.getName().equals(title.getText().toString().trim())) {
			mData = bucket.getImageList();
			setAddIcon();
			mAdapter.notifyDataSetChanged();
			title.setText(bucket.getName());
		}
		bucketWindow.dismiss();
	}


}
