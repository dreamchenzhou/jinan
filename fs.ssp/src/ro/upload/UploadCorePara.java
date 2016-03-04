package ro.upload;

import java.util.UUID;

import topevery.framework.commonModel.Log;
import topevery.framework.io.MemoryStream;
import topevery.framework.runtime.serialization.ObjectBinaryInput;
import topevery.framework.runtime.serialization.ObjectBinaryOutput;
import topevery.framework.runtime.serialization.RemoteClassAlias;

@RemoteClassAlias({ "RO.UploadCorePara" })
public class UploadCorePara
{
	public UUID Id;
	public int Position;
	public byte[] Data;

	public byte[] Serialize()
	{
		byte[] result = null;

		MemoryStream ms = null;
		ObjectBinaryOutput binOut = null;
		try
		{
			ms = new MemoryStream();
			binOut = new ObjectBinaryOutput(ms);
			{
				binOut.writeGuid(Id);
				binOut.writeInt32(Position);
				binOut.writeInt32(Data.length);
				binOut.writeBytes(Data);
			}
			result = ms.toByteArray();
		}
		catch (Exception e)
		{
			Log.value.write(e);
		}
		finally
		{
			if (binOut != null)
			{
				binOut.close();
			}
			if (ms != null)
			{
				ms.close();
			}
		}
		return result;
	}

	public void Deserialize(byte[] val)
	{
		MemoryStream ms = null;
		ObjectBinaryInput binIn = null;
		try
		{
			ms = new MemoryStream(val, 0, val.length);
			binIn = new ObjectBinaryInput(ms);
			{
				Id = binIn.readGuid();
				Position = binIn.readInt32();
				int dataLength = binIn.readInt32();
				Data = binIn.readBytes(dataLength);
			}
		}
		catch (Exception ex)
		{
			Log.value.write(ex);
		}
		finally
		{
			if (binIn != null)
			{
				binIn.close();
			}
			if (ms != null)
			{
				ms.close();
			}
		}
	}
}
