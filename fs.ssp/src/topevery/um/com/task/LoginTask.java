package topevery.um.com.task;

import topevery.um.com.Constants;
import topevery.um.com.activity.LoginActivity;
import topevery.um.com.main.MainProessDialog;
import topevery.um.com.utils.LoginUtils;
import topevery.um.com.utils.SpUtils;
import topevery.um.com.utils.ToastUtils;
import topevery.um.jinan.manager.R;
import topevery.um.net.ServiceHandle;
import topevery.um.net.newbean.LoginResult;
import topevery.um.net.newbean.ParaFromPda;
import topevery.um.net.newbean.UserCache;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;

/**
 * 
 * 	登录任务
 */
public class LoginTask extends AsyncTask<ParaFromPda, Void, LoginResult> {

	private LoginActivity mContext;
	
	private Dialog mDialog;
	public LoginTask(LoginActivity activity){
		this.mContext = activity;
	}
	@Override
	protected void onPostExecute(LoginResult result) {
		mDialog.dismiss();
		if(result==null){
			ToastUtils.show(mContext,R.string.net_error);
			return;
		}
		if(result.isSuccess()){
			ToastUtils.show(mContext,R.string.login_success);
			LoginUtils.loginIn(result);
			mContext.saveAccount();
			mContext.finish();
		}else{
			ToastUtils.show(mContext,result.getErrorMessage());
		}
		super.onPostExecute(result);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mDialog = MainProessDialog.createLoadingDialog(mContext, mContext.getString(R.string.processing), false, false);
		mDialog.show();
	}

	@Override
	protected LoginResult doInBackground(ParaFromPda... params) {
		ParaFromPda para = params[0];
		LoginResult result = null;
		try {
			result = ServiceHandle.Login(para);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
