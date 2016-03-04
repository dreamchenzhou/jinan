//package topevery.um.net.up;
//
//import java.io.Serializable;
//import java.util.UUID;
//
//import topevery.framework.runtime.serialization.IBinarySerializable;
//import topevery.framework.runtime.serialization.IObjectBinaryReader;
//import topevery.framework.runtime.serialization.IObjectBinaryWriter;
//import topevery.framework.runtime.serialization.RemoteClassAlias;
//
//@SuppressWarnings("serial")
//@RemoteClassAlias({ "Topevery.DUM.SocketShiMin.Up.UploadStartPara" })
//public class UploadStartPara implements Serializable, IBinarySerializable
//{
//	public UUID id;
//	public int length;
//
//	@Override
//	public void readObjectData(IObjectBinaryReader reader)
//	{
//		id = reader.readGuid();
//		length = reader.readInt32();
//	}
//
//	@Override
//	public void writeObjectData(IObjectBinaryWriter writer)
//	{
//		writer.writeGuid(id);
//		writer.writeInt32(length);
//	}
// }
