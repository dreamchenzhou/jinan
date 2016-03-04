package topevery.um.com.main;

import topevery.um.com.casereport.history.HistoryCase;
import topevery.um.com.casereport.report.Casereport;
import topevery.um.com.zijunlin.Zxing.Demo.CaptureActivity;
import topevery.um.map.MapManager;
import android.content.Intent;
import android.view.View;
import android.widget.GridView;
import topevery.um.jinan.manager.R;
import global.ActivityWeb;

public class MainHelper3
{
	public static void setGridViewitem(Main main)
	{
		GridItemList objects1 = new GridItemList();
		GridItemList objects2 = new GridItemList();
		objects1.add(new GridItem(main, R.xml.menu_report, "问题上报"));
		objects1.add(new GridItem(main, R.xml.menu_history, "历史记录查询"));

		objects2.add(new GridItem(main, R.xml.menu_map, "地图浏览"));
		objects2.add(new GridItem(main, R.xml.menu_web2, "便民服务"));
		// objects2.add(new GridItem(main, R.xml.menu_web, "扫一扫"));

		objects2.add(new GridItem(main, R.xml.menu_call, "电话举报"));
		objects2.add(new GridItem(main, R.xml.menu_msg, "短信举报"));
		objects2.add(new GridItem(main, R.xml.menu_about, "关于我们"));

		GridItemAdapter adapter = new GridItemAdapter(main, objects1);
		GridItemAdapter adapter2 = new GridItemAdapter(main, objects2);

		GridView gridView = (GridView) main.findViewById(R.id.gridview1);
		GridView gridView2 = (GridView) main.findViewById(R.id.gridview2);

		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(main);
		gridView2.setAdapter(adapter2);
		gridView2.setOnItemClickListener(main);
	}

	public static void setOnItemClick(View view, Intent intent, Main cMain)
	{
		switch (view.getId())
		{
			case R.xml.menu_history:
				intent = new Intent();
				intent.setClass(cMain, HistoryCase.class);
				cMain.startActivity(intent);
				break;
			case R.xml.menu_report:
				intent = new Intent();
				intent.setClass(cMain, Casereport.class);
				cMain.startActivity(intent);
				break;
			// case R.xml.menu_update:
			// UpSysDialog.update(cMain);
			// break;
			case R.xml.menu_call:
				cMain.setCall();
				break;
			case R.xml.menu_about:
				// MsgBox.show(cMain,
				// cMain.getString(R.string.explain_context));
				MainDialog.show(cMain);
				break;
			case R.xml.menu_msg:
				cMain.setMsg();
				break;
			case R.xml.menu_web:
				intent = new Intent();
				intent.setClass(cMain, CaptureActivity.class);
				cMain.startActivity(intent);
				break;
			case R.xml.menu_map:
				MapManager.show(cMain);
				break;
			case R.xml.menu_web2:
			case R.xml.menu_bmfw:
				intent = new Intent();
				// intent.putExtra("url", DisplayUtils.getUrl());

				String url = "http://121.42.53.142/PublicServer.jn/mobile/xcum/bmfw.aspx?type=1&hide=1";
				intent.putExtra("url", url);

				intent.setClass(cMain, ActivityWeb.class);
				cMain.startActivity(intent);
				break;
		}
	}
}
