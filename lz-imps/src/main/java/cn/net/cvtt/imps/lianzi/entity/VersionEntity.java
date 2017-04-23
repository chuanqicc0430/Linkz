package cn.net.cvtt.imps.lianzi.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class VersionEntity implements Serializable {
	private String versionId;
	private int versionType;
	private long versionValue;

	public String getVersionId() {
		return versionId;
	}

	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}

	public int getVersionType() {
		return versionType;
	}

	public void setVersionType(int versionType) {
		this.versionType = versionType;
	}

	public long getVersionValue() {
		return versionValue;
	}

	public void setVersionValue(long versionValue) {
		this.versionValue = versionValue;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VersionResult [versionId=");
		builder.append(versionId);
		builder.append(", versionType=");
		builder.append(versionType);
		builder.append(", versionValue=");
		builder.append(versionValue);
		builder.append("]");
		return builder.toString();
	}

}
