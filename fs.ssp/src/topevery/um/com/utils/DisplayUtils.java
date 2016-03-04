package topevery.um.com.utils;

import java.io.File;

import topevery.android.core.MsgBox;
import topevery.um.com.Settings;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.DisplayMetrics;

public class DisplayUtils
{
	public static int widthPixels;
	public static int heightPixels;

	public static void init(Activity context)
	{
		DisplayMetrics dm = new DisplayMetrics();

		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		widthPixels = dm.widthPixels;
		heightPixels = dm.heightPixels;
	}

	public static String getUrl()
	{
		String url = String.format("http://%s/PublicServer/mobile/xcum/index.aspx", Settings.getInstance().UdpIp);
		return url;
	}
	
	public static void showImage(Context mContext, File file)
	{
		try
		{
			if (mContext == null)
			{
				return;
			}

			if (file != null)
			{
				if (file.exists())
				{
					Intent intent = new Intent();
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setAction(android.content.Intent.ACTION_VIEW);
					Uri data = Uri.fromFile(file);
					intent.setDataAndType(data, "image/*");
					mContext.startActivity(intent);
				}
				else
				{
					MsgBox.makeTextSHORT(mContext, "文件不存在");
				}
			}
		}
		catch (Exception e)
		{
			MsgBox.show(mContext, e.getMessage());
		}
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}
