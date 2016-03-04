//package topevery.um.com.main;
//
//import topevery.um.com.DisplayUtils;
//import topevery.um.com.casereport.CaseDatabaseTimer;
//import topevery.um.com.service.ServiceUtils;
//import topevery.um.com.service.UpdateTimer;
//import android.app.Activity;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.os.PowerManager;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import topevery.um.jinan.manager.R;
//import global.UpSysHolder;
//
//public class Main_bk extends Activity implements OnItemClickListener
//{
//	PowerManager.WakeLock wakeLock = null;
//	Main_bk wThis = this;
//	UpdateTimer updateTimer = null;
//	CaseDatabaseTimer caseDatabaseTimer = null;
//
//	@Override
//	public void onCreate(Bundle savedInstanceState)
//	{
//		super.onCreate(savedInstanceState);
//		acquireWakeLock();
//		setContentView(R.layout.main2);
//
//		// smsObserver = new SMSObserver(this);
//		updateTimer = new UpdateTimer(this);
//
//		caseDatabaseTimer = new CaseDatabaseTimer();
//		// updateTimer.start();
//		caseDatabaseTimer.start();
//		DisplayUtils.init(this);
//		ServiceUtils.startService(this);
//
//		MainHelper.setGridViewitem(this);
//	}
//
//	private void acquireWakeLock()
//	{
//		if (wakeLock == null)
//		{
//			PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
//			wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, this.getClass().getCanonicalName());
//			wakeLock.acquire();
//		}
//	}
//
//	private void releaseWakeLock()
//	{
//		if (wakeLock != null && wakeLock.isHeld())
//		{
//			wakeLock.release();
//			wakeLock = null;
//		}
//	}
//
//	@Override
//	public void onBackPressed()
//	{
//		MainDialog.show(wThis, "确定退出随手拍吗？", new OnClickListener()
//		{
//			@Override
//			public void onClick(View v)
//			{
//				releaseWakeLock();
//				ServiceUtils.stopService(wThis);
//				finish();
//				android.os.Process.killProcess(android.os.Process.myPid());
//			}
//		});
//	}
//
//	private Intent intent;
//
//	@Override
//	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//	{
//		MainHelper.setOnItemClick(view, intent, this);
//	}
//
//	public void setMsg()
//	{
//		MainDialog.show(wThis, "确定发送举报短信？", new OnClickListener()
//		{
//			@Override
//			public void onClick(View v)
//			{
//				Uri uri = Uri.parse("smsto:106575580211");
//				Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
//				startActivity(intent);
//				setDismiss();
//			}
//		});
//	}
//
//	public void setCall()
//	{
//		MainDialog.show(wThis, "确定拨打 12319 热线电话？", new OnClickListener()
//		{
//			@Override
//			public void onClick(View v)
//			{
//				Intent intent = new Intent();
//				intent.setAction(Intent.ACTION_CALL);
//				intent.setData(Uri.parse("tel:12319"));
//				startActivity(intent);
//				setDismiss();
//			}
//		});
//	}
//
//	public void setWeb()
//	{
//		MainDialog.show(wThis, "确定要访问深圳数字城管网站？", new OnClickListener()
//		{
//			@Override
//			public void onClick(View v)
//			{
//				Uri uri = Uri.parse("http://www.12319.net.cn/");
//				Intent it = new Intent(Intent.ACTION_VIEW, uri);
//				startActivity(it);
//				setDismiss();
//			}
//		});
//	}
//
//	private void setDismiss()
//	{
//		MainDialog.setDismiss();
//	}
//
//	void yangban()
//	{
//		// 调用MsgBox
//		// MsgBox.askYesNo(wThis, "确定要访问深圳数字城管网站吗？", new OnClickListener()
//		// {
//		//
//		// @Override
//		// public void onClick(DialogInterface dialog, int which)
//		// {
//		// Uri uri = Uri.parse("http://www.12319.net.cn/");
//		// Intent it = new Intent(Intent.ACTION_VIEW, uri);
//		// startActivity(it);
//		// }
//		// }, null);
//
//		// 第二种写法
//		android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(this);
//		dialog.setTitle("呼叫12319");
//		dialog.setMessage("确定拨打 12319 热线电话？");
//		dialog.setCancelable(true);
//		dialog.setPositiveButton("是", new DialogInterface.OnClickListener()
//		{
//			@Override
//			public void onClick(DialogInterface dialog, int which)
//			{
//				Intent intent = new Intent();
//				intent.setAction(Intent.ACTION_CALL);
//				intent.setData(Uri.parse("tel:12319"));
//				startActivity(intent);
//			}
//		}).setNegativeButton("否", null);
//		dialog.show();
//
//		// MsgBox.askYesNo(this, "确定退出系统？", new
//		// DialogInterface.OnClickListener()
//		// {
//		// @Override
//		// public void onClick(DialogInterface dialog, int which)
//		// {
//		// releaseWakeLock();
//		// ServiceUtils.stopService(wThis);
//		// // PathUtils.clearPhoto();
//		// android.os.Process.killProcess(android.os.Process.myPid());
//		// }
//		// }, null);
//	}
//
//	public final int id_set = 0;
//	public final int id_up = 1;
//	public final int id_exit = 2;
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu)
//	{
//		menu.addSubMenu(0, id_set, 0, "设置");
//		menu.addSubMenu(0, id_up, 1, "升级");
//		menu.addSubMenu(0, id_exit, 2, "退出");
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item)
//	{
//		switch (item.getItemId())
//		{
//			case id_exit:
//				this.onBackPressed();
//				break;
//			case id_set:
//				Intent intent = new Intent();
//				intent.setClass(this, ClientSet.class);
//				this.startActivity(intent);
//				break;
//			case id_up:
//				new UpSysHolder(this).start(true);
//				break;
//			default:
//				break;
//		}
//		return true;
//	}
// }