package cn.net.cvtt.resource.dfsfile.fastdfs.mrg;

import org.apache.commons.pool.BasePoolableObjectFactory;

import cn.net.cvtt.resource.dfsfile.fastdfs.TrackerClient;

 

public class TrackerFactory extends BasePoolableObjectFactory{
	
	 private final String host;
     private final int port;
     private final int timeout;
     
     public TrackerFactory(String host,int port,int timeout) {
    	 super();
    	 this.host=host;
    	 this.port=port;
    	 this.timeout=timeout;
	}

	public Object makeObject() throws Exception {
		TrackerClient trackerClient=new TrackerClient(host,port,timeout);
		trackerClient.connect();
		return trackerClient;
	}

	@Override
	public void destroyObject(Object obj) throws Exception {
		if(obj instanceof TrackerClient){
			final TrackerClient trackerClient=(TrackerClient)obj;
			if(trackerClient.getConnection().isConnected()){
				try{
					trackerClient.getConnection().close();
				}catch (Exception e) {
					
				}
			}
		}
	}

	@Override
	public boolean validateObject(Object obj) {
		if(obj instanceof TrackerClient){
			final TrackerClient trackerClient=(TrackerClient)obj;
			try{
				boolean tag=trackerClient.getConnection().isConnected()&&trackerClient.getConnection().ping();
				return tag;
			}catch (Exception e) {
				return false;
			}
			
		}else{
			return false;
		}
	}
	
	

}
