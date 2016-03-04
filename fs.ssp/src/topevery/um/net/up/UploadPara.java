//package topevery.um.net.up;
//
//import topevery.framework.io.MemoryStream;
//import topevery.framework.runtime.serialization.ObjectBinaryInput;
//import topevery.framework.runtime.serialization.ObjectBinaryOutput;
//
//import topevery.framework.commonModel.Log;
//
//public class UploadPara
//{
//    public int commandType;
//    public byte[] receiveData;
//
//    public byte[] serialize()
//    {          
//        byte[] result = null;
//		MemoryStream ms = new MemoryStream();
//		try {
//			ObjectBinaryOutput binOut = new ObjectBinaryOutput(ms);
//			{			
//				binOut.writeInt32(commandType);
//				binOut.writeInt32(receiveData.length);
//				binOut.writeBytes(receiveData);
//			}
//			result = ms.toByteArray();
//		} catch (Exception ex) {
//			Log.value.write(ex);
//		} finally {
//			if (ms != null) {
//				ms.close();
//			}
//		}
//		return result;
//    }
//
//    public void deserialize(byte[] val)
//    {      
//		MemoryStream ms = new MemoryStream(val, 0, val.length);
//		try {
//			ObjectBinaryInput binIn = new ObjectBinaryInput(ms);
//			{			
//				commandType = binIn.readInt32();	
//				int dataLength = binIn.readInt32();	
//				receiveData = binIn.readBytes(dataLength);
//			}
//		} catch (Exception ex) {
//			Log.value.write(ex);
//		} finally {
//			if (ms != null) {
//				ms.close();
//			}
//		}
//    }
// }