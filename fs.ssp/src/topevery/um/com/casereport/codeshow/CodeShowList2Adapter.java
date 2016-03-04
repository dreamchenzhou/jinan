package topevery.um.com.casereport.codeshow;

import java.util.List;

import topevery.um.net.srv.FlowInfo;
import topevery.um.jinan.manager.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CodeShowList2Adapter extends ArrayAdapter<FlowInfo>
{
	private static int textViewResourceId = R.layout.checkcase_adapter_item2;
	private LayoutInflater myInflat;

	public CodeShowList2Adapter(Context context, List<FlowInfo> objects)
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
				hondler.txt_activityName = (TextView) convertView.findViewById(R.id.txt_activityName);
				hondler.txt_operatorName = (TextView) convertView.findViewById(R.id.txt_operatorName);
				hondler.txt_inDate = (TextView) convertView.findViewById(R.id.txt_inDate);
				hondler.txt_finishedDate = (TextView) convertView.findViewById(R.id.txt_finishedDate);
				hondler.txt_actUsedTime = (TextView) convertView.findViewById(R.id.txt_actUsedTime);
				hondler.txt_limitTime = (TextView) convertView.findViewById(R.id.txt_limitTime);
				hondler.txt_caseUsedDate = (TextView) convertView.findViewById(R.id.txt_caseUsedDate);
				hondler.txt_opinion = (TextView) convertView.findViewById(R.id.txt_opinion);

				convertView.setTag(hondler);
			}
			else
			{
				hondler = (ViewHondler) convertView.getTag();
			}
			hondler.txt_activityName.setText(getItem(position).activityName);
			hondler.txt_operatorName.setText(getItem(position).operatorName);
			hondler.txt_inDate.setText(getItem(position).inDate);
			hondler.txt_finishedDate.setText(getItem(position).finishedDate);
			hondler.txt_actUsedTime.setText(getItem(position).actUsedTime);
			hondler.txt_limitTime.setText(getItem(position).limitTime);
			hondler.txt_caseUsedDate.setText(getItem(position).caseUsedDate);
			hondler.txt_opinion.setText(getItem(position).opinion);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return convertView;
	}

	private class ViewHondler
	{
		public TextView txt_activityName, txt_operatorName, txt_inDate,
				txt_finishedDate, txt_actUsedTime, txt_limitTime,
				txt_caseUsedDate, txt_opinion;

	}
}
