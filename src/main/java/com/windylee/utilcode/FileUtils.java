package com.windylee.utilcode;

import java.io.*;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public final class FileUtils {

    private FileUtils() {
        throw new UnsupportedOperationException("the FileUtils cannot be initialed");
    }

    /**
     * 根据文件路径获取文件
     *
     * @param filePath 文件路径
     * @return 文件
     */
    public static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    /**
     * 判断文件路径指向的文件是否存在
     *
     * @param filePath 文件路径
     * @return true:存在 false:不存在
     */
    public static boolean isFileExists(final String filePath) {
        return isFileExists(getFileByPath(filePath));
    }

    /**
     * 判断文件是否存在
     *
     * @param file 文件
     * @return true:存在 false:不存在
     */
    public static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }

    /**
     * 文件重命名
     *
     * @param file    文件
     * @param newName 新名称
     * @return true:重命名成功 false:重命名失败
     */
    public static boolean rename(final File file, final String newName) {
        if (file == null || !file.exists() || isSpace(newName)) return true;
        if (file.getName().equals(newName)) return true;
        File newFile = new File(file.getParent() + File.separator + newName);
        return !newFile.exists() && file.renameTo(newFile);
    }

    /**
     * 判断文件是否是目录
     *
     * @param file 文件
     * @return true:是 false:不是
     */
    public static boolean isDir(final File file) {
        return file != null && file.exists() && file.isDirectory();
    }

    /**
     * 判断文件路径指向的文件是否是目录
     *
     * @param filePath 文件路径
     * @return true:是 false:不是
     */
    public static boolean isDir(final String filePath) {
        return isDir(getFileByPath(filePath));
    }

    /**
     * 判断是否是文件
     *
     * @param file 文件
     * @return true:是 false:不是
     */
    public static boolean isFile(final File file) {
        return file != null && file.exists() && file.isFile();
    }

    /**
     * 判断路径指向的是否是文件
     *
     * @param filePath 文件路径
     * @return true:是 false:不是
     */
    public static boolean isFile(final String filePath) {
        return isFile(getFileByPath(filePath));
    }

    /**
     * 判断路径指向的目录是否存在，不存在则创建该目录
     *
     * @param dirPath 文件路径
     * @return true:目录存在或创建目录成功 false:不存在或创建失败
     */
    public static boolean createOrExistsDir(final String dirPath) {
        return createOrExistsDir(getFileByPath(dirPath));
    }

    /**
     * 判断目录是否存在，不存在则创建该目录
     *
     * @param file 文件
     * @return true:目录存在或创建成功 false:不存在或创建失败
     */
    public static boolean createOrExistsDir(final File file) {
        return file != null && (file.exists() ? file.isDirectory() : file.mkdir());
    }

    /**
     * 判断路径指向的文件是否存在，不存在则创建该目录
     *
     * @param filePath 文件路径
     * @return true:目录存在或创建成功 false:不存在或创建失败
     */
    public static boolean createOrExistsFile(final String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

    /**
     * 判断目录是否存在，不存在则创建该文件
     *
     * @param file 文件
     * @return true:文件存在或创建成功 false:文件不存在或创建失败
     */
    public static boolean createOrExistsFile(final File file) {
        if (null == file) return false;
        if (file.exists()) return file.isFile();
        if (!createOrExistsDir(file.getParent())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断文件是否存在，存在则在创建之前删除
     *
     * @param filePath 文件路径
     * @return true:创建成功 false:创建失败
     */
    public static boolean createFileByDeleteOldFile(final String filePath) {
        return createFileByDeleteOldFile(getFileByPath(filePath));
    }

    /**
     * 判断文件是否存在，存在则在创建之前删除
     *
     * @param file 文件
     * @return true:创建成功 false:创建失败
     */
    public static boolean createFileByDeleteOldFile(final File file) {
        if (file == null) return false;
        if (file.exists() && !file.delete()) return false;
        if (!createOrExistsFile(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 复制或移动目录
     *
     * @param srcDirPath  源目录路径
     * @param destDirPath 目标目录路径
     * @param listener    是否覆盖监听器
     * @param isMove      是否移动
     * @return true:复制或移动成功 false:复制或移动失败
     */
    private static boolean copyOrMoveDir(final String srcDirPath, final String destDirPath, final OnReplaceListener listener, final boolean isMove) {
        return copyOrMoveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath), listener, isMove);
    }

    /**
     * 复制或移动目录
     *
     * @param srcDir   源目录
     * @param destDir  目标目录
     * @param listener 是否覆盖监听器
     * @param isMove   是否移动
     * @return true:复制或移动成功 false:复制或移动失败
     */
    private static boolean copyOrMoveDir(final File srcDir, final File destDir, final OnReplaceListener listener, final boolean isMove) {
        if (srcDir == null || destDir == null) return false;
        String srcPath = srcDir.getPath() + File.separator;
        String destPath = destDir.getPath() + File.separator;
        if (destPath.contains(srcPath)) return false;
        if (!srcDir.exists() || !srcDir.isDirectory()) return false;
        if (destDir.exists()) {
            if (listener.onReplace()) {
                if (!deleteAllInDir(destDir)) {
                    return false;
                }
            } else {
                return true;
            }
        }
        if (!createOrExistsDir(destDir)) return false;
        File[] files = srcDir.listFiles();
        for (File file : files) {
            File oneDestFile = new File(destPath + file.getName());
            if (file.isFile()) {
                if (!copyOrMoveFile(file, oneDestFile, listener, isMove)) return false;
            } else if (file.isDirectory()) {
                if (!copyOrMoveDir(file, oneDestFile, listener, isMove)) return false;
            }
        }
        return !isMove || deleteDir(srcDir);
    }

    /**
     * 复制或移动文件
     *
     * @param srcFilePath  源文件路径
     * @param destFilePath 目标文件路径
     * @param listener     是否覆盖监听器
     * @param isMove       是否移动
     * @return true:复制或移动成功 false:复制或移动失败
     */
    public static boolean copyOrMoveFile(final String srcFilePath, final String destFilePath, final OnReplaceListener listener, final boolean isMove) {
        return copyOrMoveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), listener, isMove);
    }

    /**
     * 复制或移动文件
     *
     * @param srcFile  源文件
     * @param destFile 目标文件
     * @param listener     是否覆盖监听器
     * @param isMove       是否移动
     * @return true:复制或移动成功 false:复制或移动失败
     */
    public static boolean copyOrMoveFile(final File srcFile, final File destFile, final OnReplaceListener listener, final boolean isMove) {
        if (srcFile == null || destFile == null) return false;
        if (srcFile.equals(destFile)) return false;
        if (!srcFile.exists() || !srcFile.isFile()) return false;
        if (destFile.exists()) {
            if (listener.onReplace()) {
                if (!destFile.delete()) {
                    return false;
                }
            } else {
                return true;
            }
        }
        if (!createOrExistsDir(destFile.getParent())) return false;
        try {
            return FileIOUtils.writeFileFromInputStream(destFile, new FileInputStream(srcFile), false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean copyFile(final String srcFilePath, final String destFilePath, final OnReplaceListener listener) {
        return copyFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), listener);
    }

    public static boolean copyFile(final File srcFile, final File destFile, final OnReplaceListener listener) {
        return copyOrMoveFile(srcFile, destFile, listener, false);
    }

    public static boolean copyDir(final String srcFilePath, final String destFilePath, final OnReplaceListener listener) {
        return copyDir(getFileByPath(srcFilePath), getFileByPath(destFilePath), listener);
    }

    public static boolean copyDir(final File srcDir, final File destFile, final OnReplaceListener listener) {
        return copyOrMoveDir(srcDir, destFile, listener, false);
    }

    public static boolean moveFile(final String srcFilePath, final String destFilePath, final OnReplaceListener listener) {
        return moveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), listener);
    }

    public static boolean moveFile(final File srcFile, final File destFile, final OnReplaceListener listener) {
        return copyOrMoveFile(srcFile, destFile, listener, true);
    }

    public static boolean moveDir(final String srcDirPath, final String destDirPath, final OnReplaceListener listener) {
        return moveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath), listener);
    }

    public static boolean moveDir(final File srcDir, final File destDir, OnReplaceListener listener) {
        return copyOrMoveDir(srcDir, destDir, listener, true);
    }

    /**
     * 删除给定目录下的所有文件
     *
     * @param filePath 给定目录路径
     * @return true:删除成功 false:删除失败
     */
    public static boolean deleteAllInDir(final String filePath) {
        return deleteAllInDir(getFileByPath(filePath));
    }

    /**
     * 删除给定目录项的所有文件
     *
     * @param destDir 给定目录
     * @return true:删除成功 false:删除失败
     */
    public static boolean deleteAllInDir(final File destDir) {
        return deleteFilesInDirWithFilter(destDir, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        });
    }

    public static boolean deleteFilesInDir(final String filePath) {
        return deleteFilesInDir(getFileByPath(filePath));
    }

    public static boolean deleteFilesInDir(final File file) {
        return deleteFilesInDirWithFilter(file, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.isFile();
            }
        });
    }

    /**
     * 删除给定目录下的符合条件的所有文件
     *
     * @param dirPath 给定目录路径
     * @param filter  条件过滤器
     * @return true:删除成功 false:删除失败
     */
    public static boolean deleteFilesInDirWithFilter(final String dirPath, final FileFilter filter) {
        return deleteFilesInDirWithFilter(getFileByPath(dirPath), filter);
    }

    /**
     * 删除给定目录下的符合条件的所有文件
     *
     * @param dir 给定目录
     * @param filter  条件过滤器
     * @return true:删除成功 false:删除失败
     */
    public static boolean deleteFilesInDirWithFilter(final File dir, final FileFilter filter) {
        if (dir == null) return false;
        if (!dir.exists()) return true;
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (filter.accept(file)) {
                    if (file.isDirectory()) {
                        if (!deleteDir(file)) return false;
                    } else if (file.isFile()) {
                        if (!deleteFile(file)) return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 删除给定文件
     *
     * @param filePath 文件路径
     * @return true:删除成功 false:删除失败
     */
    public static boolean deleteFile(final String filePath) {
        return deleteFile(getFileByPath(filePath));
    }

    /**
     * 删除给定文件
     *
     * @param file 文件
     * @return true:删除成功 false:删除失败
     */
    public static boolean deleteFile(File file) {
        return file != null && (!file.exists() || file.isFile() && file.delete());
    }

    /**
     * 删除给定目录
     *
     * @param dirPath 目录路径
     * @return true:删除成功 false:删除失败
     */
    public static boolean deleteDir(final String dirPath) {
        return deleteDir(getFileByPath(dirPath));
    }

    /**
     * 删除给定目录
     *
     * @param dir 目录
     * @return true:删除成功 false:删除失败
     */
    public static boolean deleteDir(final File dir) {
        if (dir == null) return false;
        if (!dir.exists()) return true;
        if (!dir.isDirectory()) return false;
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return true;
    }

    public static List<File> listFilesInDir(final String dirPath) {
        return listFilesInDir(getFileByPath(dirPath));
    }

    public static List<File> listFilesInDir(final File dir) {
        return listFilesInDir(dir, false);
    }

    public static List<File> listFilesInDir(final String dirPath, final boolean isRecursive) {
        return listFilesInDir(getFileByPath(dirPath), isRecursive);
    }

    public static List<File> listFilesInDir(final File dir, final boolean isRecursive) {
        return listFilesInDirWithFilter(dir, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        }, isRecursive);
    }

    public static List<File> listFilesInDirWithFilter(final String dirPath, final FileFilter filter) {
        return listFilesInDirWithFilter(getFileByPath(dirPath), filter, false);
    }

    public static List<File> listFilesInDirWithFilter(final File dir, final FileFilter filter) {
        return listFilesInDirWithFilter(dir, filter, false);
    }

    public static List<File> listFilesInDirWithFilter(final File dir, final FileFilter filter, final boolean isRecursive) {
        if (isDir(dir)) return null;
        List<File> result = new ArrayList<>();
        File[] files = dir.listFiles();
        for (File file : files) {
            if (filter.accept(file)) {
                result.add(file);
            }
            if (file.isDirectory() && isRecursive) {
                result.addAll(listFilesInDirWithFilter(file, filter, isRecursive));
            }
        }
        return result;
    }

    public static long getFileLastModified(final String filePath) {
        return getFileLastModified(getFileByPath(filePath));
    }

    public static long getFileLastModified(final File file) {
        if (file == null) return -1;
        return file.lastModified();
    }

    public static String getFileCharsetSimple(final String filePath) {
        return getFileCharsetSimple(getFileByPath(filePath));
    }

    public static String getFileCharsetSimple(final File file) {
        int p = 0;
        InputStream is = null;
        try {
            is = new FileInputStream(file);
            p = (is.read() << 8) + is.read();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIO(is);
        }
        switch (p) {
            case 0xefbb:
                return "UTF-8";
            case 0xfffe:
                return "Unicode";
            case 0xfeff:
                return "UTF-16BE";
            default:
                return "GBK";
        }
    }

    public static int getFileLines(final String filePath) {
        return getFileLines(getFileByPath(filePath));
    }

    public static int getFileLines(final File file) {
        int count = 1;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            while (reader.readLine() != null) {
                ++count;
            }
            return count;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIO(reader);
        }
        return -1;
    }

    public static long getFileLength(final String filePath) {
        return getFileLength(getFileByPath(filePath));
    }

    public static long getFileLength(final File file) {
        if (isFile(file)) return -1;
        return file.length();
    }

    public static long getDirLength(final String dirPath) {
        return getDirLength(getFileByPath(dirPath));
    }

    public static long getDirLength(final File dir) {
        if (!isDir(dir)) return -1;
        long length = 0;
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isFile()) {
                    length += file.length();
                } else {
                    length += getDirLength(file);
                }
            }
        }
        return length;
    }

    public static String getFileMD5ToString(final String filePath) {
        return getFileMD5ToString(getFileByPath(filePath));
    }

    public static String getFileMD5ToString(final File file) {
        return bytes2HexString(getFileMD5(file));
    }


    public static byte[] getFileMD5(final String filePath) {
        return getFileMD5(getFileByPath(filePath));
    }

    public static byte[] getFileMD5(final File file) {
        if (!isFile(file)) return null;
        DigestInputStream dis = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            MessageDigest md = MessageDigest.getInstance("MD5");
            dis = new DigestInputStream(fis, md);
            byte[] buffer = new byte[1024 * 256];
            while (true) {
                if (!(dis.read(buffer) > 0)) break;
            }
            md = dis.getMessageDigest();
            return md.digest();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIO(dis, fis);
        }
        return null;
    }

    public static String getDirName(final File file) {
        if (file == null) return null;
        return getDirName(file.getPath());
    }

    public static String getDirName(final String filePath) {
        if (filePath == null) return null;
        int lastIndex = filePath.lastIndexOf(File.separator);
        return lastIndex == -1 ? "" : filePath.substring(0, lastIndex + 1);
    }

    public static String getFileName(final File file) {
        if (file == null) return null;
        return getFileName(file.getPath());
    }

    public static String getFileName(final String filePath) {
        if (filePath == null) return null;
        int lastIndex = filePath.lastIndexOf(File.separator);
        return lastIndex == -1 ? filePath : filePath.substring(lastIndex + 1);
    }

    public static String getFileNameNoExtension(final File file) {
        if (file == null) return null;
        return getFileNameNoExtension(file.getPath());
    }

    public static String getFileNameNoExtension(final String filePath) {
        if (filePath == null) return null;
        int lastSep = filePath.lastIndexOf(File.separator);
        int lastDot = filePath.lastIndexOf(".");
        if (lastSep == -1) {
            return lastDot == -1 ? filePath : filePath.substring(0, lastDot);
        } else {
            if (lastDot == -1 || lastDot < lastSep) {
                return filePath.substring(lastSep + 1);
            }
        }
        return filePath.substring(lastSep + 1, lastDot);
    }

    public String getFileExtension(final File file) {
        if (file == null) return null;
        return getFileExtension(file.getPath());
    }

    public String getFileExtension(final String filePath) {
        if (filePath == null) return null;
        int lastDot = filePath.lastIndexOf(".");
        return lastDot == -1 ? "" : filePath.substring(lastDot + 1);
    }

    private static final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public interface OnReplaceListener {
        boolean onReplace();
    }

    private static boolean isSpace(String filePath) {
        if (filePath == null) return true;
        for (int i = 0, len = filePath.length(); i < len; ++i) {
            if (!Character.isWhitespace(filePath.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static String bytes2HexString(byte[] bytes) {
        if (bytes == null) return null;
        int len = bytes.length;
        if (len <= 0) return null;
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; ++i) {
            ret[j++] = hexDigits[bytes[i] >>> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

}
