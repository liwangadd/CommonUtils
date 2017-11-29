package com.windylee.utilcode;

import java.io.Closeable;
import java.io.IOException;

public final class CloseUtils {

    private CloseUtils() {
        throw new UnsupportedOperationException("the CloseUtils cannot be initialed");
    }

    /**
     * 关闭输入输出流
     *
     * @param closeables 输入输出流数组
     */
    public static void closeIO(final Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 静默关闭输入输出流，即不打印错误信息
     *
     * @param closeables 输入输出流数组
     */
    public static void closeIOQuietly(final Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                }
            }
        }
    }

}
