package topevery.um.com.utils;

import global.ApplicationCore;
import topevery.um.net.newbean.LoginResult;
import topevery.um.net.newbean.UserCache;

public class LoginUtils {
	public static void loginIn(LoginResult result){
		UserCache.getInstance().setResult(result);
	}
	
	public static void loginOut(){
		UserCache.clearCache();
	}
}
