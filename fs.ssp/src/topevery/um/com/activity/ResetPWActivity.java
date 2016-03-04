package topevery.um.com.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import topevery.android.core.MsgBox;
import topevery.um.com.base.BaseActivity;
import topevery.um.com.inject.InjectUtility;
import topevery.um.com.inject.ViewInject;
import topevery.um.com.task.ResetPWTask;
import topevery.um.com.utils.SpUtils;
import topevery.um.jinan.manager.R;
import topevery.um.net.newbean.ParaFromPda;
import topevery.um.net.newbean.UserCache;

public class ResetPWActivity extends BaseActivity implements OnClickListener {
	@ViewInject(idStr = "txt_old_pw", click = "")
	private EditText txt_old_pw;

	@ViewInject(idStr = "txt_new_pw", click = "")
	private EditText txt_new_pw;

	@ViewInject(idStr = "txt_confirm_pw", click = "")
	private EditText txt_confirm_pw;

	@ViewInject(idStr = "btn_reset_pw", click = "onResetClick")
	private Button btn_reset_pw;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_pw);
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
		title.setText(getString(R.string.title_reset_pw));
		mAbTitleBar.addView(view, 0);
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

	public void onResetClick(View v) {
		if(checkValue()){
			ParaFromPda para = new ParaFromPda();
			para.setLoginName(SpUtils.getString(this, UserCache.KEY_USER_NAME));
			para.setLoginPsw(txt_old_pw.getText().toString().trim());
			para.setNewPswToRest(txt_confirm_pw.getText().toString().trim());
			new ResetPWTask(this).execute(para);
		}
	}

	private boolean checkValue() {
		StringBuffer sb = new StringBuffer();
		if (TextUtils.isEmpty(txt_old_pw.getText().toString().trim())) {
			sb.append("请填写原始密码！");
			sb.append("\r\n");
		}

		if (TextUtils.isEmpty(txt_new_pw.getText().toString().trim())) {
			sb.append("请填写新密码！");
			sb.append("\r\n");
		} else {

			if (TextUtils.isEmpty(txt_confirm_pw.getText().toString().trim())) {
				sb.append("请再次填写新密码！");
				sb.append("\r\n");
			} else {
				if (!txt_confirm_pw.getText().toString().trim()
						.equals(txt_new_pw.getText().toString().trim())) {
					sb.append("两次密码不一致！");
					sb.append("\r\n");
				}
			}
		}

		if (!TextUtils.isEmpty(sb.toString())) {
			MsgBox.show(this, sb.toString());
			return false;
		}
		return true;
	}
}
