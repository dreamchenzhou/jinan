package topevery.um.net;

import topevery.framework.commonModel.Log;
import topevery.framework.commonModel.StaticHelper;
import topevery.framework.runtime.serialization.IBinarySerializable;
import topevery.framework.udp.IPEndPoint;
import topevery.framework.udp.SocketTypeEnum;
import topevery.framework.udp.UdpService;
import topevery.framework.udp.UdpServiceSendMonitoring;
import topevery.um.com.Settings;
import topevery.um.net.bus.BusinessHandle;
import topevery.um.net.bus.BusinessPara;
import topevery.um.net.bus.Message;
import topevery.um.net.runtimeconfig.RuntimeConfig;
import topevery.um.net.srv.ServicePara;

public class UmUdpService extends UdpService
{
	private static final boolean isDebugger = false;

	public static IPEndPoint udpServerAddress = null;
	static
	{
		RuntimeConfig.registeRemoteClassAlias();

		initFinal();

		// initFinal("121.42.53.142", 4011);
		// initFinal("121.42.53.142", 7011);
	}

	public static void initFinal()
	{
		try
		{
			udpServerAddress = new IPEndPoint(Settings.getInstance().UdpIp, Settings.getInstance().UdpPort);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void initFinal(String udpIp, int udpPort)
	{
		try
		{
			udpServerAddress = new IPEndPoint(udpIp, udpPort);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static final UmUdpService value = getValue();

	private synchronized static final UmUdpService getValue()
	{
		UmUdpService result = null;
		try
		{
			result = new UmUdpService();
		}
		catch (Exception ex)
		{
			Log.value.write(ex);
		}
		return result;
	}

	protected UmUdpService() throws Exception
	{
		super();
		{
			// StaticHelper.setTimerSchedule(new ThreadTaskRunnable()
			// {
			// @Override
			// public void run(ThreadTask obj)
			// {
			// /**
			// * 用于发送心跳包
			// */
			// {
			// byte[] sendData = null;
			// {
			// UmUdpServiceHandsPara para = new UmUdpServiceHandsPara();
			// {
			// para.passportId = Environments.passportId;
			// para.userId = Environments.userId;
			// }
			// sendData = StaticHelper.getSerializable(para);
			// }
			// UdpServiceSendMonitoring sendMonitoring =
			// sendToServer(UmUdpServicePacketType.Hands, sendData);
			// {
			// sendMonitoring.waitComplete();
			// {
			// NotityStatus notityStatus = NotityStatus.notifyOffLine;
			// if (sendMonitoring.state == 2)
			// {
			// notityStatus = NotityStatus.notifyOnLine;
			// }
			//
			// NotifyHolder.value.onNotityClientGPRS(notityStatus);
			// }
			// }
			// }
			// }
			// }, 15000, 45000);
		}
	}

	public UdpServiceSendMonitoring sendToServer(int commandType, byte[] sendData)
	{
		return send(udpServerAddress, commandType, sendData);
	}

	@Override
	protected byte[] receiveCallbackDataHandle(IPEndPoint clientIpEndPoint, int commandType, byte[] receiveData)
	{
		byte[] result = null;
		switch (commandType)
		{
			case UmUdpServicePacketType.Bus:
				result = handleBus((BusinessPara) StaticHelper.getDeserialize(receiveData));
				break;
			case UmUdpServicePacketType.Srv:
				result = handleSrv((ServicePara) StaticHelper.getDeserialize(receiveData));
				break;
			default:
				result = new byte[0];
				break;
		}
		if (result == null)
		{
			result = new byte[0];
		}
		return result;
	}

	private byte[] handleBus(BusinessPara para)
	{
		switch (para.commandType)
		{
			case UmUdpServicePacketType.Business.BusinessMessage:
			case UmUdpServicePacketType.Business.CheckMessage:
			case UmUdpServicePacketType.Business.CheckPointInMessage:
			case UmUdpServicePacketType.Business.CheckPointNotInMessage:
			case UmUdpServicePacketType.Business.ClientMessage:
			case UmUdpServicePacketType.Business.EarlyMessage:
			case UmUdpServicePacketType.Business.GPSNotWorkMessage:
			case UmUdpServicePacketType.Business.LateMessage:
			case UmUdpServicePacketType.Business.OverGridMessage:
			case UmUdpServicePacketType.Business.OvertimeHeCha:
			case UmUdpServicePacketType.Business.OvertimeHeShi:
			case UmUdpServicePacketType.Business.StayMessage:
			case UmUdpServicePacketType.Business.UmEvtDelMessage:
				BusinessHandle.receiveMessage((Message) para.paraValue);
				break;
			case UmUdpServicePacketType.Business.ServerTime:
				break;

			case UmUdpServicePacketType.Business.Unkown:
				break;
		}
		return new byte[0];
	}

	private byte[] handleSrv(ServicePara para)
	{
		IBinarySerializable result = null;
		switch (para.commandType)
		{
			default:
				break;
		}
		return StaticHelper.getSerializable(result);
	}

	@Override
	protected String getUid()
	{
		if (!isDebugger)
			return "ty.uid";
		else
			return "topevery.debugger";
	}

	@Override
	protected String getPwd()
	{
		if (!isDebugger)
			return "ty.pwd";
		else
			return Long.toString(System.currentTimeMillis());
	}

	@Override
	protected void receiveCallbackDataHandleByNotState(IPEndPoint clientIpEndPoint, int commandType, byte[] receiveData)
	{

	}

	public void sendToServerByNoState(int commandType, byte[] sendData) throws Exception
	{
		super.sendByNotState(udpServerAddress, commandType, sendData);
	}

	@Override
	protected SocketTypeEnum getSocketType()
	{
		return SocketTypeEnum.UDP;
	}
}
