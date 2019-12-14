package com.enhao.codegenerator.model;

import lombok.Data;

/**
 * 元数据
 *
 * @author enhao
 */
@Data
public abstract class BasicMetaData {

    /**
     * 目录路径
     */
    private String dirPath;

    /**
     * 最后生成的文件路径
     */
    private String filePath;
}
