package topevery.um.com.fragment;


import java.util.Random;

import topevery.android.core.MsgBox;
import topevery.um.com.Constants;
import topevery.um.com.activity.HelpActivity;
import topevery.um.com.activity.LoginActivity;
import topevery.um.com.activity.LoginOutActivity;
import topevery.um.com.base.BaseFragment;
import topevery.um.com.utils.SpUtils;
import topevery.um.com.view.selector.ScaleImageButton;
import topevery.um.jinan.manager.R;
import topevery.um.net.newbean.UserCache;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class MenuFragment extends BaseFragment implements OnItemClickListener,OnClickListener,IWXAPIEventHandler{

	private View mView;
	private TextView txt_name;
	private ListView list_menu;
	
	private ScaleImageButton btn_self_login;
	
	private ScaleImageButton btn_wechat_login;
	
	private Button btn_clear_data;
	
	// IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    
    private String safe_code = "";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_menu, null);
		return mView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initTheThirdLogin();
		txt_name = (TextView) mView.findViewById(R.id.txt_name);
		list_menu = (ListView) mView.findViewById(R.id.list_menu);
		ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getActivity(), 
				android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.array_help_function));
		list_menu.setAdapter(mAdapter);
		list_menu.setOnItemClickListener(this);
		btn_self_login = (ScaleImageButton) mView.findViewById(R.id.btn_self_login);
		btn_wechat_login = (ScaleImageButton) mView.findViewById(R.id.btn_wechat_login);
		btn_clear_data = (Button) mView.findViewById(R.id.btn_clear_cache);
		btn_self_login.setOnClickListener(this);
		btn_wechat_login.setOnClickListener(this);
		btn_clear_data.setOnClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent= new Intent();
		switch (position) {
		//检查系统版本
		case 0:
			//TODO
			
			break;
		//系统帮助
		case 1:
			intent.setClass(getActivity(), HelpActivity.class);
			startActivity(intent);
			break;
		case 2:

			break;
		case 3:

			break;
		case 4:

			break;
		default:
			break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_self_login:
			if(SpUtils.getBoolean(getActivity(), UserCache.IS_LOGIN)&&SpUtils.getInt(getActivity(), UserCache.KEY_USER_ID)>0){
				Intent intent= new Intent(getActivity(), LoginOutActivity.class);
				startActivity(intent);
			}else{
				Intent intent = new Intent(getActivity(),LoginActivity.class);
				startActivity(intent);
			}
			break;

		// 第三方微信登录
		case R.id.btn_wechat_login:
			getCode();
			break;
		case R.id.btn_clear_cache:
		//TODO 清除缓存数据
			
			break;
		default:
			break;
		}
	}
	
	/**
	 * 初始化第三方登录
	 */
	private void initTheThirdLogin(){
		// 通过WXAPIFactory工厂，获取IWXAPI的实例
    	api = WXAPIFactory.createWXAPI(getActivity(), Constants.APP_ID, true);
    	api.registerApp(Constants.APP_ID);
    	api.handleIntent(new Intent(), this);
	}
	
	/**
	 * 获取授权登录票据
	 */
	private void getCode(){
    	 SendAuth.Req req = new SendAuth.Req();
    	 req.scope = "snsapi_userinfo";
    	 safe_code = getRandomSafeCode(10);
    	 req.state = safe_code;
    	 api.sendReq(req);
	}
	
	/**
	 * 随机安全码
	 * @param count
	 * @return
	 */
	private String getRandomSafeCode(int count){
		StringBuffer sb = new StringBuffer();
		String  src = "abcdefghijklmnopqrstuvwxyzABCDEFJHIJKLMNOPQRSTUVWXYZ";
		int length=src.length();
		Random random= new Random();
		for(int i=0;i<count;i++){
			int index = random.nextInt(length);
			sb.append(src.charAt(index));
		}
		return sb.toString();
	}

	@Override
	public void onReq(BaseReq req) {
		switch (req.getType()) {
		default:
			break;
		}
	}

	@Override
	public void onResp(BaseResp resp) {
		int result = 0;
		switch (resp.errCode) {
		// 授权成功
		case BaseResp.ErrCode.ERR_OK:
			//TODO 把code发送给服务器，返回用户信息
			result = R.string.authou_success;
			break;
			
		// 取消了授权
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			//TODO
			result = R.string.authou_cancel;
			break;
			
		// 授权被拒觉
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			//TODO
			result = R.string.authou_deny;
			break;
		default:
			break;
		}
		MsgBox.makeTextSHORT(getActivity(), getString(result));
	}
	
	
}
