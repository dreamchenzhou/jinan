package topevery.um.com.media;

import java.io.File;
import java.io.Serializable;

@SuppressWarnings("serial")
public class ChatMessage implements Serializable
{
	private File file;
	private String name;

	/**
	 * file 录音文件路径
	 * 
	 * name 录音长度 ，2s(录音开始时间减去录音结束时间)
	 * */
	public ChatMessage(File file, String name)
	{
		super();
		this.file = file;
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public File getFile()
	{
		return file;
	}

	public void setFile(File file)
	{
		this.file = file;
	}
}
