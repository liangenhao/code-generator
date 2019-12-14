package com.enhao.codegenerator.model;

import lombok.*;

/**
 * 方法元数据
 *
 * @author enhao
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MethodMetaData extends BasicMetaData {

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 方法入参
     */
    private DTOMetaData methodParam;

    /**
     * 方法返回值
     */
    private DTOMetaData methodReturn;

    /**
     * 方法描述
     */
    private String methodDesc;

    /**
     * 是否分页
     * 表示该接口方法是否分页
     */
    private boolean paginate;

}
