package topevery.um.com.task;

import topevery.um.com.data.CaseType;
import topevery.um.com.data.DatabaseEvtRes;
import topevery.um.net.newbean.EvtPara;
import topevery.um.net.newbean.EvtResList;
import android.os.AsyncTask;

public class SearchHistoryTask extends AsyncTask<EvtPara, Void, EvtResList> {

	private OnSearchHistoryCallBack mCallBack;
	
	public SearchHistoryTask(OnSearchHistoryCallBack callBack){
		this.mCallBack = callBack;
	}
	@Override
	protected EvtResList doInBackground(EvtPara... paras) {
		return DatabaseEvtRes.getValue(CaseType.reportSuccess, paras[0]);
	}

	@Override
	protected void onPostExecute(EvtResList result) {
		super.onPostExecute(result);
		if(mCallBack!=null){
			mCallBack.onSearchFinished(result);
		}
	}
	
	public interface OnSearchHistoryCallBack{
		public void onSearchFinished( EvtResList result);
	}

}
