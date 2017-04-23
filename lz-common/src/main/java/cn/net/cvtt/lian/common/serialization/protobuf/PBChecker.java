package cn.net.cvtt.lian.common.serialization.protobuf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.lian.common.serialization.bytecode.type.ClassFile;
import cn.net.cvtt.lian.common.serialization.bytecode.type.ConstantClassInfo;
import cn.net.cvtt.lian.common.serialization.bytecode.type.ConstantUTF8Info;
import cn.net.cvtt.lian.common.serialization.bytecode.type.Global;
import cn.net.cvtt.lian.common.serialization.protobuf.util.TwoTuple;

/**
 * 
 * <b>描述: </b> 用于检查PB可序列化类格式的正确性
 * <p>
 * <b>功能: </b>
 * <p>
 * <b>用法: </b>
 * 
 * <pre>
 *  检查工程目录下全部的class
 *  PBChecker.check();
 *  检查某一个目录下全部的class
 *  PBChecker.check(new File(&quot;C:\\a&quot;));
 * </pre>
 * <p>
 * 
 * @author 
 * 
 */
public class PBChecker {
	
	public static boolean isShowProgressBar = false;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PBChecker.class);

	/**
	 * 验证当前环境变量下所有的可序列化类的准确性,返回验证失败的类的列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public static List<TwoTuple<String, Exception>> check() throws Exception {
		return check("");
	}

	/**
	 * 验证某一个包目录下所有的可序列化类的准确性,返回验证失败的类的列表
	 * 
	 * @param packageName
	 * @return 返回验证失败的类的列表
	 * @throws Exception
	 */
	public static List<TwoTuple<String, Exception>> check(String packageName) throws Exception {
		List<TwoTuple<String, Exception>> failedList = new ArrayList<TwoTuple<String, Exception>>();
		List<ClassFile> taskList = new ArrayList<ClassFile>();
		analyzePackage(packageName, taskList);
		int count = 0;
		int total = taskList.size();
		LOGGER.info("Found {} ProtoEntity.", total);
		for (ClassFile classFile : taskList) {
			int thisClass = classFile.getThis_class();
			ConstantClassInfo cpInfo = (ConstantClassInfo) classFile.getConstant_pool()[thisClass];
			String classPath = cpInfo.getName().getValue();

			classPath = classPath.replace("/", ".");

			LOGGER.info((++count) + "/" + total + " Check " + classPath);
			if(isShowProgressBar){
				progressBar(Float.valueOf(count) / Float.valueOf(total));
			}
			try {
				Class<ProtoEntity> clazz = (Class<ProtoEntity>) Class.forName(classPath);
				ProtoManager.getProtoBuilder(clazz.newInstance());
			} catch (Exception e) {
				failedList.add(new TwoTuple<String, Exception>(classPath, e));
			}
		}

		LOGGER.info("-------------------------------------------------");
		LOGGER.info("total  : {}", total);
		LOGGER.info("sucess : {}", (total - failedList.size()));
		LOGGER.info("failed : {}", failedList.size());
		if (failedList.size() > 0) {
			for (TwoTuple<String, Exception> twoTuple : failedList) {
				LOGGER.error("Failed : " + twoTuple.getFirst());
			}
		}
		return failedList;
	}

	/**
	 * 分析某一个包路径下的类
	 * 
	 * @param packageName
	 * @param taskList
	 * @throws Exception
	 */
	public static void analyzePackage(String packageName, List<ClassFile> taskList) throws Exception {
		if (packageName == null || packageName.trim().length() == 0) {
			packageName = "";
		} else {
			packageName = packageName.replaceAll("\\.", "/");
			if (!packageName.endsWith("/")) {
				packageName += "/";
			}
		}
		try {
			LOGGER.info("Load package : {}", packageName);
			Enumeration<URL> enumeration = Thread.currentThread().getContextClassLoader().getResources(packageName);
			while (enumeration.hasMoreElements()) {
				URL url = (URL) enumeration.nextElement();
				String protocol = url.getProtocol();
				if (protocol.equals("jar")) {
					analyzeJAR(url, taskList);
				} else {
					analyzeFile(url, packageName, taskList);
				}
			}

		} catch (Exception e) {
			throw new RuntimeException("loadPackage", e);
		}
	}

	/**
	 * 处理jar中的class
	 * 
	 * @param url
	 * @throws IOException
	 */
	private static void analyzeJAR(URL url, List<ClassFile> taskList) throws Exception {
		JarURLConnection con = (JarURLConnection) url.openConnection();
		JarFile file = con.getJarFile();
		Enumeration<?> enumeration = file.entries();
		while (enumeration.hasMoreElements()) {
			JarEntry element = (JarEntry) enumeration.nextElement();
			String entryName = element.getName();
			if (entryName != null && entryName.endsWith(".class")) {
				analyzeClass(entryName, taskList);
			}
		}
	}

	/**
	 * 处理文件目录中的class
	 * 
	 * @param url
	 * @throws URISyntaxException
	 */
	private static void analyzeFile(URL url, String packageName, List<ClassFile> taskList) throws Exception {
		File file = new File(new URI(url.toExternalForm()));
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				analyzeFile(files[i].toURI().toURL(), packageName + files[i].getName() + "/", taskList);
			} else {
				String entryName = packageName + files[i].getName();
				if (entryName != null && entryName.endsWith(".class")) {
					analyzeClass(entryName, taskList);
				}
			}
		}
	}

	private static void analyzeClass(String path, List<ClassFile> taskList) throws Exception {
		LOGGER.info("Read " + path);
		// path = path.startsWith("/") ? path : "/" + path;
		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
		analyzeClass(input, taskList);
	}

	/**
	 * 用于分析某一个类是否是需要验证的类
	 * 
	 * @param input
	 * @param taskList
	 * @throws Exception
	 */
	private static void analyzeClass(InputStream input, List<ClassFile> taskList) throws Exception {
		byte[] buffer = toBytes(input);
		ClassFile classFile = null;
		try {
			classFile = ClassFile.valueOf(buffer);
		} catch (Exception e) {
			LOGGER.error("analyzeClass failed. ", e);
		}
		if (classFile != null) {
			if (isProtoEntity(classFile)) {
				taskList.add(classFile);
			}
		}
	}

	/**
	 * 将流转换为字节数组
	 * 
	 * @param input
	 * @return
	 * @throws IOException
	 */
	private static byte[] toBytes(InputStream input) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int len = 0;
		byte[] b = new byte[1024];
		while ((len = input.read(b, 0, b.length)) != -1) {
			baos.write(b, 0, len);
		}
		byte[] buffer = baos.toByteArray();
		return buffer;
	}

	/**
	 * 验证某一个类文件是否是一个继承自ProtoEntity的可序列化类
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	private static boolean isProtoEntity(ClassFile classFile) throws Exception {
		try {
			int superClassIndex = classFile.getSuper_class();
			ConstantClassInfo cpInfo = (ConstantClassInfo) classFile.getConstant_pool()[superClassIndex];
			ConstantUTF8Info classPath = cpInfo.getName();
			int accessFlags = classFile.getAccess_flags();
			if ((accessFlags & Global.ACC_ABSTRACT) != 0) {
				// 不处理abstract的类
				return false;
			}
			if (classPath.getValue().equals("cn/net/cvtt/lian/common/serialization/protobuf/ProtoEntity")) {
				return true;
			}
		} catch (Exception e) {
			LOGGER.warn("isProtoEntity failed.", e);
		}
		return false;
	}

	public static void progressBar(float rate) {
		if (rate > 1) {
			rate = 1;
		} else if (rate < 0) {
			rate = 0;
		}
		System.out.println("__________________________________________________");
		rate = rate * 50;
		int rateI = (int) rate;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < rateI; i++) {
			sb.append("█");
		}
		System.out.print(sb.toString());
		sb = new StringBuffer();
		for (int i = 0; i < 50 - rateI; i++) {
			sb.append("_");
		}
		sb.append("▏");
		sb.append((rateI * 2) + "%");
		System.out.println(sb.toString());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		PBChecker.check();
		PBChecker.check("com.feinno.imps.legacy");
	}

}
