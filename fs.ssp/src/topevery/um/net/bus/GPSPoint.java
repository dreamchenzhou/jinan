package topevery.um.net.bus;

import java.io.Serializable;

import topevery.framework.runtime.serialization.IBinarySerializable;
import topevery.framework.runtime.serialization.IObjectBinaryReader;
import topevery.framework.runtime.serialization.IObjectBinaryWriter;
import topevery.framework.runtime.serialization.RemoteClassAlias;
import topevery.framework.system.DateTime;

@SuppressWarnings("serial")
@RemoteClassAlias({ "Topevery.DUM.SocketShiMin.Bus.GPSPoint" })
public class GPSPoint implements Serializable, IBinarySerializable
{

	public double absX;
	public double absY;
	public double geoX;
	public double geoY;
	public DateTime dateTime;

	// /**
	// * 海拔
	// */
	// public double elevation;
	// /**
	// * 方向，和正北方差角
	// */
	// public double heading;
	// /**
	// * 速度
	// */
	// public double speed;

	@Override
	public void readObjectData(IObjectBinaryReader reader)
	{
		absX = reader.readDouble();
		absY = reader.readDouble();
		geoX = reader.readDouble();
		geoY = reader.readDouble();
		dateTime = reader.readDateTime();
		// elevation = reader.readDouble();
		// heading = reader.readDouble();
		// speed = reader.readDouble();
	}

	@Override
	public void writeObjectData(IObjectBinaryWriter writer)
	{
		writer.writeDouble(absX);
		writer.writeDouble(absY);
		writer.writeDouble(geoX);
		writer.writeDouble(geoY);
		writer.writeDateTime(dateTime);
		// writer.writeDouble(elevation);
		// writer.writeDouble(heading);
		// writer.writeDouble(speed);
	}
}
