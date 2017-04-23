package cn.net.cvtt.imps.lianzi.enums;

import cn.net.cvtt.lian.common.util.EnumParser;

public enum ProtocolErrorRsp {
	/*
	 * 系统级别的错误码
	 */
	ACCESS_TOKEN_EMPTY(40001, "网络异常，请检查网络设置"), // Access_Token不能为空
	ACCESS_TOKEN_INVALID(40002, "网络异常，请检查网络设置"), // Access_Token无效
	ACCESS_TOKEN_EXPIRE(40003, "网络异常，请检查网络设置"), // Access_Token过期
	REFRESH_TOKEN_EXPIRE(40004, "网络异常，请检查网络设置"), // Refresh_Token过期
	VERSION_NOT_SUPPORTED(40005, "你的联子版本过低,请升级至最新版本后再登录。"), // 版本号过低，不再支持
	SERVER_MAINTAIN(40006, "网络异常，请检查网络设置"), // 服务器维护中
	REQUEST_NOT_FOUND(40007, "网络异常，请检查网络设置"), // 无效的请求资源
	REQUEST_REPEATED(40008, "网络异常，请检查网络设置"), // 重复的请求
	REQUEST_FREQ_OVER_LIMIT(40009, "网络异常，请检查网络设置"), // 操作太频繁
	REQUEST_FORMAT_ERROR(40010, "网络异常，请检查网络设置"), // 参数格式转换错误
	SEARCH_FILTER_ERROR(40011, "网络异常，请检查网络设置"), // 搜索条件无效
	SERVICE_ERROR(40999, "网络异常，请检查网络设置"), // 服务器错误

	/*
	 * 参数校验类
	 */
	USER_MOBILENO_INVALID(41101, ""), // 手机号无效
	USER_USERID_INVALID(41102, ""), // 联子号无效
	USER_SERVICE_TYPE_INVALID(41103, ""), // 短信业务类型无效
	USER_SMSCODE_INVALID(41104, ""), // 短信验证码无效
	USER_NAME_INVALID(41105, ""), // 姓名无效
	USER_NAME_SENSITIVE(41106, ""), // 姓名触发敏感词
	USER_PASSWORD_INVALID(41107, ""), // 密码无效
	USER_MACHINECODE_INVALID(41108, ""), // 机器设备码无效
	USER_GENDER_INVALID(41109, ""), // 性别无效
	USER_CONFIG_INVALID(41110, ""), // 用户配置项无效
	USER_FEEDBACK_CONTENT_INVALID(41130, ""), // 意见反馈内容无效
	USER_FEEDBACK_CONTENT_SENSITIVE(41131, ""), // 意见反馈触发敏感词

	ORG_ID_INVALID(41201, ""), // 组织号无效
	ORG_FULLNAME_INVALID(41210, ""), // 组织全称无效
	ORG_FULLNAME_SENSITIVE(41211, ""), // 组织全称触发敏感词
	ORG_SHORTNAME_INVALID(41212, ""), // 组织简称无效
	ORG_SHORTNAME_SENSITIVE(41213, ""), // 组织简称触发敏感词
	ORG_TYPE_INVALID(41214, ""), // 组织类别无效
	ORG_INDUSTRY_INVALID(41215, ""), // 组织行业类型无效
	ORG_LICENSE_TYPE_INVALID(41216, ""), // 组织证照类型无效
	ORG_SOCIAL_CREDIT_CODE_INVALID(41217, ""), // 统一社会信用代码无效
	ORG_SOCIAL_CREDIT_PIC_INVALID(41218, ""), // 三证合一照片无效
	ORG_BUSSINESS_LICENSE_CODE_INVALID(41219, ""), // 营业执照号码无效
	ORG_BUSSINESS_LICENSE_PIC_INVALID(41220, ""), // 营业执照照片无效
	ORG_ORGANIZATION_CODE_INVALID(41221, ""), // 组织机构代码无效
	ORG_ORGANIZATION_PIC_INVALID(41222, ""), // 组织机构照片无效
	ORG_REGISTER_TYPE_INVALID(41223, ""), // 组织登记注册类型无效
	ORG_REGISTER_ADDRESS_INVALID(41224, ""), // 组织注册地址无效
	ORG_OFFICE_ADDRESS_INVALID(41225, ""), // 组织办公驻地无效
	ORG_DETAIL_ADDRESS_INVALID(41226, ""), // 组织详细地址无效
	ORG_DETAIL_ADDRESS_SENSITIVE(41227, ""), // 组织详细地址触发敏感词
	ORG_AUTH_APPLY_LETTER_PIC_INVALID(41228, ""), // 认证申请公函照片无效
	ORG_CREATOR_NAME_INVALID(41229, ""), // 组织创建者姓名无效
	ORG_CREATOR_NAME_SENSITIVE(41230, ""), // 组织创建者姓名触发敏感词
	ORG_CREATOR_IDCARD_INVALID(41231, ""), // 组织创建者身份证号码无效
	ORG_CREATOR_IDCARD_PIC_INVALID(41232, ""), // 组织创建者身份证照片无效
	ORG_MEMBER_INVALID(41250, ""), // 组织成员无效
	ORG_CONTACTPHONE_INVALID(41270, ""), // 组织联系方式无效
	ORG_CONTACTPHONE_SENSITIVE(41271, ""), // 组织联系方式触发敏感词
	ORG_EMAIL_INVALID(41272, ""), // 组织邮箱无效
	ORG_EMAIL_SENSITIVE(41273, ""), // 组织邮箱触发敏感词

