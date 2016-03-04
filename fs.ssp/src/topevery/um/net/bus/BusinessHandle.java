package topevery.um.net.bus;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import topevery.framework.commonModel.StaticHelper;
import topevery.framework.runtime.serialization.IBinarySerializable;
import topevery.framework.udp.UdpServiceSendMonitoring;
import topevery.um.net.BaseInPara;
import topevery.um.net.UmUdpService;
import topevery.um.net.UmUdpServicePacketType;

public class BusinessHandle
{
	public static Queue<Message> listMessage = new ArrayBlockingQueue<Message>(16);

	/**
	 * 消息通知
	 * 
	 * @param para
	 */
	public static void receiveMessage(Message val)
	{
		listMessage.add(val);
	}

	public static Boolean sendKeepLive()
	{
		byte[] sendData = getBusinessParaBytes(UmUdpServicePacketType.Business.GPRS, null);
		UdpServiceSendMonitoring monitoring = UmUdpService.value.sendToServer(UmUdpServicePacketType.Bus, sendData);
		monitoring.waitComplete();
		if (monitoring.state == 2)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static Boolean sendGPSLocus(GPSPointCollection para)
	{
		byte[] sendData = getBusinessParaBytes(UmUdpServicePacketType.Business.GPS, para);
		UdpServiceSendMonitoring monitoring = UmUdpService.value.sendToServer(UmUdpServicePacketType.Bus, sendData);
		monitoring.waitComplete();
		if (monitoring.state == 2)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static void sendLoginOut(BaseInPara para)
	{
		byte[] sendData = getBusinessParaBytes(UmUdpServicePacketType.Business.LoginOut, para);
		UmUdpService.value.sendToServer(UmUdpServicePacketType.Bus, sendData);
	}

	private static byte[] getBusinessParaBytes(int commandType,
			IBinarySerializable para)
	{
		BusinessPara result = new BusinessPara();
		result.commandType = commandType;
		result.paraValue = para;
		return StaticHelper.getSerializable(result);
	}
}
