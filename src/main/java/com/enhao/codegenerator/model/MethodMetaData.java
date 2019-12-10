package com.enhao.codegenerator.model;

import lombok.Data;

import java.util.List;

/**
 * 方法元数据
 *
 * @author enhao
 */
@Data
public class MethodMetaData {

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
     */
    private boolean paginate;

}
