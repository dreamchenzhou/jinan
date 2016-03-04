package topevery.um.com.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import topevery.android.core.MsgBox;
import topevery.um.com.activity.CaseReportNew;
import topevery.um.com.media.ChatMessage;
import topevery.um.com.media.MediaPlayer;
import topevery.um.com.media.RecordVoiceBtnController;
import topevery.um.jinan.manager.R;
import topevery.um.net.newbean.AttachInfo;
import topevery.um.net.newbean.AttachInfoCollection;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class VoiceAdapter extends BaseAdapter implements OnClickListener{

	private final int res_id =R.layout.chat_item_receive_voice;
	
	private List<ChatMessage> mData = new ArrayList<ChatMessage>();
	private Context mContext;
	private AnimationDrawable mVoiceAnimation;
	private RecordVoiceBtnController btn_record;
	public VoiceAdapter(Context context,RecordVoiceBtnController btnController){
		this.mContext = context;
		this.btn_record = btnController;
	}
	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public ChatMessage getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(convertView==null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(parent.getContext()).inflate(res_id, null);
			viewHolder.voice_iv = (ImageView) convertView.findViewById(R.id.voice_iv);
			viewHolder.voice_length_tv = (TextView) convertView.findViewById(R.id.voice_length_tv);
			viewHolder.btn_voice_del = (ImageButton) convertView.findViewById(R.id.btn_voice_del);
			viewHolder.voice_body = convertView.findViewById(R.id.voice_body);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.voice_length_tv.setText(getItem(position).getName() + "\"");
		viewHolder.voice_body.setOnClickListener(this);
		viewHolder.voice_body.setTag(R.id.voice_body,getItem(position).getFile().getAbsolutePath());
		viewHolder.voice_body.setTag(R.id.voice_iv,viewHolder.voice_iv);
		viewHolder.btn_voice_del.setTag(position);
		viewHolder.btn_voice_del.setOnClickListener(this);
		
		return convertView;
	}
	
	class ViewHolder{
		public ImageView voice_iv;
		
		public TextView voice_length_tv;
		
		public ImageButton btn_voice_del; 
		
		public View voice_body;
	}

	@Override
	public void onClick(final View v) {
		switch (v.getId()) {
		case R.id.voice_body:
			String filePath = (String) v.getTag(R.id.voice_body);
			ImageView voice_iv = (ImageView) v.getTag(R.id.voice_iv);
			playAudio(filePath, voice_iv);
			break;
		case R.id.btn_voice_del:
//			MsgBox.askYesNo(mContext, "确认删除该条录音？", new DialogInterface.OnClickListener() {
//				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					int position = (Integer) v.getTag();
//					ChatMessage msg = mData.remove(position);
//					File file = msg.getFile();
//					if(file!=null&&file.exists()){
//						file.delete();
//					}
//					notifyDataSetChanged();
//					CaseReportNew activity = (CaseReportNew) mContext;
//					activity.setListViewHeightBasedOnChildren();
//					if(mData.size()==0){
//						activity.mList.setVisibility(View.GONE);
//					}
//				}
//			}, new DialogInterface.OnClickListener() {
//				
//				@Override
//				public void onClick(DialogInterface dialog, int which) {
//					dialog.dismiss();
//				}
//			});
			int position = (Integer) v.getTag();
			ChatMessage msg = mData.remove(position);
			File file = msg.getFile();
			if(file!=null&&file.exists()){
				file.delete();
			}
			notifyDataSetChanged();
			CaseReportNew activity = (CaseReportNew) mContext;
			activity.setListViewHeightBasedOnChildren();
			if(mData.size()==0){
				activity.mList.setVisibility(View.GONE);
			}
			break;

		default:
			break;
		}
	}
	
	public void AddChatMessage(ChatMessage msg){
		if(mData.size()>=1){
			MsgBox.makeTextSHORT(mContext, "只能上报一条录音哦");
			File audioFile = msg.getFile();
			if(audioFile!=null&&audioFile.exists()){
				audioFile.delete();
			}
			return;
		}
		mData.add(msg);
		notifyDataSetChanged();
		CaseReportNew activity =(CaseReportNew) mContext;
		activity.setListViewHeightBasedOnChildren();
	}
	
	
	private void playAudio(String filePath,ImageView voice_iv)
	{
		if (mVoiceAnimation != null)
		{
			mVoiceAnimation.stop();
		}
		voice_iv.setImageResource(R.anim.voice_receive);
		mVoiceAnimation = (AnimationDrawable) voice_iv.getDrawable();
		playVoice(filePath);
	}

	private void playVoice(String filePath)
	{
		File audioFile = new File(filePath);
		if (mVoiceAnimation != null)
		{
			mVoiceAnimation.start();
		}
		if (audioFile != null && audioFile.exists())
		{
			btn_record.setEnabled(false);
			MediaPlayer.getInstance().play(audioFile);
		}
		else
		{

		}
		MediaPlayer.getInstance().setOnCompletionListener(new android.media.MediaPlayer.OnCompletionListener()
		{
			@Override
			public void onCompletion(android.media.MediaPlayer arg0)
			{
				btn_record.setEnabled(true);
				if (mVoiceAnimation != null)
				{
					mVoiceAnimation.stop();
				}
				MediaPlayer.getInstance().reset();
			}
		});
	}
	
	public AttachInfoCollection getAttachInfoCollection(){
		AttachInfoCollection collection = new AttachInfoCollection();
		if(mData.size()==0){
			return collection;
		}
		for(ChatMessage msg:mData){
			AttachInfo info= new AttachInfo();
			String path = msg.getFile().getAbsolutePath();
			info.setUri(path);
			info.setUsageType(0);
			info.setType(1);
			info.setName(path.substring(path.lastIndexOf("/")+1,path.length()));
			collection.add(info);
		}
		return collection;
	}
	
	/**
	 * 上报成功，删除录音文件
	 */
	public void removeAllFile(){
		if(mData!=null){
			for (ChatMessage msg : mData) {
				File file = msg.getFile();
				if(file!=null&&file.exists()){
					file.delete();
				}
			}
			mData.clear();
			notifyDataSetChanged();
		}
	}

}
