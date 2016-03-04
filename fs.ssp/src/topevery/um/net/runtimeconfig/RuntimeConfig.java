package topevery.um.net.runtimeconfig;

import ro.upload.UploadCorePara;
import ro.upload.UploadFinishPara;
import ro.upload.UploadFinishRes;
import ro.upload.UploadPara;
import ro.upload.UploadStartPara;
import ro.upload.UploadStartRes;
import ro.upload.UploadStatePara;
import ro.upload.UploadStateRes;
import topevery.framework.runtime.serialization.RuntimeConfigClassResolver;
import topevery.um.net.BaseInPara;
import topevery.um.net.BaseOutRes;
import topevery.um.net.NameValue;
import topevery.um.net.NameValueCollection;
import topevery.um.net.UmUdpServiceHandsPara;
import topevery.um.net.bus.BusinessPara;
import topevery.um.net.bus.GPSPoint;
import topevery.um.net.bus.GPSPointCollection;
import topevery.um.net.bus.Message;
import topevery.um.net.bus.MessageCollection;
import topevery.um.net.srv.AttachInfo;
import topevery.um.net.srv.AttachInfoCollection;
import topevery.um.net.srv.EvtPara;
import topevery.um.net.srv.EvtRes;
import topevery.um.net.srv.FlowInfo;
import topevery.um.net.srv.FlowInfoCollection;
import topevery.um.net.srv.GetAutoPosPara;
import topevery.um.net.srv.GetAutoPosRes;
import topevery.um.net.srv.ServicePara;
import topevery.um.net.update.GetUpdatePara;
import topevery.um.net.update.GetUpdateRes;
import topevery.um.net.update.UpdateCollection;
import topevery.um.net.update.UpdateItem;
import topevery.um.net.update.UpdatePara;

public class RuntimeConfig
{
	public static void registeRemoteClassAlias()
	{
		RuntimeConfigClassResolver.registeRemoteClassAlias(ServicePara.class);

		RuntimeConfigClassResolver.registeRemoteClassAlias(EvtPara.class);
		RuntimeConfigClassResolver.registeRemoteClassAlias(EvtRes.class);

		RuntimeConfigClassResolver.registeRemoteClassAlias(BaseInPara.class);
		RuntimeConfigClassResolver.registeRemoteClassAlias(BaseOutRes.class);
		RuntimeConfigClassResolver.registeRemoteClassAlias(NameValue.class);
		RuntimeConfigClassResolver.registeRemoteClassAlias(NameValueCollection.class);
		RuntimeConfigClassResolver.registeRemoteClassAlias(UmUdpServiceHandsPara.class);
		RuntimeConfigClassResolver.registeRemoteClassAlias(BusinessPara.class);

		RuntimeConfigClassResolver.registeRemoteClassAlias(GPSPoint.class);
		RuntimeConfigClassResolver.registeRemoteClassAlias(GPSPointCollection.class);

		RuntimeConfigClassResolver.registeRemoteClassAlias(Message.class);
		RuntimeConfigClassResolver.registeRemoteClassAlias(MessageCollection.class);

		RuntimeConfigClassResolver.registeRemoteClassAlias(AttachInfo.class);
		RuntimeConfigClassResolver.registeRemoteClassAlias(AttachInfoCollection.class);

		RuntimeConfigClassResolver.registeRemoteClassAlias(UpdateItem.class);
		RuntimeConfigClassResolver.registeRemoteClassAlias(UpdateCollection.class);
		RuntimeConfigClassResolver.registeRemoteClassAlias(GetUpdatePara.class);
		RuntimeConfigClassResolver.registeRemoteClassAlias(GetUpdateRes.class);
		RuntimeConfigClassResolver.registeRemoteClassAlias(UpdatePara.class);

		RuntimeConfigClassResolver.registeRemoteClassAlias(GetAutoPosPara.class);
		RuntimeConfigClassResolver.registeRemoteClassAlias(GetAutoPosRes.class);

		RuntimeConfigClassResolver.registeRemoteClassAlias(FlowInfoCollection.class);
		RuntimeConfigClassResolver.registeRemoteClassAlias(FlowInfo.class);

		RuntimeConfigClassResolver.registeRemoteClassAlias(UploadCorePara.class);
		RuntimeConfigClassResolver.registeRemoteClassAlias(UploadFinishPara.class);
		RuntimeConfigClassResolver.registeRemoteClassAlias(UploadFinishRes.class);
		RuntimeConfigClassResolver.registeRemoteClassAlias(UploadPara.class);
		RuntimeConfigClassResolver.registeRemoteClassAlias(UploadStartPara.class);
		RuntimeConfigClassResolver.registeRemoteClassAlias(UploadStartRes.class);
		RuntimeConfigClassResolver.registeRemoteClassAlias(UploadStatePara.class);
		RuntimeConfigClassResolver.registeRemoteClassAlias(UploadStateRes.class);
	}
}
