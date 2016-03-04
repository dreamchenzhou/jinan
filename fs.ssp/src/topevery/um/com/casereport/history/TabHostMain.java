package topevery.um.com.casereport.history;

import topevery.um.jinan.manager.R;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;

public class TabHostMain extends TabActivity
{
	private TabHost tabs;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_main);
		setUI();
	}

	private void setUI()
	{

		tabs = getTabHost();

		tabs.addTab(tabs.newTabSpec("t1").setIndicator("历史记录").setContent(new Intent(this, HistoryCase.class)));
		tabs.addTab(tabs.newTabSpec("t2").setIndicator("案件查询").setContent(new Intent(this, CheckCase.class)));

		tabs.setBackgroundColor(Color.argb(0, 0, 0, 0));
		// tabs.setBackgroundColor(Color.argb(alpha, red, green, blue))
		tabs.getTabWidget().getChildAt(0).getLayoutParams().height = 60;
		tabs.getTabWidget().getChildAt(1).getLayoutParams().height = 60;
		tabs.setCurrentTab(0);
	}

}
