package cn.net.cvtt.imps.lianzi.config.tables;

import cn.net.cvtt.configuration.ConfigTableField;
import cn.net.cvtt.configuration.ConfigTableItem;

public class CFG_AutoJob extends ConfigTableItem{
	@ConfigTableField(value = "jobName", isKeyField = true)
	private String jobName;//'任务名称',
	@ConfigTableField("cronExpression")
	private String cronExpression;//'cron表达式',
	@ConfigTableField("time")
	private String time;//'执行时间',
	
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return String.format(
				"CFG_AutoJob [jobName=%s, cronExpression=%s, time=%s]",
				jobName, cronExpression, time);
	}
}
