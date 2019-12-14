package com.enhao.codegenerator.model;

import lombok.Data;

/**
 * DTO对象元数据
 *
 * @author enhao
 */
@Data
public class DTOMetaData extends BasicMetaData {

    /**
     * 包名
     */
    private String packageName;

    /**
     * 类名
     */
    private String className;

    /**
     * 是否分页
     */
    private boolean paginate;

}
