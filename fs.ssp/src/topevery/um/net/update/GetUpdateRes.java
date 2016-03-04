package topevery.um.net.update;

import java.io.Serializable;

import topevery.framework.runtime.serialization.IBinarySerializable;
import topevery.framework.runtime.serialization.IObjectBinaryReader;
import topevery.framework.runtime.serialization.IObjectBinaryWriter;
import topevery.framework.runtime.serialization.RemoteClassAlias;

@SuppressWarnings("serial")
@RemoteClassAlias({ "Topevery.DUM.SocketShiMin.Update.GetUpdateRes" })
public class GetUpdateRes implements Serializable, IBinarySerializable
{
	public boolean isSuccess = true;
	public String errorMessage;
	public String serverVersion;
	public String mainAppName;
	public boolean isRestartApp;
	public UpdateCollection datas = new UpdateCollection();

	@Override
	public void readObjectData(IObjectBinaryReader reader)
	{
		isSuccess = reader.readBoolean();
		errorMessage = reader.readUTF();
		serverVersion = reader.readUTF();
		mainAppName = reader.readUTF();
		isRestartApp = reader.readBoolean();
		datas = (UpdateCollection) reader.readObject();
	}

	@Override
	public void writeObjectData(IObjectBinaryWriter writer)
	{
		writer.writeBoolean(isSuccess);
		writer.writeUTF(errorMessage);
		writer.writeUTF(serverVersion);
		writer.writeUTF(mainAppName);
		writer.writeBoolean(isRestartApp);
		writer.writeObject(datas);
	}
}
