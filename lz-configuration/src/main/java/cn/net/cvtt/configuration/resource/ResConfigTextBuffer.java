package cn.net.cvtt.configuration.resource;

import java.util.Date;
import java.util.List;

/**
 * 配置文本
 * 
 * @author 
 */
public class ResConfigTextBuffer
{
	private String text;

	private List<String> configParams;

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
