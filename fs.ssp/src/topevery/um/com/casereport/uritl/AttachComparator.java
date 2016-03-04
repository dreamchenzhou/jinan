package topevery.um.com.casereport.uritl;

import java.util.Comparator;

import topevery.um.net.srv.AttachInfo;

public class AttachComparator implements Comparator<AttachInfo>
{
	@Override
	public int compare(AttachInfo object1, AttachInfo object2)
	{
		if (object1.lastModified() > object2.lastModified())
		{
			return -1;
		}
		return 1;
	}
}
