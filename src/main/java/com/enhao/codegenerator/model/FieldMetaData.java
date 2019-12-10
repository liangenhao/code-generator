package com.enhao.codegenerator.model;

import lombok.Data;

/**
 * 字段元数据
 *
 * @author enhao
 */
@Data
public class FieldMetaData {

    /**
     * 字段类型
     */
    private String fieldType;

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 字段备注
     */
    private String fieldRemark;
}
