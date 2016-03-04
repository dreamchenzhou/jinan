package topevery.um.com.casereport;

import topevery.framework.commonModel.GlobalTimer;
import topevery.framework.commonModel.GlobalTimer.ThreadTask;
import topevery.framework.commonModel.GlobalTimer.ThreadTaskRunnable;
import topevery.um.com.data.CaseAccept;
import topevery.um.com.data.CaseType;
import topevery.um.com.data.DatabaseEvtRes;
import topevery.um.net.ServiceHandle;
import topevery.um.net.newbean.EvtPara;
import topevery.um.net.newbean.EvtParaForIos;
import topevery.um.net.newbean.EvtRes;
import topevery.um.net.newbean.EvtResList;
import topevery.um.net.newbean.UserCache;

public class CaseDatabaseTimer implements ThreadTaskRunnable
{
	long period = 5 * 6 * 10 * 1000;
	long delay = 10 * 1000;

	public void start()
	{
		GlobalTimer.value.setTimerSchedule(this, delay, period);
	}

	@Override
	public void run(ThreadTask obj)
	{
		EvtResList evtResList = new EvtResList();
		evtResList = DatabaseEvtRes.getValue(CaseType.reportSuccess,UserCache.getInstance().getUserId());
		for (EvtRes evtRes : evtResList)
		{
			EvtRes item = evtRes;
			QueryListView(item);
		}
	}

	private void QueryListView(EvtRes evtRes)
	{
		EvtRes item = null;
		EvtParaForIos para = new EvtParaForIos();
		para.setEvtCode(evtRes.getEvtCode());
		try
		{
			if (evtRes.getCaseAccept() != CaseAccept.accepted)
			{
				//TODO to confirm 更新案件
				item = ServiceHandle.GetHistoryEvt(para);
				if (item != null && item.getFlowInfos().size() != 0)
				{
					item.setEvtCode(evtRes.getEvtCode());
					DatabaseEvtRes.update(item.getEvtCode(), CaseAccept.accepted,UserCache.getInstance().getUserId());

				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// public void setRun()
	// {
	// String path = PathUtils.getEvtCodeListPath();
	// File dir = new File(path);
	// File[] files = dir.listFiles();
	// if (files.length != 0)
	// {
	// EvtRes item = null;
	//
	// for (File file : files)
	// {
	//
	// // evtItem.name = file.getName();
	// // evtItem.file = file;
	//
	// File[] subFiles = file.listFiles();
	// if (subFiles != null && subFiles.length > 0)
	// {
	// for (File subFile : subFiles)
	// {
	// try
	// {
	// String fileName = subFile.getPath();
	//
	// item = (EvtRes) Serializer.readObject(fileName);
	// if (item != null)
	// {
	// item.file = subFile;
	// QueryListView(item);
	// // DatabaseState.getValue(item.evtCode);
	// }
	// }
	// catch (Exception e)
	// {
	// e.printStackTrace();
	// }
	// }
	// }
	// }
	// }
	// }
}
