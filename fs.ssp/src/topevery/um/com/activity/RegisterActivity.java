package topevery.um.com.activity;

import global.BaseActivity;
import topevery.android.core.MsgBox;
import topevery.um.com.inject.InjectUtility;
import topevery.um.com.inject.ViewInject;
import topevery.um.com.task.RegistTask;
import topevery.um.com.utils.ActivityUtils;
import topevery.um.jinan.manager.R;
import topevery.um.net.newbean.UserResInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends BaseActivity {
	@ViewInject(idStr = "txt_register_name", click = "")
	private EditText txt_name;

	@ViewInject(idStr = "txt_register_phone", click = "")
	private EditText txt_phone;
	
	@ViewInject(idStr = "txt_register_pw", click = "")
	private EditText txt_pw;

	@ViewInject(idStr = "txt_register_confirm_pw", click = "")
	private EditText txt_confirm_pw;

	@ViewInject(idStr = "btn_register", click = "onRegisterClick")
	private Button btn_register;

	@ViewInject(idStr = "btn_clear", click = "onClearClick")
	private Button btn_clear;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		InjectUtility.initInjectedView(this);
	}

	public void onClearClick(View v) {
		txt_name.setText("");
	}

	public void onRegisterClick(View v) {
		if(checkValue()){
			UserResInfo info = new UserResInfo();
			info.loginName = txt_name.getText().toString();
			info.loginPsw =txt_confirm_pw.getText().toString();
			info.phoneNo = txt_phone.getText().toString().trim();
			info.userName = info.loginName;
			new RegistTask(this).execute(info);
		}
	}
	
	private boolean checkValue(){
		StringBuffer sb = new StringBuffer();
		if(TextUtils.isEmpty(txt_name.getText().toString().trim())){
			sb.append("请填写用户名！");
			sb.append("\r\n");
		}
		
		if(TextUtils.isEmpty(txt_phone.getText().toString().trim())){
			sb.append("请填写电话号码！");
			sb.append("\r\n");
		}else if(ActivityUtils.isMobileNO(txt_phone.getText().toString().trim())){
			sb.append("请填写正确的电话号码！");
			sb.append("\r\n");
		}
		
		if(TextUtils.isEmpty(txt_pw.getText().toString().trim())){
			sb.append("请填写密码！");
			sb.append("\r\n");
		}
		
		if(TextUtils.isEmpty(txt_confirm_pw.getText().toString().trim())){
			sb.append("请再次填写密码！");
			sb.append("\r\n");
		}
		
		if(!txt_pw.getText().toString().trim().equals(txt_confirm_pw.getText().toString().trim())){
			sb.append("两次密码不一致！");
			sb.append("\r\n");
		}
		
		if(!TextUtils.isEmpty(sb.toString())){
			MsgBox.show(this, sb.toString());
			return false;
		}
		return true;
	}
}
