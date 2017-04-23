package cn.net.cvtt.resource.dfsfile.fastdfs.mrg;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.pool.impl.GenericObjectPool.Config;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.lian.common.initialization.Initializer;
import cn.net.cvtt.lian.common.util.ObjectHelper;
import cn.net.cvtt.resource.dfsfile.fastdfs.StorageClient;
import cn.net.cvtt.resource.dfsfile.fastdfs.TrackerClient;
 

public class FastdfsRouteDirector {
	private final static Logger LOGGER = LoggerFactory.getLogger(FastdfsRouteDirector.class);
	private static  int maxWait;
	
	private static int maxActive;
	
	private static int maxIdle;
	
	private static int minIdle;
	
	private static int timeout;
	
	private static String charset="ISO8859-1";
	
	private static ArrayList<Pool<TrackerClient>> trackerPoolArrays=new ArrayList<Pool<TrackerClient>>();
	
	private static HashMap<String, Pool<StorageClient>> storagePoolMap=new HashMap<String, Pool<StorageClient>>();
	
	
	
	
	
	public static String getCharset() {
		return charset;
	}

	@Initializer
	public static synchronized void initialize() throws Exception {
		SAXReader reader = new SAXReader();  
		InputStream in = null;
		Document document= reader.read(in);
		Element rootElm = document.getRootElement();
		maxWait=Integer.valueOf(rootElm.attributeValue("maxWait"));
        maxActive=Integer.valueOf(rootElm.attributeValue("maxActive"));
        maxIdle=Integer.valueOf(rootElm.attributeValue("maxIdle"));
        minIdle=Integer.valueOf(rootElm.attributeValue("minIdle"));
        timeout=Integer.valueOf(rootElm.attributeValue("timeout"));
        charset=rootElm.attributeValue("charset");
        List<Element> trackerGroupElementList=rootElm.elements("tracker-group");        
        Element trackerGroupElement=trackerGroupElementList.get(0);
        List<Element> trackerServerElements= trackerGroupElement.elements("tracker-server");
        List<FastdfsNode> trackerNodes=new ArrayList<FastdfsNode>();
        for(int i=0;i<trackerServerElements.size();i++){
        	Element e=trackerServerElements.get(i);
        	FastdfsNode node=new FastdfsNode(e.attributeValue("ip"),Integer.parseInt(e.attributeValue("port")));
        	trackerNodes.add(node);
        }
        Element storageGroupElement = rootElm.element("storage-group");
        List<Element> storageGroupElements= storageGroupElement.elements("storage-server");
        List<FastdfsNode> storageNodes=new ArrayList<FastdfsNode>();
        for(Element e : storageGroupElements){
        	FastdfsNode node=new FastdfsNode(e.attributeValue("ip"),Integer.parseInt(e.attributeValue("port")));
        	storageNodes.add(node);
        }
        createFastdfsPool(trackerNodes,storageNodes);
        LOGGER.info(String.format("storagePoolMap : %s", storagePoolMap));
//		if(DebugUtil.isLocalDebug())
//		{
//			ConfigurationManager.setConfigurator(new LocalConfigurator());
//		}else
//		{
//			ConfigurationManager.setConfigurator(new ZKConfigurator());
//		}
//
//		ConfigurationManager.subscribeConfigUpdate(ConfigType.TEXT, "fastdfs", null);
//        ConfigurationManager.loadTextStream("fastdfs", null, new ConfigUpdateAction<InputStream>() {
//			
//			@Override
//			public void run(InputStream in) throws Exception {
//				SAXReader reader = new SAXReader();  
//				Document document= reader.read(in);
//				Element rootElm = document.getRootElement();
//				maxWait=Integer.valueOf(rootElm.attributeValue("maxWait"));
//		        maxActive=Integer.valueOf(rootElm.attributeValue("maxActive"));
//		        maxIdle=Integer.valueOf(rootElm.attributeValue("maxIdle"));
//		        minIdle=Integer.valueOf(rootElm.attributeValue("minIdle"));
//		        timeout=Integer.valueOf(rootElm.attributeValue("timeout"));
//		        charset=rootElm.attributeValue("charset");
//		        List<Element> trackerGroupElementList=rootElm.elements("tracker-group");        
//		        Element trackerGroupElement=trackerGroupElementList.get(0);
//		        List<Element> trackerServerElements= trackerGroupElement.elements("tracker-server");
//		        List<FastdfsNode> trackerNodes=new ArrayList<FastdfsNode>();
//		        for(int i=0;i<trackerServerElements.size();i++){
//		        	Element e=trackerServerElements.get(i);
//		        	FastdfsNode node=new FastdfsNode(e.attributeValue("ip"),Integer.parseInt(e.attributeValue("port")));
//		        	trackerNodes.add(node);
//		        }
//		        Element storageGroupElement = rootElm.element("storage-group");
//		        List<Element> storageGroupElements= storageGroupElement.elements("storage-server");
//		        List<FastdfsNode> storageNodes=new ArrayList<FastdfsNode>();
//		        for(Element e : storageGroupElements){
//		        	FastdfsNode node=new FastdfsNode(e.attributeValue("ip"),Integer.parseInt(e.attributeValue("port")));
//		        	storageNodes.add(node);
//		        }
//		        createFastdfsPool(trackerNodes,storageNodes);
//		        LOGGER.info(String.format("storagePoolMap : %s", storagePoolMap));
//			}
//		});
	}
	
	public static Pool<StorageClient> getStoragePool(String name){
		return storagePoolMap.get(name);
	}
	
	public static Pool<TrackerClient> getTrackerPool(String id){
		int keys=ObjectHelper.compatibleGetHashCode(id);
		keys = keys >= 0 ? keys : -keys;
        int r = (int) (keys % trackerPoolArrays.size());
		return trackerPoolArrays.get(r);
	}
	
	private static void createFastdfsPool(List<FastdfsNode> trackerNodes,List<FastdfsNode> storageNodes){
		for(int i=0;i<trackerNodes.size();i++){
			FastdfsNode node=trackerNodes.get(i);
			Pool<TrackerClient> pool=getTrackerPool(node);
			trackerPoolArrays.add(pool);
		}
		for(int i=0;i<storageNodes.size();i++){
			FastdfsNode node=storageNodes.get(i);
			Pool<StorageClient> pool=getStoragePool(node);
			storagePoolMap.put(node.getIp(), pool);
		}
	}
	
	private static Pool<TrackerClient> getTrackerPool(FastdfsNode node){
		String ip=node.getIp();
		int port=node.getPort();
		TrackerFactory trackerFactory=new TrackerFactory(ip,port,timeout);
		Config poolConfig =new Config();
		poolConfig.maxActive=maxActive;
		poolConfig.maxIdle=maxIdle;
		poolConfig.maxWait=maxWait;
		poolConfig.minIdle=minIdle;
		poolConfig.testOnBorrow=true;
		Pool<TrackerClient> trackerPool=new Pool<TrackerClient>(poolConfig,trackerFactory); 
		return trackerPool;
	}
	
	private static Pool<StorageClient> getStoragePool(FastdfsNode node){
		String ip=node.getIp();
		int port=node.getPort();
		StorageFactory storageFactory=new StorageFactory(ip,port,timeout);
		Config poolConfig =new Config();
		poolConfig.maxActive=maxActive;
		poolConfig.maxIdle=maxIdle;
		poolConfig.maxWait=maxWait;
		poolConfig.minIdle=minIdle;
		poolConfig.testOnBorrow=true;
		Pool<StorageClient>  storagePool=new Pool<StorageClient> (poolConfig,storageFactory); 
		return storagePool;
	}
	

}
