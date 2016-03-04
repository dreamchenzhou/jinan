package topevery.um.net.newbean;

import global.ApplicationCore;

import java.io.Serializable;

import topevery.um.com.utils.SpUtils;

public class UserCache implements Serializable {
	
	private static UserCache mCache;
	
	public static final String IS_LOGIN = "is_login";	
	public static final String KEY_USER_ID = "user_id";
	public static final String KEY_USER_NAME = "user_name";
	public static final String KEY_USER_LoginNAME = "login_name";
	public static final String KEY_USER_PW = "user_pw";
	public static final String KEY_PHONE_NUMBER = "phone_number";
	public static final String 	KEY_HEAD_IMAGE_URL = "head_image_url";
	/**
	 * 用户id
	 */
	private static int userId;
	
	/**
	 * 用户名
	 */
	private static String userName;
	
	/**
	 * 账号
	 */
	private static String loginName;
	/**
	 * 登录密码
	 */
	private static String loginPW;
	
	/**
	 * 电话号码
	 */
	private static String phoneNumber;
	
	
	/**
	 * 用户头像
	 */
	private static String headImageUrl;

	private  UserCache(){
		
	}
	
	public static UserCache getInstance(){
		if(mCache==null){
			mCache = new UserCache();
		}
		return mCache;
	}
	
	
	public void setResult(LoginResult result){
		if(result.isSuccess()){
			if(result.getName()!=null){
				setUserName(result.getName());
			}
			if(result.getLoginName()!=null){
				setLoginName(result.getLoginName());
			}
			if(result.getLoginPsw()!=null){
				setLoginPW(result.getLoginPsw());
			}
			setUserId(result.getId());
			if(result.getHeadImgUrl()!=null){
				setHeadImageUrl(result.getHeadImgUrl());
			}
			SpUtils.putBoolean(ApplicationCore.getInstance(), UserCache.IS_LOGIN, true);
		}
	}
	public String getUserName() {
		return SpUtils.getString(ApplicationCore.getInstance(), KEY_USER_NAME);
	}

	public void setUserName(String userName) {
		SpUtils.putString(ApplicationCore.getInstance(), KEY_USER_NAME,userName);
	}

	public String getLoginName() {
		return SpUtils.getString(ApplicationCore.getInstance(), KEY_USER_LoginNAME);
	}
	public void setLoginName(String loginName) {
		SpUtils.putString(ApplicationCore.getInstance(), KEY_USER_LoginNAME,loginName);
	}
	public String getLoginPW() {
		return SpUtils.getString(ApplicationCore.getInstance(), KEY_USER_PW);
	}
	public void setLoginPW(String loginPW) {
		SpUtils.putString(ApplicationCore.getInstance(), KEY_USER_PW,loginPW);
	}
	public String getPhoneNumber() {
		return SpUtils.getString(ApplicationCore.getInstance(), KEY_PHONE_NUMBER);
	}

	public void setPhoneNumber(String phoneNumber) {
		SpUtils.putString(ApplicationCore.getInstance(), KEY_PHONE_NUMBER,phoneNumber);
	}

	public int getUserId() {
		return SpUtils.getInt(ApplicationCore.getInstance(), KEY_USER_ID);
	}

	public void setUserId(int userId) {
		SpUtils.putInt(ApplicationCore.getInstance(), KEY_USER_ID,userId);
	}
	public String getHeadImageUrl() {
		return SpUtils.getString(ApplicationCore.getInstance(), KEY_HEAD_IMAGE_URL);
	}
	public void setHeadImageUrl(String headImageUrl) {
		SpUtils.putString(ApplicationCore.getInstance(), KEY_HEAD_IMAGE_URL,headImageUrl);
	}

	/**
	 * 清楚缓存
	 */
	public static void clearCache(){
		SpUtils.putBoolean(ApplicationCore.getInstance(), UserCache.IS_LOGIN, false);
		SpUtils.putInt(ApplicationCore.getInstance(), KEY_USER_ID,0);
		SpUtils.putString(ApplicationCore.getInstance(), KEY_USER_NAME,"");
		SpUtils.putString(ApplicationCore.getInstance(), KEY_USER_LoginNAME,"");
		SpUtils.putString(ApplicationCore.getInstance(), KEY_USER_PW,"");
		SpUtils.putString(ApplicationCore.getInstance(), KEY_HEAD_IMAGE_URL,"");
//		SpUtils.putString(ApplicationCore.getInstance(), KEY_PHONE_NUMBER,"");
		SpUtils.putString(ApplicationCore.getInstance(), KEY_HEAD_IMAGE_URL,"");
	}
	
}
