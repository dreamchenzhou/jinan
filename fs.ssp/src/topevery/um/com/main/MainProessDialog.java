package topevery.um.com.main;

import topevery.um.jinan.manager.R;
import android.app.Activity;
import android.app.Dialog;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class MainProessDialog
{
	/**
	 * 得到自定义的progressDialog
	 * 
	 * @param context
	 * @param msg
	 * @return
	 */
	static Dialog loadingDialog;

	public static Dialog createLoadingDialog(Activity context, String msg, boolean isCanCancel, boolean isTouchCancel)
	{

		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.maindailong, null);// 得到加载view
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局

		// main.xml中的ImageView
		ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字

		// 加载动画
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.loading_animation);

		// 使用ImageView显示动画
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		tipTextView.setText(msg);// 设置加载信息

		loadingDialog = new Dialog(context, R.style.dialog);// 创建自定义样式dialog

		loadingDialog.setCancelable(isCanCancel);// 不可以用“返回键”取消
		loadingDialog.setCanceledOnTouchOutside(isTouchCancel);
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));// 设置布局

		setCount(context, null, 0.95, false);
		return loadingDialog;
	}

	public static void dissMiss()
	{
		if(loadingDialog != null)
		{
			loadingDialog.dismiss();
		}
	}

	private static void setCount(Activity context, Double fHeight, Double fWidth, boolean yes)
	{

		Window dialogWindow = loadingDialog.getWindow();
		WindowManager wManager = dialogWindow.getWindowManager();

		WindowManager.LayoutParams lp = dialogWindow.getAttributes();
		lp.alpha = 0.9f;// 透明度

		WindowManager mWindowManager = context.getWindowManager();
		Display mDisplay = mWindowManager.getDefaultDisplay();// 获取屏幕宽、高用
		WindowManager.LayoutParams mLayoutParams = loadingDialog.getWindow().getAttributes();// 获取对话框当前的参数值

		if(yes)
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
