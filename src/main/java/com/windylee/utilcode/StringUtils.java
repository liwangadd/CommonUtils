package com.windylee.utilcode;

import java.util.Iterator;

public class StringUtils {

    private static final String EMPTY = "";

    public StringUtils() {
        throw new UnsupportedOperationException("the StringUtils cannot be initialed");
    }

    /**
     * 判断字符串是否为空
     * @param cs 字符串
     * @return 空：true；else false
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * 判断字符串是否为空，或有空字符组成
     * @param cs 字符串
     * @return 空或空字符：true；else false
     */
    public static boolean isBlank(final CharSequence cs) {
        if (cs == null || cs.length() == 0) return true;
        for (int i = 0, len = cs.length(); i < len; ++i) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 去掉字符串两端空字符
     * @param str 字符串
     * @return 去掉两端空字符的字符串
     */
    public static String trim(final String str) {
        return str == null ? null : str.trim();
    }

    public static String trimToNull(final String str) {
        final String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }

    public static String trimToEmpty(final String str) {
        return str == null ? EMPTY : str.trim();
    }

    /**
     * 判断两个字符串是否相等
     * @param cs1 字符串1
     * @param cs2 字符串2
     * @return 相等：true；else： false
     */
    public static boolean equals(final CharSequence cs1, final CharSequence cs2) {
        if (cs1 == cs2) return true;
        if (cs1 == null || cs2 == null) return false;
        if (cs1.length() != cs2.length()) return false;
        if (cs1 instanceof String && cs2 instanceof String) {
            return cs1.equals(cs2);
        } else {
            for (int i = 0, len = cs1.length(); i < len; ++i) {
                if (cs1.charAt(i) != cs2.charAt(i)) return false;
            }
            return true;
        }
    }

    /**
     * 判断两个字符串是否相等（忽略大小写）
     * @param cs1 字符串1
     * @param cs2 字符串2
     * @return 相等：true；else： false
     */
    public static boolean equalsIgnoreCase(final CharSequence cs1, final CharSequence cs2) {
        if (cs1 == cs2) return true;
        if (cs1 == null || cs2 == null || cs1.length() != cs2.length()) return false;
        if (cs1 instanceof String && cs2 instanceof String) {
            return ((String) cs1).equalsIgnoreCase((String) cs2);
        } else {
            for (int i = 0, len = cs1.length(); i < len; ++i) {
                if (Character.toLowerCase(cs1.charAt(i)) != Character.toLowerCase(cs2.charAt(i)))
                    return false;
            }
            return true;
        }

    }

    /**
     * 截取字符串
     * @param str 字符串
     * @param start 截取的起始位置
     * @return 截取的字符串
     */
    public static String substring(final String str, int start) {
        if (str == null) return null;
        if (start < 0) {
            start = str.length() + start;
        }
        if (start < 0) {
            start = 0;
        } else if (start > str.length()) {
            return EMPTY;
        }
        return str.substring(start);
    }

    /**
     * 截取字符串
     * @param str 字符串
     * @param start 截取的起始位置
     * @param end 截取的结束位置
     * @return 截取的字符串
     */
    public static String substring(final String str, int start, int end) {
        if (str == null) return null;
        if (end < 0) end = str.length() + end;
        if (start < 0) start = str.length() + start;
        if (end > str.length()) end = str.length();
        if (start > end) return EMPTY;
        if (start < 0) start = 0;
        if (end < 0) end = 0;
        return str.substring(start, end);
    }

    /**
     * 截取从0下标开始，指定长度的字符串
     * @param str 字符串
     * @param len 截取的长度
     * @return 截取的字符串
     */
    public static String left(final String str, int len) {
        if (str == null) return null;
        if (len < 0) return EMPTY;
        if (len > str.length()) return str;
        return str.substring(0, len);
    }

    /**
     * 截取从最后一个字符开始向前追溯，指定长度的字符串
     * @param str 字符串
     * @param len 截取的长度
     * @return 截取的字符串
     */
    public static String right(final String str, int len) {
        if (str == null) return null;
        if (len < 0) return EMPTY;
        if (len > str.length()) return str;
        return str.substring(str.length() - len);
    }

    /**
     * 截取从指定位置开始，指定长度的字符串
     * @param str 字符串
     * @param pos 起始位置
     * @param len 截取的长度
     * @return 截取的字符串
     */
    public static String mid(final String str, int pos, int len) {
        if (str == null) return null;
        if (len < 0 || pos > str.length()) return EMPTY;
        if (pos < 0) pos = 0;
        if (str.length() <= pos + len) return str.substring(pos);
        return str.substring(pos, pos + len);
    }

    /**
     * 使用连接符将迭代器中的元素链接起来
     * @param iterable 迭代器
     * @param separator 连接符
     * @return 连接后的字符串
     */
    public static String join(final Iterator<?> iterable, final String separator) {
        if (iterable == null) return null;
        StringBuilder sb = new StringBuilder();
        while (iterable.hasNext()) {
            Object obj = iterable.next();
            if (obj != null) {
                sb.append(obj);
                sb.append(separator);
            }
        }
        sb.delete(sb.length() - separator.length(), sb.length());
        return sb.toString();
    }

    /**
     * 比较两个字符串大小
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 大于：1，等于：0，小于：-1
     */
    public static int compare(final String str1, final String str2) {
        return compare(str1, str2, true);
    }

    /**
     * 比较两个字符串大小
     * @param str1 字符串1
     * @param str2 字符串2
     * @param nullIsLess null是否最小
     * @return 大于：1，等于：0，小于：-1
     */
    public static int compare(final String str1, final String str2, final boolean nullIsLess) {
        if (str1 == str2) return 0;
        if (str1 == null) return nullIsLess ? -1 : 1;
        if (str2 == null) return nullIsLess ? 1 : -1;
        return str1.compareTo(str2);
    }

    /**
     * 比较两个字符串大小（忽略大小写）
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 大于：1，等于：0，小于：-1
     */
    public static int compareIgnoreCase(final String str1, final String str2) {
        return compareIgnoreCase(str1, str2, true);
    }

    /**
     * 比较两个字符串大小（忽略大小写）
     * @param str1 字符串1
     * @param str2 字符串2
     * @param nullIsLess null是否最小
     * @return 大于：1，等于：0，小于：-1
     */
    public static int compareIgnoreCase(final String str1, final String str2, final boolean nullIsLess) {
        if (str1 == null && str2 == null) return 0;
        if (str1 == null) return nullIsLess ? -1 : 1;
        if (str2 == null) return nullIsLess ? 1 : -1;
        return str1.compareToIgnoreCase(str2);
    }

    /**
     * 将字符串重复repeat次
     * @param str 字符串
     * @param repeat 重复次数
     * @return null：null，repeat<0: "", else: 重复指定次数的字符串
     */
    public static String repeat(final String str, final int repeat) {
        if (str == null) return null;
        if (repeat <= 0) return EMPTY;
        int inputLength = str.length();
        if (repeat == 1 || inputLength == 0) {
            return str;
        }
        int outputLength = inputLength * repeat;
        switch (inputLength) {
            case 1:
                return repeat(str.charAt(0), repeat);
            case 2:
                char ch0 = str.charAt(0);
                char ch1 = str.charAt(1);
                char[] output = new char[outputLength];
                for (int i = repeat * 2 - 2; i >= 0; i -= 2) {
                    output[i] = ch0;
                    output[i + 1] = ch1;
                }
                return new String(output);
            default:
                StringBuilder sb = new StringBuilder(outputLength);
                for (int i = 0; i < repeat; ++i) {
                    sb.append(str);
                }
                return sb.toString();
        }
    }

    /**
     * 将字符重复repeat次
     * @param ch 字符
     * @param repeat 重复次数
     * @return repeat<0: "", else重复指定次数的字符串
     */
    public static String repeat(final char ch, final int repeat) {
        if (repeat < 0) return EMPTY;
        char[] buf = new char[repeat];
        for (int i = 0; i < repeat; ++i) {
            buf[i] = ch;
        }
        return new String(buf);
    }

    /**
     * 获取字符串长度
     * @param cs 字符串
     * @return 字符串长度
     */
    public static int length(final CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

}
