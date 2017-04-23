package cn.net.cvtt.textfilter.smartTextFilter.tree;

import cn.net.cvtt.lian.common.util.Flags;
import cn.net.cvtt.lian.common.util.Guid;
import cn.net.cvtt.textfilter.FilterModeEnum;
import cn.net.cvtt.textfilter.FilterTypeEnum;

public class HmzIdentityDigest extends HmzIdentity {

	public HmzIdentityDigest(Guid digest) {
		super(null, digest.toStr().replace("-", ""), true, 1);
	}

	public Object getIdentity() {
		return this;
	}

	public boolean getBlock() {
		return true;
	}

	public boolean getLog() {
		return true;
	}

	public FilterModeEnum getMode() {
		return FilterModeEnum.None;
	}

	public Flags<FilterTypeEnum> getType() {
		return new Flags<FilterTypeEnum>(FilterTypeEnum.DialogMessage.intValue() | FilterTypeEnum.GroupMessage.intValue());
	}

}
