package topevery.um.com.title;

import topevery.um.jinan.manager.R;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class TitleTextView extends TextView
{
	public TitleTextView(Context context)
	{
		this(context, null);
	}

	public TitleTextView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		Activity body = (Activity) context;
		this.setText(body.getTitle());
	}

	public static void changeTitle(Activity body, String title)
	{
		TextView view = (TextView) body.findViewById(R.id.customTitle);
		if (view != null)
		{
			view.setText(title);
		}
	}
}
