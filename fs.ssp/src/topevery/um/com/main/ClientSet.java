package topevery.um.com.main;

import topevery.android.core.MsgBox;
import topevery.android.framework.utils.TextUtils;
import topevery.um.com.Settings;
import topevery.um.net.UmUdpService;
import topevery.um.jinan.manager.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

/**
 * 设置IP、端口
 * 
 * @author martin.zheng
 * 
 */
public class ClientSet extends Activity
{
	ClientSet wThis = this;

	EditText txtIP, txtPort, txtDh;
	Button btnSave;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		{
			requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
			setContentView(R.layout.ui_set);
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
			initContent();
		}
	}

	private void initContent()
	{
		txtDh = (EditText) this.findViewById(R.id.txtDh);
		txtIP = (EditText) this.findViewById(R.id.txtIP);
		txtPort = (EditText) this.findViewById(R.id.txtPort);

		btnSave = (Button) this.findViewById(R.id.btnSave);
		btnSave.setText("保存");
		btnSave.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					btnSure();
				}
			});

		String telNum = Settings.getInstance().TelNum;
		String ip = Settings.getInstance().UdpIp;
		int port = Settings.getInstance().UdpPort;

		txtIP.setText(ip);
		txtPort.setText(String.valueOf(port));
		txtDh.setText(telNum);

		txtIP.setSelection(txtIP.getText().length());
		txtPort.setSelection(txtPort.getText().length());

		if (!TextUtils.isEmpty(telNum))
		{
			txtDh.setSelection(telNum.length());
		}
	}

	private void btnSure()
	{
		try
		{
			if (TextUtils.isEmpty(txtDh))
			{
				MsgBox.show(wThis, "电话不能为空");
				return;
			}

			if (TextUtils.isEmpty(txtIP) || TextUtils.isEmpty(txtPort))
			{
				MsgBox.show(wThis, "IP地址或者端口不能为空");
				return;
			}

			Settings.getInstance().TelNum = txtDh.getText().toString().trim();
			Settings.getInstance().UdpIp = txtIP.getText().toString().trim();
			Settings.getInstance().UdpPort = Integer.parseInt(txtPort.getText().toString().trim());
			Settings.getInstance().writeData();
			UmUdpService.initFinal();
			finish();
		}
		catch (Exception e)
		{
			MsgBox.show(wThis, e.getMessage());
		}
	}
}