	LIAN_ID_INVALID(41301, ""), // 联号无效
	LIAN_NAME_INVALID(41310, ""), // 联名称无效
	LIAN_NAME_SENSITIVE(41311, ""), // 联名称触发敏感词
	LIAN_TYPE_INVALID(41312, ""), // 联类型无效
	LIAN_RULES_INVALID(41313, ""), // 联守则（公约）无效
	LIAN_RULES_SENSITIVE(41314, ""), // 联守则（公约）触发敏感词
	LIAN_MEMBER_INVALID(41330, ""), // 联成员无效
	LIAN_SECRETRY_INVALID(41331, ""), // 联秘无效
	LIAN_CHIEF_INVALID(41332, ""), // 联长无效
	LIAN_POSITIONID_INVALID(41350, ""), // 联岗位ID无效
	LIAN_POSITIONNAME_INVALID(41351, ""), // 联岗位名称无效
	LIAN_POSITIONNAME_SENSITIVE(41352, ""), // 联岗位名称触发敏感词
	LIAN_CONTACTPHONE_INVALID(41370, ""), // 个人联资料联系方式无效
	LIAN_CONTACTPHONE_SENSITIVE(41371, ""), // 个人联资料联系方式触发敏感词
	LIAN_EMAIL_INVALID(41372, ""), // 个人联资料邮箱无效
	LIAN_EMAIL_SENSITIVE(41373, ""), // 个人联资料邮箱触发敏感词
	VIP_LIAN_INVITATION_ID_INVALID(41380, ""), // 会员联邀请组织邀请函无效
	VIP_LIAN_INVITATION_LETTER_INVALID(41381, ""), // 会员联邀请组织邀请函无效
	VIP_LIAN_INVITATION_LETTER_SENSITIVE(41382, ""), // 会员联邀请组织邀请函触发敏感词
	VIP_LIAN_APPLY_REASON_INVALID(41383, ""), // 申请加入会员联申请理由无效
	VIP_LIAN_APPLY_REASON_SENSITIVE(41384, ""), // 申请加入会员联申请理由触发敏感词
	VIP_LIAN_APPLY_TYPE_INVALID(41385, ""), // 申请加入会员联申请身份类型无效
	VIP_LIAN_APPLY_ID_INVALID(41386, ""), // 申请加入会员联申请ID无效
	VIP_LIAN_CONFIRM_ID_INVALID(41387, ""), // 申请加入会员联申请ID无效

	APPS_NTC_NOTICEID_INVALID(41411, ""), // 通知ID无效
	APPS_NTC_SHOWTYPE_INVALID(41412, ""), // 通知栏类型无效
	APPS_NTC_TITLE_INVALID(41413, ""), // 通知标题无效
	APPS_NTC_TITLE_SENSITIVE(41414, ""), // 通知标题触发敏感词
	APPS_NTC_CONTENT_INVALID(41415, ""), // 通知内容无效
	APPS_NTC_CONTENT_SENSITIVE(41416, ""), // 通知内容触发敏感词
	APPS_NTC_RECEIVER_INVALID(41417, ""), // 通知接收人无效

	WF_TASKID_EMPTY(41701, ""), // 流程任务ID不能为空
	WF_ORDERID_EMPTY(41702, ""), // 流程实例ID不能为空
	WF_ISSUE_EMPTY(41703, ""), // 流程签发不能为空
	WF_ISSUE_REMARK_EMPTY(41704, ""), // 流程签发意见不能为空
	WF_ISSUE_REMARK_SENSITIVE(41705, ""), // 流程签发意见触发敏感词

