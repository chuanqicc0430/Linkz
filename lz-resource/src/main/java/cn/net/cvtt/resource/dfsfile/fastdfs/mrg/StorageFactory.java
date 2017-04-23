package cn.net.cvtt.resource.dfsfile.fastdfs.mrg;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.resource.dfsfile.fastdfs.StorageClient;

 

public class StorageFactory extends BasePoolableObjectFactory{
	
	
	 private final String host;
     private final int port;
     private final int timeout;
	
	public StorageFactory(String host,int port,int timeout) {
		 super();
    	 this.host=host;
    	 this.port=port;
    	 this.timeout=timeout;
	}
	
	

	@Override
	public Object makeObject() throws Exception {
		StorageClient storageClient = new StorageClient(host,port,timeout);
		storageClient.connect();
		return storageClient;
	}
	
	@Override
	public void destroyObject(Object obj) throws Exception {
		
		if(obj instanceof StorageClient){
			final StorageClient storageClient=(StorageClient)obj;
			if(storageClient.getConnection().isConnected()){
				try{
					storageClient.getConnection().close();
				}catch (Exception e) {
					
				}
			}
		}
	}

	@Override
	public boolean validateObject(Object obj) {
		Logger LOGGER = LoggerFactory.getLogger(StorageFactory.class);
		if(obj instanceof StorageClient){
			final StorageClient storageClient=(StorageClient)obj;
			try{
				boolean tag1=storageClient.getConnection().isConnected();
				if(!tag1){
					LOGGER.error("isConnected() false");
					return false;
				}
				boolean tag2=storageClient.getConnection().ping();
				if(!tag2){
					LOGGER.error("ping() false");
					return false;
				}
				return true;
			}catch (Exception e) {
				LOGGER.error("validate storage faile", e);
				return false;
			}
			
		}else{
			LOGGER.error("obj is not StorageClient");
			return false;
		}
	}

}
