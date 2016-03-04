package topevery.um.com.fragment;

import global.ActivityWeb;
import topevery.um.com.activity.CaseReportNew;
import topevery.um.com.activity.QuestionSearchNew;
import topevery.um.com.activity.WechatActivity;
import topevery.um.com.base.BaseFragment;
import topevery.um.com.casereport.report.Casereport;
import topevery.um.com.grid.GridItems;
import topevery.um.com.main.MainDialog;
import topevery.um.com.utils.FunctionUtils;
import topevery.um.com.zijunlin.Zxing.Demo.CaptureActivity;
import topevery.um.jinan.manager.R;
import topevery.um.map.MapManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class ContentFragment extends BaseFragment implements OnItemClickListener{
	private View mView;

	private GridView grid_main;
	private static Context mContext;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_content, null);
		return mView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		grid_main =  (GridView) mView.findViewById(R.id.grid_main);
		mContext = getActivity();
		setGridViewitem();
	}

	private static enum Tags
	{
		政策法规库, 城管动态, 政务微信, 投诉建议, 历史记录, 地图浏览, 便民服务, 扫一扫, 单键拨号, 短信举报, 关于我们
	}
	
	public  void setGridViewitem()
	{
		GridItems items = new GridItems();

		items.add(mContext, Tags.投诉建议, R.drawable.selector_icon_main_tsjy);
		items.add(mContext, Tags.历史记录, R.drawable.selector_icon_main_lsjl);
		items.add(mContext, Tags.地图浏览, R.drawable.selector_icon_main_dtll);

		items.add(mContext, Tags.便民服务, R.drawable.selector_icon_main_bmfw);
		items.add(mContext, Tags.单键拨号, R.drawable.selector_icon_main_djbh);
		items.add(mContext, Tags.短信举报, R.drawable.selector_icon_main_dxjb);

		items.add(mContext, Tags.政策法规库, R.drawable.selector_icon_main_zcfg);
		items.add(mContext, Tags.城管动态, R.drawable.selector_icon_main_cgdt);
		items.add(mContext, Tags.政务微信, R.drawable.selector_icon_main_zwwx);

		// items.add(mContext, Tags.关于我们, R.drawable.menu_grrw);

		// items.add(new GridItem(mContext, R.xml.menu_report, "问题上报"));
		// items.add(new GridItem(mContext, R.xml.menu_history, "历史记录查询"));
		//
		// items.add(new GridItem(mContext, R.xml.menu_map, "地图浏览"));
		// items.add(new GridItem(mContext, R.xml.menu_web2, "便民服务"));
		//
		// items.add(new GridItem(mContext, R.xml.menu_call, "电话举报"));
		// items.add(new GridItem(mContext, R.xml.menu_msg, "短信举报"));
		// items.add(new GridItem(mContext, R.xml.menu_about, "关于我们"));


		items.setAdapter(grid_main, this);
	}

	public  void setOnItemClick(View view, Intent intent, Context cMain)
	{
		switch (view.getId())
		{
			case R.xml.menu_history:
			case R.drawable.menu_lsjl:
			case R.drawable.selector_icon_main_lsjl:
				intent = new Intent();
				intent.setClass(cMain, QuestionSearchNew.class);
				cMain.startActivity(intent);
				break;
			case R.xml.menu_report:
			case R.drawable.menu_wtsb:
			case R.drawable.selector_icon_main_tsjy:
				intent = new Intent();
				intent.setClass(cMain, CaseReportNew.class);
				cMain.startActivity(intent);
				break;
			// case R.xml.menu_update:
			// UpSysDialog.update(cMain);
			// break;
			case R.xml.menu_call:
			case R.drawable.menu_txl:
			case R.drawable.selector_icon_main_djbh:
				FunctionUtils.setCall(cMain);
				break;
			case R.xml.menu_about:
			case R.drawable.menu_grrw:
				// MsgBox.show(cMain,
				// cMain.getString(R.string.explain_context));
				MainDialog.show(cMain);
				break;
			case R.xml.menu_msg:
			case R.drawable.menu_yydx:
			case R.drawable.selector_icon_main_dxjb:
				FunctionUtils.setMsg(cMain);
				break;
			case R.xml.menu_web:
				intent = new Intent();
				intent.setClass(cMain, CaptureActivity.class);
				cMain.startActivity(intent);
				break;
			case R.xml.menu_map:
			case R.drawable.menu_dtll:
			case R.drawable.selector_icon_main_dtll:
				MapManager.show(cMain);
				break;
			case R.drawable.menu_ajcx:
			case R.xml.menu_web2:
			case R.xml.menu_bmfw:
			case R.drawable.selector_icon_main_bmfw:
				intent = new Intent();
				// intent.putExtra("url", DisplayUtils.getUrl());

				String url = "http://121.42.53.142/PublicServer.jn/mobile/xcum/bmfw.aspx?type=1&hide=1";
				intent.putExtra("url", url);

				intent.setClass(cMain, ActivityWeb.class);
				cMain.startActivity(intent);
				break;
			// 政务微信
			case R.drawable.menu_sjtb:
			case R.drawable.selector_icon_main_zwwx:
				intent.setClass(cMain, WechatActivity.class);
				startActivity(intent);
				break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		setOnItemClick(view, new Intent(), mContext);
	}
}
