package topevery.um.com.casereport.codeshow;

import java.util.List;

import topevery.um.com.casereport.uritl.KeyValue;
import topevery.um.jinan.manager.R;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CodeShowListAdapter extends ArrayAdapter<KeyValue>
{
	private static int textViewResourceId = R.layout.checkcase_adapter_item;
	private LayoutInflater myInflat;

	public CodeShowListAdapter(Context context, List<KeyValue> objects)
	{
		super(context, textViewResourceId, objects);
		this.myInflat = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		try
		{
			ViewHondler hondler = null;
			if (convertView == null)
			{
				hondler = new ViewHondler();
				convertView = myInflat.inflate(textViewResourceId, null);
				hondler.txtKey = (TextView) convertView.findViewById(R.id.txtKey);
				hondler.txtValue = (TextView) convertView.findViewById(R.id.txtValue);
				convertView.setTag(hondler);
			}
			else
			{
				hondler = (ViewHondler) convertView.getTag();
			}
			hondler.txtKey.setText(getItem(position).key);
			hondler.txtValue.setText(getItem(position).value);

			hondler.txtKey.setTextColor(Color.BLACK);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return convertView;
	}

	private class ViewHondler
	{
		public TextView txtKey;
		public TextView txtValue;
	}
}
