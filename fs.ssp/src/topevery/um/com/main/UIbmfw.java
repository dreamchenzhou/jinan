//package topevery.um.com.main;
//
//import java.util.Random;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import longHuaOffice.DialogSet.UrlCallback;
//import longhua.office.oa.R;
//import android.annotation.SuppressLint;
//import android.app.Activity;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.Color;
//import android.net.Uri;
//import android.net.http.SslError;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.KeyEvent;
//import android.view.Menu;
//import android.view.MenuInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.webkit.CookieManager;
//import android.webkit.CookieSyncManager;
//import android.webkit.DownloadListener;
//import android.webkit.JavascriptInterface;
//import android.webkit.SslErrorHandler;
//import android.webkit.WebChromeClient;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.webkit.WebViewClient;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//import core.DocFlow;
//import core.JsonCore;
//import core.MsgBox;
//import core.PhoneCore;
//import core.ScrawlCompleted;
//import core.ScrawlUtils;
//import core.Settings;
//
///**
// * 
// * @author martin.zheng
// *
// */
//public class UIbmfw extends Activity
//{
//	// private static final String random_url = "&123456789";
//	private UIbmfw wThis = this;
//
//	public WebView webView;
//	private ProgressBar pBar, webBar;
////	private DialogSet dialogSet;
////	private DialogUpdate dialogUpdate;
//
//	private DocFlow docFlow;
//	@SuppressWarnings("unused")
//	private String lastUrl;
//
//	private Random mRandom = new Random();
//
//	@SuppressLint(
//	{ "JavascriptInterface", "SetJavaScriptEnabled" })
//	@Override
//	public void onCreate(Bundle savedInstanceState)
//	{
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.main_web);
//
//		pBar = (ProgressBar) this.findViewById(R.id.pBar);
//		webBar = (ProgressBar) this.findViewById(R.id.webBar);
//		webView = (WebView) this.findViewById(R.id.webView);
//		webView.getSettings().setJavaScriptEnabled(true);
//		// webView.getSettings().setPluginsEnabled(true);
//		// webView.getSettings().setPluginState(PluginState.ON);
//		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//		// WebViewCache.initWebView(wThis, webView);
//		webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//		webView.setWebViewClient(new MyWebViewClient());
//		webView.setWebChromeClient(new MyWebChromeClient());
//		webView.setDownloadListener(new MyWebViewDownLoadListener());
//		webView.setBackgroundColor(Color.TRANSPARENT);
//		webView.getBackground().setAlpha(0);
//		webView.getSettings().setAppCacheEnabled(false);
//		webView.getSettings().setDomStorageEnabled(false);
//		webView.getSettings().setDatabaseEnabled(false);
//
//		if (ScrawlUtils.canWrite())
//		{
//			webView.addJavascriptInterface(new HandWrite(), "DocFlow");
//		}
//
//		/**
//		 * 双击放大
//		 */
//		// webView.getSettings().setUseWideViewPort(true);
//		// webView.getSettings().setLoadWithOverviewMode(true);
//
//		// 设置可以支持缩放
//		// webView.getSettings().setSupportZoom(true);
//		// // 设置默认缩放方式尺寸是far
//		// webView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
//		// // 设置出现缩放工具
//		// webView.getSettings().setBuiltInZoomControls(true);
//		// // 让网页自适应屏幕宽度
//		// WebSettings webSettings = webView.getSettings(); // webView:
//		// 类WebView的实例
//		// webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
//
//		// onOpen();
//
//		dialogSet = new DialogSet(wThis);
//		dialogSet.setUrlCallback(new UrlCallback()
//		{
//			@Override
//			public void callback(String url)
//			{
//				// NetUtils.OAURL = url;
//			}
//		});
//
//		dialogUpdate = new DialogUpdate(wThis);
//
//		new DownloaderDeleteTask().execute((Void) null);
//
//		String url = NetUtils.getOAURL();
//
//		// if (TextUtils.isEmpty(url))
//		// {
//		// url = NetUtils.OAURL;
//		// }
//
//		boolean isNumLogin = Settings.getInstance().isNumLogin;
//		String num = Settings.getInstance().number;
//
//		if (isNumLogin && !TextUtils.isEmpty(num))
//		{
//			url = url.replace("mobile/login.aspx",
//					"ashx/MobileLogin.ashx?mobileNO=");
//			url = url + num;
//		}
//
//		// url = "http://192.168.3.30:8081/Default.aspx";
//
//		webView.loadUrl(url);
//
//		new Timer().schedule(new TimerTask()
//		{
//			@Override
//			public void run()
//			{
//				dialogUpdate.show(false);
//			}
//		}, 20 * 1000);
//
//		// ActionBar actionBar = getActionBar();
//		// actionBar.setDisplayHomeAsUpEnabled(true);
//
//		// actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
//	}
//
//	private class HandWrite
//	{
//		/**
//		 * docFlow(json)
//		 * 
//		 * @param docFlow
//		 */
//
//		@JavascriptInterface
//		public void showHandWrite(String jsonDocFlow)
//		{
//			docFlow = JsonCore.fromJson(jsonDocFlow, DocFlow.class);
//
//			ScrawlUtils.showHandWrite(wThis, new ScrawlCompleted()
//			{
//				@Override
//				public void completed(String path)
//				{
//					if (docFlow != null && docFlow.getFileId() > 0)
//					{
//						docFlow.setNoteFileName(path);
//						new UploadTask(wThis, docFlow).execute((Void) null);
//					}
//				}
//			});
//		}
//	}
//
//	class MyWebChromeClient extends WebChromeClient
//	{
//		@Override
//		public void onReceivedTitle(WebView view, String title)
//		{
//			super.onReceivedTitle(view, title);
//			// setTitle(title);
//		}
//
//		@Override
//		public void onProgressChanged(WebView view, int newProgress)
//		{
//			super.onProgressChanged(view, newProgress);
//			webBar.setProgress(newProgress);
//			if (newProgress >= 100)
//			{
//				webBar.setVisibility(View.GONE);
//			}
//			else
//			{
//				if (webBar.getVisibility() != View.VISIBLE)
//				{
//					webBar.setVisibility(View.VISIBLE);
//				}
//			}
//		}
//	}
//
//	private class MyWebViewDownLoadListener implements DownloadListener
//	{
//		@Override
//		public void onDownloadStart(String url, String userAgent,
//				String contentDisposition, String mimetype, long contentLength)
//		{
//			// Log.i("tag", "url=" + url);
//			// Log.i("tag", "userAgent=" + userAgent);
//			// Log.i("tag", "contentDisposition=" + contentDisposition);
//			// Log.i("tag", "mimetype=" + mimetype);
//			// Log.i("tag", "contentLength=" + contentLength);
//			// Uri uri = Uri.parse(url);
//			// Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//			// startActivity(intent);
//
//			// DownloaderTask task = new DownloaderTask(wThis, url);
//			// task.execute(url);
//
//			ScrawlUtils.checkDown(wThis, url, contentLength);
//		}
//	}
//
//	private boolean tel_sms(String url)
//	{
//		if (url.startsWith("sms:") || url.startsWith("tel:"))
//		{
//			String num = null;
//
//			if (url.startsWith("sms:"))
//			{
//				num = url.replace("sms:", "");
//				PhoneCore.sendSMS(wThis, num);
//			}
//			else if (url.startsWith("tel:"))
//			{
//				num = url.replace("tel:", "");
//				PhoneCore.callPhone(wThis, false, num);
//			}
//			return true;
//		}
//		return false;
//	}
//
//	class MyWebViewClient extends WebViewClient
//	{
//		public boolean shouldOverrideUrlLoading(WebView view, String url)
//		{
//			if (url.indexOf(Settings.getInstance().oaip) < 0)
//			{
//				onOpen(url);
//				return true;
//			}
//			if (!tel_sms(url))
//			{
//				view.loadUrl(url);
//			}
//			return true;
//		}
//
//		@Override
//		public void onPageStarted(WebView view, String url, Bitmap favicon)
//		{
//			// pDialog.show();
//			pBar.setVisibility(View.VISIBLE);
//			super.onPageStarted(view, url, favicon);
//		}
//
//		@Override
//		public void onPageFinished(WebView view, String url)
//		{
//			super.onPageFinished(view, url);
//			// pDialog.hide();
//			pBar.setVisibility(View.GONE);
//		}
//
//		@Override
//		public void onReceivedSslError(WebView view, SslErrorHandler handler,
//				SslError error)
//		{
//			super.onReceivedSslError(view, handler, error);
//		}
//
//		@Override
//		public void onReceivedError(WebView view, int errorCode,
//				String description, String failingUrl)
//		{
//			super.onReceivedError(view, errorCode, description, failingUrl);
//		}
//	}
//
//	public void showBar()
//	{
//		pBar.setVisibility(View.VISIBLE);
//	}
//
//	public void hideBar()
//	{
//		pBar.setVisibility(View.GONE);
//	}
//
//	void onOpen()
//	{
//		try
//		{
//			Intent intent = new Intent();
//			// intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//			intent.setAction("android.intent.action.VIEW");
//			Uri content_url = Uri.parse(NetUtils.getOAURL());
//			intent.setData(content_url);
//			startActivity(intent);
//			// count++;
//		}
//		catch (Exception e)
//		{
//			Toast.makeText(wThis, e.getMessage(), Toast.LENGTH_SHORT).show();
//		}
//		finally
//		{
//			finish();
//		}
//	}
//
//	public boolean onKeyDown(int keyCode, KeyEvent event)
//	{
//		if ((keyCode == KeyEvent.KEYCODE_BACK))
//		{
//			if (goBack())
//			{
//				return true;
//			}
//		}
//		return super.onKeyDown(keyCode, event);
//	}
//
//	@Override
//	protected void onResume()
//	{
//		super.onResume();
//
//		// onOpen();
//	}
//
//	private final int id_set = 0;
//	private final int id_up = 1;
//	private final int id_exit = 2;
//	private final int id_retry = 3;
//
//	private final int id_goBack = 4;// 后退
//	private final int id_goForward = 5;// 前进
//	private final int id_reload = 6;// 刷新
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu)
//	{
//		// menu.addSubMenu(0, id_set, 0, "设置");
//		// menu.addSubMenu(0, id_up, 0, "更新");
//		// // menu.addSubMenu(0, id_retry, 0, "注销");
//		// menu.addSubMenu(0, id_exit, 0, "退出");
//		//
//		// // menu.addSubMenu(0, id_goBack, 0, "后退");
//		// // menu.addSubMenu(0, id_goForward, 0, "前进");
//		// // menu.addSubMenu(0, id_reload, 0, "刷新");
//		// return true;
//
//		MenuInflater inflater = getMenuInflater();
//		inflater.inflate(R.menu.oa_actions, menu);
//		return super.onCreateOptionsMenu(menu);
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item)
//	{
//		switch (item.getItemId())
//		{
//		case R.id.oa_set:
//		case id_set:
//			dialogSet.show();
//			break;
//		case id_exit:
//		case R.id.oa_exit:
//			hookupClose();
//			break;
//		case R.id.oa_update:
//		case id_up:
//			dialogUpdate.show(true);
//			break;
//		case id_retry:
//		case R.id.oa_logout:
//			// WebViewCache.clearWebViewCache(wThis);
//			// webView.loadDataWithBaseURL(null, "", "text/html", "utf-8",
//			// null);
//			clearCache();
//			webView.loadUrl(NetUtils.getOAURL());
//			break;
//		case R.id.oa_back:
//		case id_goBack:
//			goBack();
//			break;
//
//		case R.id.oa_forward:
//		case id_goForward:
//			goForward();
//			break;
//
//		case R.id.oa_reload:
//		case id_reload:
//			reload();
//			break;
//		}
//		return true;
//	}
//
//	// 后退
//	private boolean goBack()
//	{
//		if (webView.canGoBack())
//		{
//			webView.goBack();
//			return true;
//		}
//		return false;
//	}
//
//	// 前进
//	private void goForward()
//	{
//		if (webView.canGoForward())
//		{
//			webView.goForward();
//		}
//	}
//
//	// 刷新
//	public void reload()
//	{
//		webView.reload();
//		// String url = lastUrl;
//		// url = reloadUrl(url);
//		// webView.loadUrl(url);
//	}
//
//	@SuppressWarnings("unused")
//	private String reloadUrl(String url)
//	{
//		clearCache2();
//
//		String reload_url = url;
//		if (reload_url.indexOf("temp") < 0)
//		{
//			if (reload_url.endsWith("aspx?") || reload_url.endsWith("aspx"))
//			{
//				if (!reload_url.endsWith("?"))
//				{
//					reload_url += "?";
//				}
//				reload_url = reload_url + getRandom();
//			}
//			else
//			{
//				reload_url = reload_url + getRandom();
//			}
//		}
//		return reload_url;
//	}
//
//	private String getRandom()
//	{
//		String txt = String.format("&%s", mRandom.nextInt());
//		return txt;
//	}
//
//	// 停止加载
//	public void stopLoading()
//	{
//		webView.stopLoading();
//	}
//
//	private void clearCache()
//	{
//		try
//		{
//			// DataCleanManager.cleanApplicationData(wThis);
//
//			// 清理自动登录
//			CookieSyncManager.createInstance(this);
//			// CookieSyncManager.getInstance().startSync();
//			CookieManager cookieManager = CookieManager.getInstance();
//			// cookieManager.removeSessionCookie();
//			cookieManager.removeAllCookie();
//
//			// webView.pauseTimers();
//
//			// 清理缓存
//			webView.clearCache(true);
//			webView.clearHistory();
//			webView.clearFormData();
//			wThis.deleteDatabase("webview.db");
//			wThis.deleteDatabase("webviewCache.db");
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//
//	private void clearCache2()
//	{
//		try
//		{
//			// File file = CacheManager.getCacheFileBaseDir();
//
//			webView.clearSslPreferences();
//			webView.clearCache(true);
//			webView.clearHistory();
//			webView.clearFormData();
//			wThis.deleteDatabase("webview.db");
//			wThis.deleteDatabase("webviewCache.db");
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void onBackPressed()
//	{
//		// super.onBackPressed();
//		hookupClose();
//	}
//
//	private void hookupClose()
//	{
//		MsgBox.askYesNo(wThis, "您确定退出？", new DialogInterface.OnClickListener()
//		{
//			@Override
//			public void onClick(DialogInterface dialog, int which)
//			{
//				onClose();
//			}
//		}, null);
//	}
//
//	private void onClose()
//	{
//		clearCache();
//		finish();
//		android.os.Process.killProcess(android.os.Process.myPid());
//	}
//
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data)
//	{
//		super.onActivityResult(requestCode, resultCode, data);
//
//		// if (resultCode == Activity.RESULT_OK)
//		// {
//		// if (data != null)
//		// {
//		// NetUtils.OAURL = data.getStringExtra("OAURL");
//		// }
//		// }
//	}
//
//	void onOpen(String url)
//	{
//		try
//		{
//			Intent intent = new Intent();
//			// intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//			intent.setAction("android.intent.action.VIEW");
//			Uri content_url = Uri.parse(url);
//			intent.setData(content_url);
//			startActivity(intent);
//			// count++;
//		}
//		catch (Exception e)
//		{
//			Toast.makeText(wThis, e.getMessage(), Toast.LENGTH_SHORT).show();
//		}
//		finally
//		{
//		}
//	}
// }