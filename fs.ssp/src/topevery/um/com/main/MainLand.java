//package topevery.um.com.main;
//
//import java.util.Timer;
//
//import topevery.um.com.DisplayUtils;
//import topevery.um.com.camera.CameraUtils;
//import topevery.um.com.camera.OnCameraListener;
//import topevery.um.com.casereport.CaseDatabaseTimer;
//import topevery.um.com.casereport.report.Casereport;
//import topevery.um.com.service.UpdateTimer;
//import topevery.um.com.sms.SMSObserver;
//import topevery.um.com.sms.SMSObserver.OnCompletedListener;
//import topevery.um.net.UmUdpService;
//import topevery.um.jinan.manager.R;
//import android.app.Activity;
//import android.content.Intent;
//import android.graphics.BitmapFactory;
//import android.graphics.BitmapFactory.Options;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//public class MainLand extends Activity
//{
//	private Timer time = new Timer();
//	private Button button1, button2;
//	private Intent intent;
//	SMSObserver smsObserver = null;
//	UpdateTimer updateTimer = null;
//	CaseDatabaseTimer caseDatabaseTimer = null;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState)
//	{
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.mainland);
//
//		// 这两个顺序调整一下
//		smsObserver = new SMSObserver(this);
//		// updateTimer = new UpdateTimer(this);
//
//		caseDatabaseTimer = new CaseDatabaseTimer();
//		updateTimer.start();
//		caseDatabaseTimer.start();
//		DisplayUtils.init(this);
//		display();
//		// time.schedule(task, 800);
//		// UmUdpService.initFinal("183.62.104.27", 4008);
//		UmUdpService.initFinal("202.104.124.114", 4008);
//
//		// DatabaseState.test();
//
//		button1 = (Button) findViewById(R.id.mainland_button1);
//		button1.setOnClickListener(new OnClickListener()
//			{
//
//				@Override
//				public void onClick(View v)
//				{
//					intent = new Intent(MainLand.this, Main.class);
//					startActivity(intent);
//					overridePendingTransition(R.anim.fade, R.anim.hold);
//					finish();
//				}
//			});
//		button2 = (Button) findViewById(R.id.mainland_button2);
//		button2.setOnClickListener(new OnClickListener()
//			{
//
//				@Override
//				public void onClick(View v)
//				{
//					CameraUtils.value.camera(MainLand.this, new OnCameraListener()
//						{
//
//							@Override
//							public void onCamera(String fileName)
//							{
//								Bundle bundle = new Bundle();
//								intent = new Intent();
//								bundle.putSerializable("fileName", (String) fileName);
//								intent.putExtras(bundle);
//								intent.setClass(MainLand.this, Casereport.class);
//								startActivity(intent);
//								finish();
//							}
//						});
//				}
//			});
//	}
//
//	public void getLocalNum(OnCompletedListener onCompletedListener)
//	{
//		smsObserver.getNum(onCompletedListener);
//	}
//
//	@Override
//	public void finish()
//	{
//		smsObserver.unregisterContentObserver();
//		super.finish();
//	}
//
//	private void display()
//	{
//		if (DisplayUtils.widthPixels == 320)
//		{
//			int width;
//			int height;
//
//			Options opts = new Options();
//			opts.inJustDecodeBounds = true;
//			BitmapFactory.decodeResource(this.getResources(), R.drawable.mainland, opts);
//			width = (int) (opts.outWidth / 1.5);
//			height = (int) (opts.outHeight / 1.5);
//
//			TextView textView = (TextView) findViewById(R.id.textView);
//
//			LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textView.getLayoutParams();
//			params.width = width;
//			params.height = height;
//
//			textView.setLayoutParams(params);
//		}
//	}
//
//	// TimerTask task = new TimerTask()
//	// {
//	//
//	// @Override
//	// public void run()
//	// {
//	// Intent intent = new Intent(MainLand.this, Main.class);
//	// // MainLand.this.startService(MainLand.this.intent);
//	// startActivity(intent);
//	// finish();
//	// }
//	// };
// }
