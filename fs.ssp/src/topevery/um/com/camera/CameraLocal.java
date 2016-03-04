package topevery.um.com.camera;

import java.io.File;
import java.io.FileOutputStream;

import topevery.um.com.main.MainDialog;
import topevery.um.com.utils.PathUtils;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

public class CameraLocal extends Activity
{

	Bitmap bitmap;
	private String path = "";
	private File file = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		path = PathUtils.getImageName();
		file = new File(path);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("output", Uri.fromFile(file));
		intent.putExtra("outputFormat", "JPEG");
		// intent.putExtra("aspectX", 2);
		// intent.putExtra("aspectY", 2);
		intent.putExtra("outputY", 408);
		intent.putExtra("outputX", 306);
		intent.putExtra("return-data", true);

		startActivityForResult(intent, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		FileOutputStream stream;
		try
		{
			bitmap = (Bitmap) data.getExtras().get("data");
			if (bitmap != null)
			{
				stream = new FileOutputStream(file.getAbsoluteFile());
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
				bitmap.recycle();
				stream.flush();
				stream.close();
			}

			if (file.exists())
			{
				CameraUtils.value.onCamera(path);
			}
		}
		catch (Exception e)
		{
			// MsgBox.show(this, e.toString());
			MainDialog.askYes(this, e.toString());
		}
		finally
		{
			this.finish();
		}

	}

}
