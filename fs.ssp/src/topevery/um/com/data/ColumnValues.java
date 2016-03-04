package topevery.um.com.data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import android.database.Cursor;
import android.text.TextUtils;

@SuppressWarnings("serial")
public class ColumnValues implements Serializable
{
	/**
	 * String sql = "CREATE TABLE IF NOT EXISTS " + tableName +(fid INTEGER
	 * PRIMARY KEY,title VARCHAR);"
	 */

	public static class ColumnType
	{
		public static final String Int16 = "Short";
		public static final String Int32 = "INTEGER";
		public static final String Int64 = "LONG";

		public static final String String = "VARCHAR";
		public static final String DateTime = "datetime";
		public static final String UUID = "uniqueidentifier";
		public static final String Double = "DOUBLE";
		public static final String Float = "FlOAT";
		public static final String Boolean = "VARCHAR";
	}

	static final SimpleDateFormat SDF_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static final String dateTimeEmpty = "0000-00-00 00:00:00";
	static final String uuidEmptyString = "00000000-0000-0000-0000-000000000000";
	static final java.util.UUID uuidEmpty = java.util.UUID.fromString("00000000-0000-0000-0000-000000000000");
	static final String formatCreate = "CREATE TABLE IF NOT EXISTS %s  (%s);";

	String tableName;
	ArrayList<ColumnValue> values = new ArrayList<ColumnValue>();

	/**
	 * 
	 * true自动创建
	 * 
	 * createDate(DateTime),createId(UUID),
	 * 
	 * lastUpdateDate(DateTime), lastUpdateId(UUID),
	 * 
	 * dbState(int)
	 */
	String createTableSqlText(ArrayList<ColumnValue> values, boolean defalut)
	{
		if (defalut)
		{
			put("createDate", String.format("%s %s", ColumnType.DateTime, "NULL"));
			put("createId", String.format("%s %s", ColumnType.UUID, "NULL"));
			put("lastUpdateDate", String.format("%s %s", ColumnType.DateTime, "NULL"));
			put("lastUpdateId", String.format("%s %s", ColumnType.UUID, "NULL"));
			put("dbState", String.format("%s %s", ColumnType.Int32, "NULL"));
		}

		String sqlText = "";
		int size = values.size();

		StringBuffer sb = new StringBuffer();

		for (int i = 1; i <= size; i++)
		{
			ColumnValue item = values.get(i - 1);
			if (i == size)
			{
				addColumn(sb, item, true);
			}
			else
			{
				addColumn(sb, item, false);
			}
		}

		sqlText = java.lang.String.format(formatCreate, tableName, sb.toString());

		return sqlText;
	}

	/**
	 * 
	 * true自动创建
	 * 
	 * createDate(DateTime),createId(UUID),
	 * 
	 * lastUpdateDate(DateTime), lastUpdateId(UUID),
	 * 
	 * dbState(int)
	 */
	public String createTableSqlText(boolean defalut)
	{
		return createTableSqlText(values, defalut);
	}

	public String createTableSqlText()
	{
		return createTableSqlText(false);
	}

	void addColumn(StringBuffer sb, ColumnValue item, boolean isLast)
	{
		sb.append(item.columnName).append(" ");
		sb.append(item.columnType).append(" ");

		if (item.isPrimaryKey)
		{
			sb.append("PRIMARY KEY");
		}

		if (!isLast)
		{
			sb.append(",");
		}
	}

	public ColumnValues()
	{

	}

	public ColumnValues(String tableName)
	{
		this.tableName = tableName;
	}

	public String getTableName()
	{
		return tableName;
	}

	public void setTableName(String tableName)
	{
		this.tableName = tableName;
	}

	public void put(String fieldName, String fieldTypeName)
	{
		put(fieldName, fieldTypeName, false);
	}

	public void put(String columnName, String columnType, boolean isPrimaryKey)
	{
		ColumnValue item = new ColumnValue(columnName, columnType, isPrimaryKey);
		values.add(item);
	}

	/**
	 * 创建自增键fid
	 */
	public void putPrimaryKey()
	{
		ColumnValue item = new ColumnValue("fid", ColumnType.Int32, true);
		values.add(item);
	}

