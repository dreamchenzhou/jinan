package topevery.um.net.srv;

import topevery.framework.runtime.serialization.IBinarySerializable;
import topevery.framework.runtime.serialization.IObjectBinaryReader;
import topevery.framework.runtime.serialization.IObjectBinaryWriter;
import topevery.framework.runtime.serialization.RemoteClassAlias;

@SuppressWarnings("serial")
@RemoteClassAlias({ "Topevery.DUM.SocketShiMin.Srv.FlowInfo" })
public class FlowInfo implements IBinarySerializable
{
	public String activityName = "";// 环节名
	public String operatorName = "";// 经办单位（无需人员姓名）
	public String inDate = "";// 来件时间
	public String finishedDate = "";// 办结时间
	public String actUsedTime = "";// 环节用时
	public String limitTime = "";// 办理时限
	public String caseUsedDate = "";// 累计用时
	public String opinion = "";// 处理意见
	public String evtCode;

	@Override
	public void readObjectData(IObjectBinaryReader reader)
	{
		activityName = reader.readUTF();
		operatorName = reader.readUTF();
		inDate = reader.readUTF();
		finishedDate = reader.readUTF();
		actUsedTime = reader.readUTF();
		limitTime = reader.readUTF();
		caseUsedDate = reader.readUTF();
		opinion = reader.readUTF();
	}

	@Override
	public void writeObjectData(IObjectBinaryWriter writer)
	{
		writer.writeUTF(activityName);
		writer.writeUTF(operatorName);
		writer.writeUTF(inDate);
		writer.writeUTF(finishedDate);
		writer.writeUTF(actUsedTime);
		writer.writeUTF(limitTime);
		writer.writeUTF(caseUsedDate);
		writer.writeUTF(opinion);
	}

}
