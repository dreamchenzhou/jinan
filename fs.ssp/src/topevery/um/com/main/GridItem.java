package topevery.um.com.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import topevery.um.jinan.manager.R;

@SuppressLint("InflateParams")
public class GridItem extends FrameLayout
{
	private ImageView imageView;
	private TextView itemTitle;

	public GridItem(Context context, int defStyle, String title)
	{
		super(context);
		View convertView = LayoutInflater.from(context).inflate(R.layout.main_item, null);
		addView(convertView);

		imageView = (ImageView) this.findViewById(R.id.iamgeView);
		itemTitle = (TextView) this.findViewById(R.id.itemTitle);
		itemTitle.setText(title);
		setImageView(defStyle);
	}

	private void setImageView(int imageView)
	{
		this.setId(imageView);
		this.imageView.setBackgroundResource(imageView);
	}
}
