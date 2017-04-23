package cn.net.cvtt.textfilter.smartTextFilter.context;

import java.util.ArrayList;
import java.util.List;

import cn.net.cvtt.textfilter.FilterTypeEnum;

public class FilterContext {

    private HmzContext[] context;

    private List<HmzIdentityChain> hit;
    
    public List<HmzIdentityChain> getHit()
    {
        return hit; 
    }

    public FilterContext( HmzContext[] context)
    {
        this.context = context;
        hit = new ArrayList<HmzIdentityChain>();
    }

    public boolean next(char c, FilterTypeEnum type, char next)
    {
        boolean boolHit = false;
        for (int i = 0; i < context.length; ++i)
        {
            if (context[i].checkNext(c, type, next))
            {
            	boolHit = true;
                hit.addAll(context[i].getHit());
                context[i].getHit().clear();
            }
        }
        return boolHit;
    }

    public boolean complete(FilterTypeEnum type)
    {
        boolean boolHit = false;
        for (int i = 0; i < context.length; ++i)
        {
            if (context[i].checkEnd(type))
            {
            	boolHit = true;
                hit.addAll(context[i].getHit());
                context[i].getHit().clear();
            }
        }
        return boolHit;
    }
}
