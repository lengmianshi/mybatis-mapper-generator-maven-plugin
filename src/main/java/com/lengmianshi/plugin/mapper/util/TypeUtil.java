package com.lengmianshi.plugin.mapper.util;

import com.lengmianshi.plugin.mapper.model.JavaType;

import java.util.HashMap;
import java.util.Map;

public class TypeUtil {

    /**
     * 数据库类型对应的Java类型
     * 参考：https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-type-conversions.html
     */
    private static final Map<String, JavaType> DB_JAVA_TYPE_MAP = new HashMap() {{
        put("bit", JavaType.BOOLEAN);
        put("tinyint", JavaType.INTEGER);
        put("smallint", JavaType.INTEGER);
        put("mediumint", JavaType.INTEGER);
        put("int", JavaType.INTEGER);
        put("integer", JavaType.INTEGER);
        put("bigint", JavaType.LONG);
        put("float", JavaType.FLOAT);
        put("double", JavaType.DOUBLE);
        put("decimal", JavaType.BIG_DECIMAL);
        put("date", JavaType.DATE);
        put("datetime", JavaType.DATE);
        put("timestamp", JavaType.DATE);
        put("time", JavaType.DATE);
        put("year", JavaType.INTEGER);
        put("char", JavaType.STRING);
        put("varchar", JavaType.STRING);
        put("binary", JavaType.BINARY);
        put("varbinary", JavaType.BINARY);
        put("blob", JavaType.BLOB);
        put("tinyblob", JavaType.BLOB);
        put("mediumblob", JavaType.BLOB);
        put("longblob", JavaType.BLOB);
        put("text", JavaType.STRING);
        put("tinytext", JavaType.STRING);
        put("mediumtext", JavaType.STRING);
        put("longtext", JavaType.STRING);
        put("json", JavaType.STRING);
        put("longvarchar", JavaType.STRING);
        put("numeric", JavaType.BIG_DECIMAL);
        put("real", JavaType.FLOAT);
        put("longvarbinary", JavaType.BINARY);
        put("clob", JavaType.CLOB);
        put("array", JavaType.ARRAY);
        put("struct", JavaType.STRUCT);
    }};

    /**
     * 数据库类型对应的JDBC类型
     * 参考：https://dev.mysql.com/doc/connector-j/8.0/en/connector-j-reference-type-conversions.html
     */
    private static final Map<String, String> DB_JDBC_TYPE_MAP = new HashMap() {{
        put("bit", "BIT");
        put("tinyint", "INTEGER");
        put("smallint", "INTEGER");
        put("mediumint", "INTEGER");
        put("int", "INTEGER");
        put("integer", "INTEGER");
        put("bigint", "BIGINT");
        put("float", "FLOAT");
        put("double", "DOUBLE");
        put("decimal", "DECIMAL");
        put("date", "TIMESTAMP");
        put("datetime", "TIMESTAMP");
        put("timestamp", "TIMESTAMP");
        put("time", "TIMESTAMP");
        put("year", "YEAR");
        put("char", "CHAR");
        put("varchar", "VARCHAR");
        put("binary", "BINARY");
        put("varbinary", "BINARY");
        put("blob", "BLOB");
        put("tinyblob", "TINYBLOB");
        put("mediumblob", "MEDIUMBLOB");
        put("longblob", "LONGBLOB");
        put("text", "LONGVARCHAR");
        put("tinytext", "LONGVARCHAR");
        put("mediumtext", "LONGVARCHAR");
        put("longtext", "LONGVARCHAR");
        put("json", "JSON");
        put("longvarchar", "LONGVARCHAR");
        put("numeric", "NUMERIC");
        put("real", "REAL");
        put("longvarbinary", "LONGVARBINARY");
        put("clob", "CLOB");
        put("array", "ARRAY");
        put("struct", "STRUCT");
    }};

    public static JavaType getJavaType(String dbType) {
        return DB_JAVA_TYPE_MAP.get(dbType);
    }

    public static String getJDBCType(String dbType) {
        return DB_JDBC_TYPE_MAP.get(dbType);
    }
}
