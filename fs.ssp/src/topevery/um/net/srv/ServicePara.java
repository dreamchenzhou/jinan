package topevery.um.net.srv;

import java.io.Serializable;

import topevery.framework.runtime.serialization.IBinarySerializable;
import topevery.framework.runtime.serialization.IObjectBinaryReader;
import topevery.framework.runtime.serialization.IObjectBinaryWriter;
import topevery.framework.runtime.serialization.RemoteClassAlias;

@SuppressWarnings("serial")
@RemoteClassAlias({ "Topevery.DUM.SocketShiMin.Srv.ServicePara" })
public class ServicePara implements Serializable, IBinarySerializable
{
	public int commandType;
	public IBinarySerializable paraValue;

	@Override
	public void readObjectData(IObjectBinaryReader reader)
	{
		commandType = reader.readInt32();
		paraValue = (IBinarySerializable) reader.readObject();
	}

	@Override
	public void writeObjectData(IObjectBinaryWriter writer)
	{
		writer.writeInt32(commandType);
		writer.writeObject(paraValue);
	}
}
