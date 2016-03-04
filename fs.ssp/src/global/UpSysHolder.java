package global;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import topevery.android.core.GzipHelper;
import topevery.android.core.MsgBox;
import topevery.android.framework.utils.DialogUtils;
import topevery.um.net.update.GetUpdatePara;
import topevery.um.net.update.GetUpdateRes;
import topevery.um.net.update.UpdateHandle;
import topevery.um.net.update.UpdateItem;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import topevery.um.jinan.manager.R;

/**
 * 升级
 * 
 * @author martin.zheng
 * 
 */
@SuppressLint("HandlerLeak")
public class UpSysHolder implements OnCancelListener
{
	public static final Object lockVaue = new Object();
	public static final String versionSubType = "app_ssp";
	public static final String clientVersion = "1.2";
	public static final int packetType = 0;
	public static final String Text_New = "当前已经是最新版本。";
	public static final String Text_Net = "网络故障，请稍后重试。";
	public static final String Text_End = "更新完毕。";

	private boolean isCancel = false;
	private Context mContext;
	private ProgressDialog pDialog;

	private boolean isRun = false;
	private boolean canShowDialog = true;

	private String apkFile = "";

	public UpSysHolder(Context context)
	{
		this.mContext = context;
		pDialog = DialogUtils.getDialogLoading(mContext);

		pDialog.setCanceledOnTouchOutside(false);
		pDialog.setCancelable(false);
		pDialog.setMessage("版本检测中... ...");
		pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pDialog.setOnCancelListener(this);
	}

	public void start(final boolean canShowDialog)
	{
		if (!isRun)
		{
			new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					isRun = true;
					checkUpdate(canShowDialog);
					isRun = false;
				}
			}).start();
		}
	}

	private void checkUpdate(boolean canShowDialog)
	{
		try
		{
			this.canShowDialog = canShowDialog;

			isCancel = false;

			showMsg(null, 5);

			GetUpdatePara para = new GetUpdatePara();
			para.clientVersion = UpSysHolder.clientVersion;
			para.versionSubType = UpSysHolder.versionSubType;
			para.packetType = UpSysHolder.packetType;
			para.passportId = UUID.randomUUID();
			para.userId = UUID.randomUUID();

			GetUpdateRes res = UpdateHandle.GetServerVersion(para);
			if (res != null)
			{
				if (res.isSuccess)
				{
					if (res.datas != null && res.datas.size() > 0)
					{
						showMsg(null, 4);

						for (UpdateItem item : res.datas)
						{
							downFile(item);
							if (item.fileName.indexOf("apk") > 0)
							{
								apkFile = item.path;
								showMsg(apkFile, 2);
							}
						}
						showMsg(null, 3);
					}
					else
					{
						showMsg(Text_New, 0);
					}
				}
				else
				{
					showMsg(res.errorMessage, 0);
				}
			}
			else
			{
				showMsg(Text_Net, 0);
			}
		}
		catch (Exception e)
		{
			showMsg(e.getMessage(), 0);
		}
	}

	/**
	 * 0显示错误，1下载进度 2设置安装启动路劲 3开始安装
	 * 
	 * @param obj
	 * @param what
	 */
	private void showMsg(Object obj, int what)
	{
		Message msg = new Message();
		msg.what = what;
		msg.obj = obj;
		showHandler.sendMessage(msg);
	}

	private Handler showHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);

			if (pDialog.isShowing())
			{
				pDialog.hide();
			}

			switch (msg.what)
			{
				case 5:
					if (canShowDialog)
					{
						pDialog.show();
					}
					// upItem.resetProgress();
					break;
				case 0:
					if (canShowDialog)
					{
						MsgBox.show(mContext, msg.obj.toString());
						// MsgBox.makeTextSHORT(mContext,
						// msg.obj.toString());
					}
					break;
				case 1:
					int progress = Integer.parseInt(msg.obj.toString());
					// upItem.setProgress(progress);
					break;
				case 2:
					// upItem.setApkPath(msg.obj.toString());
					break;
				case 3:
					MsgBox.makeTextSHORT(mContext, "更新下载完毕");
					// upItem.showInstall();
					// upItem.install();
					installApk();
					break;
				case 4:
					// upItem.showBar();
					MsgBox.makeTextSHORT(mContext, "检测到新版本，更新下载中... ...");
					break;
				default:
					break;
			}
		}
	};

	private void installApk()
	{
		File file = new File(apkFile);
		if (file.exists())
		{
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
			mContext.startActivity(intent);
		}
		else
		{
			MsgBox.show(mContext, "安装程序文件不存在。");
		}
	}

	private void downFile(UpdateItem item) throws Exception
	{
		String path = "";
		InputStream is = null;
		FileOutputStream fos = null;
		int download_precent = 0;

		try
		{

			path = getRootFolder(item);

			// item.downUrl = ClientUtils.replaceIP(item.downUrl);

			HttpURLConnection urlConnection = null;
			URL url = new URL(item.downUrl);
			urlConnection = (HttpURLConnection) url.openConnection();

			urlConnection.setDoInput(true);
			urlConnection.connect();
			is = urlConnection.getInputStream();
			fos = new FileOutputStream(new File(path));

			long length = urlConnection.getContentLength();

			int read;
			long count = 0;
			int precent = 0;

			byte[] bt = new byte[1024];
			while ((read = is.read(bt)) > 0)
			{
				fos.write(bt, 0, read);
				count += read;
				precent = (int) (((double) count / length) * 100);

				// 每下载完成5%就通知任务栏进行修改下载进度
				if (precent - download_precent >= 1)
				{
					download_precent = precent;
					showMsg(download_precent, 1);
					sleep();
				}
			}
		}
		finally
		{
			if (fos != null)
			{
				fos.close();
			}
			if (is != null)
			{
				is.close();
			}
			unGzip(path);
		}
	}

	private void sleep()
	{
		try
		{
			Thread.sleep(50);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private void unGzip(String pathIn)
	{
		String pathOut = "";
		pathOut = pathIn.replace(".gzip", "");
		try
		{
			GzipHelper.unGzip(pathIn, pathOut);
			delGzip(pathIn);
		}
		catch (Exception e)
		{
			renameTo(pathIn, pathOut);
		}
	}

	private void delGzip(String pathIn)
	{
		File file = new File(pathIn);
		file.delete();
	}

	private void renameTo(String pathIn, String pathOut)
	{
		File fileIn = new File(pathIn);
		File fileOut = new File(pathOut);
		fileIn.renameTo(fileOut);
	}

	private String getRootFolder(UpdateItem item)
	{
		String path = PathManager.startUpPath;
		path = path + "/update";
		PathManager.mkdirs(path);

		if (!TextUtils.isEmpty(item.clientSubFolder))
		{
			path = path + "/" + item.clientSubFolder.replace('\\', '/');
			PathManager.mkdirs(path);
		}

		if (item.fileName.indexOf("apk") >= 0)
		{
			String appName = mContext.getResources().getString(R.string.app_name);
			item.path = path + "/" + appName + "_" + item.fileName;
			path = item.path + ".gzip";
		}
		else
		{
			path = path + "/" + item.fileName + ".gzip";
		}
		return path;
	}

	@Override
	public void onCancel(DialogInterface dialog)
	{
		isCancel = true;
	}
}
