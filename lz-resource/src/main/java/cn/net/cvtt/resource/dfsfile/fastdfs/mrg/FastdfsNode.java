package cn.net.cvtt.resource.dfsfile.fastdfs.mrg;

public class FastdfsNode {
	
	private String ip;
	
	private int port;
	
	public FastdfsNode(String ip,int port) {
		this.ip=ip;
		this.port=port;
	}

	public String getIp() {
		return ip;
	}

	public int getPort() {
		return port;
	}
	
	

}
