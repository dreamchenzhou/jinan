package ro.upload;

import java.util.UUID;

import ro.BasePara;
import topevery.framework.runtime.serialization.IObjectBinaryReader;
import topevery.framework.runtime.serialization.IObjectBinaryWriter;
import topevery.framework.runtime.serialization.RemoteClassAlias;
import topevery.framework.system.DateTime;

@SuppressWarnings("serial")
@RemoteClassAlias({ "RO.UploadFinishPara" })
public class UploadFinishPara extends BasePara
{
	public UUID id;
	public String name;
	/**
	 * 0城管通，1领导通，2工作通
	 */
	public int picType = 0;
	/**
	 * 手机当前时间
	 */
	public DateTime pdaTime;
	/**
	 * 图片拍照时间
	 */
	public DateTime picTime;
	public DateTime syncTime;

	@Override
	public void readData(IObjectBinaryReader reader)
	{
		id = reader.readGuid();
		name = reader.readUTF();
		picType = reader.readInt32();
		pdaTime = reader.readDateTime();
		picTime = reader.readDateTime();
		syncTime = reader.readDateTime();
	}

	@Override
	public void writeData(IObjectBinaryWriter writer)
	{
		writer.writeGuid(id);
		writer.writeUTF(name);
		writer.writeInt32(picType);
		writer.writeDateTime(pdaTime);
		writer.writeDateTime(picTime);
		writer.writeDateTime(syncTime);
	}
}