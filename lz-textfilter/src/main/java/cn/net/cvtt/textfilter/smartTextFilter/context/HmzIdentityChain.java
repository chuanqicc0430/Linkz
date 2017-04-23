package cn.net.cvtt.textfilter.smartTextFilter.context;

import java.util.ArrayList;
import java.util.List;

import cn.net.cvtt.textfilter.FilterModeEnum;
import cn.net.cvtt.textfilter.smartTextFilter.tree.HmzIdentity;

public class HmzIdentityChain {
	
	private List<HmzIdentity> chain;

    public Object getIdentity()
    {
         return chain.size() > 0 ? chain.get(0).getIdentity() : null; 
    }

    public FilterModeEnum getMode()
    {
        return chain.size() > 0 ? chain.get(0).getMode() : FilterModeEnum.None; 
    }

    public boolean getBlock()
    {
        return chain.size() > 0 ? chain.get(0).getBlock() : false;
    }

//    public boolean getLog()
//    {
//        return chain.size() > 0 ? chain.get(0).getLog() : false; 
//    }

    public byte getTreatment()
    {
        return chain.size() > 0 ? chain.get(0).getTreatment() : Byte.MIN_VALUE; 
    }
    
    public boolean getEnabled() {
    	return chain.size() > 0 ? chain.get(0).getEnabled() :false;
    }

    public HmzIdentityChain()
    {
        chain = new ArrayList<HmzIdentity>();
    }

    public HmzIdentityChain(HmzIdentity id)
    {
        chain = new ArrayList<HmzIdentity>(1);
        chain.add(id);
    }

    public int getCount()
    {
         return chain.size(); 
    }

    public HmzIdentity get(int i)
    {
         return chain.get(i);
    }

    public void add(HmzIdentity identity)
    {
        chain.add(identity);
    }

}
