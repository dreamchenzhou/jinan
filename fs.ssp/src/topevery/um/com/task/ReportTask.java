package topevery.um.com.task;

import java.util.List;
import java.util.UUID;

import topevery.um.com.activity.CaseReportNew;
import topevery.um.com.data.CaseAccept;
import topevery.um.com.data.CaseType;
import topevery.um.com.data.DatabaseAttach;
import topevery.um.com.data.DatabaseEvtRes;
import topevery.um.com.main.MainProessDialog;
import topevery.um.com.utils.ToastUtils;
import topevery.um.jinan.manager.R;
import topevery.um.net.ServiceHandle;
import topevery.um.net.newbean.AttachInfoCollection;
import topevery.um.net.newbean.EvtPara;
import topevery.um.net.newbean.EvtParaForIos;
import topevery.um.net.newbean.EvtRes;
import topevery.um.net.newbean.UserCache;
import topevery.um.net.up.UploadHandleHttp;
import android.app.Dialog;
import android.os.AsyncTask;

/**
 * 
 * 	上报任务
 */
public class ReportTask extends AsyncTask<EvtParaForIos, Void, EvtRes> {

	private CaseReportNew mContext;
	
	private Dialog mDialog;
	public ReportTask(CaseReportNew activity){
		this.mContext = activity;
	}
	@Override
	protected void onPostExecute(EvtRes result) {
		mDialog.dismiss();
		if(result==null){
			ToastUtils.show(mContext,R.string.net_error);
			//TODO 重置上传参数
			mContext.reportFailed = true;
			mContext.para = result.getEvtPara();
			return;
		}
		if(result.isSuccess()){
			ToastUtils.show(mContext,R.string.report_evt_success);
			mContext.finish();
		}else{
			//TODO 重置上传参数
			mContext.reportFailed = true;
			mContext.para = result.getEvtPara();
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
	protected EvtRes doInBackground(EvtParaForIos... params) {
		EvtParaForIos para = params[0];
		EvtRes res = null;
		try {
			// /boolean handleValue = UploadHandle.UploadCore(para.attachs);
			UploadHandleHttp uploadHandleHttp = new UploadHandleHttp();
			List<UUID> fidList = uploadHandleHttp.upload(para.getAttachs());
//			if (handleValue) {
			para = replaceId(fidList, para);
			res = ServiceHandle.reportEvt(para);
//			} else {
//				res = new EvtRes();
//				res.setSuccess(false);
//				res.setErrorMessage("图片上传失败,请重试");
//			}
			if (res != null && res.isSuccess()) {
				res.setSuccess(true);
				res.setCaseAccept(CaseAccept.unaccepted);
				res.setCaseType(CaseType.reportSuccess);
				res.setEvtPara(para);
				// 案件上报成功，把案件插入数据库
				DatabaseEvtRes.insert(res, UserCache.getInstance().getUserId());
				// 图片上传成功把图片插入数据库
				DatabaseAttach.insert(res.getEvtCode(), para.getAttachs());
			}
		} catch (Exception e) {
			res = new EvtRes();
			res.setSuccess(false);
			res.setCaseType(CaseType.reportFail);
			res.setErrorMessage(e.toString());
		}
		return res;
	}

	
	/**
	 * 替换图片上传之后的id
	 * @return
	 */
	private EvtParaForIos replaceId(List<UUID> fidList,EvtParaForIos para){
		if(para.getAttachs()==null||para.getAttachs().size()==0){
			return para;
		}
		AttachInfoCollection collection = para.getAttachs();
		for (int i = 0; i < collection.size(); i++) {
			collection.get(i).setId(fidList.get(i));
			collection.get(i).setUploaded(true);
		}
		para.setAttachs(collection);
		return para;
	}
}
