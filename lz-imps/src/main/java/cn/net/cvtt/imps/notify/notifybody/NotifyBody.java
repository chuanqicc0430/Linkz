package cn.net.cvtt.imps.notify.notifybody;

import java.util.Arrays;
import java.util.Map;

public abstract class NotifyBody {
	private int command;
	private String targetType = "users";
	private String[] targets;
	private Map<String, String> ext;
	
	public NotifyBody(){
		
	}

	public NotifyBody(int command, String[] targets) {
		super();
		this.command = command;
		this.targets = targets;
	}

	public abstract void putExtMap();

	public int getCommand() {
		return command;
	}

	public void setCommand(int command) {
		this.command = command;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public String[] getTargets() {
		return targets;
	}

	public void setTargets(String target) {
		this.targets = new String[] { target };
	}

	public void setTargets(String[] targets) {
		this.targets = targets;
	}

	public Map<String, String> getExt() {
		putExtMap();
		return ext;
	}

	public void setExt(Map<String, String> ext) {
		this.ext = ext;
	}

	@Override
	public String toString() {
		return String.format("NotifyBody [command=%s, targetType=%s, targets=%s, ext=%s]", command, targetType, Arrays.toString(targets), getExt());
	}

}
