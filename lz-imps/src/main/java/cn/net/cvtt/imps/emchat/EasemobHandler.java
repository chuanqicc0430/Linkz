package cn.net.cvtt.imps.emchat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import cn.net.cvtt.imps.emchat.api.AuthTokenAPI;
import cn.net.cvtt.imps.emchat.api.ChatGroupAPI;
import cn.net.cvtt.imps.emchat.api.IMUserAPI;
import cn.net.cvtt.imps.emchat.api.SendMessageAPI;
import cn.net.cvtt.imps.emchat.comm.ClientContext;
import cn.net.cvtt.imps.emchat.comm.EasemobRestAPIFactory;
import cn.net.cvtt.imps.emchat.comm.body.ChatGroupBody;
import cn.net.cvtt.imps.emchat.comm.body.CommandMessageBody;
import cn.net.cvtt.imps.emchat.comm.body.GroupOwnerTransferBody;
import cn.net.cvtt.imps.emchat.comm.body.IMUserBody;
import cn.net.cvtt.imps.emchat.comm.body.UserNamesBody;
import cn.net.cvtt.imps.emchat.comm.wrapper.BodyWrapper;
import cn.net.cvtt.imps.emchat.comm.wrapper.ResponseWrapper;
import cn.net.cvtt.imps.exception.LzCustomImpsException;
import cn.net.cvtt.imps.lianzi.enums.NotifyType;
import cn.net.cvtt.lian.common.initialization.InitialUtil;
import cn.net.cvtt.lian.common.initialization.Initializer;
import cn.net.cvtt.lian.common.util.StringUtils;
import cn.net.cvtt.resource.redis.RedisClient;
import cn.net.cvtt.resource.redis.RedisKey;
import cn.net.cvtt.resource.redis.RedisProxy;
import cn.net.cvtt.resource.route.ResourceFactory;

public class EasemobHandler {
	private static final Logger LOGGER = Logger.getLogger(EasemobHandler.class);

	private static EasemobRestAPIFactory factory = null;
	private static RedisProxy redisProxy = null;
	private static RedisClient redisClient;

	@Initializer
	public static void initialize() throws Exception {
		InitialUtil.init(ResourceFactory.class);
		factory = ClientContext.getInstance().init(ClientContext.INIT_FROM_PROPERTIES).getAPIFactory();
		redisProxy = ResourceFactory.getRedisProxy("USER");
		redisClient = redisProxy.getRedisClient(0);
		initEmchatAuthToken();
	}

	private static void initEmchatAuthToken() throws LzCustomImpsException {
		String emchatToken = null;
		try {
			emchatToken = redisClient.getStr(RedisKey.EMCHAT_AUTH_TOKEN_KEY);
		} catch (Exception e) {
			LOGGER.error(String.format("get authToken from redis error"), e);
		}
		if (StringUtils.isNullOrEmpty(emchatToken)) {
			try {
				AuthTokenAPI authService = (AuthTokenAPI) factory.newInstance(EasemobRestAPIFactory.TOKEN_CLASS);
				ResponseWrapper response = (ResponseWrapper) authService.getAuthToken(ClientContext.getInstance().getClientId(), ClientContext.getInstance().getClientSecret());
				if (response.hasError()) {
					LOGGER.error(String.format("Get authToken from emchat has error : %s", response.getMessages()));
					throw new LzCustomImpsException("GET_EASE_ACCESS_TOKEN_ERROR");
				}
				if (null != response && 200 == response.getResponseStatus() && null != response.getResponseBody()) {
					ObjectNode responseBody = (ObjectNode) response.getResponseBody();
					emchatToken = responseBody.get("access_token").asText();
					int newTokenExpire = responseBody.get("expires_in").asInt();
					redisClient.setex(RedisKey.EMCHAT_AUTH_TOKEN_KEY, newTokenExpire, emchatToken);
				} else {
					LOGGER.error(String.format("Get authToken from emchat error,code : %s,body : %s", response.getResponseStatus(), response.getResponseBody()));
					throw new LzCustomImpsException("GET_EASE_ACCESS_TOKEN_ERROR");
				}
			} catch (Exception ex) {
				LOGGER.error("invoke easemob service error",ex);
				throw new LzCustomImpsException("GET_EASE_ACCESS_TOKEN_ERROR", ex);
			}
		}
		factory.getContext().setAuthToken(emchatToken);
	}

