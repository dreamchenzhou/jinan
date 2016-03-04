package topevery.um.com.task;

import global.ApplicationCore;
import topevery.android.core.MsgBox;
import topevery.um.com.activity.LoginActivity;
import topevery.um.com.activity.RegisterActivity;
import topevery.um.com.main.MainProessDialog;
import topevery.um.com.utils.ToastUtils;
import topevery.um.jinan.manager.R;
import topevery.um.net.ServiceHandle;
import topevery.um.net.newbean.LoginResult;
import topevery.um.net.newbean.UserResInfo;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;

/**
 * 
 * 	注册任务
 */
public class RegistTask extends AsyncTask<UserResInfo, Void, LoginResult> {

	private RegisterActivity mContext;
	private Dialog mDialog;
	
	public RegistTask(RegisterActivity activity){
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
			ToastUtils.show(mContext,R.string.register_success);
			Intent intent=new Intent(mContext,LoginActivity.class);
			mContext.startActivity(intent);
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
	protected LoginResult doInBackground(UserResInfo... params) {
		UserResInfo para = params[0];
		LoginResult result = null;
		try {
			result = ServiceHandle.Register(para);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}



	
}
