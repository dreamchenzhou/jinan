package topevery.um.com.multipic;

public class PictureUtils {
	private static OnTakePictureCallBack mCallBack;
	
	public static void addTakePictureCallBack(OnTakePictureCallBack callBack){
		mCallBack  =callBack;
	}
	
	public static void cameraFinished(String path){
		if(mCallBack!=null){
			mCallBack.onCamereFinished(path);
		}
	}
	
	public interface OnTakePictureCallBack{
		public void onCamereFinished(String path);
	}
}
