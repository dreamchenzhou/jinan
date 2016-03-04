//package topevery.um.net.up;
//
//import java.io.Serializable;
//
//import topevery.framework.runtime.serialization.IBinarySerializable;
//import topevery.framework.runtime.serialization.IObjectBinaryReader;
//import topevery.framework.runtime.serialization.IObjectBinaryWriter;
//import topevery.framework.runtime.serialization.RemoteClassAlias;
//
//@SuppressWarnings("serial")
//@RemoteClassAlias({ "Topevery.DUM.SocketShiMin.Up.UploadFinishRes" })
//public class UploadFinishRes implements Serializable, IBinarySerializable
//{
//	/**
//	 * 是否成功
//	 */
//	public boolean isSuccess = true;
//
//	/**
//	 * 错误消息
//	 */
//	public String errorMessage;
//
//	@Override
//	public void readObjectData(IObjectBinaryReader reader)
//	{
//		isSuccess = reader.readBoolean();
//		errorMessage = reader.readUTF();
//	}
//
//	@Override
//	public void writeObjectData(IObjectBinaryWriter writer)
//	{
//		writer.writeBoolean(isSuccess);
//		writer.writeUTF(errorMessage);
//	}
// }