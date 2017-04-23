package cn.net.cvtt.configuration.resource;

import java.util.Date;
import java.util.List;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.ProtoMember;

/**
 * 配置文本
 * 
 * @author 
 */
public class ResConfigTextBuffer extends ProtoEntity
{
	@ProtoMember(1)
	private String text;

	@ProtoMember(2)
	private List<String> configParams;

	@ProtoMember(3)
	private Date version;

	public ResConfigTextBuffer()
	{
	}

	public ResConfigTextBuffer(String text)
	{
		this.text = text;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public void setConfigParams(List<String> configParams)
	{
		this.configParams = configParams;
	}

	public List<String> getConfigParams()
	{
		return configParams;
	}

	public Date getVersion() {
		return version;
	}

	public void setVersion(Date version) {
		this.version = version;
	}
	
}
