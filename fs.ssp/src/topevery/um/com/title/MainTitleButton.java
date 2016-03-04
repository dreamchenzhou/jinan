package topevery.um.com.title;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class MainTitleButton extends Button
{

	public MainTitleButton(Context context)
	{
		this(context, null);
	}

	public MainTitleButton(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.setOnTouchListener(new OnTouchListener()
			{

				@Override
				public boolean onTouch(View v, MotionEvent event)
				{
					if (event.getAction() == MotionEvent.ACTION_DOWN)
					{
						Activity context = (Activity) v.getContext();
						context.onBackPressed();
					}
					return false;
				}
			});
	}

}
