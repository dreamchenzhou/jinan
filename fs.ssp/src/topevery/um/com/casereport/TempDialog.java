package topevery.um.com.casereport;

import topevery.um.com.casereport.report.CaseTempAdapter;
import topevery.um.com.casereport.report.Casereport;
import topevery.um.com.casereport.report.TempDialogAdapter;
import topevery.um.net.srv.EvtPara;
import topevery.um.jinan.manager.R;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class TempDialog extends Dialog
{
	private EvtPara item;
	private ListView listView;
	private Context context;
	private String[] strings;
	CaseTempAdapter adapter;

	public TempDialog(CaseTempAdapter adapter, Context context, EvtPara item,
			String[] strings)
	{
		super(context);
		this.adapter = adapter;
		this.item = item;
		this.context = context;
		this.strings = strings;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.temp_dialog_view);
		setTitle("信息选项");
		setUI();
	}

	private void setUI()
	{
		TempDialogAdapter adapter = new TempDialogAdapter(context, strings);
		listView = (ListView) findViewById(R.id.temp_dialog_listview);
		listView.setOnItemClickListener(new OnItemClickListener()
			{

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id)
				{
					switch (position)
					{
						case 0:
							try
							{
								EvtPara item2 = new EvtPara();
								item2 = item;
								item.file.delete();
								Bundle bundle = new Bundle();
								bundle.putSerializable("temp", (EvtPara) item);
								Intent intent = new Intent();
								intent.putExtras(bundle);
								intent.setClass(context, Casereport.class);
								context.startActivity(intent);
								((Activity) context).finish();
							}
							catch (Exception e)
							{
								e.printStackTrace();
							}
							dismiss();
							break;
						case 1:
							TempDialog.this.adapter.remove(item);
							TempDialog.this.adapter.notifyDataSetChanged();
							item.file.delete();
							dismiss();
					}
				}
			});
		listView.setAdapter(adapter);
	}

}
