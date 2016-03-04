package topevery.um.com.casereport.codeshow;

import java.util.List;

import topevery.android.framework.zoom.ZoomHolder;
import topevery.um.com.utils.PathUtils;
import topevery.um.jinan.manager.R;
import topevery.um.net.srv.AttachInfo;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class CodeShowGridAdapter extends ArrayAdapter<AttachInfo>
{
	private static int textViewResourceId = R.layout.codeshow_gridview_item;
	private Context mContext;
	private LayoutInflater mInflater;

	public CodeShowGridAdapter(Context context, List<AttachInfo> objects)
	{
		super(context, textViewResourceId, objects);
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHondle hondle = null;
		if (convertView == null)
		{
			hondle = new ViewHondle();
			convertView = mInflater.inflate(textViewResourceId, null);
			hondle.imageView = (ImageView) convertView.findViewById(R.id.codeshow_grid_item);
			convertView.setTag(hondle);
		}
		else
		{
			hondle = (ViewHondle) convertView.getTag();
		}
		AttachInfo aInfo = getItem(position);

		Drawable drawable = PathUtils.getDrawable(aInfo.uri, 1);

		hondle.imageView.setImageDrawable(drawable);
		// hondle.imageButton.setImageDrawable(drawable);

		// hondle.imageButton.setTag(aInfo);
		// hondle.imageButton.setOnClickListener(new OnClickListener()
		// {
		// @Override
		// public void onClick(View v)
		// {
		// AttachInfo attachInfo = (AttachInfo) v.getTag();
		// ZoomHolder.value.show(mContext, attachInfo.uri);
		// }
		// });

		convertView.setId(position);
		convertView.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					int pos = v.getId();
					AttachInfo attachInfo = getItem(pos);
					ZoomHolder.value.show(mContext, attachInfo.uri);
				}
			});

		return convertView;
	}

	public class ViewHondle
	{
		public ImageView imageView;
	}
}
