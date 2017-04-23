package cn.net.cvtt.lian.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.net.cvtt.lian.common.util.StringUtils;

public class RegExUtil {

	private static String NUM_REGEX = "[0-9]*";
	private static String MOBILENO_REGEX = "^[1][3,7,4,5,8][0-9]{9}$";
	private static String EMAIL_REGX = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
	// 用户姓名：可以输入汉字(简体繁体)、字母、点（全半角）、空格
	private static final String USER_NAME_REGEX = "^[\u4E00-\u9FA5.\\s。a-zA-Z]+";
	// 联名称：2-12位
	private static final String LIAN_NAME_REGEX = "^.{2,12}$";
	// 组织全称：可以输入汉字(简体繁体)、数字、字母、和特殊符号中的“（）”，4-50位
	private static final String ORG_FULL_NAME_REGEX = "^[\u4E00-\u9FA5()（）a-zA-Z0-9]{4,100}$";
	// 组织简称：可以输入汉字(简体繁体)、数字、字母，2-8位
	private static final String ORG_SHORT_NAME_REGEX = "^[\u4E00-\u9FA5a-zA-Z0-9]{2,8}$";
	// 组织联系方式：1-15位
	private static final String ORG_PHONE_REGEX = "^.{1,15}$";

	/**
	 * 是否是数字
	 * 
	 * @param numStr
	 * @return
	 */
	public static boolean isNumeric(String numStr) {
		Pattern pattern = Pattern.compile(NUM_REGEX);
		Matcher m = pattern.matcher(numStr);
		return m.matches();
	}

	/**
	 * 手机号是否合法
	 * 
	 * @param mobileNo
	 * @return
	 */
	public static boolean isMobileNoMatch(long mobileNo) {
		Pattern pattern = Pattern.compile(MOBILENO_REGEX);
		Matcher m = pattern.matcher(String.valueOf(mobileNo));
		return m.matches();
	}
	
	/**
	 * 邮箱是否合法
	 * 
	 * @param mobileNo
	 * @return
	 */
	public static boolean isEmailMatch(String email) {
		Pattern pattern = Pattern.compile(EMAIL_REGX);
		Matcher m = pattern.matcher(email);
		return m.matches();
	}

	/**
	 * 用户注册姓名是否合法
	 * 
	 * @param lianName
	 * @return
	 */
	public static boolean isUserNameMatch(String userName) {
		Pattern pattern = Pattern.compile(USER_NAME_REGEX);
		Matcher m = pattern.matcher(userName.trim());
		return m.matches();
	}

	/**
	 * 联名称是否合法
	 * 
	 * @param lianName
	 * @return
	 */
	public static boolean isLianNameMatch(String lianName) {
		Pattern pattern = Pattern.compile(LIAN_NAME_REGEX);
		Matcher m = pattern.matcher(lianName.trim());
		return m.matches();
	}

	/**
	 * 组织全称是否合法
	 * 
	 * @param orgFullName
	 * @return
	 */
	public static boolean isOrgFullNameMatch(String orgFullName) {
		Pattern pattern = Pattern.compile(ORG_FULL_NAME_REGEX);
		Matcher m = pattern.matcher(orgFullName.trim());
		return m.matches();
	}

	/**
	 * 组织简称是否合法
	 * 
	 * @param orgShortName
	 * @return
	 */
	public static boolean isOrgShortNameMatch(String orgShortName) {
		Pattern pattern = Pattern.compile(ORG_SHORT_NAME_REGEX);
		Matcher m = pattern.matcher(orgShortName.trim());
		return m.matches();
	}
	
	/**
	 * 组织的联系方式
	 * 
	 * @param orgShortName
	 * @return
	 */
	public static boolean isOrgPhoneMatch(String orgPhone) {
		Pattern pattern = Pattern.compile(ORG_PHONE_REGEX);
		Matcher m = pattern.matcher(StringUtils.trimAll(orgPhone));
		return m.matches();
	}

	public static void main(String[] args) {
		// System.out.println(RegExUtil.isNumeric("045324"));
		// System.out.println(RegExUtil.isMobileNoMatch(0));
//		System.out.println(RegExUtil.isOrgFullNameMatch("电哈哈哈哈(空)间规划（覆盖）"));
		// System.out.println(StringUtils.trimAll(" 交道3stta大(asd) \n "));
		System.out.println(RegExUtil.isUserNameMatch("的打的费打算发的撒范德.萨范德萨范德萨范德萨           奥德赛范德。萨范德萨发大师傅打dsasafd申达股份第三个反倒是割发代首 safdsafdsafdsafdsafdsafdsafdsafdsaf"));
	}
}
