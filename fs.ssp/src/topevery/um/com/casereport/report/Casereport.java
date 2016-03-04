package topevery.um.com.casereport.report;

import java.io.File;

import ro.upload.UploadHandle;
import topevery.android.framework.map.MapHandleType;
import topevery.android.framework.map.MapValue;
import topevery.android.framework.map.OnCompletedListener;
import topevery.android.framework.utils.TextUtils;
import topevery.android.framework.zoom.ZoomHolder;
import topevery.um.com.Settings;
import topevery.um.com.camera.CameraUtils;
import topevery.um.com.camera.OnCameraListener;
import topevery.um.com.casereport.uritl.AttachComparator;
import topevery.um.com.casereport.uritl.MyListView;
import topevery.um.com.data.CaseAccept;
import topevery.um.com.data.CaseType;
import topevery.um.com.data.DatabaseAttach;
import topevery.um.com.data.DatabaseEvtRes;
import topevery.um.com.data.DatabaseLocalNum;
import topevery.um.com.data.Serializer;
import topevery.um.com.main.Main;
import topevery.um.com.main.MainDialog;
import topevery.um.com.utils.PathUtils;
import topevery.um.jinan.manager.R;
import topevery.um.map.MapManager;
import topevery.um.map.ReceiveUmLocationListener;
import topevery.um.map.UmLocation;
import topevery.um.map.UmLocationClient;
import topevery.um.net.srv.AttachInfo;
import topevery.um.net.srv.AttachInfoCollection;
import topevery.um.net.srv.EvtPara;
import topevery.um.net.srv.EvtRes;
import topevery.um.net.srv.ServiceHandle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

public class Casereport extends Activity implements ReceiveUmLocationListener
{
	public static int menu;

	private Button btn_menu, btn_report;
	EditText edit_linkman, edt_linkPhone, evtDesc;

	private TextView btnMap;
	private EditText evtPos;
	private ProgressDialog pDialog;
	private MyListView listView;
	String path = PathUtils.getTempPath();
	String path2 = PathUtils.getTemp();
	public AttachInfoCollection attachs = new AttachInfoCollection();

	private EvtPara para = new EvtPara();
	private AttachInfoAdapter2 adapter;
	Casereport wThis = this;
	boolean isSuccess = false;
	EvtPara para2 = new EvtPara();

	boolean itemClicked = false;
	ListView listViewAuto;
	PopupWindow autoPosPopu;

	private Bundle bundle;

	// private UmLocation mUmLocation = null;

	private MapValue selectResult = new MapValue();

