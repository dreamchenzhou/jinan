package topevery.um.net.update;

import java.io.Serializable;
import java.util.UUID;

import topevery.framework.runtime.serialization.IBinarySerializable;
import topevery.framework.runtime.serialization.IObjectBinaryReader;
import topevery.framework.runtime.serialization.IObjectBinaryWriter;
import topevery.framework.runtime.serialization.RemoteClassAlias;

@SuppressWarnings("serial")
@RemoteClassAlias(
{ "Topevery.DUM.SocketShiMin.Update.UpdateItem" })
public class UpdateItem implements Serializable, IBinarySerializable
{
	public String fileName;
	public String fileVersion;
	public String clientRootFolder;
	public String clientSubFolder;
	public UUID fileID;
	public String downUrl;

	@Override
	public void readObjectData(IObjectBinaryReader reader)
	{
		fileName = reader.readUTF();
		fileVersion = reader.readUTF();
		clientRootFolder = reader.readUTF();
		clientSubFolder = reader.readUTF();
		fileID = reader.readGuid();
		downUrl = reader.readUTF();
	}

	@Override
	public void writeObjectData(IObjectBinaryWriter writer)
	{
		writer.writeUTF(fileName);
		writer.writeUTF(fileVersion);
		writer.writeUTF(clientRootFolder);
		writer.writeUTF(clientSubFolder);
		writer.writeGuid(fileID);
		writer.writeUTF(downUrl);
	}

	public String path = "";
}
