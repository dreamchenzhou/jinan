package topevery.um.com.casereport.report;

import java.util.ArrayList;

import topevery.um.net.srv.AttachInfoCollection;

public class CaseUtils
{
	public static AttachInfoCollection attachs = new AttachInfoCollection();

	public static ArrayList<String> files = new ArrayList<String>();

	public static void reset()
	{
		attachs.clear();
		files.clear();
	}
}
