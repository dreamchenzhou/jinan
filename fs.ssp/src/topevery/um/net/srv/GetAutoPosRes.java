package topevery.um.net.srv;

import java.util.ArrayList;

import topevery.framework.runtime.serialization.IBinarySerializable;
import topevery.framework.runtime.serialization.IObjectBinaryReader;
import topevery.framework.runtime.serialization.IObjectBinaryWriter;
import topevery.framework.runtime.serialization.RemoteClassAlias;

@SuppressWarnings("serial")
@RemoteClassAlias({ "Topevery.DUM.SocketShiMin.Srv.GetAutoPosRes" })
public class GetAutoPosRes implements IBinarySerializable
{
	public boolean isSuccess = true;
	public String errorMessage = "";
	public ArrayList<String> dataList = new ArrayList<String>();

	@Override
	public void readObjectData(IObjectBinaryReader reader)
	{
		isSuccess = reader.readBoolean();
		errorMessage = reader.readUTF();
		int count = reader.readInt32();
		for (int i = 0; i < count; i++)
		{
			dataList.add(reader.readUTF());
		}
	}

	@Override
	public void writeObjectData(IObjectBinaryWriter writer)
	{
		writer.writeBoolean(isSuccess);
		writer.writeUTF(errorMessage);

		int count = dataList.size();
		writer.writeInt32(count);
		for (int i = 0; i < count; i++)
		{
			writer.writeUTF(dataList.get(i));
		}
	}
}
