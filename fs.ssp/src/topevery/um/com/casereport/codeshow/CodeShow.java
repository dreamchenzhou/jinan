package topevery.um.com.casereport.codeshow;

import java.util.ArrayList;

import topevery.android.framework.utils.DialogUtils;
import topevery.android.framework.utils.TextUtils;
import topevery.um.com.casereport.uritl.KeyValue;
import topevery.um.com.casereport.uritl.MyGridView;
import topevery.um.com.casereport.uritl.MyListView;
import topevery.um.com.data.CaseAccept;
import topevery.um.com.data.DatabaseAttach;
import topevery.um.com.data.DatabaseEvtRes;
import topevery.um.com.data.DatabaseFlowInfo;
import topevery.um.com.main.MainDialog;
import topevery.um.net.newbean.UserCache;
import topevery.um.net.srv.AttachInfoCollection;
import topevery.um.net.srv.EvtPara;
import topevery.um.net.srv.EvtRes;
import topevery.um.net.srv.FlowInfoCollection;
import topevery.um.net.srv.ServiceHandle;
import topevery.um.jinan.manager.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class CodeShow extends Activity
{
	private MyListView listView, listView2;
	private MyGridView gridView;
	private Button btnButton;
	private CodeShowGridAdapter adapter2;
	private CodeShowListAdapter adapter;
	private ProgressDialog pDialog;
	private LinearLayout layout, layout2;
	private HorizontalScrollView hScrollView;
	AttachInfoCollection objects2 = new AttachInfoCollection();

	boolean accepted = false;
	EvtRes item;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.codeshow);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
		setUI();
	}

	private void setUI()
	{
		listView = (MyListView) findViewById(R.id.list_codeshow);
		listView2 = (MyListView) findViewById(R.id.list2_codeshow);
		gridView = (MyGridView) findViewById(R.id.grid_codeshow);

		layout = (LinearLayout) findViewById(R.id.codeshow_linearlayout);
		layout2 = (LinearLayout) findViewById(R.id.codeshow_linearlayout2);
		hScrollView = (HorizontalScrollView) findViewById(R.id.codeshow_horizontalscrollview);

		btnButton = (Button) findViewById(R.id.btn_return);
		btnButton.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{

				finish();
			}
		});

		Bundle bundle = this.getIntent().getExtras();
		item = (EvtRes) bundle.getSerializable("name");

		FlowInfoCollection fCollection = new FlowInfoCollection();
		fCollection = DatabaseFlowInfo.getValue(item.evtCode);
