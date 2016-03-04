package topevery.um.com.multipic;

import global.ApplicationCore;
import topevery.um.com.cache.BitmapCache;
import topevery.um.com.multipic.interfaces.LoadLocalInterface;
import topevery.um.com.multipic.interfaces.SwitchInterface;
import topevery.um.com.utils.BitmapUtils;
import topevery.um.jinan.manager.R;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.widget.ImageView;

public class DecodeTask extends AsyncTask<String, Void, Bitmap> {

	private ImageView mImageView;
	private String mPath;
	private SwitchInterface switchInterface;
	private boolean decodeError = false;
	private LoadLocalInterface loadInterface;
	/**
	 * imageview 中缓存的路径
	 */
	private String cachePath;

	public DecodeTask(ImageView img, String path, SwitchInterface switcher) {
		this.mImageView = img;
		this.mPath = path;
		this.switchInterface = switcher;
	}

	public void setOnLoadLocalInterface(LoadLocalInterface localInterface) {
		this.loadInterface = localInterface;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		BitmapDrawable bitmapDrawable = (BitmapDrawable) ApplicationCore
				.getInstance().getResources()
				.getDrawable(R.drawable.post_image);
		mImageView.setImageBitmap(BitmapUtils.getSolideSizeBitmap(
				bitmapDrawable.getBitmap(), 200, 200));
		cachePath = (String) mImageView.getTag();
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		if (switchInterface.isOff() && !mPath.equals(cachePath)) {
			return null;
		}
		Bitmap value = BitmapCache.getInstance().getBitmap(mPath);
		if (value == null) {
			value = BitmapUtils.getBitmapByPath(mPath, 200, 200);
			if (value != null) {
				value = BitmapUtils.getSolideSizeBitmap(value, 200, 200);
				BitmapCache.getInstance().put(mPath, value);
			} else {
				BitmapDrawable bitmapDrawable = (BitmapDrawable) ApplicationCore
						.getInstance().getResources()
						.getDrawable(R.drawable.post_image_loading_failed);
				value = BitmapUtils.getSolideSizeBitmap(
						bitmapDrawable.getBitmap(), 200, 200);
				decodeError = true;
			}
		}
		return value;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		super.onPostExecute(result);
		cachePath = (String) mImageView.getTag();
		if (switchInterface.isOff() && !mPath.equals(cachePath)) {
			return;
		}
		if (result != null) {
			if (cachePath.equals(mPath)) {
				mImageView.setImageBitmap(result);
			}
		}
		if (loadInterface != null&&cachePath.equals(mPath)) {
			if (decodeError) {
				loadInterface.onLoadLocalFailed(mImageView);
			} else {
				loadInterface.onLoadLocalSuccess(mImageView);
			}
		}
	}

}
