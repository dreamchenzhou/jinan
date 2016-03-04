package ro.upload;

import java.util.ArrayList;
import java.util.List;

import ro.BaseRes;
import topevery.framework.runtime.serialization.IObjectBinaryReader;
import topevery.framework.runtime.serialization.IObjectBinaryWriter;
import topevery.framework.runtime.serialization.RemoteClassAlias;

@SuppressWarnings("serial")
@RemoteClassAlias({ "RO.UploadStateRes" })
public class UploadStateRes extends BaseRes
{
	public static final UploadStateRes errorVal = new UploadStateRes();
	static
	{
		setNullValueError(errorVal);
	}

	public List<Integer> positions = new ArrayList<Integer>();

	@Override
	public void readData(IObjectBinaryReader reader)
	{
		int length = reader.readInt32();
		for (int i = 0; i < length; i++)
		{
			positions.add(reader.readInt32());
		}
	}

	@Override
	public void writeData(IObjectBinaryWriter writer)
	{
		int length = positions.size();
		writer.writeInt32(length);
		for (int i = 0; i < length; i++)
		{
			writer.writeInt32(positions.get(i));
		}
	}
}
