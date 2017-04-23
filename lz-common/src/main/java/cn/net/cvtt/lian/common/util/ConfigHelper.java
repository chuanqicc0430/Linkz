package cn.net.cvtt.lian.common.util;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * {在这里补充类的功能说明}
 * 
 * @author 
 */
public class ConfigHelper
{
	public static Properties loadProperties(String path) throws IOException
	{
		FileReader fileReader = new FileReader(path);
		try {
			Properties props = new Properties();
			props.load(fileReader);
			return props;
		} finally {
			fileReader.close();
		}
	}
	
	public static <E> E loadConfigBean(String path, Class<? extends ConfigBean> beanClazz) throws IOException
	{
		Properties props = loadProperties(path);
		return (E)ConfigBean.valueOf(props, beanClazz);
	}
}
