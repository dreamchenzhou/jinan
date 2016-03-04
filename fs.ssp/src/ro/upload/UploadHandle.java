package ro.upload;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import topevery.framework.commonModel.Log;
import topevery.framework.commonModel.StaticHelper;
import topevery.framework.system.DateTime;
import topevery.framework.udp.UdpServiceSendMonitoring;
import topevery.um.net.UmUdpService;
import topevery.um.net.UmUdpServicePacketType;
import topevery.um.net.srv.AttachInfo;
import topevery.um.net.srv.AttachInfoCollection;

public class UploadHandle
{
	public static void testUpload()
	{
		try
		{
			String path = "/sdcard/test.jpg";
			UploadCore(0, UUID.randomUUID(), UUID.randomUUID(), path);
		}
		catch (Exception e)
		{
			Log.value.write(e);
		}
	}

	static Date dateBegin;
	static Date dateEnd;
	static double second = 0;

	public static boolean UploadCore(AttachInfoCollection attachs)
	{
		boolean reuslt = true;
		try
		{
			if (attachs != null && attachs.size() > 0)
			{
				for (AttachInfo item : attachs)
				{
					File file = new File(item.uri);
					{
						if (file.exists())
						{
							if (!item.uploaded)
							{
								dateBegin = new Date();

								item.uploaded = UploadCore(0, UUID.randomUUID(), item.id, item.uri);

								dateEnd = new Date();
								second = (dateEnd.getTime() - dateBegin.getTime()) / 1000.000;

								reuslt &= item.uploaded;
							}
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			reuslt = false;
			Log.value.write(e);
		}
		return reuslt;
	}

	public static boolean UploadCore(int userId, UUID passportId, UUID fileId,
			String filePath)
	{
		boolean result = true;
		try
		{
			RandomAccessFile raf = new RandomAccessFile(filePath, "r");
			UploadStartRes usRes = UploadStart(fileId, (int) raf.length());
			if (!usRes.isSuccess)
			{
				return false;
			}
			else if (usRes.isExists)
			{
				return true;
			}
			else
			{
				int bufLength = 1024;
				byte[] buf = new byte[bufLength];
				int readLength = 0;
				int readPosition = 0;
				int sleepIdx = 16;
				for (int i = 0; true; i++)
				{
					readLength = ReadFile(raf, buf, readPosition);
					if (readLength > 0)
					{
						UploadCorePara para = new UploadCorePara();
						para.Id = fileId;
						para.Position = readPosition;
						readPosition += readLength;
						if (buf.length == readLength)
							para.Data = buf;
						else
						{
							para.Data = new byte[readLength];
							System.arraycopy(buf, 0, para.Data, 0, para.Data.length);
						}
						byte[] data = GetParaBytes(UmUdpServicePacketType.Upload.UploadCore, para.Serialize());
						UmUdpService.value.sendToServerByNoState(UmUdpServicePacketType.Up, data);
					}
					if (readLength < bufLength)
						break;
					else if (i > 0 && i % sleepIdx == 0)
						StaticHelper.threadSleep();
				}

				int packCount = -1;
				do
				{
					UploadStatePara statePara = new UploadStatePara();
					statePara.id = fileId;
					statePara.fileLength = (int) raf.length();
					statePara.beginPosition = 0;
					statePara.endPosition = (int) raf.length();
					statePara.packetLength = bufLength;
					UploadStateRes stateRes = UploadState(statePara);
					if (!stateRes.isSuccess)
						throw new Exception(stateRes.errorMessage);
					else if (stateRes.positions.size() == 0)
						break;
					else
					{
						if (packCount == -1 || packCount > stateRes.positions.size())
						{
							packCount = stateRes.positions.size();
						}
						else
						{
							throw new Exception("网络中断");
						}
						int forIdx = 0;
						for (int positionIdx : stateRes.positions)
						{
							readLength = ReadFile(raf, buf, positionIdx);
							if (readLength > 0)
							{
								UploadCorePara para = new UploadCorePara();
								para.Id = fileId;
								para.Position = positionIdx;
								if (buf.length == readLength)
									para.Data = buf;
								else
								{
									para.Data = new byte[readLength];
									System.arraycopy(buf, 0, para.Data, 0, para.Data.length);
								}
								byte[] data = GetParaBytes(UmUdpServicePacketType.Upload.UploadCore, para.Serialize());
								UmUdpService.value.sendToServerByNoState(UmUdpServicePacketType.Up, data);
							}
							if (forIdx > 0 && forIdx % sleepIdx == 0)
							{
								StaticHelper.threadSleep();
							}
							forIdx++;
						}

						StaticHelper.threadSleep(50);
					}
				} while (true);

				if (result)
				{
					UploadFinishPara para = new UploadFinishPara();
					{
						para.id = fileId;
						para.userId = userId;
						para.passportId = passportId;
						para.name = new File(filePath).getName();
						para.pdaTime = DateTime.getNow();
						para.syncTime = DateTime.getNow();
						para.picTime = getCreateDate(new File(filePath));
					}
					UploadFinishRes res = UploadFinish(para);
					if (res != null)
					{
						result = res.isSuccess;
					}
					else
					{
						result = false;
					}
				}
			}
		}
		catch (Exception ex)
		{
			result = false;
		}
		return result;
	}

	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static DateTime getCreateDate(File file)
	{
		DateTime result = DateTime.getNow();
		if (file != null && file.exists())
		{
			Date date = null;
			long milliseconds = file.lastModified();
			date = new Date(milliseconds);
			result = DateTime.fromJavaDate(date);
		}
		return result;
	}

	private static UploadStartRes UploadStart(UUID fileId, int fileLength)
	{
		UploadStartPara para = new UploadStartPara();
		para.id = fileId;
		para.length = fileLength;
		return UploadStart(para);
	}

	private static UploadStartRes UploadStart(UploadStartPara para)
	{
		UploadStartRes result = null;
		byte[] data = GetParaBytes(UmUdpServicePacketType.Upload.UploadStart, StaticHelper.getSerializable(para));
		UdpServiceSendMonitoring monitoring = UmUdpService.value.sendToServer(UmUdpServicePacketType.Up, data);
		{
			monitoring.waitComplete();
			if (monitoring.state == 2)
			{
				result = (UploadStartRes) StaticHelper.getDeserialize(monitoring.callbackData);
			}
		}
		if (result == null)
		{
			result = UploadStartRes.errorVal;
		}
		return result;
	}

	private static UploadStateRes UploadState(UploadStatePara para)
	{
		UploadStateRes result = null;
		byte[] data = GetParaBytes(UmUdpServicePacketType.Upload.UploadState, StaticHelper.getSerializable(para));
		UdpServiceSendMonitoring monitoring = UmUdpService.value.sendToServer(UmUdpServicePacketType.Up, data);
		{
			monitoring.waitComplete();
			if (monitoring.state == 2)
			{
				result = (UploadStateRes) StaticHelper.getDeserialize(monitoring.callbackData);
			}
		}
		if (result == null)
		{
			result = UploadStateRes.errorVal;
		}
		return result;
	}

	private static UploadFinishRes UploadFinish(UploadFinishPara para)
	{
		UploadFinishRes result = null;
		byte[] data = GetParaBytes(UmUdpServicePacketType.Upload.UploadFinish, StaticHelper.getSerializable(para));
		UdpServiceSendMonitoring monitoring = UmUdpService.value.sendToServer(UmUdpServicePacketType.Up, data);
		{
			monitoring.waitComplete();
			if (monitoring.state == 2)
			{
				result = (UploadFinishRes) StaticHelper.getDeserialize(monitoring.callbackData);
			}
		}
		if (result == null)
		{
			result = UploadFinishRes.errorVal;
		}
		return result;
	}

	private static byte[] GetParaBytes(int commandType, byte[] data)
	{
		UploadPara para = new UploadPara();
		para.commandType = commandType;
		para.receiveData = data;
		return para.serialize();
	}

	private static int ReadFile(RandomAccessFile fileRAStream, byte[] buf,
			int startPosition) throws Exception
	{
		fileRAStream.seek(startPosition);
		return fileRAStream.read(buf, 0, buf.length);
	}
}