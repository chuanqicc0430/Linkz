package cn.net.cvtt.textfilter.smartTextFilter.normalizer;

import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;

public class HtmlReader extends Reader{
	
	private static final HashMap<Long, Character> mapping = new HashMap<Long, Character>();
	
	private Reader reader;

     private int mode;
     private boolean begin;
     private long code;
     private boolean valid;

     private int[] cContent;
     private int lContent;
     private int iContent;
	
	 static
     {
         mapping.put(getPredefinedHtmlCode("euro"),'€');
         mapping.put(getPredefinedHtmlCode("quot"),'"');
         mapping.put(getPredefinedHtmlCode("amp"),'&');
         mapping.put(getPredefinedHtmlCode("lt"),'<');
         mapping.put(getPredefinedHtmlCode("gt"),'>');
         mapping.put(getPredefinedHtmlCode("nbsp"),' ');
         mapping.put(getPredefinedHtmlCode("iexcl"),'¡');
         mapping.put(getPredefinedHtmlCode("cent"),'¢');
         mapping.put(getPredefinedHtmlCode("pound"),'£');
         mapping.put(getPredefinedHtmlCode("curren"),'¤');
         mapping.put(getPredefinedHtmlCode("yen"),'¥');
         mapping.put(getPredefinedHtmlCode("brvbar"),'¦');
         mapping.put(getPredefinedHtmlCode("sect"),'§');
         mapping.put(getPredefinedHtmlCode("uml"),'¨');
         mapping.put(getPredefinedHtmlCode("copy"),'©');
         mapping.put(getPredefinedHtmlCode("ordf"),'ª');
         mapping.put(getPredefinedHtmlCode("not"),'¬');
         mapping.put(getPredefinedHtmlCode("shy"),'­');
         mapping.put(getPredefinedHtmlCode("reg"),'®');
         mapping.put(getPredefinedHtmlCode("macr"),'¯');
         mapping.put(getPredefinedHtmlCode("deg"),'°');
         mapping.put(getPredefinedHtmlCode("plusmn"),'±');
         mapping.put(getPredefinedHtmlCode("sup2"),'²');
         mapping.put(getPredefinedHtmlCode("sup3"),'³');
         mapping.put(getPredefinedHtmlCode("acute"),'´');
         mapping.put(getPredefinedHtmlCode("micro"),'µ');
         mapping.put(getPredefinedHtmlCode("para"),'¶');
         mapping.put(getPredefinedHtmlCode("middot"),'·');
         mapping.put(getPredefinedHtmlCode("cedil"),'¸');
         mapping.put(getPredefinedHtmlCode("sup1"),'¹');
         mapping.put(getPredefinedHtmlCode("ordm"),'º');
         mapping.put(getPredefinedHtmlCode("raquo"),'»');
         mapping.put(getPredefinedHtmlCode("frac14"),'¼');
         mapping.put(getPredefinedHtmlCode("frac12"),'½');
         mapping.put(getPredefinedHtmlCode("frac34"),'¾');
         mapping.put(getPredefinedHtmlCode("iquest"),'¿');
         mapping.put(getPredefinedHtmlCode("Agrave"),'À');
         mapping.put(getPredefinedHtmlCode("Aacute"),'Á');
         mapping.put(getPredefinedHtmlCode("Acirc"),'Â');
         mapping.put(getPredefinedHtmlCode("Atilde"),'Ã');
         mapping.put(getPredefinedHtmlCode("Auml"),'Ä');
         mapping.put(getPredefinedHtmlCode("Aring"),'Å');
         mapping.put(getPredefinedHtmlCode("AElig"),'Æ');
         mapping.put(getPredefinedHtmlCode("Ccedil"),'Ç');
         mapping.put(getPredefinedHtmlCode("Egrave"),'È');
         mapping.put(getPredefinedHtmlCode("Eacute"),'É');
         mapping.put(getPredefinedHtmlCode("Ecirc"),'Ê');
         mapping.put(getPredefinedHtmlCode("Euml"),'Ë');
         mapping.put(getPredefinedHtmlCode("Igrave"),'Ì');
         mapping.put(getPredefinedHtmlCode("Iacute"),'Í');
         mapping.put(getPredefinedHtmlCode("Icirc"),'Î');
         mapping.put(getPredefinedHtmlCode("Iuml"),'Ï');
         mapping.put(getPredefinedHtmlCode("ETH"),'Ð');
         mapping.put(getPredefinedHtmlCode("Ntilde"),'Ñ');
         mapping.put(getPredefinedHtmlCode("Ograve"),'Ò');
         mapping.put(getPredefinedHtmlCode("Oacute"),'Ó');
         mapping.put(getPredefinedHtmlCode("Ocirc"),'Ô');
         mapping.put(getPredefinedHtmlCode("Otilde"),'Õ');
         mapping.put(getPredefinedHtmlCode("Ouml"),'Ö');
         mapping.put(getPredefinedHtmlCode("times"),'×');
         mapping.put(getPredefinedHtmlCode("Oslash"),'Ø');
         mapping.put(getPredefinedHtmlCode("Ugrave"),'Ù');
         mapping.put(getPredefinedHtmlCode("Uacute"),'Ú');
         mapping.put(getPredefinedHtmlCode("Ucirc"),'Û');
         mapping.put(getPredefinedHtmlCode("Uuml"),'Ü');
         mapping.put(getPredefinedHtmlCode("Yacute"),'Ý');
         mapping.put(getPredefinedHtmlCode("THORN"),'Þ');
         mapping.put(getPredefinedHtmlCode("szlig"),'ß');
         mapping.put(getPredefinedHtmlCode("agrave"),'à');
         mapping.put(getPredefinedHtmlCode("aacute"),'á');
         mapping.put(getPredefinedHtmlCode("acirc"),'â');
         mapping.put(getPredefinedHtmlCode("atilde"),'ã');
         mapping.put(getPredefinedHtmlCode("auml"),'ä');
         mapping.put(getPredefinedHtmlCode("aring"),'å');
         mapping.put(getPredefinedHtmlCode("aelig"),'æ');
         mapping.put(getPredefinedHtmlCode("ccedil"),'ç');
         mapping.put(getPredefinedHtmlCode("egrave"),'è');
         mapping.put(getPredefinedHtmlCode("eacute"),'é');
         mapping.put(getPredefinedHtmlCode("ecirc"),'ê');
         mapping.put(getPredefinedHtmlCode("euml"),'ë');
         mapping.put(getPredefinedHtmlCode("igrave"),'ì');
         mapping.put(getPredefinedHtmlCode("iacute"),'í');
         mapping.put(getPredefinedHtmlCode("icirc"),'î');
         mapping.put(getPredefinedHtmlCode("iuml"),'ï');
         mapping.put(getPredefinedHtmlCode("eth"),'ð');
         mapping.put(getPredefinedHtmlCode("ntilde"),'ñ');
         mapping.put(getPredefinedHtmlCode("ograve"),'ò');
         mapping.put(getPredefinedHtmlCode("oacute"),'ó');
         mapping.put(getPredefinedHtmlCode("ocirc"),'ô');
         mapping.put(getPredefinedHtmlCode("otilde"),'õ');
         mapping.put(getPredefinedHtmlCode("ouml"),'ö');
         mapping.put(getPredefinedHtmlCode("divide"),'÷');
         mapping.put(getPredefinedHtmlCode("oslash"),'ø');
         mapping.put(getPredefinedHtmlCode("ugrave"),'ù');
         mapping.put(getPredefinedHtmlCode("uacute"),'ú');
         mapping.put(getPredefinedHtmlCode("ucirc"),'û');
         mapping.put(getPredefinedHtmlCode("uuml"),'ü');
         mapping.put(getPredefinedHtmlCode("yacute"),'ý');
         mapping.put(getPredefinedHtmlCode("thorn"),'þ');
     }
	 private static long getPredefinedHtmlCode(String name)
     {
         long code = 0L;
         for (int i = 0; i < name.length(); ++i)
         {
             code <<= 8;
             code |= name.charAt(i);
         }
         return code;
     }
	
