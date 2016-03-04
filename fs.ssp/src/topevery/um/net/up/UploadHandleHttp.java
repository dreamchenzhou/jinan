package topevery.um.net.up;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import topevery.android.core.ApplicationEx;
import topevery.um.net.Environments;
import topevery.um.net.newbean.AttachInfo;
import topevery.um.net.newbean.AttachInfoCollection;

public class UploadHandleHttp
{

	public HttpClient client = null;

	@SuppressWarnings("deprecation")
	private String getCreateDate(File file)
	{
		String returnValue = "";

		if(file.exists())
		{
			String sDate = "";

			sDate = ApplicationEx.getCreateDateTime(file);

			// DateTime dateTime = DateTime.MAX_VALUE;
			// Date date = dateTime.toJavaDate();
			// sDate = ApplicationEx.getCreateDateTime(date);

			try
			{
				returnValue = URLEncoder.encode(sDate, "utf-8");
			}
			catch (UnsupportedEncodingException e)
			{
				returnValue = URLEncoder.encode(sDate);
			}
		}
		return returnValue;
	}

	public List<UUID> upload(AttachInfoCollection attachs) throws Exception
	{
		List<UUID> fidList = new ArrayList<UUID>();
		try {
  				if(attachs != null && attachs.size() > 0)
				{
					for (AttachInfo item : attachs)
					{
						File file = new File(item.getUri());
						{
							if(file.exists() && !item.isUploaded())
							{
		//						item.setUploaded(upload(item));
		//						reuslt &= item.isUploaded();
								UUID fid= AttachUpload.execute(item);
								fidList.add(fid);
							}
						}
					}
				}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return fidList;
	}

	public boolean upload(AttachInfo attach) throws Exception
	{
		boolean returnValue = false;

		try
		{
			String actionUrlBase = Environments.httpHandleUrl + "fn=%s&sfn=%s&pos=%s&tlen=%s&sdt=%s&pictypessp=%s";

			File file = new File(attach.getUri());
			int fileLen = (int) file.length();

			FileInputStream fis = new FileInputStream(file);

			if(client == null)
			{
				client = new DefaultHttpClient();

				// client = HttpUtility.createHttpClient(null);
			}

			int truck = 64;
			if(truck < 8 || truck > 64)
			{
				truck = 64;
			}
			int truckLen = truck * 1024;
			byte[] buffer = new byte[truckLen];
			int readLen = fis.read(buffer, 0, truckLen);
			int offset = 0;
			String sFileDate = getCreateDate(file);
			String sFileID = attach.getId().toString();
			String sFileName = file.getName();
			do
			{
				String actionUrl = String.format(actionUrlBase, sFileName, sFileID, String.valueOf(offset), String.valueOf(fileLen), sFileDate, 99);

				HttpPost httpPost = new HttpPost(actionUrl);
				ByteArrayInputStream bufferInput = new ByteArrayInputStream(buffer, 0, readLen);

				InputStreamEntity inputStream = new InputStreamEntity(bufferInput, readLen);
				httpPost.setEntity(inputStream);
				HttpResponse httpResponse = client.execute(httpPost);
				
				String sValue;
				if(httpResponse.getStatusLine().getStatusCode() == 200)
				{
					HttpEntity outputEntity = httpResponse.getEntity();
					sValue = EntityUtils.toString(outputEntity);
					{
						if(!sValue.equalsIgnoreCase("ok"))
						{
							throw new RuntimeException(sValue);
						}
					}
				}
				else
				{
					throw new RuntimeException("Invalidate Parameter");
				}
				offset += readLen;
				readLen = fis.read(buffer, 0, truckLen);
			}
			while (offset < fileLen);
			returnValue = true;
			fis.close();
		}
		catch (Exception e)
		{
			throw e;
		}
		return returnValue;
	}
}