	public static boolean sendCmdMessage(int cmd, String targetType, String[] targets, Map<String, String> ext) throws LzCustomImpsException {
		if (StringUtils.isNullOrEmpty(factory.getContext().getAuthToken())) {
			initEmchatAuthToken();
		}
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(String.format("Send cmd message,cmd:%s,targetType:%s,easeUserNames:%s,ext:%s", cmd, targetType, Arrays.toString(targets), ext));
		}
		try {
			SendMessageAPI messageService = (SendMessageAPI) factory.newInstance(EasemobRestAPIFactory.SEND_MESSAGE_CLASS);
			CommandMessageBody cmdMsg = new CommandMessageBody(targetType, targets, factory.getContext().getUnionSecretAccount(), ext, String.valueOf(cmd));
			ResponseWrapper wrapper = (ResponseWrapper) messageService.sendMessage(cmdMsg);
			if (wrapper.hasError()) {
				LOGGER.error(String.format("Send cmd message has error : %s", wrapper.getMessages()));
				return false;
			} else {
				if (null == wrapper || 200 != wrapper.getResponseStatus() || null == wrapper.getResponseBody()) {
					LOGGER.error(String.format("Send cmd message response error,response:%s", wrapper.toString()));
					return false;
				}
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info(String.format("Send cmd message success, response : %s", wrapper.toString()));
				}
			}
			return true;
		} catch (Exception ex) {
			LOGGER.error("invoke easemob service error",ex);
			throw new LzCustomImpsException("SEND_CMD_MESSAGE_ERROR", ex);
		}
	}

	public static void registerAccount(String easeUserName, String password, String name) throws LzCustomImpsException {
		if (StringUtils.isNullOrEmpty(factory.getContext().getAuthToken())) {
			initEmchatAuthToken();
		}
		// 环信授权注册
		try {
			IMUserAPI user = (IMUserAPI) factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
			BodyWrapper userBody = new IMUserBody(easeUserName, password, name);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(String.format("Create single ease user,easeUserName:%s,password:%s,name:%s", easeUserName, password, name));
			}
			ResponseWrapper wrapper = (ResponseWrapper) user.createNewIMUserSingle(userBody);
			if (wrapper.hasError()) {
				LOGGER.error(String.format("Create single ease user has error : %s", wrapper.getMessages()));
				throw new LzCustomImpsException("CREATE_EASE_USER_ERROR");
			} else {
				if (null == wrapper || 200 != wrapper.getResponseStatus() || null == wrapper.getResponseBody()) {
					LOGGER.error(String.format("Create single ease user response error,response:%s", wrapper.toString()));
					throw new LzCustomImpsException("CREATE_EASE_USER_ERROR");
				}
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info(String.format("Create single ease user success, response : %s", wrapper.toString()));
				}
			}
		} catch (Exception ex) {
			LOGGER.error("invoke easemob service error",ex);
			throw new LzCustomImpsException("CREATE_EASE_USER_ERROR", ex);
		}
	}

	public static String createNewChatGroup(String groupName, int maxUsers, String owner, String[] members) throws LzCustomImpsException {
		if (StringUtils.isNullOrEmpty(factory.getContext().getAuthToken())) {
			initEmchatAuthToken();
		}
		try {
			// 环信-创建群
			ChatGroupAPI chatGroup = (ChatGroupAPI) factory.newInstance(EasemobRestAPIFactory.CHATGROUP_CLASS);
			BodyWrapper charGroupBody = new ChatGroupBody(groupName, "Lianzi Group", false, (long) maxUsers, true, owner, members);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info(String.format("Create emchat group,groupName:%s,maxUsers:%s,owner:%s,members:%s", groupName, maxUsers, owner, Arrays.toString(members)));
			}
			ResponseWrapper response = (ResponseWrapper) chatGroup.createChatGroup(charGroupBody);
			if (response.hasError()) {
				LOGGER.error(String.format("Create emchat group has error : %s", response.getMessages()));
				throw new LzCustomImpsException("EMCHAT_CREATE_GROUP_ERROR");
			}
			if (null != response && 200 == response.getResponseStatus() && null != response.getResponseBody()) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info(String.format("Create emchat group success, response : %s", response.toString()));
				}
				ObjectNode responseBody = (ObjectNode) response.getResponseBody();
				JsonNode node = responseBody.findValue("data");
				return node.get("groupid").asText();
			} else {
				LOGGER.error(String.format("Create emchat group response error,response:%s", response.toString()));
				throw new LzCustomImpsException("EMCHAT_CREATE_GROUP_ERROR");
			}
		} catch (Exception ex) {
			LOGGER.error("invoke easemob service error",ex);
			throw new LzCustomImpsException("EMCHAT_CREATE_GROUP_ERROR", ex);
		}
	}

	public static void addSingleUserToChatGroup(String groupId, String member) throws LzCustomImpsException {
		if (StringUtils.isNullOrEmpty(factory.getContext().getAuthToken())) {
			initEmchatAuthToken();
		}
		// 环信-批量添加群成员
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(String.format("Add single user to emchat group,groupId:%s,member:%s", groupId, member));
		}
		try {
			ChatGroupAPI chatGroup = (ChatGroupAPI) factory.newInstance(EasemobRestAPIFactory.CHATGROUP_CLASS);
			ResponseWrapper response = (ResponseWrapper) chatGroup.addSingleUserToChatGroup(groupId, member);
			if (response.hasError()) {
				LOGGER.error(String.format("Add single user to emchat group has error : %s", response.getMessages()));
				throw new LzCustomImpsException("ADD_SINGLE_USER_TO_CHATGROUP_ERROR");
			}
			if (null != response && 200 == response.getResponseStatus() && null != response.getResponseBody()) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info(String.format("Add single user to emchat group success, response : %s", response.toString()));
				}
			} else {
				LOGGER.error(String.format("Add single user to emchat group response error,response:%s", response.toString()));
				throw new LzCustomImpsException("ADD_SINGLE_USER_TO_CHATGROUP_ERROR");
			}
		} catch (Exception ex) {
			LOGGER.error("invoke easemob service error",ex);
			throw new LzCustomImpsException("ADD_SINGLE_USER_TO_CHATGROUP_ERROR", ex);
		}
	}

	public static void addBatchUsersToChatGroup(String groupId, String[] members) throws LzCustomImpsException {
		if (StringUtils.isNullOrEmpty(factory.getContext().getAuthToken())) {
			initEmchatAuthToken();
		}
		// 环信-批量添加群成员
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(String.format("Add users to emchat group,groupId:%s,member:%s", groupId, Arrays.toString(members)));
		}
		try {
			ChatGroupAPI chatGroup = (ChatGroupAPI) factory.newInstance(EasemobRestAPIFactory.CHATGROUP_CLASS);
			BodyWrapper charGroupBody = new UserNamesBody(members);
			ResponseWrapper response = (ResponseWrapper) chatGroup.addBatchUsersToChatGroup(groupId, charGroupBody);
			if (response.hasError()) {
				LOGGER.error(String.format("Add users to emchat group has error : %s", response.getMessages()));
				throw new LzCustomImpsException("ADD_USERS_TO_CHATGROUP_ERROR");
			}
			if (null != response && 200 == response.getResponseStatus() && null != response.getResponseBody()) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info(String.format("Add users to emchat group success, response : %s", response.toString()));
				}
			} else {
				LOGGER.error(String.format("Add users to emchat group response error,response:%s", response.toString()));
				throw new LzCustomImpsException("ADD_USERS_TO_CHATGROUP_ERROR");
			}
		} catch (Exception ex) {
			LOGGER.error("invoke easemob service error",ex);
			throw new LzCustomImpsException("ADD_USERS_TO_CHATGROUP_ERROR", ex);
		}
	}

	public static void removeBatchUsersFromChatGroup(String groupId, String[] members) throws LzCustomImpsException {
		if (StringUtils.isNullOrEmpty(factory.getContext().getAuthToken())) {
			initEmchatAuthToken();
		}
		// 环信-批量删除群成员
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(String.format("Remove users from emchat group,groupId:%s,member:%s", groupId, Arrays.toString(members)));
		}
		try {
			ChatGroupAPI chatGroup = (ChatGroupAPI) factory.newInstance(EasemobRestAPIFactory.CHATGROUP_CLASS);
			ResponseWrapper response = (ResponseWrapper) chatGroup.removeBatchUsersFromChatGroup(groupId, members);
			if (response.hasError()) {
				LOGGER.error(String.format("Remove users from emchat group has error : %s", response.getMessages()));
				throw new LzCustomImpsException("REMOVE_USERS_FROM_CHATGROUP_ERROR");
			}
			if (null != response && 200 == response.getResponseStatus() && null != response.getResponseBody()) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info(String.format("Remove users from emchat group success, response : %s", response.toString()));
				}
			} else {
				LOGGER.error(String.format("Remove users from emchat group response error,response:%s", response.toString()));
				throw new LzCustomImpsException("REMOVE_USERS_FROM_CHATGROUP_ERROR");
			}
		} catch (Exception ex) {
			LOGGER.error("invoke easemob service error",ex);
			throw new LzCustomImpsException("REMOVE_USERS_FROM_CHATGROUP_ERROR", ex);
		}
	}

	public static void removeSingleUserFromChatGroup(String groupId, String member) throws LzCustomImpsException {
		if (StringUtils.isNullOrEmpty(factory.getContext().getAuthToken())) {
			initEmchatAuthToken();
		}
		// 环信-删除单个群成员
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(String.format("Remove single user from emchat group,groupId:%s,member:%s", groupId, member));
		}
		try {
			ChatGroupAPI chatGroup = (ChatGroupAPI) factory.newInstance(EasemobRestAPIFactory.CHATGROUP_CLASS);
			ResponseWrapper response = (ResponseWrapper) chatGroup.removeSingleUserFromChatGroup(groupId, member);
			if (response.hasError()) {
				LOGGER.error(String.format("Remove single user from emchat group has error : %s", response.getMessages()));
				throw new LzCustomImpsException("REMOVE_SINGLE_USER_FROM_CHATGROUP_ERROR");
			}
			if (null != response && 200 == response.getResponseStatus() && null != response.getResponseBody()) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info(String.format("Remove single user from emchat group success, response : %s", response.toString()));
				}
			} else {
				LOGGER.error(String.format("Remove single user from emchat group response error,response:%s", response.toString()));
				throw new LzCustomImpsException("REMOVE_SINGLE_USER_FROM_CHATGROUP_ERROR");
			}
		} catch (Exception ex) {
			LOGGER.error("invoke easemob service error",ex);
			throw new LzCustomImpsException("REMOVE_SINGLE_USER_FROM_CHATGROUP_ERROR", ex);
		}
	}

	public static void transferChatGroup(String groupId, String newGroupOwner) throws LzCustomImpsException {
		if (StringUtils.isNullOrEmpty(factory.getContext().getAuthToken())) {
			initEmchatAuthToken();
		}
		// 环信-转让群组
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(String.format("Transfer chat group,groupId:%s,newGroupOwner:%s", groupId, newGroupOwner));
		}
		try {
			ChatGroupAPI chatGroup = (ChatGroupAPI) factory.newInstance(EasemobRestAPIFactory.CHATGROUP_CLASS);
			BodyWrapper charGroupBody = new GroupOwnerTransferBody(newGroupOwner);
			ResponseWrapper response = (ResponseWrapper) chatGroup.transferChatGroupOwner(groupId, charGroupBody);
			if (response.hasError()) {
				LOGGER.error(String.format("Transfer chat group has error : %s", response.getMessages()));
				throw new LzCustomImpsException("TRANSFER_CHATGROUP_ERROR");
			}
			if (null != response && 200 == response.getResponseStatus() && null != response.getResponseBody()) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info(String.format("Transfer chat group, response : %s", response.toString()));
				}
				ObjectNode responseBody = (ObjectNode) response.getResponseBody();
				JsonNode node = responseBody.findValue("data");
				boolean isTransferOk = node.get("newowner").asBoolean();
				if (!isTransferOk) {
					throw new LzCustomImpsException("TRANSFER_CHATGROUP_ERROR");
				}
			} else {
				LOGGER.error(String.format("Transfer chat group response error,response:%s", response.toString()));
				throw new LzCustomImpsException("TRANSFER_CHATGROUP_ERROR");
			}
		} catch (Exception ex) {
			LOGGER.error("invoke easemob service error",ex);
			throw new LzCustomImpsException("TRANSFER_CHATGROUP_ERROR", ex);
		}
	}

	public static void deleteChatGroup(String groupId) throws LzCustomImpsException {
		if (StringUtils.isNullOrEmpty(factory.getContext().getAuthToken())) {
			initEmchatAuthToken();
		}
		// 环信-删除群组
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(String.format("Delete chat group,groupId:%s", groupId));
		}
		try {
			ChatGroupAPI chatGroup = (ChatGroupAPI) factory.newInstance(EasemobRestAPIFactory.CHATGROUP_CLASS);
			ResponseWrapper response = (ResponseWrapper) chatGroup.deleteChatGroup(groupId);
			if (response.hasError()) {
				LOGGER.error(String.format("Delete chat group has error : %s", response.getMessages()));
				throw new LzCustomImpsException("DELETE_CHATGROUP_ERROR");
			}
			if (null != response && 200 == response.getResponseStatus() && null != response.getResponseBody()) {
				if (LOGGER.isInfoEnabled()) {
					LOGGER.info(String.format("Delete chat group, response : %s", response.toString()));
				}
				ObjectNode responseBody = (ObjectNode) response.getResponseBody();
				JsonNode node = responseBody.findValue("data");
				boolean isDeleteOk = node.get("success").asBoolean();
				if (!isDeleteOk) {
					throw new LzCustomImpsException("DELETE_CHATGROUP_ERROR");
				}
			} else {
				LOGGER.error(String.format("Delete chat group response error,response:%s", response.toString()));
				throw new LzCustomImpsException("DELETE_CHATGROUP_ERROR");
			}
		} catch (Exception ex) {
			LOGGER.error("invoke easemob service error",ex);
			throw new LzCustomImpsException("DELETE_CHATGROUP_ERROR", ex);
		}
	}

	public static void main(String[] args) throws Exception {
		InitialUtil.init(ResourceFactory.class, EasemobHandler.class);

		// IMUserAPI user = (IMUserAPI) factory.newInstance(EasemobRestAPIFactory.USER_CLASS);
		// String cursor = "";
		// do {
		// ResponseWrapper wrapper = (ResponseWrapper) user.getIMUsersBatch(10L, cursor);
		// if (null != wrapper && 200 == wrapper.getResponseStatus() && null != wrapper.getResponseBody()) {
		// if (LOGGER.isInfoEnabled()) {
		// LOGGER.info(String.format("getIMUsersBatch success, response : %s", wrapper.toString()));
		// }
		// ObjectNode responseBody = (ObjectNode) wrapper.getResponseBody();
		// JsonNode cursorNode = responseBody.findValue("cursor");
		// cursor = cursorNode.asText();
		//
		// JsonNode entitiesNode = responseBody.findValue("entities");
		// Iterator<JsonNode> nodeItr = entitiesNode.elements();
		// while (nodeItr.hasNext()) {
		// JsonNode j = nodeItr.next();
		// user.deleteIMUserByUserName(j.get("username").asText());
		// }
		// }
		// } while (!StringUtils.isNullOrEmpty(cursor));

		// while (true) {
		// Map<String, String> extMap = new HashMap<String, String>();
		// extMap.put("lianId", "13122002");
		// extMap.put("invitationId", "2");
		// extMap.put("invitedOrgId", "10005");
		// extMap.put("tips", "您收到一份来自【组织简称】的邀请函。");
		// extMap.put("linkWord", "赶快打开看看吧");
		// EasemobHandler.sendCmdMessage(NotifyType.CMD_LIANJOIN_INVITATION_NOTIFY.intValue(), "users", new String[] { "C2dX8i0E99R8mN9", "8Sni9bu4q6SFcJG" }, extMap);
		//
		// Map<String, String> extMap1 = new HashMap<String, String>();
		// extMap1.put("tips", "【XXXXX】拒绝了您的邀请。");
		// EasemobHandler.sendCmdMessage(NotifyType.CMD_INVITATION_REJECTED_NOTIFY.intValue(), "users", new String[] { "C2dX8i0E99R8mN9", "8Sni9bu4q6SFcJG" }, extMap1);
		//
		// Map<String, String> extMap2 = new HashMap<String, String>();
		// extMap2.put("lianId", "13122002");
		// extMap2.put("applyId", "2");
		// extMap2.put("tips", "XXXX申请加入您管理的XXXX会员联。");
		// extMap2.put("linkWord", "赶快处理一下吧");
		// EasemobHandler.sendCmdMessage(NotifyType.CMD_JOIN_LIAN_APPLY_NOTIFY.intValue(), "users", new String[] { "C2dX8i0E99R8mN9", "8Sni9bu4q6SFcJG" }, extMap2);
		//
		// Map<String, String> extMap3 = new HashMap<String, String>();
		// extMap3.put("tips", "【XXXX】的XXXXX联拒绝了您的申请加入。");
		// EasemobHandler.sendCmdMessage(NotifyType.CMD_JOIN_LIAN_APPLY_REJECTED_NOTIFY.intValue(), "users", new String[] { "C2dX8i0E99R8mN9", "8Sni9bu4q6SFcJG" }, extMap3);
		//
		// Map<String, String> extMap4 = new HashMap<String, String>();
		// extMap4.put("tips", "您的组织【XXXX】已被移出【XXXX|XXXXX】。");
		// EasemobHandler.sendCmdMessage(NotifyType.CMD_LIAN_JOIN_ORG_REMOVED_NOTIFY.intValue(), "users", new String[] { "C2dX8i0E99R8mN9", "8Sni9bu4q6SFcJG" }, extMap4);
		// Thread.sleep(60 * 1000);
		// }

		while (true) {
			Map<String, String> extMap = new HashMap<String, String>();
			extMap.put("orgId", String.valueOf((int) (1 + Math.random() * (100000 - 1 + 1))));
			extMap.put("orgShortName", getRandomString(5));
			extMap.put("orgType", "C");
			extMap.put("signName", getRandomString(5));
			extMap.put("signId", String.valueOf((int) (1 + Math.random() * (100000000 - 1 + 1))));
			extMap.put("note", "联子1.0上线发布");
			EasemobHandler.sendCmdMessage(NotifyType.CMD_NET_VIPLIAN_BROADCAST_NOTIFY.intValue(), "users", new String[] { "P973BNSB55H8v9J" }, extMap);
			EasemobHandler.sendCmdMessage(NotifyType.CMD_NET_DOWNSTREAM_BROADCAST_NOTIFY.intValue(), "users", new String[] { "P973BNSB55H8v9J" }, extMap);
			EasemobHandler.sendCmdMessage(NotifyType.CMD_NET_PEER_BROADCAST_NOTIFY.intValue(), "users", new String[] { "P973BNSB55H8v9J"}, extMap);
			EasemobHandler.sendCmdMessage(NotifyType.CMD_NET_UPSTREAM_BROADCAST_NOTIFY.intValue(), "users", new String[] { "P973BNSB55H8v9J" }, extMap);
			
			extMap.put("signName", getRandomString(5));
			extMap.put("signId", String.valueOf(432545));
			extMap.put("note", "再传捷报：中国食品工业协会豆制品专业委员会签约创始用户");
			EasemobHandler.sendCmdMessage(NotifyType.CMD_NET_LIANNET_NOTIFY.intValue(), "users", new String[] { "P973BNSB55H8v9J"}, extMap);
			Thread.sleep(1000*60);
		}

		/**
		 * 联网站动态
		 */
		// Map<String, String> extMap = new HashMap<String, String>();
		// extMap.put("netContent", "联子1.0上线发布");
		// extMap.put("orgId", String.valueOf(13219));
		// extMap.put("orgShortName", "联子科技");
		// extMap.put("orgType", "C");
		// extMap.put("orgPortrait ", "291ec42ba0aa544192efbcb88e90b20c");
		// EasemobHandler.sendCmdMessage(NotifyType.CMD_NET_LIANNET_NOTIFY.intValue(), "users", new String[] { "p48H51ruL542950", "k07x1dR42L3e12D" }, extMap);
		// TipsNotifyBody body = new TipsNotifyBody();
		// body.setCommand(NotifyType.CMD_REGISTER_NOTIFY.intValue());
		// body.setTargets(new String[] { "p48H51ruL542950" });
		// body.setTips(String.format("终于等到您（联号：%s）了！\r\n我是您的贴身秘书小联。\r\n从此，由我伴您左右，共度联时光。\r\n小联的职责就是为您服务，除做好基本的告知、提醒、转达、督办外，小联会努力了解您的习惯、爱好，做到更懂您、更贴心。小联真心希望能帮上您！\r\n您有何需要帮助的，可以随时到“阅办”或“我的”找我吧。\r\n您对小联服务有什么不满意的，就直接“点”我告知，我会第一时间调整。适应您是我的本分。\r\n小联只有一个心愿：您因联子而改变，联子有您更精彩。", 10116));
		// NotifyHelper.sendNotify(body);
		/**
		 * 会员联报
		 */
		// Map<String, String> extMap = new HashMap<String, String>();
		// extMap.put("title", "再传捷报：中国食品工业协会豆制品专业委员会签约创始用户");
		// extMap.put("orgId", String.valueOf(13219));
		// extMap.put("orgShortName", "联子科技");
		// extMap.put("orgType", "C");
		// extMap.put("orgPortrait ", "fda60a913a9dc45c75d5ca83e8287d79");
		// extMap.put("lianName", "客户管理");
		// extMap.put("lianId", String.valueOf(13219012));
		// EasemobHandler.sendCmdMessage(NotifyType.CMD_NET_VIPLIAN_BROADCAST_NOTIFY.intValue(), "users", new String[] { "p48H51ruL542950", "k07x1dR42L3e12D" }, extMap);

		/**
		 * 上游联报
		 */
		// Map<String, String> extMap = new HashMap<String, String>();
		// extMap.put("title", "你若盛开，清风自来");
		// extMap.put("orgId", String.valueOf(13217));
		// extMap.put("orgShortName", "赛维智库");
		// extMap.put("orgType", "C");
		// extMap.put("orgPortrait ", "291ec42ba0aa544192efbcb88e90b20c");
		// extMap.put("industryName", "文化艺术业");
		// extMap.put("industry", "R87XX000Z");
		// EasemobHandler.sendCmdMessage(NotifyType.CMD_NET_UPSTREAM_BROADCAST_NOTIFY.intValue(), "users", new String[] { "p48H51ruL542950", "k07x1dR42L3e12D" }, extMap);

		/**
		 * 下游联报
		 */
		// Map<String, String> extMap = new HashMap<String, String>();
		// extMap.put("title", "联子1.0上线发布");
		// extMap.put("orgId", String.valueOf(13219));
		// extMap.put("orgShortName", "联子科技");
		// extMap.put("orgType", "C");
		// extMap.put("orgPortrait ", "fda60a913a9dc45c75d5ca83e8287d79");
		// extMap.put("industryName", "互联网和相关服务");
		// extMap.put("industry", "I64XX000B");
		// EasemobHandler.sendCmdMessage(NotifyType.CMD_NET_DOWNSTREAM_BROADCAST_NOTIFY.intValue(), "users", new String[] { "p48H51ruL542950", "k07x1dR42L3e12D" }, extMap);

		/**
		 * 同行联报
		 */
		// Map<String, String> extMap = new HashMap<String, String>();
		// extMap.put("title", "第十届中国中小企业节筹委会主任办公会在深圳市召开");
		// extMap.put("orgId", String.valueOf(13200));
		// extMap.put("orgShortName", "联子科技");
		// extMap.put("orgType", "C");
		// extMap.put("orgPortrait ", "fda60a913a9dc45c75d5ca83e8287d79");
		// extMap.put("industryName", "社会保障");
		// extMap.put("industry", "S93XX000E");
		// EasemobHandler.sendCmdMessage(NotifyType.CMD_NET_PEER_BROADCAST_NOTIFY.intValue(), "users", new String[] { "p48H51ruL542950", "k07x1dR42L3e12D" }, extMap);
	}

	public static String getRandomString(int length) {
		String str = "我是会员联你是谁你从哪里来要到哪里去";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(str.length());
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}

}
