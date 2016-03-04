package topevery.um.net;

public class UmUdpServicePacketType
{
	/**
	 * 握手
	 */
	public static final int Hands = -1;
	/**
	 * 业务
	 */
	public static final int Bus = 0;
	/**
	 * 服务
	 */
	public static final int Srv = 1;
	/**
	 * 文件上传
	 */
	public static final int Up = 2;
	/**
	 * 升级，锄大地
	 */
	public static final int UpSys = 3;

	/**
	 * 业务类型
	 */
	public class Business
	{
		/**
		 * GPS数据上传
		 */
		public static final int GPS = 0;
		/**
		 * GPRS数据上传
		 */
		public static final int GPRS = 1;
		/**
		 * 系统消息，由服务端发往客户端
		 */
		public static final int ClientMessage = 2;
		/**
		 * 退出登录
		 */
		public static final int LoginOut = 3;
		/**
		 * 服务器时间同步
		 */
		public static final int ServerTime = 4;
		/**
		 * 案件作废消息
		 */
		public static final int UmEvtDelMessage = 5;
		/**
		 * 核实超时消息
		 */
		public static final int OvertimeHeShi = 6;
		/**
		 * 核查超时消息
		 */
		public static final int OvertimeHeCha = 7;
		/**
		 * 迟到消息
		 */
		public static final int LateMessage = 8;
		/**
		 * 早退消息
		 */
		public static final int EarlyMessage = 9;
		/**
		 * 长时间不动消息
		 */
		public static final int StayMessage = 10;
		/**
		 * GPS没信号警告消息
		 */
		public static final int GPSNotWorkMessage = 11;
		/**
		 * 点名、抽查消息
		 */
		public static final int CheckMessage = 12;
		/**
		 * 业务短消息
		 */
		public static final int BusinessMessage = 13;
		/**
		 * 关键点签到消息
		 */
		public static final int CheckPointInMessage = 14;
		/**
		 * 这个关键点该到，但是还没到的提醒消息或者报警消息
		 */
		public static final int CheckPointNotInMessage = 15;
		/**
		 * 超出管理网格
		 */
		public static final int OverGridMessage = 16;
		/**
		 * 未知
		 */
		public static final int Unkown = -1;

		public static final int TaskNumber = 17;
	}

	/**
	 * 服务
	 * 
	 * @author martin.zheng
	 * 
	 */
	public class Service
	{
		/**
		 * 登录
		 */
		public static final int ReportEvtInfo = 0;
		/**
		 * 案件查询(根据受理号)
		 */
		public static final int GetEvtInfo = 1;

		/**
		 * 自动地址列表
		 */
		public static final int GetAutoPos = 2;
	}

	/**
	 * 文件上传
	 * 
	 * @author martin.zheng
	 * 
	 */
	public class Upload
	{
		/**
		 * 上传数据
		 */
		public static final int UploadCore = 0;

		/**
		 * 上传完成
		 */
		public static final int UploadFinish = 1;

		public static final int UploadStart = 2;

		/**
		 * 上传状态
		 */
		public static final int UploadState = 3;
	}

	public class Update
	{
		/**
		 * 根据客户端版本号，获取服务器端更新列表
		 */
		public static final int GetServerVersion = 0;
	}
}
