package topevery.um.com.casereport.history;

import java.util.ArrayList;

import topevery.android.core.MsgBox;
import topevery.android.framework.utils.DialogUtils;
import topevery.android.framework.utils.TextUtils;
import topevery.um.com.casereport.codeshow.CodeShowListAdapter;
import topevery.um.com.casereport.uritl.KeyValue;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class CheckCase extends Activity
{
	// private static CheckCase checkCase;
	private EditText edt_check;
	private Button btn_check;
	private ListView listView;

	private ProgressDialog pDialog;
	private boolean isDebug = true;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.checkcase);
		setUI();
	}

	private void setUI()
	{
		listView = (ListView) findViewById(R.id.list_checkcase);

		edt_check = (EditText) findViewById(R.id.edt_check);
		btn_check = (Button) findViewById(R.id.btn_check);
		btn_check.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					if (!TextUtils.isEmpty(edt_check.getText()))
					{
						setQuery();
					}
					else
					{
						MsgBox.show(CheckCase.this, "请输入受理号");
					}
				}
			});
	}

	private void setQuery()
	{
		EvtPara para = new EvtPara();
		para.evtCode = edt_check.getText().toString();
		new CheckCaseAsyTank().execute(para);
	}

	private void setDialog()
	{
		if (pDialog == null)
		{
			pDialog = DialogUtils.getDialogLoading(this);
		}
	}

	private class CheckCaseAsyTank extends AsyncTask<EvtPara, Void, EvtRes>
	{

		private CodeShowListAdapter adapter;

		public CheckCaseAsyTank()
		{
			setDialog();
		}

		@Override
		protected EvtRes doInBackground(EvtPara... params)
		{
			EvtRes res = null;
			EvtPara para = params[0];
			try
			{
				// if (isDebug)
				// {
				// res = new EvtRes();
				// res.evtCode = UUID.randomUUID().toString();
				// res.evtPara = new EvtPara();
				// res.evtPara.evtDesc = "evtDesc";
				// }
				// else
				// {
				res = ServiceHandle.GetEvtInfo(para);
				// }
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
		protected void onPostExecute(EvtRes result)
		{
			super.onPostExecute(result);
			pDialog.dismiss();
			if (result != null)
			{
				if (result.isSuccess == true)
				{
					FlowInfoCollection flowitem = result.flowInfos;
					if (flowitem.size() == 0)
					{
						MsgBox.show(CheckCase.this, "案件正在受理中");
					}
					else
					{
						ArrayList<KeyValue> keyValues = new ArrayList<KeyValue>();

						keyValues.add(new KeyValue("受理号：", result.evtCode));
						// keyValues.add(new KeyValue("环节名：", flowitem));
						// keyValues.add(new KeyValue("联系电话：",
						// flowitem.operatorName));
						// keyValues.add(new KeyValue("案发地址：",
						// flowitem.evtPos));
						//
						// keyValues.add(new KeyValue("案发描述：",
						// flowitem.evtDesc));

						adapter = new CodeShowListAdapter(CheckCase.this, keyValues);
						listView.setAdapter(adapter);
					}

				}
				else
				{
					MsgBox.show(CheckCase.this, result.errorMessage);
				}
			}
			else
			{
				MsgBox.show(CheckCase.this, "网络故障");
			}
		}

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			pDialog.show();
		}
	}
}
