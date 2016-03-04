package topevery.um.com.camera;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;

import topevery.android.core.MsgBox;
import topevery.um.com.multipic.ActivityPreView;
import topevery.um.com.utils.PathUtils;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;

public class CameraHolder extends Activity
{
	private String path = "";
	private File file = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		path = PathUtils.getImageName();
		file = new File(path);

		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		// intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		startActivityForResult(intent, 1);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode == RESULT_OK) {
			try {
				if (file.exists()) {
					onCamera1();
				} else {
					onCamera2(resultCode, data);
				}

				if (file.exists()) {
					CameraUtils.value.onCamera(path);
				}
				ArrayList<String> paths = new ArrayList<String>();
				paths.add(path);
				data.setClass(this, ActivityPreView.class);
				data.putExtra("paths", paths);
				data.putExtra("from", true);
				startActivity(data);
			} catch (Exception e) {
				MsgBox.show(this, e.toString());
			} finally {
				this.finish();
			}
		}
		finish();
	}

	private void onCamera1() throws Exception
	{
		Bitmap bitmap;
		FileOutputStream stream;

		Options opts = new Options();
		opts.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
		opts.inJustDecodeBounds = false;

		float val;
		if (opts.outWidth < opts.outHeight)
		{
			val = opts.outHeight / (float) 320;
		}
		else
		{
			val = opts.outWidth / (float) 240;
		}

		if (val < 1)
		{
			val = 1;
		}

		BigDecimal bigDecimal = new BigDecimal(String.valueOf(val)).setScale(0, BigDecimal.ROUND_HALF_UP);
		int be = Integer.parseInt(String.valueOf(bigDecimal));
		opts.inSampleSize = be;

		// Options opts2 = new Options();
		// opts2.inSampleSize = 2;

		bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), opts);
		stream = new FileOutputStream(file.getAbsoluteFile());
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		bitmap.recycle();
		stream.flush();
		stream.close();

		if (bitmap != null)
		{
			bitmap.recycle();
			bitmap = null;
		}
	}

	private void onCamera2(int resultCode, Intent data) throws Exception
	{
		if (resultCode == Activity.RESULT_OK)
		{
			Bundle bundle = data.getExtras();
			Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回数据，并转换为b格式
			FileOutputStream bFileOutputStream = null;
			bFileOutputStream = new FileOutputStream(path);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bFileOutputStream);// 数据写入文件
			bFileOutputStream.flush();
			bFileOutputStream.close();
		}
	}
}
