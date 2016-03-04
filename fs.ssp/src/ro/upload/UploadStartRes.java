package ro.upload;

import ro.BaseRes;
import topevery.framework.runtime.serialization.IObjectBinaryReader;
import topevery.framework.runtime.serialization.IObjectBinaryWriter;
import topevery.framework.runtime.serialization.RemoteClassAlias;

@SuppressWarnings("serial")
@RemoteClassAlias({ "RO.UploadStartRes" })
public class UploadStartRes extends BaseRes
{
	public static final UploadStartRes errorVal = new UploadStartRes();
	static
	{
		errorVal.isSuccess = false;
		errorVal.errorMessage = "Null Value Error!";
	}

	/**
	 * 是否已经存在了
	 * */
	public boolean isExists = false;

	@Override
	public void readData(IObjectBinaryReader reader)
	{
		isExists = reader.readBoolean();
	}

	@Override
	public void writeData(IObjectBinaryWriter writer)
	{
		writer.writeBoolean(isExists);
	}
}