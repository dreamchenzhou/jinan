package topevery.um.com.grid;

import java.util.ArrayList;

import android.content.Context;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

@SuppressWarnings("serial")
public class GridItems extends ArrayList<GridItem>
{
	public boolean hasValue()
	{
		return size() > 0;
	}

	public GridItem add(Context context, Object tag, int resId)
	{
		GridItem item = new GridItem(context);
		item.setGridTitle(tag.toString());
		item.setTag(tag);
		item.setGridImg(resId);
		item.setId(resId);
		this.add(item);
		return item;
	}

	public GridItem add(Context context, CharSequence title, Object tag, int resId)
	{
		GridItem item = new GridItem(context);
		item.setGridTitle(title);
		item.setTag(tag);
		item.setGridImg(resId);
		item.setId(resId);
		this.add(item);
		return item;
	}

	public GridItem add(Context context, CharSequence title, Object tag, int resId, OnClickListener listener)
	{
		GridItem item = new GridItem(context);
		item.setGridTitle(title);
		item.setTag(tag);
		item.setGridImg(resId);
		item.setId(resId);
		item.setOnClickListener(listener);
		this.add(item);
		return item;
	}

	public void setAdapter(GridView gridView, OnItemClickListener listener)
	{
		if (gridView != null && hasValue())
		{
			GridAdapter adapter = new GridAdapter(gridView.getContext(), this);
			gridView.setAdapter(adapter);

			if (listener != null)
			{
				gridView.setOnItemClickListener(listener);
			}
		}
	}

	public void setCount(Object tag, int count)
	{
		for (GridItem item : this)
		{
			if (item.getTag().equals(tag))
			{
				item.setGridCount(count);
				break;
			}
		}
	}
}