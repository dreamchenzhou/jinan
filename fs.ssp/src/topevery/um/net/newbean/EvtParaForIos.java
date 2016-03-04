package topevery.um.net.newbean;

import java.io.Serializable;

public class EvtParaForIos extends EvtPara implements Serializable {
	private AttachInfoCollection Attachs;

	public AttachInfoCollection getAttachs() {
		return Attachs;
	}

	public void setAttachs(AttachInfoCollection attachs) {
		Attachs = attachs;
	}
	
}
