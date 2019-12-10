package com.enhao.codegenerator.model;

import lombok.Data;

/**
 * 基础配置
 *
 * @author enhao
 */
@Data
public class BasicConfig {

    /**
     * 项目名
     */
    private String projectName;

    /**
     * 包名前缀
     */
    private String packageNamePrefix;

    /**
     * 接口描述
     */
    private String interfaceDesc;

    /**
     * 接口前缀
     */
    private String interfacePrefix;

    /**
     * 创建人
     */
    private String creatorName;

    /**
     * 创建时间 YYYY/MM/dd HH:mm
     */
    private String createTime;
}
