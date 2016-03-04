package topevery.um.net;

import java.io.Serializable;
import java.util.UUID;

import topevery.framework.runtime.serialization.IBinarySerializable;
import topevery.framework.runtime.serialization.IObjectBinaryReader;
import topevery.framework.runtime.serialization.IObjectBinaryWriter;
import topevery.framework.runtime.serialization.RemoteClassAlias;

@SuppressWarnings("serial")
@RemoteClassAlias({ "Topevery.DUM.SocketShiMin.NameValue" })
public class NameValue implements Serializable, IBinarySerializable
{
	public String name;

	public UUID value;

	public int typeLable;

	public boolean isChecked = false;

	@Override
	public void readObjectData(IObjectBinaryReader reader)
	{
		name = reader.readUTF();
		value = reader.readGuid();
		typeLable = reader.readInt32();
	}

	@Override
	public void writeObjectData(IObjectBinaryWriter writer)
	{
		writer.writeUTF(name);
		writer.writeGuid(value);
		writer.writeInt32(typeLable);
	}

	@Override
	public String toString()
	{
		return name;
	}
}
