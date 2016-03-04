package topevery.um.com.casereport.report;

import topevery.um.jinan.manager.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MenuDialog extends Dialog
{
	private ListView listView;
	private Context context;
	private String[] strings;
	private String title;
	private OnItemClickListener listener;

	public MenuDialog(Context context, String[] strings,
			OnItemClickListener listener, String title)
	{
		super(context);
		this.context = context;
		this.strings = strings;
		this.listener = listener;
		this.title = title;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_LEFT_ICON);
		setContentView(R.layout.temp_dialog_view);
		setTitle(title);
		setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, android.R.drawable.ic_menu_more);
		setUI();
	}

	private void setUI()
	{
		TempDialogAdapter adapter = new TempDialogAdapter(context, strings);
		listView = (ListView) findViewById(R.id.temp_dialog_listview);
		listView.setOnItemClickListener(listener);
		listView.setAdapter(adapter);
	}
}
