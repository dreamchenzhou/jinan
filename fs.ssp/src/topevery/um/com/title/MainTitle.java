package topevery.um.com.title;

import java.io.File;

import topevery.um.com.casereport.report.CaseTemp;
import topevery.um.com.main.MainDialog;
import topevery.um.com.utils.PathUtils;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

public class MainTitle extends Button
{
	private String pathString;
	private Context mContext;

	public MainTitle(Context context)
	{
		this(context, null);
	}

	public MainTitle(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.mContext = context;
		this.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (exists())
				{
					Intent intent = new Intent(mContext, CaseTemp.class);
					mContext.startActivity(intent);
				}
				else
				{
					MainDialog.askYes(mContext, "您的草稿箱里没有东西！");
				}
			}
		});
		// Toast.makeText(mContext, "您的草稿箱里没有东西！",
		// Toast.LENGTH_SHORT).show();

	}

	private boolean exists()
	{
		pathString = PathUtils.getTemp();
		File file = new File(pathString);
		File[] files = file.listFiles();
		if (files == null || files.length == 0)
		{
			return false;
		}
		return true;
	}
}
