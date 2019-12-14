package com.enhao.codegenerator.controller;

import com.enhao.codegenerator.constant.Constants;
import com.enhao.codegenerator.model.BasicConfig;
import com.enhao.codegenerator.model.ClassMetaData;
import com.enhao.codegenerator.model.CommonResult;
import com.enhao.codegenerator.model.MethodMetaData;
import com.enhao.codegenerator.service.CodeGenerateService;
import com.enhao.codegenerator.utils.FreeMarkerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

/**
 * 代码生成Controller
 *
 * @author enhao
 */
@RestController
public class CodeGenerateController {

    @Autowired
    private CodeGenerateService codeGenerateService;

    @RequestMapping(value = "/interfaceCodeGenerate", method = RequestMethod.POST)
    public CommonResult<Boolean> interfaceCodeGenerate(MultipartFile file, BasicConfig basicConfig) throws Exception {
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(Constants.Format.YYYY_MM_dd_HH_mm));
        basicConfig.setCreateTime(createTime);
        // 解析excel，获取接口方法元数据
        List<MethodMetaData> methodMetaDataList = codeGenerateService.parseOutMethodMetaData(file, basicConfig);
        // List<DTOMetaData> methodParamList = methodMetaDataList.stream().map(MethodMetaData::getMethodParam).collect(Collectors.toList());
        // List<DTOMetaData> methodReturnList = methodMetaDataList.stream().map(MethodMetaData::getMethodReturn).collect(Collectors.toList());

        // 获取api类元数据
        ClassMetaData classMetaData = codeGenerateService.getClassMetaData(basicConfig, methodMetaDataList, Constants.ClassConfig.API_SUFFIX);
        HashMap<String, Object> dataModel = getDataModel(basicConfig, classMetaData);

        // Api模板转成文件
        FreeMarkerUtils.processTemplateInfoFile(Constants.Template.API, dataModel, classMetaData.getDirPath(), classMetaData.getFilePath());

        return CommonResult.success(true);
    }

    private HashMap<String, Object> getDataModel(BasicConfig basicConfig, ClassMetaData classMetaData) {
        return new HashMap<String, Object>() {{
            put("basicConfig", basicConfig);
            put("classMetaData", classMetaData);
        }};
    }
}
