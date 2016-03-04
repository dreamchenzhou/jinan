package topevery.um.com.zijunlin.Zxing.Demo.jsoup;

import topevery.um.com.title.TitleTextView;
import topevery.um.jinan.manager.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Activit1 extends Activity
{
	WebView mWebView;
	public static String url = "";
	Activit1 thisActivit1 = this;
	ProgressDialog pdDialog;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.mainjsoup);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);

		mWebView = (WebView) this.findViewById(R.id.webView);

		pdDialog = new ProgressDialog(thisActivit1);
		pdDialog.setTitle("正在加载页面");
		pdDialog.setMessage("请稍等...");

		// 设置WebView属性，能够执行Javascript脚本(响应网页触发事件)
		mWebView.getSettings().setJavaScriptEnabled(true);

		WebSettings webSettings = mWebView.getSettings();
		webSettings.setAllowFileAccess(true);

		// mWebView.addJavascriptInterface(new DemoJavaScriptInterface(),
		// "demo");

		// 响应网页对话框
		mWebView.setWebChromeClient(new WebChromeClient()
			{

				@Override
				public void onProgressChanged(WebView view, int newProgress)
				{
					// thisActivit1.setTitle("正在载页面，请稍等...");
					pdDialog.show();

					pdDialog.setProgress(newProgress * 100);
					if (newProgress == 100)
						pdDialog.hide();
				}

				@Override
				public void onReceivedTitle(WebView view, String title)
				{
					TitleTextView.changeTitle(thisActivit1, title);
				}

				@Override
				public boolean onJsAlert(WebView view, String url,
						String message, final JsResult result)
				{
					Builder builder = new Builder(Activit1.this);
					builder.setTitle("警告");
					builder.setMessage(message);
					builder.setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener()
						{
							public void onClick(DialogInterface dialog,
									int which)
							{
								// 点击确定按钮之后,继续执行网页中的操作
								result.confirm();
							}
						});
					builder.setCancelable(false);
					builder.create();
					builder.show();
					return true;
				}
			});

		//
		mWebView.setWebViewClient(new WebViewClient()
			{
				@Override
				public void onPageStarted(WebView view, String url,
						Bitmap favicon)
				{
					super.onPageStarted(view, url, favicon);
					// pdDialog.show();
				}

				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url)
				{
					view.loadUrl(url);
					return true;
				}

				@Override
				public void onPageFinished(WebView view, String url)
				{
					super.onPageFinished(view, url);
					// pdDialog.hide();
				}

				@Override
				public void onReceivedError(WebView view, int errorCode,
						String description, String failingUrl)
				{

					super.onReceivedError(view, errorCode, description, failingUrl);
				}
			});

		mWebView.loadUrl(url);
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		if (pdDialog != null)
		{
			pdDialog.cancel();
		}
	}

	// // 设置回退
	// public boolean onKeyDown(int keyCode, KeyEvent event)
	// {
	// if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack())
	// {
	// mWebView.goBack(); // goBack()表示返回WebView的上一页面
	// return true;
	// }
	// return false;
	// }
}
