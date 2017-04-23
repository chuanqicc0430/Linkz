package cn.net.cvtt.lian.common.serialization.protobuf.util;

import cn.net.cvtt.lian.common.serialization.protobuf.ProtoEntity;
import cn.net.cvtt.lian.common.serialization.protobuf.UnknownFieldSet;

/**
 * 
 * <b>描述: </b>用于序列化组件,是一个用于序列化时作为中转数据的实体类,这个实体中不存在任何数据字段，所有反序列化出来的信息都存入基类的
 * {@link UnknownFieldSet}对象中，在这个实体序列化的时候，也随之序列化到输出流中，可以保证信息不丢失
 * <p>
 * <b>功能: </b>是一个用于序列化时作为中转数据的实体类，在反序列化时将此类传入，此类会反序列化出源信息中的内容，如果将此类序列化，会将原信息写回流中
 * <p>
 * <b>用法: </b>
 * 
 * <pre>
 * 中转数据示例
 * ProtoTransferEntity entity = new ProtoTransferEntity();
 * ProtoManager.parseForm(entity, input);// 从流中反序列化到ProtoTransferEntity对象中
 * ProtoManager.writeTo(entity,output);//将信息写回流中
 * </pre>
 * <p>
 * 
 * @author 
 * 
 */
public class ProtoTransferEntity extends ProtoEntity {

}
