package topevery.um.net.bus;

import java.io.Serializable;
import java.util.UUID;
import java.util.Vector;

import topevery.framework.runtime.serialization.IBinarySerializable;
import topevery.framework.runtime.serialization.IObjectBinaryReader;
import topevery.framework.runtime.serialization.IObjectBinaryWriter;
import topevery.framework.runtime.serialization.RemoteClassAlias;

@SuppressWarnings("serial")
@RemoteClassAlias({ "Topevery.DUM.SocketShiMin.Bus.GPSPointCollection" })
public class GPSPointCollection extends Vector<GPSPoint> implements Serializable, IBinarySerializable
{
	public UUID passportId;
	private int length;

	@Override
	public void readObjectData(IObjectBinaryReader reader)
	{
		passportId = reader.readGuid();
		length = reader.readInt32();
		for (int i = 0; i < length; i++)
		{
			this.add((GPSPoint) reader.readObject());
		}
	}

	@Override
	public void writeObjectData(IObjectBinaryWriter writer)
	{
		writer.writeGuid(passportId);
		length = this.size();
		writer.writeInt32(length);
		for (int i = 0; i < length; i++)
		{
			writer.writeObject(this.get(i));
		}
	}

}
