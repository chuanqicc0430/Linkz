package cn.net.cvtt.textfilter.smartTextFilter.context;

import cn.net.cvtt.textfilter.smartTextFilter.tree.HmzNode;

public class HmzContextLanguage extends HmzContext{
	
	 public HmzContextLanguage(HmzNode root){
		 super(root);
	 }

     protected boolean ignore(char c, char e)
     {
         if (e == Character.MIN_VALUE)
             return !isChineseChar(c);
         return !isChineseChar(c, e);
     }

     public static boolean isChineseChar(char c)
     {
         if ((c >= 0x3400 && c <= 0x4DB5) || (c >= 0x4E00 && c <= 0x9FB3) || (c >= 0xF900 && c <= 0xFAF6))
             return true;
         return false;
     }

     public static boolean isChineseChar(char h, char l)
     {
         if (l >= 0xD840 && l <= 0xD869)
             return true;
         if ((h >= 0xD840 && h <= 0xD868) && (l >= 0xDC00 && l <= 0xDFFF))
             return true;
         if (h == 0xD869 && (l >= 0xDC00 && l <= 0xDED6))
             return true;
         return false;
     }

}
