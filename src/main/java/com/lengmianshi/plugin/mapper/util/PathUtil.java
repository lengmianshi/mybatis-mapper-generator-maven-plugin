package com.lengmianshi.plugin.mapper.util;

import java.io.File;
import java.util.regex.Matcher;

public class PathUtil {

    /**
     * 绝对路径 + 相对路径 计算最终的绝对路径
     *
     * @param basePath
     * @param relativePath
     * @return
     */
    public static String getAbsolutePath(String basePath, String relativePath) {
        File baseFile = new File(basePath);
        String[] array = relativePath.split("/");
        for (String rp : array) {
            if ("".equals(rp)) {
                continue;
            }
            if ("..".equals(rp)) {
                baseFile = baseFile.getParentFile();
            } else {
                baseFile = new File(baseFile, rp);
            }
        }
        return baseFile.getAbsolutePath();
    }

    /**
     * 将java中的包路径转为目录路径
     *
     * @param packageName
     * @return
     */
    public static String convertPath(String packageName) {
        return packageName.replaceAll("\\.", Matcher.quoteReplacement(File.separator));
    }
}
