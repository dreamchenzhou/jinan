package topevery.um.com.casereport.report;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import topevery.android.framework.utils.TextUtils;
import topevery.um.net.srv.AttachInfo;
import topevery.um.net.srv.EvtPara;
import topevery.um.jinan.manager.R;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CaseTempAdapter extends ArrayAdapter<EvtPara>
{
	private LayoutInflater mInflater;
	private static int textViewResourceId = R.layout.casetemp_listview_item;

	public CaseTempAdapter(Context context, List<EvtPara> objects)
	{
		super(context, textViewResourceId, objects);
		this.mInflater = LayoutInflater.from(context);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHondle hondle = null;
		if (convertView == null)
		{
			hondle = new ViewHondle();
			convertView = mInflater.inflate(R.layout.casetemp_listview_item, null);
			hondle.temp_txtTime = (TextView) convertView.findViewById(R.id.temp_txtTime);
			hondle.temp_txt_evtDesc = (TextView) convertView.findViewById(R.id.temp_txt_evtDesc);
			hondle.temp_txt_evtPos = (TextView) convertView.findViewById(R.id.temp_txt_evtPos);
			convertView.setTag(hondle);
		}
		else
		{
			hondle = (ViewHondle) convertView.getTag();
		}
		EvtPara item = this.getItem(position);

		if (TextUtils.isEmpty(item.evtDesc))
		{
			hondle.temp_txt_evtDesc.setText("无描述信息");
		}
		else
		{
			hondle.temp_txt_evtDesc.setText(item.evtDesc);
		}

		if (TextUtils.isEmpty(item.evtPos))
		{
			hondle.temp_txt_evtPos.setText("无地址信息");
		}
		else
		{
			hondle.temp_txt_evtPos.setText(item.evtPos);
		}
		setTimp(item, hondle);
		return convertView;
	}

	private void setTimp(EvtPara item, ViewHondle hondle)
	{
		try
		{
			String name = item.file.getName();
			String name2, name3, name4, todayname, mothname;

			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yy_HHmmss");

			Date date = sdf.parse(name);

			sdf = new SimpleDateFormat("MM-dd-yy");
			SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
			SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
			SimpleDateFormat sdf4 = new SimpleDateFormat("MM");

			name = sdf.format(date);
			name2 = sdf2.format(date);
			name3 = sdf3.format(date);
			name4 = sdf4.format(date);

			String string = sdf.format(new java.util.Date());// 当前时间
			Date date2 = sdf.parse(string);

			todayname = sdf3.format(date2);
			mothname = sdf4.format(date2);

			int day1 = Integer.parseInt(name3);
			int day2 = Integer.parseInt(todayname);

			if (name.equals(string))
			{
				hondle.temp_txtTime.setText(name2);
			}
			else
			{
				if (mothname.equals(name4))
				{
					if (day2 - day1 == 1)
					{
						hondle.temp_txtTime.setText("昨天");
					}
					else
					{
						hondle.temp_txtTime.setText(name);
					}
				}
				else
				{
					hondle.temp_txtTime.setText(name);
				}
			}

		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// public String getCreateDate(EvtPara item)
	// {
	// File file = new File(item.uri);
	// Long time = file.lastModified();
	// Calendar cd = Calendar.getInstance();
	// cd.setTimeInMillis(time);
	// Date date = cd.getTime();
	//
	// String value = "";
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// value = sdf.format(date);
	// return value;
	// }

	private Drawable getDrawable(AttachInfo item)
	{
		Drawable result = BitmapDrawable.createFromPath(item.uri);
		return result;
	}

	private class ViewHondle
	{
		public TextView temp_txtTime, temp_txt_evtDesc, temp_txt_evtPos;
	}

}
