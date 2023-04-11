package com.lengmianshi.plugin.mapper.util;

import java.io.*;

public class FileUtil {

    /**
     * 删除文件中的空行
     *
     * @param file
     * @param startKeyword          从包含某关键字的行作为起始行
     * @param endKeyword            从包含某关键字的行作为结束行
     * @param appendEmptyRowKeyword 如果某行包含某关键字，则在它行后面追加一个空白行
     */
    public static void removeEmptyRows(File file, String startKeyword, String endKeyword, String appendEmptyRowKeyword) throws Exception {
        BufferedReader br = null;
        String tmp;
        StringBuilder sb = new StringBuilder();
        FileInputStream in = new FileInputStream(file);
        br = new BufferedReader(new InputStreamReader(in, "utf-8"));

        boolean isStart = false;
        String row = null;
        while ((tmp = br.readLine()) != null) {
            if (tmp != null) {
                if (StringUtil.isNotEmpty(startKeyword) && tmp.contains(startKeyword)) {
                    isStart = true;
                }

                if (StringUtil.isNotEmpty(endKeyword) && tmp.contains(endKeyword)) {
                    isStart = false;
                }
            }

            row = tmp == null ? "" : tmp;

            if (!isStart) {
                sb.append(row + "\n");
                continue;
            }

            if (StringUtil.isNotBlank(tmp)) {
                sb.append(tmp + "\n");
            }

            if (StringUtil.isNotEmpty(appendEmptyRowKeyword) && row.contains(appendEmptyRowKeyword)) {
                sb.append("\n");
            }

        }
        in.close();

        FileCopyUtil.copy(new ByteArrayInputStream(sb.toString().getBytes()), new FileOutputStream(file));
    }


    /**
     * 替换文件内容
     *
     * @param file
     * @param startKeyword
     * @param endKeyword
     * @param replaceContent
     */
    public static void replaceContent(File file, String startKeyword, String endKeyword, String replaceContent) throws Exception {
        BufferedReader br = null;
        String tmp;
        StringBuilder sb = new StringBuilder();
        FileInputStream in = new FileInputStream(file);
        br = new BufferedReader(new InputStreamReader(in, "utf-8"));
        boolean isReplace = false;
        boolean isStart = false;
        String row = null;
        while ((tmp = br.readLine()) != null) {
            if (tmp != null) {
                if (!isReplace && StringUtil.isNotEmpty(startKeyword) && tmp.contains(startKeyword)) {
                    isStart = true;
                }

                if (StringUtil.isNotEmpty(endKeyword) && tmp.contains(endKeyword)) {
                    isStart = false;
                    if (!isReplace) {
                        sb.append(" " + replaceContent + "\n");

                    }else {
                        sb.append(tmp + "\n");
                    }

                    isReplace = true;
                    continue;
                }
            }

            row = tmp == null ? "" : tmp;

            if (!isStart) {
                sb.append(row + "\n");
            }
        }

        in.close();
        FileCopyUtil.copy(new ByteArrayInputStream(sb.toString().getBytes()), new FileOutputStream(file));
    }

}
