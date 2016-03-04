package topevery.um.com.casereport.history;

import java.io.File;
import java.util.List;

import topevery.android.core.MsgBox;
import topevery.um.com.casereport.codeshow.CodeShow;
import topevery.um.com.utils.PathUtils;
import topevery.um.jinan.manager.R;
import topevery.um.net.srv.AttachInfo;
import topevery.um.net.srv.EvtRes;
import topevery.um.net.srv.EvtResList;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class HistoryAdapter extends ArrayAdapter<EvtRes>
{

	private static int textViewResourceId = R.layout.history_item;
	private LayoutInflater myInflat;
	private HistoryCase historyCase;
	private CodeShow cShow;
	private EvtResList objects = new EvtResList();

	public HistoryAdapter(Context context, List<EvtRes> objects)
	{
		super(context, textViewResourceId, objects);
		this.historyCase = (HistoryCase) context;
		this.myInflat = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHondler hondler = null;
		if (convertView == null)
		{
			hondler = new ViewHondler();
			convertView = myInflat.inflate(textViewResourceId, null);
			hondler.txtevtCode = (TextView) convertView.findViewById(R.id.txt_history_evtCode);
			hondler.btnButton = (ImageButton) convertView.findViewById(R.id.btn_history_evtCode);

			convertView.setTag(hondler);
		}
		else
		{
			hondler = (ViewHondler) convertView.getTag();
		}

		EvtRes item = getItem(position);

		hondler.txtevtCode.setText(item.evtCode);
		hondler.btnButton.setTag(item);
		hondler.btnButton.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					// Bundle bundle = new Bundle();
					// bundle.putSerializable("item", (EvtRes) v.getTag());
					// Intent intent = new Intent();
					// intent.putExtras(bundle);
					// intent.setClass(historyCase, CodeShow.class);
					// historyCase.startActivity(intent);

					final EvtRes evtRes = (EvtRes) v.getTag();

					MsgBox.askYesNo(historyCase, "确定删除吗？", new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog,
									int which)
							{
								delete(evtRes);
								remove(evtRes);
								notifyDataSetChanged();
							}
						}, null);
				}
			});

		return convertView;
	}

	private class ViewHondler
	{
		public TextView txtevtCode;
		public ImageButton btnButton;
		public ImageButton btndelet;
	}

	private void delete(EvtRes item)
	{
		try
		{
			String path = PathUtils.getEvtCodePath(item.evtCode);
			File file = new File(path);
			if (file.exists())
			{
				file.delete();
				file.getParentFile().delete();
			}

			if (item.evtPara != null && item.evtPara.attachs != null)
			{
				for (AttachInfo attachInfo : item.evtPara.attachs)
				{
					attachInfo.delete();
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
