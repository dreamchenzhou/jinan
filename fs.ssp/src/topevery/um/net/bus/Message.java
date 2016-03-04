package topevery.um.net.bus;

import java.io.Serializable;
import java.util.UUID;

import topevery.framework.runtime.serialization.IBinarySerializable;
import topevery.framework.runtime.serialization.IObjectBinaryReader;
import topevery.framework.runtime.serialization.IObjectBinaryWriter;
import topevery.framework.runtime.serialization.RemoteClassAlias;

@SuppressWarnings("serial")
@RemoteClassAlias({ "Topevery.DUM.SocketShiMin.Bus.Message" })
public class Message implements Serializable, IBinarySerializable
{

	public int id;

	public UUID dataId;

	public int type;

	public String title;

	public String body;

	public topevery.framework.system.DateTime dateTime;

	@Override
	public void readObjectData(IObjectBinaryReader reader)
	{
		id = reader.readInt32();
		dataId = reader.readGuid();
		type = reader.readInt32();
		title = reader.readUTF();
		body = reader.readUTF();
		dateTime = reader.readDateTime();
	}

	@Override
	public void writeObjectData(IObjectBinaryWriter writer)
	{
		writer.writeInt32(id);
		writer.writeGuid(dataId);
		writer.writeUTF(title);
		writer.writeUTF(body);
		writer.writeDateTime(dateTime);
	}

	public boolean isNew = true;

	public long lastModified()
	{
		return dateTime.toJavaDate().getTime();
	}
}
