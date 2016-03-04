package topevery.um.net.up;

import java.io.File;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import topevery.android.framework.utils.UUIDUtils;
import topevery.um.net.newbean.AttachInfo;
import topevery.um.net.newbean.AttachInfoCollection;
import android.text.TextUtils;

/**
 * 上传附件
 * 
 * @author martin.zheng
 * 
 */
public class AttachUpload
{
	public static String url = "http://192.168.3.7/PublicServer.jn/AttachUpload.ashx?";

	public static void test()
	{
		String sdcard = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
		String path = sdcard + "/topevery/QQ截图20160303142400.jpg";

		AttachInfo attachInfo = new AttachInfo();
		attachInfo.setUri(path);
		execute(attachInfo);
	}

	public static void execute(AttachInfoCollection attachs)
	{
		if (attachs != null && attachs.size() != 0)
		{
			HttpMultipartPost post = new HttpMultipartPost(attachs);
			post.execute();
		}
	}

	public static UUID execute(AttachInfo attachInfo)
	{
		UUID result = null;

		if (attachInfo != null)
		{
			HttpMultipartPost post = new HttpMultipartPost();
			result = post.execute(attachInfo);
		}
		return result;
	}

	private static class HttpMultipartPost
	{
		private AttachInfoCollection attachs;

		public HttpMultipartPost(AttachInfoCollection attachs)
		{
			this.attachs = attachs;
		}

		public HttpMultipartPost()
		{
		}

		public void execute()
		{
			for (AttachInfo attachInfo : attachs)
			{
				if (!attachInfo.isUploaded())
				{
					attachInfo.setUploaded( execute(attachInfo)==null?false:true);
				}
			}
		}

		@SuppressWarnings("deprecation")
		public UUID execute(AttachInfo attachInfo)
		{
			UUID result = null;

			// String actionUrlBase = url +
			// "fn=%s&sfn=%s&pos=%s&tlen=%s&sdt=%s&&userId=%s&userName=%s&sha1=%s";
			// File file = new File(attachInfo.uri);
			// int fileLen = (int) file.length();
			// String sFileDate = getCreateDate(file);
			// String sFileID = attachInfo.id.toString();
			// String sFileName = file.getName();
			// int offset = 0;
			// String actionUrl = String.format(actionUrlBase, sFileName,
			// sFileID, String.valueOf(offset), String.valueOf(fileLen),
			// sFileDate, userId, userName, sha1);

			HttpClient httpClient = new DefaultHttpClient();
			HttpContext httpContext = new BasicHttpContext();
			HttpPost httpPost = new HttpPost(url);

			try
			{
				CustomMultipartEntity multipartContent = new CustomMultipartEntity(null);

				FileBody fileBody = new FileBody(new File(attachInfo.getUri()));
				multipartContent.addPart("file", fileBody);

				httpPost.setEntity(multipartContent);
				HttpResponse response = httpClient.execute(httpPost, httpContext);

				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
				{
					HttpEntity outputEntity = response.getEntity();
					String sValue = EntityUtils.toString(outputEntity);
					{
						// if (!sValue.equalsIgnoreCase("ok"))
						// {
						// throw new RuntimeException(sValue);
						// }
						// else
						// {
						// result = true;
						// }
						result = getId(sValue);
						if (result == null)
						{
							throw new RuntimeException(sValue);
						}
					}
				}
			}
			catch (ClientProtocolException e)
			{
				e.printStackTrace();
			}
			catch (ConnectTimeoutException e)
			{
				e.printStackTrace();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				if (httpClient != null && httpClient.getConnectionManager() != null)
				{
					httpClient.getConnectionManager().shutdown();
				}
			}
			return result;
		}
	}

	private static UUID getId(String str)
	{
		UUID id = null;
		try
		{
			if (!TextUtils.isEmpty(str))
			{
				id = UUID.fromString(str);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return id;
	}
}
