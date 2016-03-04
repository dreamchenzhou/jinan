package topevery.um.com.data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class Serializer
{
	private static boolean isNew = false;

	public static Object readObject(String file) throws Exception
	{
		Object obj = null;
		if (!isNew)
		{
			ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(file));
			obj = objIn.readObject();
			objIn.close();
		}
		else
		{
			obj = ObjectBinaryFormatter.readObject(file);
		}
		return obj;
	}

	public static void test() throws Exception
	{

	}

	public static void writeObject(Object obj, String file) throws Exception
	{
		if (!isNew)
		{
			ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(file));
			objOut.writeObject(obj);
			objOut.flush();
			objOut.close();
		}
		else
		{
			ObjectBinaryFormatter.writeObject(file, file);
		}
	}
}
