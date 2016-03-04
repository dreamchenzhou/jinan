package topevery.um.net.newbean;

import java.io.File;

import topevery.um.com.data.CaseAccept;
import topevery.um.com.data.CaseType;

public class EvtRes extends BaseRes {
	public CaseType caseType;
	
	public CaseAccept caseAccept;
	
	/// <summary>
    /// 上报成功，返回受理号
    /// </summary>
	public String EvtCode = "";

	public EvtParaForIos EvtPara = null;
    
	public FlowInfoCollection FlowInfos = new FlowInfoCollection();

	public File file = null;
	
	public String datetime = "";
	
	public CaseType getCaseType() {
		return caseType;
	}

	public void setCaseType(CaseType caseType) {
		this.caseType = caseType;
	}

	public CaseAccept getCaseAccept() {
		return caseAccept;
	}

	public void setCaseAccept(CaseAccept caseAccept) {
		this.caseAccept = caseAccept;
	}

	public String getEvtCode() {
		return EvtCode;
	}

	public void setEvtCode(String evtCode) {
		EvtCode = evtCode;
	}

	public EvtParaForIos getEvtPara() {
		return EvtPara;
	}

	public void setEvtPara(EvtParaForIos evtPara) {
		EvtPara = evtPara;
	}

	public FlowInfoCollection getFlowInfos() {
		return FlowInfos;
	}

	public void setFlowInfos(FlowInfoCollection flowInfos) {
		FlowInfos = flowInfos;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	
}
