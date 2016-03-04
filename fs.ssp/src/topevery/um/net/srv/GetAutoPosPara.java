package topevery.um.net.srv;

import topevery.framework.runtime.serialization.IBinarySerializable;
import topevery.framework.runtime.serialization.IObjectBinaryReader;
import topevery.framework.runtime.serialization.IObjectBinaryWriter;
import topevery.framework.runtime.serialization.RemoteClassAlias;

@SuppressWarnings("serial")
@RemoteClassAlias({ "Topevery.DUM.SocketShiMin.Srv.GetAutoPosPara" })
public class GetAutoPosPara implements IBinarySerializable
{
	public String evtPos = "";

	@Override
	public void readObjectData(IObjectBinaryReader reader)
	{
		evtPos = reader.readUTF();
	}

	@Override
	public void writeObjectData(IObjectBinaryWriter writer)
	{
		writer.writeUTF(evtPos);
	}
}
