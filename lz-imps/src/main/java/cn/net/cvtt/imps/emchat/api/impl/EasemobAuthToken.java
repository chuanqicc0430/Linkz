package cn.net.cvtt.imps.emchat.api.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.imps.emchat.api.AuthTokenAPI;
import cn.net.cvtt.imps.emchat.api.EasemobRestAPI;
import cn.net.cvtt.imps.emchat.comm.body.AuthTokenBody;
import cn.net.cvtt.imps.emchat.comm.constant.HTTPMethod;
import cn.net.cvtt.imps.emchat.comm.helper.HeaderHelper;
import cn.net.cvtt.imps.emchat.comm.wrapper.BodyWrapper;
import cn.net.cvtt.imps.emchat.comm.wrapper.HeaderWrapper;

public class EasemobAuthToken extends EasemobRestAPI implements AuthTokenAPI{
	
	public static final String ROOT_URI = "/token";
	
	private static final Logger log = LoggerFactory.getLogger(EasemobAuthToken.class);
	
	@Override
	public String getResourceRootURI() {
		return ROOT_URI;
	}

	public Object getAuthToken(String clientId, String clientSecret) {
		String url = getContext().getSeriveURL() + getResourceRootURI();
		BodyWrapper body = new AuthTokenBody(clientId, clientSecret);
		HeaderWrapper header = HeaderHelper.getDefaultHeader();
		
		return getInvoker().sendRequest(HTTPMethod.METHOD_POST, url, header, body, null);
	}
}
