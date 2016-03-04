package topevery.um.com.main;

import topevery.um.jinan.manager.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class MainDialog
{
	private static Dialog mainDialog;
	private static int textViewResourceId = R.layout.main_dialog;
	private static int textViewResourceId2 = R.layout.main_dialog_little;
	private static int textViewResourceId3 = R.layout.main_dialog_single;
	private static int styleResourceId = R.style.dialog;
	private static TextView title;
	private static TextView contentTxt;
	private static Button btn_sure, btn_yes, btn_no, btn_single;

	public static void setDismiss()
	{
		mainDialog.dismiss();
	}

	public static void askYes(Activity context, String content)
	{
		mainDialog = new Dialog(context, styleResourceId);
		mainDialog.setContentView(textViewResourceId3);
		title = (TextView) mainDialog.findViewById(R.id.main_dialog_single_content);

		btn_single = (Button) mainDialog.findViewById(R.id.btn_dialog_single_yes);
		btn_single.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					mainDialog.dismiss();
				}
			});
		title.setText(content);
		setCount(context, mainDialog, null, 0.95, false);
		mainDialog.show();
	}

	public static void askYes(Context context, String content)
	{
		mainDialog = new Dialog(context, styleResourceId);
		mainDialog.setContentView(textViewResourceId3);
		title = (TextView) mainDialog.findViewById(R.id.main_dialog_single_content);

		btn_single = (Button) mainDialog.findViewById(R.id.btn_dialog_single_yes);
		btn_single.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					mainDialog.dismiss();
				}
			});
		title.setText(content);
		// setCount(context, mainDialog, null, 0.95, false);
		mainDialog.show();
	}

	public static void askYes(Activity context, String content,
			OnClickListener listenrYes)
	{
		mainDialog = new Dialog(context, styleResourceId);
		mainDialog.setContentView(textViewResourceId3);
		title = (TextView) mainDialog.findViewById(R.id.main_dialog_single_content);

		btn_single = (Button) mainDialog.findViewById(R.id.btn_dialog_single_yes);
		btn_single.setOnClickListener(listenrYes);
		title.setText(content);
		setCount(context, mainDialog, null, 0.95, false);
		mainDialog.show();
	}

	public static void show(Context context, String content,
			OnClickListener listenerYes)
	{
		mainDialog = new Dialog(context, styleResourceId);
		mainDialog.setContentView(textViewResourceId2);
		contentTxt = (TextView) mainDialog.findViewById(R.id.main_dialog_content);

		btn_yes = (Button) mainDialog.findViewById(R.id.btn_dialog_yes);
		btn_no = (Button) mainDialog.findViewById(R.id.btn_dialog_no);
		btn_yes.setOnClickListener(listenerYes);
		btn_no.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View v)
				{
					mainDialog.dismiss();
				}
			});

		contentTxt.setText(content);
		setCount(context, mainDialog, null, 0.95, false);
		mainDialog.show();
	}

	public static void show(Activity context, String content,
			OnClickListener listenerYes, OnClickListener listenerNo)
	{
		mainDialog = new Dialog(context, styleResourceId);
		mainDialog.setContentView(textViewResourceId2);
		contentTxt = (TextView) mainDialog.findViewById(R.id.main_dialog_content);

		btn_yes = (Button) mainDialog.findViewById(R.id.btn_dialog_yes);
		btn_no = (Button) mainDialog.findViewById(R.id.btn_dialog_no);
		btn_yes.setOnClickListener(listenerYes);
		btn_no.setOnClickListener(listenerNo);

		contentTxt.setText(content);
		setCount(context, mainDialog, null, 0.95, false);
		mainDialog.show();
	}

	public static void show(Context cMain)
	{

		mainDialog = new Dialog(cMain, styleResourceId);
		mainDialog.setContentView(textViewResourceId);
		btn_sure = (Button) mainDialog.findViewById(R.id.main_dialog_btn);
		btn_sure.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					mainDialog.dismiss();
				}
			});
		setCount(cMain, mainDialog, 0.8, 0.95, true);
		mainDialog.show();
	}

	private static void setCount(Context context, Dialog mainDialog2,
			Double fHeight, Double fWidth, boolean yes)
	{

		Window dialogWindow = mainDialog.getWindow();
		WindowManager wManager = dialogWindow.getWindowManager();

		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.alpha = 0.9f;// 透明度

		WindowManager mWindowManager = ((Activity) context).getWindowManager();
		Display mDisplay = mWindowManager.getDefaultDisplay();// 获取屏幕宽、高用
		WindowManager.LayoutParams mLayoutParams = mainDialog.getWindow().getAttributes();// 获取对话框当前的参数值

		if (yes)
		{
			mLayoutParams.height = (int) (mDisplay.getHeight() * fHeight); // 高度设置为屏幕的0.6
		}
		else
		{
			mLayoutParams.height = LayoutParams.WRAP_CONTENT; // 高度设置为屏幕的0.6
		}
		mLayoutParams.width = (int) (mDisplay.getWidth() * fWidth); // 宽度设置为屏幕的0.95

		dialogWindow.setAttributes(mLayoutParams);
	}

}