	/*
	 * 业务逻辑处理
	 */
	SMSCODE_NOT_MATCH(42001, ""), // 短信验证码错误
	SMSCODE_EXPIRED(42002, ""), // 短信验证码已失效
	SMS_OVER_DAY_LIMIT(42003, ""), // 短信超过日上限
	SMS_FREQ_OVER_ONE_MINUTE_LIMIT(42004, ""), // 短信每分钟发送频率超过上限

	USER_NOT_FOUND(42101, ""), // 用户还未注册
	USER_NOT_INVITED(42102, ""), // 产品内测中，非白名单用户不可注册
	USER_BE_BLOCKED(42103, ""), // 用户被锁定或黑名单用户
	USER_ABNORMAL(42104, ""), // 用户状态异常
	USER_MOBILENO_REGISTERED(42105, ""), // 手机号已经被注册
	USER_PASSWORD_WEEK_STRENGTH(42106, ""), // 密码太弱
	USER_LOGIN_MACHINE_CHANGE(42107, ""), // 异机登录，需要验证手机号
	USER_REGISTER_EASE_ERROR(42108, ""), // 注册环信失败
	USER_LOGIN_ERROR_PASSWORD(42109, ""), // 登录密码错误
	USER_PASSWORD_CAN_NOT_DUPLICATE(42110, ""), // 新密码和旧密码不能一致

	ORG_NOT_FOUND(42201, ""), // 组织还未注册
	ORG_BE_BLOCKED(42202, ""), // 组织暂时被锁定
	ORG_ABNORMAL(42203, ""), // 组织状态异常
	ORG_USER_NOT_JOINED(42204, ""), // 用户未加入本组织
	ORG_PERMISSION_DENIED(42205, ""), // 没有组织管理操作权限
	ORG_FULLNAME_REGISTERED(42220, ""), // 组织全称已被注册
	ORG_SHORTNAME_REGISTERED(42221, ""), // 组织简称已被注册
	ORG_LEADER_COUNT_OVER_LIMIT(42222, ""), // 担任的组织管理者超过上限
	ORG_JOINORG_COUNT_OVER_LIMIT(42223, ""), // 加入组织的数量超过上限
	ORG_LIAN_COUNT_OVER_LIMIT(42224, ""), // 组织下的联数量超过上限
	ORG_LEADER_CHIEF_CAN_NOT_CHANGED(42225, ""), // 不能更换总联的大联长
	ORG_CAN_NOT_REMOVE_MEMBER_AS_ACCESS_USER(42226, ""), // 不能移除仍是对外组织对接人的组织成员

	LIAN_NOT_FOUND(42301, ""), // 联还未注册
	LIAN_BE_BLOCKED(42302, ""), // 联暂时被锁定
	LIAN_ABNORMAL(42303, ""), // 联状态异常
	LIAN_TYPE_NOT_MATCH_ORG_TYPE(42304, ""), // 联类型与组织类型不匹配
	LIAN_USER_NOT_JOINED(42305, ""), // 用户未加入本联
	LIAN_PERMISSION_DENIED(42306, ""), // 没有联管理操作权限
	LIAN_REGISTERED(42320, ""), // 联已经被注册
	LIAN_MEMBER_COUNT_OVER_LIMIT(42321, ""), // 联成员已达到上限
	LIAN_COUNCILMEMBER_COUNT_OVER_LIMIT(42322, ""), // 联委会成员数量达到上限
	LIAN_JOINLIAN_COUNT_OVER_LIMIT(42323, ""), // 加入的联数量超过上限
	LIAN_MEMBER_NOT_FOUND(42324, ""), // 联成员未找到
	LIAN_CHIEF_NOT_FOUND(42325, ""), // 联长未找到
	LIAN_SECRETRY_NOT_FOUND(42326, ""), // 联秘未找到
	LIAN_MEMBER_ALREADY_JOINED(42327, ""), // 已经加入本联
	LIAN_IDENTITY_ERROR(42328, ""), // 联身份错误
	LIAN_REGISTER_EASE_COMPLETE_GROUP_ERROR(42329, ""), // 注册环信全联群失败
	LIAN_REGISTER_EASE_COUNCIL_GROUP_ERROR(42330, ""), // 注册环信联委会群失败
	LIAN_MEMBER_JOIN_EASE_COMPLETE_GROUP_ERROR(42331, ""), // 联成员加入环信全联群失败
	LIAN_MEMBER_JOIN_EASE_COUNCIL_GROUP_ERROR(42332, ""), // 联成员加入环信联委会群失败
	LIAN_MEMBER_REMOVE_EASE_COMPLETE_GROUP_ERROR(42333, ""), // 联成员从环信全联群移除失败
	LIAN_MEMBER_REMOVE_EASE_COUNCIL_GROUP_ERROR(42334, ""), // 联成员从环信联委会群移除失败
	LIAN_CHIEF_CAN_NOT_REMOVED(42335, ""), // 联长不能被移除
	LIAN_SECRETRY_CAN_NOT_REMOVED(42336, ""), // 联秘不能被移除
	LIAN_ALREADY_CHIEF_IDENTITY(42337, ""), // 已经是联长身份
	LIAN_ALREADY_SECRETRY_IDENTITY(42338, ""), // 已经是联秘身份
	LIAN_ALREADY_COUNCIL_IDENTITY(42339, ""), // 已经是联委会身份

