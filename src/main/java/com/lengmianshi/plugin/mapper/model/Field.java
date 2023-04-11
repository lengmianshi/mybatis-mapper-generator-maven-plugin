package com.lengmianshi.plugin.mapper.model;

import lombok.Data;

@Data
public class Field {
    //表字段名
    private String fieldName;
    //对应的java名称
    private String javaName;

    //表字段类型
    private String fieldType;
    //是否为主链
    private boolean isPrimaryKey;
    //字段说明
    private String remark;
    private String jdbcType;
}
