package cn.net.cvtt.resource.database;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.sql.rowset.CachedRowSet;

/**
 * ResultSet序列化反序列化帮助类
 * 
 * @author lichunlei
 *
 */

public class ResultSetSerializer {
	
	public static byte[] encode(CachedRowSet rs) throws IOException
	{
		if(rs == null)
			return null;
		
		ByteArrayOutputStream   baos = new ByteArrayOutputStream(); 
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(rs);
		byte[] bytes = baos.toByteArray();
		oos.flush();
		oos.close();
		baos.close();
		
		return bytes;
		
	}
	
	public static CachedRowSet decode(byte[] bs) throws IOException, ClassNotFoundException
	{
		if(bs == null)
			return null;
				
		ByteArrayInputStream bais = new ByteArrayInputStream(bs);
		ObjectInputStream ois = new ObjectInputStream(bais);
		CachedRowSet cs = (CachedRowSet)ois.readObject();		
		ois.close();
		bais.close();	
		
		return cs;
		
	}

}