	 public HtmlReader(Reader reader)
     {
         mode = 0;
         begin = true;
         code = 0L;
         valid = true;

         cContent = new int[2];
         lContent = 0;
         iContent = 0;

         this.reader = reader;
     }
	
	 public int read()throws IOException
     {
         int c = readCache();
         if (c != -1)
             return c;
       
			for (c = reader.read(); c != -1; c = reader.read())
			 {
			     if (fillCache(c))
			     {
			         c = readCache();
			         if (c != -1)
			             return c;
			     }
			 }
         return -1;
     }
	
	 
	 private int readCache()
     {
         if (iContent < lContent)
             return cContent[iContent++];
         return -1;
     }
	
	
	 private boolean fillCache(int c)
     {
         int fillMode = mode;
         switch (mode)
         {
             case 0: // normal
                 if (c == '<')
                 {
                     mode = 1;
                     return false;
                 }
                 if (c == '&')
                 {
                     mode = 2;
                     return false;
                 }
                 cContent[0] = c;
                 lContent = 1;
                 iContent = 0;
                 break;

             case 1: // html tag
                 if (c == '>')
                     mode = 0;
                 return false;

             case 2: // html pre-defined escaping
                 if (begin && c == '#')
                 {
                     mode = 3;
                     return false;
                 }
                 if (c != ';')
                 {
                     appendCode(fillMode, c);
                     return false;
                 }
                 extractChar(mode);
                 mode = 0;
                 break;

             case 3: // html escaping decimal
                 if (begin && (c == 'x' || c == 'u'))
                 {
                     mode = 4;
                     return false;
                 }
                 //goto case 4;

                 case 4: // html escaping hex or unicode
                 if (c != ';')
                 {
                     appendCode(fillMode, c);
                     return false;
                 }
                 extractChar(mode);
                 mode = 0;
                 break;

             default:
                 return false;
         }

         return lContent > 0;
     }
	 
	 
	 //private void appendCode(int mode, int c)
	 private void appendCode(int mode, long c)
     {
         if (!valid)    // silently discard
             return;

         begin = false;
         switch (mode)
         {
             case 2:
                 if (('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z'))
                 {
                     code <<= 8;
                     // _code |= (uint)c;
                     code |= c;
                 }
                 else
                 {
                     valid = false;
                 }
                 break;

             case 3:
                 if ('0' <= c && c <= '9')
                 {
                     code *= 10;
                     code += c - '0';
                 }
                 else
                 {
                     valid = false;
                 }
                 break;

             case 4:
                 if ('0' <= c && c <= '9')
                 {
                     code *= 16;
                     code += c - '0';
                 }
                 else if ('A' <= c && c <= 'F')
                 {
                     code *= 16;
                     code += 10;
                     code += c - 'A';
                 }
                 else if ('a' <= c && c <= 'f')
                 {
                     code *= 16;
                     code += 10;
                     code += c - 'a';
                 }
                 else
                 {
                     valid = false;
                 }
                 break;

             default:
                 valid = false;
                 break;
         }
     }
	 
	 
	 private boolean extractChar(int mode)
     {
         lContent = 0;
         iContent = 0;

         long logCode = code;

         begin = true;
         code = 0L;

         if (!valid)
             return false;

         valid = true;

         if (mode == 2)
         {
              Character c = mapping.get(logCode);
             if(c != null){
            	 cContent[0] = c;
                 lContent = 1;
                 return true;
             }
             return false;
         }

         if (logCode < 0 || logCode > 0x10FFFF)
             return false;

         if (logCode < 0x10000)
         {
             cContent[0] = (int)logCode;
             lContent = 1;
         }
         else
         {
             char[] str = Character.toChars((int)logCode);
             cContent[0] = str[0];
             cContent[1] = str[1];
             lContent = 2;
         }

         return true;
     }
	 
	public int read(char[] cbuf, int off, int len) throws IOException {
		return 0;
	}

	public void close() throws IOException {
	}

}
