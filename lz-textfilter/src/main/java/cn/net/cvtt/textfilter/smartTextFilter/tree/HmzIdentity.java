package cn.net.cvtt.textfilter.smartTextFilter.tree;

import cn.net.cvtt.lian.common.util.Flags;
import cn.net.cvtt.textfilter.FilterModeEnum;
import cn.net.cvtt.textfilter.FilterTypeEnum;
import cn.net.cvtt.textfilter.HarmonizedWords;

public class HmzIdentity {

    private HarmonizedWords identity;

    private String target;
    
    private boolean isPrimary;
    
    private int match;

    
    public Object getIdentity()
    {
         return identity; 
    }
    public String getTarget() {
		return target;
	}
    
	public  Flags<FilterTypeEnum> getType()
    {
        return identity.getFilterType(); 
    }

    public FilterModeEnum getMode()
    {
         return identity.getFilterMode(); 
    }

    public  byte getTreatment()
    {
         return identity.getTreatment();
    }
    
    public boolean isPrimary() {
		return isPrimary;
	}
    
	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}
	
	public int getMatch() {
		return match;
	}
	
	public void setMatch(int match) {
		this.match = match;
	}
    public boolean getBlock(){
    	return identity.getTreatment() < 40;
    }

//    public boolean getLog()
//    {
//        return identity.isNeedLog(); 
//    }
    
    public boolean getEnabled() {
    	return identity.isEnabled();
    }

    public HmzIdentity(HarmonizedWords identity, String target, boolean isPrimary, int count)
    {
        this.identity = identity;
        this.target = target;
        this.isPrimary = isPrimary;
        match = count;
    }
}
