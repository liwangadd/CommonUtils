package com.windylee.utilcode;

import com.sun.deploy.association.utility.DesktopEntryFile;

import java.io.*;

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

    public static boolean createOrExistsFile(final String filePath) {
        return createOrExistsFile(getFileByPath(filePath));
    }

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

    private static boolean copyOrMoveDir(final String srcDirPath, final String destDirPath, final OnReplaceListener listener, final boolean isMove) {
        return copyOrMoveDir(getFileByPath(srcDirPath), getFileByPath(destDirPath), listener, isMove);
    }

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

    public static boolean copyOrMoveFile(final String srcFilePath, final String destFilePath, final OnReplaceListener listener, final boolean isMove) {
        return copyOrMoveFile(getFileByPath(srcFilePath), getFileByPath(destFilePath), listener, isMove);
    }

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

    public static boolean deleteAllInDir(final String filePath) {
        deleteAllInDir(getFileByPath(filePath));
    }

    public static boolean deleteAllInDir(final File destDir) {
        deleteFilesInDirWithFilter(destDir, new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        })
    }

    public static boolean deleteFilesInDirWithFilter(final String dirPath, final FileFilter filter) {
        return deleteFilesInDirWithFilter(getFileByPath(dirPath), filter);
    }

    public static boolean deleteFilesInDirWithFilter(final File file, final FileFilter filter) {
        if (file == null || !file.exists()) return false;

    }

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

}
