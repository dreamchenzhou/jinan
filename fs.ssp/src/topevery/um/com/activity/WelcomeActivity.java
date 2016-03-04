package topevery.um.com.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import topevery.um.com.view.CircleImageView;
import topevery.um.jinan.manager.R;

public class WelcomeActivity extends Activity {
	private CircleImageView img_head;
	
	private TextView txt_login_name;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);
		img_head = (CircleImageView) findViewById(R.id.img_head_icon);
		txt_login_name = (TextView) findViewById(R.id.txt_login_name);
		img_head.setAnimation(AnimationUtils.loadAnimation(this, R.anim.breath_img));
		img_head.postDelayed(new IntentRunnable(), 3*1000);
	}
	
	class IntentRunnable implements Runnable{
		@Override
		public void run() {
			Intent  intent =new Intent(WelcomeActivity.this,MainActivity.class);
			startActivity(intent);
			finish();
		}
	}
}
