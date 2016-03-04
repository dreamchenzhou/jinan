package topevery.um.net.update;

import java.io.Serializable;
import java.util.Vector;

import topevery.framework.runtime.serialization.IBinarySerializable;
import topevery.framework.runtime.serialization.IObjectBinaryReader;
import topevery.framework.runtime.serialization.IObjectBinaryWriter;
import topevery.framework.runtime.serialization.RemoteClassAlias;

@SuppressWarnings("serial")
@RemoteClassAlias({ "Topevery.DUM.SocketShiMin.Update.UpdateCollection" })
public class UpdateCollection extends Vector<UpdateItem> implements Serializable, IBinarySerializable
{
	@Override
	public void readObjectData(IObjectBinaryReader reader)
	{
		int length = reader.readInt32();
		for (int i = 0; i < length; i++)
		{
			this.add((UpdateItem) reader.readObject());
		}
	}

	@Override
	public void writeObjectData(IObjectBinaryWriter writer)
	{
		int length = this.size();
		writer.writeInt32(length);
		for (int i = 0; i < length; i++)
		{
			writer.writeObject(this.get(i));
		}
	}
}