	LIAN_POSITION_NOT_FOUND(42450, ""), // 联岗位不存在
	LIAN_POSITION_ALREADY_EXIST(42451, ""), // 联岗位已存在
	LIAN_POSITION_USER_ALREADY_EXIST(42452, ""), // 岗位上已存在人员
	LIAN_POSITION_USER_NOT_FOUND(42453, ""), // 岗位上不存在此人或此人已从本岗位离职
	LIAN_USER_POSITION_COUNT_OVER_LIMIT(42454, ""), // 担任的岗位数量已达上限

	VIP_LIAN_ORG_IDENTITY_ONLY(42501, ""), // 会员联-只允许以组织身份加入
	VIP_LIAN_PERSONAL_IDENTITY_ONLY(42502, ""), // 会员联-只允许个人身份加入
	VIP_LIAN_ORG_IDENTITY_ALREADY_JOIN(42503, ""), // 已经以组织身份加入到会员联中
	VIP_LIAN_PERSONAL_IDENTITY_ALREADY_JOIN(42504, ""), // 已经以个人身份加入到会员联中
	VIP_LIAN_ORG_IDENTITY_RESTRICTED_NO_ORG_JOINED(42505, ""), // 会员联只允许组织身份加入，但用户未加入任何组织
	VIP_LIAN_ORG_ALREADY_JOIN_LIAN(42506, ""), // 组织已经加入到会员联中
	VIP_LIAN_ORG_CAN_NOT_JOIN_OWN_LIAN(42507, ""), // 不能以某组织身份加入到该组织下的会员联
	VIP_LIAN_INVITATION_NOT_FOUND(42508, ""), // 邀请函不存在
	VIP_LIAN_INVITATION_NOT_EFFECTIVE(42509, ""), // 邀请函已失效
	VIP_LIAN_CONFIRM_NOT_FOUND(42510, ""), // 确认函不存在
	VIP_LIAN_CONFIRM_NOT_EFFECTIVE(42511, ""), // 确认函已失效
	VIP_LIAN_JOIN_LIAN_APPLY_NOT_FOUND(42512, ""), // 会员申请审核申请未找到
	VIP_LIAN_JOIN_LIAN_APPLY_NOT_EFFECTIVE(42513, ""), // 会员申请审核申请已失效

	APPS_NTC_NOTICE_NOT_FOUND(42601, "通知不存在"), // 通知不存在
	APPS_NTC_PERMISSION_DENIED(42602, "通知权限不够"), // 通知权限不够
	APPS_NTC_STATUS_ERROR(42603, "通知状态不匹配"), // 通知状态不匹配
	APPS_NTC_REPEAD_SUBMIT_ERROR(42604, "通知重复提交"), // 通知重复提交
	APPS_NTC_RELEASE_SCOPE_NOT_FOUND(42605, "通知发布范围不存在"), // 通知发布范围不存在
	APPS_NTC_PARENTNOTICE_NOT_FOUND(42606, "原始通知不存在"), // 原始通知不存在
	APPS_NTC_UNREAD_REMIND_COUNT_OVER_LIMIT(42607, ""), // 未读提醒超过3次上限
	APPS_NTC_UNREAD_REMIND_FREQ_OVER_LIMIT(42608, ""), // 未读提醒超过频率限制

	WF_ORDER_NOT_FOUND(42901, ""), // 流程实例不存在
	WF_TASK_NOT_FOUND(42902, ""), // 流程任务节点不存在
	WF_TASK_STATUS_ERROR(42903, ""), // 流程任务状态异常
	WF_TASK_PERMISSION_DENIED(42904, ""),// 无权限操作流程

	;

	private final int code;

	private final String msg;

	ProtocolErrorRsp(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

	public static void main(String[] args) {
		int code = 42701;
		ProtocolErrorRsp rsp = (ProtocolErrorRsp) EnumParser.parseInt(ProtocolErrorRsp.class, code);
		switch (rsp) {
		case WF_TASK_NOT_FOUND:

		case WF_TASK_PERMISSION_DENIED:
		default:
			break;

		}
	}

}
