package cn.net.cvtt.textfilter.smartTextFilter.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.net.cvtt.textfilter.FilterModeEnum;
import cn.net.cvtt.textfilter.FilterTypeEnum;
import cn.net.cvtt.textfilter.smartTextFilter.tree.HmzIdentity;
import cn.net.cvtt.textfilter.smartTextFilter.tree.HmzNode;

public class HmzContext {

	

    private HmzNode root;

    private HashMap<Character, HmzNodeChain> candy;
    private HashMap<Character, HmzNodeChain> specy;
    private HashMap<Character, HmzNodeChain> fly_c;
    private HashMap<Character, HmzNodeChain> fly_t;
    private HashMap<Object, HmzIdentityChain> match;
    private HashMap<Object, Boolean> history;
    private char first;
    private List<HmzIdentityChain> hit;
    
    public List<HmzIdentityChain> getHit()
    {
        return hit; 
    }

   

    protected HmzContext(HmzNode root)
    {
        this.root = root;
        candy = new HashMap<Character, HmzNodeChain>();
        specy = new HashMap<Character, HmzNodeChain>();
        fly_c = new HashMap<Character, HmzNodeChain>();
        fly_t = new HashMap<Character, HmzNodeChain>();
        match = new HashMap<Object, HmzIdentityChain>();
        hit = new ArrayList<HmzIdentityChain>();
        history = new HashMap<Object, Boolean>();
        first = Character.MIN_VALUE;
    }

    public boolean checkNext(char c, FilterTypeEnum type, char next)
    {
        if (Character.isHighSurrogate(c))
        {
            first = c;
            return false;
        }

        char h = Character.MIN_VALUE;
        char l = Character.MIN_VALUE;
        if (Character.isLowSurrogate(c))
        {
            if (first == Character.MIN_VALUE)
                return false;

            h = first;
            l = c;
        }
        else 
        {
            h = c;
        }
        first = Character.MIN_VALUE;

        if (!ignore(h, l))
            updateContext(h, l, type, next);

        return checkHit(h, l, hit.size() > 0);
    }

    public boolean checkEnd(FilterTypeEnum type)
    {
        first = Character.MIN_VALUE;
        return checkHit(Character.MIN_VALUE, Character.MIN_VALUE, hit.size() > 0);
    }

    protected boolean ignore(char c, char e)
    {
        return false;
    }

    protected boolean checkIn(char c, char e) 
    { 
        return true; 
    }

    protected Object createState(char c, char e) 
    { 
        return null; 
    }

    protected boolean count(char c, char e, Object state)
    {
        return true;
    }

    protected boolean special(char c, char e)
    {
        return false;
    }

    protected void waste(char c, char e)
    {
        // no-op
    }

    // char.MinValue: continue
    // char.MaxValue: stop
    protected boolean checkHit(char c, char e, boolean hit)
    {
        return hit;
    }

    protected void updateContext(char c, char e, FilterTypeEnum type, char next)
    {
        HashMap<Character, HmzNodeChain> candyMap = fly_c;

        updateCandidate(specy, candyMap, c, e, type, next);
        updateCandidate(candy, candyMap, c, e, type, next);

        if (checkIn(c, e))
        {
            HmzNode node = root.getNexts().get(c);
            /*for(Character ct : root.getNexts().keySet()){
            	System.out.println("key : "+ct+"   value : "+root.getNexts().get(ct).getzChar());
            	for(Character key : root.getNexts().get(ct).getNexts().keySet()){
            		System.out.println("sub key : "+key+" sub value : "+root.getNexts().get(ct).getNexts().get(key).getzChar());
            		HmzNode subNode = root.getNexts().get(ct).getNexts().get(key);
            		for(Character skey : subNode.getNexts().keySet())
            			System.out.println("seconde key : "+skey+"  seconde value : "+subNode.getNexts().get(skey));
            	}	//System.out.println("key : "+ct+"   value : "+root.getNexts().get(ct).getzChar()+"  "+root.getNexts().get(ct).getNexts());
            }*/
            
           // System.out.println(node.getNexts().size());
            if(node != null){
            	//HmzNode hNode = null;
            	if (e != Character.MIN_VALUE)            		
            		 node = node.getNexts().get(e);

                if ( node != null)
                    evolveCandidate(candyMap, node, type, null, next);
            }
        }

        if (special(c, e))
        {
            fly_c = specy;
            specy = candyMap;
        }
        else
        {
            fly_c = candy;
            candy = candyMap;
        }
        fly_c.clear();

        waste(c, e);
    }

