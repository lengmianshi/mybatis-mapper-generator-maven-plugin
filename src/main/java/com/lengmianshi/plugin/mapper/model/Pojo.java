package com.lengmianshi.plugin.mapper.model;

import lombok.Data;

import java.util.List;

@Data
public class Pojo {
    //主键类型
    private String primaryKeyType;
    //表注释
    private String remark;
    //所有成员
    private List<Field> fieldList;

    //要导入的包
    private List<String> pkgList;
    //pojo对应的表名
    private String tableName;
    //pojo的类名
    private String className;
}
