package cn.net.cvtt.imps.emchat.comm.body;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.node.ContainerNode;

import cn.net.cvtt.imps.emchat.comm.constant.MsgType;

public class TextMessageBody extends MessageBody {
	private String msg;

	public TextMessageBody(String targetType, String[] targets, String from, Map<String, String> ext, String msg) {
		super(targetType, targets, from, ext);
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

    public ContainerNode<?> getBody() {
        if(!isInit()){
            this.getMsgBody().put("type", MsgType.TEXT);
            this.getMsgBody().put("msg", msg);
            this.setInit(true);
        }

        return this.getMsgBody();
    }

    public Boolean validate() {
		return super.validate() && StringUtils.isNotBlank(msg);
	}
}
