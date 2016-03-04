package topevery.um.com.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
	private static Toast mToast;
	
	public static void show(Context context,String text){
		if(mToast==null){
			mToast =Toast.makeText(context, text, Toast.LENGTH_SHORT);
		}else{
			mToast.setText(text);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}
	
	public static void show(Context context,int resId){
		if(mToast==null){
			mToast =Toast.makeText(context, resId, Toast.LENGTH_SHORT);
		}else{
			mToast.setText(resId);
			mToast.setDuration(Toast.LENGTH_SHORT);
		}
		mToast.show();
	}
}
