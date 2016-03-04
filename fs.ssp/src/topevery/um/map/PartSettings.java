package topevery.um.map;

import java.io.File;

/**
 * 龙华新区：龙华，大浪，民治，观澜
 * 
 * @author martin.zheng
 * 
 */
public final class PartSettings
{
	public static String groupName = "";

	private static final String RegionName = "佛山";

	/**
	 * 2m
	 */
	private static final long db_size = 2 * 1020 * 1024;

	public static String MapRootPath = "";

	public static String getPartInformationPath()
	{
		return MapRootPath + "/Map/Infomations/dgumV2Pda/szPart";
	}

	public static String getPartImagePath()
	{
		String path = getPartInformationPath();
		return path + "/Images";
	}

	public static String getPartMappingFileName()
	{
		String fileName = getPartInformationPath() + "/partMapping.xml";
		return fileName;
	}

	public static String getGridDataPath()
	{
		String path = MapRootPath + "/Map/Data/dgumV2Pda/sz/柳州/";
		// path = path +
		// topevery.um.common.setting.Environments.CurUser.streetName;
		// path = path + "佛山";
		return path;
	}

	public static String getPartDataPath()
	{
		String path = MapRootPath + "/Map/Data/dgumV2Pda/szPart/柳州/";
		// path = path +
		// topevery.um.common.setting.Environments.CurUser.streetName;
		// path = path + "佛山";
		return path;
	}

	public static void streetNameChanged()
	{
		try
		{
			// String path = MapRootPath + "/Map/Data/dgumV2Pda/szPart/";
			// File filePath = new File(path);
			// File[] files = filePath.listFiles();
			// if (files != null && files.length == 1)
			// {
			// File fileOld = files[0];
			//
			// String nameOld = fileOld.getName();
			// if (!nameOld.equalsIgnoreCase(RegionName))
			// {
			// String pathNew = MapRootPath + "/Map/Data/dgumV2Pda/szPart/" +
			// RegionName;
			// fileOld.renameTo(new File(pathNew));
			// }
			// }
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// public static String MapRootPath = "/sdcard/topevery/许昌";

	// "Map/Images/dgumV2Pda/sz/176_120/176_120/mapImages.db";
	public static boolean isSqlite()
	{
		String path = MapRootPath + "/Map/Images/dgumV2Pda/sz/176_120/176_120/mapImages.db";
		File file = new File(path);
		if (file.exists() && file.length() >= db_size)
		{
			return true;

			// File[] files = file.listFiles();
			// if (files != null && files.length > 0)
			// {
			//
			// }
		}
		return false;
	}

	public static String getPartImagePath2()
	{
		// String path =
		// Environment.getExternalStorageDirectory().getAbsolutePath();
		// path = path + "/BaiduMapSDK/partImage";
		// PathManager.mkdirs(new File(path));
		// return path;
		return null;
	}
}
