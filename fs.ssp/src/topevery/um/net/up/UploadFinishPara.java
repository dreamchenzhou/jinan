﻿//package topevery.um.net.up;
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
//@RemoteClassAlias({ "Topevery.DUM.SocketShiMin.Up.UploadFinishPara" })
//public class UploadFinishPara implements Serializable, IBinarySerializable
//{
//	public UUID userId;
//	public UUID passportId;
//	public UUID id;
//	public String name;
//
//	@Override
//	public void readObjectData(IObjectBinaryReader reader)
//	{
//		userId = reader.readGuid();
//		passportId = reader.readGuid();
//		id = reader.readGuid();
//		name = reader.readUTF();
//	}
//
//	@Override
//	public void writeObjectData(IObjectBinaryWriter writer)
//	{
//		writer.writeGuid(userId);
//		writer.writeGuid(passportId);
//		writer.writeGuid(id);
//		writer.writeUTF(name);
//	}
// }