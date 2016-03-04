package topevery.um.com.casereport.history;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import topevery.android.framework.utils.DialogUtils;
import topevery.um.com.casereport.codeshow.CodeShow;
import topevery.um.com.data.CaseAccept;
import topevery.um.com.data.CaseType;
import topevery.um.com.data.DatabaseAttach;
import topevery.um.com.data.DatabaseEvtRes;
import topevery.um.com.data.DatabaseFlowInfo;
import topevery.um.com.main.MainDialog;
import topevery.um.net.srv.AttachInfo;
import topevery.um.net.srv.AttachInfoCollection;
import topevery.um.net.srv.EvtRes;
import topevery.um.net.srv.EvtResList;
import topevery.um.jinan.manager.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class HistoryCase extends Activity
{
	private ExpandableListView expandableListView;
	private Button btn_clear, btn_refresh;
	private CaseAdapter adapter;
	private EvtResList objects = new EvtResList();

	private HistoryCase historyCase = this;
	private ProgressDialog pDialog;

	private CaseGroupList caseGroupList = new CaseGroupList();

	// String path = PathUtils.getEvtCodeListPath();
	private EvtResList oList1 = new EvtResList();
	private EvtResList oList2 = new EvtResList();
	private EvtResList oList3 = new EvtResList();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.historycase);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
		setUI();
	}

	private void setUI()
	{
		// DatabaseEvtRes.test();
//		objects = DatabaseEvtRes.getValue(CaseType.reportSuccess);
		ButtonOnTuchListen buttonListen = new ButtonOnTuchListen();
		btn_refresh = (Button) findViewById(R.id.btn_refresh);
		btn_refresh.setOnClickListener(buttonListen);
		btn_clear = (Button) findViewById(R.id.btn_clear);
		if (!exeist())
		{
			btn_clear.setBackgroundResource(R.drawable.clear_on);
			btn_clear.setTextColor(Color.GRAY);
		}
		btn_clear.setOnClickListener(buttonListen);
		expandableListView = (ExpandableListView) findViewById(R.id.historycase_listview);
		try
		{
			setDate();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		expandableListView.setOnChildClickListener(new OnChildClickListener()
			{
				@Override
				public boolean onChildClick(ExpandableListView parent, View v,
						int groupPosition, int childPosition, long id)
				{
					showItem(groupPosition, childPosition);
					return true;
				}
			});
	}

	private void showItem(int groupPosition, int childPosition)
	{
		try
		{
			EvtResList evtList = caseGroupList.get(groupPosition);

			EvtRes item = evtList.get(childPosition);
			Bundle bundle = new Bundle();
			bundle.putSerializable("name", (EvtRes) item);
			Intent intent = new Intent();
			intent.putExtras(bundle);
			intent.setClass(HistoryCase.this, CodeShow.class);
			startActivityForResult(intent, 0);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{

		String evtCode = String.valueOf(resultCode);
		if (evtCode != null && resultCode != 0)
		{
			for (EvtRes res : objects)
			{
				if (Integer.parseInt(res.evtCode) == resultCode)
				{
					res.caseAccept = CaseAccept.accepted;
				}
			}
			notifyDataSetChanged();
		}
	}

	private void notifyDataSetChanged()
	{
		if (adapter == null)
		{
			caseGroupList.add(objects);
			adapter = new CaseAdapter(HistoryCase.this, caseGroupList, historyCase);
			expandableListView.setAdapter(adapter);
		}
		else
		{
			adapter.notifyDataSetChanged();
		}
	}

	private class ButtonOnTuchListen implements OnClickListener
	{

		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
				case R.id.btn_refresh:
					groupClear();
//					objects = DatabaseEvtRes.getValue(CaseType.reportSuccess);
					try
					{
						setDate();
					}
					catch (ParseException e)
					{
						e.printStackTrace();
					}
					break;

				case R.id.btn_clear:
					if (exeist())
					{
						setClear();
					}
					break;
			}
		}
	}

	private void setClear()
	{
		MainDialog.show(this, "确定清空记录？", new View.OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					clearEvt();

					if (adapter != null)
					{
						groupClear();
						adapter.notifyDataSetChanged();
					}
					if (!exeist())
					{
						btn_clear.setBackgroundResource(R.drawable.clear_on);
						btn_clear.setTextColor(Color.GRAY);
					}
					MainDialog.setDismiss();
				}
			});
	}

	private void clearEvt()
	{
		for (EvtResList evtItem : caseGroupList)
		{
			for (EvtRes item : evtItem)
			{

				AttachInfoCollection attachs =null;
//				DatabaseAttach.getValue(item.evtCode);
				if (attachs != null && attachs.size() != 0)
				{
					for (AttachInfo attach : attachs)
					{
						attach.delete();
					}
				}
			}
		}
		DatabaseEvtRes.clear();
		DatabaseAttach.clear();
		DatabaseFlowInfo.clear();
	}

	private boolean exeist()
	{
		if (objects.size() != 0)
		{
			return true;
		}
		else
		{
			return false;
		}
		// File file = new File(path);
		// File[] files = file.listFiles();
		// if (files.length == 0)
		// {
		// return false;
		// }
		// else
		// {
		// return true;
		// }
	}

	private void setDate() throws ParseException
	{
		oList1.name = "今天";
		oList2.name = "昨天";
		oList3.name = "更早";

		SimpleDateFormat sdFormat = new SimpleDateFormat("MM-dd-yy_HHmmss");
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy");
		SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
		SimpleDateFormat sdfDay = new SimpleDateFormat("dd");
		SimpleDateFormat sdftime = new SimpleDateFormat("HH:mm");

		String string = sdf.format(new java.util.Date());
		String stringMonth = sdfMonth.format(new java.util.Date());
		String stringDay = sdfDay.format(new java.util.Date());
		Integer integerDay = Integer.parseInt(stringDay);

		caseGroupList.clear();
		if (objects.size() != 0 && objects != null)
		{
			for (EvtRes evtRes : objects)
			{
				String todayDate, monthtime, daytiem, time;
				Date date = sdFormat.parse(evtRes.datetime);

				todayDate = sdf.format(date);
				monthtime = sdfMonth.format(date);
				daytiem = sdfDay.format(date);
				time = sdftime.format(date);
				Integer day = Integer.parseInt(daytiem);

				if (todayDate.equals(string))
				{
					evtRes.datetime = time;
					oList1.add(evtRes);
				}
				else
				{
					if (monthtime.equals(stringMonth))
					{
						if (integerDay - day == 1)
						{
							evtRes.datetime = time;
							oList2.add(evtRes);
						}
						else
						{
							evtRes.datetime = todayDate;
							oList3.add(evtRes);
						}
					}
					else
					{
						evtRes.datetime = todayDate;
						oList3.add(evtRes);
					}
				}
			}
		}
		caseGroupList.add(oList1);
		caseGroupList.add(oList2);
		caseGroupList.add(oList3);

		adapter = new CaseAdapter(HistoryCase.this, caseGroupList, historyCase);
		expandableListView.setAdapter(adapter);
	}

	private void setpDialog()
	{
		if (pDialog == null)
		{
			pDialog = DialogUtils.getDialogLoading(this);
		}
	}

	private void groupClear()
	{
		oList1.clear();
		oList2.clear();
		oList3.clear();
		objects.clear();
	}
}
