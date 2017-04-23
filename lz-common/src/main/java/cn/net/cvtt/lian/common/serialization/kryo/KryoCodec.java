package cn.net.cvtt.lian.common.serialization.kryo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.JavaSerializer;

import cn.net.cvtt.lian.common.serialization.Codec;

/**
 * @author renwei
 *
 */
public class KryoCodec implements Codec {

	Class<?> clazz;
	Kryo kryo ;

	
	
	
	/**
	 * 构造方法
	 * 
	 * @param clazz
	 */
	public KryoCodec(Class clazz) {
		this.kryo = new Kryo();
	    kryo.setReferences(false);
	    
		this.clazz = clazz;
	}

	
	public void registerClass(Class type)
	{
		kryo.register(type);
		//kryo.register(type, serializer,id);
	}
	/*
	 * @see cn.net.cvtt.lian.common.serialization.Codec#encode(java.lang.Object,
	 * java.io.OutputStream)
	 */
	@Override
	public void encode(Object obj, OutputStream stream)  {
 
	      ByteArrayOutputStream baos = new ByteArrayOutputStream();
	      Output output = new Output(baos);
	      kryo.writeClassAndObject(output, obj);
	      output.flush();
	      output.close();
	      stream=output;	 
	}

	/*
	 * @see cn.net.cvtt.lian.common.serialization.Codec#encode(java.lang.Object)
	 */
	@Override
	public byte[] encode(Object obj)   {

 
	 
	      ByteArrayOutputStream baos = new ByteArrayOutputStream();
	      Output output = new Output(baos);
	      kryo.writeClassAndObject(output, obj);
	      output.flush();
	      output.close();
	      
	      
	      byte[] b = baos.toByteArray();
	      try {
	            baos.flush();
	            baos.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	      return b;
	}

	/*
	 * @see cn.net.cvtt.lian.common.serialization.Codec#decode(java.io.InputStream)
	 */
	@Override
	public <E> E decode(InputStream stream, int len) throws IOException  {
		byte[] buffer = new byte[len];
		stream.read(buffer, 0, len);
	    return this.<E> decode(buffer);

	}

	/*
	 * @see cn.net.cvtt.lian.common.serialization.Codec#decode(byte[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <E> E decode(byte[] buffer)  {
		
		ByteArrayInputStream bais=new ByteArrayInputStream(buffer);
	    Input input=new Input(bais);
		
	 
		return (E) kryo.readClassAndObject(input);
	}


	@Override
	public String codecName() {
		// TODO Auto-generated method stub
		return "kryo";
	}

}
