package topevery.um.com.multipic.album;

import global.ApplicationCore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import topevery.um.jinan.manager.R;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Images.Thumbnails;
import android.util.Log;

/**
 * 
 * 获取相册helper类
 */
public class MediaHelper {

	private static final String TAG = MediaHelper.class.getSimpleName();

	private static List<String> pathList = new ArrayList<String>();

	private static Map<String, ImageBucket> buckets = new HashMap<String, ImageBucket>();

	private static Map<String, String> thumbList = new HashMap<String, String>();
	private static ContentResolver mCR;

	private static Context mContext;
	/**
	 * 是否已经初始化相册
	 */
	private static boolean hasInitBucket = false;

	public static void init(Context context) {
		mContext = context;
		mCR = mContext.getContentResolver();
	}

	/**
	 * 获取所有图片对应的缩略图
	 * 
	 * @return
	 */
	private static Map<String, String> getThumbNail() {
		String project[] = { Thumbnails._ID, Thumbnails.IMAGE_ID,
				Thumbnails.DATA };
		Cursor cursor = null;
		try {
			cursor = mCR.query(Thumbnails.EXTERNAL_CONTENT_URI, project, null,
					null, null);
			cursor.moveToFirst();
			while (cursor.getCount() != cursor.getPosition()) {
				int id = cursor.getInt(cursor.getColumnIndex(Thumbnails._ID));
				int imageId = cursor.getInt(cursor
						.getColumnIndex(Thumbnails.IMAGE_ID));
				String path = cursor.getString(cursor
						.getColumnIndex(Thumbnails.DATA));
				String key = String.valueOf(imageId);
				if (!thumbList.containsKey(key)) {
					thumbList.put(key, path);
				}
				cursor.moveToNext();
			}
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		} finally {
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
		}
		return thumbList;
	}

	/**
	 * 获取所有相册
	 * @param refresh 是否重新刷新
	 * @return
	 */
	public static Map<String, ImageBucket> getAllAlbums(boolean refresh) {  
		if(!refresh&&hasInitBucket){
			return buckets;
		}
		hasInitBucket = false;
		buckets.clear();
		getThumbNail();
		String projection[] = { Media._ID, Media.DATA, Media.SIZE,
				Media.DISPLAY_NAME, Media.TITLE, Media.BUCKET_DISPLAY_NAME,
				Media.BUCKET_ID, Media.PICASA_ID };
		Cursor cursor = null;
		try {
			cursor = mCR.query(Media.EXTERNAL_CONTENT_URI, projection, null,
					null, null);
			cursor.moveToFirst();
			while (cursor.getPosition() != cursor.getCount()) {
				int image_id = cursor.getInt(cursor.getColumnIndex(Media._ID));
				String image_path = cursor.getString(cursor
						.getColumnIndex(Media.DATA));
				long image_size = cursor.getLong(cursor
						.getColumnIndex(Media.SIZE));
				String pic_total_name = cursor.getString(cursor
						.getColumnIndex(Media.DISPLAY_NAME));
				String pic_name = cursor.getString(cursor
						.getColumnIndex(Media.TITLE));
				String bucket_name = cursor.getString(cursor
						.getColumnIndex(Media.BUCKET_DISPLAY_NAME));
				int bucket_id = cursor.getInt(cursor
						.getColumnIndex(Media.BUCKET_ID));
				int picasaId = cursor.getInt(cursor
						.getColumnIndex(Media.PICASA_ID));
				Log.i(TAG, "iamge_id:" + image_id + ", bucketId: " + bucket_id
						+ ", picasaId: " + picasaId + " name:" + pic_total_name
						+ " path:" + image_path + " title: " + pic_name
						+ " size: " + image_size + " bucket: " + bucket_name
						+ "---");
				ImageBucket bucket = (ImageBucket) buckets.get(String
						.valueOf(bucket_id));
				if (bucket == null) {
					bucket = new ImageBucket();
					bucket.setImageList(new ArrayList<ImageItem>());
					bucket.setBucket_id(bucket_id);
					bucket.setName(bucket_name);
					bucket.setPicasa_id(picasaId);
				}
				ImageItem item = new ImageItem();
				item.setId(String.valueOf(image_id));
				item.setName(pic_name);
				item.setPath(image_path);
				item.setThumbNail(thumbList.get(String.valueOf(image_id)));
				item.setSize(image_size);
				bucket.getImageList().add(item);
				bucket.setCount(bucket.getImageList().size());
				buckets.put(String.valueOf(bucket_id), bucket);
				
				cursor.moveToNext();
			}
			
			hasInitBucket = true;
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		} finally {
			if (cursor != null && !cursor.isClosed()) {
				cursor.close();
			}
		}
		
		return buckets;
	}

	/**
	 * 所有相册
	 * @param refresh
	 * @return
	 */
	public static List<ImageBucket> getListBuckets(boolean refresh){
		List<ImageBucket> listBuckets = new ArrayList<ImageBucket>();
		Map<String, ImageBucket>buckets = getAllAlbums(refresh);
		Iterator<Entry<String, ImageBucket>> entryIterator = buckets.entrySet()
				.iterator();
		while (entryIterator.hasNext()) {
			Entry<String, ImageBucket> entry = entryIterator.next();
			listBuckets.add(entry.getValue());
		}
		// 所有图片
		ImageBucket allPic = new ImageBucket();
		allPic.setName(ApplicationCore.getInstance().getResources().getString(R.string.all_pic));
		allPic.setImageList(getAllImageList(refresh));
		allPic.setCount(allPic.getImageList().size());
		allPic.setThumbPath(allPic.getImageList().get(0).getThumbNail());
		listBuckets.add(0, allPic);
		return listBuckets;
	}
	/**
	 * 根据所有相册中的所有图片
	 */
	public static List<ImageItem> getAllImageList(boolean refresh) {
		if(refresh||(!refresh&&!hasInitBucket)){
			getAllAlbums(refresh);
		}
		List<ImageItem> temp = new ArrayList<ImageItem>();
		Iterator<Entry<String, ImageBucket>> entryIterator = buckets.entrySet()
				.iterator();
		while (entryIterator.hasNext()) {
			Entry<String, ImageBucket> entry = entryIterator.next();
			ImageBucket bucket = entry.getValue();
			Log.e(TAG, "bucket_name:" + bucket.getName());
			for (ImageItem imageItem : bucket.getImageList()) {
				temp.add(imageItem);
				Log.e(TAG, "image_path:" + imageItem.getPath());
			}
		}
		return temp;
	}
	
	/**
	 * 根据相册的id获取相册的图片
	 */
	public static List<ImageItem> getAllImageListById(boolean refresh,int bucketId) {
		if(refresh||(!refresh&&!hasInitBucket)){
			getAllAlbums(refresh);
		}
		List<ImageItem> temp = new ArrayList<ImageItem>();
		Iterator<Entry<String, ImageBucket>> entryIterator = buckets.entrySet()
				.iterator();
		while (entryIterator.hasNext()) {
			Entry<String, ImageBucket> entry = entryIterator.next();
			ImageBucket bucket = entry.getValue();
			Log.e(TAG, "bucket_name:" + bucket.getName());
			if (bucketId == bucket.getBucket_id()) {
				for (ImageItem imageItem : bucket.getImageList()) {
					temp.add(imageItem);
					Log.e(TAG, "image_path:" + imageItem.getPath());
				}
			}
		}
		return temp;
	}
}
