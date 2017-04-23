package cn.net.cvtt.imps.lianzi.config.tables;

import cn.net.cvtt.configuration.ConfigTableField;
import cn.net.cvtt.configuration.ConfigTableItem;

public class CFG_UpDownIndustry extends ConfigTableItem {

	@ConfigTableField( "UpStreamID")
	private String upStreamID;
	
	@ConfigTableField("DownStreamID")
	private String downStreamID;

	@ConfigTableField("IndustryLevel")
	private int industryLevel;

	@ConfigTableField(value ="ID" , isKeyField = true )
	private int id;
	
	public int getID() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}
	public String getUpStreamID() {
		return upStreamID;
	}
	public void setUpStreamID(String upStreamID) {
		this.upStreamID = upStreamID;
	}
	public String getDownStreamID() {
		return downStreamID;
	}
	public void setDownStreamID(String downStreamID) {
		this.downStreamID = downStreamID;
	}
	public int getIndustryLevel() {
		return industryLevel;
	}
	public void setIndustryLevel(int industryLevel) {
		this.industryLevel = industryLevel;
	}
	@Override
	public String toString() {
		return String.format("CFG_UpDownIndustry [upStreamID=%s, downStreamID=%s, industryLevel=%s]", upStreamID, downStreamID, industryLevel);
	}

}
