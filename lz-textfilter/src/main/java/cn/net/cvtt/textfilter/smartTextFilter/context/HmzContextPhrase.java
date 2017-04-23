package cn.net.cvtt.textfilter.smartTextFilter.context;

import java.util.ArrayList;
import java.util.List;

import cn.net.cvtt.textfilter.smartTextFilter.tree.HmzNode;

public class HmzContextPhrase extends HmzContext{
	
	 private char prev = Character.MIN_VALUE;

     private List<HmzIdentityChain> temp = new ArrayList<HmzIdentityChain>();
     private List<HmzIdentityChain> hit = new ArrayList<HmzIdentityChain>();
    
     public List<HmzIdentityChain> getHit()
     {
          return hit; 
     }

     public HmzContextPhrase(HmzNode root){
    	 super(root);
     }

     protected void waste(char c, char e)
     {
         prev = c;
     }

     protected boolean checkIn(char c, char e)
     {
         if (!isAsciiLetter(prev))
             return true;
         return false;
     }

     protected boolean checkHit(char c, char e, boolean boolHit)
     {
         boolean bingo = false;
         if (c == Character.MIN_VALUE)
         {
             hit.addAll(super.getHit());
             super.getHit().clear();
             bingo = true;
         }

         if (temp.size() > 0)
         {
             if (!isAsciiLetter(c)) // including char.MinValue
             {
                 hit.addAll(temp);
                 temp.clear();
                 bingo = true;
             }
             else
             {
                 temp.clear();
             }
         }

         if (boolHit)
         {
             temp.addAll(super.getHit());
             super.getHit().clear();
         }

         return bingo;
     }

     public static boolean isAsciiLetter(char c)
     {
         //到这一步的时候输入字符已通过形似字映射和转小写，故此范围足够
         return c >= 0x61 && c <= 0x7A;
     }

}
