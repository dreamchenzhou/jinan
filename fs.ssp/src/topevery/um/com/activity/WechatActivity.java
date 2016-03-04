package topevery.um.com.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import topevery.um.com.base.BaseActivity;
import topevery.um.jinan.manager.R;

public class WechatActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wechat);
	}
}
