package topevery.um.net.bus;

import java.io.Serializable;
import java.util.Vector;

import topevery.framework.runtime.serialization.IBinarySerializable;
import topevery.framework.runtime.serialization.IObjectBinaryReader;
import topevery.framework.runtime.serialization.IObjectBinaryWriter;
import topevery.framework.runtime.serialization.RemoteClassAlias;

@SuppressWarnings("serial")
@RemoteClassAlias({ "Topevery.DUM.SocketShiMin.Bus.MessageCollection" })
public class MessageCollection extends Vector<Message> implements Serializable, IBinarySerializable
{
	private int length;

	@Override
	public void readObjectData(IObjectBinaryReader reader)
	{
		length = reader.readInt32();
		for (int i = 0; i < length; i++)
		{
			this.add((Message) reader.readObject());
		}
	}

	@Override
	public void writeObjectData(IObjectBinaryWriter writer)
	{
		length = this.size();
		for (int i = 0; i < length; i++)
		{
			writer.writeObject(this.get(i));
		}
	}
}
