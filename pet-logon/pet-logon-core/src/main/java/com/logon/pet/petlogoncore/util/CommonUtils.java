package com.logon.pet.petlogoncore.util;


import org.springframework.util.StringUtils;

/**
 *  工具类
 * @Auth jianglin
 */
public class CommonUtils {

    public static boolean isNotBlank(String str) {
        return StringUtils.isEmpty(str);
    }

    /**
     * 判断字符串是否为数字
     *
     * @param number
     * @return
     */
    public static boolean isNumber(String number) {
        if (number.matches("\\d+.?\\d*")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断字符串是否为数字包含负数
     *
     * @param number
     * @return
     */
    public static boolean isNumberContainsMinus(String number) {
        if (number.matches("(-?\\d+)(\\.\\d+)?$")) {
            return true;
        } else {
            return false;
        }
    }
}
