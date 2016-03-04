package ro.upload;

import java.util.UUID;

import topevery.framework.runtime.serialization.IBinarySerializable;
import topevery.framework.runtime.serialization.IObjectBinaryReader;
import topevery.framework.runtime.serialization.IObjectBinaryWriter;
import topevery.framework.runtime.serialization.RemoteClassAlias;

@SuppressWarnings("serial")
@RemoteClassAlias({ "RO.UploadStatePara" })
public class UploadStatePara implements IBinarySerializable
{
	public UUID id;
	/**
	 * 文件长度
	 */
	public int fileLength;
	/**
	 * 分包大小
	 */
	public int packetLength;
	/**
	 * 验证的起点
	 */
	public int beginPosition;
	/**
	 * 验证的结束点
	 */
	public int endPosition;

	@Override
	public void readObjectData(IObjectBinaryReader reader)
	{
		id = reader.readGuid();
		fileLength = reader.readInt32();
		packetLength = reader.readInt32();
		beginPosition = reader.readInt32();
		endPosition = reader.readInt32();
	}

	@Override
	public void writeObjectData(IObjectBinaryWriter writer)
	{
		writer.writeGuid(id);
		writer.writeInt32(fileLength);
		writer.writeInt32(packetLength);
		writer.writeInt32(beginPosition);
		writer.writeInt32(endPosition);
	}
}