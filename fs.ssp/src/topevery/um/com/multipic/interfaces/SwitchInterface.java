package topevery.um.com.multipic.interfaces;

/**
 * 
 * 控制线程开关
 */
public interface SwitchInterface {
	public static final int STATE_ON = 0;
	
	public static final int STATE_OFF =1;
	
	/**
	 * 开关转换
	 */
	public  void  switcher();
	
	/**
	 * 开
	 */
	public void on();
	
	/**
	 *关
	 */
	public void off();
	
	/**
	 * 
	 * @return
	 */
	public boolean isOff();
}
