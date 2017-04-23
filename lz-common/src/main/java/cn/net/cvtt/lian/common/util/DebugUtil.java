package cn.net.cvtt.lian.common.util;

import java.net.URL;

/**
 * 
 * @author 
 *
 */
public class DebugUtil {
	private static boolean localDebug=false;
    static   
    {
    	URL url=ClassLoader.getSystemResource("debug.properties");
        
    	if(url!=null)
        {
    		DebugUtil.localDebug=true;
        }
 
    }
    public static boolean isLocalDebug()
    {
    	 
    	return DebugUtil.localDebug;
    }
	public static void setLocalDebug(boolean localDebug) {
		DebugUtil.localDebug = localDebug;
	}
}
