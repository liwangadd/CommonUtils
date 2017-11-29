package com.windylee.utilcode;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public final class EmptyUtils {

    private EmptyUtils() {
        throw new UnsupportedOperationException("the emptyUtils cannot be initialed");
    }

    /**
     * 检查给定对象是否为空
     * 传入对象不能为null
     * 字符串，数组，集合类，Map长度不能为0
     *
     * @param obj 要判空的对象
     * @return
     */
    public static boolean isEmpty(final Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String && ((String) obj).length() == 0) {
            return true;
        }
        if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            return true;
        }
        if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;
        }
        if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 判断给定对象是否不为空
     *
     * @param obj 判空对象
     * @return
     */
    public static boolean isNotEmpty(final Object obj) {
        return !isEmpty(obj);
    }

}
