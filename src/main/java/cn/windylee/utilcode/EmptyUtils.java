package cn.windylee.utilcode;

import com.sun.istack.internal.Nullable;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

public final class EmptyUtils {

    private EmptyUtils() {
        throw new UnsupportedOperationException("the emptyUtils cannot be initialed");
    }

    /**
     * Return whether object is empty.
     *
     * @param obj The object.
     * @return {@code true}: yes<br>{@code false}: no
     */
    public static boolean isEmpty(final Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            return true;
        }
        if (obj instanceof CharSequence && obj.toString().length() == 0) {
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

    public static boolean isEmpty(final CharSequence obj) {
        return obj == null || obj.toString().length() == 0;
    }

    public static boolean isEmpty(@Nullable final Collection<?> obj) {
        return obj == null || obj.isEmpty();
    }

    public static boolean isEmpty(final Map obj) {
        return obj == null || obj.isEmpty();
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
