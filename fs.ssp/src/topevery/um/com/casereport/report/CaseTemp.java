package topevery.um.com.casereport.report;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;

import topevery.um.com.data.Serializer;
import topevery.um.com.utils.PathUtils;
import topevery.um.jinan.manager.R;
import topevery.um.net.srv.EvtPara;
import topevery.um.net.srv.EvtParaList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

public class CaseTemp extends Activity
{
	private ListView listView;
	private Button btn;
	private EvtParaList evtItem = new EvtParaList();
	private CaseTempAdapter adapter;
	private CaseTemp wTemp = this;
	private MenuDialog teDialog;
	private EvtPara item2 = new EvtPara();

	private String[] string = new String[]
	{ "查看", "删除" };

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.casetemp);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
		setUI();
		setTempDate();
	}

	private void setUI()
	{
		listView = (ListView) findViewById(R.id.casetemp_listview);
		listView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				item2 = evtItem.get(position);
				teDialog = new MenuDialog(wTemp, string, new OnItemClickListener()
				{

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id)
					{
						switch (position)
						{
							case 0:
								try
								{
									item2.file.delete();
									Bundle bundle = new Bundle();
									bundle.putSerializable("temp", (EvtPara) item2);
									Intent intent = new Intent();
									intent.putExtras(bundle);
									intent.setClass(wTemp, Casereport.class);
									wTemp.startActivity(intent);
									((Activity) wTemp).finish();
								}
								catch (Exception e)
								{
									e.printStackTrace();
								}
								teDialog.dismiss();
								break;
							case 1:
								adapter.remove(item2);
								adapter.notifyDataSetChanged();
								item2.file.delete();
								teDialog.dismiss();
								break;
						}
					}
				}, "信息选项");
				teDialog.show();
			}
		});
		btn = (Button) findViewById(R.id.casetemp_btn_back);
		btn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});

	}

	public void setDelet(EvtPara item)
	{
		adapter.remove(item);
	}

	private void setTempDate()
	{
		if (adapter != null)
		{
			adapter.clear();
		}
		String path = PathUtils.getTemp();
		File file = new File(path);
		File[] files = file.listFiles();
		if (files.length != 0)
		{
			for (File file2 : files)
			{
				EvtPara item = null;

				try
				{
					String pathString = file2.getPath();
					item = (EvtPara) Serializer.readObject(pathString);
					if (item != null)
					{
						item.file = file2;
						evtItem.add(item);
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}

			}
		}

		Collections.sort(evtItem, new ItemComparator());

		adapter = new CaseTempAdapter(this, evtItem);
		listView.setAdapter(adapter);
	}

	public class ItemComparator implements Comparator<EvtPara>
	{
		@Override
		public int compare(EvtPara object1, EvtPara object2)
		{
			File file;

			long a = object1.file.lastModified();
			long b = object2.file.lastModified();

			if (a > b)
			{
				return -1;
			}
			return 1;
		}
	}
}
