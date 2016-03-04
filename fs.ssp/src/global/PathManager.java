package global;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import topevery.android.core.StringCollection;
import topevery.android.framework.utils.TextUtils;
import android.content.Context;
import android.os.Environment;

public final class PathManager
{
	/** 佛山工作台 */
	private static String appDomainPath = "/topevery/foshan_ssp/";

	public static String sdcard = "";
	public static String startUpPath = null;

	public static void onCreate(Context context)
	{
		sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();

		startUpPath = sdcard + appDomainPath;
		mkdirs(startUpPath);
	}

	public static String existsDomain(String appDomainPath, StringCollection cardCollection)
	{
		String strReturn = "";

		for (String item : cardCollection)
		{
			File file = new File(item + appDomainPath);
			if (file.exists())
			{
				return item;
			}
		}

		return strReturn;
	}

	public static String getConfigPath()
	{
		return startUpPath + "/interface.dll";
	}

	public static String getDBPath()
	{
		return startUpPath + "/Files/t_um.db";
	}

	public static String getStartUp()
	{
		return startUpPath;
	}

	public static void setStartUp(String path)
	{
		startUpPath = path;
	}

	public static String getFiles()
	{
		String path = getStartUp() + "/Files/";
		mkdirs(path);
		return path;
	}

	public static String getUpdate()
	{
		String path = getStartUp() + "/update/";
		mkdirs(path);
		return path;
	}

	public static String dirError()
	{
		String path = null;
		path = getStartUp() + "/Files/log/";
		mkdirs(path);
		return path;
	}

	/**
	 * 应用程序更新下载目录
	 * 
	 * @return
	 */
	public static String getUpDownPath()
	{
		String path = getStartUp() + "/update/";
		mkdirs(path);
		return path;
	}

	// / <summary>
	// / 临时目录;
	// / </summary>
	// / <returns></returns>
	public static String getTempPath()
	{
		String path = getStartUp() + "/Files/Temp";
		mkdirs(path);
		return path;
	}

	public static void clearTemp()
	{
		try
		{
			String path = getTempPath();
			File file = new File(path);
			File[] files = file.listFiles();
			if (files != null && files.length > 0)
			{
				for (File item : files)
				{
					item.delete();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void clearPhotosTemp()
	{
		try
		{
			String path = getPhotosTemp();
			File file = new File(path);
			File[] files = file.listFiles();
			if (files != null && files.length > 0)
			{
				for (File item : files)
				{
					item.delete();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static String getPhotosTemp()
	{
		String path = sdcard + "/Topevery/TempPhotos/";
		mkdirs(path);
		return path;
	}

	public static String getBaseDataPath()
	{
		String path = getStartUp() + "/Files/CacheFiles/Data";
		mkdirs(path);
		return path;
	}

	public static String getSpecialBaseDataPath()
	{
		String path = getStartUp() + "/Files/CacheFiles/Special";
		mkdirs(path);
		return path;
	}

	public static void delete(String path)
	{
		delete(new File(path));
	}

	public static void delete(File file)
	{
		try
		{
			if (file != null && file.exists())
			{
				file.delete();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static boolean copy(File fromFile, File toFile)
	{
		boolean result = false;

		try
		{
			FileInputStream fosfrom = new FileInputStream(fromFile);
			java.io.FileOutputStream fosto = new FileOutputStream(toFile);
			byte bt[] = new byte[1024];
			int c;
			while ((c = fosfrom.read(bt)) > 0)
			{
				fosto.write(bt, 0, c); // 将内容写到新文件当中
			}
			// 关闭数据流
			fosfrom.close();
			fosto.close();
			result = true;
		}
		catch (Exception e)
		{
			result = false;
		}
		return result;
	}

	public static void mkdirs(String path)
	{
		if (!TextUtils.isEmpty(path))
		{
			mkdirs(new File(path));
		}
	}

	public static void mkdirs(File file)
	{
		try
		{
			if (file != null && !file.exists())
			{
				file.mkdirs();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static boolean exists(String path)
	{
		if (!TextUtils.isEmpty(path))
		{
			return exists(new File(path));
		}
		return false;
	}

	public static boolean exists(File file)
	{
		if (file != null)
		{
			return file.exists();
		}
		return false;
	}

	public static void clearSub(String path)
	{
		clearSub(new File(path));
	}

	public static void clearSub(File file)
	{
		if (file.isDirectory())
		{
			File[] files = file.listFiles();
			if (files != null && files.length > 0)
			{
				for (File f : files)
				{
					clearSub(f);
				}
			}
		}
	}
}
