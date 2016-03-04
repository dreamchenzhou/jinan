package topevery.um.net.newbean;

import android.R.integer;

public class EvtPara extends BasePara {
	public String Linkman = "";//联系人
    public String LinkPhone = "";//联系电话
    public String EvtPos = "";//案发地址
    public String EvtDesc = "";//案发描述
    public boolean IsNeedReply;
    public String ReplyWay;
    public int ReplyWayId;
    public double AbsX;
    public double AbsY;
    public double GeoX;
    public double GeoY;
    public String EvtResult = "";//处理结果
    /// <summary>
    /// 上报成功，返回受理号
    /// </summary>
    public String EvtCode;

    /// <summary>
    /// 0:上报，1列表，2详细
    /// </summary>
    public int HandleType = 0;

    public int pageIndex;
    
    public int pageSize;
    
	public String getLinkman() {
		return Linkman;
	}

	public void setLinkman(String linkman) {
		Linkman = linkman;
	}

	public String getLinkPhone() {
		return LinkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		LinkPhone = linkPhone;
	}

	public String getEvtPos() {
		return EvtPos;
	}

	public void setEvtPos(String evtPos) {
		EvtPos = evtPos;
	}

	public String getEvtDesc() {
		return EvtDesc;
	}

	public void setEvtDesc(String evtDesc) {
		EvtDesc = evtDesc;
	}

	public boolean isIsNeedReply() {
		return IsNeedReply;
	}

	public void setIsNeedReply(boolean isNeedReply) {
		IsNeedReply = isNeedReply;
	}

	public String getReplyWay() {
		return ReplyWay;
	}

	public void setReplyWay(String replyWay) {
		ReplyWay = replyWay;
	}

	public int getReplyWayId() {
		return ReplyWayId;
	}

	public void setReplyWayId(int replyWayId) {
		ReplyWayId = replyWayId;
	}

	public double getAbsX() {
		return AbsX;
	}

	public void setAbsX(double absX) {
		AbsX = absX;
	}

	public double getAbsY() {
		return AbsY;
	}

	public void setAbsY(double absY) {
		AbsY = absY;
	}

	public double getGeoX() {
		return GeoX;
	}

	public void setGeoX(double geoX) {
		GeoX = geoX;
	}

	public double getGeoY() {
		return GeoY;
	}

	public void setGeoY(double geoY) {
		GeoY = geoY;
	}

	public String getEvtResult() {
		return EvtResult;
	}

	public void setEvtResult(String evtResult) {
		EvtResult = evtResult;
	}

	public String getEvtCode() {
		return EvtCode;
	}

	public void setEvtCode(String evtCode) {
		EvtCode = evtCode;
	}

	public int getHandleType() {
		return HandleType;
	}

	public void setHandleType(int handleType) {
		HandleType = handleType;
	}
    
}
