package topevery.um.net;

public class SoapMethodAndkey {
	/************************************METHOD BEGIN***************************************/
	
	/**
	 * 注册
	 */
	public static final String METHOD_REGISTER = "UserRes";
	/**
	 * 登录
	 */
	public static final String METHOD_LOGIN = "UserLogin";
	/**
	 * 重置密码
	 */
	public static final String METHOD_RESET_PW = "UserPswReset";
	public static final String METHOD_LOGIN_OUT = "";
	/**
	 * 微信登录
	 */
	public static final String METHOD_WX_LOGIN = "UserWxLogin";
	/**
	 * 上报案件
	 */
	public static final String METHOD_REPORT_EVT = "ReportEvtInfo";
	/**
	 * 获取案件信息
	 */
	public static final String METHOD_GET_HISTORY_EVT = "GetEvtInfo";
	/************************************METHOD END***************************************/
	
	
	
	
	/************************************KEY BEGIN***************************************/
	/**
	 * 注册soap key
	 */
	public static final String KEY_REGIST ="resInfoJoson";
	/**
	 * 登录soap key
	 */
	public static final String KEY_LOGIN ="paraFromPdaJsonStr";
	/**
	 * 微信登录soap key
	 */
	public static final String KEY_WX_LOGIN ="Scope";
	/**
	 * 重置密码key
	 */
	public static final String KEY_RESET_PW ="paraFromPdaJsonStr";
	
	/**
	 * 上报key
	 */
	public static final String KEY_REPORT_EVT="paraJsonStr";
	
	/**
	 * 获取历史记录key
	 */
	public static final String KEY_GET_HISTORY_EVT ="paraJsonStr";
	/************************************KEY END***************************************/
}
