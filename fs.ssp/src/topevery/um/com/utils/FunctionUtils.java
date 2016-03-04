package topevery.um.com.utils;

import topevery.um.com.main.Main;
import topevery.um.com.main.MainDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;

public class FunctionUtils {
	public static void setMsg(final Context context) {
		MainDialog.show(context, "确定发送举报短信？", new OnClickListener() {
			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse("smsto:106575580211");
				Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
				context.startActivity(intent);
				MainDialog.setDismiss();
			}
		});
	}

	public static void setCall(final Context context) {
		MainDialog.show(context, "确定拨打 16039 热线电话？", new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:16039"));
				context.startActivity(intent);
				MainDialog.setDismiss();
			}
		});
	}

	public static void setWeb(final Context context) {
		MainDialog.show(context, "确定要访问深圳数字城管网站？", new OnClickListener() {
			@Override
			public void onClick(View v) {
				Uri uri = Uri.parse("http://www.12319.net.cn/");
				Intent it = new Intent(Intent.ACTION_VIEW, uri);
				context.startActivity(it);
				MainDialog.setDismiss();
			}
		});
	}
}
