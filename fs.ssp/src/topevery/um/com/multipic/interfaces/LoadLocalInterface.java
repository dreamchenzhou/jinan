package topevery.um.com.multipic.interfaces;

import android.widget.ImageView;

/**
 * 
 * 本地加载
 * 数据放在imageview中
 */
public interface LoadLocalInterface {
	
	public void onLoadLocalFailed(ImageView imageView);
	
	public void onLoadLocalSuccess(ImageView imageView);
}
