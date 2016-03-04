package topevery.um.com.data;

import java.io.File;
import java.util.Random;

import topevery.um.com.utils.PathUtils;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class Database
{
	private static final String TABLE_NAME = "employee";
	private static SQLiteDatabase sqLiteDatabase;

	static
	{
		try
		{
			String path = PathUtils.getDatabasePath();
			File file = new File(path);
			if (!file.exists())
			{
				file.createNewFile();
			}
			sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(file, null);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void initialize()
	{

	}

	public static void beginTransaction()
	{
		if (sqLiteDatabase != null)
		{
			sqLiteDatabase.beginTransaction();
		}
	}

	public static void endTransaction()
	{
		if (sqLiteDatabase != null)
		{
			sqLiteDatabase.endTransaction();
		}
	}

	public static void setTransactionSuccessful()
	{
		if (sqLiteDatabase != null)
		{
			sqLiteDatabase.setTransactionSuccessful();
		}
	}

	public static void close()
	{
		try
		{
			if (sqLiteDatabase != null)
			{
				sqLiteDatabase.close();
				sqLiteDatabase = null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void execSQL(String sql) throws SQLException
	{
		if (sqLiteDatabase != null)
		{
			sqLiteDatabase.execSQL(sql);
		}
	}

	public static void execSQL(String sql, Object[] bindArgs) throws SQLException
	{
		if (sqLiteDatabase != null)
		{
			sqLiteDatabase.execSQL(sql, bindArgs);
		}
	}

	public static void createTable(String sql) throws SQLException
	{
		if (sqLiteDatabase != null)
		{
			sqLiteDatabase.execSQL(sql);
		}
	}

	public static void dropTable(String sql) throws SQLException
	{
		if (sqLiteDatabase != null)
		{
			sqLiteDatabase.execSQL(sql);
		}
	}

	public static void insert(String table, ContentValues values)
	{
		if (sqLiteDatabase != null)
		{
			sqLiteDatabase.insert(table, null, values);
		}
	}

	public static void insert(String sql) throws SQLException
	{
		if (sqLiteDatabase != null)
		{
			sqLiteDatabase.execSQL(sql);
		}
	}

	public static void clear(String table)
	{
		clear(table, null, null);
	}

	public static void clear(String table, String whereClause,
			String[] whereArgs)
	{
		if (sqLiteDatabase != null)
		{
			sqLiteDatabase.delete(table, whereClause, whereArgs);
		}
	}

	public static void delete(String table, String whereClause,
			String[] whereArgs) throws SQLException
	{
		if (sqLiteDatabase != null)
		{
			sqLiteDatabase.delete(table, whereClause, whereArgs);
		}
	}

	public static void updateItem(String table, ContentValues contentValues,
			String whereClause, String[] whereArgs) throws SQLException
	{
		if (sqLiteDatabase != null)
		{
			sqLiteDatabase.update(table, contentValues, whereClause, whereArgs);
		}
	}

	public static Cursor query(String table, String[] columns,
			String selection, String[] selectionArgs, String groupBy,
			String having, String orderBy) throws SQLException
	{
		if (sqLiteDatabase != null)
		{
			return sqLiteDatabase.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
		}
		return null;
	}
	
	public static Cursor query(String sql){
		if (sqLiteDatabase != null)
		{
			return sqLiteDatabase.rawQuery(sql, null);
		}
		return null;
	}

	// 创建数据表
	public static void createTable()
	{
		String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (ID INTEGER PRIMARY KEY, Name VARCHAR, Age INTEGER);";
		try
		{
			sqLiteDatabase.execSQL(sql);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

	// 插入数据
	public static void insertItem()
	{
		try
		{
			Random random = new Random();
			for (int i = 0; i < 3; i++)
			{
				String sql = "insert into " + TABLE_NAME + " (name, age) values ('name" + String.valueOf(i) + "', " + random.nextInt() + ")";
				// execSQL() - 执行指定的 sql
				sqLiteDatabase.execSQL(sql);
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

	// 删除数据
	public static void deleteItem()
	{
		try
		{
			sqLiteDatabase.delete(TABLE_NAME, " id < 999999", null);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

	// 更新数据
	public static void updateItem()
	{
		try
		{
			ContentValues values = new ContentValues();
			values.put("name", "批量更新后的名字");

			sqLiteDatabase.update(TABLE_NAME, values, "id<?", new String[] { "3" });
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

	// 查询数据
	public static void showItems()
	{
		try
		{
			StringBuilder sb = new StringBuilder();

			String[] column = { "id", "name", "age" };
			Cursor cursor = sqLiteDatabase.query(TABLE_NAME, column, null, null, null, null, null);
			Integer num = cursor.getCount();
			sb.append("共 " + Integer.toString(num) + " 条记录\n");
			cursor.moveToFirst();

			while (cursor.getPosition() != cursor.getCount())
			{
				sb.append(Integer.toString(cursor.getPosition()) + "," + String.valueOf(cursor.getString(0)) + "," + cursor.getString(1) + "," + String.valueOf(cursor.getString(2)) + "\n");
				cursor.moveToNext();
			}
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

	// 删除数据表
	public static void dropTable()
	{
		String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
		try
		{
			sqLiteDatabase.execSQL(sql);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

	public static void dropTableByName(String tableName)
	{
		String sql = "DROP TABLE IF EXISTS " + tableName;
		try
		{
			sqLiteDatabase.execSQL(sql);
		}
		catch (SQLException ex)
		{
			ex.printStackTrace();
		}
	}

}
