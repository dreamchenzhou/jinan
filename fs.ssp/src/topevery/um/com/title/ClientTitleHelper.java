package topevery.um.com.title;

import topevery.um.jinan.manager.R;
import android.app.Activity;
import android.widget.TextView;

public class ClientTitleHelper
{
	public static void setTitle(Activity context)
	{
		TextView view = (TextView) context.findViewById(R.id.customTitle);
		String title = context.getTitle().toString();
		view.setText(title);
	}

	public static void setTitle(Activity context, String title)
	{
		TextView view = (TextView) context.findViewById(R.id.customTitle);
		view.setText(title);
	}
}