    private void updateCandidate(HashMap<Character, HmzNodeChain> input, HashMap<Character, HmzNodeChain> output, char c, char e, FilterTypeEnum type, char next)
    {
        HmzNodeChain chain = input.get(c);
        if(chain != null){
        	if (e == Character.MIN_VALUE)
            {
                for (int i = 0; i < chain.getCount(); ++i)
                    evolveCandidate(output, chain.get(i), type, chain.getState(), next);
            }
            else 
            {
                for (int i = 0; i < chain.getCount(); ++i)
                    evolveCandidate(fly_t, chain.get(i), type, chain.getState(), next);

                HmzNodeChain nChain = fly_t.get(e);
                if (nChain != null)
                {
                    for (int i = 0; i < nChain.getCount(); ++i)
                        evolveCandidate(output, nChain.get(i), type, nChain.getState(), next);
                }
                fly_t.clear();
            }
        }
    }

    private void evolveCandidate(HashMap<Character, HmzNodeChain> candy, HmzNode node, FilterTypeEnum type, Object state, char nt)
    {
        for (int i = 0; i < node.getIdentities().size(); ++i)
        {
        	if((node.getIdentities().get(i).getType().intValue() & type.intValue()) == FilterTypeEnum.None.intValue())
        		continue;
            Object identity = node.getIdentities().get(i).getIdentity();
            if (history.containsKey(identity))
                continue;
            
            if (node.getIdentities().get(i).getMode() == FilterModeEnum.Phrase && isasciiLetter(nt)) {
            	continue;
            }
            
            HmzIdentityChain HmzChain = match.get(identity);
            if(HmzChain == null){
            	HmzChain = new HmzIdentityChain();
            	match.put(identity, HmzChain);
            }
           
                 
            HmzIdentity primary = null;
            boolean exist = false;
            for (int m = 0; m < HmzChain.getCount(); ++m)
            {
                if (primary != null && exist)
                    break;

                if (HmzChain.get(m).isPrimary())
                    primary = HmzChain.get(m);
                
               // if(HmzChain.get(m) == null){
                //	if(node.getIdentities().get(i) == null)
                //		exist = true;
               // }else{
                	if(node.getIdentities().get(i) == HmzChain.get(m))
                	exist = true;
                //}
            }

            if (primary == null && node.getIdentities().get(i).isPrimary())
                primary = node.getIdentities().get(i);

            if (!exist)
            	HmzChain.add(node.getIdentities().get(i));

            if (primary != null && HmzChain.getCount() >= primary.getMatch())
            {
                match.remove(identity);
                hit.add(HmzChain);
                history.put(identity, true);
            }
        }

        
        if (candy != null)
        {
            for (HmzNode next : node.getNexts().values())
            {
            	//if((next.getType().intValue() & type.intValue()) != FilterTypeEnum.None.intValue())
                  if((next.getType().intValue() & type.intValue()) != (FilterTypeEnum.None.intValue()))
                {
                    HmzNodeChain nexts = candy.get(next.getzChar());
                    if(nexts == null){
                    	nexts = new HmzNodeChain(state);
                    	candy.put(next.getzChar(), nexts);
                    }
                    nexts.add(next);
                }
            }
        }
    }



	private boolean isasciiLetter(char c) {
		//到这一步的时候输入字符已通过形似字映射和转小写，故此范围足够
        return c >= 0x61 && c <= 0x7A;
	}
}
