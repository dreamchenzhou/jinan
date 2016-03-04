package topevery.um.net.newbean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BaseRes implements Serializable
{
	private boolean IsSuccess = true;
	private String ErrorMessage;

	public boolean isSuccess()
	{
		return IsSuccess;
	}

	public void setSuccess(boolean isSuccess)
	{
		this.IsSuccess = isSuccess;
	}

	public String getErrorMessage()
	{
		return ErrorMessage;
	}

	public void setErrorMessage(String errorMessage)
	{
		this.ErrorMessage = errorMessage;
	}
}
