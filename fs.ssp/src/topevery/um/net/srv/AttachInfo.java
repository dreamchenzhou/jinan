package topevery.um.net.srv;

import java.io.File;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import topevery.framework.runtime.serialization.IBinarySerializable;
import topevery.framework.runtime.serialization.IObjectBinaryReader;
import topevery.framework.runtime.serialization.IObjectBinaryWriter;
import topevery.framework.runtime.serialization.RemoteClassAlias;
import topevery.framework.system.DateTime;
import android.R.integer;
import android.graphics.drawable.BitmapDrawable;

@SuppressWarnings("serial")
@RemoteClassAlias(
{ "Topevery.DUM.SocketShiMin.Srv.AttachInfo" })
public class AttachInfo implements IBinarySerializable, Serializable
{
	/**
	 * 文件id
	 */
	public UUID id = UUID.randomUUID();
	/**
	 * 文件名称
	 */
	public String name;

	// / <summary>
	// / 文件类型 -1:未知
	// /
	// / 0：照片
	// /
	// / 1：声音
	// /
	// / 2: 视频
	// /
	// / 3: 其它
	// /
	// / </summary>
	public int type = 0;

	// / <summary>
	// / 0:问题原始附件
	// / 1:核实附件
	// / 2:问题核查附件
	// / 3:专业部门附件
	// / 4:其他附件
	// / </summary>
	public int usageType = 0;

	/**
	 * 资源路径
	 */
	public String uri;

	/**
	 * 创建时间
	 */
	public DateTime createDate;

	public double absX;
	public double absY;
	public double geoX;
	public double geoY;

	// public long position = -1;

	public boolean isChecked = false;

	public boolean loadingFailed = false;

	public transient BitmapDrawable drawable;

	public String localPath;

	public boolean uploaded = false;
	public boolean isSave = false;

	/**
	 * 0是正常的，1是添加图标，需要其他类型，请添加说明
	 */
	public int viewType  = 0;
	
	public boolean exists()
	{
		File file = new File(uri);
		return file.exists();
	}

	public void delete()
	{
		File file = new File(uri);
		if (file.exists())
		{
			file.delete();
		}
	}

	public DateTime getCreateDateTime()
	{
		File file = new File(uri);
		Long time = file.lastModified();
		Calendar cd = Calendar.getInstance();
		cd.setTimeInMillis(time);
		return DateTime.fromCalendar(cd);
	}

	public Date getCreateDate()
	{
		File file = new File(uri);
		Long time = file.lastModified();
		Calendar cd = Calendar.getInstance();
		cd.setTimeInMillis(time);
		return cd.getTime();
	}

	public long lastModified()
	{
		File file = new File(uri);
		return file.lastModified();
	}

	@Override
	public void readObjectData(IObjectBinaryReader reader)
	{
		id = reader.readGuid();
		name = reader.readUTF();
		type = reader.readInt32();
		usageType = reader.readInt32();
		uri = reader.readUTF();
		createDate = reader.readDateTime();
		isChecked = reader.readBoolean();
		uploaded = reader.readBoolean();
	}

	@Override
	public void writeObjectData(IObjectBinaryWriter writer)
	{
		writer.writeGuid(id);
		writer.writeUTF(name);
		writer.writeInt32(type);
		writer.writeInt32(usageType);
		writer.writeUTF(uri);
		writer.writeDateTime(getCreateDateTime());
		writer.writeBoolean(isChecked);
		writer.writeBoolean(uploaded);
	}
}
