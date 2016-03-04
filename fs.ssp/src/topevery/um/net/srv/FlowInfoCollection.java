package topevery.um.net.srv;

import java.util.ArrayList;

import topevery.framework.runtime.serialization.IBinarySerializable;
import topevery.framework.runtime.serialization.IObjectBinaryReader;
import topevery.framework.runtime.serialization.IObjectBinaryWriter;
import topevery.framework.runtime.serialization.RemoteClassAlias;

@SuppressWarnings("serial")
@RemoteClassAlias({ "Topevery.DUM.SocketShiMin.Srv.FlowInfoCollection" })
public class FlowInfoCollection extends ArrayList<FlowInfo> implements IBinarySerializable
{
	private int length;

	@Override
	public void readObjectData(IObjectBinaryReader reader)
	{
		length = reader.readInt32();
		for (int i = 0; i < length; i++)
		{
			this.add((FlowInfo) reader.readObject());
		}
	}

	@Override
	public void writeObjectData(IObjectBinaryWriter writer)
	{
		length = this.size();
		writer.writeInt32(length);
		for (int i = 0; i < length; i++)
		{
			writer.writeObject(this.get(i));
		}
	}

}
