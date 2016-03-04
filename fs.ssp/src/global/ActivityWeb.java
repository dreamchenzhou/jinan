package global;

import java.io.File;
import java.io.FileOutputStream;

import topevery.android.framework.utils.FileUtils;
import topevery.um.map.UmLocation;
import topevery.um.map.UmLocationClient;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import topevery.um.jinan.manager.R;

/**
 * web嵌套
 * 
 * @author martin.zheng
 * 
 */
@SuppressLint("SetJavaScriptEnabled")
public class ActivityWeb extends BaseActivity
{
	// 主界面
	private WebView mWebView = null;
	private ProgressBar mProgressBar = null;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.web);

		mWebView = (WebView) findViewById(R.id.webView);
		mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
		// 设置支持JavaScript脚本
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		// 设置可以访问文件
		webSettings.setAllowFileAccess(true);
		// 设置可以支持缩放
		webSettings.setSupportZoom(true);
		// 设置默认缩放方式尺寸是far
		webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
		// 设置出现缩放工具
		webSettings.setBuiltInZoomControls(false);
		webSettings.setDefaultFontSize(20);

		mWebView.addJavascriptInterface(new JavascriptandroidFun(), "androidFun");

		// 设置WebViewClient
		mWebView.setWebViewClient(new WebViewClient()
		{
			// url拦截
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url)
			{
				// 使用自己的WebView组件来响应Url加载事件，而不是使用默认浏览器器加载页面
				view.loadUrl(url);
				// 相应完成返回true
				return true;
				// return super.shouldOverrideUrlLoading(view, url);
			}

			// 页面开始加载
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon)
			{
				mProgressBar.setVisibility(View.VISIBLE);
				super.onPageStarted(view, url, favicon);
			}

			// 页面加载完成
			@Override
			public void onPageFinished(WebView view, String url)
			{
				mProgressBar.setVisibility(View.GONE);
				super.onPageFinished(view, url);
			}

			// WebView加载的所有资源url
			@Override
			public void onLoadResource(WebView view, String url)
			{
				super.onLoadResource(view, url);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
			{
				super.onReceivedError(view, errorCode, description, failingUrl);
			}
		});

		// 设置WebChromeClient
		mWebView.setWebChromeClient(new WebChromeClient()
		{
			@Override
			// 处理javascript中的alert
			public boolean onJsAlert(WebView view, String url, String message, final JsResult result)
			{
				return super.onJsAlert(view, url, message, result);
			};

			@Override
			// 处理javascript中的confirm
			public boolean onJsConfirm(WebView view, String url, String message, final JsResult result)
			{
				return super.onJsConfirm(view, url, message, result);
			};

			@Override
			// 处理javascript中的prompt
			public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, final JsPromptResult result)
			{
				return super.onJsPrompt(view, url, message, defaultValue, result);
			};

			// 设置网页加载的进度条
			@Override
			public void onProgressChanged(WebView view, int newProgress)
			{
				mProgressBar.setProgress(newProgress);
				super.onProgressChanged(view, newProgress);
			}

			// 设置程序的Title
			@Override
			public void onReceivedTitle(WebView view, String title)
			{
				setTitle(title);
				super.onReceivedTitle(view, title);
			}

			public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String capture)
			{
				openFileChooser(uploadFile);
			}

			public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType)
			{
				openFileChooser(uploadFile);
			}

			public void openFileChooser(ValueCallback<Uri> uploadFile)
			{
				mUploadMessage = uploadFile;
				// Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				// i.addCategory(Intent.CATEGORY_OPENABLE);
				// i.setType("image/*");
				// // i.setType("*/*");
				// wThis.startActivityForResult(Intent.createChooser(i,
				// "image"),
				// FILECHOOSER_RESULTCODE);

				showPictureSource();
			}
		});

		if (getIntent().getExtras() != null)
		{
			String url = getIntent().getExtras().getString("url");
			if (!TextUtils.isEmpty(url))
			{
				mWebView.loadUrl(url);
			}
		}
	}

	class JavascriptandroidFun
	{
		@JavascriptInterface
		public String getLocation()
		{
			String result = null;
			try
			{
				// GpsValue gps = GpsClient.value.getCurrently();

				UmLocation location = UmLocationClient.getLocation();
				if (location != null)
				{
					result = String.format("%s,%s", location.getLongitude(), location.getLatitude());
				}

				// mTencentLocation = GpsTencent.getInstance().getLocation();
				// if (mTencentLocation != null)
				// {
				// result = String.format("%s,%s",
				// mTencentLocation.getLongitude(),
				// mTencentLocation.getLatitude());
				// }
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return result;
		}
	}

	@Override
	public void onBackPressed()
	{
		if (mWebView.canGoBack())
		{
			mWebView.goBack();
		}
		else
		{
			super.onBackPressed();
		}
	}

	private ValueCallback<Uri> mUploadMessage;

	private int SELECT_PICTURE = 100;
	private int SELECT_CAMER = 101;

	private String mCameraFilePath;

	private void showPictureSource()
	{
		CharSequence[] items =
		{ "相册", "相机" };
		new AlertDialog.Builder(this).setTitle("选择图片来源").setItems(items, new OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				if (which == 0)
				{
					// Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
					// intent.addCategory(Intent.CATEGORY_OPENABLE);
					// intent.setType("image/*");
					// startActivityForResult(Intent.createChooser(intent,
					// "选择图片"), SELECT_PICTURE);

					Intent intent = new Intent(Intent.ACTION_PICK, null);
					intent.setType("image/*");
					// intent.putExtra("crop", "true");
					// intent.putExtra("return-data", true);
					// intent.putExtra("output", Uri.fromFile(getFile()));
					// intent.putExtra("outputFormat", "JPEG");
					intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
					startActivityForResult(intent, SELECT_PICTURE);
				}
				else
				{
					File file = getFile();
					mCameraFilePath = file.getAbsolutePath();

					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
					startActivityForResult(intent, SELECT_CAMER);
				}
			}
		}).setOnCancelListener(new OnCancelListener()
		{
			@Override
			public void onCancel(DialogInterface dialog)
			{
				if (mUploadMessage != null)
				{
					mUploadMessage.onReceiveValue(null);
					mUploadMessage = null;
				}
			}
		}).create().show();
	}

	private File getFile()
	{
		File externalDataDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
		File cameraDataDir = new File(externalDataDir.getAbsolutePath() + File.separator + "browser-photo");
		if (!cameraDataDir.exists())
		{
			cameraDataDir.mkdirs();
		}
		String path = cameraDataDir.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg";
		return new File(path);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		// if (requestCode == FILECHOOSER_RESULTCODE)
		// {
		// if (null == mUploadMessage)
		// return;
		// Uri result = intent == null || resultCode != RESULT_OK ? null :
		// intent.getData();
		// mUploadMessage.onReceiveValue(result);
		// mUploadMessage = null;
		// }

		if (null == mUploadMessage)
			return;
		Uri uri = null;

		if (requestCode == SELECT_PICTURE)
		{
			uri = intent == null || resultCode != RESULT_OK ? null : intent.getData();
			if (uri != null)
			{
				Cursor cursor = getContentResolver().query(uri, null, null, null, null);
				if (cursor.moveToNext())
				{
					String path = cursor.getString(cursor.getColumnIndex("_data"));
					File fromFile = new File(path);
					if (BmfwLink.checkCompress(fromFile))
					{
						File toFile = getFile();
						FileUtils.copy(fromFile, toFile);
						BmfwLink.compress(toFile);
						uri = Uri.fromFile(toFile);
					}
				}
			}
		}
		else if (requestCode == SELECT_CAMER && resultCode == Activity.RESULT_OK)
		{
			if (intent != null)
			{
				uri = intent.getData();
			}
			if (uri == null && mCameraFilePath != null)
			{
				File cameraFile = new File(mCameraFilePath);

				if (cameraFile.exists())
				{
					BmfwLink.compress(cameraFile);
					uri = Uri.fromFile(cameraFile);
				}
			}
		}

		UploadMessage(uri);
	}

	private void UploadMessage(Uri uri)
	{
		if (mUploadMessage != null)
		{
			mUploadMessage.onReceiveValue(uri);
			mUploadMessage = null;
		}
	}

	private static class BmfwLink
	{
		/**
		 * 压缩图片
		 * 
		 * @param file
		 */
		public static void compress(File file)
		{
			try
			{
				Bitmap bitmap;
				FileOutputStream stream;

				Options opts = new Options();
				opts.inSampleSize = 4;
				bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);

				stream = new FileOutputStream(file.getAbsoluteFile());
				bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
				bitmap.recycle();
				stream.flush();
				stream.close();

				if (bitmap != null)
				{
					bitmap.recycle();
					bitmap = null;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		private static long value = 1024 * 1024 * 1024;

		/**
		 * 检测是否需要压缩
		 * 
		 * @param file
		 */
		public static boolean checkCompress(File file)
		{
			if (file != null && file.exists())
			{
				long fValue = file.lastModified();
				if (fValue > value)
				{
					return true;
				}
			}
			return false;
		}
	}
}
