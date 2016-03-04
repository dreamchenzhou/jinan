package topevery.um.com.multipic.album;

import java.util.List;

import android.R.integer;

/**
 * 
 * 相册
 */
public class ImageBucket {
	/**
	 * 相册中的相片的数量
	 */
	private int count;
	
	/**
	 * bucket_id
	 */
	private int bucket_id;
	
	/**
	 * picasa_id
	 */
	private int picasa_id;
	
	/**
	 * 相册的名称
	 */
	private String name;
	
	/**
	 * 相册的缩略图
	 */
	private String thumbPath;
	
	/**
	 * 相册中的相片
	 */
	private List<ImageItem> imageList;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getThumbPath() {
		return thumbPath;
	}

	public void setThumbPath(String thumbPath) {
		this.thumbPath = thumbPath;
	}

	public List<ImageItem> getImageList() {
		return imageList;
	}

	public void setImageList(List<ImageItem> imageList) {
		this.imageList = imageList;
	}

	public int getBucket_id() {
		return bucket_id;
	}

	public void setBucket_id(int bucket_id) {
		this.bucket_id = bucket_id;
	}

	public int getPicasa_id() {
		return picasa_id;
	}

	public void setPicasa_id(int picasa_id) {
		this.picasa_id = picasa_id;
	}

}
