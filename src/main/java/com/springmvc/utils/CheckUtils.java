package com.springmvc.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class CheckUtils {

	public static final String EMPTY = "";

	public static final String ENGLISH_NUMERALS = "";

	public static final String CHINESE_LETTER_DIGIT = "^[a-z0-9A-Z\u4e00-\u9fa5]+$";
	public static final String LETTER = "[a-zA-Z]*";
	public static final String DIGIT = "[0-9]*";
	public static final String LETTERORDIGIT = "[a-z0-9A-Z]*";
	public static final String EMAIL = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.([a-zA-Z0-9_-])+)+$";
	public static final String EMAILLIKE = "^([a-zA-Z0-9_-])";

	public static final String IP = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

	public static final String HTTP = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

	/**
	 * 
	 */
	public static final String PWD = "^(?![0-9]+$)(?![^0-9]+$)(?![a-zA-Z]+$)(?![^a-zA-Z]+$)(?![a-zA-Z0-9]+$)[a-zA-Z0-9\\S]+$";

	/**
	 * 判断是否为null
	 * 
	 * @param _obj
	 * @return
	 */
	public static boolean isNull(Object _obj) {
		if (null != _obj) {
			return false;
		}
		return true;
	}

	/**
	 * 判断字符串是否为null
	 * 
	 * @param _str
	 * @return
	 */
	public static boolean isNullOrEmpty(String _str) {
		if (isNull(_str) || StringUtils.equals(EMPTY, _str)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断字符串是否为空
	 * @param _str
	 * @return
	 */
	public static boolean isEmpty(String _str){
		return StringUtils.equals(EMPTY, _str);
	}

	/**
	 * 判断字符串是否为null
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNullOrEmpty(List list) {
		if (isNull(list) || list.size() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 检测参数格式是否正确 参数为空或者null或者格式正确返回true
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkPara(String str, final String PatternStr, int len) {
		if (isNullOrEmpty(str)) {
			return true;
		}
		if (str.matches(PatternStr)) {
			if (len == 0) {
				return true;
			}
			if (str.length() > len) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 检测参数格式是否正确 参数为空或者null或者格式正确返回true
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkPara(String str, int len) {
		if (isNullOrEmpty(str)) {
			return true;
		}
		if (str.length() > len) {
			return false;
		}
		return true;
	}

	
	/**
	 * 检测参数格式是否正确 参数为空或者null或者格式正确返回false
	 * 
	 * @param str
	 * @param PatternStr
	 * @param len
	 * @return
	 */
	public static boolean checkParas(String str, final String PatternStr,
			int len) {
		if (isNullOrEmpty(str)) {
			return false;
		}
		if (str.matches(PatternStr)) {
			if (len == 0) {
				return true;
			}
			if (str.length() > len) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 验证IP地址
	 * 
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isIP(String str) {
		if (isNull(str)) {
			return false;
		}
		String regex = "^" + IP + "\\." + IP + "\\." + IP + "\\." + IP + "$";
		return str.matches(regex);
	}

	/**
	 * 验证网址Url
	 * 
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isUrl(String str) {
		if (isNull(str)) {
			return false;
		}
		return str.matches(HTTP);
	}

	/**
	 * 判断是否为邮箱地址
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMail(String str) {
		if (isNull(str)) {
			return false;
		}
		return str.matches(EMAIL);
	}

	public static boolean checkMail(String str) {
		if (isNullOrEmpty(str)) {
			return true;
		}
		str = str.replace("@", "");
		str = str.replace(".", "");
		str = str.replace("-", "");
		str = str.replace("_", "");

		return CheckUtils.checkPara(str, CheckUtils.CHINESE_LETTER_DIGIT, 200);
	}

	/**
	 * 判断是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isDigit(String str) {
		if (isNull(str)) {
			return false;
		}

		return str.matches(DIGIT);
	}

	/**
	 * 判断是否为英字母
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isLetter(String str) {
		if (isNull(str)) {
			return false;
		}
		return str.matches(LETTER);
	}

	public static boolean isLetterOrDigit(String str) {
		if (isNull(str)) {
			return false;
		}
		return str.matches(LETTERORDIGIT);
	}

	/**
	 * 英数字或者汉字或者数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isLetterDigitOrChinese(String str) {
		if (isNull(str)) {
			return false;
		}
		return str.matches(CHINESE_LETTER_DIGIT);
	}


	/**
	 * 检测IP格式是否正确 参数为空或者null或者格式正确返回true
	 * 
	 * @param str
	 * @return
	 */
	public static boolean checkPara(String str) {
		if (isNullOrEmpty(str)) {
			return true;
		}
		if (isIP(str)) {
			return true;
		}
		return false;
	}

	public static boolean isNullOrEmptyTime(Timestamp timestamp) {
		if (isNull(timestamp) || EMPTY.equals(timestamp)) {
			return true;
		}
		return false;
	}

	public static boolean isNullOrEmptyDate(Date date) {
		if (isNull(date) || EMPTY.equals(date)) {
			return true;
		}
		return false;
	}
}
