package cn.net.cvtt.imps.emchat.api.impl;

import java.io.File;

import cn.net.cvtt.imps.emchat.api.EasemobRestAPI;
import cn.net.cvtt.imps.emchat.api.FileAPI;
import cn.net.cvtt.imps.emchat.comm.helper.HeaderHelper;
import cn.net.cvtt.imps.emchat.comm.wrapper.HeaderWrapper;

public class EasemobFile extends EasemobRestAPI implements FileAPI {
    private static final String ROOT_URI = "/chatfiles";

    @Override
    public String getResourceRootURI() {
        return ROOT_URI;
    }

    public Object uploadFile(Object file) {
        String url = getContext().getSeriveURL() + getResourceRootURI();
        HeaderWrapper header = HeaderHelper.getUploadHeaderWithToken();

        return getInvoker().uploadFile(url, header, (File) file);
    }

    public Object downloadFile(String fileUUID, String shareSecret, Boolean isThumbnail) {
        String url = getContext().getSeriveURL() + getResourceRootURI() + "/" + fileUUID;
        HeaderWrapper header = HeaderHelper.getDownloadHeaderWithToken(shareSecret, isThumbnail);

        return getInvoker().downloadFile(url, header, null);
    }
}
