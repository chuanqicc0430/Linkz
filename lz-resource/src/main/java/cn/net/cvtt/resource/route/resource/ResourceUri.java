package cn.net.cvtt.resource.route.resource;

/**
 * {在这里补充类的功能说明}
 * 
 * @author
 */
public class ResourceUri {
	private String name;

	private String type;

	private Integer index;

	private String config;

	public ResourceUri(String name, String type, int index, String config) {
		this.name = name;
		this.type = type;
		this.index = index;
		this.config = config;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the index
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

	/**
	 * @return the config
	 */
	public String getConfig() {
		return config;
	}

	/**
	 * @param config
	 *            the config to set
	 */
	public void setConfig(String config) {
		this.config = config;
	}

}
