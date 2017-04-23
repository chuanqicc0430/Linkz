/*package cn.net.cvtt.lian.common.serialization.protobuf.util;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import com.feinno.osgi.CommonServiceAceivator;

public class ClassLoaderUtil {

	public static URLClassLoader instance;
	
	public static URLClassLoader GetClassLoader() {
		if (instance != null)
			return instance;
		try{
			List<String> classPath = CommonServiceAceivator.getClassPath();
			URL[] urls = new URL[classPath.size()];
			for (int i = 0; i < urls.length; i++){
				urls[i] = new URL("file:" + classPath.get(i));
			}
			instance = new URLClassLoader(urls);
		}
		catch (Exception ex){
			
		}
		return instance;
	}
	
	
}
*/