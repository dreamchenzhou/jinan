package topevery.um.com.data;

import java.text.SimpleDateFormat;

import topevery.um.net.newbean.EvtPara;
import topevery.um.net.newbean.EvtRes;
import topevery.um.net.newbean.EvtResList;
import topevery.um.net.newbean.UserCache;
import android.R.integer;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;

/**
 * 
 * 历史记录
 */
public class DatabaseEvtRes
{
	private static final String TABLE_NAME = "EvtRes";

	static
	{
		// Database.dropTableByName(TABLE_NAME);
		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (fid INTEGER PRIMARY KEY,evtCode VARCHAR,caseAccept VARCHAR,caseType VARCHAR,linkPhone VARCHAR," + "evtPos VARCHAR,evtDesc VARCHAR,evtResult VARCHAR,datetime VARCHAR,userid integer,username VARCHAR);";
		Database.createTable(sql);

		// if (!exitColumn("datetime"))
		// {
		// alterTable();
		// }
	}

	public static void clear()
	{
		Database.clear(TABLE_NAME);
	}

	public static void alterTable()
	{
		String sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN datetime VARCHAR";
		try
		{
			Database.execSQL(sql);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

	public static boolean exitColumn(String columnName)
	{
		boolean exit = false;
		try
		{
			String[] columns = { columnName };
			Database.query(TABLE_NAME, columns, null, null, null, null, null);
			exit = true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return exit;
	}

	public static EvtResList getValue(CaseType aType,EvtPara para)
	{
		EvtResList result = new EvtResList();
		if(para.getUserID()==0){
			return result;
		}
		try
		{
			String[] columns = { "evtCode", "caseAccept", "caseType", "linkPhone", "evtPos", "evtDesc", "evtResult", "datetime","userid","username" };
			String sql = String.format("select %s from %s where caseType=%s and userid=%s order by fid desc Limit %s Offset %s", 
					columns,TABLE_NAME, aType.toString(),String.valueOf(para.getUserID()),para.pageIndex, 
					para.pageSize*(para.pageIndex-1));
			Cursor cursor = Database.query(sql);
			if (cursor != null && cursor.getCount() > 0)
			{
				cursor.moveToFirst();
				while (cursor.getPosition() != cursor.getCount())
				{
					EvtRes item = new EvtRes();
					item.setEvtCode(cursor.getString(0));
					item.setCaseAccept( CaseAccept.valueOf(cursor.getString(1)));
					item.setCaseType(CaseType.valueOf(cursor.getString(2)));
					item.getEvtPara().setLinkman(cursor.getString(3));
					item.getEvtPara().setEvtPos(cursor.getString(4));
					item.getEvtPara().setEvtDesc(cursor.getString(5));
					item.getEvtPara().setEvtResult(cursor.getString(6));
					item.setDatetime(cursor.getString(7));
					item.getEvtPara().setUserID(cursor.getInt(8));
					item.getEvtPara().setUserName(cursor.getString(9));
					result.add(item);
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

		return result;

	}

	/**
	 * 获取该用户下的所有案件
	 * @param aType
	 * @param userid
	 * @return
	 */
	public static EvtResList getValue(CaseType aType,int userid)
	{
		EvtResList result = new EvtResList();
		if(userid==0){
			return result;
		}
		try
		{
			String[] columns = { "evtCode", "caseAccept", "caseType", "linkPhone", "evtPos", "evtDesc", "evtResult", "datetime","userid","username" };
			String sql = String.format("select %s from %s where caseType=%s and userid=%s order by fid desc ", 
					columns,TABLE_NAME, aType.toString(),String.valueOf(userid));
			Cursor cursor = Database.query(sql);
			if (cursor != null && cursor.getCount() > 0)
			{
				cursor.moveToFirst();
				while (cursor.getPosition() != cursor.getCount())
				{
					EvtRes item = new EvtRes();
					item.setEvtCode(cursor.getString(0));
					item.setCaseAccept( CaseAccept.valueOf(cursor.getString(1)));
					item.setCaseType(CaseType.valueOf(cursor.getString(2)));
					item.getEvtPara().setLinkman(cursor.getString(3));
					item.getEvtPara().setEvtPos(cursor.getString(4));
					item.getEvtPara().setEvtDesc(cursor.getString(5));
					item.getEvtPara().setEvtResult(cursor.getString(6));
					item.setDatetime(cursor.getString(7));
					item.getEvtPara().setUserID(cursor.getInt(8));
					item.getEvtPara().setUserName(cursor.getString(9));
					result.add(item);
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

		return result;

	}
	public static EvtRes getValue(String evtCode,int userid)
	{
		EvtRes item = new EvtRes();
		if(userid==0){
			return item;
		}
		try
		{
			String selection = "evtCode=? and userid=?";
			String[] selectionArgs = { evtCode,String.valueOf(userid) };
			String groupBy = null;
			String having = null;
			String orderBy = "fid desc";

			String[] columns = { "evtCode", "caseAccept", "caseType", "linkPhone", "evtPos", "evtDesc", "evtResult", "datetime","userid","username" };
			Cursor cursor = Database.query(TABLE_NAME, columns, selection, selectionArgs, groupBy, having, orderBy);
			if (cursor != null && cursor.getCount() > 0)
			{
				cursor.moveToFirst();
				while (cursor.getPosition() != cursor.getCount())
				{
					item.setEvtCode(cursor.getString(0));
					item.setCaseAccept( CaseAccept.valueOf(cursor.getString(1)));
					item.setCaseType(CaseType.valueOf(cursor.getString(2)));
					item.getEvtPara().setLinkman(cursor.getString(3));
					item.getEvtPara().setEvtPos(cursor.getString(4));
					item.getEvtPara().setEvtDesc(cursor.getString(5));
					item.getEvtPara().setEvtResult(cursor.getString(6));
					item.setDatetime(cursor.getString(7));
					item.getEvtPara().setUserID(cursor.getInt(8));
					item.getEvtPara().setUserName(cursor.getString(9));
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

		return item;

	}

	public static void insert(EvtRes item,int userid)
	{
		if(userid==0){
			return;
		}
		try
		{
			if (item != null)
			{
				ContentValues values = new ContentValues();
				values.put("evtCode", item.getEvtCode());
				values.put("caseAccept", item.getCaseAccept().toString());
				values.put("caseType", item.getCaseType().toString());
				values.put("linkPhone", item.getEvtPara().getLinkPhone());
				values.put("evtPos", item.getEvtPara().getEvtPos());
				values.put("evtDesc", item.getEvtPara().getEvtDesc());
				values.put("evtResult", item.getEvtPara().getEvtResult());
				values.put("datetime", getTime());

				Database.insert(TABLE_NAME, values);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void delete(String evtCode,int userid)
	{
		if(userid==0){
			return ;
		}
		try
		{
			String whereClause = "evtCode =? and userid = ?";
			String[] whereArgs = { evtCode ,String.valueOf(userid)};
			Database.delete(TABLE_NAME, whereClause, whereArgs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void update(String evtCode, CaseAccept accept,
			String evtResult,int userid)
	{
		if(userid==0){
			return ;
		}
		try
		{
			String whereClause = "evtCode=? and userid = ?";
			String[] whereArgs = { evtCode ,String.valueOf(userid)};

			ContentValues values = new ContentValues();
			values.put("caseAccept", accept.toString());
			values.put("evtResult", evtResult);

			Database.updateItem(TABLE_NAME, values, whereClause, whereArgs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void update(String evtCode, CaseAccept accept,int userid)
	{
		if(userid ==0){
			return ;
		}
		try
		{
			String whereClause = "evtCode=? and userid=?";
			String[] whereArgs = { evtCode ,String.valueOf(userid) };

			ContentValues values = new ContentValues();
			values.put("caseAccept", accept.toString());

			Database.updateItem(TABLE_NAME, values, whereClause, whereArgs);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static String getTime()
	{
		SimpleDateFormat sFormat = new SimpleDateFormat("MM-dd-yy_HHmmss");
		String date = sFormat.format(new java.util.Date());
		String time = date;
		return time;

	}
}
