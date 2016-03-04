package topevery.um.com.multipic;

import topevery.um.com.multipic.interfaces.SwitchInterface;

/**
 * 
 * 图片解析开关控制代理类
 */
public class DecoderSwitch implements SwitchInterface {

	public  int state = STATE_ON;
	
	@Override
	public void switcher() {
		if(state==STATE_OFF){
			state = STATE_ON;
		}else{
			state = STATE_OFF;
		}
	}

	@Override
	public boolean isOff() {
		return state==STATE_OFF? true: false;
	}

	@Override
	public void on() {
		state = STATE_ON;
	}

	@Override
	public void off() {
		state = STATE_OFF;
	}

}
