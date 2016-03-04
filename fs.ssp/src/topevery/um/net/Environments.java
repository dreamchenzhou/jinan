package topevery.um.net;

import java.util.UUID;

import topevery.um.com.Settings;

public class Environments
{
	public static UUID userId = UUID.randomUUID();
	
	public static UUID passportId = UUID.randomUUID();
	
	public static String ip = "192.168.3.7";
	
	//public static String httpHandleUrl = String.format("http://%s/JINANUM/AttachUpload.ashx?",ip);
	
	public static String httpHandleUrl = String.format("http://%s/JiNanQmcgUploadSv/Handlers/upload.ashx?",ip);

	
	public static String PdaUrl = String.format("http://%s/PublicServer.jn/webservice/PadService.asmx", ip);
	
	
}
