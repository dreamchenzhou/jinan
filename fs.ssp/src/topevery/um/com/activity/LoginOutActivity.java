package topevery.um.com.activity;

import topevery.um.com.base.BaseActivity;
import topevery.um.com.inject.InjectUtility;
import topevery.um.com.inject.ViewInject;
import topevery.um.com.utils.LoginUtils;
import topevery.um.com.utils.SpUtils;
import topevery.um.jinan.manager.R;
import topevery.um.net.newbean.UserCache;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * 登出界面
 */
public class LoginOutActivity extends BaseActivity implements OnClickListener{
	@ViewInject(idStr="btn_reset_pw",click="onResetClick")
	private Button btn_reset;

	@ViewInject(idStr="btn_login_out",click="onLoginOutClick")
	private Button btn_loginout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loginout);
		InjectUtility.initInjectedView(this);
		initTitleBar();
	}
	
	private void initTitleBar() {
		mAbBottomBar.setVisibility(View.GONE);
		mAbTitleBar.setVisibility(View.VISIBLE);
		mAbTitleBar.setBackgroundResource(R.drawable.main_title_bar_bg);
		View view = LayoutInflater.from(this).inflate(
				R.layout.titile_bar_normal, null);
		view.findViewById(R.id.btn_left).setOnClickListener(this);
		view.findViewById(R.id.btn_right).setVisibility(View.INVISIBLE);
		TextView title = (TextView) view.findViewById(R.id.txt_title);
		title.setText(getString(R.string.title_account));
		mAbTitleBar.addView(view, 0);
	}

	
	public void onResetClick(View v) {
		Intent intent = new Intent(this,ResetPWActivity.class);
		startActivity(intent);
	}
	
	public void onLoginOutClick(View v){
		LoginUtils.loginOut();
		finish();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_left:
			finish();
			break;

		default:
			break;
		}
		
	}
	
	@Override
	protected void onResume() {
		if(!SpUtils.getBoolean(this, UserCache.IS_LOGIN)||SpUtils.getInt(this, 
				UserCache.KEY_USER_ID)==0){
			finish();
		}
		super.onResume();
	}
}