//		item.evtPara.attachs = DatabaseAttach.getValue(item.evtCode);
		// if (fCollection.size() == 0)
		// {
		setQueryListView(item.evtCode);
		// }
		// else
		// {
		// EvtRes iRes = new EvtRes();
		// iRes = DatabaseEvtRes.getValue(item.evtCode);
		// item.flowInfos = fCollection;
		// item.evtPara.evtResult = iRes.evtPara.evtResult;
		// setItem(item);
		// }

	}

	private void setQueryGridView(EvtRes evt)
	{
		objects2.add(evt.evtPara.attachs);
		adapter2 = new CodeShowGridAdapter(CodeShow.this, objects2);
		gridView.setAdapter(adapter2);
	}

	void setQueryListView(String string)
	{
		EvtPara para = new EvtPara();
		para.evtCode = string;
		new HistoryCaseAsyTank().execute(para);
	}

	private void setpDialog()
	{
		if(pDialog == null)
		{
			pDialog = DialogUtils.getDialogLoading(this);
		}
	}

	private class HistoryCaseAsyTank extends AsyncTask<EvtPara, Void, EvtRes>
	{

		public HistoryCaseAsyTank()
		{
			setpDialog();
		}

		@Override
		protected EvtRes doInBackground(EvtPara... params)
		{
			EvtRes res = null;
			EvtPara para = params[0];
			try
			{
				res = ServiceHandle.GetEvtInfo(para);
			}
			catch (Exception e)
			{
				res = new EvtRes();
				res.isSuccess = false;
				res.errorMessage = e.toString();
			}
			return res;
		}

		@Override
		protected void onPostExecute(EvtRes res)
		{
			super.onPostExecute(res);
			pDialog.dismiss();
			if(res != null)
			{
				if(res.isSuccess == true)
				{
					if(res.flowInfos.size() != 0)
					{
						res.evtCode = item.evtCode;
						DatabaseEvtRes.update(res.evtCode, CaseAccept.accepted, res.evtPara.evtResult,UserCache.getInstance().getUserId());
						DatabaseFlowInfo.insert(item.evtCode, res.flowInfos);
						accepted = true;
					}
					else
					{
						res.evtCode = item.evtCode;
						DatabaseEvtRes.update(res.evtCode, CaseAccept.unaccepted, res.evtPara.evtResult,UserCache.getInstance().getUserId());
					}
					setItem(res);
				}
				else
				{
					// MsgBox.show(CodeShow.this, res.errorMessage);
					MainDialog.askYes(CodeShow.this, res.errorMessage);
				}
			}
			else
			{
				MainDialog.askYes(CodeShow.this, "网络故障");
				// MsgBox.show(CodeShow.this, "网络故障");
			}
		}

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			pDialog.show();
		}
	}

	@Override
	public void finish()
	{
		if(accepted)
		{
			setResult(Integer.parseInt(item.evtCode));
		}
		super.finish();
	}

	private void setItem(EvtRes res)
	{
		ArrayList<KeyValue> keyValues = new ArrayList<KeyValue>();

		keyValues.add(new KeyValue("受理号", item.evtCode));

		if(!TextUtils.isEmpty(item.evtPara.linkman))
		{
			keyValues.add(new KeyValue("联系人", item.evtPara.linkman));
		}
		if(!TextUtils.isEmpty(item.evtPara.linkPhone))
		{
			keyValues.add(new KeyValue("联系电话", item.evtPara.linkPhone));
		}

		if(!TextUtils.isEmpty(item.evtPara.evtPos))
		{
			keyValues.add(new KeyValue("案发地址", item.evtPara.evtPos));
		}

		if(!TextUtils.isEmpty(item.evtPara.evtDesc))
		{
			keyValues.add(new KeyValue("案件描述", item.evtPara.evtDesc));
		}

		keyValues.add(new KeyValue("上报状态", res.evtPara.evtResult));

		adapter = new CodeShowListAdapter(CodeShow.this, keyValues);
		listView.setAdapter(adapter);

		if(item.evtPara.attachs != null && item.evtPara.attachs.size() != 0)
		{
			setQueryGridView(item);
		}
		if(res.flowInfos.size() != 0)
		{
			layout.setVisibility(View.VISIBLE);
			hScrollView.setVisibility(View.VISIBLE);
			CodeShowList2Adapter adapter3 = new CodeShowList2Adapter(CodeShow.this, res.flowInfos);
			listView2.setAdapter(adapter3);
		}
		layout2.setVisibility(View.VISIBLE);
		// setTimerSchedule();
	}
	// private void setTimerSchedule()
	// {
	// StaticHelper.setTimerSchedule(new ThreadTaskRunnable()
	// {
	// @Override
	// public void run(ThreadTask obj)
	// {
	// setHeightHandler.sendMessage(new Message());
	// }
	// }, 1 * 1000);
	// }
	//
	// private Handler setHeightHandler = new Handler()
	// {
	// @Override
	// public void handleMessage(Message msg)
	// {
	// setHeigth();
	// }
	// };
	//
	// void setHeigth()
	// {
	// int lCount = listView.getAdapter().getCount();
	// int height = listView.getHeight();
	// height = lCount * height;
	// invalidatelLstView(height);
	//
	// if (lCount != listView.getChildCount())
	// {
	// setHeightHandler.sendMessage(new Message());
	// }
	// else
	// {
	// int lHeight = 0;
	// for (int i = 0; i < lCount; i++)
	// {
	// View view = listView.getChildAt(i);
	// if (view != null)
	// {
	// lHeight += view.getHeight();
	// }
	// }
	//
	// lHeight += (lCount) * listView.getDividerHeight() * 10;
	// invalidatelLstView(lHeight);
	// }
	//
	// setGrid();
	// }
	//
	// private void invalidatelLstView(int height)
	// {
	// ViewGroup.LayoutParams params = listView.getLayoutParams();
	// params.height = height;
	// listView.setLayoutParams(params);
	// listView.invalidate();
	// }
	//
	// boolean setGrid = false;
	//
	// private void setGrid()
	// {
	// int count = gridView.getChildCount();
	// if (count > 0 && !setGrid)
	// {
	// setGrid = true;
	// int gHeight = gridView.getHeight();
	//
	// count = (count + count % 2) / 2;
	// gHeight = gHeight * count;
	// ViewGroup.LayoutParams params2 = gridView.getLayoutParams();
	// params2.height = gHeight;
	// gridView.setLayoutParams(params2);
	// }
	// }
}
