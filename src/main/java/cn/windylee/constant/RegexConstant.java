package cn.windylee.constant;

import java.util.regex.Pattern;

public class RegexConstant {

    /**
     * Regex of email.
     */
    public static final String EMAIL_REGEX = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";

    /**
     * Regex of exact mobile.
     * <p>china mobile: 134(0-8), 135, 136, 137, 138, 139, 147, 150, 151, 152, 157, 158, 159, 178, 182, 183, 184, 187, 188, 198</p>
     * <p>china unicom: 130, 131, 132, 145, 155, 156, 166, 171, 175, 176, 185, 186</p>
     * <p>china telecom: 133, 153, 173, 177, 180, 181, 189, 199</p>
     * <p>global star: 1349</p>
     * <p>virtual operator: 170</p>
     */
    public static final String MOBILE_REGEX = "^1(3[0-9]|4[57]|5[0-35-9]|7[0135-8]|8[0-9]|9[89])\\d{8}$";

    /**
     * Regex of id card number which length is 15.
     */
    public static final String ID_CARD15_REGEX = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";

    /**
     * Regex of id card number which length is 18.
     */
    public static final String ID_CARD18_REGEX = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$";

    /**
     * Regex of url.
     */
    public static final String URL_REGEX = "^(https?:\\/\\/)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([\\/\\w \\.-]*)*\\/?$";

    /**
     * Regex of Chinese character.
     */
    public static final String ZH_REGEX = "^[\\u4e00-\\u9fa5]+$";

    /**
     * Regex of username.
     * <p>scope for "a-z", "A-Z", "0-9", "_", "Chinese character"</p>
     * <p>can't end with "_"</p>
     * <p>length is between 6 to 20</p>
     */
    public static final String USERNAME_REGEX = "^[\\w\\u4e00-\\u9fa5_-]{6,20}$";

    /**
     * Regex of date which pattern is "yyyy-MM-dd".
     */
    public static final String REGEX_DATE = "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$";

    /**
     * Regex of password
     */
    public static final String PASSWORD_REGEX = "^[a-z0-9_-]{6,18}$";

    /**
     * Regex of ip address.
     */
    public static final String IP_REGEX = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

    /**
     * Regex of QQ number.
     */
    public static final String QQ_REGEX = "^[1-9]\\d{4,}$";

    /**
     * Regex of positive integer.
     */
    public static final String POSITIVE_INTEGER_REGEX = "^[1-9]\\d*$";

    /**
     * Regex of negative integer.
     */
    public static final String NEGATIVE_INTEGER_REGEX = "^-[1-9]\\d*$";

    /**
     * Regex of integer.
     */
    public static final String INTEGER_REGEX = "^-?[1-9]\\d*$";

    /**
     * Regex of positive float.
     */
    public static final String POSITIVE_FLOAT_REGEX = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$";

    /**
     * Regex of negative float.
     */
    public static final String NEGATIVE_FLOAT_REGEX = "^-[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$";

    /**
     * Regex of float
     */
    public static final String FLOAT_REGEX = "^-?[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$";

    /**
     * Regex of blank line.
     */
    public static final String REGEX_BLANK_LINE = "\\n\\s*\\r";

    /**
     * Regex of postal code in China.
     */
    public static final String REGEX_CHINA_POSTAL_CODE    = "[1-9]\\d{5}(?!\\d)";

    public static void main(String[] args) {
        System.out.println(Pattern.matches(MOBILE_REGEX, "14511111111"));
    }

}
