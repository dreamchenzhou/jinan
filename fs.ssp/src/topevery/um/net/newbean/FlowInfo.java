package topevery.um.net.newbean;

import java.io.Serializable;

public class FlowInfo implements Serializable {
	private String activityName = "";//环节名
    private String operatorName = "";//经办单位（无需人员姓名）
    private String inDate = "";//来件时间
    private String finishedDate = "";//办结时间
    private String actUsedTime = "";//环节用时
    private String limitTime = "";//办理时限
    private String caseUsedDate = "";//累计用时
    private String opinion = ""; // 处理意见
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public String getInDate() {
		return inDate;
	}
	public void setInDate(String inDate) {
		this.inDate = inDate;
	}
	public String getFinishedDate() {
		return finishedDate;
	}
	public void setFinishedDate(String finishedDate) {
		this.finishedDate = finishedDate;
	}
	public String getActUsedTime() {
		return actUsedTime;
	}
	public void setActUsedTime(String actUsedTime) {
		this.actUsedTime = actUsedTime;
	}
	public String getLimitTime() {
		return limitTime;
	}
	public void setLimitTime(String limitTime) {
		this.limitTime = limitTime;
	}
	public String getCaseUsedDate() {
		return caseUsedDate;
	}
	public void setCaseUsedDate(String caseUsedDate) {
		this.caseUsedDate = caseUsedDate;
	}
	public String getOpinion() {
		return opinion;
	}
	public void setOpinion(String opinion) {
		this.opinion = opinion;
	}
}
