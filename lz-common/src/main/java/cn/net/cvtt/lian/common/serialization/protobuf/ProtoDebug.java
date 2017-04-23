package cn.net.cvtt.lian.common.serialization.protobuf;

/**
 * 
 * <b>描述:
 * </b>用于辅助定位序列化问题的类，该类提供了用于测试序列化后的数据是否可以成功反序列化的方法，还可以生成某一个想要序列化的实体类型的辅助源码
 * ，可以用于排错
 * <p>
 * <b>功能: </b>一个类想要使用Protobuf序列化，在不能判断是否可以成功的情况下，可以使用此类进行测试
 * <p>
 * <b>用法: </b>
 * 
 * <pre>
 * 判断当前类型是否可以成功序列化及反序列化:
 * TestEntity testEntity1 = new TestEntity();
 * testEntity1.setId(1);
 * testEntity1.setName(&quot;Feinno&quot;);
 * TestEntity testEntity2 = ProtoDebug.testWriteAndMerage(testEntity);
 * boolean isSucess = testEntity1.equals(testEntity2);
 * </pre>
 * <p>
 * 
 * @author 
 * 
 */
public class ProtoDebug {

	/**
	 * 本方法是用于测试某一个实体类型是否可以进行序列化和反序列化<br>
	 * 调用本方法时，请将一个已装填满数据的实体对象传入，此方法会将这些数据序列化到磁盘上，再从磁盘中对这些数据进行反序列化，
	 * 反序列化的数据会被装填到一个新的实体对象中
	 * ，最后将这个实体对象返回给外部调用者，外部调用者在判断对象中的数据是否有丢失后，就可以知道这个实体是否可以完美序列化了
	 * 
	 * @param t
	 * @param action
	 * @return
	 */
	public static <T extends ProtoEntity> T testWriteAndMerage(T t) {
		try {
			byte[] buffer = t.toByteArray();
			T t2 = (T) t.getClass().newInstance();
			t2.parseFrom(buffer);
			return t2;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
