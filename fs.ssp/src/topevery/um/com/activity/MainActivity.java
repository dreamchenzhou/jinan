package topevery.um.com.activity;

import global.BaseActivity;
import global.UpSysHolder;
import topevery.um.com.Constants;
import topevery.um.com.casereport.CaseDatabaseTimer;
import topevery.um.com.fragment.ContentFragment;
import topevery.um.com.fragment.MenuFragment;
import topevery.um.com.main.ClientSet;
import topevery.um.com.service.ServiceUtils;
import topevery.um.com.service.UpdateTimer;
import topevery.um.com.utils.DisplayUtils;
import topevery.um.com.utils.LoginUtils;
import topevery.um.com.utils.SpUtils;
import topevery.um.com.view.slidemenu.SlidingMenu;
import topevery.um.jinan.manager.R;
import topevery.um.net.newbean.UserCache;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends BaseActivity implements OnClickListener {

	private SlidingMenu menu;
	private MenuFragment menuFragment;
	private ContentFragment contentFragment;

	PowerManager.WakeLock wakeLock = null;
	UpdateTimer updateTimer = null;
	CaseDatabaseTimer caseDatabaseTimer = null;

	private View btn_set;

	@Override
	public void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		acquireWakeLock();
		initTitleBar();
		setContentView(R.layout.activity_main);
		menuFragment = new MenuFragment();
		contentFragment = new ContentFragment();

		// SlidingMenu的配置 left
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setShadowDrawable(R.drawable.shadow);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

		// SlidingMenu的配置 right
		// menu = new SlidingMenu(this);
		// menu.setMode(SlidingMenu.RIGHT);
		// menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// menu.setShadowWidthRes(R.dimen.shadow_width);
		// menu.setShadowDrawable(R.drawable.shadow_right);
		// menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		// menu.setFadeDegree(0.35f);
		// menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);

		menu.setMenu(R.layout.slide_menu);
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, menuFragment).commit();
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.layout_content, contentFragment).commit();

		// smsObserver = new SMSObserver(this);
		updateTimer = new UpdateTimer(this);

		caseDatabaseTimer = new CaseDatabaseTimer();
		// updateTimer.start();
		caseDatabaseTimer.start();
		DisplayUtils.init(this);
		// 启动定位服务，udp发送数据
		ServiceUtils.startService(this);
	}

	private void initTitleBar() {
		mAbTitleBar.setLogo(R.drawable.button_selector_more);
		mAbTitleBar.getLogoView().setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				menu.toggle();
			}
		});
	}

	private void acquireWakeLock() {
		if (wakeLock == null) {
			PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
			wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, this
					.getClass().getCanonicalName());
			wakeLock.acquire();
		}
	}

	private void releaseWakeLock() {
		if (wakeLock != null && wakeLock.isHeld()) {
			wakeLock.release();
			wakeLock = null;
		}
	}

	private long lastClickTime = 0;

	@Override
	public void onBackPressed() {
		long currentClickTime = System.currentTimeMillis();
		if (currentClickTime - lastClickTime > 2 * 1000) {
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			lastClickTime = currentClickTime;
		} else {
			releaseWakeLock();
			ServiceUtils.stopService(this);
			LoginUtils.loginOut();
			finish();
			android.os.Process.killProcess(android.os.Process.myPid());
		}

	}

	void yangban() {
		// 调用MsgBox
		// MsgBox.askYesNo(wThis, "确定要访问深圳数字城管网站吗？", new OnClickListener()
		// {
		//
		// @Override
		// public void onClick(DialogInterface dialog, int which)
		// {
		// Uri uri = Uri.parse("http://www.12319.net.cn/");
		// Intent it = new Intent(Intent.ACTION_VIEW, uri);
		// startActivity(it);
		// }
		// }, null);

		// 第二种写法
		android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(
				this);
		dialog.setTitle("呼叫12319");
		dialog.setMessage("确定拨打 12319 热线电话？");
		dialog.setCancelable(true);
		dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:12319"));
				startActivity(intent);
			}
		}).setNegativeButton("否", null);
		dialog.show();

		// MsgBox.askYesNo(this, "确定退出系统？", new
		// DialogInterface.OnClickListener()
		// {
		// @Override
		// public void onClick(DialogInterface dialog, int which)
		// {
		// releaseWakeLock();
		// ServiceUtils.stopService(wThis);
		// // PathUtils.clearPhoto();
		// android.os.Process.killProcess(android.os.Process.myPid());
		// }
		// }, null);
	}

	public final int id_set = 0;
	public final int id_up = 1;
	public final int id_exit = 2;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.addSubMenu(0, id_set, 0, "设置");
		menu.addSubMenu(0, id_up, 1, "升级");
		menu.addSubMenu(0, id_exit, 2, "退出");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case id_exit:
			this.onBackPressed();
			break;
		case id_set:
			onSet();
			break;
		case id_up:
			new UpSysHolder(this).start(true);
			break;
		default:
			break;
		}
		return true;
	}

	private void onSet() {
		Intent intent = new Intent();
		intent.setClass(this, ClientSet.class);
		this.startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		if (v == btn_set) {
			onSet();
		}
	}
}
