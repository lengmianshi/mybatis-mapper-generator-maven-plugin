package com.lengmianshi.plugin.mapper;

import lombok.Data;

import java.util.List;

@Data
public class Config {
    //数据库连接信息
    private JdbcConnection jdbcConnection;
    //生成pojo配置
    private Package pojo;
    //生成dto配置
    private Package dto;
    //生成mapper配置
    private Package mapper;
    //生成xml配置
    private Package xml;
    //生成service配置
    private Package service;
    //生成serviceImpl配置
    private Package serviceImpl;
    //要生成的表
    private List<Table> tableList;

    @Data
    public static class Table {
        private String tableName;
        private String className;
        //是否生成Controller
        //private boolean generateController;
        //是否生成service
        private boolean generateService = true;
        //是否生成dto
        private boolean generateDTO = false;
        private String template;
    }

    @Data
    public static class Package {
        private String packageName;
        private String projectPath;
    }

    @Data
    public static class JdbcConnection {
        //数据库方言
        //private String dialect;
        private String driverClass;
        private String url;
        private String user;
        private String password;
        //数据库名
        private String database;
    }
}
