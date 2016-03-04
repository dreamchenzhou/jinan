package topevery.um.map;

import topevery.android.framework.map.MapHandleType;
import topevery.android.framework.map.MapValue;
import topevery.android.framework.map.OnCompletedListener;
import topevery.um.maptencent.ActivityTencentMap;
import android.content.Context;
import android.content.Intent;

public class MapManager
{
	private static OnCompletedListener onCompletedListener = null;

	public static void show(Context context)
	{
		MapValue mapValue = new MapValue();
		mapValue.mapHandleType = MapHandleType.position;
		show(context, mapValue);
	}

	public static void show(Context context, MapValue mapValue)
	{
		Intent intent = new Intent();
		intent.putExtra("SelectResult", mapValue);

		if (UmLocationClient.mLocationType == LocationTypeEnum.Baidu)
		{
			// intent.setClass(context, ActivityBaiduMap.class);
		}
		else
		{
			intent.setClass(context, ActivityTencentMap.class);
		}
		context.startActivity(intent);
	}

	public static void show(Context context, MapValue mapValue, OnCompletedListener listener)
	{
		onCompletedListener = listener;
		show(context, mapValue);
	}

	public static void setOnCompletedListener(OnCompletedListener listener)
	{
		onCompletedListener = listener;
	}

	public static void onCompleted(MapValue mapValue)
	{
		if (onCompletedListener != null)
		{
			onCompletedListener.onCompleted(mapValue);
			onCompletedListener = null;
		}
	}
}
