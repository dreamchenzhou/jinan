package topevery.um.com.multipic;

import topevery.um.com.multipic.interfaces.LoadLocalInterface;
import topevery.um.com.multipic.interfaces.SwitchInterface;
import android.text.TextUtils;
import android.widget.ImageView;

public class BitmapLoader {
	
	/**
	 * 
	 * @param path 本地路径
	 * @param img 图片显示的view
	 * @param switcher 线程开关
	 * @param localInterface 回调接口
	 */
	public static void loadImage(String path,ImageView img,SwitchInterface switcher,LoadLocalInterface localInterface){
		if(TextUtils.isEmpty(path)){
			return;
		}
		DecodeTask decodeTask = new DecodeTask(img, path, switcher);
		decodeTask.setOnLoadLocalInterface(localInterface);
		decodeTask.execute();
	}
}
