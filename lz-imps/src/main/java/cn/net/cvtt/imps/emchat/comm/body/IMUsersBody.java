package cn.net.cvtt.imps.emchat.comm.body;

import java.util.List;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import cn.net.cvtt.imps.emchat.comm.wrapper.BodyWrapper;

public class IMUsersBody implements BodyWrapper {
	
	private List<IMUserBody> users;
	
	public IMUsersBody(List<IMUserBody> users) {
		super();
		this.users = users;
	}
	
	public ContainerNode<?> getBody() {
		ArrayNode root = JsonNodeFactory.instance.arrayNode();
		
		for( IMUserBody user : users ) {
			root.add(user.getBody());
		}
		
		return root;
	}

	public Boolean validate() {
		if( null == users || users.isEmpty() ) {
			return Boolean.FALSE;
		}
		
		for( IMUserBody user : users ) {
			if( null == user || !user.validate() ) {
				return Boolean.FALSE;
			}
		}
		
		return Boolean.TRUE;
	}

}
