package topevery.um.com.utils;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecimalUtils
{
	/**
	 * 保留小数点后两位
	 * */
	public static String format2(double value)
	{
		String text = "";
		DecimalFormat df = new DecimalFormat(".00");
		text = df.format(value);
		return text;
	}

	/** 判断是否为手机号码 */
	public static boolean isMobileNO(String mobiles)
	{
		Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(18[0,5-9])|(17[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}
}
