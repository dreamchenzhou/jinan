package topevery.um.com.task;

import topevery.um.com.activity.ResetPWActivity;
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
import android.os.AsyncTask;

/**
 * 
 * 	重置密码任务
 */
public class ResetPWTask extends AsyncTask<ParaFromPda, Void, LoginResult> {

	private ResetPWActivity mContext;
	
	private Dialog mDialog;
	public ResetPWTask(ResetPWActivity activity){
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
			ToastUtils.show(mContext,R.string.reset_success);
			LoginUtils.loginOut();
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
			result = ServiceHandle.resetPW(para);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
