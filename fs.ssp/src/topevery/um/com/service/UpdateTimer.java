package topevery.um.com.service;

import java.util.UUID;

import android.content.Context;

import topevery.framework.commonModel.GlobalTimer;
import topevery.framework.commonModel.GlobalTimer.ThreadTask;
import topevery.framework.commonModel.GlobalTimer.ThreadTaskRunnable;
import topevery.um.com.UpSysDialog;
import topevery.um.com.main.Main;
import topevery.um.net.update.GetUpdatePara;
import topevery.um.net.update.GetUpdateRes;
import topevery.um.net.update.UpdateHandle;

public class UpdateTimer implements ThreadTaskRunnable
{
	Context mContext;
	// Timer timer;
	long delay = 3 * 1000;
	long period = 10 * 60 * 1000;
	String versionName = "";

	public UpdateTimer(Context main)
	{
		this.mContext = main;
		// period = 10 * 1000;
		// timer = new Timer();
		// timer.schedule(new TimerTask()
		// {
		// @Override
		// public void run()
		// {
		// // TODO Auto-generated method stub
		//
		// }
		// }, 3 * 1000, 5 * 60 * 1000);

		// GlobalTimer.value.setTimerSchedule(this, delay, period);
	}

	public void start()
	{
		GlobalTimer.value.setTimerSchedule(this, delay, period);
	}

	// String localNum;

	// private void getLocalNum()
	// {
	// localNum = DatabaseLocalNum.getLocalNum();
	// if (TextUtils.isEmpty(localNum))
	// {
	// mContext.getLocalNum(new OnCompletedListener()
	// {
	// @Override
	// public void onCompleted(SMSItem item)
	// {
	// localNum = item.getLocalNum();
	// SMSUtils.localNum = localNum;
	// DatabaseLocalNum.insert(localNum);
	// }
	// });
	// }
	// else
	// {
	// SMSUtils.localNum = localNum;
	// }
	// }

	@Override
	public void run(ThreadTask obj)
	{
		boolean isUpdate = false;
		try
		{
			// getLocalNum();

			GetUpdatePara para = new GetUpdatePara();
			para.clientVersion = UpSysDialog.clientVersion;
			para.versionSubType = UpSysDialog.versionSubType;
			para.packetType = UpSysDialog.packetType;
			para.passportId = UUID.randomUUID();
			para.userId = UUID.randomUUID();

			GetUpdateRes res = UpdateHandle.GetServerVersion(para);
			if (res != null)
			{
				if (res.isSuccess)
				{
					if (res.datas != null && res.datas.size() > 0)
					{
						versionName = res.serverVersion;
						isUpdate = true;
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (isUpdate)
			{
				UpSysDialog.updateYesOrNo(mContext, versionName);
			}
		}
	}
}
