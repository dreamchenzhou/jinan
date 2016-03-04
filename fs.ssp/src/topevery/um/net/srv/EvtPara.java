package topevery.um.net.srv;

import java.io.File;

import topevery.framework.runtime.serialization.IBinarySerializable;
import topevery.framework.runtime.serialization.IObjectBinaryReader;
import topevery.framework.runtime.serialization.IObjectBinaryWriter;
import topevery.framework.runtime.serialization.RemoteClassAlias;

@SuppressWarnings("serial")
@RemoteClassAlias(
{ "Topevery.DUM.SocketShiMin.Srv.EvtPara" })
public class EvtPara implements IBinarySerializable
{
	public String linkman = "";// 联系人
	public String linkPhone = "";// 联系电话
	public String evtPos = "";// 案发地址
	public String evtDesc = "";// 案发描述
	public double absX;
	public double absY;
	public double geoX;
	public double geoY;
	public AttachInfoCollection attachs = new AttachInfoCollection();
	public String evtResult = "";// 处理结果
	// / <summary>
	// / 上报成功，返回受理号
	// / </summary>
	public String evtCode = "";

	// / <summary>
	// / 0:上报，1列表，2详细
	// / </summary>
	public int handleType = 0;

	public File file = null;

	public int PageIndex = 0;
	
	public String ReportDate;
	
	public String Title;
	@Override
	public void readObjectData(IObjectBinaryReader reader)
	{
		try
		{
			linkman = reader.readUTF();
			linkPhone = reader.readUTF();
			evtPos = reader.readUTF();
			evtDesc = reader.readUTF();
			absX = reader.readDouble();
			absY = reader.readDouble();
			geoX = reader.readDouble();
			geoY = reader.readDouble();
			attachs = (AttachInfoCollection) reader.readObject();
			evtResult = reader.readUTF();
			evtCode = reader.readUTF();
			handleType = reader.readInt32();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void writeObjectData(IObjectBinaryWriter writer)
	{
		writer.writeUTF(linkman);
		writer.writeUTF(linkPhone);
		writer.writeUTF(evtPos);
		writer.writeUTF(evtDesc);
		writer.writeDouble(absX);
		writer.writeDouble(absY);
		writer.writeDouble(geoX);
		writer.writeDouble(geoY);
		writer.writeObject(attachs);
		writer.writeUTF(evtResult);
		writer.writeUTF(evtCode);
		writer.writeInt32(handleType);
	}
}
