package topevery.um.net;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.text.TextUtils;
import android.util.Log;

public class PdaService
{
	private static final String nameSpace = "http://tempuri.org/";

	// private static final String endPoint =
	// "http://192.168.3.19/nm.pda/ASMX/PdaService.asmx";

	private static String getEndPoint()
	{
		return Environments.PdaUrl;
		// return endPoint;
	}

	public static <T> T invoke(String key,Object obj, String methodName, Class<T> clazz) throws Exception
	{
		String req = "";
		if (obj != null)
		{
			req = JsonCore.toJson(obj);
		}
		Log.e("dream","req="+req);
		String soapAction = String.format("%s%s", nameSpace, methodName);

		SoapObject rpc = new SoapObject(nameSpace, methodName);
		rpc.addProperty(key, req);

		// 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

		envelope.bodyOut = rpc;
		// 设置是否调用的是dotNet开发的WebService
		envelope.dotNet = true;
		// 等价于envelope.bodyOut = rpc;
		envelope.setOutputSoapObject(rpc);

		HttpTransportSE transport = new HttpTransportSE(getEndPoint());
		// 调用WebService
		transport.call(soapAction, envelope);

		SoapObject object = (SoapObject) envelope.bodyIn;
		// 获取返回的结果
		String json = object.getProperty(0).toString();
		Log.e("dream","json="+json.toString());
		if (!TextUtils.isEmpty(json) && clazz != null)
		{
			return JsonCore.fromJson(json, clazz, true);
		}
		return null;
	}
}
