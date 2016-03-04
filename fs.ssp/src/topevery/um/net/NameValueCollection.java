package topevery.um.net;

import java.io.Serializable;
import java.util.Vector;

import topevery.framework.runtime.serialization.IBinarySerializable;
import topevery.framework.runtime.serialization.IObjectBinaryReader;
import topevery.framework.runtime.serialization.IObjectBinaryWriter;
import topevery.framework.runtime.serialization.RemoteClassAlias;

@SuppressWarnings("serial")
@RemoteClassAlias({ "Topevery.DUM.SocketShiMin.NameValueCollection" })
public class NameValueCollection extends Vector<NameValue> implements Serializable, IBinarySerializable
{

	@Override
	public void readObjectData(IObjectBinaryReader reader)
	{
		int length = reader.readInt32();
		for (int i = 0; i < length; i++)
		{
			this.add((NameValue) reader.readObject());
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
