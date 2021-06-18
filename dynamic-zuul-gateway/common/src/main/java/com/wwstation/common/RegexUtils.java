package com.wwstation.common;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author william
 * @description
 * @Date: 2021-06-18 15:09
 */
public class RegexUtils {
    public static String getString(String pattern, String source) {
        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(source);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return StringUtils.EMPTY;
    }
    public static String getString(String prefix,String suffix, String source) {
        Pattern p = Pattern.compile("(?<=" + prefix + ")([^\\}]*)(?=" + suffix + ")");
        Matcher matcher = p.matcher(source);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return StringUtils.EMPTY;
    }

}
