//package topevery.um.com.sms;
//
//import android.content.ContentResolver;
//import android.content.ContentUris;
//import android.content.Context;
//import android.database.ContentObserver;
//import android.database.Cursor;
//import android.net.Uri;
//import android.os.Handler;
//import android.os.Message;
//
//public class SMSObserver extends ContentObserver
//{
//	private static final String[] PROJECTION = new String[] { SMSItem._ID,// 0
//	SMSItem.TYPE,// 1
//	SMSItem.ADDRESS,// 2
//	SMSItem.BODY,// 3
//	SMSItem.DATE,// 4
//	SMSItem.THREAD_ID,// 5
//	SMSItem.READ,// 6
//	SMSItem.PROTOCOL // 7
//	};
//
//	private static final String SELECTION = SMSItem._ID + " > %s" + " and (" + SMSItem.TYPE + " = " + SMSItem.MESSAGE_TYPE_INBOX + " or " + SMSItem.TYPE + " = " + SMSItem.MESSAGE_TYPE_SENT + ")";
//	private static final int COLUMN_INDEX_ID = 0;
//	private static final int COLUMN_INDEX_TYPE = 1;
//	private static final int COLUMN_INDEX_PHONE = 2;
//	private static final int COLUMN_INDEX_BODY = 3;
//	private static final int COLUMN_INDEX_PROTOCOL = 7;
//	private static final int MAX_NUMS = 10;
//	private static int MAX_ID = 0;
//	private ContentResolver mResolver;
//	private OnCompletedListener onCompletedListener;
//	private Context mContext;
//	private static Handler mHandler = new Handler()
//		{
//			@Override
//			public void handleMessage(Message msg)
//			{
//				super.handleMessage(msg);
//			}
//		};
//
//	public interface OnCompletedListener
//	{
//		void onCompleted(SMSItem item);
//	}
//
//	public SMSObserver(Context context)
//	{
//		super(mHandler);
//		this.mContext = context;
//		this.mResolver = mContext.getContentResolver();
//		registerContentObserver();
//	}
//
//	public void registerContentObserver()
//	{
//		mResolver.registerContentObserver(SMSItem.CONTENT_URI, true, this);
//	}
//
//	public void unregisterContentObserver()
//	{
//		mResolver.unregisterContentObserver(this);
//	}
//
//	public void getNum(OnCompletedListener onCompletedListener)
//	{
//		this.onCompletedListener = onCompletedListener;
//		SMSItem.getNum(mContext);
//	}
//
//	@Override
//	public void onChange(boolean selfChange)
//	{
//		super.onChange(selfChange);
//
//		Cursor cursor = mResolver.query(SMSItem.CONTENT_URI, PROJECTION, String.format(SELECTION, MAX_ID), null, null);
//		int id, type, protocol;
//		String phone, body;
//		SMSItem item;
//		int iter = 0;
//		boolean hasDone = false;
//		while (cursor.moveToNext())
//		{
//			id = cursor.getInt(COLUMN_INDEX_ID);
//			type = cursor.getInt(COLUMN_INDEX_TYPE);
//			phone = cursor.getString(COLUMN_INDEX_PHONE);
//			body = cursor.getString(COLUMN_INDEX_BODY);
//			protocol = cursor.getInt(COLUMN_INDEX_PROTOCOL);
//			if (hasDone)
//			{
//				MAX_ID = id;
//				break;
//			}
//
//			if (protocol == SMSItem.PROTOCOL_SMS && body != null && body.startsWith(SMSItem.SZSSPQUERYNUM))
//			{
//				hasDone = true;
//				item = new SMSItem();
//				item.setId(id);
//				item.setType(type);
//				item.setPhone(phone);
//				item.setBody(body);
//				item.setProtocol(protocol);
//
//				String localNum = item.getBody().replaceAll(SMSItem.SZSSPQUERYNUM, "");
//				item.setLocalNum(localNum);
//
//				if (onCompletedListener != null)
//				{
//					onCompletedListener.onCompleted(item);
//					onCompletedListener = null;
//
//					Uri uri = ContentUris.withAppendedId(SMSItem.CONTENT_URI, item.getId());
//					mResolver.delete(uri, null, null);
//					break;
//				}
//			}
//			else
//			{
//				if (id > MAX_ID)
//				{
//					MAX_ID = id;
//				}
//			}
//			if (iter > MAX_NUMS)
//			{
//				break;
//			}
//			iter++;
//		}
//
//		if (cursor != null)
//		{
//			cursor.close();
//			cursor = null;
//		}
//	}
// }
