package topevery.um.net;

import java.io.Serializable;
import java.util.UUID;

import topevery.framework.runtime.serialization.IBinarySerializable;
import topevery.framework.runtime.serialization.IObjectBinaryReader;
import topevery.framework.runtime.serialization.IObjectBinaryWriter;
import topevery.framework.runtime.serialization.RemoteClassAlias;

@SuppressWarnings("serial")
@RemoteClassAlias({ "Topevery.DUM.SocketShiMin.UmUdpServiceHandsPara" })
public class UmUdpServiceHandsPara implements Serializable, IBinarySerializable
{

	public UUID passportId;
	public UUID userId;

	@Override
	public void readObjectData(IObjectBinaryReader reader)
	{
		passportId = reader.readGuid();
		userId = reader.readGuid();
	}

	@Override
	public void writeObjectData(IObjectBinaryWriter writer)
	{
		writer.writeGuid(passportId);
		writer.writeGuid(userId);
	}

}
