package global;

import android.app.Application;

public class ApplicationCore extends Application
{
	public static ApplicationCore mApplicationCore;

	@Override
	public void onCreate()
	{
		super.onCreate();

		// LocationApi.setContext(this);
		// LocationApi.start();
//		BaiduAPI.initialize(this);
		mApplicationCore = this;
//		UniversalImageLoader.init(this,PathUtils.getPhotoPath());
	}

	public static ApplicationCore getInstance()
	{
		return mApplicationCore;
	}
}
