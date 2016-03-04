package topevery.um.net.srv;

import java.io.File;
import java.util.ArrayList;

import android.graphics.Color;

@SuppressWarnings("serial")
public class EvtResList extends ArrayList<EvtRes>
{
	public File file = null;
	public String name = "";

	public int setTextColor(EvtResList iEvtResList)
	{
		int color = 0;
		if (iEvtResList.size() == 0)
		{
			color = Color.GRAY;
		}
		else
		{
			color = Color.BLACK;
		}
		return color;

	}

	public void clearItem()
	{
		for (EvtRes item : this)
		{
			if (item.file != null && item.file.exists())
			{
				item.file.delete();
			}
		}

		file.delete();
	}
}
