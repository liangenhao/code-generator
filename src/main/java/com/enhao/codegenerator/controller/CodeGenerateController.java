package com.enhao.codegenerator.controller;

import com.enhao.codegenerator.constant.Constants;
import com.enhao.codegenerator.model.*;
import com.enhao.codegenerator.service.CodeGenerateService;
import com.enhao.codegenerator.utils.FreeMarkerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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

        // 获取api类元数据
        ClassMetaData apiClassMetaData = codeGenerateService.getClassMetaData(basicConfig, methodMetaDataList, Constants.ClassConfig.API_SUFFIX, null);
        // Api模板转成文件
        HashMap<String, Object> dataModel = getClassDataModel(basicConfig, apiClassMetaData);
        FreeMarkerUtils.processTemplateInfoFile(Constants.Template.API, dataModel, apiClassMetaData.getDirPath(), apiClassMetaData.getFilePath());

        // 获取controller类元数据
        ClassMetaData controllerClassMetaData = codeGenerateService.getClassMetaData(basicConfig, methodMetaDataList, Constants.ClassConfig.CONTROLLER_SUFFIX, apiClassMetaData);
        // Controller模板转成文件
        dataModel = getClassDataModel(basicConfig, controllerClassMetaData);
        FreeMarkerUtils.processTemplateInfoFile(Constants.Template.CONTROLLER, dataModel, controllerClassMetaData.getDirPath(), controllerClassMetaData.getFilePath());

        // 获取sv类元数据
        ClassMetaData svClassMetaData = codeGenerateService.getClassMetaData(basicConfig, methodMetaDataList, Constants.ClassConfig.SV_SUFFIX, null);
        // SV模板转成文件
        dataModel = getClassDataModel(basicConfig, svClassMetaData);
        FreeMarkerUtils.processTemplateInfoFile(Constants.Template.SV, dataModel, svClassMetaData.getDirPath(), svClassMetaData.getFilePath());

        // 获取SVImpl类元数据
        ClassMetaData svImplClassMetaData = codeGenerateService.getClassMetaData(basicConfig, methodMetaDataList, Constants.ClassConfig.SV_IMPL_SUFFIX, svClassMetaData);
        // svImpl模板转成文件
        dataModel = getClassDataModel(basicConfig, svImplClassMetaData);
        FreeMarkerUtils.processTemplateInfoFile(Constants.Template.SV_IMPL, dataModel, svImplClassMetaData.getDirPath(), svImplClassMetaData.getFilePath());

        // Request DTO元数据
        List<DTOMetaData> methodParamList = methodMetaDataList.stream().map(MethodMetaData::getMethodParam).collect(Collectors.toList());
        methodParamList.forEach(dtoMetaData -> {
            // Request模板转成文件
            HashMap<String, Object> dtoDataModel = getDTODataModel(basicConfig, dtoMetaData);
            FreeMarkerUtils.processTemplateInfoFile(Constants.Template.REQUEST, dtoDataModel, dtoMetaData.getDirPath(), dtoMetaData.getFilePath());
        });

        // Response DTO元数据，
        methodMetaDataList.stream()
                .filter(methodMetaData -> {
                    // 需要排除 PageResponse baseResponse
                    boolean isBaseOrPageResponse = methodMetaData.getMethodReturn().getClassName().equals(Constants.ClassConfig.BASE_RESPONSE.classSuffix)
                            || methodMetaData.getMethodReturn().getClassName().equals(Constants.ClassConfig.PAGE_RESPONSE.classSuffix);
                    return !isBaseOrPageResponse;
                })
                .forEach(methodMetaData -> {
                    DTOMetaData methodReturn = methodMetaData.getMethodReturn();
                    HashMap<String, Object> dtoDataModel = getDTODataModel(basicConfig, methodReturn);
                    FreeMarkerUtils.processTemplateInfoFile(Constants.Template.RESPONSE, dtoDataModel, methodReturn.getDirPath(), methodReturn.getFilePath());
                });

        return CommonResult.success(true);
    }

    private HashMap<String, Object> getDTODataModel(BasicConfig basicConfig, DTOMetaData dtoMetaData) {
        return new HashMap<String, Object>() {{
            put("basicConfig", basicConfig);
            put("dtoMetaData", dtoMetaData);
        }};
    }

    private HashMap<String, Object> getClassDataModel(BasicConfig basicConfig, ClassMetaData classMetaData) {
        return new HashMap<String, Object>() {{
            put("basicConfig", basicConfig);
            put("classMetaData", classMetaData);
        }};
    }
}
