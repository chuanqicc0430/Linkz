package cn.net.cvtt.resource.redis;

public class RedisKey {
	
	private  final static String MSGMEDIA_KEY="msgmedia::%s";
	
	/**
	 * 短信验证码key
	 */
	public final static String SMSCODE_KEY = "sms::%s";
	/**
	 * userId生成key
	 */
	public final static String USERID_INCR_KEY = "uid_incr";
	
	/**
	 * 联成员memberId生成key
	 */
	public final static String MEMBERID_INCR_KEY = "memberid_incr";
	/**
	 * 环信token保存key
	 */
	public final static String EMCHAT_AUTH_TOKEN_KEY = "ec_auth_token";
	/**
	 * 在线缓存key
	 */
	public final static String SESSION_KEY = "session::%s";
	/**
	 * 在线缓存_登录点ID field
	 */
	public final static String SESSION_ENDPOINT_FIELD = "endpointId";
	/**
	 * 在线缓存_登录点ID field
	 */
	public final static String SESSION_KEEPALIVETIME_FIELD = "keepAliveTime";
	/**
	 * 在线缓存_登录凭证 field
	 */
	public final static String SESSION_CREDENTIAL_FIELD = "credential";
	/**
	 * 在线缓存_机器设备码 field
	 */
	public final static String SESSION_MACHINECODE_FIELD = "machineCode";
	/**
	 * 在线用户列表
	 */
	public final static String ONLINE_LIST_KEY = "online_list";
	/**
	 * 自动登录的credential
	 */
//	public final static String AUTO_LOGIN_TOKEN_KEY = "alt::%s";
	/**
	 * 自动登录的credential
	 */
	public final static String REFRESH_TOKEN_KEY = "rtoken::%s";
	/**
	 * 用户基础信息
	 */
//	public final static String BASIC_USERINFO_KEY = "bu::%s";
	/**
	 * 用户扩展信息
	 */
//	public final static String EXT_USERINFO_KEY = "eu::%s";
	
	/**
	 * 用户信息
	 */
	public final static String USERINFO_KEY = "userinfo::%s";
	/**
	 * 手机号与userId的对应关系
	 */
	public final static String MOBILE_TO_USERID_KEY = "m2u::%s";
	
	/**
	 * 版本号key
	 */
	public final static String VERSION_KEY = "v::%s";
	/**
	 * 用户信息版本号 field
	 */
	public final static String VERSION_USERINFO_FIELD = "profile";
	/**
	 * 用户的联列表版本号 field
	 */
	public final static String VERSION_USERLIANLIST_FIELD = "userlianlist";
	/**
	 * 组织信息版本号 field
	 */
	public final static String VERSION_ORGINFO_FIELD = "orginfo";
	/**
	 * 组织成员列表版本号 field
	 */
	public final static String VERSION_ORG_ALONE_MEMBERS_FIELD = "orgalonemembers";
	/**
	 * 联信息版本号 field
	 */
	public final static String VERSION_LIANINFO_FIELD = "lianinfo";
	/**
	 * 联成员列表版本号 field
	 */
	public final static String VERSION_LIANMEMBERLIST_FIELD = "lianmembers";
	/**
	 * 联岗位列表版本号 field
	 */
	public final static String VERSION_LIANPOSITIONLIST_FIELD = "lianpositions";
	/**
	 * 联的批次列表 field
	 */
	public final static String VERSION_LIANBATCHLIST_FIELD = "lianbatches";
	/**
	 * 批次成员列表 field
	 */
	public final static String VERSION_BATCHMEMBERS_FIELD = "batchmembers";
	
	/**
	 * 关注的组织 field
	 */
	public final static String VERSION_FOLLOWORG_FIELD = "followorg";
	
	/**
	 * 友好组织 field
	 */
	public final static String VERSION_FRIENDLYORG_FIELD = "friendlyorg";
	
	/**
	 * 用户联列表key
	 */
	public final static String USER_LIAN_LIST_KEY = "userlian::%s_%s";
	/**
	 * 用户组织列表key
	 */
	public final static String USER_ORG_LIST_KEY = "userorg::%s";
	/**
	 * 联信息key
	 */
	public final static String LIAN_INFO_KEY = "lianinfo::%s";
	/**
	 * 联岗位列表key
	 */
	public final static String LIAN_POSITION_KEY = "position::%s";
	/**
	 * 联岗位成员key
	 */
	public final static String LIAN_POSITION_MEMBER_KEY = "positionmember::%s_%s";
	/**
	 * 组织信息key
	 */
	public final static String ORG_INFO_KEY = "orginfo::%s";
	/**
	 * 组织联列表key
	 */
	public final static String ORG_LIAN_LIST_KEY = "orglian::%s";
	/**
	 * 联长或联秘缓存信息Key
	 */
	public final static String LIAN_LEADER_MEMBER_KEY = "lm_manager::%s";
	
	public final static String LIAN_CHIEF_MEMBER_FIELD = "chief";
	
	public final static String LIAN_SECRETRY_MEMBER_FIELD = "secretry";
	/**
	 * 联委会缓存信息Key
	 */
	public final static String LIAN_COUNCIL_MEMBER_LIST_KEY = "lm_council::%s";
	/**
	 * 联成员列表缓存信息Key
	 */
	public final static String LIAN_COMPLETE_MEMBER_LIST_KEY = "lianmember::%s";
	
	
	
	
	
	
	
	
	
	/**
	 * orgId生成key
	 */
	public final static String ORGID_INCR_KEY = "orgid_incr";
	
	public static String getMsgMediaKey(String name)
	{
		return String.format(MSGMEDIA_KEY, name);
	}
}
