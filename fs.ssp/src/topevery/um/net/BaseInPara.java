package topevery.um.net;

import java.io.Serializable;
import java.util.UUID;

import topevery.framework.runtime.serialization.IBinarySerializable;
import topevery.framework.runtime.serialization.IObjectBinaryReader;
import topevery.framework.runtime.serialization.IObjectBinaryWriter;
import topevery.framework.runtime.serialization.RemoteClassAlias;

@SuppressWarnings("serial")
@RemoteClassAlias({ "Topevery.DUM.SocketShiMin.BaseInPara" })
public class BaseInPara implements Serializable, IBinarySerializable
{
	public UUID userId;
	public UUID passportId;

	@Override
	public void readObjectData(IObjectBinaryReader reader)
	{
		userId = reader.readGuid();
		passportId = reader.readGuid();
	}

	@Override
	public void writeObjectData(IObjectBinaryWriter writer)
	{
		writer.writeGuid(userId);
		writer.writeGuid(passportId);
	}
}
