package topevery.um.net.newbean;

import java.io.Serializable;

public class ParaFromPda implements Serializable {
	private String loginName;
    private String loginPsw;

    private String newPswToRest;

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

	public String getNewPswToRest() {
		return newPswToRest;
	}

	public void setNewPswToRest(String newPswToRest) {
		this.newPswToRest = newPswToRest;
	}
}
