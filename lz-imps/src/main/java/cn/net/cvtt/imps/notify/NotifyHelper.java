package cn.net.cvtt.imps.notify;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.net.cvtt.imps.emchat.EasemobHandler;
import cn.net.cvtt.imps.notify.notifybody.NotifyBody;
import cn.net.cvtt.lian.common.util.Action;
import cn.net.cvtt.lian.common.util.obsoleted.LazyQueue;

public class NotifyHelper {
	private static Logger LOGGER = LoggerFactory.getLogger(NotifyHelper.class);
	private static final int RETRY_COUNT = 3;

	/**
	 * 环信的接口有每秒不超过30次的限制，因此在这用一个延迟队列，<br/>
	 * 如果发送失败，重试3次
	 */
	private static LazyQueue<NotifyBody> notifyQueue = new LazyQueue<NotifyBody>("SendEaseNotifyQueue", 30, 500, new Action<List<NotifyBody>>() {
		@Override
		public void run(List<NotifyBody> list) {
			for (NotifyBody body : list) {
				try {
					boolean sendSuccess = EasemobHandler.sendCmdMessage(body.getCommand(), body.getTargetType(), body.getTargets(), body.getExt());
					int retryCount = 0;
					if (!sendSuccess) {
						while (++retryCount < RETRY_COUNT) {
							sendSuccess = EasemobHandler.sendCmdMessage(body.getCommand(), body.getTargetType(), body.getTargets(), body.getExt());
							if (sendSuccess) {
								break;
							}
						}
					}
				} catch (Exception e) {
					LOGGER.error(String.format("NotifyHelper:Send cmd message error : %s", body.toString()), e);
				}
			}
		}
	});

	public static void sendNotify(NotifyBody body) {
		notifyQueue.enQueue(body);
	}

	/*
	 * public static void sendKickoffNotify(String easeUserName, String type, String message, String clientType, String clientVersion) throws UnionException { Map<String, String> ext = new HashMap<String, String>(); ext.put("type", type); ext.put("message", message); ext.put("clientType", clientType); ext.put("clientVersion", clientVersion);
	 * 
	 * sendNotify(new String[] { easeUserName }, NotifyType.CMD_KICK_OFF_NOTIFY.intValue(), ext); }
	 * 
	 * public static void sendLianCreationAuditNotify(String easeUserName, long creatorUserId, String creatorUserName, String workFlowType, int lianId, String lianName) throws UnionException { Map<String, String> ext = new HashMap<String, String>(); ext.put("creatorUserId", String.valueOf(creatorUserId)); ext.put("creatorUserName", creatorUserName); ext.put("workFlowType", workFlowType); ext.put("lianId", String.valueOf(lianId)); ext.put("lianName", lianName);
	 * 
	 * sendNotify(new String[] { easeUserName }, NotifyType.CMD_WORKFLOW_NOTIFY.intValue(), ext); }
	 * 
	 * public static void sendUserLianListVersionChangedNotify(String easeUserName, String type, long version) throws UnionException { Map<String, String> ext = new HashMap<String, String>(); ext.put("type", type); ext.put("version", String.valueOf(version));
	 * 
	 * sendNotify(new String[] { easeUserName }, NotifyType.CMD_VERSION_CHANGED_NOTIFY.intValue(), ext); }
	 * 
	 * public static void sendKickoffNotify(String[] easeUserNames, Map<String, String> ext) throws UnionException { sendNotify(easeUserNames, NotifyType.CMD_KICK_OFF_NOTIFY.intValue(), ext); }
	 * 
	 * public static void sendSingleUnionSecretNotify(String easeUserName, String msgConent) throws UnionException { Map<String, String> ext = new HashMap<String, String>(); ext.put("msgContent", msgConent); sendNotify(new String[] { easeUserName }, NotifyType.CMD_UNION_SECRET_NOTIFY.intValue(), ext); }
	 * 
	 * public static void sendMultiUnionSecretNotify(String[] easeUserNames, String msgConent) throws UnionException { Map<String, String> ext = new HashMap<String, String>(); ext.put("msgContent", msgConent); sendNotify(easeUserNames, NotifyType.CMD_UNION_SECRET_NOTIFY.intValue(), ext); }
	 * 
	 * // public static void sendUnionSecretNotify(String[] easeUserNames, Map<String, String> ext) throws UnionException { // sendNotify(easeUserNames, NotifyType.CMD_UNION_SECRET_NOTIFY.intValue(), ext); // }
	 * 
	 * public static void sendUnionSecretMessage(String[] easeUserNames, Map<String, String> ext) throws UnionException { sendNotify(easeUserNames, NotifyType.CMD_UNION_SECRET_MESSAGE.intValue(), ext); }
	 * 
	 * public static void sendNotify(String[] easeUserNames, int command, Map<String, String> ext) throws UnionException { sendNotify(easeUserNames, command, ext, false, false); }
	 * 
	 * public static void sendNotify(final String[] easeUserNames, final int command, final Map<String, String> ext, final boolean saveOffLine, boolean isSync) throws UnionException { if (isSync) { sendNotifySync(easeUserNames, command, ext, saveOffLine); } else { ThreadPoolUtil.getInstance().pop(new Runnable() {
	 * 
	 * @Override public void run() { try { sendNotifySync(easeUserNames, command, ext, saveOffLine); } catch (Exception t) { LOGGER.error(String.format("sendNotify failed, easeUserNames = %s, cmd = %s, ext = %s", easeUserNames, command, ext), t); } } }); } }
	 * 
	 * private static void sendNotifySync(String[] easeUserNames, int command, Map<String, String> ext, boolean saveOffLine) throws UnionException { EasemobHandler.sendCmdMessage(command, easeUserNames, ext); if (saveOffLine) {
	 * 
	 * } }
	 */
}
