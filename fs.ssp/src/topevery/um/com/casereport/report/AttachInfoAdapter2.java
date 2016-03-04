package topevery.um.com.casereport.report;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import topevery.android.framework.utils.TextUtils;
import topevery.um.com.utils.PathUtils;
import topevery.um.jinan.manager.R;
import topevery.um.net.srv.AttachInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class AttachInfoAdapter2 extends ArrayAdapter<AttachInfo>
{
	private LayoutInflater mInflater;
	private static int textViewResourceId = R.layout.attch_adapter_item;
	Casereport context;

	public AttachInfoAdapter2(Casereport context, List<AttachInfo> objects)
	{
		super(context, textViewResourceId, objects);
		this.mInflater = LayoutInflater.from(context);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHondle hondle = null;
		if (convertView == null)
		{
			hondle = new ViewHondle();
			convertView = mInflater.inflate(textViewResourceId, null);
			hondle.imageView = (ImageButton) convertView.findViewById(R.id.imageView);
			hondle.txtName = (TextView) convertView.findViewById(R.id.txtName);
			hondle.imageButton = (ImageButton) convertView.findViewById(R.id.imageButton);
			hondle.txtSize = (TextView) convertView.findViewById(R.id.txtSize);
			convertView.setTag(hondle);
		}
		else
		{
			hondle = (ViewHondle) convertView.getTag();
		}
		AttachInfo item = this.getItem(position);
		if (item.drawable == null)
		{
			// item.drawable = getDrawable(item);

			item.drawable = PathUtils.getDrawable(item.uri, 1);
		}

		if (TextUtils.isEmpty(item.name))
		{
			item.name = getName(item);
		}

		hondle.imageView.setImageDrawable(item.drawable);
		hondle.txtName.setText(item.name);
		hondle.txtSize.setText(getSize(item));

		hondle.imageButton.setTag(position);
		hondle.imageButton.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					// MsgBox.askYesNo(context, "确定删除？", new
					// DialogInterface.OnClickListener()
					// {
					//
					// @Override
					// public void onClick(DialogInterface dialog,
					// int which)
					// {
					int pos = Integer.parseInt(v.getTag().toString());
					AttachInfo item = getItem(pos);
					remove(item);
					item.isSave = false;
					item.isChecked = true;
					notifyDataSetChanged();
					context.addTemp(item);
					// }
					// }, null);
				}
			});
		return convertView;
	}

	public String getSize(AttachInfo item)
	{
		File file = new File(item.uri);
		double value = (double) file.length() / 1024;

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);

		return String.format("%sKB", bd);
	}

	private String getName(AttachInfo item)
	{
		return new File(item.uri).getName();
	}

	private class ViewHondle
	{
		public ImageButton imageView;
		public TextView txtName;
		public TextView txtSize;
		public ImageButton imageButton;
	}
}
