package com.lzy.commonsdk.utils.display;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串工具类
 * Created by bullet on 2017/8/30.
 */

public class StringUtils {
    /**
     * 判断字符串是否有值，如果为null或者是空字符串或者只有空格或者为"null"字符串，则返回true，否则则返回false
     */
    public static boolean isEmpty(String value) {
        return !(value != null && !"".equalsIgnoreCase(value.trim())
                && !"null".equalsIgnoreCase(value.trim()));
    }

    /**
     * 判断字符串是否是邮箱
     *
     * @param email email
     * @return 字符串是否是邮箱
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(" +
                "([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 判断手机号字符串是否合法
     *
     * @param phoneNumber 手机号字符串
     * @return 手机号字符串是否合法
     */
    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        String expression = "^1[3|4|5|7|8]\\d{9}$";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * 判断手机号字符串是否合法
     *
     * @param areaCode    区号
     * @param phoneNumber 手机号字符串
     * @return 手机号字符串是否合法
     */
    public static boolean isPhoneNumberValid(String areaCode, String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)) {
            return false;
        }

        if (phoneNumber.length() < 5) {
            return false;
        }

        if (TextUtils.equals(areaCode, "+86") || TextUtils.equals(areaCode, "86")) {
            return isPhoneNumberValid(phoneNumber);
        }

        boolean isValid = false;
        String expression = "^[0-9]*$";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * 判断字符串是否是手机号格式
     *
     * @param areaCode    区号
     * @param phoneNumber 手机号字符串
     * @return 字符串是否是手机号格式
     */
    public static boolean isPhoneFormat(String areaCode, String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)) {
            return false;
        }

        if (phoneNumber.length() < 7) {
            return false;
        }

        boolean isValid = false;
        String expression = "^[0-9]*$";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * 判断字符串是否为纯数字
     *
     * @param str 字符串
     * @return 是否纯数字
     */
    public static boolean isNumber(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是不是11位数字
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNumber(String mobiles) {
        String expression = "\\d{11}$";
        Pattern p = Pattern.compile(expression);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }


    /**
     * 判断字符串是不是url
     *
     * @param url
     * @return
     */
    public static boolean isUrl(String url) {

        Pattern pattern =
                Pattern.compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
        return pattern.matcher(url).matches();
    }


}
