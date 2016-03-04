package topevery.um.com.utils;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

public class TextUtilsEx
{
	public static void setText(EditText editText, String text)
	{
		if (!TextUtils.isEmpty(text))
		{
			editText.setText(text);
			editText.setSelection(text.length());
		}
	}

	public static boolean isEmpty(TextView textView)
	{
		String text = textView.getText().toString().trim();
		return TextUtils.isEmpty(text);
	}
}