	private String[] string = new String[]
	{ "相机拍摄", "手机相册" };

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		UmLocationClient.addLocationListener(this);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.casereport);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.case_title);
		// ClientTitleHelper.setTitle(wThis, "案件上报");
		setUI();
		if (TextUtils.isEmpty(para2.evtPos) && TextUtils.isEmpty(para2.evtDesc) && para2.attachs.size() == 0)
		{
			setHaveOld();
		}
		else
		{
			setTempValue(para2);
		}
	}

	@Override
	public void onReceiveUmLocation(UmLocation location)
	{
		if (evtPos.getText().length() == 0)
		{
			selectResult.absX = location.absX;
			selectResult.absY = location.absY;
			selectResult.geoX = location.geoX;
			selectResult.geoY = location.geoY;
			selectResult.posDesc = location.address;
			evtPos.setText(location.address);
		}
	}

	private void setTempValue(EvtPara para2)
	{
		edt_linkPhone.setText(para2.linkPhone);
		evtPos.setText(para2.evtPos);
		evtDesc.setText(para2.evtDesc);
		if (!para2.attachs.isEmpty)
		{
			attachs = para2.attachs;
			notifyDataSetChanged();
		}

		if (para2.absX > 0 && para2.absY > 0)
		{
			selectResult.absX = para2.absX;
			selectResult.absY = para2.absY;
			selectResult.geoX = para2.geoX;
			selectResult.geoY = para2.geoY;
			selectResult.posDesc = para2.evtPos;
		}

		para2.attachs.attachInfo.isChecked = false;
	}

	private void setHaveOld()
	{
		if (exeist())
		{
			MainDialog.show(wThis, "发现草稿，要打开草稿箱？", new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					Intent intent = new Intent();
					intent.setClass(Casereport.this, CaseTemp.class);
					startActivity(intent);
					setDialogDismiss();
				}
			});
		}
	}

	private boolean exeist()
	{
		File file = new File(path2);
		File[] files = file.listFiles();
		if (files == null || files.length == 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	@Override
	protected void onDestroy()
	{
		UmLocationClient.removeLocationListener(this);
		deleteAttachs();
		super.onDestroy();
	}

	void deleteAttachs()
	{
		try
		{
			if (!isSuccess && !attachs.attachInfo.isSave)
			{
				for (AttachInfo attachInfo : attachs)
				{
					attachInfo.delete();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void setUI()
	{
		ButtonTouchListener buttonTouchListener = new ButtonTouchListener();

		evtPos = (EditText) findViewById(R.id.evtPos);
		evtDesc = (EditText) findViewById(R.id.evtDesc);
		edt_linkPhone = (EditText) findViewById(R.id.edt_linkPhone);

		btnMap = (TextView) findViewById(R.id.btnMap);

		listView = (MyListView) findViewById(R.id.attachinfo_listview);

		btn_menu = (Button) findViewById(R.id.btn_menu);
		btn_menu.setOnClickListener(buttonTouchListener);

		btn_report = (Button) findViewById(R.id.btn_report);
		btn_report.setOnClickListener(buttonTouchListener);

		evtDesc.setText("垃圾暴露");

		listView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				AttachInfo item = attachs.get(position);
				ZoomHolder.value.show(wThis, item.uri);
			}
		});

		btnMap.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (selectResult == null)
				{
					selectResult = new MapValue();
				}
				selectResult.mapHandleType = MapHandleType.position;
				MapManager.show(wThis, selectResult, new OnCompletedListener()
				{
					@Override
					public void onCompleted(MapValue mapValue)
					{
						selectResult.absX = mapValue.absX;
						selectResult.absY = mapValue.absY;
						selectResult.geoX = mapValue.geoX;
						selectResult.geoY = mapValue.geoY;

						UmLocationClient.getAddr(mapValue, evtPos);
					}
				});
			}
		});

		// edt_linkPhone.setText(DatabaseLocalNum.getLocalNum());
		edt_linkPhone.setText(Settings.getInstance().TelNum);

		try
		{
			bundle = this.getIntent().getExtras();

			if (bundle.getSerializable("temp") != null)
			{
				para2 = (EvtPara) bundle.getSerializable("temp");
			}
			if (bundle.getSerializable("fileName") != null)
			{
				String fileName = (String) bundle.getSerializable("fileName");
				AttachInfo aInfo = new AttachInfo();
				aInfo.uri = fileName;
				para2.attachs.add(aInfo);
				para2.attachs.isEmpty = false;
				para2.linkPhone = DatabaseLocalNum.getLocalNum();

				if (para.absX > 0 && para.absY > 0)
				{
					selectResult.absX = para.absX;
					selectResult.absY = para.absY;
					selectResult.geoX = para.geoX;
					selectResult.geoY = para.geoY;
					selectResult.posDesc = para.evtPos;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void btnReport()
	{
		para.linkPhone = edt_linkPhone.getText().toString();
		para.evtDesc = evtDesc.getText().toString();
		para.evtPos = evtPos.getText().toString();
		para.attachs = attachs;
		setEvtInfo(para);
		new ReportAsyncTask().execute(para);
	}

	void setEvtInfo(EvtPara para)
	{
		if (selectResult.absX > 0)
		{
			para.absX = selectResult.absX;
			para.absY = selectResult.absY;
			para.geoX = selectResult.geoX;
			para.geoY = selectResult.geoY;
		}
	}

	MenuDialog menuDialog;

	private class ButtonTouchListener implements android.view.View.OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			switch (v.getId())
			{
				case R.id.btn_menu:
					menuDialog = new MenuDialog(wThis, string, new OnItemClickListener()
					{
						@Override
						public void onItemClick(AdapterView<?> parent, View view, int position, long id)
						{
							switch (position)
							{
								case 0:
									setCamera();
									menuDialog.dismiss();
									break;
								case 1:
									setLocalCamera();
									menuDialog.dismiss();
									break;
							}
						}
					}, "添加附件");
					menuDialog.show();
					break;
				case R.id.btn_report:
					report();
					break;
			}
		}
	}

	protected void report()
	{
		if (TextUtils.isEmpty(evtDesc))
		{
			MainDialog.askYes(wThis, "问题描述不能为空");
			return;
		}
		if (TextUtils.isEmpty(evtPos))
		{
			MainDialog.askYes(wThis, "案发地址不能为空");
			return;
		}
		// if (edt_linkPhone.length() > 11 || edt_linkPhone.length() < 11)
		// {
		// Toast.makeText(wThis, "您输入的联系方式不正确", Toast.LENGTH_SHORT).show();
		// return;
		// }

		MainDialog.show(wThis, "确定上报案件吗?", new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				btnReport();
				setDialogDismiss();
			}
		});
	}

	private void setDialog()
	{
		if (pDialog == null)
		{
			pDialog = new ProgressDialog(this);
			pDialog.setMessage("上报中，请稍等... ...");
			pDialog.setCancelable(false);
		}
	}

	private class ReportAsyncTask extends AsyncTask<EvtPara, Void, EvtRes>
	{
		public ReportAsyncTask()
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
				boolean handleValue = UploadHandle.UploadCore(para.attachs);
				if (handleValue)
				{
					res = ServiceHandle.ReportEvtInfo(para);
				}
				else
				{
					res = new EvtRes();
					res.isSuccess = false;
					res.errorMessage = "图片上传失败,请重试";
					res.caseType = CaseType.reportFail;
					res.caseAccept = CaseAccept.unaccepted;
				}
				if (res != null && res.isSuccess)
				{
					isSuccess = true;
					res.caseType = CaseType.reportSuccess;
					res.caseAccept = CaseAccept.unaccepted;
					res.evtPara = para;
//					DatabaseEvtRes.insert(res);
				}
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
		protected void onPostExecute(final EvtRes result)
		{
			pDialog.dismiss();
			if (result != null)
			{
				if (result.isSuccess)
				{
					String text = String.format("您的举报已经受理，受理编号为%s", result.evtCode);
					// String text = result.evtCode + "上报成功";
//					DatabaseAttach.insert(result.evtCode, result.evtPara.attachs);
					MainDialog.askYes(wThis, text, new View.OnClickListener()
					{

						@Override
						public void onClick(View v)
						{
							for (AttachInfo item : temp)
							{
								item.delete();
							}
							try
							{
								if (bundle.getSerializable("fileName") != null)
								{
									Intent intent = new Intent(Casereport.this, Main.class);

									startActivity(intent);
								}
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
							setDialogDismiss();
							finish();
						}
					});
				}
				else
				{
					MainDialog.askYes(wThis, result.errorMessage);
				}
			}
			else
			{
				MainDialog.askYes(wThis, "网络故障");
			}
		}

		@Override
		protected void onPreExecute()
		{
			pDialog.show();
		}
	}

	// public void setData(EvtRes result)
	// {

	// try
	// {
	// String path = PathUtils.getEvtCodePath(result.evtCode);
	// Serializer.writeObject(result, path);
	// }
	// catch (Exception e)
	// {
	// e.printStackTrace();
	// }
	// }

	public void setCamera()
	{
		CameraUtils.value.camera(Casereport.this, new OnCameraListener()
		{
			@Override
			public void onCamera(String fileName)
			{
				AttachInfo attachInfo = new AttachInfo();
				attachInfo.uri = fileName;
				attachs.add(attachInfo);
				attachs.isEmpty = false;
				notifyDataSetChanged();
			}
		});
	}

	public void setLocalCamera()
	{
		CameraUtils.value.localcamera(Casereport.this, new OnCameraListener()
		{

			@Override
			public void onCamera(String fileName)
			{
				AttachInfo attachInfo = new AttachInfo();
				attachInfo.uri = fileName;
				attachs.add(attachInfo);
				attachs.isEmpty = false;
				notifyDataSetChanged();
			}
		});

	}

	public void notifyDataSetChanged()
	{
		if (adapter == null)
		{
			adapter = new AttachInfoAdapter2(wThis, attachs);
			adapter.sort(new AttachComparator());
			listView.setAdapter(adapter);
		}
		else
		{
			adapter.sort(new AttachComparator());
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onBackPressed()
	{
		if (TextUtils.isEmpty(evtPos) && TextUtils.isEmpty(evtDesc) && attachs.size() == 0)
		{
			for (AttachInfo item : temp)
			{
				item.delete();
			}
			finish();
		}
		if (!TextUtils.isEmpty(evtPos) || !TextUtils.isEmpty(evtDesc) || attachs.size() != 0)
		{
			MainDialog.show(wThis, "案件尚未上报，是否保存？", new View.OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					save();
				}
			}, new View.OnClickListener()
			{

				@Override
				public void onClick(View v)
				{

					for (AttachInfo item : temp)
					{
						item.delete();
					}
					if (para2.attachs.size() != 0)
					{
						for (AttachInfo attachInfo : para2.attachs)
						{
							attachInfo.delete();
						}
					}
					if (para2.attachs.size() == 0)
					{
						for (AttachInfo attachInfo : attachs)
						{
							attachInfo.delete();
						}
					}
					if (para2.file != null)
					{
						Intent intent = new Intent(Casereport.this, CaseTemp.class);
						startActivity(intent);
						finish();
					}
					setDialogDismiss();
					finish();
				}
			});
		}
		else
		{
			finish();
		}
	}

	private void save()
	{
		try
		{
			para.linkPhone = edt_linkPhone.getText().toString();
			para.evtDesc = evtDesc.getText().toString();
			para.evtPos = evtPos.getText().toString();
			para.attachs = attachs;
			setEvtInfo(para);

			for (AttachInfo item : temp)
			{
				item.delete();
			}

			if (!attachs.isEmpty)
			{
				attachs.attachInfo.isSave = true;
				para.attachs = attachs;
			}

			Serializer.writeObject(para, path);
			if (para2.file != null)
			{
				Intent intent = new Intent(Casereport.this, CaseTemp.class);
				startActivity(intent);
				finish();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		setDialogDismiss();
		finish();
	}

	private AttachInfoCollection temp = new AttachInfoCollection();

	public void addTemp(AttachInfo aInfo2)
	{
		attachs.attachInfo = aInfo2;
		temp.add(aInfo2);
	}

	private void setDialogDismiss()
	{
		MainDialog.setDismiss();
	}
}
