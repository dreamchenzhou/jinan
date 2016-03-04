package topevery.um.com.data;

import topevery.android.framework.utils.TextUtils;
import topevery.um.net.srv.FlowInfo;
import topevery.um.net.srv.FlowInfoCollection;
import android.content.ContentValues;
import android.database.Cursor;

public class DatabaseFlowInfo
{
	private static String TABLE_NAME = "flowinfo";

	static
	{
		// Database.dropTableByName(TABLE_NAME);

		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (fid INTEGER PRIMARY KEY,evtCode VARCHAR,activityName VARCHAR,operatorName VARCHAR,inDate VARCHAR," + "finishedDate VARCHAR,actUsedTime VARCHAR,limitTime VARCHAR,caseUsedDate VARCHAR,opinion VARCHAR);";
		Database.createTable(sql);
	}

	public static FlowInfoCollection getValue(String evtCode)
	{
		FlowInfoCollection fCollection = new FlowInfoCollection();
		try
		{
			String selection = "evtCode=?";
			String[] selectionArgs = { evtCode };
			String groupBy = null;
			String having = null;
			String orderBy = "fid desc";

			String[] columns = { "evtCode", "activityName", "operatorName", "inDate", "finishedDate", "actUsedTime", "limitTime", "caseUsedDate", "opinion" };
			Cursor cursor = Database.query(TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
			if (cursor != null && cursor.getCount() > 0)
			{
				cursor.moveToFirst();
				while (cursor.getPosition() != cursor.getCount())
				{
					FlowInfo item = new FlowInfo();
					item.evtCode = cursor.getString(0);
					item.activityName = cursor.getString(1);
					item.operatorName = cursor.getString(2);
					item.inDate = cursor.getString(3);
					item.finishedDate = cursor.getString(4);
					item.actUsedTime = cursor.getString(5);
					item.limitTime = cursor.getString(6);
					item.caseUsedDate = cursor.getString(7);
					item.opinion = cursor.getString(8);
					fCollection.add(item);
					cursor.moveToNext();
				}
				cursor.close();
				cursor = null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return fCollection;
	}

	public static void insert(String evtCode, FlowInfoCollection fCollection)
	{
		try
		{
			if (!TextUtils.isEmpty(evtCode) && fCollection != null && fCollection.size() > 0)
			{
				for (FlowInfo fInfo : fCollection)
				{
					ContentValues values = new ContentValues();
					values.put("evtCode", evtCode);
					values.put("activityName", fInfo.activityName);
					values.put("operatorName", fInfo.operatorName);
					values.put("inDate", fInfo.inDate);
					values.put("finishedDate", fInfo.finishedDate);
					values.put("actUsedTime", fInfo.actUsedTime);
					values.put("limitTime", fInfo.limitTime);
					values.put("caseUsedDate", fInfo.caseUsedDate);
					values.put("opinion", fInfo.opinion);

					Database.insert(TABLE_NAME, values);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
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

	public static void clear()
	{
		Database.clear(TABLE_NAME);
	}
}
