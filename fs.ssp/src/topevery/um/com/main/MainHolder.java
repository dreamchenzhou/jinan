//package topevery.um.com.main;
//
//import java.util.ArrayList;
//
//import topevery.android.core.MsgBox;
//import topevery.um.jinan.manager.R;
//import topevery.um.com.UpSysDialog;
//import topevery.um.com.casereport.history.TabHostMain;
//import topevery.um.com.casereport.report.Casereport;
//import android.app.AlertDialog.Builder;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.BitmapFactory;
//import android.graphics.BitmapFactory.Options;
//import android.net.Uri;
//import android.util.DisplayMetrics;
//import android.view.Gravity;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//
//public class MainHolder
//{
//	Main cMain;
//	ImageView btnQuery;
//	ImageView btnReport;
//	ImageView btnMsg;
//	ImageView btnPhone;
//	ImageView btnHistory;
//	Builder pdDialog;
//	Intent intent = null;
//
//	ArrayList<View> viewList = new ArrayList<View>();
//
//	FrameLayout frameLayout;
//	DisplayMetrics dm = new DisplayMetrics();
//	int widthPixels;
//	int heightPixels;
//	Options opts = null;
//
//	int heightBody = 0;
//	FrameLayout.LayoutParams paramQuery;
//	FrameLayout.LayoutParams paramReport;
//	FrameLayout.LayoutParams paramPhone;
//	FrameLayout.LayoutParams paramMsg;
//	FrameLayout.LayoutParams paramHistory;
//
//	public MainHolder(Main cMain)
//	{
//		this.cMain = cMain;
//		opts = new Options();
//		opts.inJustDecodeBounds = true;
//		initLayout();
//	}
//
//	void initLayout()
//	{
//		cMain.getWindowManager().getDefaultDisplay().getMetrics(dm);
//		widthPixels = dm.widthPixels;
//		heightPixels = dm.heightPixels;
//
//		frameLayout = (FrameLayout) cMain.findViewById(R.id.layoutBody);
//
//		btnQuery = new ImageView(cMain);
//		btnReport = new ImageView(cMain);
//		btnMsg = new ImageView(cMain);
//		btnPhone = new ImageView(cMain);
//		btnHistory = new ImageView(cMain);
//
//		viewList.add(btnQuery);
//		viewList.add(btnReport);
//		viewList.add(btnMsg);
//		viewList.add(btnPhone);
//		viewList.add(btnHistory);
//
//		for (View view : viewList)
//		{
//			view.setOnClickListener(onClickListener);
//		}
//
//		btnQuery.setImageResource(R.xml.menu_history_query);
//		btnQuery.setId(R.xml.menu_history_query);
//
//		btnReport.setImageResource(R.xml.menu_report);
//		btnReport.setId(R.xml.menu_report);
//
//		btnMsg.setImageResource(R.xml.menu_msg);
//		btnMsg.setId(R.xml.menu_msg);
//
//		btnPhone.setImageResource(R.xml.menu_call);
//		btnPhone.setId(R.xml.menu_call);
//
//		btnHistory.setImageResource(R.xml.menu_aboat);
//		btnHistory.setId(R.xml.menu_aboat);
//
//		switch (widthPixels)
//		{
//			case 320:
//				initLayout320();
//				break;
//			case 480:
//				initLayout480();
//				break;
//			case 540:
//				initLayout540();
//				break;
//			default:
//				initLayout800();
//				break;
//		}
//
//		frameLayout.addView(btnQuery, paramQuery);
//		frameLayout.addView(btnReport, paramReport);
//		frameLayout.addView(btnPhone, paramPhone);
//		frameLayout.addView(btnMsg, paramMsg);
//		frameLayout.addView(btnHistory, paramHistory);
//	}
//
//	void initLayout320()
//	{
//		BitmapFactory.decodeResource(cMain.getResources(), R.drawable.menu_report, opts);
//		opts = getOptions320(opts);
//		paramReport = new FrameLayout.LayoutParams(opts.outWidth, opts.outHeight);
//		heightBody = opts.outHeight;
//		paramReport.gravity = Gravity.CENTER | Gravity.TOP;
//		paramReport.topMargin = heightBody / 2 + heightBody / 12;
//
//		BitmapFactory.decodeResource(cMain.getResources(), R.drawable.menu_history_query, opts);
//		opts = getOptions320(opts);
//		paramQuery = new FrameLayout.LayoutParams(opts.outWidth, opts.outHeight);
//		paramQuery.gravity = Gravity.LEFT | Gravity.TOP;
//		paramQuery.topMargin = heightBody / 2 + opts.outHeight;
//		paramQuery.leftMargin = opts.outWidth / 12;
//
//		BitmapFactory.decodeResource(cMain.getResources(), R.drawable.menu_phone, opts);
//		opts = getOptions320(opts);
//		paramPhone = new FrameLayout.LayoutParams(opts.outWidth, opts.outHeight);
//		paramPhone.gravity = Gravity.LEFT | Gravity.TOP;
//		paramPhone.topMargin = heightBody + opts.outHeight / 4 + opts.outHeight / 8;
//		paramPhone.leftMargin = opts.outWidth / 4 + opts.outWidth / 10 + opts.outWidth / 12;
//
//		BitmapFactory.decodeResource(cMain.getResources(), R.drawable.menu_msg, opts);
//		opts = getOptions320(opts);
//		paramMsg = new FrameLayout.LayoutParams(opts.outWidth, opts.outHeight);
//		paramMsg.gravity = Gravity.RIGHT | Gravity.TOP;
//		paramMsg.topMargin = heightBody + opts.outHeight / 16 + opts.outHeight / 16;
//		paramMsg.rightMargin = opts.outWidth / 6;
//
//		BitmapFactory.decodeResource(cMain.getResources(), R.drawable.menu_aboat, opts);
//		opts = getOptions320(opts);
//		paramHistory = new FrameLayout.LayoutParams(opts.outWidth, opts.outHeight);
//		paramHistory.gravity = Gravity.BOTTOM | Gravity.RIGHT;
//	}
//
//	void initLayout480()
//	{
//		BitmapFactory.decodeResource(cMain.getResources(), R.drawable.menu_report, opts);
//		paramReport = new FrameLayout.LayoutParams(opts.outWidth, opts.outHeight);
//		heightBody = opts.outHeight;
//		paramReport.gravity = Gravity.CENTER | Gravity.TOP;
//		paramReport.topMargin = heightBody / 2 + heightBody / 4;
//
//		BitmapFactory.decodeResource(cMain.getResources(), R.drawable.menu_history_query, opts);
//		paramQuery = new FrameLayout.LayoutParams(opts.outWidth, opts.outHeight);
//		paramQuery.gravity = Gravity.LEFT | Gravity.TOP;
//		paramQuery.topMargin = heightBody + opts.outHeight / 6 + opts.outHeight / 6 + opts.outHeight / 6;
//		paramQuery.leftMargin = opts.outWidth / 12;
//
//		BitmapFactory.decodeResource(cMain.getResources(), R.drawable.menu_phone, opts);
//		paramPhone = new FrameLayout.LayoutParams(opts.outWidth, opts.outHeight);
//		paramPhone.gravity = Gravity.LEFT | Gravity.TOP;
//		paramPhone.topMargin = heightBody + opts.outHeight / 2 + opts.outHeight / 10;
//		paramPhone.leftMargin = opts.outWidth / 4 + opts.outWidth / 10 + opts.outWidth / 12;
//
//		BitmapFactory.decodeResource(cMain.getResources(), R.drawable.menu_msg, opts);
//		paramMsg = new FrameLayout.LayoutParams(opts.outWidth, opts.outHeight);
//		paramMsg.gravity = Gravity.RIGHT | Gravity.TOP;
//		paramMsg.topMargin = heightBody + opts.outHeight / 4 + opts.outHeight / 10;
//		paramMsg.rightMargin = opts.outWidth / 6;
//
//		BitmapFactory.decodeResource(cMain.getResources(), R.drawable.menu_aboat, opts);
//		paramHistory = new FrameLayout.LayoutParams(opts.outWidth, opts.outHeight);
//		paramHistory.gravity = Gravity.BOTTOM | Gravity.RIGHT;
//		// paramHistory.bottomMargin = opts.outHeight / 2;
//	}
//
//	void initLayout540()
//	{
//		BitmapFactory.decodeResource(cMain.getResources(), R.drawable.menu_report, opts);
//		opts = getOptions540(opts);
//		paramReport = new FrameLayout.LayoutParams(opts.outWidth, opts.outHeight);
//		heightBody = opts.outHeight;
//		paramReport.gravity = Gravity.CENTER | Gravity.TOP;
//		paramReport.topMargin = heightBody / 2 + heightBody / 4;
//
//		BitmapFactory.decodeResource(cMain.getResources(), R.drawable.menu_history_query, opts);
//		opts = getOptions540(opts);
//		paramQuery = new FrameLayout.LayoutParams(opts.outWidth, opts.outHeight);
//		paramQuery.gravity = Gravity.LEFT | Gravity.TOP;
//		paramQuery.topMargin = heightBody + opts.outHeight / 6 + opts.outHeight / 6 + opts.outHeight / 6;
//		paramQuery.leftMargin = opts.outWidth / 12;
//
//		BitmapFactory.decodeResource(cMain.getResources(), R.drawable.menu_phone, opts);
//		opts = getOptions540(opts);
//		paramPhone = new FrameLayout.LayoutParams(opts.outWidth, opts.outHeight);
//		paramPhone.gravity = Gravity.LEFT | Gravity.TOP;
//		paramPhone.topMargin = heightBody + opts.outHeight / 4 + opts.outHeight / 6 + opts.outHeight / 6 + 2;
//		paramPhone.leftMargin = opts.outWidth / 8 + opts.outWidth / 8 + opts.outWidth / 6 + 4;
//
//		BitmapFactory.decodeResource(cMain.getResources(), R.drawable.menu_msg, opts);
//		opts = getOptions540(opts);
//		paramMsg = new FrameLayout.LayoutParams(opts.outWidth, opts.outHeight);
//		paramMsg.gravity = Gravity.RIGHT | Gravity.TOP;
//		paramMsg.topMargin = heightBody + opts.outHeight / 8 + opts.outHeight / 8 + opts.outHeight / 10;
//		paramMsg.rightMargin = opts.outWidth / 6;
//
//		BitmapFactory.decodeResource(cMain.getResources(), R.drawable.menu_aboat, opts);
//		opts = getOptions540(opts);
//		paramHistory = new FrameLayout.LayoutParams(opts.outWidth, opts.outHeight);
//		paramHistory.gravity = Gravity.BOTTOM | Gravity.RIGHT;
//	}
//
//	void initLayout800()
//	{
//		BitmapFactory.decodeResource(cMain.getResources(), R.drawable.menu_report, opts);
//		opts = getOptions800(opts);
//		paramReport = new FrameLayout.LayoutParams(opts.outWidth, opts.outHeight);
//		heightBody = opts.outHeight;
//		paramReport.gravity = Gravity.CENTER | Gravity.TOP;
//		paramReport.topMargin = heightBody / 2 + heightBody / 4;
//
//		BitmapFactory.decodeResource(cMain.getResources(), R.drawable.menu_history_query, opts);
//		opts = getOptions800(opts);
//		paramQuery = new FrameLayout.LayoutParams(opts.outWidth, opts.outHeight);
//		paramQuery.gravity = Gravity.LEFT | Gravity.TOP;
//		paramQuery.topMargin = heightBody + opts.outHeight / 6 + opts.outHeight / 6 + opts.outHeight / 6;
//		paramQuery.leftMargin = opts.outWidth / 12;
//
//		BitmapFactory.decodeResource(cMain.getResources(), R.drawable.menu_phone, opts);
//		opts = getOptions800(opts);
//		paramPhone = new FrameLayout.LayoutParams(opts.outWidth, opts.outHeight);
//		paramPhone.gravity = Gravity.LEFT | Gravity.TOP;
//		paramPhone.topMargin = heightBody + opts.outHeight / 4 + opts.outHeight / 6 + opts.outHeight / 6 + 2;
//		paramPhone.leftMargin = opts.outWidth / 8 + opts.outWidth / 8 + opts.outWidth / 6 + 4;
//
//		BitmapFactory.decodeResource(cMain.getResources(), R.drawable.menu_msg, opts);
//		opts = getOptions800(opts);
//		paramMsg = new FrameLayout.LayoutParams(opts.outWidth, opts.outHeight);
//		paramMsg.gravity = Gravity.RIGHT | Gravity.TOP;
//		paramMsg.topMargin = heightBody + opts.outHeight / 8 + opts.outHeight / 8 + opts.outHeight / 10;
//		paramMsg.rightMargin = opts.outWidth / 6;
//
//		BitmapFactory.decodeResource(cMain.getResources(), R.drawable.menu_aboat, opts);
//		opts = getOptions800(opts);
//		paramHistory = new FrameLayout.LayoutParams(opts.outWidth, opts.outHeight);
//		paramHistory.gravity = Gravity.BOTTOM | Gravity.RIGHT;
//	}
//
//	Options getOptions320(Options opts)
//	{
//		opts.outHeight = (int) (opts.outHeight / 1.5);
//		opts.outWidth = (int) (opts.outWidth / 1.5);
//		return opts;
//	}
//
//	Options getOptions540(Options opts)
//	{
//		opts.outHeight = (int) (opts.outHeight * 1.125);
//		opts.outWidth = (int) (opts.outWidth * 1.125);
//		return opts;
//	}
//
//	Options getOptions800(Options opts)
//	{
//		opts.outHeight = (int) (opts.outHeight * 1.6);
//		opts.outWidth = (int) (opts.outWidth * 1.6);
//		return opts;
//	}
//
//	OnClickListener onClickListener = new OnClickListener()
//		{
//			@Override
//			public void onClick(View v)
//			{
//				onItemClick(v.getId());
//
//				frameLayout.removeAllViews();
//				for (View view : viewList)
//				{
//					if (v != view)
//					{
//						frameLayout.addView(view, view.getLayoutParams());
//					}
//				}
//				frameLayout.addView(v, v.getLayoutParams());
//			}
//		};
//
//	void onItemClick(int id)
//	{
//		Intent intent = null;
//
//		switch (id)
//		{
//			case R.xml.menu_history_query:
//				intent = new Intent();
//				intent.setClass(cMain, TabHostMain.class);
//				cMain.startActivity(intent);
//				break;
//			case R.xml.menu_report:
//				intent = new Intent();
//				intent.setClass(cMain, Casereport.class);
//				cMain.startActivity(intent);
//				break;
//			case R.xml.menu_msg:
//				// setMsg();
//				UpSysDialog.update(cMain);
//				break;
//			case R.xml.menu_call:
//				setCall();
//				break;
//			case R.xml.menu_aboat:
//				MsgBox.show(cMain, cMain.getString(R.string.explain_context));
//				break;
//		}
//	}
//
//	void setCall()
//	{
//		android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(cMain);
//		dialog.setTitle("呼叫12319");
//		dialog.setMessage("确定拨打 12319 热线电话？");
//		dialog.setCancelable(true);
//		dialog.setPositiveButton("是", new DialogInterface.OnClickListener()
//			{
//				@Override
//				public void onClick(DialogInterface dialog, int which)
//				{
//					Intent intent = new Intent();
//					intent.setAction(Intent.ACTION_CALL);
//					intent.setData(Uri.parse("tel:12319"));
//					cMain.startActivity(intent);
//				}
//			}).setNegativeButton("否", null);
//		dialog.show();
//	}
//
//	void setMsg()
//	{
//		android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(cMain);
//		dialog.setTitle("短信发送");
//		dialog.setMessage("确定发送举报短信？");
//		dialog.setCancelable(true);
//		dialog.setPositiveButton("是", new DialogInterface.OnClickListener()
//			{
//				@Override
//				public void onClick(DialogInterface dialog, int which)
//				{
//					Uri uri = Uri.parse("smsto:106575580211");
//					Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
//					cMain.startActivity(intent);
//				}
//			}).setNegativeButton("否", null);
//		dialog.show();
//	}
// }
