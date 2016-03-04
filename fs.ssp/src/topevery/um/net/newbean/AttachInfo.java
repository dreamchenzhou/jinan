package topevery.um.net.newbean;

import java.io.Serializable;
import java.util.UUID;

import topevery.framework.system.DateTime;

public class AttachInfo implements Serializable {
	 /// <summary>
    /// 文件id
    /// </summary>
    private UUID Id;
    /// <summary>
    /// 文件名称
    /// </summary>
    private String Name;

    /// <summary>
    ///   文件类型 -1:未知
    ///  0：照片
    ///  1：声音
    ///  2: 视频
    ///  3: 其它
    /// </summary>
    private int Type;

    /// <summary>
    /// 0:问题原始附件
    /// 1:核实附件
    /// 2:问题核查附件
    /// 3:专业部门附件
    /// 4:其他附件
    /// </summary>
    private int UsageType;

    /// <summary>
    /// 资源路径
    /// </summary>
    private String Uri;

    /// <summary>
    /// 创建时间
    /// </summary>
    private DateTime CreateDate = DateTime.getNow();

    private boolean IsChecked = false;

    private boolean Uploaded = false;

    
    /**
	 * 0是正常的，1是添加图标，需要其他类型，请添加说明
	 */
	private int viewType  = 0;
	
	public UUID getId() {
		if(Id ==null){
			Id = UUID.randomUUID();
		}
		return Id;
	}

	public void setId(UUID id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}

	public int getUsageType() {
		return UsageType;
	}

	public void setUsageType(int usageType) {
		UsageType = usageType;
	}

	public String getUri() {
		return Uri;
	}

	public void setUri(String uri) {
		Uri = uri;
	}

	public DateTime getCreateDate() {
		return CreateDate;
	}

	public void setCreateDate(DateTime createDate) {
		CreateDate = createDate;
	}

	public boolean isIsChecked() {
		return IsChecked;
	}

	public void setIsChecked(boolean isChecked) {
		IsChecked = isChecked;
	}

	public boolean isUploaded() {
		return Uploaded;
	}

	public void setUploaded(boolean uploaded) {
		Uploaded = uploaded;
	}

	public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}
	
    
}
