package cn.net.cvtt.imps.notify.notifybody;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.net.cvtt.imps.lianzi.enums.NotifyButtonTips;
import cn.net.cvtt.imps.lianzi.enums.NotifyButtonType;
import cn.net.cvtt.lian.common.util.StringUtils;

public class RobotLianNotifyBody extends NotifyBody {
	private static final Logger LOGGER = Logger.getLogger(RobotLianNotifyBody.class);

	private long orgId;
	private long lianId;
	private String orgShortName;
	private String portrait;
	private int portraitType;
	private String name;
	private String note;
	private int category;
	private String buttonTips;
	private int buttonType;
	private String forwardUrl;
	private Map<String, Object> variable;

	public RobotLianNotifyBody() {

	}

	public RobotLianNotifyBody(int cmd, String[] targets, long orgId, long lianId, String orgShortName, String name, int category, int buttonType) {
		super(cmd, targets);
		this.orgId = orgId;
		this.lianId = lianId;
		this.buttonType = buttonType;
		this.orgShortName = orgShortName;
		this.name = name;
		this.category = category;
		if (buttonType == NotifyButtonType.BUTTON_TIP.intValue()) {
			this.buttonTips = NotifyButtonTips.TIP_BUTTON_TIP.strValue();
		} else if (buttonType == NotifyButtonType.BUTTON_DISPOSE.intValue()) {
			this.buttonTips = NotifyButtonTips.TIP_BUTTON_DISPOSE.strValue();
		} else if (buttonType == NotifyButtonType.BUTTON_ENTER.intValue()) {
			this.buttonTips = NotifyButtonTips.TIP_BUTTON_ENTER.strValue();
		}
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public long getLianId() {
		return lianId;
	}

	public void setLianId(long lianId) {
		this.lianId = lianId;
	}

	public String getOrgShortName() {
		return orgShortName;
	}

	public void setOrgShortName(String orgShortName) {
		this.orgShortName = orgShortName;
	}

	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	public int getPortraitType() {
		return portraitType;
	}

	public void setPortraitType(int portraitType) {
		this.portraitType = portraitType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	/**
	 * @return the category
	 */
	public int getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(int category) {
		this.category = category;
	}

	public String getButtonTips() {
		return buttonTips;
	}

	public void setButtonTips(String buttonTips) {
		this.buttonTips = buttonTips;
	}

	/**
	 * @return the buttonType
	 */
	public int getButtonType() {
		return buttonType;
	}

	/**
	 * @param buttonType
	 *            the buttonType to set
	 */
	public void setButtonType(int buttonType) {
		this.buttonType = buttonType;
	}

	public String getForwardUrl() {
		return forwardUrl;
	}

	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}

	public Map<String, Object> getVariable() {
		return variable;
	}

	public void setVariable(Map<String, Object> variable) {
		this.variable = variable;
	}

	@Override
	public void putExtMap() {
		Map<String, String> ext = new HashMap<>();
		if (orgId != 0) {
			ext.put("orgId", String.valueOf(orgId));
		}
		if (lianId != 0) {
			ext.put("lianId", String.valueOf(lianId));
		}
		if (!StringUtils.isNullOrEmpty(orgShortName)) {
			ext.put("orgShortName", orgShortName);
		}
		if (!StringUtils.isNullOrEmpty(portrait)) {
			ext.put("portrait", portrait);
			ext.put("portraitType", String.valueOf(portraitType));
		}
		ext.put("name", name);
		if (!StringUtils.isNullOrEmpty(note)) {
			ext.put("note", note);
		}
		if (category != 0) {
			ext.put("category", String.valueOf(category));
		}
		if (buttonType != 0) {
			ext.put("buttonType", String.valueOf(buttonType));
		}
		if (!StringUtils.isNullOrEmpty(buttonTips)) {
			ext.put("buttonTips", buttonTips);
		}
		if (!StringUtils.isNullOrEmpty(forwardUrl)) {
			ext.put("forwardUrl", forwardUrl);
		}
		if (variable != null && !variable.isEmpty()) {
			try {
				ext.put("variable", new ObjectMapper().writeValueAsString(variable));
			} catch (JsonProcessingException e) {
				LOGGER.error(String.format("parse variable error,variable : %s", variable), e);
			}
		}
		setExt(ext);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NotifyBody [orgId=");
		builder.append(orgId);
		builder.append(", lianId=");
		builder.append(lianId);
		builder.append(", orgShortName=");
		builder.append(orgShortName);
		builder.append(", portrait=");
		builder.append(portrait);
		builder.append(", portraitType=");
		builder.append(portraitType);
		builder.append(", name=");
		builder.append(name);
		builder.append(", note=");
		builder.append(note);
		builder.append(", category=");
		builder.append(category);
		builder.append(", buttonTips=");
		builder.append(buttonTips);
		builder.append(", buttonType=");
		builder.append(buttonType);
		builder.append(", forwardUrl=");
		builder.append(forwardUrl);
		builder.append(", variable=");
		builder.append(variable);
		builder.append("]");
		return builder.toString();
	}

}
