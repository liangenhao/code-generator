package com.enhao.codegenerator.model;

import lombok.Data;

import java.util.List;

/**
 * DTO对象元数据
 *
 * @author enhao
 */
@Data
public class DTOMetaData {

    /**
     * 包名
     */
    private String packageName;

    /**
     * 类名
     */
    private String className;

    /**
     * 字段
     */
    // private List<FieldMetaData> fieldList;
}
