//package topevery.um.map;
//
//import topevery.android.framework.utils.TextUtils;
//import topevery.um.maptencent.GeoCoderHolder;
//import android.content.Context;
//import android.os.AsyncTask;
//import android.widget.EditText;
//
//public class PosAsyncTask extends AsyncTask<Void, Void, String>
//{
//	private Context mContext;
//	private EditText txtAddr;
//	private GeoCoderHolder mGeoCoderHolder;
//	private double latitude, longitude;
//
//	public PosAsyncTask(double latitude, double longitude, EditText txtAddr)
//	{
//		super();
//		this.latitude = latitude;
//		this.longitude = longitude;
//		this.txtAddr = txtAddr;
//		this.mContext = txtAddr.getContext();
//		this.mGeoCoderHolder = new GeoCoderHolder(mContext);
//	}
//
//	@Override
//	protected String doInBackground(Void... params)
//	{
//		String result = null;
//
//		result = mGeoCoderHolder.searchFromLocation(latitude, longitude);
//		return result;
//	}
//
//	@Override
//	protected void onPreExecute()
//	{
//	}
//
//	@Override
//	protected void onPostExecute(String result)
//	{
//		if (!TextUtils.isEmpty(result))
//		{
//			txtAddr.setText(result);
//			txtAddr.setSelection(result.length());
//		}
//	}
// }
