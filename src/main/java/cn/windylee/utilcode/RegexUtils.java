package cn.windylee.utilcode;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {


    private static final String EMAIL_REGEX = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";

    private static final String MOBILE_REGEX = "^1(3[0-9]|4[57]|5[0-35-9]|7[01678]|8[0-9])\\d{8}$";

    private static final String ID_CARD15_REGEX = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";

    private static final String ID_CARD18_REGEX = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$";

    private static final String URL_REGEX = "^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$";

    private static final String ZH_REGEX = "^[\\u4e00-\\u9fa5]+$";

    private static final String USERNAME_REGEX = "^[\\w\\u4e00-\\u9fa5_-]{6,20}$";

    private static final String PASSWORD_REGEX = "^[a-z0-9_-]{6,18}$";

    private static final String IP_REGEX = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

    private static final String QQ_REGEX = "^[1-9]\\d{4,}$";

    private static final String POSITIVE_INTEGER_REGEX = "^[1-9]\\d*$";

    private static final String NEGATIVE_INTEGER_REGEX = "^-[1-9]\\d*$";

    private static final String INTEGER_REGEX = "^-?[1-9]\\d*$";

    private static final String POSITIVE_FLOAT_REGEX = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$";

    private static final String NEGATIVE_FLOAT_REGEX = "^-[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$";

    private static final String FLOAT_REGEX = "^-?[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$";

    private RegexUtils() {
        throw new UnsupportedOperationException("the RegexUtils cannot be initialed");
    }

    /**
     * 验证邮箱
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isEmail(final String input) {
        return isMatch(EMAIL_REGEX, input);
    }

    /**
     * 验证手机号
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMobilePhone(final String input) {
        return isMatch(MOBILE_REGEX, input);
    }

    /**
     * 验证15位身份证号
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIDCard15(final String input) {
        return isMatch(ID_CARD15_REGEX, input);
    }

    /**
     * 验证18位身份证号
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIDCard18(final String input) {
        return isMatch(ID_CARD18_REGEX, input);
    }

    /**
     * 验证身份证号
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIDCard(final String input) {
        return isIDCard15(input) || isIDCard18(input);
    }

    /**
     * 验证URL
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isURL(final String input) {
        return isMatch(URL_REGEX, input);
    }

    /**
     * 验证中文
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isZH(final String input) {
        return isMatch(ZH_REGEX, input);
    }

    /**
     * 验证用户名，用户名由中文、字母、数字、-、_组成，长度为6-20位
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isUsername(final String input) {
        return isMatch(USERNAME_REGEX, input);
    }

    /**
     * 验证密码，密码由字母、数字、-、_组成，长度为6-18位
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isPassword(final String input) {
        return isMatch(PASSWORD_REGEX, input);
    }

    /**
     * 验证ip地址
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isIP(final String input) {
        return isMatch(IP_REGEX, input);
    }

    /**
     * 验证QQ号
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isQQ(final String input) {
        return isMatch(QQ_REGEX, input);
    }

    /**
     * 验证正整数
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isPositiveInteger(final String input) {
        return isMatch(POSITIVE_INTEGER_REGEX, input);
    }

    /**
     * 验证负整数
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isNegativeInteger(final String input) {
        return isMatch(NEGATIVE_INTEGER_REGEX, input);
    }

    /**
     * 验证整数
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isInteger(final String input) {
        return isMatch(INTEGER_REGEX, input);
    }

    /**
     * 验证正浮点数
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isPositiveFloat(final String input) {
        return isMatch(POSITIVE_FLOAT_REGEX, input);
    }

    /**
     * 验证负浮点数
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isNegativeFloat(final String input) {
        return isMatch(NEGATIVE_FLOAT_REGEX, input);
    }

    /**
     * 验证浮点数
     *
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isFloat(final String input) {
        return isMatch(FLOAT_REGEX, input);
    }

    /**
     * 验证输入文本是否符合给定的正则表达式
     *
     * @param regex 给定的正则表达式
     * @param input 待验证文本
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMatch(final String regex, final String input) {
        return input != null && input.length() > 0 && Pattern.matches(regex, input);
    }

    /**
     * 从文本中提取符合给定正则的部分
     *
     * @param regex 给定的正则表达式
     * @param input 待验证文本
     * @return 正则匹配部分数组
     */
    public static List<String> getMatches(final String regex, final String input) {
        if (input == null) return null;
        List<String> matches = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches;
    }

    /**
     * 根据给定正则切分给定文本
     *
     * @param regex 给定正则表达式
     * @param input 待切分文本
     * @return 切分开的字符串数组
     */
    public static String[] getSplits(final String regex, final String input) {
        if (input == null) return null;
        return input.split(regex);
    }

    /**
     * 替换给定字符串中正则匹配的第一部分
     *
     * @param regex       给定正则表达式
     * @param input       待替换文本
     * @param replacement 替换文本
     * @return 替换后的字符串
     */
    public static String getReplaceFirst(final String regex, final String input, final String replacement) {
        if (input == null) return null;
        return Pattern.compile(regex).matcher(input).replaceFirst(replacement);
    }

    /**
     * 替换给定字符串中正则匹配的所有部分
     *
     * @param regex       给定正则表达式
     * @param input       待替换文本
     * @param replacement 替换文本
     * @return 替换后的字符串
     */
    public static String getReplaceAll(final String regex, final String input, final String replacement) {
        if (input == null) return null;
        return Pattern.compile(regex).matcher(input).replaceAll(replacement);
    }

}
