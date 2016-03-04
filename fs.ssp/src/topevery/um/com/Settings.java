package topevery.um.com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import topevery.um.com.utils.PathUtils;
import android.util.Xml;

public class Settings
{
	private static Settings instance = null;
	private static String configPath = PathUtils.startUp + "/config.xml";

	private Settings()
	{

	}

	public static Settings getInstance()
	{
		if (instance == null)
		{
			synchronized (Settings.class)
			{
				if (instance == null)
				{
					instance = Settings.readData();
				}
			}
		}
		return instance;
	}

	public void delete(File dirs)
	{
		File[] files = dirs.listFiles();
		if (files != null && files.length > 0)
		{
			for (int i = 0; i < files.length; i++)
			{
				files[i].delete();
			}
		}
	}

	public void writeData()
	{
		try
		{
			StringWriter writer = new StringWriter();

			XmlSerializer serializer = Xml.newSerializer();
			serializer.setOutput(writer);
			serializer.startDocument("UTF-8", true);
			serializer.startTag("", "Settings");

			serializer.startTag("", "TelNum");
			serializer.text(this.TelNum);
			serializer.endTag("", "TelNum");

			// //////////////////////////
			serializer.startTag("", "UdpIp");
			serializer.text(this.UdpIp);
			serializer.endTag("", "UdpIp");

			// //////////////////////////
			serializer.startTag("", "UdpPort");
			serializer.text(String.valueOf(this.UdpPort));
			serializer.endTag("", "UdpPort");

			serializer.endTag("", "Settings");

			serializer.endDocument();

			FileOutputStream os = new FileOutputStream(Settings.configPath);
			OutputStreamWriter osw = new OutputStreamWriter(os);
			osw.write(writer.toString());
			osw.close();
			os.close();
			writer.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static Settings readData()
	{
		Settings returnValue = null;

		try
		{
			File file = new File(Settings.configPath);
			if (file.exists())
			{
				InputStream inputStream = new FileInputStream(Settings.configPath);
				XmlPullParser parser = Xml.newPullParser();
				parser.setInput(inputStream, "UTF-8");

				int type = parser.getEventType();

				while (type != XmlPullParser.END_DOCUMENT)
				{
					switch (type)
					{
						case XmlPullParser.START_DOCUMENT:
							returnValue = new Settings();
							break;
						case XmlPullParser.START_TAG:

							String name = parser.getName();
							String text = "";

							if (name.equalsIgnoreCase("TelNum"))
							{
								text = parser.nextText();
								returnValue.TelNum = text;
							}

							if (name.equalsIgnoreCase("UdpIp"))
							{
								text = parser.nextText();
								returnValue.UdpIp = text;
							}

							if (name.equalsIgnoreCase("UdpPort"))
							{
								text = parser.nextText();
								returnValue.UdpPort = java.lang.Integer.parseInt(text);
							}

							break;
						case XmlPullParser.END_TAG:
							break;
					}
					type = parser.next();
				}

				inputStream.close();
			}
			else
			{
				returnValue = new Settings();
			}
		}
		catch (Exception e)
		{
			returnValue = new Settings();
			e.printStackTrace();
		}

		return returnValue;
	}

	public String TelNum = "";
	public String UdpIp = "121.42.53.142";
	public int UdpPort = 9011;// 4011
}
