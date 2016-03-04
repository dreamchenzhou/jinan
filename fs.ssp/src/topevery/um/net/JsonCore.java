package topevery.um.net;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import topevery.framework.system.DateTime;
import android.annotation.SuppressLint;
import android.text.TextUtils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

@SuppressLint(
{ "DefaultLocale", "SimpleDateFormat" })
public final class JsonCore
{
	/** 空的 {@code JSON} 数据 - <code>"{}"</code> */
	public static final String EMPTY_JSON = "{}";
	/** 空的 {@code JSON} 数组(集合)数据 - {@code "[]"}。 */
	public static final String EMPTY_JSON_ARRAY = "[]";
	/** 默认的 {@code JSON} 日期/时间字段的格式化模式。 */
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

	// yyyy-MM-dd HH:mm:ss SSS

	public static final SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_PATTERN);

	public static String toString(Date date)
	{
		String str = sdf.format(date);
		return str;
	}

	public static Date toDate(String str)
	{
		// str = "2015-04-04 04:32:32";

		Date date = null;
		try
		{
			if (!TextUtils.isEmpty(str))
			{
				// if (str.startsWith("\"") && str.endsWith("\""))
				// {
				// // String[] strs = str.split("\\\"");
				// // str = strs[1];
				// str = str.replace("\"", "");
				// }

				str = str.replace("T", " ");
				str = str.replace("\"", "");
				date = sdf.parse(str);
			}
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (date == null)
			{
				date = new Date(0);
			}
		}
		return date;
	}

	private static class DateDeserializer implements JsonDeserializer<Date>
	{
		@Override
		public Date deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException
		{
			return toDate(json.toString());
		}
	}

	private static class DateTimeDeserializer implements JsonDeserializer<DateTime>
	{
		@Override
		public DateTime deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException
		{
			return DateTime.fromJavaDate(toDate(json.toString()));
		}
	}

	// private static class UUIDDeserializer implements JsonDeserializer<UUID>
	// {
	// @Override
	// public UUID deserialize(JsonElement json, Type type,
	// JsonDeserializationContext context) throws JsonParseException
	// {
	// return UUID.fromString(json.toString());
	// }
	// }

	/**
	 * 把对象封装为JSON格式
	 * 
	 * @param o
	 *            对象
	 * @return JSON格式
	 */
	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	public static String toJson(final Object o)
	{
		if (o == null)
		{
			return "null";
		}
		if (o instanceof UUID)
		{
			return string2Json(o.toString());
		}
		if (o instanceof Date)
		{
			return string2Json(toString((Date) o));
		}
		if (o instanceof DateTime)
		{
			DateTime dateTime = (DateTime) o;
			Date date = dateTime.toJavaDate();
			return string2Json(toString(date));
		}
		if (o instanceof String) // String
		{
			return string2Json((String) o);
		}
		if (o instanceof Boolean) // Boolean
		{
			return boolean2Json((Boolean) o);
		}
		if (o instanceof Number) // Number
		{
			return number2Json((Number) o);
		}
		if (o instanceof Map) // Map
		{
			return map2Json((Map<String, Object>) o);
		}
		if (o instanceof Collection) // List Set
		{
			return collection2Json((Collection) o);
		}
		if (o instanceof Object[]) // 对象数组
		{
			return array2Json((Object[]) o);
		}

		if (o instanceof int[])// 基本类型数组
		{
			return intArray2Json((int[]) o);
		}
		if (o instanceof boolean[])// 基本类型数组
		{
			return booleanArray2Json((boolean[]) o);
		}
		if (o instanceof long[])// 基本类型数组
		{
			return longArray2Json((long[]) o);
		}
		if (o instanceof float[])// 基本类型数组
		{
			return floatArray2Json((float[]) o);
		}
		if (o instanceof double[])// 基本类型数组
		{
			return doubleArray2Json((double[]) o);
		}
		if (o instanceof short[])// 基本类型数组
		{
			return shortArray2Json((short[]) o);
		}
		if (o instanceof byte[])// 基本类型数组
		{
			return byteArray2Json((byte[]) o);
		}

		if (o instanceof Object) // 保底收尾对象
		{
			return object2Json(o);
		}

		throw new RuntimeException("不支持的类型: " + o.getClass().getName());
	}

	/**
	 * 将 String 对象编码为 JSON格式，只需处理好特殊字符
	 * 
	 * @param s
	 *            String 对象
	 * @return JSON格式
	 */
	static String string2Json(final String s)
	{
		final StringBuilder sb = new StringBuilder(s.length() + 20);
		sb.append('\"');
		for (int i = 0; i < s.length(); i++)
		{
			final char c = s.charAt(i);
			switch (c)
			{
				case '\"':
					sb.append("\\\"");
					break;
				case '\\':
					sb.append("\\\\");
					break;
				case '/':
					sb.append("\\/");
					break;
				case '\b':
					sb.append("\\b");
					break;
				case '\f':
					sb.append("\\f");
					break;
				case '\n':
					sb.append("\\n");
					break;
				case '\r':
					sb.append("\\r");
					break;
				case '\t':
					sb.append("\\t");
					break;
				default:
					sb.append(c);
			}
		}
		sb.append('\"');
		return sb.toString();
	}

	/**
	 * 将 Number 表示为 JSON格式
	 * 
	 * @param number
	 *            Number
	 * @return JSON格式
	 */
	static String number2Json(final Number number)
	{
		return number.toString();
	}

	/**
	 * 将 Boolean 表示为 JSON格式
	 * 
	 * @param bool
	 *            Boolean
	 * @return JSON格式
	 */
	static String boolean2Json(final Boolean bool)
	{
		return bool.toString();
	}

	/**
	 * 将 Collection 编码为 JSON 格式 (List,Set)
	 * 
	 * @param c
	 * @return
	 */
	static String collection2Json(final Collection<Object> c)
	{
		final Object[] arrObj = c.toArray();
		return toJson(arrObj);
	}

	/**
	 * 将 Map<String, Object> 编码为 JSON 格式
	 * 
	 * @param map
	 * @return
	 */
	static String map2Json(final Map<String, Object> map)
	{
		if (map.isEmpty())
		{
			return "{}";
		}
		final StringBuilder sb = new StringBuilder(map.size() << 4); // 4次方
		sb.append('{');
		final Set<String> keys = map.keySet();
		for (final String key : keys)
		{
			final Object value = map.get(key);
			sb.append('\"');
			sb.append(key); // 不能包含特殊字符
			sb.append('\"');
			sb.append(':');
			sb.append(toJson(value)); // 循环引用的对象会引发无限递归
			sb.append(',');
		}
		// 将最后的 ',' 变为 '}':
		sb.setCharAt(sb.length() - 1, '}');
		return sb.toString();
	}

	/**
	 * 将数组编码为 JSON 格式
	 * 
	 * @param array
	 *            数组
	 * @return JSON 格式
	 */
	static String array2Json(final Object[] array)
	{
		if (array.length == 0)
		{
			return "[]";
		}
		final StringBuilder sb = new StringBuilder(array.length << 4); // 4次方
		sb.append('[');
		for (final Object o : array)
		{
			sb.append(toJson(o));
			sb.append(',');
		}
		// 将最后添加的 ',' 变为 ']':
		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	static String intArray2Json(final int[] array)
	{
		if (array.length == 0)
		{
			return "[]";
		}
		final StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for (final int o : array)
		{
			sb.append(Integer.toString(o));
			sb.append(',');
		}
		// set last ',' to ']':
		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	static String longArray2Json(final long[] array)
	{
		if (array.length == 0)
		{
			return "[]";
		}
		final StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for (final long o : array)
		{
			sb.append(Long.toString(o));
			sb.append(',');
		}
		// set last ',' to ']':
		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	static String booleanArray2Json(final boolean[] array)
	{
		if (array.length == 0)
		{
			return "[]";
		}
		final StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for (final boolean o : array)
		{
			sb.append(Boolean.toString(o));
			sb.append(',');
		}
		// set last ',' to ']':
		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	static String floatArray2Json(final float[] array)
	{
		if (array.length == 0)
		{
			return "[]";
		}
		final StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for (final float o : array)
		{
			sb.append(Float.toString(o));
			sb.append(',');
		}
		// set last ',' to ']':
		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	static String doubleArray2Json(final double[] array)
	{
		if (array.length == 0)
		{
			return "[]";
		}
		final StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for (final double o : array)
		{
			sb.append(Double.toString(o));
			sb.append(',');
		}
		// set last ',' to ']':
		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	static String shortArray2Json(final short[] array)
	{
		if (array.length == 0)
		{
			return "[]";
		}
		final StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for (final short o : array)
		{
			sb.append(Short.toString(o));
			sb.append(',');
		}
		// set last ',' to ']':
		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	static String byteArray2Json(final byte[] array)
	{
		if (array.length == 0)
		{
			return "[]";
		}
		final StringBuilder sb = new StringBuilder(array.length << 4);
		sb.append('[');
		for (final byte o : array)
		{
			sb.append(Byte.toString(o));
			sb.append(',');
		}
		// set last ',' to ']':
		sb.setCharAt(sb.length() - 1, ']');
		return sb.toString();
	}

	public static String object2Json(final Object bean)
	{
		// 数据检查
		if (bean == null)
		{
			return "{}";
		}
		final Method[] methods = bean.getClass().getMethods(); // 方法数组
		final StringBuilder sb = new StringBuilder(methods.length << 4); // 4次方
		sb.append('{');

		for (final Method method : methods)
		{
			try
			{
				final String name = method.getName();
				String key = "";
				if (name.startsWith("get"))
				{
					key = name.substring(3);

					// 防死循环
					final String[] arrs =
					{ "Class" };
					boolean bl = false;
					for (final String s : arrs)
					{
						if (s.equals(key))
						{
							bl = true;
							continue;
						}
					}
					if (bl)
					{
						continue; // 防死循环
					}
				}
				else if (name.startsWith("is"))
				{
					key = name.substring(2);
				}
				if (key.length() > 0 && Character.isUpperCase(key.charAt(0)) && method.getParameterTypes().length == 0)
				{
					// if (key.length() == 1)
					// {
					// key = key.toLowerCase();
					// }
					// else if (!Character.isUpperCase(key.charAt(1)))
					// {
					// key = key.substring(0, 1).toLowerCase() +
					// key.substring(1);
					// }
					final Object elementObj = method.invoke(bean);

					// System.out.println("###" + key + ":" +
					// elementObj.toString());

					sb.append('\"');
					sb.append(key); // 不能包含特殊字符
					sb.append('\"');
					sb.append(':');
					sb.append(toJson(elementObj)); // 循环引用的对象会引发无限递归
					sb.append(',');
				}
			}
			catch (final Exception e)
			{
				// e.getMessage();
				throw new RuntimeException("在将bean封装成JSON格式时异常：" + e.getMessage(), e);
			}
		}
		if (sb.length() == 1)
		{
			return bean.toString();
		}
		else
		{
			sb.setCharAt(sb.length() - 1, '}');
			return sb.toString();
		}
	}

	/**
	 * 判断字符串是否为有内容
	 * 
	 * @param string
	 * @return
	 */
	private static boolean isBlankString(String string)
	{
		if (string == null || "".equals(string))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。
	 * 
	 * @param <T>
	 *            要转换的目标类型。
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param token
	 *            {@code com.google.gson.reflect.TypeToken} 的类型指示类对象。
	 * @param datePattern
	 *            日期格式模式。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 * @since 1.0
	 */
	public static <T> T fromJson(String json, TypeToken<T> token, String datePattern)
	{
		if (isBlankString(json))
		{
			return null;
		}
		GsonBuilder builder = new GsonBuilder();
		if (isBlankString(datePattern))
		{
			datePattern = DEFAULT_DATE_PATTERN;
		}
		Gson gson = builder.create();
		try
		{
			return gson.fromJson(json, token.getType());
		}
		catch (Exception ex)
		{
			// Common.log("ws", json + " 无法转换为 " + token.getRawType().getName()
			// + " 对象!异常原因：" + ex.getMessage());
			return null;
		}
	}

	/**
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。
	 * 
	 * @param <T>
	 *            要转换的目标类型。
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param token
	 *            {@code com.google.gson.reflect.TypeToken} 的类型指示类对象。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 * @since 1.0
	 */
	public static <T> T fromJson(String json, TypeToken<T> token)
	{
		return fromJson(json, token, null);
	}

	/**
	 * capital首写字母是否大写
	 * 
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。此方法通常用来转换普通的 {@code JavaBean} 对象。
	 * 
	 * @param <T>
	 *            要转换的目标类型。
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param clazz
	 *            要转换的目标类。
	 * @param datePattern
	 *            日期格式模式。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 * @since 1.0
	 */
	public static <T> T fromJson(String json, Class<T> clazz, String datePattern, boolean capital)
	{
		if (isBlankString(json))
		{
			return null;
		}
		GsonBuilder builder = new GsonBuilder();

		builder.registerTypeAdapter(Date.class, new DateDeserializer());
		builder.registerTypeAdapter(DateTime.class, new DateTimeDeserializer());

		// 首写字母大写，对应.net的属性
		if (capital)
		{
			builder = builder.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE);
		}
		else
		{
			builder = builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
		}
		if (isBlankString(datePattern))
		{
			datePattern = DEFAULT_DATE_PATTERN;
		}
		Gson gson = builder.create();
		try
		{
			return gson.fromJson(json, clazz);
		}
		catch (Exception ex)
		{
			// Common.log("ws", json + " 无法转换为 " + clazz.getName() + " 对象!异常原因："
			// + ex.getMessage());
			ex.printStackTrace();
			return null;
		}
	}

	/***
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。<strong>此方法通常用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * 
	 * @param <T>
	 *            要转换的目标类型。
	 * 
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param clazz
	 *            要转换的目标类。
	 * @param capital
	 *            转换.net传输来的json数据，capital为true，否则内部使用false
	 * 
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象
	 */
	public static <T> T fromJson(String json, Class<T> clazz, boolean capital)
	{
		return fromJson(json, clazz, null, capital);
	}

	/**
	 * 根据指定的文件读取数据
	 * 
	 * @param path
	 * @param clazz
	 * @return
	 */
	public static <T> T read(String path, Class<T> clazz)
	{
		T obj = null;

		try
		{
			File file = new File(path);
			if (clazz != null && file.exists())
			{
				// FileReader fr = new FileReader(path);
				// BufferedReader br = new BufferedReader(fr);
				// String s = null;
				// while ((s = br.readLine()) != null)
				// {
				//
				// }

				StringBuffer sb = new StringBuffer();

				InputStream instream = new FileInputStream(file);
				if (instream != null)
				{
					InputStreamReader inputreader = new InputStreamReader(instream, "utf8");
					BufferedReader buffreader = new BufferedReader(inputreader);
					String line;
					// 分行读取
					while ((line = buffreader.readLine()) != null)
					{
						sb.append(line);
						// sb.append("sb.append(line);");
					}
					instream.close();
				}

				String jsonText = sb.toString();
				if (!TextUtils.isEmpty(jsonText))
				{
					obj = JsonCore.fromJson(jsonText, clazz, false);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return obj;
	}

	public static void write(String path, Object obj)
	{
		if (obj == null || path == null)
		{
			return;
		}
		String jsonText = JsonCore.toJson(obj);
		try
		{
			FileWriter fileWriter = new FileWriter(new File(path), false);
			fileWriter.write(jsonText);

			fileWriter.flush();
			fileWriter.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}