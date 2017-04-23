package cn.net.cvtt.resource.route.resource;

/**
 * 用于进行资源定位的表<br>
 * <table>
 * <tr>
 * 	<td>type</td>
	<td>name</td>
	<td>protocol</td>
	<td>locator</td>
	<td>params</td>
 * </tr>
 * <tr>
 * 	<td>database</td>
	<td>IICUPDB</td>
	<td>id</td>
	<td>IdPoolLocator</td>
	<td>CFG_LogicalPool</td>
 * </tr>
 * </table>
 * 
 * @author 
 */
public class ResourcePolicy
{
	private String name;
	
	private ResourceType type;
	
	private String protocol;
	
	private String locator;
	
	private String locatorParams;
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the protocol
	 */
	public String getProtocol() {
		return protocol;
	}

	/**
	 * @param protocol the protocol to set
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	/**
	 * @return the locator
	 */
	public String getLocator() {
		return locator;
	}

	/**
	 * @param locator the locator to set
	 */
	public void setLocator(String locator) {
		this.locator = locator;
	}

	/**
	 * @return the locatorParams
	 */
	public String getLocatorParams() {
		return locatorParams;
	}

	/**
	 * @param locatorParams the locatorParams to set
	 */
	public void setLocatorParams(String locatorParams) {
		this.locatorParams = locatorParams;
	}

	public ResourceType getType()
	{
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(ResourceType type) {
		this.type = type;
	}
}

