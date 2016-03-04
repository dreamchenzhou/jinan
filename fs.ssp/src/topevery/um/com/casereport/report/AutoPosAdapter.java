package topevery.um.com.casereport.report;

import java.util.List;

import topevery.um.jinan.manager.R;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AutoPosAdapter extends ArrayAdapter<String>
{
	static int textViewResourceId = R.layout.autoposadapter_item;
	Context mContext;

	public AutoPosAdapter(Context context, List<String> objects)
	{
		super(context, textViewResourceId, objects);
		mContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		TextView txtContent = null;
		if (convertView == null)
		{
			convertView = LayoutInflater.from(mContext).inflate(textViewResourceId, null);
			txtContent = (TextView) convertView.findViewById(R.id.txtContent);
			convertView.setTag(txtContent);
		}
		else
		{
			txtContent = (TextView) convertView.getTag();
		}
		String item = this.getItem(position);
		txtContent.setText(item);
		txtContent.setTextColor(Color.BLACK);
		return convertView;
	}
}
