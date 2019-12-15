package com.enhao.codegenerator.service;

import com.enhao.codegenerator.constant.Constants;
import com.enhao.codegenerator.model.BasicConfig;
import com.enhao.codegenerator.model.ClassMetaData;
import com.enhao.codegenerator.model.MethodMetaData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author enhao
 */
public interface CodeGenerateService {

    /**
     * 解析出方法元数据
     *
     * @param file        文件二进制
     * @param basicConfig 基本配置
     * @return
     */
    List<MethodMetaData> parseOutMethodMetaData(MultipartFile file, BasicConfig basicConfig);

    /**
     * 获取类元数据
     *
     * @param basicConfig        基本配置
     * @param methodMetaDataList 方法元数据集合
     * @param classConfig        类配置
     * @param implementsInterface 需要实现的接口
     * @return
     */
    ClassMetaData getClassMetaData(BasicConfig basicConfig, List<MethodMetaData> methodMetaDataList,
                                   Constants.ClassConfig classConfig, ClassMetaData implementsInterface);
}
