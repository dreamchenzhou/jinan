package topevery.um.net.newbean;

import java.io.Serializable;

public class UserResInfo implements Serializable {
	  //手机号
    public String phoneNo;
     //登陆名
    public String loginName;
     //密码
    public String loginPsw;
     //姓名
    public String userName;
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getLoginPsw() {
		return loginPsw;
	}
	public void setLoginPsw(String loginPsw) {
		this.loginPsw = loginPsw;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
    
}
