package topevery.um.com.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.UUID;

import topevery.um.com.data.Serializer;
import topevery.um.net.srv.AttachInfo;
import topevery.um.net.srv.AttachInfoCollection;
import topevery.um.net.srv.EvtPara;
import topevery.um.net.srv.EvtRes;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;

public class PathUtils
{
	public static String sdcard = "sdcard";
	public static String startUp = "";

	static
	{
		String sdstate = Environment.getExternalStorageState();
		if (sdstate.equals(Environment.MEDIA_MOUNTED))
		{
			sdcard = Environment.getExternalStorageDirectory().getPath();
		}

		startUp = sdcard + "/" + "topevery/jinan/smt";
		checkPath(startUp);
	}

	public static void testData()
	{
		for (int i = 0; i < 4; i++)
		{
			String evtCode = UUID.randomUUID().toString();

			EvtRes evtRes = new EvtRes();
			evtRes.evtCode = evtCode;
			evtRes.evtPara = new EvtPara();
			evtRes.evtPara.evtDesc = String.valueOf(i);

			try
			{
				String path = PathUtils.getEvtCodePath(evtCode);
				Serializer.writeObject(evtRes, path);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public static String getLocalNumPath()
	{
		String path = startUp + "/" + "localNum";
		checkPath(path);
		return path;
	}

	public static String getDatabasePath()
	{
		String path = startUp + "/" + "ssp.db";
		return path;
	}

	public static String getTempPath()
	{
		String path = startUp + "/Temp";
		checkPath(path);
		path = path + "/" + getTempTime();
		return path;
	}

	public static String getTemp()// 2
	{
		String path = startUp + "/Temp";
		checkPath(path);
		return path;
	}

	public static String getPhotoPath()
	{
		String path = startUp + "/" + "photos";
		checkPath(path);
		return path;
	}

	public static String getTempPhotoPath()
	{
		String path = startUp + "/" + "tempphotos";
		checkPath(path);
		return path;
	}
	public static String getSavePhotoPath(){
		String path = startUp + "/" + "savephotos";
		checkPath(path);
		return path;
	}
	
	public static String getRecordsPath()
	{
		String path = startUp + "/Records";
		checkPath(path);
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
	
	public static void clearPhoto()
	{
		String path = getPhotoPath();
		File dir = new File(path);
		File[] files = dir.listFiles();

		if (files != null)
		{
			for (File file : files)
			{
				try
				{
					file.delete();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	static AttachInfoCollection getEveList()
	{
		AttachInfoCollection objects = new AttachInfoCollection();
		String path = PathUtils.getEvtCodeListPath();
		File dir = new File(path);
		File[] files = dir.listFiles();
		if (files != null)
		{
			for (File file : files)
			{
				try
				{
					EvtRes item = (EvtRes) Serializer.readObject(file.getPath() + "/" + file.getName());
					if (item != null && item.evtPara != null && item.evtPara.attachs != null)
					{
						objects.addAll(item.evtPara.attachs);
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
		return objects;
	}

	public static void clearPhoto2()
	{
		AttachInfoCollection attachs = getEveList();

		String path = getPhotoPath();
		File dir = new File(path);
		File[] files = dir.listFiles();

		if (files != null && attachs.size() > 0)
		{
			for (File file : files)
			{
				if (!contains(file, attachs))
				{
					file.delete();
				}
			}
		}
	}

	static boolean contains(File file, AttachInfoCollection attachs)
	{
		for (AttachInfo attachInfo : attachs)
		{
			if (attachInfo.uri.equalsIgnoreCase(file.getPath()))
			{
				return true;
			}
		}
		return false;
	}

	public static void clearEvtCode()// 暂时没用
	{
		String path = getEvtCodeListPath();
		File dir = new File(path);
		File[] files = dir.listFiles();
		if (files != null)
		{
			for (File file : files)
			{
				try
				{
					new File(file.getPath() + "/" + file.getName()).delete();
					file.delete();
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public static String getEvtCodeListPath()// 暂时没用
	{
		String path = startUp + "/" + "EvtInfo";
		checkPath(path);
		return path;
	}

	public static String getEvtCodePath(String evtCode)// 暂时没用
	{
		String path = getEvtCodeListPath() + "/" + getTime();// + "/" + evtCode;
		checkPath(path);
		path = path + "/" + evtCode;
		return path;
	}

	public static String getTime()
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String data = simpleDateFormat.format(new java.util.Date());

		String name = data;
		return name;

	}

	public static String getTempTime()
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yy_HHmmss");
		String data = simpleDateFormat.format(new java.util.Date());

		String name = data;
		return name;

	}

	public static String getImageName()
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String date = simpleDateFormat.format(new java.util.Date());

		String name = getPhotoPath() + "/" + date + ".jpg";
		return name;
	}

	public static void checkPath(String path)
	{
		File file = new File(path);
		if (!file.exists())
		{
			file.mkdirs();
		}
	}

	public static BitmapDrawable getDrawable(String path, int inSampleSize)
	{
		Options opts = new Options();
		opts.inSampleSize = inSampleSize;

		Bitmap bm = BitmapFactory.decodeFile(path, opts);
		BitmapDrawable bitmapDrawable = new BitmapDrawable(bm);

		return bitmapDrawable;
	}

	public static Bitmap getBitmap(String path, int inSampleSize)
	{
		Options opts = new Options();
		opts.inSampleSize = inSampleSize;

		Bitmap bm = BitmapFactory.decodeFile(path, opts);
		return bm;
	}

}
