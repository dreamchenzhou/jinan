package topevery.um.com.main;

import topevery.um.com.casereport.history.HistoryCase;
import topevery.um.com.casereport.report.Casereport;
import topevery.um.com.grid.GridItems;
import topevery.um.com.zijunlin.Zxing.Demo.CaptureActivity;
import topevery.um.map.MapManager;
import android.content.Intent;
import android.view.View;
import android.widget.GridView;
import topevery.um.jinan.manager.R;
import global.ActivityWeb;

public class MainHelper
{
	private static enum Tags
	{
		政策法规库, 城管动态, 政务微信, 投诉建议, 历史记录, 地图浏览, 便民服务, 扫一扫, 单键拨号, 短信举报, 关于我们
	}

	public static void setGridViewitem(Main main)
	{
		GridItems items = new GridItems();

		items.add(main, Tags.投诉建议, R.drawable.menu_wtsb);
		items.add(main, Tags.历史记录, R.drawable.menu_lsjl);
		items.add(main, Tags.地图浏览, R.drawable.menu_dtll);

		items.add(main, Tags.便民服务, R.drawable.menu_ajcx);
		items.add(main, Tags.单键拨号, R.drawable.menu_txl);
		items.add(main, Tags.短信举报, R.drawable.menu_yydx);

		items.add(main, Tags.政策法规库, R.drawable.menu_zxbl);
		items.add(main, Tags.城管动态, R.drawable.menu_xtsj);
		items.add(main, Tags.政务微信, R.drawable.menu_sjtb);

		// items.add(main, Tags.关于我们, R.drawable.menu_grrw);

		// items.add(new GridItem(main, R.xml.menu_report, "问题上报"));
		// items.add(new GridItem(main, R.xml.menu_history, "历史记录查询"));
		//
		// items.add(new GridItem(main, R.xml.menu_map, "地图浏览"));
		// items.add(new GridItem(main, R.xml.menu_web2, "便民服务"));
		//
		// items.add(new GridItem(main, R.xml.menu_call, "电话举报"));
		// items.add(new GridItem(main, R.xml.menu_msg, "短信举报"));
		// items.add(new GridItem(main, R.xml.menu_about, "关于我们"));

		GridView gridView = (GridView) main.findViewById(R.id.gridview1);

		items.setAdapter(gridView, main);
	}

	public static void setOnItemClick(View view, Intent intent, Main cMain)
	{
		switch (view.getId())
		{
			case R.xml.menu_history:
			case R.drawable.menu_lsjl:
				intent = new Intent();
				intent.setClass(cMain, HistoryCase.class);
				cMain.startActivity(intent);
				break;
			case R.xml.menu_report:
			case R.drawable.menu_wtsb:
				intent = new Intent();
				intent.setClass(cMain, Casereport.class);
				cMain.startActivity(intent);
				break;
			// case R.xml.menu_update:
			// UpSysDialog.update(cMain);
			// break;
			case R.xml.menu_call:
			case R.drawable.menu_txl:
				cMain.setCall();
				break;
			case R.xml.menu_about:
			case R.drawable.menu_grrw:
				// MsgBox.show(cMain,
				// cMain.getString(R.string.explain_context));
				MainDialog.show(cMain);
				break;
			case R.xml.menu_msg:
			case R.drawable.menu_yydx:
				cMain.setMsg();
				break;
			case R.xml.menu_web:
				intent = new Intent();
				intent.setClass(cMain, CaptureActivity.class);
				cMain.startActivity(intent);
				break;
			case R.xml.menu_map:
			case R.drawable.menu_dtll:
				MapManager.show(cMain);
				break;
			case R.drawable.menu_ajcx:
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
