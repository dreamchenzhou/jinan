package topevery.um.net.srv;

import java.util.UUID;

import topevery.framework.commonModel.StaticHelper;
import topevery.framework.runtime.serialization.IBinarySerializable;
import topevery.framework.udp.UdpServiceSendMonitoring;
import topevery.um.net.UmUdpService;
import topevery.um.net.UmUdpServicePacketType;

public class ServiceHandle
{
	public static UUID userId;
	public static UUID passportId;

	/**
	 * 案件上报
	 * 
	 * @param para
	 * @return
	 */
	public static EvtRes ReportEvtInfo(EvtPara para)
	{
		EvtRes result = null;
		byte[] sendData = getParaBytes(UmUdpServicePacketType.Service.ReportEvtInfo, para);
		UdpServiceSendMonitoring monitoring = UmUdpService.value.sendToServer(UmUdpServicePacketType.Srv, sendData);
		{
			monitoring.waitComplete();
			{
				if (monitoring.state == 2)
				{
					result = (EvtRes) StaticHelper.getDeserialize(monitoring.callbackData);
				}
			}
		}

		return result;
	}

	/**
	 * 案件查询
	 * 
	 * @param para
	 * @return
	 */
	public static EvtRes GetEvtInfo(EvtPara para)
	{
		EvtRes result = null;
		byte[] sendData = getParaBytes(UmUdpServicePacketType.Service.GetEvtInfo, para);
		UdpServiceSendMonitoring monitoring = UmUdpService.value.sendToServer(UmUdpServicePacketType.Srv, sendData);
		{
			monitoring.waitComplete();
			{
				if (monitoring.state == 2)
				{
					result = (EvtRes) StaticHelper.getDeserialize(monitoring.callbackData);
				}
			}
		}

		return result;
	}

	/**
	 * 自动地址列表
	 * 
	 * @param para
	 * @return
	 */
	public static GetAutoPosRes GetAutoPos(GetAutoPosPara para)
	{
		GetAutoPosRes result = null;
		byte[] sendData = getParaBytes(UmUdpServicePacketType.Service.GetAutoPos, para);
		UdpServiceSendMonitoring monitoring = UmUdpService.value.sendToServer(UmUdpServicePacketType.Srv, sendData);
		{
			monitoring.waitComplete();
			{
				if (monitoring.state == 2)
				{
					result = (GetAutoPosRes) StaticHelper.getDeserialize(monitoring.callbackData);
				}
			}
		}

		return result;
	}

	public static byte[] getParaBytes(int commandType, IBinarySerializable para)
	{
		ServicePara result = new ServicePara();
		result.commandType = commandType;
		result.paraValue = para;
		return StaticHelper.getSerializable(result);
	}
}
