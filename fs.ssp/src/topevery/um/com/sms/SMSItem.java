//package topevery.um.com.sms;
//
//import java.io.Serializable;
//
//import android.app.PendingIntent;
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//import android.provider.BaseColumns;
//import android.telephony.SmsManager;
//
//@SuppressWarnings("serial")
//public class SMSItem implements BaseColumns, Serializable
//{
//	public static final Uri CONTENT_URI = Uri.parse("content://sms");
//	public static final String FILTER = "!imichat";
//	public static final String TYPE = "type";
//	public static final String THREAD_ID = "thread_id";
//	public static final String ADDRESS = "address";
//	public static final String PERSON_ID = "person";
//	public static final String DATE = "date";
//	public static final String READ = "read";
//	public static final String BODY = "body";
//	public static final String PROTOCOL = "protocol";
//	public static final int MESSAGE_TYPE_ALL = 0;
//	public static final int MESSAGE_TYPE_INBOX = 1;
//	public static final int MESSAGE_TYPE_SENT = 2;
//	public static final int MESSAGE_TYPE_DRAFT = 3;
//	public static final int MESSAGE_TYPE_OUTBOX = 4;
//	public static final int MESSAGE_TYPE_FAILED = 5;
//	public static final int MESSAGE_TYPE_QUEUED = 6;
//	public static final int PROTOCOL_SMS = 0;
//	public static final int PROTOCOL_MMS = 1;
//
//	public static String num = "";
//	public static final String SZSSPQUERYNUM = "SZSSPQUERYNUM";
//
//	/**
//	 * 获取本机号码
//	 * 
//	 * @param context
//	 */
//	public static void getNum(Context context)
//	{
//		if (context != null)
//		{
//			String num = "106575580211";
//			String content = "SZSSPQUERYNUM";
//			sendByPending(context, num, content);
//		}
//	}
//
//	public static void sendByPending(Context context, String num, String content)
//	{
//		if (context != null)
//		{
//			SmsManager smsManager = SmsManager.getDefault();
//			PendingIntent sentIntent = PendingIntent.getBroadcast(context, 0, new Intent(), 0);
//			smsManager.sendTextMessage(num, null, content, sentIntent, null);
//		}
//	}
//
//	public static void sendByIntent(Context context, String num, String content)
//	{
//		if (context != null)
//		{
//			Uri uri = Uri.parse("smsto:" + num);
//			Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
//			intent.putExtra("sms_body", content);
//			context.startActivity(intent);
//		}
//	}
//
//	private int id;
//	private int type;
//	private int protocol;
//	private String phone;
//	private String body;
//	private String localNum = "";
//
//	public SMSItem()
//	{
//	}
//
//	public String getLocalNum()
//	{
//		return localNum;
//	}
//
//	public void setLocalNum(String localNum)
//	{
//		this.localNum = localNum;
//	}
//
//	public int getId()
//	{
//		return id;
//	}
//
//	public void setId(int id)
//	{
//		this.id = id;
//	}
//
//	public int getType()
//	{
//		return type;
//	}
//
//	public void setType(int type)
//	{
//		this.type = type;
//	}
//
//	public int getProtocol()
//	{
//		return protocol;
//	}
//
//	public void setProtocol(int protocol)
//	{
//		this.protocol = protocol;
//	}
//
//	public String getPhone()
//	{
//		return phone;
//	}
//
//	public void setPhone(String phone)
//	{
//		this.phone = phone;
//	}
//
//	public String getBody()
//	{
//		return body;
//	}
//
//	public void setBody(String body)
//	{
//		this.body = body;
//	}
//
//	public String toString()
//	{
//		return
//
//		"id = " + id + ";" +
//
//		"type = " + type + ";" +
//
//		"protocol = " + protocol + ";" +
//
//		"phone = " + phone + ";" +
//
//		"body = " + body;
//	}
// }
