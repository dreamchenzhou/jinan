package topevery.um.com.service;

import java.lang.Thread.UncaughtExceptionHandler;

import topevery.android.framework.notify.NotifyHolder;
import topevery.android.framework.notify.NotityStatus;
import topevery.framework.commonModel.GlobalTimer;
import topevery.framework.commonModel.GlobalTimer.ThreadTask;
import topevery.framework.commonModel.GlobalTimer.ThreadTaskRunnable;
import topevery.framework.commonModel.StaticHelper;
import topevery.framework.udp.UdpServiceSendMonitoring;
import topevery.um.map.LocationTypeEnum;
import topevery.um.map.UmLocationClient;
import topevery.um.net.Environments;
import topevery.um.net.UmUdpService;
import topevery.um.net.UmUdpServiceHandsPara;
import topevery.um.net.UmUdpServicePacketType;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;

public class ServiceHolder extends Service implements UncaughtExceptionHandler
{
	boolean created = false;
	boolean exited = false;

	PowerManager.WakeLock wakeLock = null;

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		Thread.setDefaultUncaughtExceptionHandler(this);

		UmLocationClient.setContext(this, LocationTypeEnum.Tencent);
		UmLocationClient.start();
		acquireWakeLock();

		if (!created && !exited)
		{
			created = true;
			GlobalTimer.value.setTimerSchedule(new ThreadTaskRunnable()
			{
				@Override
				public void run(ThreadTask obj)
				{
					if (exited)
					{
						created = false;
						obj.period = 0;
					}
					else
					{
						sendData();
					}
				}
			}, 15000, 45000);
		}
	}

	@Override
	public void onDestroy()
	{
		UmLocationClient.stop();
		releaseWakeLock();
		super.onDestroy();

		exited = true;
	}

	private void acquireWakeLock()
	{
		if (wakeLock == null)
		{
			PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
			wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, this.getClass().getCanonicalName());
			wakeLock.acquire();
		}
	}

	private void releaseWakeLock()
	{
		if (wakeLock != null && wakeLock.isHeld())
		{
			wakeLock.release();
			wakeLock = null;
		}
	}

	private void sendData()
	{
		try
		{
			byte[] sendData = null;
			{
				UmUdpServiceHandsPara para = new UmUdpServiceHandsPara();
				{
					para.passportId = Environments.passportId;
					para.userId = Environments.userId;
				}
				sendData = StaticHelper.getSerializable(para);
			}
			UdpServiceSendMonitoring sendMonitoring = UmUdpService.value.sendToServer(UmUdpServicePacketType.Hands, sendData);
			{
				sendMonitoring.waitComplete();
				{
					NotityStatus notityStatus = NotityStatus.notifyOffLine;
					if (sendMonitoring.state == 2)
					{
						notityStatus = NotityStatus.notifyOnLine;
					}

					NotifyHolder.value.onNotityClientGPRS(notityStatus);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex)
	{
		ex.printStackTrace();
	}
}
