package com.enhao.codegenerator.model;


import lombok.Data;

import java.util.List;

/**
 * 类元数据
 *
 * @author enhao
 */
@Data
public class ClassMetaData extends BasicMetaData {

    /**
     * 包名
     */
    private String packageName;

    /**
     * 类名
     */
    private String className;

    /**
     * 方法
     */
    private List<MethodMetaData> methodMetaDataList;
}
