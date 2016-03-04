package topevery.um.com.activity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import topevery.android.core.MsgBox;
import topevery.android.framework.map.MapValue;
import topevery.android.framework.map.OnCompletedListener;
import topevery.um.com.adapter.VoiceAdapter;
import topevery.um.com.base.BaseActivity;
import topevery.um.com.camera.CameraUtils;
import topevery.um.com.camera.OnCameraListener;
import topevery.um.com.casereport.report.CaseTemp;
import topevery.um.com.data.CaseAccept;
import topevery.um.com.data.CaseType;
import topevery.um.com.data.DatabaseAttach;
import topevery.um.com.data.DatabaseEvtRes;
import topevery.um.com.data.Serializer;
import topevery.um.com.main.MainDialog;
import topevery.um.com.main.MainProessDialog;
import topevery.um.com.media.ChatMessage;
import topevery.um.com.media.MediaPlayer;
import topevery.um.com.media.RecordVoiceBtnController;
import topevery.um.com.media.RecordVoiceBtnController.AudioCallback;
import topevery.um.com.multipic.ActivityChooseMultiPics;
import topevery.um.com.multipic.PictureUtils;
import topevery.um.com.multipic.PictureUtils.OnTakePictureCallBack;
import topevery.um.com.task.ReportTask;
import topevery.um.com.utils.BitmapUtils;
import topevery.um.com.utils.DecimalUtils;
import topevery.um.com.utils.DisplayUtils;
import topevery.um.com.utils.PathUtils;
import topevery.um.com.view.attachview.AttachView;
import topevery.um.jinan.manager.R;
import topevery.um.map.MapManager;
import topevery.um.map.ReceiveUmLocationListener;
import topevery.um.map.UmLocation;
import topevery.um.map.UmLocationClient;
import topevery.um.maptencent.GeoCoderHolder;
import topevery.um.net.newbean.AttachInfo;
import topevery.um.net.newbean.AttachInfoCollection;
import topevery.um.net.newbean.EvtParaForIos;
import topevery.um.net.srv.ServiceHandle;
import topevery.um.net.up.UploadHandleHttp;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.android.bbalbs.common.a.c;
import com.tencent.map.geolocation.TencentLocation;

