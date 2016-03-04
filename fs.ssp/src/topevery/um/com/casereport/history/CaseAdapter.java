package topevery.um.com.casereport.history;

import topevery.um.com.data.CaseAccept;
import topevery.um.com.data.DatabaseAttach;
import topevery.um.com.data.DatabaseEvtRes;
import topevery.um.com.main.MainDialog;
import topevery.um.net.newbean.UserCache;
import topevery.um.net.srv.AttachInfo;
import topevery.um.net.srv.AttachInfoCollection;
import topevery.um.net.srv.EvtRes;
import topevery.um.net.srv.EvtResList;
import topevery.um.jinan.manager.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class CaseAdapter extends BaseExpandableListAdapter
{
	private Context mContext;
	private LayoutInflater mInflater;
	private CaseGroupList caseGroupList;
	private EvtResList itemList;
	private Activity aCase;

	public CaseAdapter(Context context, CaseGroupList caseGroupList,
			Activity activity)
	{
		super();
		this.aCase = activity;
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		this.caseGroupList = caseGroupList;
	}

	@Override
	public int getGroupCount()
	{

		return caseGroupList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition)
	{
		return caseGroupList.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition)
	{
		return caseGroupList.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition)
	{
		return caseGroupList.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition)
	{
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition)
	{
		return childPosition;
	}

	@Override
	public boolean hasStableIds()
	{
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent)
	{
		CaseGroupHolder holder = null;
		if (convertView == null)
		{
			holder = new CaseGroupHolder();
			convertView = mInflater.inflate(R.layout.case_grouplist_ui, parent, false);
			holder.txt = (TextView) convertView.findViewById(R.id.grouplist_txt);
			holder.imageView = (ImageView) convertView.findViewById(R.id.grouplist_img);
			convertView.setTag(holder);
		}
		else
		{
			holder = (CaseGroupHolder) convertView.getTag();
		}
		itemList = caseGroupList.get(groupPosition);
		holder.txt.setText(itemList.name);
		holder.txt.setTextColor(itemList.setTextColor(itemList));
		holder.setExpanded(isExpanded, itemList);
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent)
	{
		CaseChlidHolder holder = null;
		if (convertView == null)
		{
			holder = new CaseChlidHolder();
			convertView = mInflater.inflate(R.layout.history_item, parent, false);
			holder.txtevtCode = (TextView) convertView.findViewById(R.id.txt_history_evtCode);
			holder.btnButton = (ImageButton) convertView.findViewById(R.id.btn_history_evtCode);
			holder.txtevtDesc = (TextView) convertView.findViewById(R.id.miaoshu);
			holder.txtCondIamge = (TextView) convertView.findViewById(R.id.condImage);
			holder.txtCondition = (TextView) convertView.findViewById(R.id.condition);
			holder.txtCondTime = (TextView) convertView.findViewById(R.id.condTime);
			convertView.setTag(holder);
		}
		else
		{
			holder = (CaseChlidHolder) convertView.getTag();
		}
		EvtRes item = caseGroupList.get(groupPosition).get(childPosition);

		if (item.caseAccept == CaseAccept.accepted)
		{
			holder.txtCondition.setText("已受理");
			holder.txtCondIamge.setBackgroundResource(R.xml.code_icon1);

		}
		else
		{
			holder.txtCondition.setText("未受理");
			holder.txtCondIamge.setBackgroundResource(R.xml.code_icon2);
		}
		holder.txtCondTime.setText(item.datetime);
		holder.txtevtCode.setText(item.evtCode);
		holder.btnButton.setTag(item);
		holder.txtevtDesc.setText(item.evtPara.evtDesc);
		holder.btnButton.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					final EvtRes evtRes = (EvtRes) v.getTag();

					MainDialog.show(aCase, "确定删除吗？", new View.OnClickListener()
						{

							@Override
							public void onClick(View v)
							{
								for (EvtResList list : caseGroupList)
								{
									for (EvtRes eRes : list)
									{
										if (evtRes.equals(eRes))
										{
											list.remove(eRes);
											delete(eRes);
											notifyDataSetChanged();
											break;
										}
									}
								}
								MainDialog.setDismiss();
							}
						});
				}
			});
		return convertView;

	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition)
	{
		return true;
	}

	private void delete(EvtRes item)
	{
		AttachInfoCollection aCollection = null;
//				DatabaseAttach.getValue(item.evtCode);
		if (aCollection != null && aCollection.size() != 0)
		{
			for (AttachInfo attachInfo : aCollection)
			{
				attachInfo.delete();
			}
		}
		DatabaseAttach.delete(item.evtCode);
		DatabaseEvtRes.delete(item.evtCode,UserCache.getInstance().getUserId());
	}

	public void clear()
	{
		if (caseGroupList != null)
		{

		}
	}
}
