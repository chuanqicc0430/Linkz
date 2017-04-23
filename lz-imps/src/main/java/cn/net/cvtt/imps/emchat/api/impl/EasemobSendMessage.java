package cn.net.cvtt.imps.emchat.api.impl;

import cn.net.cvtt.imps.emchat.api.EasemobRestAPI;
import cn.net.cvtt.imps.emchat.api.SendMessageAPI;
import cn.net.cvtt.imps.emchat.comm.constant.HTTPMethod;
import cn.net.cvtt.imps.emchat.comm.helper.HeaderHelper;
import cn.net.cvtt.imps.emchat.comm.wrapper.BodyWrapper;
import cn.net.cvtt.imps.emchat.comm.wrapper.HeaderWrapper;

public class EasemobSendMessage extends EasemobRestAPI implements SendMessageAPI {
    private static final String ROOT_URI = "/messages";

    @Override
    public String getResourceRootURI() {
        return ROOT_URI;
    }

    public Object sendMessage(Object payload) {
        String  url = getContext().getSeriveURL() + getResourceRootURI();
        HeaderWrapper header = HeaderHelper.getDefaultHeaderWithToken();
        BodyWrapper body = (BodyWrapper) payload;

        return getInvoker().sendRequest(HTTPMethod.METHOD_POST, url, header, body, null);
    }
}
