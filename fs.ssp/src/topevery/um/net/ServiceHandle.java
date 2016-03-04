package topevery.um.net;

import ro.BasePara;
import topevery.um.net.newbean.BaseRes;
import topevery.um.net.newbean.BaseSearchPara;
import topevery.um.net.newbean.EvtParaForIos;
import topevery.um.net.newbean.EvtRes;
import topevery.um.net.newbean.LoginResult;
import topevery.um.net.newbean.ParaFromPda;
import topevery.um.net.newbean.UserResInfo;

public class ServiceHandle
{
	public static final void setNullValueError(BaseRes val)
	{
		val.setSuccess(false);
		val.setErrorMessage("网络故障");
	}

	public static final void setNullValueError(BaseRes val, Exception e)
	{
		val.setSuccess(false);
		val.setErrorMessage(e.getMessage());
	}

	/**
	 * 注册
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public static LoginResult Register(UserResInfo para) throws Exception
	{
		return PdaService.invoke(SoapMethodAndkey.KEY_REGIST,para, SoapMethodAndkey.METHOD_REGISTER, LoginResult.class);
	}
	
	/**
	 * 登录
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public static LoginResult Login(ParaFromPda para) throws Exception
	{
		return PdaService.invoke(SoapMethodAndkey.KEY_LOGIN,para, SoapMethodAndkey.METHOD_LOGIN, LoginResult.class);
	}
	
	/**
	 * 重置密码
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public static LoginResult resetPW(ParaFromPda para)throws Exception{
//		para.setUserID(Environments.getUserId());
		return PdaService.invoke(SoapMethodAndkey.KEY_RESET_PW,para, SoapMethodAndkey.METHOD_RESET_PW, LoginResult.class);
	}
	/**
	 * 微信授权
	 * 传入授权的票据
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public static LoginResult wxAuthoriz(String para)throws Exception{
//		para.setUserID(Environments.getUserId());
		return PdaService.invoke(SoapMethodAndkey.KEY_WX_LOGIN,para, SoapMethodAndkey.METHOD_WX_LOGIN, LoginResult.class);
	}
	
	/**
	 * 上报案件
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public static EvtRes reportEvt(EvtParaForIos para)throws Exception{
//		para.setUserID(Environments.getUserId());
		return PdaService.invoke(SoapMethodAndkey.KEY_REPORT_EVT,para, SoapMethodAndkey.METHOD_REPORT_EVT, EvtRes.class);
	}
	
	/**
	 * 获取历史案件记录
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public static EvtRes GetHistoryEvt(EvtParaForIos para)throws Exception{
//		para.setUserID(Environments.getUserId());
		return PdaService.invoke(SoapMethodAndkey.KEY_GET_HISTORY_EVT,para, SoapMethodAndkey.METHOD_GET_HISTORY_EVT, EvtRes.class);
	}
}
