//package topevery.um.net.up;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.util.UUID;
//
//import topevery.framework.commonModel.Log;
//import topevery.framework.commonModel.StaticHelper;
//import topevery.framework.udp.UdpServiceSendMonitoring;
//import topevery.um.net.Environments;
//import topevery.um.net.UmUdpService;
//import topevery.um.net.UmUdpServicePacketType;
//import topevery.um.net.srv.AttachInfo;
//import topevery.um.net.srv.AttachInfoCollection;
//
//public class UploadHandle
//{
//	public static void testUpload()
//	{
//		try
//		{
//			String path = "/sdcard/test.jpg";
//			UploadCore(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), path);
//		}
//		catch (Exception e)
//		{
//			Log.value.write(e);
//		}
//	}
//
//	public static boolean UploadCore(AttachInfoCollection attachs)
//	{
//		boolean reuslt = true;
//		try
//		{
//			if(attachs != null && attachs.size() > 0)
//			{
//				for (AttachInfo item : attachs)
//				{
//					File file = new File(item.uri);
//					{
//						if(file.exists())
//						{
//							if(!item.uploaded)
//							{
//								item.uploaded = UploadCore(Environments.userId, Environments.passportId, item.id, item.uri);
//								reuslt &= item.uploaded;
//							}
//						}
//					}
//				}
//			}
//		}
//		catch (Exception e)
//		{
//			reuslt = false;
//			Log.value.write(e);
//		}
//		return reuslt;
//	}
//
//	public static boolean UploadCore(UUID userId, UUID passportId, UUID fileId, String filePath) throws Exception
//	{
//		boolean result = true;
//		FileInputStream fis = null;
//		try
//		{
//
//			UploadStart(fileId, filePath);
//
//			fis = new FileInputStream(filePath);
//			{
//				UdpServiceSendMonitoring[] listMonitoring = new UdpServiceSendMonitoring[16];
//				{
//					int readIdx = 0;
//					int readPosition = 0;
//					while (true)
//					{
//						boolean isEndTag = false;
//						for (int i = listMonitoring.length - 1; 0 <= i; i--)
//						{
//							UdpServiceSendMonitoring iObj = listMonitoring[i];
//							if(iObj != null)
//							{
//								if(iObj.state == 2)
//								{
//									iObj = null;
//									listMonitoring[i] = null;
//								}
//								else if(iObj.state == -1)
//								{
//									throw new Exception("Upload UnSuccess.");
//								}
//							}
//							if(iObj == null)
//							{
//
//								for (int j = 0; j < listMonitoring.length; j++)
//								{
//									if(j != i)
//									{
//										UdpServiceSendMonitoring jObj = listMonitoring[j];
//										if(jObj != null)
//										{
//											if(jObj.state == 2)
//											{
//												continue;
//											}
//											else if(jObj.state == -1)
//											{
//												throw new Exception("Upload UnSuccess.");
//											}
//											else
//											{
//												while (true)
//												{
//													StaticHelper.threadSleep();
//													if(jObj.state == 2)
//													{
//														break;
//													}
//													else if(jObj.state == -1)
//													{
//														throw new Exception("Upload UnSuccess.");
//													}
//												}
//											}
//										}
//									}
//								}
//
//								byte[] buf = new byte[1 * 1024];
//								readIdx = fis.read(buf, 0, buf.length);
//								if(readIdx > 0)
//								{
//									UploadCorePara para = new UploadCorePara();
//									{
//										para.Id = fileId;
//										para.Position = readPosition;
//										{
//											readPosition += readIdx;
//										}
//										if(buf.length > readIdx)
//										{
//											para.Data = new byte[readIdx];
//											{
//												for (int j = 0; j < readIdx; j++)
//												{
//													para.Data[j] = buf[j];
//												}
//											}
//										}
//										else
//										{
//											para.Data = buf;
//										}
//									}
//									byte[] data = GetParaBytes(UmUdpServicePacketType.Upload.UploadCore, para.Serialize());
//									UdpServiceSendMonitoring monitoring = UmUdpService.value.sendToServer(UmUdpServicePacketType.Up, data);
//									listMonitoring[i] = monitoring;
//								}
//								else
//								{
//									isEndTag = true;
//									{
//										break;
//									}
//								}
//							}
//						}
//						if(!isEndTag)
//						{
//							boolean isSpeepWait = true;
//							for (UdpServiceSendMonitoring i : listMonitoring)
//							{
//								if(i == null)
//								{
//									isSpeepWait = false;
//									{
//										break;
//									}
//								}
//							}
//							if(isSpeepWait)
//							{
//								StaticHelper.threadSleep();
//							}
//						}
//						else
//						{
//							break;
//						}
//					}
//
//					for (UdpServiceSendMonitoring i : listMonitoring)
//					{
//						if(i != null)
//						{
//							i.waitComplete();
//							if(i.state != 2)
//							{
//								throw new Exception("Upload UnSuccess.");
//							}
//						}
//					}
//				}
//			}
//		}
//		finally
//		{
//			if(fis != null)
//			{
//				fis.close();
//			}
//		}
//
//		if(result)
//		{
//			UploadFinishPara para = new UploadFinishPara();
//			{
//				para.id = fileId;
//				para.userId = userId;
//				para.passportId = passportId;
//				para.name = new File(filePath).getName();
//			}
//			UploadFinishRes res = UploadFinish(para);
//			if(res != null)
//			{
//				result = res.isSuccess;
//			}
//			else
//			{
//				result = false;
//			}
//		}
//		return result;
//	}
//
//	private static UploadFinishRes UploadFinish(UploadFinishPara para)
//	{
//		UploadFinishRes result = null;
//		byte[] data = GetParaBytes(UmUdpServicePacketType.Upload.UploadFinish, StaticHelper.getSerializable(para));
//		UdpServiceSendMonitoring monitoring = UmUdpService.value.sendToServer(UmUdpServicePacketType.Up, data);
//		{
//			monitoring.waitComplete();
//			{
//				if(monitoring.state == 2)
//				{
//					result = (UploadFinishRes) StaticHelper.getDeserialize(monitoring.callbackData);
//				}
//			}
//		}
//		return result;
//	}
//
//	private static void UploadStart(UUID fileId, String filePath)
//	{
//		try
//		{
//			File file = new File(filePath);
//			int leng = (int) file.length();
//
//			UploadStartPara para = new UploadStartPara();
//			para.id = fileId;
//			para.length = leng;
//			UploadStart(para);
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//	}
//
//	private static void UploadStart(UploadStartPara para)
//	{
//		byte[] data = GetParaBytes(UmUdpServicePacketType.Upload.UploadStart, StaticHelper.getSerializable(para));
//		UdpServiceSendMonitoring monitoring = UmUdpService.value.sendToServer(UmUdpServicePacketType.Up, data);
//		{
//			monitoring.waitComplete();
//		}
//	}
//
//	private static byte[] GetParaBytes(int commandType, byte[] data)
//	{
//		UploadPara para = new UploadPara();
//		para.commandType = commandType;
//		para.receiveData = data;
//		return para.serialize();
//	}
// }