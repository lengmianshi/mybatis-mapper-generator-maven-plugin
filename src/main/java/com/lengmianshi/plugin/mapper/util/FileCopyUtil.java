package com.lengmianshi.plugin.mapper.util;

import java.io.InputStream;
import java.io.OutputStream;

public class FileCopyUtil {

    public static String copyToString(InputStream in) throws Exception {
        StringBuffer sb = new StringBuffer();
        byte buffer[] = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = in.read(buffer, 0, 4096)) != -1) {//-1表示读取结束
            sb.append(new String(buffer, 0, bytesRead));
        }

        in.close();

        return sb.toString();
    }

    public static void copy(InputStream in, OutputStream out) throws Exception {
        int byteCount = 0;
        byte[] buffer = new byte[4096];
        for (int bytesRead = -1; (bytesRead = in.read(buffer)) != -1; byteCount += bytesRead) {
            out.write(buffer, 0, bytesRead);
        }

        out.flush();
        in.close();
        out.close();
    }
}
