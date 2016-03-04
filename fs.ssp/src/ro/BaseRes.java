package ro;

import topevery.framework.runtime.serialization.IBinarySerializable;
import topevery.framework.runtime.serialization.IObjectBinaryReader;
import topevery.framework.runtime.serialization.IObjectBinaryWriter;

@SuppressWarnings("serial")
public abstract class BaseRes implements IBinarySerializable
{
	/**
	 * 是否成功
	 */
	public boolean isSuccess = true;

	/**
	 * 错误消息
	 */
	public String errorMessage;

	public abstract void readData(IObjectBinaryReader reader);

	public abstract void writeData(IObjectBinaryWriter writer);

	@Override
	public void readObjectData(IObjectBinaryReader reader)
	{
		isSuccess = reader.readBoolean();
		errorMessage = reader.readUTF();
		readData(reader);
	}

	@Override
	public void writeObjectData(IObjectBinaryWriter writer)
	{
		writer.writeBoolean(isSuccess);
		writer.writeUTF(errorMessage);
		writeData(writer);
	}

	protected static final void setNullValueError(BaseRes val)
	{
		val.isSuccess = false;
		val.errorMessage = "网络故障";
	}
	
}
