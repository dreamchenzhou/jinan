package topevery.um.net.update;

import java.io.Serializable;
import java.util.UUID;

import topevery.framework.runtime.serialization.IBinarySerializable;
import topevery.framework.runtime.serialization.IObjectBinaryReader;
import topevery.framework.runtime.serialization.IObjectBinaryWriter;
import topevery.framework.runtime.serialization.RemoteClassAlias;

@SuppressWarnings("serial")
@RemoteClassAlias({ "Topevery.DUM.SocketShiMin.Update.GetUpdatePara" })
public class GetUpdatePara implements Serializable, IBinarySerializable
{
	public UUID userId;
	public UUID passportId;
	public String clientVersion;
	public String versionSubType;
	public int packetType;

	@Override
	public void readObjectData(IObjectBinaryReader reader)
	{
		userId = reader.readGuid();
		passportId = reader.readGuid();
		clientVersion = reader.readUTF();
		versionSubType = reader.readUTF();
		packetType = reader.readInt32();
	}

	@Override
	public void writeObjectData(IObjectBinaryWriter writer)
	{
		writer.writeGuid(userId);
		writer.writeGuid(passportId);
		writer.writeUTF(clientVersion);
		writer.writeUTF(versionSubType);
		writer.writeInt32(packetType);
	}
}
