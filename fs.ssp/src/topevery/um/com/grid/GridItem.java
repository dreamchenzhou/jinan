package topevery.um.com.grid;

import java.io.File;
import java.io.Serializable;

import topevery.android.core.MsgBox;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import topevery.um.jinan.manager.R;

@SuppressWarnings("serial")
public class GridItem extends FrameLayout implements Serializable
{
	private static final int resource = R.layout.grid_item_view;
	private Context mContext;
	private LayoutInflater mInflater;

	private ImageView gridImg;
	private TextView gridTitle, gridCount;

	private ProgressBar barUpdate;
	private Button btnUpdate;

	private String apkPath;

	public GridItem(Context context)
	{
		super(context);
		this.mContext = context;
		this.mInflater = LayoutInflater.from(mContext);
		// this.setBackgroundResource(R.drawable.bg_griditem);
		onCreateView();
	}

	private void onCreateView()
	{
		View convertView = this.mInflater.inflate(resource, null);
		this.addView(convertView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

		gridImg = (ImageView) convertView.findViewById(R.id.gridImg);
		gridTitle = (TextView) convertView.findViewById(R.id.gridTitle);
		gridCount = (TextView) convertView.findViewById(R.id.gridCount);
		gridCount.setVisibility(View.GONE);

		barUpdate = (ProgressBar) this.findViewById(R.id.barUpdate);
		btnUpdate = (Button) this.findViewById(R.id.btnUpdate);

		btnUpdate.setOnClickListener(vClickListener);
		barUpdate.setMax(100);
	}

	public void setGridImg(int resId)
	{
		if (resId == 0)
		{
			gridImg.setVisibility(View.INVISIBLE);
		}
		else
		{
			gridImg.setImageResource(resId);
			this.setId(resId);
		}
	}

	public void setGridTitle(CharSequence title)
	{
		gridTitle.setText(title);
	}

	public void setGridCount(int count)
	{
		if (count > 0)
		{
			gridCount.setText(String.valueOf(count));
			gridCount.setVisibility(View.VISIBLE);
		}
		else
		{
			gridCount.setVisibility(View.GONE);
		}
	}

	private View.OnClickListener vClickListener = new View.OnClickListener()
	{
		@Override
		public void onClick(View v)
		{
			install();
		}
	};

	public void setApkPath(String apkPath)
	{
		this.apkPath = apkPath;
	}

	public void install()
	{
		File file = new File(apkPath);
		if (file.exists())
		{
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
			mContext.startActivity(intent);
		}
		else
		{
			MsgBox.show(mContext, "安装程序文件不存在。");
		}
	}

	public void setProgress(int progress)
	{
		if (barUpdate.getVisibility() != View.VISIBLE)
		{
			barUpdate.setVisibility(View.VISIBLE);
		}
		barUpdate.setProgress(progress);
	}

	public void showInstall()
	{
		if (barUpdate.getVisibility() != View.GONE)
		{
			barUpdate.setVisibility(View.GONE);
		}

		if (btnUpdate.getVisibility() != View.VISIBLE)
		{
			btnUpdate.setVisibility(View.VISIBLE);
		}
	}

	public void showBar()
	{
		barUpdate.setVisibility(View.VISIBLE);
	}

	public void resetProgress()
	{
		barUpdate.setProgress(0);

		btnUpdate.setVisibility(View.GONE);
		barUpdate.setVisibility(View.GONE);
	}
}