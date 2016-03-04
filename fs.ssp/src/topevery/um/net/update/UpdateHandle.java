package topevery.um.net.update;

import topevery.framework.commonModel.StaticHelper;
import topevery.framework.runtime.serialization.IBinarySerializable;
import topevery.framework.udp.UdpServiceSendMonitoring;
import topevery.um.net.UmUdpService;
import topevery.um.net.UmUdpServicePacketType;
import android.util.Log;

public class UpdateHandle
{

	/**
	 * 根据客户端版本号，获取服务器端更新列表
	 * 
	 * @param para
	 * @return
	 */
	public static GetUpdateRes GetServerVersion(GetUpdatePara para)
	{
		GetUpdateRes result = null;
		byte[] data = getParaBytes(UmUdpServicePacketType.Update.GetServerVersion, para);
		UdpServiceSendMonitoring monitoring = UmUdpService.value.sendToServer(UmUdpServicePacketType.UpSys, data);
		{
			monitoring.waitComplete();
			{
				if(monitoring.state == 2)
				{
					result = (GetUpdateRes) StaticHelper.getDeserialize(monitoring.callbackData);
				}
			}
		}
		return result;
	}

	public static void Test()
	{
		try
		{

			GetUpdatePara para = new GetUpdatePara();
			para.versionSubType = "android";
			para.packetType = 0;
			para.clientVersion = "1.0.6";
			GetUpdateRes res = GetServerVersion(para);
			if(res != null)
			{
				if(res.isSuccess)
				{
					Log.d("fuck", "fuck");
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static byte[] getParaBytes(int commandType, IBinarySerializable para)
	{
		UpdatePara result = new UpdatePara();
		result.commandType = commandType;
		result.paraValue = para;
		return StaticHelper.getSerializable(result);
	}
}
