package topevery.um.com.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.ab.activity.AbActivity;
import com.ab.view.titlebar.AbBottomBar;
import com.ab.view.titlebar.AbTitleBar;

public class BaseActivity extends AbActivity {
	public int FILL_PARENT = ViewGroup.LayoutParams.FILL_PARENT;
	public int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;
	public int MATCH_PARENT = ViewGroup.LayoutParams.MATCH_PARENT;

	public InputMethodManager mInputMethodManager;

	public AbTitleBar mAbTitleBar = null;
	public AbBottomBar mAbBottomBar = null;

	/**
	 * 检查是否退出
	 */
	public boolean isCheckExit = true;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0)
		{
			finish();
			return;
		}

		mAbTitleBar = this.getTitleBar();
		mAbBottomBar = this.getBottomBar();
//		if (mAbTitleBar != null)
//		{
//			mAbTitleBar.setTitleText(getTitle().toString());
//			mAbTitleBar.setLogo(R.drawable.button_selector_back);
//			mAbTitleBar.setTitleBarBackground(R.drawable.top_bar);
//			mAbTitleBar.setTitleTextMargin(10, 0, 0, 0);
//			mAbTitleBar.setLogoLine(R.drawable.line);
//
//			mAbTitleBar.getLogoView().setOnClickListener(new View.OnClickListener()
//			{
//				@Override
//				public void onClick(View v)
//				{
//					onBackPressed();
//				}
//			});
//
//			// ViewGroup.LayoutParams paramsM = mAbTitleBar.getLayoutParams();
//			// if (paramsM != null)
//			// {
//			// paramsM.height = 65;
//			// }
//		}

		mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	}

	@Override
	public void setContentView(int layoutResID)
	{
		// super.setContentView(layoutResID);
		setAbContentView(layoutResID);
	}

	@Override
	public void setTitle(CharSequence title)
	{
		super.setTitle(title);
		if (mAbTitleBar != null)
		{
			mAbTitleBar.setTitleText(title.toString());
		}
	}

	/**
	 * 隐藏返回按钮
	 */
	public void hideLogoView()
	{
		mAbTitleBar.getLogoView().setVisibility(View.GONE);
	}

	public void startActivity(Class<?> cls)
	{
		Intent intent = new Intent();
		intent.setClass(this, cls);
		startActivity(intent);
	}

	public void startActivity(Class<?> cls, Bundle extras)
	{
		Intent intent = new Intent();
		intent.putExtras(extras);
		intent.setClass(this, cls);
		startActivity(intent);
	}

	public void startActivityForResult(Class<?> cls, int requestCode)
	{
		Intent intent = new Intent();
		intent.setClass(this, cls);
		startActivityForResult(intent, requestCode);
	}

	public void startActivityForResult(Class<?> cls, int requestCode, Bundle extras)
	{
		Intent intent = new Intent();
		intent.putExtras(extras);
		intent.setClass(this, cls);
		startActivityForResult(intent, requestCode);
	}

	/** 隐藏输入法 */
	public void inputHide()
	{
		View decorView = getWindow().getDecorView();
		if (decorView != null)
		{
			IBinder binder = getWindow().getDecorView().getWindowToken();
			mInputMethodManager.hideSoftInputFromWindow(binder, 0);
		}
	}

	/** 隐藏输入法 */
	public void inputHide(View view)
	{
		if (view != null)
		{
			mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	/** 打开输入法 */
	public void inputShow(View view)
	{
		if (view != null)
		{
			int flags = InputMethodManager.SHOW_FORCED;
		}
	}
}
