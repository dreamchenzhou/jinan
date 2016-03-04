package topevery.um.net.srv;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import topevery.framework.runtime.serialization.IBinarySerializable;
import topevery.framework.runtime.serialization.IObjectBinaryReader;
import topevery.framework.runtime.serialization.IObjectBinaryWriter;
import topevery.framework.runtime.serialization.RemoteClassAlias;

@SuppressWarnings("serial")
@RemoteClassAlias({ "Topevery.DUM.SocketShiMin.Srv.AttachInfoCollection" })
public class AttachInfoCollection extends ArrayList<AttachInfo> implements IBinarySerializable
{

	@Override
	public void readObjectData(IObjectBinaryReader reader)
	{
		int length = reader.readInt32();
		for (int i = 0; i < length; i++)
		{
			this.add((AttachInfo) reader.readObject());
		}
	}

	@Override
	public void writeObjectData(IObjectBinaryWriter writer)
	{
		int length = this.size();
		writer.writeInt32(length);
		for (int i = 0; i < length; i++)
		{
			writer.writeObject(this.get(i));
		}
	}

	public void add(String path)
	{
		if(!contains(path))
		{
			AttachInfo item = new AttachInfo();
			item.uri = path;
			item.id = UUID.randomUUID();
			item.name = new File(path).getName();
			this.add(item);
		}
	}

	public void add(ArrayList<AttachInfo> collection)
	{
		if(collection != null && collection.size() > 0)
		{
			for (AttachInfo attach : collection)
			{
				if(!contains(attach.uri))
				{
					attach.isChecked = false;
					this.add(attach);
				}
			}
		}
	}

	public void add(File[] fileList)
	{
		if(fileList != null && fileList.length > 0)
		{
			for (File file : fileList)
			{
				this.add(file.getPath());
			}
		}
	}

	public boolean contains(String path)
	{
		for (AttachInfo attach : this)
		{
			if(attach.uri.equalsIgnoreCase(path))
			{
				return true;
			}
		}
		return false;
	}

	public void refresh()
	{
		AttachInfoCollection temps = new AttachInfoCollection();

		for (AttachInfo item : this)
		{
			if(!item.exists())
			{
				temps.add(item);
			}
		}

		if(temps.size() > 0)
		{
			for (AttachInfo item : temps)
			{
				this.remove(item);
			}
		}
	}

	public void clearFiles()
	{
		for (AttachInfo item : this)
		{
			delFile(item);
		}
	}

	public void delFile(AttachInfo item)
	{
		delFile(item.uri);
	}

	public void delFile(String path)
	{
		try
		{
			File file = new File(path);
			if(file.exists())
			{
				file.delete();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public boolean isEmpty = true;
	// public boolean isSave = false;
	public AttachInfo attachInfo = new AttachInfo();

}
