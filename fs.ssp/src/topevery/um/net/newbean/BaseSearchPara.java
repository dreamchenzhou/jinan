package topevery.um.net.newbean;

@SuppressWarnings("serial")
public class BaseSearchPara extends BasePara
{
	public int SearchType = 0;
	public int PageIndex = 1;
	public int PageSize = 10;

	public int getSearchType()
	{
		return SearchType;
	}

	public void setSearchType(int searchType)
	{
		this.SearchType = searchType;
	}

	public int getPageIndex()
	{
		return PageIndex;
	}

	public void setPageIndex(int pageIndex)
	{
		this.PageIndex = pageIndex;
	}

	public int getPageSize()
	{
		return PageSize;
	}

	public void setPageSize(int pageSize)
	{
		this.PageSize = pageSize;
	}
}
