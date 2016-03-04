package topevery.um.net.srv;

import java.io.File;

import topevery.framework.runtime.serialization.IBinarySerializable;
import topevery.framework.runtime.serialization.IObjectBinaryReader;
import topevery.framework.runtime.serialization.IObjectBinaryWriter;
import topevery.framework.runtime.serialization.RemoteClassAlias;
import topevery.um.com.data.CaseAccept;
import topevery.um.com.data.CaseType;

@SuppressWarnings("serial")
@RemoteClassAlias({ "Topevery.DUM.SocketShiMin.Srv.EvtRes" })
public class EvtRes implements IBinarySerializable
{
	public CaseAccept caseAccept;
	public CaseType caseType;
	public boolean isSuccess = true;
	public String errorMessage = "";
	// / <summary>
	// / 上报成功，返回受理号
	// / </summary>
	public String evtCode = "";
	public EvtPara evtPara = new EvtPara();

	public File file = null;
	public FlowInfoCollection flowInfos = new FlowInfoCollection();
	public String datetime = "";

	@Override
	public void readObjectData(IObjectBinaryReader reader)
	{
		try
		{
			isSuccess = reader.readBoolean();
			errorMessage = reader.readUTF();
			evtCode = reader.readUTF();
			evtPara = (EvtPara) reader.readObject();
			flowInfos = (FlowInfoCollection) reader.readObject();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void writeObjectData(IObjectBinaryWriter writer)
	{
		writer.writeBoolean(isSuccess);
		writer.writeUTF(errorMessage);
		writer.writeUTF(evtCode);
		writer.writeObject(evtPara);
		writer.writeObject(flowInfos);
	}
}
