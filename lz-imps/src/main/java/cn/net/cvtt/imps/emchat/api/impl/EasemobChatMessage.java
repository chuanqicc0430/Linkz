package cn.net.cvtt.imps.emchat.api.impl;

import cn.net.cvtt.imps.emchat.api.ChatMessageAPI;
import cn.net.cvtt.imps.emchat.api.EasemobRestAPI;
import cn.net.cvtt.imps.emchat.comm.constant.HTTPMethod;
import cn.net.cvtt.imps.emchat.comm.helper.HeaderHelper;
import cn.net.cvtt.imps.emchat.comm.wrapper.HeaderWrapper;
import cn.net.cvtt.imps.emchat.comm.wrapper.QueryWrapper;

public class EasemobChatMessage extends EasemobRestAPI implements ChatMessageAPI {

    private static final String ROOT_URI = "chatmessages";

    public Object exportChatMessages(Long limit, String cursor, String query) {
        String url = getContext().getSeriveURL() + getResourceRootURI();
        HeaderWrapper header = HeaderHelper.getDefaultHeaderWithToken();
        QueryWrapper queryWrapper = QueryWrapper.newInstance().addLimit(limit).addCursor(cursor).addQueryLang(query);

        return getInvoker().sendRequest(HTTPMethod.METHOD_DELETE, url, header, null, queryWrapper);
    }

    @Override
    public String getResourceRootURI() {
        return ROOT_URI;
    }
}
