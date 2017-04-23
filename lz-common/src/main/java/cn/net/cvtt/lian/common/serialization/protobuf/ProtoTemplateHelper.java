package cn.net.cvtt.lian.common.serialization.protobuf;

import java.io.IOException;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.lian.common.serialization.protobuf.generator.ProtoConfig;
import cn.net.cvtt.lian.common.serialization.protobuf.util.FileUtil;

/**
 * 
 * <b>描述: </b>这是一个用于序列化模板辅助生成的类，在代码自动生成时，自动生成的代码会来填充模板，此模板帮助类，
 * 仅仅是为了将一个文件类型的模板转换成字节数组的形式
 * <p>
 * <b>功能: </b>帮助文件或文字类型的模板转换成字节数组的表现形式
 * <p>
 * <b>用法: </b>不建议外部调用
 * <p>
 * 
 * @author 
 * 
 */
public class ProtoTemplateHelper {

	private static final Logger logger = LoggerFactory.getLogger(ProtoTemplateHelper.class);

	public static void getBytesFrom(String path) throws IOException {
		String string = FileUtil.read(path).trim();
		logger.info(Arrays.toString(string.getBytes()));
		System.out.println(path + ":");
		System.out.println(Arrays.toString(string.getBytes()));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		// if (ProtoTemplateHelper.class.getResource("/") != null) {
		// String path = ProtoTemplateHelper.class.getResource("/").getPath();
		// logger.info("NativeProtoEntity.template");
		// getBytesFrom(path + "protobuf/NativeProtoEntity.template");
		// logger.info("ProtoBuilder.template");
		// getBytesFrom(path + "protobuf/ProtoBuilder.template");
		// logger.info("ProtoBuilderFactory.template");
		// getBytesFrom(path + "protobuf/ProtoBuilderFactory.template");
		// }
		System.out.println(ProtoConfig.PROTO_BUILDER_TEMPLATE);
		getBytesFrom("c:\\native.txt");
	}
}
