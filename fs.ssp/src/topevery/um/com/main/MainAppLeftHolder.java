package topevery.um.com.main;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import topevery.um.jinan.manager.R;

public class MainAppLeftHolder implements OnItemClickListener
{
	private Main mMain;
	private ListView mListView;

	public MainAppLeftHolder(Main mMain)
	{
		this.mMain = mMain;
		findView();
	}

	private void findView()
	{
		mListView = (ListView) this.mMain.findViewById(R.id.listView);
		mListView.setOnItemClickListener(this);
	}

	public void init()
	{
		ArrayList<String> objects = new ArrayList<String>();
		objects.add("测试1");
		objects.add("测试2");

		LeftAdapter adapter = new LeftAdapter(mMain, objects);
		mListView.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id)
	{

	}

	private class LeftAdapter extends ArrayAdapter<String>
	{
		private static final int textViewResourceId = R.layout.main_app_left_item;
		private LayoutInflater mLayoutInflater;

		public LeftAdapter(Context context, List<String> objects)
		{
			super(context, textViewResourceId, objects);
			mLayoutInflater = LayoutInflater.from(context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ViewHolder holder = null;
			if (convertView == null)
			{
				holder = new ViewHolder();
				convertView = mLayoutInflater.inflate(textViewResourceId, null);
				holder.itemTag = (TextView) convertView.findViewById(R.id.itemTag);
				holder.itemTitle = (TextView) convertView.findViewById(R.id.itemTitle);

				convertView.setTag(holder);
			}
			else
			{
				holder = (ViewHolder) convertView.getTag();
			}

			String item = getItem(position);

			holder.itemTitle.setText(item);
			holder.itemTag.setText(item.subSequence(0, 1));

			return convertView;
		}

		private class ViewHolder
		{
			TextView itemTag, itemTitle;
		}
	}
}
