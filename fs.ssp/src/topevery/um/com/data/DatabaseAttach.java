package topevery.um.com.data;

import java.text.SimpleDateFormat;
import java.util.UUID;

import topevery.android.framework.utils.TextUtils;
import topevery.um.net.newbean.AttachInfo;
import topevery.um.net.newbean.AttachInfoCollection;
import android.content.ContentValues;
import android.database.Cursor;

public class DatabaseAttach
{
	private static final String TABLE_NAME = "t_evt_attach";
	private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * evtCode 图片对应的案件号；
	 * fileName 图片的名称；
	 * uri 图片物理路径；
	 * uploaded 0 为没有上传，1为已上传至服务器；
	 * delete 0 为没有删除本地路径，1为已经删除本地路径；
	 */
	static
	{
		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (id INTEGER PRIMARY KEY,evtCode VARCHAR,fileName VARCHAR,uri VARCHAR,fid VARCHAR ,uploaded INTEGER,delete INTEGER);";
		// ALTER TABLE ADD COLUMN
		Database.createTable(sql);

		// ColumnValues columnValues = new ColumnValues(TABLE_NAME);
		// columnValues.put("fid", ColumnType.Int32, true);
		// columnValues.put("fileName", ColumnType.String);
		//
		// String sql = columnValues.createTableSqlText();
		// Database.createTable(sql);
	}

	public static void clear()
	{
		Database.clear(TABLE_NAME);
	}

	public static AttachInfoCollection getValue(String eveCode)
	{
		AttachInfoCollection result = new AttachInfoCollection();
		try
		{
			String selection = "evtCode =?";
			String[] selectionArgs = { eveCode };
			String groupBy = null;
			String having = null;
			String orderBy = "id desc";

			String[] columns = { "evtCode", "fileName", "uri" };
			Cursor cursor = Database.query(TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
			if (cursor != null && cursor.getCount() > 0)
			{
				cursor.moveToFirst();
				while (cursor.getPosition() != cursor.getCount())
				{
					AttachInfo item = new AttachInfo();
					item.setName(cursor.getString(2));
					item.setUri(cursor.getString(3));
					item.setId(UUID.fromString(cursor.getString(4)));
					item.setUploaded(cursor.getInt(5)==1?true:false);
					// Date date = ColumnValues.getDate(cursor, 2);
					// item.createDate = DateTime.fromJavaDate(date);

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
	

	public static void insert(String evtCode, AttachInfoCollection attachs)
	{
		try
		{
			if (!TextUtils.isEmpty(evtCode) && attachs != null && attachs.size() > 0)
			{
				for (AttachInfo attachInfo : attachs)
				{
					ContentValues values = new ContentValues();
					values.put("evtCode", evtCode);
					values.put("fileName", attachInfo.getName());
					values.put("uri", attachInfo.getUri());
					values.put("fid", attachInfo.getId().toString());
					values.put("uploaded", attachInfo.isUploaded()==true?1:0);
					// Date date = attachInfo.createDate.toJavaDate();
					// values.put("dateTime", ColumnValues.getDateString(date));

					Database.insert(TABLE_NAME, values);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

//	 private static String getDateTime(AttachInfo attachInfo)
//	 {
//	 Date date = attachInfo.getCreateDate();
//	 return formatter.format(date);
//	 }

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
