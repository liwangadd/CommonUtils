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

    public static boolean writeFileFromInputStream(final String filePath, final InputStream is) {
        return writeFileFromInputStream(getFileByPath(filePath), is, false);
    }

    public static boolean writeFileFromInputStream(final String filePath, final InputStream is, final boolean append) {
        return writeFileFromInputStream(getFileByPath(filePath), is, append);
    }

    public static boolean writeFileFromInputStream(final File file, final InputStream is) {
        return writeFileFromInputStream(file, is, false);
    }

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

    public static boolean writeFileFromBytesByStream(final String filePath, final byte[] bytes) {
        return writeFileFromBytesByStream(getFileByPath(filePath), bytes, false);
    }

    public static boolean writeFileFromBytesByStream(final String filePath, final byte[] bytes, boolean append) {
        return writeFileFromBytesByStream(getFileByPath(filePath), bytes, append);
    }

    public static boolean writeFileFromBytesByStream(final File file, final byte[] bytes) {
        return writeFileFromBytesByStream(file, bytes, false);
    }

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

    public static boolean writeFileFromBytesByChannel(final String filePath, final byte[] bytes, final boolean isForce) {
        return writeFileFromBytesByChannel(getFileByPath(filePath), bytes, false, isForce);
    }

    public static boolean writeFileFromBytesByChannel(final String filePath, final byte[] bytes, final boolean append, final boolean isForce) {
        return writeFileFromBytesByChannel(getFileByPath(filePath), bytes, append, isForce);
    }

    public static boolean writeFileFromBytesByChannel(final File file, final byte[] bytes, final boolean isForce) {
        return writeFileFromBytesByChannel(file, bytes, false, isForce);
    }

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

    public static boolean writeFileFromBytesByMap(final String filePath, final byte[] bytes, final boolean isForce) {
        return writeFileFromBytesByMap(getFileByPath(filePath), bytes, false, isForce);
    }

    public static boolean writeFileFromBytesByMap(final String filePath, final byte[] bytes, boolean isAppend, final boolean isForce) {
        return writeFileFromBytesByMap(getFileByPath(filePath), bytes, isAppend, isForce);
    }

    public static boolean writeFileFromBytesByMap(final File file, final byte[] bytes, final boolean isForce) {
        return writeFileFromBytesByMap(file, bytes, false, isForce);
    }

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

    public static boolean writeFileFromString(final String filePath, final String content) {
        return writeFileFromString(getFileByPath(filePath), content, false);
    }

    public static boolean writeFileFromString(final String filePath, final String content, boolean isAppend) {
        return writeFileFromString(getFileByPath(filePath), content, isAppend);
    }

    public static boolean writeFileFromString(final File file, final String content) {
        return writeFileFromString(file, content, false);
    }

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

    public static List<String> readFile2List(final String filePath) {
        return readFile2List(getFileByPath(filePath), null);
    }

    public static List<String> readFile2List(final String filePath, final String charsetName) {
        return readFile2List(getFileByPath(filePath), charsetName);
    }

    public static List<String> readFile2List(final File file) {
        return readFile2List(file, 0, 0x7FFFFFFF, null);
    }

    public static List<String> readFile2List(final File file, final String charsetName) {
        return readFile2List(file, 0, 0x7FFFFFFF, charsetName);
    }

    public static List<String> readFile2List(final String filePath, final int st, final int end) {
        return readFile2List(getFileByPath(filePath), st, end, null);
    }

    public static List<String> readFile2List(final String filePath, final int st, final int end, final String charsetName) {
        return readFile2List(getFileByPath(filePath), st, end, charsetName);
    }

    public static List<String> readFile2List(final File file, final int st, final int end) {
        return readFile2List(file, st, end, null);
    }

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

    public static String readFile2String(final String filePath) {
        return readFile2String(getFileByPath(filePath), null);
    }

    public static String readFile2String(final String filePath, final String charsetName) {
        return readFile2String(getFileByPath(filePath), charsetName);
    }

    public static String readFile2String(final File file) {
        return readFile2String(file, null);
    }

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

    public static byte[] readFile2BytesByStream(final String filePath) {
        return readFile2BytesByStream(getFileByPath(filePath));
    }

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

    public static byte[] readFile2BytesByChannel(final String filePath) {
        return readFile2BytesByChannel(getFileByPath(filePath));
    }

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

    public static byte[] readFile2BytesByMap(final String filePath) {
        return readFile2BytesByMap(getFileByPath(filePath));
    }

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

    public static void setBufferSize(int bufferSize) {
        FileIOUtils.bufferSize = bufferSize;
    }

    public static boolean createOrExistsFile(final String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

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

    private static boolean createOrExistsDir(File dir) {
        return dir != null && (dir.exists() ? dir.isDirectory() : dir.mkdir());
    }

    public static boolean isFileExists(final File file) {
        return null != file && file.exists();
    }

    private static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    private static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
