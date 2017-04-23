package cn.net.cvtt.imps.emchat.comm.body;


import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.node.ContainerNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import cn.net.cvtt.imps.emchat.comm.wrapper.BodyWrapper;

public class ResetPasswordBody implements BodyWrapper{
    private String newPassword;

    public ResetPasswordBody(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public ContainerNode<?> getBody() {
        return JsonNodeFactory.instance.objectNode().put("newpassword", newPassword);
    }

    public Boolean validate() {
        return StringUtils.isNotBlank(newPassword);
    }
}
