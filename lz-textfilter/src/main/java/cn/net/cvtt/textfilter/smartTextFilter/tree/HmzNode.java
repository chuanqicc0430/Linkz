package cn.net.cvtt.textfilter.smartTextFilter.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.net.cvtt.lian.common.util.Flags;
import cn.net.cvtt.textfilter.FilterTypeEnum;

public class HmzNode {

	private char zChar = Character.MIN_VALUE;

	private Flags<FilterTypeEnum> type;

	private HashMap<Character, HmzNode> nexts;

	private List<HmzIdentity> identities;

	private byte treatment = Byte.MIN_VALUE;

	public HmzNode(char c, byte treatement) {
		zChar = c;
		type = new Flags<FilterTypeEnum>(FilterTypeEnum.None.intValue());
		identities = new ArrayList<HmzIdentity>();
		nexts = new HashMap<Character, HmzNode>();
		this.treatment = treatement;
	}

	public char getzChar() {
		return zChar;
	}

	public void setzChar(char zChar) {
		this.zChar = zChar;
	}

	public Flags<FilterTypeEnum> getType() {
		return type;
	}

	public void setType(Flags<FilterTypeEnum> type) {
		this.type = type;
	}

	public HashMap<Character, HmzNode> getNexts() {
		return nexts;
	}

	public void setNexts(HashMap<Character, HmzNode> nexts) {
		this.nexts = nexts;
	}

	public List<HmzIdentity> getIdentities() {
		return identities;
	}

	public void setIdentities(List<HmzIdentity> identities) {
		this.identities = identities;
	}

	public byte getTreatment() {
		return treatment;
	}

	public void setTreatment(byte treatment) {
		this.treatment = treatment;
	}

	public void addType(Flags<FilterTypeEnum> type) {
		// this.type = FilterTypeEnum.intConvert(this.type.intValue() | type.intValue());
		this.type = new Flags<FilterTypeEnum>(this.type.intValue() | type.intValue());
	}

}
