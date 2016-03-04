package topevery.um.com.service;

import android.content.Context;
import android.content.Intent;

public class ServiceUtils
{
	public static void startService(Context context)
	{
		if (context != null)
		{
			context.startService(new Intent(context, ServiceHolder.class));
		}
	}

	public static void stopService(Context context)
	{
		if (context != null)
		{
			context.stopService(new Intent(context, ServiceHolder.class));
		}
	}
}
