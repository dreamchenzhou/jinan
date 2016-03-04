package topevery.um.com.activity;

import topevery.android.core.MsgBox;
import topevery.um.com.inject.InjectUtility;
import topevery.um.com.inject.ViewInject;
import topevery.um.com.task.LoginTask;
import topevery.um.com.utils.ActivityUtils;
import topevery.um.com.utils.SpUtils;
import topevery.um.jinan.manager.R;
import topevery.um.net.newbean.ParaFromPda;
import topevery.um.net.newbean.UserCache;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import global.BaseActivity;
/**
 * 
 * 登录界面
 */
public class LoginActivity extends BaseActivity {
	@ViewInject(idStr="txt_login_name")
	private EditText txt_login_name;
	@ViewInject(idStr="txt_login_pw")
	private EditText txt_login_pw;
	@ViewInject(idStr="btn_find_pw",click="onFindPWClick")
	private Button btn_find_pw;
	@ViewInject(idStr="btn_login",click="onLoginClick")
	private Button btn_login;
	@ViewInject(idStr="btn_register" ,click="onRegisterClick")
	private Button btn_register;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		InjectUtility.initInjectedView(this);
		init();
	}
	
	private void init(){
		String name = SpUtils.getString(this, UserCache.KEY_USER_NAME);
		if(!TextUtils.isEmpty(name)){
			txt_login_name.setText(name);
			if(!TextUtils.isEmpty(SpUtils.getString(this, UserCache.KEY_USER_PW))){
				txt_login_pw.setText(SpUtils.getString(this, UserCache.KEY_USER_PW));
			}
		}
	}
	
	public void onFindPWClick(View v){
		Intent intent =new Intent(this,FindPWActivity.class);
		startActivity(intent);
	}
	
	public void onLoginClick(View v){
		if(checkValue()){
			ParaFromPda para= new ParaFromPda();
			para.setLoginName(txt_login_name.getText().toString().trim());
			para.setLoginPsw(txt_login_pw.getText().toString().trim());
			new LoginTask(this).execute(para);
		}
	}
	
	public void onRegisterClick(View v){
		Intent intent =new Intent(this,RegisterActivity.class);
		startActivity(intent);
	}

	private boolean checkValue(){
		StringBuffer sb = new StringBuffer();
		if(TextUtils.isEmpty(txt_login_name.getText().toString().trim())){
			sb.append("请填写登录账号！");
			sb.append("\r\n");
		}
		
		if(TextUtils.isEmpty(txt_login_pw.getText().toString().trim())){
			sb.append("请填写登录密码！");
			sb.append("\r\n");
		}
		
		if(!TextUtils.isEmpty(sb.toString())){
			MsgBox.show(this, sb.toString());
			return false;
		}
		return true;
	}

	public void saveAccount(){
		SpUtils.putString(this, UserCache.KEY_USER_NAME, txt_login_name.getText().toString().trim());
		SpUtils.putString(this, UserCache.KEY_USER_PW, txt_login_pw.getText().toString().trim());
	}
	
}

