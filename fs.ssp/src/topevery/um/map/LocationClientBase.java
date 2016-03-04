package topevery.um.map;

import java.util.ArrayList;

import android.content.Context;

public abstract class LocationClientBase
{
	public UmLocation lastLocation;

	public ArrayList<ReceiveUmLocationListener> listeners = new ArrayList<ReceiveUmLocationListener>();

	public void addLocationListener(ReceiveUmLocationListener listener)
	{
		if (listener != null)
		{
			listeners.add(listener);
		}
	}

	public void removeLocationListener(ReceiveUmLocationListener listener)
	{
		if (listener != null)
		{
			listeners.remove(listener);
		}
	}

	public void onReceiveUmLocation(UmLocation location)
	{
		for (ReceiveUmLocationListener listener : listeners)
		{
			if (listener != null)
			{
				listener.onReceiveUmLocation(location);
			}
		}
	}

	public abstract void setContext(Context context);

	public abstract void start();

	public abstract void stop();

	public abstract boolean isStarted();

	public abstract UmLocation getLocation();
}
