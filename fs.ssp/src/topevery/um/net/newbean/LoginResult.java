package topevery.um.net.newbean;

public class LoginResult extends BaseRes {
	/**
	 * 用户id
	 */
	private int Id;
	/**
     * 名称
     */
	private String Name = "";
	/**
     * 头像url
     */
	private String HeadImgUrl = "";
	/**
	 * 登录名称
	 */
	private String LoginName = "";
	/**
     * 登录密码
     */
	private String LoginPsw = "";
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getHeadImgUrl() {
		return HeadImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		HeadImgUrl = headImgUrl;
	}
	public String getLoginName() {
		return LoginName;
	}
	public void setLoginName(String loginName) {
		LoginName = loginName;
	}
	public String getLoginPsw() {
		return LoginPsw;
	}
	public void setLoginPsw(String loginPsw) {
		LoginPsw = loginPsw;
	}
	
}
