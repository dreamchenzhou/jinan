package topevery.um.com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

public class BitmapUtils {

	/**
	 * 解析超大图片可能会oom
	 * 
	 * @param path
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap getBitmapByPath(String path, int width, int height) {
		if (TextUtils.isEmpty(path)) {
			return null;
		}
		Bitmap bitmap = null;
		BitmapFactory.Options opts = new Options();
		opts.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeFile(path, opts);
		int sample = 1;
		if (opts.outWidth > width || opts.outHeight > height) {
			float scaleX = opts.outWidth * 1f / (width * 1f);
			float scaleY = opts.outHeight * 1f / (height * 1f);
			float scale = Math.max(scaleX, scaleY);
			sample = Math.round(scale);
		}
		opts.inSampleSize = sample;
		opts.inJustDecodeBounds = false;
		opts.inPurgeable = true;
		opts.inInputShareable = true;
		if ("image/png".equalsIgnoreCase(opts.outMimeType)) {
			// png格式图片带透明通道
			opts.inPreferredConfig = Config.ARGB_8888;
		} else {
			// 其他图片格式不带透明通道，每个像素点不需要占用那么多字节
			opts.inPreferredConfig = Config.RGB_565;
		}
		try {
			bitmap = BitmapFactory.decodeStream(new FileInputStream(path),
					null, opts);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * decode出来的所有图片不大于100k,实际上会是100k左右 （麻麻再也不用担心解析图片oom了）
	 * 
	 * @param path
	 * @return
	 */
	public static Bitmap decodeBitmap(String path) {
		Bitmap bitmap = null;
		if (TextUtils.isEmpty(path)) {
			return null;
		}
		BitmapFactory.Options opts = new Options();
		opts.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeFile(path, opts);
		int sample = 1;
		opts.inJustDecodeBounds = false;
		opts.inPurgeable = true;
		opts.inInputShareable = true;
		// 每个像素点所占用的字节
		int countPerPix = 0;
		if ("image/png".equalsIgnoreCase(opts.outMimeType)) {
			// png格式图片带透明通道
			opts.inPreferredConfig = Config.ARGB_8888;
			// ARGB_8888 一个像素占4个自己
			countPerPix = 4;
		} else {
			// 其他图片格式不带透明通道，每个像素点不需要占用那么多字节
			opts.inPreferredConfig = Config.RGB_565;
			// 占两个字节
			countPerPix = 2;
		}
		int width = opts.outWidth;
		int height = opts.outHeight;
		// 压缩图片使他小于100k
		while ((width / sample) * (height / sample) * countPerPix > 100 * 1024) {
			sample++;
		}
		opts.inSampleSize = sample;
		try {
			bitmap = BitmapFactory.decodeStream(new FileInputStream(new File(
					path)), null, opts);
			if (bitmap != null) {
				int size = bitmap.getRowBytes() * bitmap.getHeight() / 1024;
				Log.e("dream", "size=" + size);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	/**
	 * 注意：如果图片被压缩，path所在的图片会被压缩！！！
	 * @param path 图片的路径
	 * @param maxWidth 限定的宽度，图片的长宽如果大于该值的话，会被压缩
	 */
	public static void compress(String path,int maxWidth)
	{
		Bitmap bitmap = null;
		FileOutputStream stream = null;

		Options opts = new Options();

		int width = 0;
		int height = 0;

		int newValue = maxWidth;

		try
		{
			opts.inJustDecodeBounds = true;
			bitmap = BitmapFactory.decodeFile(path, opts);

			int val = 0;

			if (opts.outHeight > opts.outWidth)
			{
				val = opts.outHeight / newValue;
			}
			else
			{
				val = opts.outWidth / newValue;
			}

			opts.inSampleSize = val;
			opts.inJustDecodeBounds = false;
			bitmap = BitmapFactory.decodeFile(path, opts);

			width = bitmap.getWidth();
			height = bitmap.getHeight();

			float sxValue = 0;

			if (height > width)
			{
				sxValue = ((float) newValue) / height;
			}
			else
			{
				sxValue = ((float) newValue) / width;
			}

			Matrix matrix = new Matrix();

			if (width > height)
			{
				// 竖向显示图片，如果宽大于高，则翻转90度或者-90度
				matrix.postRotate(90);
				bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
				width = bitmap.getWidth();
				height = bitmap.getHeight();
				sxValue = ((float) newValue) / height;
				matrix.reset();
			}

			// 缩放图片动作
			// matrix.postScale(scaleWidth,
			// scaleHeight);//指定的宽高压缩比率，可能变形，因为新的宽高与原始图片可能不成比例
			matrix.postScale(sxValue, sxValue);// 以宽或高的压缩比率去整体压缩，不变形
			// 创建新的图片
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);

			stream = new FileOutputStream(path);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
			bitmap.recycle();
			stream.flush();
			stream.close();

			if (bitmap != null)
			{
				bitmap.recycle();
				bitmap = null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取固定尺寸的bitmap
	 * 
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap getSolideSizeBitmap(Bitmap bm, int width, int height) {
		float widthScale = (width * 1f) / (bm.getWidth() * 1f);
		float heightScale = (height * 1f) / (bm.getHeight() * 1f);
		Matrix matrix = new Matrix();
		matrix.setScale(widthScale, heightScale);
		return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(),
				matrix, false);
	}

	/**
	 * 获取圆角图片
	 * 
	 * @param bitmap
	 * @param radius
	 *            ，圆角半径的大小
	 * @return
	 */
	public static Bitmap getRoundCornerBitmap(Bitmap bitmap, int radius) {
		// Canvas canvas = new Canvas(bitmap);
		// Rect r =new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		// RectF rect = new RectF(r);
		// Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		// paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
		// canvas.drawRoundRect(rect, 3f, 3f, paint);
		// // canvas.drawBitmap(bitmap, 0, 0, paint);
		// return bitmap;
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		// final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = radius;
		paint.setAntiAlias(true);
		// canvas.drawARGB(0, 0, 0, 0);
		// paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, new Paint(
				Paint.ANTI_ALIAS_FLAG));
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	public static DisplayMetrics getDisplayMetrics() {
		DisplayMetrics metrics = new DisplayMetrics();
		WindowManager manager = (WindowManager) global.ApplicationCore.getInstance()
				.getSystemService(Context.WINDOW_SERVICE);
		manager.getDefaultDisplay().getMetrics(metrics);
		return metrics;
	}

	/**
	 * 根据矩阵压缩：大于制定的尺寸，将被压缩成制定的尺寸，小于制定的尺寸，不压缩
	 * 
	 * @param path
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap getPressedSolidBitmapByPath(String path, int width,
			int height) {
		if (TextUtils.isEmpty(path)) {
			return null;
		}
		Bitmap bitmap = null;
		BitmapFactory.Options opts = new Options();
		opts.inJustDecodeBounds = true;
		bitmap = BitmapFactory.decodeFile(path, opts);
		// 重新获取bitmap
		opts.inJustDecodeBounds = false;
		opts.inPurgeable = true;
		opts.inInputShareable = true;
		if ("image/png".equalsIgnoreCase(opts.outMimeType)) {
			// png格式图片带透明通道
			opts.inPreferredConfig = Config.ARGB_8888;
		} else {
			// 其他图片格式不带透明通道，每个像素点不需要占用那么多字节
			opts.inPreferredConfig = Config.RGB_565;
		}
		try {
			bitmap = BitmapFactory.decodeStream(new FileInputStream(path),
					null, opts);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (bitmap == null) {
			return null;
		}
		Matrix matrix = new Matrix();
		float widthScale = 1;
		float heightScale = 1;
		if (opts.outWidth > width || opts.outHeight > height) {
			widthScale = (width * 1f) / (bitmap.getWidth() * 1f);
			heightScale = (height * 1f) / (bitmap.getHeight() * 1f);
		}
		matrix.setScale(widthScale, heightScale);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
				bitmap.getHeight(), matrix, false);
	}
	/**
	 * 
	 * @param fromPath
	 * @param toPath
	 * @param deleteSrc 是否删除源文件
	 * @return
	 */
	public static boolean copy(String fromPath,String toPath,boolean deleteSrc){
		boolean result = false;
		if(TextUtils.isEmpty(fromPath)){
			return result;
		}
		File fromFile = new File(fromPath);
		if(!fromFile.exists()){
			return result;
		}
		File toFile = new File(toPath);
		if(!toFile.exists()){
			try {
				toFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return copy(fromFile, toFile, deleteSrc);
	}
	public static boolean copy(File fromFile, File toFile,boolean deleteSrc)
	{
		boolean result = false;
		try
		{
			FileInputStream fosfrom = new FileInputStream(fromFile);
			java.io.FileOutputStream fosto = new FileOutputStream(toFile);
			byte bt[] = new byte[1024];
			int c;
			while ((c = fosfrom.read(bt)) > 0)
			{
				fosto.write(bt, 0, c); // 将内容写到新文件当中
			}
			// 关闭数据流
			fosfrom.close();
			fosto.close();
			if(deleteSrc){
				fromFile.delete();
			}
			result = true;
		}
		catch (Exception e)
		{
			result = false;
		}
		return result;
	}
}
