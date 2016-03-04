package topevery.um.net.newbean;

@SuppressWarnings("serial")
public class BaseOutRes extends BaseRes
{
	public long Id;
	public String Title;

	public long getId()
	{
		return Id;
	}

	public void setId(long id)
	{
		this.Id = id;
	}

	public String getTitle()
	{
		return Title;
	}

	public void setTitle(String title)
	{
		this.Title = title;
	}
}
