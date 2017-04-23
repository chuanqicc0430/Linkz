package cn.net.cvtt.http.container.helper;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.http.container.app.bean.HttpApplication;
import cn.net.cvtt.resource.route.context.ApplicationCtx;

/**
 * 装载application
 * 
 * @author zongchuanqi
 *
 */
public class ApplicationFactory {

	private final static Logger LOGGER = LoggerFactory.getLogger(ApplicationFactory.class);

	/**
	 * 实例化每个Application，并加载每个application的load方法
	 * 
	 * @param classises
	 * @return applicationSet
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Set<HttpApplication<? extends ApplicationCtx>> createApplications(Set<Class<? extends HttpApplication<? extends ApplicationCtx>>> classises) throws Exception {
		Set<HttpApplication<? extends ApplicationCtx>> applicationSet = new HashSet<HttpApplication<? extends ApplicationCtx>>();
		for (Iterator<Class<? extends HttpApplication<? extends ApplicationCtx>>> iterator = classises.iterator(); iterator.hasNext();) {
			Class<HttpApplication<? extends ApplicationCtx>> applicationClass = (Class<HttpApplication<? extends ApplicationCtx>>) iterator.next();
			try {
				HttpApplication<? extends ApplicationCtx> newInstance = applicationClass.newInstance();
				newInstance.load();
				applicationSet.add(newInstance);
			} catch (Exception e) {
				LOGGER.error(String.format("load application %s error", applicationClass.getSimpleName()), e);
			}
		}
		return applicationSet;
	}

	/**
	 * 卸载Application，执行application的unload方法
	 * 
	 * @param classises
	 * @return applicationSet
	 * @throws Exception
	 */
	public static void releaseApplications(Set<HttpApplication<? extends ApplicationCtx>> applicationSet) {
		for (Iterator<HttpApplication<? extends ApplicationCtx>> iterator = applicationSet.iterator(); iterator.hasNext();) {
			HttpApplication<? extends ApplicationCtx> application = (HttpApplication<? extends ApplicationCtx>) iterator.next();
			try {
				application.unload();
			} catch (Exception e) {
				LOGGER.error(String.format("unload application %s error", application.getClass().getSimpleName()), e);
			}
		}
	}

}
