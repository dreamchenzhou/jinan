package topevery.um.maptencent;

import org.apache.http.Header;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.EditText;

import com.tencent.lbssearch.TencentSearch;
import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.object.Location;
import com.tencent.lbssearch.object.param.Geo2AddressParam;
import com.tencent.lbssearch.object.result.Geo2AddressResultObject;

/**
 * 根据经纬度获取地址
 * 
 * @author martin.zheng
 * 
 */
public class GeoCoderHolder
{
	private Activity wThis;
	private TencentSearch api;
	private String strResult = "";
	private String errResult = "";
	private EditText editText;

	public GeoCoderHolder(Activity wThis, EditText editText)
	{
		this.wThis = wThis;
		this.editText = editText;
		api = new TencentSearch(this.wThis);
	}

	public void searchFromLocation(double latitude, double longitude)
	{
		Geo2AddressParam param = new Geo2AddressParam().location(new Location().lat((float) latitude).lng((float) longitude));
		try
		{
			api.geo2address(param, new HttpResponseListener()
			{
				@Override
				public void onSuccess(int statusCode, Header[] headers, BaseObject object)
				{
					if (object != null)
					{
						Geo2AddressResultObject oj = (Geo2AddressResultObject) object;
						if (oj.result != null)
						{
							strResult += oj.result.address;
						}
					}

					if (!wThis.isFinishing() && !TextUtils.isEmpty(strResult))
					{
						editText.setText(strResult);
						editText.setSelection(strResult.length());
					}
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
				{
					errResult = responseString;
				}
			});
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
