package com.lengmianshi.plugin.mapper.util;

import java.util.Arrays;
import java.util.Locale;
import java.util.stream.Collectors;

public class StringUtil {
    public static boolean isEmpty(String source) {
        return source == null || source.length() == 0;
    }

    public static boolean isNotEmpty(String source) {
        return !isEmpty(source);
    }

    public static boolean isBlank(String source) {
        return source == null || source.trim().length() == 0;
    }

    public static boolean isNotBlank(String source) {
        return !isBlank(source);
    }

    public static boolean contains(String source, String... keywords) {
        for (String keyword : keywords) {
            if (source.contains(keyword)) {
                return true;
            }
        }

        return false;
    }


    /**
     * 转为驼峰命名
     *
     * @param source
     * @return
     */
    public static String toCamelCase(String source) {
        return Arrays.stream(source.split("_"))
                .map(e -> {
                    if (e.length() > 1) {
                        return e.substring(0, 1).toUpperCase(Locale.ROOT) + e.substring(1).toLowerCase(Locale.ROOT);
                    }

                    return e.toUpperCase();
                }).collect(Collectors.joining(""));
    }

    public static void main(String[] args) {
        System.out.println(toCamelCase("project_work"));
    }
}
