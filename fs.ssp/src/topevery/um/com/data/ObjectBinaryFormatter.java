package topevery.um.com.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import topevery.framework.runtime.serialization.ObjectBinaryInput;
import topevery.framework.runtime.serialization.ObjectBinaryOutput;

/**
 * 外置二进制序列化
 * 
 * @author martin.zheng
 * 
 */
public final class ObjectBinaryFormatter
{
	public static void writeObject(Object obj, File file) throws Exception
	{
		writeObject(obj, new FileOutputStream(file));
	}

	public static void writeObject(Object obj, String path) throws Exception
	{
		writeObject(obj, new FileOutputStream(path));
	}

	/**
	 * new FileOutputStream(path)
	 * 
	 * @param obj
	 * @param output
	 * @throws Exception
	 */
	public static void writeObject(Object obj, OutputStream output) throws Exception
	{
		ObjectBinaryOutput binOut = new ObjectBinaryOutput(output);
		binOut.writeObject(obj);
	}

	public static Object readObject(String path) throws Exception
	{
		return readObject(new FileInputStream(path));
	}

	public static Object readObject(File file) throws Exception
	{
		return readObject(new FileInputStream(file));
	}

	/**
	 * new FileInputStream(path)
	 * 
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public static Object readObject(InputStream input) throws Exception
	{
		ObjectBinaryInput binIn = new ObjectBinaryInput(input);
		return binIn.readObject();
	}
}
