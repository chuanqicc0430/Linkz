package cn.net.cvtt.textfilter.smartTextFilter.context;

import java.util.ArrayList;
import java.util.List;

import cn.net.cvtt.textfilter.smartTextFilter.tree.HmzNode;

public class HmzNodeChain {
	
	private List<HmzNode> chain = new ArrayList<HmzNode>();

    private Object state;
    
    public Object getState()
    {
        return state;
    }

    public HmzNodeChain(Object state)
    {
        this.state = state;
    }

    public int getCount()
    {
        return chain.size();
    }

    public HmzNode get(int i)
    {
        return chain.get(i);
    }

    public void add(HmzNode node)
    {
        chain.add(node);
    }

    public void addRange(HmzNodeChain nodeChain)
    {
        if (nodeChain != null)
            chain.addAll(nodeChain.chain); 
    }
}
