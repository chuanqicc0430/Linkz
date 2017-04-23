package cn.net.cvtt.imps.emchat.api;

import java.io.File;

import cn.net.cvtt.imps.emchat.comm.wrapper.BodyWrapper;
import cn.net.cvtt.imps.emchat.comm.wrapper.HeaderWrapper;
import cn.net.cvtt.imps.emchat.comm.wrapper.QueryWrapper;
import cn.net.cvtt.imps.emchat.comm.wrapper.ResponseWrapper;

public interface RestAPIInvoker {
	ResponseWrapper sendRequest(String method, String url, HeaderWrapper header, BodyWrapper body, QueryWrapper query);
	ResponseWrapper uploadFile(String url, HeaderWrapper header, File file);
    ResponseWrapper downloadFile(String url, HeaderWrapper header, QueryWrapper query);
}
