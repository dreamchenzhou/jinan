package topevery.um.com.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import topevery.android.framework.utils.TextUtils;
import topevery.android.utils.TdKeyValue;
import topevery.android.utils.TdKeyValueList;
import android.content.ContentValues;
import android.database.Cursor;

public class DatabaseCaseTime
{
	private static final String TABLE_NAME = "case_time";
	private static final SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yy");

	static
	{
		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (fid INTEGER PRIMARY KEY,evtCode VARCHAR,dateTime VARCHAR);";

		Database.createTable(sql);
	}

	public static void clear()
	{
		Database.clear(TABLE_NAME);
	}

	public static TdKeyValueList getValue()
	{
		TdKeyValueList result = new TdKeyValueList();
		try
		{
			String selection = null;
			String[] selectionArgs = null;
			String groupBy = null;
			String having = null;
			String orderBy = "fid desc";

			String[] columns = { "evtCode", "dateTime" };
			Cursor cursor = Database.query(TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
			if (cursor != null && cursor.getCount() > 0)
			{
				cursor.moveToFirst();
				while (cursor.getPosition() != cursor.getCount())
				{
					TdKeyValue item = new TdKeyValue();
					item.key = cursor.getString(0);
					item.value = cursor.getString(1);
					result.add(item);
					cursor.moveToNext();
				}

				cursor.close();
				cursor = null;
			}
		}
		catch (Exception e)
		{
			result.clear();
			e.printStackTrace();
		}
		return result;
	}

	public static void insert(String evtCode)
	{
		try
		{
			if (!TextUtils.isEmpty(evtCode))
			{
				ContentValues values = new ContentValues();
				values.put("evtCode", evtCode);
				values.put("dateTime", getDateTime());
				Database.insert(TABLE_NAME, values);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static String getDateTime()
	{
		Date date = Calendar.getInstance().getTime();
		return formatter.format(date);
	}

	public static void delete(String evtCode)
	{
		try
		{
			String whereClause = "evtCode =?";
			String[] whereArgs = { evtCode };
			Database.delete(TABLE_NAME, whereClause, whereArgs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
