package com.lengmianshi.plugin.mapper.model;

import lombok.Data;

@Data
public class JavaType {
    //类名
    private String typeName;
    //包名
    private String pkg;
    //是否需要导包
    private boolean isImport;

    public static final JavaType STRING = new JavaType("String", "", false);
    public static final JavaType BIG_DECIMAL = new JavaType("BigDecimal", "java.math.BigDecimal", true);
    public static final JavaType BOOLEAN = new JavaType("Boolean", "", false);
    public static final JavaType INTEGER = new JavaType("Integer", "", false);
    public static final JavaType LONG = new JavaType("Long", "", false);
    public static final JavaType FLOAT = new JavaType("Float", "", false);
    public static final JavaType DOUBLE = new JavaType("Double", "", false);
    public static final JavaType BINARY = new JavaType("Byte[]", "", false);
    public static final JavaType DATE = new JavaType("Date", "java.util.Date", true);
    public static final JavaType URL = new JavaType("URL", "java.net.URL", true);
    public static final JavaType CLOB = new JavaType("Clob", "java.sql.Clob", true);
    public static final JavaType BLOB = new JavaType("Blob", "java.sql.Blob", true);
    public static final JavaType ARRAY = new JavaType("Array", "java.sql.Array", true);
    public static final JavaType STRUCT = new JavaType("Struct", "java.sql.Struct", true);

    private JavaType(String typeName, String pkg, boolean isImport) {
        this.typeName = typeName;
        this.pkg = pkg;
        this.isImport = isImport;
    }
}
