package topevery.um.net.newbean;

import java.io.Serializable;
import java.util.UUID;

import topevery.android.framework.utils.UUIDUtils;

@SuppressWarnings("serial")
public class BasePara implements Serializable
{
	public String objId;

	private int UserID = 0;
	private UUID PassportID = UUIDUtils.getUUIDEmpty();

	private long BaseID;

	private int BaseNameSpaceId;
	
	private String UserName;

	public int getUserID()
	{
		return UserID;
	}

	public void setUserID(int userID)
	{
		this.UserID = userID;
	}

	public UUID getPassportID()
	{
		return PassportID;
	}

	public void setPassportID(UUID passportID)
	{
		this.PassportID = passportID;
	}

	public long getBaseID()
	{
		return BaseID;
	}

	public void setBaseID(long baseID)
	{
		this.BaseID = baseID;
	}

	public int getBaseNameSpaceId()
	{
		return BaseNameSpaceId;
	}

	public void setBaseNameSpaceId(int baseNameSpaceId)
	{
		this.BaseNameSpaceId = baseNameSpaceId;
	}

	public long fid;

	public long getFid() {
		return fid;
	}

	public void setFid(long fid) {
		this.fid = fid;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}
	
}
