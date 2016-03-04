package topevery.um.com.activity;

import java.util.HashMap;

import media.core.UniversalImageLoader;
import topevery.android.framework.utils.DialogUtils;
import topevery.um.com.base.BaseActivity;
import topevery.um.com.main.MainDialog;
import topevery.um.com.task.SearchHistoryTask;
import topevery.um.com.task.SearchHistoryTask.OnSearchHistoryCallBack;
import topevery.um.com.utils.BitmapUtils;
import topevery.um.com.utils.DisplayUtils;
import topevery.um.jinan.manager.R;
import topevery.um.net.ServiceHandle;
import topevery.um.net.newbean.EvtPara;
import topevery.um.net.newbean.EvtParaForIos;
import topevery.um.net.newbean.EvtRes;
import topevery.um.net.newbean.EvtResList;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ab.util.AbAnimationUtil;
import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
 
public class QuestionSearchNew extends BaseActivity implements OnClickListener,OnItemClickListener,
OnHeaderRefreshListener, OnFooterLoadListener,OnSearchHistoryCallBack
{
	private QuestionSearchAdapter adapter;
	private EvtResList mDatas = new EvtResList();

	private QuestionSearchNew historyCase = this;
	private ProgressDialog pDialog;

	private int currentPosition =  -1;

	private String currrentEvtCode = "";
	
	private Dialog mToast;
	
	private ListView mListView;
	
	private ImageView pBar;
	private AbPullToRefreshView mAbPullToRefreshView = null;
	private int pageIndex = 1;
	private int pageSize = 5;
	private int rowCount = 0;
	
	private EvtPara para = new EvtPara();
	
	private boolean isRefresh = true;

	private static final int REQUESE_CODE_SETTING = 15;
	public static final String NEED_BACK = "needback";
	
	private static int textViewResourceId = R.layout.item_new_report_history;
	
	private DisplayImageOptions imgOptions;
	
	private HashMap<String, Boolean> dateMap = new HashMap<String, Boolean>();
	
	private AlphaAnimation anim ;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_search_new);
		imgOptions = UniversalImageLoader.getDisplayImageOptions(true, false);
		initTitleBar();
		findView();
		setUI();
		refreshTask();
	}

	private void initTitleBar(){
		mAbBottomBar.setVisibility(View.GONE);
		mAbTitleBar.setVisibility(View.VISIBLE);
		mAbTitleBar.setBackgroundResource(R.drawable.main_title_bar_bg);
		View view = LayoutInflater.from(this).inflate(R.layout.titile_bar_normal, null);
		view.findViewById(R.id.btn_left).setOnClickListener(this);
		Button btn_right = (Button) view.findViewById(R.id.btn_right);
		btn_right.setText("刷新");
		btn_right.setOnClickListener(this);
		TextView title = (TextView) view.findViewById(R.id.txt_title);
		title.setText("我的投诉");
		mAbTitleBar.addView(view, 0);
	}
	
	private void findView(){

		pBar = (ImageView) findViewById(R.id.pBar);

		pBar.setBackgroundResource(R.drawable.ic_load);
		pBar.setVisibility(View.GONE);
		// pBar.setBackgroundResource(R.drawable.ic_refresh);

		// 获取ListView对象
		mAbPullToRefreshView = (AbPullToRefreshView) findViewById(R.id.mPullRefreshView);
		mListView = (ListView) findViewById(R.id.mListView);
		mListView.setOnItemClickListener(this);

		// 设置监听器
		mAbPullToRefreshView.setOnHeaderRefreshListener(this);
		mAbPullToRefreshView.setOnFooterLoadListener(this);

		// 设置进度条的样式
		mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(
				getResources().getDrawable(
						R.drawable.progress_circular));
		mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(
				getResources().getDrawable(
						R.drawable.progress_circular));
	}
	
	private void setUI()
	{
		// DatabaseEvtRes.test();
		anim =new AlphaAnimation(0, 255);
		anim.setDuration(40000);
		anim.setInterpolator(new AccelerateInterpolator());
		
		adapter = new QuestionSearchAdapter();
		mListView.setAdapter(adapter);
//		mToast = new Dialog(historyCase, R.style.Dialog_No_Frame);
//		mToast.setContentView(R.layout.toast_question_search);
//		mToast.show();
//		new Thread(){
//			public void run() {
//				try {
//					Thread.sleep(2000);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				mHandler.sendEmptyMessage(0);
//			};
//		}.start();
	}

	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(mToast!=null&&mToast.isShowing()){
				mToast.dismiss();
			}
		};
	};
	
	private void setpDialog()
	{
		if (pDialog == null)
		{
			pDialog = DialogUtils.getDialogLoading(this);
		}
	}

	/** 更多 */
	@Override
	public void onFooterLoad(AbPullToRefreshView view) {
		loadMoreTask();
	}

	/** 加载 */
	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {
		refreshTask();
	}

	public void refreshTask() {
		isRefresh = true;
		pageIndex = 1;
		getData();
	}

	private void loadMoreTask() {
		isRefresh = false;
		if (rowCount > 1) {
			pageIndex++;
			getData();
		} else {
			mAbPullToRefreshView.onFooterLoadFinish();
		}
	}

	private void getData() {
		pBar.setVisibility(View.VISIBLE);

		loadStart();

		para.pageIndex= pageIndex;
		new SearchHistoryTask(this).execute(para);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent arg2) {
		super.onActivityResult(requestCode, resultCode, arg2);
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case REQUESE_CODE_SETTING:
				refreshTask();
				break;

			default:
				break;
			}
		}
	}
	
	private void notifyDataSetChanged() {
		if (!isFinishing()) {
			if (adapter == null) {
				adapter = new QuestionSearchAdapter();
				mListView.setAdapter(adapter);
			} else {
				if (isRefresh) {
					adapter.notifyDataSetInvalidated();
				} else {
					adapter.notifyDataSetChanged();
				}
			}
		}
	}

	private void postNotify() {
		mAbPullToRefreshView.onHeaderRefreshFinish();
		mAbPullToRefreshView.onFooterLoadFinish();
		notifyDataSetChanged();
	}

	/**
	 * 加载结束
	 */
	private void loadStop() {
		// 停止动画
		pBar.postDelayed(new Runnable() {
			@Override
			public void run() {
				pBar.clearAnimation();
			}
		}, 200);
	}

	/**
	 * 加载调用
	 */
	private void loadStart() {
		AbAnimationUtil.playRotateAnimation(pBar, 300, Animation.INFINITE,
				Animation.RESTART);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_left:
			finish();
			break;
		case R.id.btn_right:
			refreshTask();
			break;
		case R.id.layout_history:
		case R.id.btn_evnt_detail:
			int position = (Integer) v.getTag(R.id.btn_evnt_detail);
			EvtPara para = new EvtPara();
//			para.evtCode = adapter.getItem(position);
//			new HistoryCaseAsyTank().execute(para);
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
//		currentPosition = position;
//		currrentEvtCode = adapter.getItem(position).evtCode;
//		EvtPara para = new EvtPara();
//		para.evtCode = adapter.getItem(position).evtCode;
//		new HistoryCaseAsyTank().execute(para);
		
	}
	
	/**
	 * 
	 * 历史列表的adapter
	 */
	class QuestionSearchAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return mDatas.size();
		}

		@Override
		public EvtRes getItem(int position) {
			return mDatas.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ViewHondler hondler = null;
			if(convertView == null)
			{
				hondler = new ViewHondler();
				convertView = LayoutInflater.from(QuestionSearchNew.this).inflate(textViewResourceId, null);
				hondler.txt_time_divider = (TextView) convertView.findViewById(R.id.txt_time_divider);
				
				hondler.img_question = (ImageView) convertView.findViewById(R.id.img_question);
				
				hondler.txt_time_detail = (TextView) convertView.findViewById(R.id.txt_time_detail);
				hondler.txt_accept_code= (TextView) convertView.findViewById(R.id.txt_event_type);
				hondler.btn_evnt_detail = (ImageButton) convertView.findViewById(R.id.btn_evnt_detail);
				hondler.txt_event_code = (TextView) convertView.findViewById(R.id.txt_event_flow);
				hondler.txt_addr = (TextView) convertView.findViewById(R.id.txt_company);
				hondler.txt_state = (TextView) convertView.findViewById(R.id.txt_time_used);
				hondler.txt_des  = (TextView) convertView.findViewById(R.id.txt_des);
				hondler.divider = convertView.findViewById(R.id.view_divide);
				hondler.layout = convertView.findViewById(R.id.layout_history);
				convertView.setTag(hondler);
			}
			else
			{
				hondler = (ViewHondler) convertView.getTag();
			}
//			if(position ==0){
//				hondler.divider.setVisibility(View.VISIBLE);
//			}else{
//				hondler.divider.setVisibility(View.GONE);
//			}
			
//			EvtPara item = getItem(position) ;
//			
//			if (item.attachs != null && item.attachs.size() != 0) {
//				for (int i= 0;i<item.attachs.size();i++) {
//					if(item.attachs.get(i).type == 0){
//						String uri = item.attachs.get(i).uri;
//						UniversalImageLoader.getImageLoader().displayImage(uri, hondler.img_question, imgOptions);
//						break;
//					}
//				}
//			}
//			hondler.txt_time_detail.setText(String.format("时间：%s", item.ReportDate));
//			hondler.txt_accept_code.setText(String.format("受理号：%s", item.evtCode));
//			if(!TextUtils.isEmpty(item.Title)){
//				hondler.txt_event_code.setText(String.format("案件号：%s", item.Title));
//				hondler.txt_event_code.setVisibility(View.VISIBLE);
//			}else{
//				hondler.txt_event_code.setVisibility(View.GONE);
//			}
//			hondler.txt_addr.setText(String.format("地点：%s", item.evtPos));
//			hondler.txt_state.setText(String.format("状态：%s", item.evtResult));
//			hondler.txt_des.setText(String.format("描述：%s", item.evtDesc));
//			hondler.btn_evnt_detail.setTag(R.id.btn_evnt_detail,position);
//			hondler.layout.setTag(R.id.btn_evnt_detail,position);
//			// 该案件是否受理
////			hondler.btn_evnt_detail.setTag(R.id.txt_event_code,!item.evtResult.equals(getString(R.string.event_state_unaccepte)));
////			hondler.layout.setTag(R.id.txt_event_code,!item.evtResult.equals(getString(R.string.event_state_unaccepte)));
//			hondler.btn_evnt_detail.setOnClickListener(QuestionSearchNew.this);
//			hondler.layout.setOnClickListener(QuestionSearchNew.this);
			return convertView;
		}

		private class ViewHondler
		{
			public View divider;								// 如果是第一item,则该view显示
			public TextView txt_time_divider;  		// 时间分割
			public ImageView img_question;  		// 问题照片
			public TextView txt_time_detail;   			// 详细时间
			public TextView txt_accept_code;			// 案件受理号
			public ImageButton btn_evnt_detail;		// 案件详细
			public TextView txt_event_code;			// 案件号
			public TextView txt_des;						// 案件详情
			public TextView txt_addr;						// 地点
			public TextView txt_state;						// 状态
			public View layout;						 		// 整个布局
		}

		class ImageListener implements ImageLoadingListener{

			private ImageView mImageView;
			
			public ImageListener(ImageView img){
				this.mImageView = img;
			}
			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
				
			}

			@Override
			public void onLoadingComplete(String path, View imageView, Bitmap bitmap) {
				bitmap = BitmapUtils.getSolideSizeBitmap(bitmap, 
						DisplayUtils.dip2px(QuestionSearchNew.this, 100), DisplayUtils.dip2px(QuestionSearchNew.this, 120));
				bitmap = BitmapUtils.getRoundCornerBitmap(bitmap, DisplayUtils.dip2px(QuestionSearchNew.this, 3));
				if(mImageView.getDrawingCache()!=bitmap){
					mImageView.startAnimation(anim);
				}
				mImageView.setImageBitmap(bitmap);
//				ImageView img= (ImageView) imageView;
//				img.setImageBitmap(bitmap);
			}

			@Override
			public void onLoadingFailed(String arg0, View imageView, FailReason arg2) {
				BitmapDrawable drawable =(BitmapDrawable) QuestionSearchNew.this.getResources().getDrawable(R.drawable.post_image_loding);
				Bitmap bitmap = drawable.getBitmap();
				bitmap = BitmapUtils.getSolideSizeBitmap(bitmap, 
						DisplayUtils.dip2px(QuestionSearchNew.this, 100), DisplayUtils.dip2px(QuestionSearchNew.this, 120));
				bitmap = BitmapUtils.getRoundCornerBitmap(bitmap, DisplayUtils.dip2px(QuestionSearchNew.this, 3));
				mImageView.setImageBitmap(bitmap);
				
//				ImageView img= (ImageView) imageView;
//				img.setImageBitmap(bitmap);
				
			}

			@Override
			public void onLoadingStarted(String arg0, View imageView) {
				BitmapDrawable drawable =(BitmapDrawable) QuestionSearchNew.this.getResources().getDrawable(R.drawable.post_image_loding);
				Bitmap bitmap = drawable.getBitmap();
				bitmap = BitmapUtils.getSolideSizeBitmap(bitmap, 
						DisplayUtils.dip2px(QuestionSearchNew.this, 100), DisplayUtils.dip2px(QuestionSearchNew.this, 120));
				bitmap = BitmapUtils.getRoundCornerBitmap(bitmap, DisplayUtils.dip2px(QuestionSearchNew.this, 3));
				mImageView.setImageBitmap(bitmap);
				
//				ImageView img= (ImageView) imageView;
//				img.setImageBitmap(bitmap);
			}
			
		}
	}
	
	/**
	 * 获取案件详细信息
	 * 
	 */
	private class HistoryCaseAsyTank extends AsyncTask<EvtParaForIos, Void, EvtRes>
	{

		public HistoryCaseAsyTank()
		{
			setpDialog();
		}

		@Override
		protected EvtRes doInBackground(EvtParaForIos... params)
		{
			EvtRes res = null;
			EvtParaForIos para = params[0];
			try
			{
				res = ServiceHandle.GetHistoryEvt(para);
			}
			catch (Exception e)
			{
				res = new EvtRes();
				res.setSuccess(false);
				res.setErrorMessage(e.getMessage());
			}
			return res;
		}

		@Override
		protected void onPostExecute(EvtRes res)
		{
			super.onPostExecute(res);
			if(historyCase.isFinishing()){
				return;
			}
			if(!historyCase.isFinishing()){
				pDialog.dismiss();
			}
			if (res != null)
			{
				if (res.isSuccess() == true)
				{
//					Intent intent = new Intent(QuestionSearchNew.this,EventDetailActivity.class);
//					intent.putExtra("detail", res);
//					startActivity(intent);
				}
				else
				{
					// MsgBox.show(CodeShow.this, res.errorMessage);
					MainDialog.askYes(QuestionSearchNew.this, res.getErrorMessage());
				}
			}
			else
			{
				if(!historyCase.isFinishing()){
					MainDialog.askYes(QuestionSearchNew.this, "网络故障");
				}
			}
		}

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			pDialog.show();
		}
	}

//	@Override
	public void onSearchFinished(EvtResList result) {
		if(isFinishing()){
			return ;
		}
		if (result!=null&&result.size()>0) {
			rowCount = result.size();
			if (isRefresh) {
				mDatas.clear();
				mDatas = result;
			} else {
				for (int i = 0; i < result.size(); i++) {
					mDatas.add(result.get(i));
				}
			}

		} else{
			
		}

		new Handler().post(new Runnable() {
			@Override
			public void run() {
				postNotify();
			}
		});

		loadStop();
		pBar.setVisibility(View.GONE);
		mListView.setVisibility(View.VISIBLE);
		
	}

}
