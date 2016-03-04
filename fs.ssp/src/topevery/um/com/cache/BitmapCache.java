package topevery.um.com.cache;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;

@SuppressLint("NewApi") 
public class BitmapCache extends LruCache<String, Bitmap>{
	
	private static BitmapCache mCache;
	
	public static BitmapCache getInstance(){
		if(mCache ==null){
			// cache最大的内存为程序运存的1/8；
			int maxSize = (int) (Runtime.getRuntime().maxMemory()/8/1024/1024);
			Log.e("dream", maxSize+"");
			mCache = new BitmapCache(maxSize);
		}
		return mCache;
	}
	
	private BitmapCache(int maxSize) {
		super(maxSize);
	}
	
	@Override
	protected void entryRemoved(boolean evicted, String key, Bitmap oldValue,
			Bitmap newValue) {
		super.entryRemoved(evicted, key, oldValue, newValue);
	}
	
	@Override
	protected int sizeOf(String key, Bitmap value) {
		return value.getRowBytes()*value.getHeight();
	}

	public void putBitmap(String key,Bitmap value){
		if(TextUtils.isEmpty(key)||value==null){
			return ;
		}
		put(key, value);
	}
	
	public Bitmap getBitmap(String key){
		if(TextUtils.isEmpty(key)){
			return null;
		}
		return get(key);
	}
	
}
