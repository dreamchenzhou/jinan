package topevery.um.com.casereport.uritl;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class MyGridView extends GridView
{

	public MyGridView(Context context)
	{
		super(context);
		this.setValue();
	}

	public MyGridView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.setValue();
	}

	public MyGridView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		this.setValue();
	}

	private void setValue()
	{
		this.setVerticalSpacing(10);
		this.setHorizontalSpacing(10);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
