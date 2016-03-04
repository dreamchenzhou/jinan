package topevery.um.com.casereport.history;

import topevery.um.net.srv.EvtResList;
import topevery.um.jinan.manager.R;
import android.widget.ImageView;
import android.widget.TextView;

public class CaseGroupHolder
{
	public TextView txt;
	public ImageView imageView;

	public void setExpanded(boolean isExpanded, EvtResList itemList)
	{
		if (itemList.size() == 0)
		{
			imageView.setImageResource(R.drawable.btn_expand_null);
		}
		else
		{
			if (isExpanded && itemList.size() != 0)
			{
				imageView.setImageResource(R.drawable.btn_expand_yes);
			}
			else
			{
				imageView.setImageResource(R.drawable.btn_expand_no);
			}
		}

	}

}