	public void clear()
	{
		values.clear();
	}

	public static boolean validateColumn(Cursor cursor, int columnIndex)
	{
		return (cursor != null && columnIndex >= 0 && !cursor.isNull(columnIndex));
	}

	public static short getInt16(Cursor cursor, int columnIndex)
	{
		if (validateColumn(cursor, columnIndex))
		{
			return cursor.getShort(columnIndex);
		}
		return java.lang.Short.MIN_VALUE;
	}

	public static int getInt32(Cursor cursor, int columnIndex)
	{
		if (validateColumn(cursor, columnIndex))
		{
			return cursor.getInt(columnIndex);
		}
		return java.lang.Integer.MIN_VALUE;
	}

	public static long getInt64(Cursor cursor, int columnIndex)
	{
		if (validateColumn(cursor, columnIndex))
		{
			return cursor.getLong(columnIndex);
		}
		return java.lang.Long.MIN_VALUE;
	}

	public static double getDouble(Cursor cursor, int columnIndex)
	{
		if (validateColumn(cursor, columnIndex))
		{
			return cursor.getDouble(columnIndex);
		}
		return java.lang.Double.MIN_VALUE;
	}

	public static float getFloat(Cursor cursor, int columnIndex)
	{
		if (validateColumn(cursor, columnIndex))
		{
			return cursor.getFloat(columnIndex);
		}
		return java.lang.Float.MIN_VALUE;
	}

	public static String getString(Cursor cursor, int columnIndex)
	{
		if (validateColumn(cursor, columnIndex))
		{
			return cursor.getString(columnIndex);
		}
		return "";
	}

	public static java.util.UUID getUUID(Cursor cursor, int columnIndex)
	{
		java.util.UUID uuid = java.util.UUID.fromString("00000000-0000-0000-0000-000000000000");

		String uuidtString = "";

		if (validateColumn(cursor, columnIndex))
		{
			uuidtString = cursor.getString(columnIndex);
		}
		try
		{
			if (!TextUtils.isEmpty(uuidtString))
			{
				uuid = java.util.UUID.fromString(uuidtString);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return uuid;
	}

	public static Date getDate(Cursor cursor, int columnIndex)
	{
		Date date = null;

		try
		{
			date = SDF_FORMAT.parse(dateTimeEmpty);

			if (validateColumn(cursor, columnIndex))
			{
				String value = cursor.getString(columnIndex);
				date = SDF_FORMAT.parse(value);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return date;
	}

	public static Boolean getBoolean(Cursor cursor, int columnIndex)
	{
		Boolean reslut = false;

		try
		{
			if (validateColumn(cursor, columnIndex))
			{
				String value = cursor.getString(columnIndex);
				reslut = Boolean.valueOf(value);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return reslut;
	}

	/**
	 * empty 0000-00-00 00:00:00
	 */
	public static String getDateString(Date date)
	{
		if (date != null)
		{
			return SDF_FORMAT.format(date);
		}
		return "";
	}

	/**
	 * empty 00000000-0000-0000-0000-000000000000
	 */
	public static String getUUIDString(UUID uuid)
	{
		if (uuid != null)
		{
			return uuid.toString();
		}
		return "00000000-0000-0000-0000-000000000000";
	}

	public static String getBooleanString(Boolean value)
	{
		if (value != null)
		{
			return Boolean.toString(value);
		}
		return "";
	}

	class ColumnValue implements Serializable
	{
		/**
		 * 列名
		 */
		public String columnName = "";
		/**
		 * 列类型
		 */
		public String columnType = "";
		/**
		 * 是否自增键，INTEGER类型
		 */
		public boolean isPrimaryKey = false;

		public ColumnValue()
		{

		}

		public ColumnValue(String columnName, String columnType)
		{
			this(columnName, columnType, false);
		}

		public ColumnValue(String columnName, String columnType,
				boolean isPrimaryKey)
		{
			this.columnName = columnName;
			this.columnType = columnType;
			this.isPrimaryKey = isPrimaryKey;
		}
	}
}