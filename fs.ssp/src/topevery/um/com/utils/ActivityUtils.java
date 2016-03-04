package topevery.um.com.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import topevery.um.jinan.manager.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class ActivityUtils
{
	public static void startActivity(Activity packageContext, Intent intent)
	{
		packageContext.startActivity(intent);
		if (packageContext instanceof Activity)
		{
			((Activity) packageContext).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		}
	}

	public static void startActivity(Context packageContext, Class<?> cls)
	{
		Intent intent = new Intent();
		intent.setClass(packageContext, cls);
		packageContext.startActivity(intent);
		if (packageContext instanceof Activity)
		{
			((Activity) packageContext).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		}
	}

	public static void startActivityForResult(Activity packageContext, Class<?> cls, int requestCode)
	{
		Intent intent = new Intent();
		intent.setClass(packageContext, cls);
		packageContext.startActivityForResult(intent, requestCode);
		if (packageContext instanceof Activity)
		{
			((Activity) packageContext).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		}
	}

	public static void overridePendingTransition(Context packageContext)
	{
		if (packageContext instanceof Activity)
		{
			((Activity) packageContext).overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
		}
	}

	public static boolean isMobileNO(String mobiles)
	{
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
}
