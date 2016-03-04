package topevery.um.com.view.attachview;

import java.util.UUID;

import topevery.android.core.MsgBox;
import topevery.framework.system.DateTime;
import topevery.um.com.utils.BitmapUtils;
import topevery.um.com.utils.DisplayUtils;
import topevery.um.jinan.manager.R;
import topevery.um.net.srv.AttachInfo;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

@SuppressLint("NewApi") public class AttachItem extends FrameLayout implements OnClickListener{

	private ImageView img_mImage;
	
	private ImageButton img_delete;
	
	private AttachView mParent;
	
	private int index = 0;
	/**
	 * 文件id
	 */
	public UUID id = UUID.randomUUID();
	/**
	 * 文件名称
	 */
	public String name;

	// / <summary>
	// / 文件类型 -1:未知
	// /
	// / 0：照片
	// /
	// / 1：声音
	// /
	// / 2: 视频
	// /
	// / 3: 其它
	// /
	// / </summary>
	public int type = 0;

	// / <summary>
	// / 0:问题原始附件
	// / 1:核实附件
	// / 2:问题核查附件
	// / 3:专业部门附件
	// / 4:其他附件
	// / </summary>
	public int usageType = 0;

	/**
	 * 资源路径
	 */
	public String uri;

	/**
	 * 创建时间
	 */
	public DateTime createDate;

	public double absX;
	public double absY;
	public double geoX;
	public double geoY;

	// public long position = -1;

	public boolean isChecked = false;

	public boolean loadingFailed = false;

	public transient BitmapDrawable drawable;

	public boolean uploaded = false;
	public boolean isSave = false;
	
	public AttachItem(Context context) {
		super(context);
		init(context);
	}

	public AttachItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public AttachItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	
	private void init(Context context){
		img_mImage = new ImageView(context);
		img_delete= new ImageButton(context);
		LayoutParams mImage_para = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		LayoutParams delet_para = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		delet_para.gravity = Gravity.TOP|Gravity.RIGHT;
		delet_para.rightMargin = DisplayUtils.dip2px(context, 10);
		img_delete.setImageResource(R.drawable.selector_picture_delete);
		img_delete.setPadding(0, 0, 0, 0);
		img_delete.setBackground(null);
		img_delete.setOnClickListener(this);
		addView(img_mImage, 0,mImage_para);
		addView(img_delete, 1, delet_para);
	}
	
	public void setAttachInfo(AttachInfo info,AttachView parent,int index){
		this.index = index;
		if(mParent==null){
			mParent = parent;
		}
		uri = info.uri;
		id= info.id;
		name = info.name;
		type = info.type;
		usageType = info.usageType;
		createDate = info.createDate;
		uploaded = info.uploaded;
		isChecked = info.isChecked;
		isSave = info.isSave ;
		if(uri!=null&&!TextUtils.isEmpty(uri)){
			Bitmap bitmap = BitmapUtils.decodeBitmap(uri);
			if(bitmap==null){
				img_mImage.setImageResource(R.drawable.post_image_loading_lost);
			}else{
				img_mImage.setImageBitmap(BitmapUtils.getSolideSizeBitmap(bitmap, DisplayUtils.dip2px(getContext(), 80), DisplayUtils.dip2px(getContext(), 100)));
			}
		}
	}

	@Override
	public void onClick(View v) {
		MsgBox.askYesNo(getContext(), "确认删除该图片？", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(mParent!=null){
					mParent.remove(index);
				}
				dialog.dismiss();
			}
		}, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
	}

}
