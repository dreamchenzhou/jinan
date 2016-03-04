package topevery.um.com.data;

import topevery.android.framework.utils.TextUtils;
import android.content.ContentValues;
import android.database.Cursor;

public class DatabaseLocalNum
{
	private static final String TABLE_NAME = "t_local_num";

	static
	{
		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (fid INTEGER PRIMARY KEY,localNum VARCHAR);";

		Database.createTable(sql);
	}

	public static void clear()
	{
		Database.clear(TABLE_NAME);
	}

	public static String getLocalNum()
	{
		String localNum = "";
		Cursor cursor = null;
		try
		{
			String selection = null;
			String[] selectionArgs = null;
			String groupBy = null;
			String having = null;
			String orderBy = null;

			String[] columns = { "localNum" };
			cursor = Database.query(TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
			if (cursor != null && cursor.getCount() > 0)
			{
				cursor.moveToFirst();
				while (cursor.getPosition() != cursor.getCount())
				{
					localNum = cursor.getString(0);
					break;
					// cursor.moveToNext();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (cursor != null)
			{
				cursor.close();
				cursor = null;
			}
		}
		return localNum;
	}

	public static void insert(String localNum)
	{
		try
		{
			clear();

			if (!TextUtils.isEmpty(localNum))
			{
				ContentValues values = new ContentValues();
				values.put("localNum", localNum);
				Database.insert(TABLE_NAME, values);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void delete(String localNum)
	{
		try
		{
			String whereClause = "localNum =?";
			String[] whereArgs = { localNum };
			Database.delete(TABLE_NAME, whereClause, whereArgs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
