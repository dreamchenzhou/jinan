package topevery.um.com.casereport.history;

import java.util.Comparator;

import topevery.um.net.srv.EvtResList;

public class FileGroupComparator implements Comparator<EvtResList>
{
	@Override
	public int compare(EvtResList object1, EvtResList object2)
	{
		int a = Integer.parseInt(object1.name);
		int b = Integer.parseInt(object2.name);

		if (a > b)
		{
			return -1;
		}
		return 1;
	}
}
