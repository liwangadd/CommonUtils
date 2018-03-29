package com.windylee.utilcode;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public final class FileIOUtils {

    private FileIOUtils() {
        throw new UnsupportedOperationException("the FileIOUtils cannot be initialed");
    }

    private static int bufferSize = 8192;

    /**
     * 写入输入流内容到文件
     *
     * @param filePath 文件路径
     * @param is       输入流
     * @return 是否写入成功
     */
    public static boolean writeFileFromInputStream(final String filePath, final InputStream is) {
        return writeFileFromInputStream(getFileByPath(filePath), is, false);
    }

    /**
     * 写入输入流内容到文件
     *
     * @param filePath 文件路径
     * @param is       输入流
     * @param append   是否是追加内容
     * @return 是否写入成功
     */
    public static boolean writeFileFromInputStream(final String filePath, final InputStream is, final boolean append) {
        return writeFileFromInputStream(getFileByPath(filePath), is, append);
    }

    /**
     * 写入输入流内容到文件
     *
     * @param file 文件引用
     * @param is   输入流
     * @return 是否写入成功
     */
    public static boolean writeFileFromInputStream(final File file, final InputStream is) {
        return writeFileFromInputStream(file, is, false);
    }

    /**
     * 写入输入流内容到文件
     *
     * @param file   文件引用
     * @param is     输入流
     * @param append 是否是追加内容
     * @return 是否写入成功
     */
    public static boolean writeFileFromInputStream(final File file, final InputStream is, final boolean append) {
        if (!createOrExistsFile(file) || is == null) return false;
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file, append));
            byte data[] = new byte[bufferSize];
            int len;
            while ((len = is.read(data, 0, bufferSize)) != -1) {
                os.write(data, 0, len);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            CloseUtils.closeIO(os, is);
        }
    }

    /**
     * 写字节数组内容到文件
     *
     * @param filePath 文件路径
     * @param bytes    字节数组
     * @return 是否写入成功
     */
    public static boolean writeFileFromBytesByStream(final String filePath, final byte[] bytes) {
        return writeFileFromBytesByStream(getFileByPath(filePath), bytes, false);
    }

    /**
     * 写字节数组内容到文件
     *
     * @param filePath 文件路径
     * @param bytes    字节数组
     * @param append   是否是追加
     * @return 是否写入成功
     */
    public static boolean writeFileFromBytesByStream(final String filePath, final byte[] bytes, boolean append) {
        return writeFileFromBytesByStream(getFileByPath(filePath), bytes, append);
    }

    /**
     * 写字节数组内容到文件
     *
     * @param file  文件引用
     * @param bytes 字节数组
     * @return 是否写入成功
     */
    public static boolean writeFileFromBytesByStream(final File file, final byte[] bytes) {
        return writeFileFromBytesByStream(file, bytes, false);
    }

    /**
     * 写字节数组内容到文件
     *
     * @param file   文件引用
     * @param bytes  字节数组
     * @param append 是否是追加
     * @return 是否写入成功
     */
    public static boolean writeFileFromBytesByStream(final File file, final byte[] bytes, boolean append) {
        if (!createOrExistsDir(file) || bytes == null) return false;
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file, append));
            bos.write(bytes);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            CloseUtils.closeIO(bos);
        }
    }

    /**
     * 写字节数组到文件
     *
     * @param filePath 文件路径
     * @param bytes    字节数组
     * @param isForce
     * @return 是否写入成功
     */
    public static boolean writeFileFromBytesByChannel(final String filePath, final byte[] bytes, final boolean isForce) {
        return writeFileFromBytesByChannel(getFileByPath(filePath), bytes, false, isForce);
    }

    /**
     * 写字节数组到文件
     *
     * @param filePath 文件路径
     * @param bytes    字节数组
     * @param append   是否是追加
     * @param isForce
     * @return 是否写入成功
     */
    public static boolean writeFileFromBytesByChannel(final String filePath, final byte[] bytes, final boolean append, final boolean isForce) {
        return writeFileFromBytesByChannel(getFileByPath(filePath), bytes, append, isForce);
    }

    /**
     * 写字节数组到文件
     *
     * @param file    文件引用
     * @param bytes   字节数组
     * @param isForce
     * @return 是否写入成功
     */
    public static boolean writeFileFromBytesByChannel(final File file, final byte[] bytes, final boolean isForce) {
        return writeFileFromBytesByChannel(file, bytes, false, isForce);
    }

    /**
     * 写字节数组到文件
     *
     * @param file    文件路径
     * @param bytes   字节数组
     * @param append  是否是追加
     * @param isForce
     * @return 是否写入成功
     */
    public static boolean writeFileFromBytesByChannel(final File file, final byte[] bytes, final boolean append, final boolean isForce) {
        if (bytes == null) return false;
        FileChannel fc = null;
        try {
            fc = new FileOutputStream(file, append).getChannel();
            fc.position(fc.size());
            fc.write(ByteBuffer.wrap(bytes));
            if (isForce) fc.force(true);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            CloseUtils.closeIO(fc);
        }
    }

    /**
     * 写字节数组到文件
     *
     * @param filePath 文件路径
     * @param bytes    字节数组
     * @param isForce
     * @return 是否写入成功
     */
    public static boolean writeFileFromBytesByMap(final String filePath, final byte[] bytes, final boolean isForce) {
        return writeFileFromBytesByMap(getFileByPath(filePath), bytes, false, isForce);
    }

    /**
     * 写字节数组到文件
     *
     * @param filePath 文件路径
     * @param bytes    字节数组
     * @param isAppend 是否是追加
     * @param isForce
     * @return 是否写入成功
     */
    public static boolean writeFileFromBytesByMap(final String filePath, final byte[] bytes, boolean isAppend, final boolean isForce) {
        return writeFileFromBytesByMap(getFileByPath(filePath), bytes, isAppend, isForce);
    }

    /**
     * 写字节数组到文件
     *
     * @param file    文件引用
     * @param bytes   字节数组
     * @param isForce
     * @return 是否写入成功
     */
    public static boolean writeFileFromBytesByMap(final File file, final byte[] bytes, final boolean isForce) {
        return writeFileFromBytesByMap(file, bytes, false, isForce);
    }

    /**
     * 写字节数组到文件
     *
     * @param file     文件引用
     * @param bytes    字节数组
     * @param isAppend 是否是追加
     * @param isForce
     * @return 是否写入成功
     */
    public static boolean writeFileFromBytesByMap(final File file, final byte[] bytes, final boolean isAppend, final boolean isForce) {
        if (bytes == null || !createOrExistsFile(file)) return false;
        FileChannel fc = null;
        try {
            fc = new FileOutputStream(file, isAppend).getChannel();
            MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_WRITE, fc.size(), bytes.length);
            mbb.put(bytes);
            if (isForce) mbb.force();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            CloseUtils.closeIO(fc);
        }
    }

    /**
     * 写字符串到文件
     *
     * @param filePath 文件路径
     * @param content  字符串
     * @return 是否写入成功
     */
    public static boolean writeFileFromString(final String filePath, final String content) {
        return writeFileFromString(getFileByPath(filePath), content, false);
    }

    /**
     * 写字符串到文件
     *
     * @param filePath 文件路径
     * @param content  字符串
     * @param isAppend 是否是追加
     * @return 是否写入成功
     */
    public static boolean writeFileFromString(final String filePath, final String content, boolean isAppend) {
        return writeFileFromString(getFileByPath(filePath), content, isAppend);
    }

    /**
     * 写字符串到文件
     *
     * @param file    文件引用
     * @param content 字符串
     * @return 是否写入成功
     */
    public static boolean writeFileFromString(final File file, final String content) {
        return writeFileFromString(file, content, false);
    }

    /**
     * 写字符串到文件
     *
     * @param file     文件引用
     * @param content  字符串
     * @param isAppend 是否是追加
     * @return 是否写入成功
     */
    public static boolean writeFileFromString(final File file, final String content, boolean isAppend) {
        if (content == null || !createOrExistsFile(file)) return false;
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, isAppend));
            bw.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            CloseUtils.closeIO(bw);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // the divide line of write and read
    ///////////////////////////////////////////////////////////////////////////

    /**
     * 按行读取文件内容到string集合中
     *
     * @param filePath 文件路径
     * @return string集合
     */
    public static List<String> readFile2List(final String filePath) {
        return readFile2List(getFileByPath(filePath), null);
    }

    /**
     * 按行读取文件内容到string集合中
     *
     * @param filePath    文件路径
     * @param charsetName 字符编码方式
     * @return string集合
     */
    public static List<String> readFile2List(final String filePath, final String charsetName) {
        return readFile2List(getFileByPath(filePath), charsetName);
    }

    /**
     * 按行读取文件内容到string集合中
     *
     * @param file 文件引用
     * @return string集合
     */
    public static List<String> readFile2List(final File file) {
        return readFile2List(file, 0, 0x7FFFFFFF, null);
    }

    /**
     * 按行读取文件内容到string集合中
     *
     * @param file        文件引用
     * @param charsetName 字符编码方式
     * @return string集合
     */
    public static List<String> readFile2List(final File file, final String charsetName) {
        return readFile2List(file, 0, 0x7FFFFFFF, charsetName);
    }

    /**
     * 读取文件指定行内容到string集合中
     *
     * @param filePath 文件路径
     * @param st       起始行
     * @param end      结束行
     * @return stirng集合
     */
    public static List<String> readFile2List(final String filePath, final int st, final int end) {
        return readFile2List(getFileByPath(filePath), st, end, null);
    }

    /**
     * 读取文件指定行内容到string集合中
     *
     * @param filePath    文件路径
     * @param st          起始行
     * @param end         结束行
     * @param charsetName 字符编码方式
     * @return stirng集合
     */
    public static List<String> readFile2List(final String filePath, final int st, final int end, final String charsetName) {
        return readFile2List(getFileByPath(filePath), st, end, charsetName);
    }

    /**
     * 读取文件指定行内容到string集合中
     *
     * @param file 文件引用
     * @param st   起始行
     * @param end  结束行
     * @return stirng集合
     */
    public static List<String> readFile2List(final File file, final int st, final int end) {
        return readFile2List(file, st, end, null);
    }

    /**
     * 读取文件指定行内容到string集合中
     *
     * @param file        文件引用
     * @param st          起始行
     * @param end         结束行
     * @param charsetName 字符编码方式
     * @return stirng集合
     */
    public static List<String> readFile2List(final File file, final int st, final int end, final String charsetName) {
        if (!isFileExists(file)) return null;
        if (st > end) return null;
        BufferedReader reader = null;
        try {
            String line;
            int curLine = 1;
            List<String> result = new ArrayList<>();
            if (isSpace(charsetName)) {
                reader = new BufferedReader(new FileReader(file));
            } else {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
            }
            while ((line = reader.readLine()) != null) {
                if (curLine > end) break;
                if (st <= curLine && curLine <= end) result.add(line);
                ++curLine;
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtils.closeIO(reader);
        }
    }

    /**
     * 读取文件内容
     *
     * @param filePath 文件路径
     * @return 文件内容字符串
     */
    public static String readFile2String(final String filePath) {
        return readFile2String(getFileByPath(filePath), null);
    }

    /**
     * 读取文件内容
     *
     * @param filePath    文件路径
     * @param charsetName 字符编码方式
     * @return 文件内容字符串
     */
    public static String readFile2String(final String filePath, final String charsetName) {
        return readFile2String(getFileByPath(filePath), charsetName);
    }

    /**
     * 读取文件内容
     *
     * @param file 文件引用
     * @return 文件内容字符串
     */
    public static String readFile2String(final File file) {
        return readFile2String(file, null);
    }

    /**
     * 读取文件内容
     *
     * @param file        文件引用
     * @param charsetName 字符编码方式
     * @return 文件内容字符串
     */
    public static String readFile2String(final File file, final String charsetName) {
        if (!isFileExists(file)) return null;
        BufferedReader reader = null;
        try {
            StringBuilder sb = new StringBuilder();
            if (isSpace(charsetName)) {
                reader = new BufferedReader(new FileReader(file));
            } else {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
            }
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtils.closeIO(reader);
        }
    }

    /**
     * 读取文件内容
     *
     * @param filePath 文件路径
     * @return 文件内容字节数组
     */
    public static byte[] readFile2BytesByStream(final String filePath) {
        return readFile2BytesByStream(getFileByPath(filePath));
    }

    /**
     * 读取文件内容
     *
     * @param file 文件引用
     * @return 文件内容字节数组
     */
    public static byte[] readFile2BytesByStream(final File file) {
        if (!isFileExists(file)) return null;
        FileInputStream fis = null;
        ByteArrayOutputStream os = null;
        try {
            fis = new FileInputStream(file);
            os = new ByteArrayOutputStream();
            byte[] buffer = new byte[bufferSize];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            return os.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtils.closeIO(fis, os);
        }
    }

    /**
     * 读取文件内容
     *
     * @param filePath 文件路径
     * @return 文件内容字节数组
     */
    public static byte[] readFile2BytesByChannel(final String filePath) {
        return readFile2BytesByChannel(getFileByPath(filePath));
    }

    /**
     * 读取文件内容
     *
     * @param file 文件引用
     * @return 文件内容字节数组
     */
    public static byte[] readFile2BytesByChannel(final File file) {
        if (!isFileExists(file)) return null;
        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(file, "r").getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) fc.size());
            while (true) {
                if (!((fc.read(byteBuffer)) > 0)) break;
            }
            return byteBuffer.array();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtils.closeIO(fc);
        }
    }

    /**
     * 读取文件内容
     *
     * @param filePath 文件路径
     * @return 文件内容字节数组
     */
    public static byte[] readFile2BytesByMap(final String filePath) {
        return readFile2BytesByMap(getFileByPath(filePath));
    }

    /**
     * 读取文件内容
     *
     * @param file 文件引用
     * @return 文件内容字节数组
     */
    public static byte[] readFile2BytesByMap(final File file) {
        if (!isFileExists(file)) return null;
        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(file, "r").getChannel();
            int size = (int) fc.size();
            MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, size).load();
            byte[] result = new byte[size];
            mbb.get(result, 0, size);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            CloseUtils.closeIO(fc);
        }
    }

    /**
     * 设置缓冲数组大小
     *
     * @param bufferSize 数组大小
     */
    public static void setBufferSize(int bufferSize) {
        FileIOUtils.bufferSize = bufferSize;
    }

    /**
     * 判断文件是否存在，不存在则创建该文件
     *
     * @param filePath 文件路径
     * @return 存在or创建成功： true；else false
     */
    public static boolean createOrExistsFile(final String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

    /**
     * 判断文件是否存在，不存在则创建该文件
     *
     * @param file 文件引用
     * @return 存在or创建成功： true；else false
     */
    private static boolean createOrExistsFile(File file) {
        if (file == null) return false;
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断文件夹是否存在，不存在则创建该文件夹
     *
     * @param dir 文件夹路径
     * @return 存在or创建成功： true；else false
     */
    private static boolean createOrExistsDir(File dir) {
        return dir != null && (dir.exists() ? dir.isDirectory() : dir.mkdir());
    }

    /**
     * 判断文件是否存在
     *
     * @param file 文件引用
     * @return 存在：true；else false
     */
    public static boolean isFileExists(final File file) {
        return null != file && file.exists();
    }

    /**
     * 获取路径指向的文件
     *
     * @param filePath 文件路径
     * @return 指向的文件
     */
    private static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    /**
     * 判断是否为空字符串
     *
     * @param s 字符串
     * @return 字符串为空：true；else false
     */
    private static boolean isSpace(final String s) {
        if (s == null || s.length() == 0) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
