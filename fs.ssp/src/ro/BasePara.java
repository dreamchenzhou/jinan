package ro;

import java.util.UUID;

import topevery.android.framework.utils.UUIDUtils;
import topevery.framework.runtime.serialization.IBinarySerializable;
import topevery.framework.runtime.serialization.IObjectBinaryReader;
import topevery.framework.runtime.serialization.IObjectBinaryWriter;

@SuppressWarnings("serial")
public abstract class BasePara implements IBinarySerializable
{
	public int userId = 0;
	public UUID passportId = UUIDUtils.getUUIDEmpty();

	public abstract void readData(IObjectBinaryReader reader);

	public abstract void writeData(IObjectBinaryWriter writer);

	@Override
	public void readObjectData(IObjectBinaryReader reader)
	{
		userId = reader.readInt32();
		passportId = reader.readGuid();
		readData(reader);
	}

	@Override
	public void writeObjectData(IObjectBinaryWriter writer)
	{
		writer.writeInt32(userId);
		writer.writeGuid(passportId);
		writeData(writer);
	}
}
