package topevery.um.com;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import topevery.android.core.GzipHelper;
import topevery.android.core.MsgBox;
import topevery.um.com.utils.PathUtils;
import topevery.um.net.update.GetUpdatePara;
import topevery.um.net.update.GetUpdateRes;
import topevery.um.net.update.UpdateHandle;
import topevery.um.net.update.UpdateItem;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

public class UpSysDialog extends ProgressDialog
{
	private static final Object lockVaue = new Object();
	public static String versionSubType = "sui_shou_pai";
	public static String clientVersion = "1.0";
	public static int packetType = 0;
	static String Text_New = "当前已经是最新版本。";
	static String Text_Net = "网络故障，请稍后重试。";
	static String Text_End = "更新完毕。";
	static Context mContext = null;

	public UpSysDialog(Context context)
	{
		super(context);

		this.setTitle("系统升级");
		this.setMessage("请稍候...");
		this.setCancelable(false);
		this.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	}

	public static void update(Context context)
	{
		synchronized (lockVaue)
		{
			mContext = context;
			UpSysDialog dialog = new UpSysDialog(mContext);
			dialog.show();
		}
	}

	public static void updatMust(final Context context, String versionName)
	{
		android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(context);
		dialog.setTitle(MsgBox.title);
		dialog.setCancelable(false);
		dialog.setMessage("系统检测到有最新版本[" + versionName + "]，必须完成在线升级!");
		dialog.setPositiveButton("Ok", new android.content.DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					update(context);
				}
			});
		dialog.show();
	}

	public static void updateYesOrNo(final Context context, String versionName)
	{
		String text = "系统检测到有最新版本[" + versionName + "]，是否完成在线升级!";
		MsgBox.askYesNo(context, text, new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					dialog.dismiss();
					update(context);
				}
			}, new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					dialog.dismiss();
				}
			});
	}

	@Override
	public void show()
	{
		super.show();

		new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					downFile();
				}
			}).start();
	}

	private void downFile()
	{
		try
		{
			GetUpdatePara para = new GetUpdatePara();
			para.clientVersion = UpSysDialog.clientVersion;
			para.versionSubType = UpSysDialog.versionSubType;
			para.packetType = UpSysDialog.packetType;
			para.passportId = UUID.randomUUID();
			para.userId = UUID.randomUUID();

			GetUpdateRes res = UpdateHandle.GetServerVersion(para);
			if (res != null)
			{
				if (res.isSuccess)
				{
					if (res.datas != null && res.datas.size() > 0)
					{
						String apkName = "";

						for (UpdateItem item : res.datas)
						{
							downFile(item);
							if (item.fileName.indexOf("apk") > 0)
							{
								apkName = item.fileName;
							}
						}

						this.dismiss();

						if (res.isRestartApp)
						{
							String apkFile = PathUtils.startUp + "/update/" + apkName;
							File file = new File(apkFile);
							if (file.exists())
							{
								Intent intent = new Intent(Intent.ACTION_VIEW);
								intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
								mContext.startActivity(intent);
							}
							else
							{
								showMsg("安装程序不存在。");
							}
						}
					}
					else
					{
						showMsg(Text_New);
					}
				}
				else
				{
					showMsg(res.errorMessage);
				}
			}
			else
			{
				showMsg(Text_Net);
			}
		}
		catch (Exception e)
		{
			showMsg(e.toString());
		}
	}

	private void downFile(UpdateItem item) throws Exception
	{
		String path = "";
		InputStream is = null;
		FileOutputStream fos = null;

		try
		{
			path = getRootFolder(item);

			HttpURLConnection urlConnection = null;
			URL url = new URL(item.downUrl);
			urlConnection = (HttpURLConnection) url.openConnection();

			urlConnection.setDoInput(true);
			urlConnection.connect();
			is = urlConnection.getInputStream();
			fos = new FileOutputStream(new File(path));

			byte[] bt = new byte[1024];
			int i = 0;
			while ((i = is.read(bt)) > 0)
			{
				fos.write(bt, 0, i);
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
		String path = PathUtils.startUp;
		path = path + "/update";
		PathUtils.checkPath(path);

		if (!TextUtils.isEmpty(item.clientSubFolder))
		{
			path = path + "/" + item.clientSubFolder.replace('\\', '/');
			PathUtils.checkPath(path);
		}

		path = path + "/" + item.fileName + ".gzip";

		return path;
	}

	private void showMsg(String text)
	{
		Message msg = new Message();
		msg.obj = text;
		handler.sendMessage(msg);
	}

	private Handler handler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				super.handleMessage(msg);
				UpSysDialog.this.dismiss();
				MsgBox.show(mContext, msg.obj.toString());
			}
		};

	public static class Config
	{
		public static void setUpdate()
		{
			try
			{
				String root = PathUtils.startUp;

				String update = root + "/update";
				File file = new File(update);
				if (file.exists())
				{
					renameTo(file);
					// deleteUpdate(file);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		public static void deleteUpdate(File item)
		{
			File[] files = item.listFiles();
			if (files != null && files.length > 0)
			{
				for (int i = 0; i < files.length; i++)
				{
					File file = files[i];
					if (file.isDirectory())
					{
						deleteUpdate(file);
					}
					else
					{
						file.delete();
					}
				}
			}
			else
			{
				item.delete();
			}
		}

		private static void renameTo(File item)
		{
			File[] files = item.listFiles();
			if (files != null && files.length > 0)
			{
				for (int i = 0; i < files.length; i++)
				{
					File file = files[i];
					if (file.isDirectory())
					{
						renameTo(file);
					}
					else
					{
						String path = file.getParent();
						// path = path.replace("/update", "");
						// PathManager.checkPath(path);

						path = file.getPath();

						if (!path.endsWith("apk"))
						{
							path = path.replace("/update", "");
							file.renameTo(new File(path));
						}
					}
				}
			}
		}
	}
}
