package cn.net.cvtt.lian.common.util;

 

import org.slf4j.Logger;

import com.google.gson.Gson;

import cn.net.cvtt.lian.common.dumper.Dumpable;

public class LogHelper
{

    @Deprecated
    public static void info(Logger log, String message, Object obj)
    {
        try
        {
            if (log.isInfoEnabled())
            {
                String str = String.format("%s %s" , message , transferStr(obj));
                log.info(str);
            }
        }
        catch (Exception ex)
        {
            log.error("logInfo is error" , ex);
        }
    }

    public static <A, B, C> void info(Logger log, String message, A objA, B objB, C objC)
    {
        try
        {
            if (log.isInfoEnabled())
            {
                String str = String.format("%s\r\n%s\r\n%s\r\n%s" , message , transferString(objA) , transferString(objB) , transferString(objC));

                log.info(str);
            }
        }
        catch (Exception ex)
        {
            log.error("logInfo is error" , ex);
        }
    }

    public static <A, B> void info(Logger log, String message, A objA, B objB)
    {
        try
        {
            if (log.isInfoEnabled())
            {
                String str = String.format("%s\r\n%s\r\n%s " , message , transferString(objA) , transferString(objB));

                log.info(str);
            }
        }
        catch (Exception ex)
        {
            log.error("logInfo is error" , ex);
        }
    }

 

    @Deprecated
    public static void error(Logger log, String errinfo, Throwable ex, Object obj)
    {
        try
        {
        	String str = String.format("%s\r\n%s" ,errinfo,  transferString(obj));
            log.error(str, ex); 
        }
        catch (Exception e)
        {
            log.error(errinfo , e);
        }
    }

    public static <A, B> void error(Logger log, String errinfo, Throwable ex, A objA, B objB)
    {
        try
        {
            String str = String.format("%s\r\n%s\r\n%s " , errinfo , transferString(objA) , transferString(objB));
            log.error(str , ex);
        }
        catch (Exception e)
        {
            log.error(errinfo , e);
        }
    }

    @Deprecated
    private static String transferStr(Object obj)
    {
        if (obj == null)
            return " NULL ";
        if (obj instanceof Dumpable)
        {
            Dumpable d = (Dumpable) obj;
            return d.dumpContent();
        }
        else
        {
            return new Gson().toJson(obj);
        }
    }

    private static <T> String transferString(T obj)
    {
        if (obj == null)
            return " NULL ";
        if (obj instanceof Dumpable)
        {
            Dumpable d = (Dumpable) obj;
            return d.dumpContent();
        }
        else
        {
            return new Gson().toJson(obj);
        }
    }
}