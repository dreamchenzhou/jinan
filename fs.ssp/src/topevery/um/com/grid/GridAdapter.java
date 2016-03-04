package topevery.um.com.grid;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class GridAdapter extends ArrayAdapter<GridItem>
{
	private static final int textViewResourceId = 0;

	public GridAdapter(Context context, List<GridItem> objects)
	{
		super(context, textViewResourceId, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		return getItem(position);
	}
}