public class CaseReportNew extends BaseActivity implements OnClickListener,
		AudioCallback, OnFocusChangeListener,OnTakePictureCallBack,ReceiveUmLocationListener,OnCompletedListener {

	private static final int REQUEST_CODE_GET_LOCAL_PICTURE = 11;

	private ImageButton btn_camera;

	private ImageButton btn_album;

	private ImageButton btn_clear;

	private Button btn_choose;

	private EditText txt_addr;

	private ImageButton btn_location;

	private Button btn_report;

	private EditText txt_des;

	public ListView mList;

	private VoiceAdapter mAdapter;

	private GridAttachAdapter attachAdapter;
	
	private TextView voice_length_tv;
	private RecordVoiceBtnController btn_record;

	private GridView grid_attachView;

	private Dialog pDialog;

	public EvtParaForIos para;

	private String phoneNum;

	String path = PathUtils.getTempPath();

	String path2 = PathUtils.getTemp();

	String savePath = PathUtils.getSavePhotoPath();
	private AttachInfo audioInfo;

	private Animation anim_location;

	private ArrayList<String> pathList = new ArrayList<String>();

	private MapValue selectResult = new MapValue();
	
	private TencentLocation mLocation;
	/**
	 * 上报案件失败
	 */
	public boolean reportFailed = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		UmLocationClient.addLocationListener(this);
		setContentView(R.layout.casereport_new);
		para = (EvtParaForIos) getIntent().getSerializableExtra("temp");
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		phoneNum = telephonyManager.getLine1Number();
		clearTempPhotos();
		initTitleBar();
		findView();
		initBaiDu();
		setUI();
		PictureUtils.addTakePictureCallBack(this);
	}

	/**
	 * 清理tempphotos 文件加下的文件
	 */
	private void clearTempPhotos() {
		File file = new File(PathUtils.getTempPhotoPath());
		if (file.isDirectory()) {
			String[] pathList = file.list();
			String dir = file.getPath();
			for (int i = 0; i < pathList.length; i++) {
				File chileFile = new File(dir+File.separator+pathList[i]);
				if (chileFile.exists()) {
					chileFile.delete();
				}
			}
		}
	}

	public void invalidate() {
		LinearLayout layout = (LinearLayout) findViewById(R.id.layout_report);
		layout.setWillNotCacheDrawing(true);
		layout.setDrawingCacheEnabled(false);

		layout.requestLayout();
		layout.invalidate();
	}

	@Override
	public void finish() {
		MediaPlayer.getInstance().stop();
		super.finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void initTitleBar() {
		mAbBottomBar.setVisibility(View.GONE);
		mAbTitleBar.setVisibility(View.VISIBLE);
		mAbTitleBar.setBackgroundResource(R.drawable.main_title_bar_bg);
		View view = LayoutInflater.from(this).inflate(
				R.layout.titile_bar_normal, null);
		view.findViewById(R.id.btn_left).setOnClickListener(this);
		view.findViewById(R.id.btn_right).setOnClickListener(this);
		TextView title = (TextView) view.findViewById(R.id.txt_title);
		title.setText("问题投诉");
		mAbTitleBar.addView(view, 0);
	}

	private void initBaiDu() {

	}

	private void findView() {
		btn_camera = (ImageButton) findViewById(R.id.btn_camera);
		btn_album = (ImageButton) findViewById(R.id.btn_album);
		btn_choose = (Button) this.findViewById(R.id.btn_choose);
		txt_addr = (EditText) this.findViewById(R.id.txt_addr);
		btn_clear = (ImageButton) findViewById(R.id.btn_clear);
		btn_location = (ImageButton) findViewById(R.id.btn_location);
		btn_report = (Button) findViewById(R.id.btn_report);
		txt_des = (EditText) findViewById(R.id.txt_des);
		mList = (ListView) findViewById(R.id.list_voice);
		
		grid_attachView = (GridView) findViewById(R.id.grid_attachview);
		grid_attachView.getParent().requestDisallowInterceptTouchEvent(true);

		voice_length_tv = (TextView) this.findViewById(R.id.voice_length_tv);
		btn_record = (RecordVoiceBtnController) this
				.findViewById(R.id.btn_record);

		btn_record.setAudioCallback(this);

		btn_choose.setOnClickListener(this);
		btn_album.setOnClickListener(this);
		btn_camera.setOnClickListener(this);
		txt_addr.setOnFocusChangeListener(this);
		btn_clear.setOnClickListener(this);
		btn_location.setOnClickListener(this);
		btn_report.setOnClickListener(this);

		mAdapter = new VoiceAdapter(this, btn_record);
		mList.setAdapter(mAdapter);

		AttachInfoCollection collection = new AttachInfoCollection();
		AttachInfo info= new AttachInfo();
		info.setViewType(1);
		collection.add(info);
		attachAdapter = new GridAttachAdapter(this, collection, this, this);
		grid_attachView.setAdapter(attachAdapter);
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.layout_report);
		layout.setDrawingCacheEnabled(false);
		layout.setWillNotCacheDrawing(true);
		layout.setAlwaysDrawnWithCacheEnabled(false);
	}

	private void setUI() {
		if (para != null) {
			txt_addr.setText(para.getEvtPos());
			txt_des.setText(para.getEvtDesc());
			AttachInfoCollection picCollection = new AttachInfoCollection();
			if (para.getAttachs() != null) {
				for (AttachInfo info : para.getAttachs()) {
					if (info != null) {
						if (info.getType() == 0) {
							picCollection.add(info);
						} else if (info.getType() == 1) {
							ChatMessage msg = new ChatMessage(
									new File(info.getUri()), info.getName());
							mAdapter.AddChatMessage(msg);
							mList.setVisibility(View.VISIBLE);
						}
					}
				}
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQUEST_CODE_GET_LOCAL_PICTURE:
				ArrayList<String> tempList = data
						.getStringArrayListExtra("datas");

				ArrayList<String> targetList = new ArrayList<String>();
				String tempPhotosDir = PathUtils.getTempPhotoPath();
				// 压缩
				for (String string : tempList) {
					String fileName = string.substring(string.lastIndexOf("/"));
					File file = new File(tempPhotosDir, fileName);
					if (file.exists()) {
						MsgBox.makeTextSHORT(this, "该图片已添加");
						continue;
					} else {
						try {
							file.createNewFile();
							// 图片复制到里一个目录，然后压缩他们，上传成功之后要删除
							BitmapUtils.copy(new File(string), file, false);
							BitmapUtils.compress(file.getAbsolutePath(),
									CameraUtils.newValue);
							targetList.add(file.getAbsolutePath());
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				for (String filepath : targetList) {
					AttachInfo attachInfo = new AttachInfo();
					attachInfo.setUri(filepath);
					attachInfo.setName(filepath.substring(filepath.lastIndexOf("/") + 1));
					attachInfo.setType(0);
					attachAdapter.addValue(attachInfo);
				}
				break;

			default:
				break;
			}
		}
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add:
			if (attachAdapter.getCount() >= 6) {
				MsgBox.show(this, "最多添加5张图片");
			} else {
				  Intent album_inten = new Intent(this,
				 ActivityChooseMultiPics.class); album_inten.putExtra("max", 6
				 - attachAdapter.getCount());
				 startActivityForResult(album_inten,
				  REQUEST_CODE_GET_LOCAL_PICTURE);
			}
			break;

		case R.id.btn_delete:
			int position = (Integer) v.getTag();
			attachAdapter.removeIndex(position);
			break;
		case R.id.btn_voice_del:
//			 delAudio();
			break;
		case R.id.voice_body:
			// playAudio();
			break;
		case R.id.btn_clear:
			txt_addr.setText("");
			break;
		case R.id.btn_location:
			
			break;
		case R.id.btn_choose:
			MapManager.show(this, selectResult, this);
			break;
		case R.id.btn_report:
			report();
			break;
		case R.id.btn_right:
			if (exeist()) {
				if (mAdapter.getAttachInfoCollection() != null
						&& mAdapter.getAttachInfoCollection().size() > 0) {
					mAdapter.removeAllFile();
				}
				Intent intent = new Intent(this, CaseTemp.class);
				startActivity(intent);
				finish();
			} else {
				MainDialog.askYes(this, "您的草稿箱里没有东西！");
			}
			break;
		case R.id.btn_left:
			onBackPressed();
			break;
		default:
			break;
		}
	}

	/** 有坐标时，btnMap显示坐标值，截取小数点后两位 */
	private void setBtnMapText(double absX, double absY) {
		String txt_x = DecimalUtils.format2(absX);
		String txt_y = DecimalUtils.format2(absY);
		String text = String.format("%s，%s", txt_x, txt_y);
		// btnMap.setText(text);
	}

	@Override
	public void audioCallback(ChatMessage chatMessage) {
		// audioFile = chatMessage.getFile();
		// mChatMessage = chatMessage;
		// voice_length_tv.setText(chatMessage.getName() + "\"");
		// voice_body.setVisibility(View.VISIBLE);
		if (mAdapter != null) {
			mAdapter.AddChatMessage(chatMessage);
			mList.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// if(hasFocus){
		// btn_clear.setVisibility(View.VISIBLE);
		// }else{
		// btn_clear.setVisibility(View.GONE);
		// }

	}

	/*
	 * @Override public void onBDReceiveLocation(BDLocation location) {
	 * if(location!=null){ if(para==null){ para = new EvtPara(); } para.absX =
	 * location.getLatitude(); para.absY = location.getLongitude();
	 * 
	 * LatLng latLng = new LatLng(location.getLatitude(),
	 * location.getLongitude()); anim_location =
	 * AnimationUtils.loadAnimation(this, R.anim.rotate_location);
	 * btn_location.setImageResource(R.drawable.selector_location);
	 * btn_location.setAnimation(anim_location);
	 * btn_location.startAnimation(anim_location); mSearch.reverseGeoCode(new
	 * ReverseGeoCodeOption().location(latLng) ); //
	 * btn_location.setImageResource(R.drawable.icon_location);
	 * 
	 * BaiduLocationAPI.stop();
	 * BaiduLocationAPI.removeBaiduLocationListener(this); } }
	 */

	/*
	 * @Override public void onGetReverseGeoCodeResult(ReverseGeoCodeResult
	 * result) { if (result == null || result.error !=
	 * SearchResult.ERRORNO.NO_ERROR) {// 抱歉，未能找到结果
	 * btn_location.clearAnimation();
	 * btn_location.setImageResource(R.drawable.selector_location); return; }
	 * if(!isFinishing()){ btn_location.clearAnimation();
	 * btn_location.setImageResource(R.drawable.icon_location);
	 * txt_addr.setText(result.getAddress()); } }
	 */

	private void report() {
		if (checkValue()) {
			// 案件上报成功不需要重新获取para
			if(!reportFailed||para==null){
				getValue();
			}
			new ReportTask(this).execute(para);
		}
	}

	private boolean checkValue() {
		StringBuffer sb = new StringBuffer();
		if (TextUtils.isEmpty(txt_des.getText().toString().trim())
				&& mAdapter.getAttachInfoCollection().size() == 0) {
			sb.append("问题描述没有添加！");
			sb.append("\r\n");
		}

		if (txt_des.getText().toString().length() > 500) {
			sb.append("字数不能超过500！");
			sb.append("\r\n");
		}
		if (!TextUtils.isEmpty(sb.toString())) {
			MsgBox.show(this, sb.toString());
			return false;
		}
		return true;
	}

	private void getValue() {
		if (para == null) {
			para = new EvtParaForIos();
		}
		if (!TextUtils.isEmpty(txt_des.getText().toString().trim())) {
			para.setEvtDesc(txt_des.getText().toString().trim());
		}
		AttachInfoCollection collection = attachAdapter.getAttachInfoCollection();
		AttachInfoCollection audioCollection = mAdapter
				.getAttachInfoCollection();

		if (collection == null) {
			collection = new AttachInfoCollection();
		}

		if (audioCollection != null && audioCollection.size() > 0) {
			for (AttachInfo attachInfo : audioCollection) {
				collection.add(attachInfo);
			}
		}
		para.setAttachs(collection);

		if (!TextUtils.isEmpty(txt_addr.getText().toString().trim())) {
			para.setEvtPos(txt_addr.getText().toString().trim());
		}
		// TODO
		// para.linkPhone = SpUtils.getString(this, SettingActivity.PHONE);
		// String sex= SpUtils.getInt(this, SettingActivity.SEX)==0?"先生":"女士";
		// para.linkman = SpUtils.getString(this, SettingActivity.NAME)+sex;
	}

	private void setDialog() {
		if (pDialog == null) {
			pDialog = MainProessDialog.createLoadingDialog(this,
					"上报中，请稍等... ...", false, false);
		}
	}

	private void setDialogDismiss() {
		MainDialog.setDismiss();
	}

//	private class ReportAsyncTask extends AsyncTask<EvtPara, Void, EvtRes> {
//		public ReportAsyncTask() {
//			setDialog();
//		}
//
//		@Override
//		protected EvtRes doInBackground(EvtPara... params) {
//			EvtRes res = null;
//			EvtPara para = params[0];
//			try {
//				// /boolean handleValue = UploadHandle.UploadCore(para.attachs);
//				UploadHandleHttp uploadHandleHttp = new UploadHandleHttp();
//				boolean handleValue = uploadHandleHttp.upload(para.);
//				if (handleValue) {
//					res = ServiceHandle.ReportEvtInfo(para);
//				} else {
//					res = new EvtRes();
//					res.isSuccess = false;
//					res.errorMessage = "图片上传失败,请重试";
//					res.caseType = CaseType.reportFail;
//					res.caseAccept = CaseAccept.unaccepted;
//				}
//				if (res != null && res.isSuccess) {
//					res.isSuccess = true;
//					res.caseType = CaseType.reportSuccess;
//					// TODO confirm
//					res.caseAccept = CaseAccept.accepted;
//					res.evtPara = para;
//					DatabaseEvtRes.insert(res);
//				}
//			} catch (Exception e) {
//				res = new EvtRes();
//				res.isSuccess = false;
//				res.errorMessage = e.toString();
//			}
//			return res;
//		}
//
//		@Override
//		protected void onPostExecute(final EvtRes result) {
//			pDialog.hide();
//			if (result != null) {
//				if (result.isSuccess) {
//					String text = result.evtCode + "上报成功";
//					DatabaseAttach.insert(result.evtCode,
//							result.evtPara.attachs);
//					MainDialog.askYes(CaseReportNew.this, text,
//							new View.OnClickListener() {
//
//								@Override
//								public void onClick(View v) {
//									setDialogDismiss();
//									// 上传成功删除图片
//									// 上传成功删除录音
//									mAdapter.removeAllFile();
//									finish();
//								}
//							});
//				} else {
//					MainDialog.askYes(CaseReportNew.this, result.errorMessage);
//				}
//			} else {
//				MainDialog.askYes(CaseReportNew.this, "网络故障");
//			}
//		}
//
//		@Override
//		protected void onPreExecute() {
//			pDialog.show();
//		}
//	}

	@Override
	public void onBackPressed() {
		if (mAdapter.getAttachInfoCollection().size() == 0
				&& attachAdapter.getCount() == 1) {
			finish();
		} else {
			MainDialog.show(CaseReportNew.this, "案件尚未上报，是否保存？",
					new View.OnClickListener() {

						@Override
						public void onClick(View v) {
							save();
						}
					}, new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							setDialogDismiss();
							attachAdapter.removeAll();
							mAdapter.removeAllFile();
							finish();
						}
					});
		}
	}

	private void save() {
		try {
			if (para == null) {
				para = new EvtParaForIos();
			}
			para.setEvtDesc(txt_des.getText().toString().trim());
			para.setEvtPos(txt_addr.getText().toString().trim());
			AttachInfoCollection collection = attachAdapter.getAttachInfoCollection();
			// if(createAudioAttach()!=null){
			// collection.add(createAudioAttach());
			// }
			List<String> tempList = new ArrayList<String>();
			List<String> pathList = new ArrayList<String>();
			for (int i = 0; i < collection.size(); i++) {
				AttachInfo info = collection.get(i);
				if(TextUtils.isEmpty(info.getUri())){
					continue;
				}
				pathList.add(info.getUri());
				collection.get(i).setUri(savePath + "/"
						+ info.getUri().substring(info.getUri().lastIndexOf("/")));
				tempList.add(collection.get(i).getUri());
			}

			AttachInfoCollection audioCollection = mAdapter
					.getAttachInfoCollection();
			if (audioCollection != null && audioCollection.size() > 0) {
				for (AttachInfo attachInfo : audioCollection) {
					collection.add(attachInfo);
				}
			}
			para.setAttachs(collection);
			//TODO modify
//			para.attachs.attachInfo.isSave = true;
//
//			if (!para.attachs.isEmpty) {
//				para.attachs.attachInfo.isSave = true;
//			}
			Serializer.writeObject(para, path);

			// 复制到savephotos目录，删除tempphotos;
			new CopyThread(pathList, tempList).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		setDialogDismiss();
		finish();
	}

	private boolean exeist() {
		File file = new File(path2);
		File[] files = file.listFiles();
		if (files == null || files.length == 0) {
			return false;
		} else {
			return true;
		}
	}

	public void setListViewHeightBasedOnChildren() {
		ListView listView = mList;
		if (listView == null)
			return;

		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	private class CopyThread extends Thread {
		private List<String> fromList;

		private List<String> toList;

		public CopyThread(List<String> from, List<String> to) {
			this.fromList = from;
			this.toList = to;
		}

		@Override
		public void run() {
			super.run();
			for (int i = 0; i < fromList.size(); i++) {
				String fromPath = fromList.get(i);
				String toPath = toList.get(i);
				BitmapUtils.copy(fromPath, toPath, true);
			}
		}
	}

	private class GridAttachAdapter extends BaseAdapter {

		private AttachInfoCollection mDatas = null;
		private OnClickListener onDeleteListener;

		private OnClickListener onAddListener;

		private Context context;

		public GridAttachAdapter(Context context,
				AttachInfoCollection mAttachInfoCollection,
				OnClickListener deleteListener, OnClickListener addListener) {
			this.context = context;
			this.mDatas = mAttachInfoCollection;
			this.onDeleteListener = deleteListener;
			this.onAddListener = addListener;
		}

		@Override
		public int getCount() {
			return mDatas==null?0:mDatas.size();
		} 

		@Override
		public AttachInfo getItem(int position) {
			return mDatas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_attachview, null);
			}
			ImageButton btn_delete = (ImageButton) convertView.findViewById(R.id.btn_delete);
			ImageButton btn_add = (ImageButton) convertView.findViewById(R.id.btn_add);
			//TODO inflate path to bitmap
			btn_delete.setTag(position);
			if(getItem(position).getViewType() ==1){
				btn_add.setOnClickListener(onAddListener);
				btn_add.setImageResource(R.drawable.icon_add_picture);
				btn_delete.setVisibility(View.GONE);
			}else{
				btn_add.setOnClickListener(null);
				Bitmap bitmap = BitmapUtils.getBitmapByPath(getItem(position).getUri(), 100, 200);
				btn_delete.setVisibility(View.VISIBLE);
				if(bitmap!=null){
					btn_add.setImageBitmap(bitmap);
				}else{
					btn_add.setImageResource(R.drawable.post_image_loading_failed);
				}
			}
			btn_delete.setOnClickListener(onDeleteListener);
			LayoutParams params = btn_add.getLayoutParams();
			params.height = DisplayUtils.dip2px(context, 100);
			btn_add.setLayoutParams(params);
			return convertView;
		}
		
		public AttachInfoCollection getAttachInfoCollection(){
			AttachInfoCollection collection = new AttachInfoCollection();
			collection.addAll(mDatas);
			collection.remove(collection.size()-1);
			return collection ;
		}
		
		public void setAttachinfoCollection(AttachInfoCollection collection){
			this.mDatas = collection;
			notifyDataSetChanged();
		}
		public void addValue(AttachInfo info){
			AttachInfo addInfo = mDatas.get(mDatas.size()-1);
			mDatas.remove(mDatas.size()-1);
			mDatas.add(info);
			mDatas.add(addInfo);
			notifyDataSetChanged();
		}
		
		public void removeIndex(int position){
			AttachInfo info = mDatas.get(position);
			mDatas.remove(position);
			notifyDataSetChanged();
			File tempFile = new File(info.getUri());
			if(tempFile.exists()){
				tempFile.delete();
			}
		}
		
		/***
		 * 删除所有文件，并且删除所有gird
		 */
		public void removeAll(){
			if(mDatas==null||mDatas.size()==0){
				return;
			}
			for (AttachInfo info : mDatas) {
				if(TextUtils.isEmpty(info.getUri())){
					return;
				}
				File tempFile = new File(info.getUri());
				if(tempFile.exists()){
					tempFile.delete();
				}
			}
			mDatas.clear();
			notifyDataSetChanged();
		}

	}

	@Override
	public void onCamereFinished(String path) {
		AttachInfo attachInfo = new AttachInfo();
		attachInfo.setUri(path);
		attachInfo.setName(path.substring(path.lastIndexOf("/") + 1));
		attachInfo.setType(0);
		attachAdapter.addValue(attachInfo);
	}

	/**
	 * 定位回调接口
	 */
	@Override
	public void onReceiveUmLocation(UmLocation location) {
		if (txt_addr.getText().length() == 0)
		{
			selectResult.absX = location.absX;
			selectResult.absY = location.absY;
			selectResult.geoX = location.geoX;
			selectResult.geoY = location.geoY;
			selectResult.posDesc = location.address;
			mLocation = location.tencentLocation;
			txt_addr.setText(location.address);
			if(para==null){
				para= new EvtParaForIos();
			}
			para.setAbsX(location.absX);
			para.setAbsY(location.absY);
		}
	}

	/**
	 * 选择地图上的点回调坐标
	 */
	@Override
	public void onCompleted(MapValue value) {
		if(value!=null){
			GeoCoderHolder geoCoder = new GeoCoderHolder(this, txt_addr);
			geoCoder.searchFromLocation(value.absY, value.absX);
			if(para==null){
				para= new EvtParaForIos();
			}
			para.setAbsX(value.absX);
			para.setAbsY(value.absY);
		}
	}
}
