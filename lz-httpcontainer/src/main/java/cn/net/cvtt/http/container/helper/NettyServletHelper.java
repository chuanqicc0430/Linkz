package cn.net.cvtt.http.container.helper;

import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.configuration.ConfigurationException;
import cn.net.cvtt.http.container.app.bean.HttpApplication;
import cn.net.cvtt.http.container.app.context.ApplicationCtxMaker;
import cn.net.cvtt.http.container.core.configuration.NettyServletConfig;
import cn.net.cvtt.http.container.core.configuration.NettyServletConfigFactory;
import cn.net.cvtt.http.container.core.filter.NettyFilterFactory;
import cn.net.cvtt.http.container.core.service.NettyServletService;
import cn.net.cvtt.http.container.core.service.NettyServletServiceImpl;
import cn.net.cvtt.resource.route.context.ApplicationCtx;

/**
 * NettyService 构造启动助手类
 * 
 * @author
 *
 */
public class NettyServletHelper {
	// 判断是否容器启动了
	private static boolean isStart = false;
	private final static Logger LOGGER = LoggerFactory.getLogger(NettyServletHelper.class);

	/**
	 * @category param application
	 * @param isFilter
	 * @param serviceName
	 *            读取配置项，过滤规则名
	 * @return servletService
	 * @throws ServletException
	 */
	public static NettyServletService nettyServletServiceStartup(HttpApplication<? extends ApplicationCtx> application, String serviceName, boolean isFilter) throws ServletException {
		NettyServletService servletService = new NettyServletServiceImpl();
		// 填充HttpApplication到NettyServlet容器里
		servletService.addApplication(application);
		start(servletService, serviceName, isFilter);
		return servletService;
	}

	/**
	 * @category param applications
	 * @param isFilter
	 * @param serviceName
	 *            读取配置项
	 * @return servletService
	 * @throws ServletException
	 */
	public static NettyServletService nettyServletServiceStartup(Set<HttpApplication<? extends ApplicationCtx>> applications, String serviceName, boolean isFilter) throws ServletException {
		NettyServletService servletService = new NettyServletServiceImpl();
		// 填充HttpApplication到NettyServlet容器里
		servletService.addApplications(applications);
		start(servletService, serviceName, isFilter);
		return servletService;
	}

	/**
	 * @category param applications
	 * @param isFilter
	 * @param serviceName
	 *            读取配置项
	 * @return servletService
	 * @throws ServletException
	 */
	public static NettyServletService nettyServletServiceStartupByLocal(Set<HttpApplication<? extends ApplicationCtx>> applications, String serviceName, String ip, int port) throws ServletException {
		NettyServletService servletService = new NettyServletServiceImpl();
		// 填充HttpApplication到NettyServlet容器里
		servletService.addApplications(applications);
		start(servletService, serviceName, ip, port);
		return servletService;
	}

	private static void start(NettyServletService servletService, String serviceName, String ip, int port) throws ServletException {

		// 注册自定义的context maker，context maker可以自定义，目前所有的application应该都是无状态的，所有都是NullContext；
		servletService.registerContextFactory(new ApplicationCtxMaker());
		if (!isStart) {
			// 构建启动参数，端口，连接，超时等连接参数
			NettyServletConfig createConfiguration = null;
			try {
				createConfiguration = NettyServletConfigFactory.createConfigurationByLocal(serviceName, ip, port, "90000", "60000", "", "", "");

				LOGGER.info(String.format("init NettyServlet configuration : %s", serviceName));
			} catch (ConfigurationException e) {
				LOGGER.error(String.format("init NettyServlet configuration : %s", serviceName), e);
				throw new ServletException(String.format("init NettyServlet configuration : %s", serviceName), e);
			}
			servletService.start(createConfiguration);
			isStart = true;
		}
		LOGGER.info("NettyServletEngine------------start service");
	}

	private static void start(NettyServletService servletService, String serviceName, boolean isFilter) throws ServletException {
		if (isFilter) {
			FilterChain filterChain = NettyFilterFactory.createFilter();
			servletService.setFilterChain(filterChain);
		}
		// 注册自定义的context maker，context maker可以自定义，目前所有的application应该都是无状态的，所有都是NullContext；
		servletService.registerContextFactory(new ApplicationCtxMaker());
		if (!isStart) {
			// 构建启动参数，端口，连接，超时等连接参数
			NettyServletConfig createConfiguration = null;
			try {
				createConfiguration = NettyServletConfigFactory.createConfiguration(serviceName);
				LOGGER.info(String.format("init NettyServlet service : %s,configuration : %s", serviceName, createConfiguration.toString()));
			} catch (ConfigurationException e) {
				LOGGER.error(String.format("init NettyServlet configuration : %s error", serviceName), e);
				throw new ServletException(String.format("init NettyServlet service : %s error", serviceName), e);
			}
			servletService.start(createConfiguration);
			isStart = true;
		}
		LOGGER.info("NettyServletEngine---start service");
	}

	/**
	 * 连接配置项匹配*的
	 * 
	 * @param application
	 * @return servletService
	 * @throws ServletException
	 */
	public static NettyServletService nettyServletServiceStartup(HttpApplication<? extends ApplicationCtx> application) throws ServletException {
		return nettyServletServiceStartup(application, "*", false);
	}
}
