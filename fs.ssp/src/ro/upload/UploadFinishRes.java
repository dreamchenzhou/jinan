package ro.upload;

import ro.BaseRes;
import topevery.framework.runtime.serialization.IObjectBinaryReader;
import topevery.framework.runtime.serialization.IObjectBinaryWriter;
import topevery.framework.runtime.serialization.RemoteClassAlias;

@SuppressWarnings("serial")
@RemoteClassAlias({ "RO.UploadFinishRes" })
public class UploadFinishRes extends BaseRes
{
	public static final UploadFinishRes errorVal = new UploadFinishRes();
	static
	{
		errorVal.isSuccess = false;
		errorVal.errorMessage = "Null Value Error!";
	}

	@Override
	public void readData(IObjectBinaryReader reader)
	{

	}

	@Override
	public void writeData(IObjectBinaryWriter writer)
	{

	}
}