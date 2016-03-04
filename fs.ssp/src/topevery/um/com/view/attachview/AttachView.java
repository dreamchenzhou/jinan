package topevery.um.com.view.attachview;

import java.io.File;

import topevery.android.core.MsgBox;
import topevery.um.com.utils.DisplayUtils;
import topevery.um.net.srv.AttachInfo;
import topevery.um.net.srv.AttachInfoCollection;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

@SuppressLint("NewApi") 
public class AttachView extends HorizontalScrollView{

	private LinearLayout mContent;
	
	private Context mContext;
	
	private AttachInfoCollection mCollection;
	
	public AttachView(Context context) {
		super(context);
		init(context);
	}

	public AttachView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public AttachView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	
	private void init(Context context){
		mContext = context;
		mCollection = new AttachInfoCollection();
		addLayoutContent();
	}
	
	private void addLayoutContent(){
		mContent = new LinearLayout(mContext);
		mContent.setOrientation(LinearLayout.HORIZONTAL);
		LayoutParams mParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		addView(mContent,mParams);
	}
	/**
	 * 一次添加一张图片
	 * @param info
	 */
	public void addValue(AttachInfo info){
		setVisibility(View.VISIBLE);
		if(mCollection.size()>=5){
			MsgBox.makeTextSHORT(mContext, "最多添加五张图片");
			return;
		}
		if(containsUri(info.uri)){
			MsgBox.makeTextSHORT(mContext, "该图片已添加");
			return;
		}
		AttachItem item = new AttachItem(mContext);
		item.setAttachInfo(info,this,getAttachSize());
		LayoutParams params = new LayoutParams(DisplayUtils.dip2px(mContext, 90),
				DisplayUtils.dip2px(mContext, 100));
		params.rightMargin=DisplayUtils.dip2px(mContext, 10);
		mContent.addView(item,getAttachSize(),params);
		mCollection.add(info);
	}
	
	private void reFill(AttachInfoCollection collection){
		for (AttachInfo info : collection) {
			AttachItem item = new AttachItem(mContext);
			item.setAttachInfo(info,this,getAttachSize());
			LayoutParams params = new LayoutParams(DisplayUtils.dip2px(mContext, 90),
					DisplayUtils.dip2px(mContext, 100));
			params.rightMargin=DisplayUtils.dip2px(mContext, 10);
			mContent.addView(item,getAttachSize(),params);
		}
	}
	
	
	/**
	 * 一次添加多个图片
	 * @param collection
	 */
	public void setValues(AttachInfoCollection collection){
		if(collection==null||collection.size()==0){
			return;
		}
		for (AttachInfo attachInfo : collection) {
			addValue(attachInfo);
		}
	}
	
	/**
	 * 获取所有添加的图片的信息
	 * @return
	 */
	public AttachInfoCollection getValues(){
//		AttachInfoCollection  collection = new AttachInfoCollection();
//		int childCount = getAttachSize();
//		if(childCount==0){
//			return collection;
//		}
//		for(int i=0;i<childCount;i++){
//			AttachItem item = (AttachItem) mContent.getChildAt(i);
//			AttachInfo info = new AttachInfo();
//			info.id = item.id;
//			info.isChecked = item.isChecked;
//			info.isSave = item.isSave;
//			info.name = item.name;
//			info.type = item.type;
//			info.usageType = item.type;
//			info.uri = item.uri;
//			info.uploaded = item.uploaded;
//			collection.add(info);
//		}
		return mCollection;
	}
	
	/**
	 * 获取当前的图片张数
	 * @return
	 */
	public int getAttachSize(){
		return mContent.getChildCount();
	}
	
	public void remove(int index){
		AttachInfo info = mCollection.remove(index);
		File file = new File(info.uri);
		if(file.exists() ){
			file.delete();
		}
		mContent.removeAllViews();
		reFill(mCollection);
		if(mCollection.size()==0){
			setVisibility(View.GONE);
		}
	}
	
	/**
	 * 上报成功后，删除所有图片文件
	 */
	public void removeAll(){
		if(mCollection!=null){
			for (AttachInfo info : mCollection) {
				File file = new File(info.uri);
				if(file.exists() ){
					file.delete();
				}
			}
			mCollection.clear();
		}
		mContent.removeAllViews();
		setVisibility(View.GONE);
	}
	
	public boolean containsUri(String path){
		for (AttachInfo info : mCollection) {
			if(info.uri.equals(path)){
				return true;
			}
		}
		return false;
	}
}
