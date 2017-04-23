package cn.net.cvtt.imps.emchat.comm.body;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.node.ContainerNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import cn.net.cvtt.imps.emchat.comm.wrapper.BodyWrapper;

public class GroupOwnerTransferBody implements BodyWrapper {
    private String newOwner;

    public GroupOwnerTransferBody(String newOwner) {
        this.newOwner = newOwner;
    }

    public String getNewOwner() {
        return newOwner;
    }

    public ContainerNode<?> getBody() {
        return JsonNodeFactory.instance.objectNode().put("newowner", newOwner);
    }

    public Boolean validate() {
        return StringUtils.isNotBlank(newOwner);
    }
}
