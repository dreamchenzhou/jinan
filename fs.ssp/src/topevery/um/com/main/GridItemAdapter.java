package topevery.um.com.main;

import java.util.List;

import topevery.um.com.utils.DisplayUtils;
import topevery.um.jinan.manager.R;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class GridItemAdapter extends ArrayAdapter<GridItem>
{
	Options opts;
	int width;
	int height;
	private LayoutInflater mInflater;
	private static int textViewResourceId;

	public GridItemAdapter(Context context, List<GridItem> objects)
	{
		super(context, textViewResourceId, objects);
		mInflater = LayoutInflater.from(context);
		opts = new Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(context.getResources(), R.drawable.about, opts);

		if (DisplayUtils.widthPixels == 320)
		{
			width = (int) (opts.outWidth / 1.5);
			height = (int) (opts.outHeight / 1.5);
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		convertView = getItem(position);

		// if (DisplayUtils.widthPixels == 320)
		// {
		// GridItem gridItem = (GridItem) convertView;
		// ImageView imageView = gridItem.imageView;
		//
		// FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)
		// imageView.getLayoutParams();
		// if (params == null)
		// {
		// params = new FrameLayout.LayoutParams(width, height);
		// }
		// else
		// {
		// params.width = width;
		// params.height = height;
		// }
		//
		// imageView.setLayoutParams(params);
		// }

		return convertView;
	}

}
