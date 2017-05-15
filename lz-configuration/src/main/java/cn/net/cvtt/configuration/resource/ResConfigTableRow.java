package cn.net.cvtt.configuration.resource;

import java.util.List;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;

public class ResConfigTableRow 
{
	private List<String> values;

	public List<String> getValues()
	{
		return values;
	}

	public void setValues(List<String> values)
	{
		this.values = values;
	}

	public String getValue(int i)
	{
		return values.get(i);
	}
}
