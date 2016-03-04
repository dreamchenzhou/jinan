package topevery.um.com.multipic.album;

import java.io.Serializable;

/**
 * 
 * 	图片信息
 */
public class ImageItem implements Serializable{
	
	/**
	 * 图片的id
	 */
	private String id;
	
	/**
	 * 图片的名称
	 */
	private String name;
	
	/**
	 * 图片的缩略图
	 */
	private String thumbNail;
	
	/**
	 * 图片的物理路径
	 */
	private String path;

	/**
	 * 图片大小
	 */
	private long size;
	
	/**
	 * 创建日期
	 */
	private String date;

	/**
	 * 0为图片展示模式，点击无效
	 * 1为图片添加模式，点击有效
	 * 注意：如果添加其他模式请在此注明
	 */
	private int type;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getThumbNail() {
		return thumbNail;
	}

	public void setThumbNail(String thumbNail) {
		this.thumbNail = thumbNail;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
}
