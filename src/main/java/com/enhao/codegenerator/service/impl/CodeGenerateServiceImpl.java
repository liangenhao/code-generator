package com.enhao.codegenerator.service.impl;

import com.enhao.codegenerator.constant.Constants;
import com.enhao.codegenerator.model.BasicConfig;
import com.enhao.codegenerator.model.ClassMetaData;
import com.enhao.codegenerator.model.DTOMetaData;
import com.enhao.codegenerator.model.MethodMetaData;
import com.enhao.codegenerator.service.CodeGenerateService;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author enhao
 */
@Service
public class CodeGenerateServiceImpl implements CodeGenerateService {


    @Override
    public List<MethodMetaData> parseOutMethodMetaData(MultipartFile file, BasicConfig basicConfig) {
        Workbook workbook = null;
        try {
            workbook = WorkbookFactory.create(file.getInputStream());
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("不支持解析该文件");
        } catch (InvalidFormatException | IOException e) {
            throw new RuntimeException("获取文件流失败");
        }
        Sheet sheet = workbook.getSheetAt(0);
        int rowAmount = sheet.getLastRowNum();
        if (rowAmount <= 0) {
            throw new RuntimeException("excel表没有数据");
        }

        int rowCount = 1;
        List<MethodMetaData> methodMetadataList = new ArrayList<>();
        while (rowCount <= rowAmount) {
            Row row = sheet.getRow(rowCount);
            if (null == row) {
                continue;
            }
            MethodMetaData methodMetaData = getMethodMetaData(row, basicConfig, rowCount);

            methodMetadataList.add(methodMetaData);

            rowCount++;
        }

        return methodMetadataList;
    }

    @Override
    public ClassMetaData getClassMetaData(BasicConfig basicConfig, List<MethodMetaData> methodMetaDataList,
                                          Constants.ClassConfig classConfig, ClassMetaData implementsInterface) {
        // 包名
        String packageName = getPackageName(basicConfig, classConfig);
        // 类名
        String className = getClassName(basicConfig.getInterfacePrefix(), classConfig);
        // 类目录路径
        String classDirPath = getClassDirPath(basicConfig.getFilePathPrefix(), packageName);
        // 类文件路径
        String classFilePath = getClassFilePath(classDirPath, className);

        ClassMetaData classMetadata = new ClassMetaData();
        classMetadata.setPackageName(packageName);
        classMetadata.setClassName(className);
        classMetadata.setMethodMetaDataList(methodMetaDataList);
        classMetadata.setImplementsInterface(implementsInterface);
        classMetadata.setDirPath(classDirPath);
        classMetadata.setFilePath(classFilePath);

        return classMetadata;
    }

    /**
     * 获取方法元数据
     *
     * @param row         行对象
     * @param basicConfig 基本配置
     * @return 方法元数据
     */
    private MethodMetaData getMethodMetaData(Row row, BasicConfig basicConfig, int rowCount) {
        int count = rowCount + 1;
        Cell methodNameCell = row.getCell(0);
        Cell methodDescCell = row.getCell(1);
        Cell paginateCell = row.getCell(2);
        Cell needReturnDataCell = row.getCell(3);

        // 方法名
        String methodName = methodNameCell.getStringCellValue();
        if (!StringUtils.hasText(methodName)) {
            throw new RuntimeException(String.format("第%s行方法名为空", count));
        }
        // 方法描述
        String methodDesc = methodDescCell.getStringCellValue();
        if (!StringUtils.hasText(methodDesc)) {
            throw new RuntimeException(String.format("第%s行方法描述为空", count));
        }
        // 是否分页
        boolean paginate = paginateCell.getBooleanCellValue();
        // 是否需要返回数据
        boolean needReturnData = needReturnDataCell.getBooleanCellValue();
        // 方法入参
        DTOMetaData methodParam = getReqOrRespDTOMetaData(methodName, paginate, basicConfig, Constants.ClassConfig.REQ_SUFFIX);
        // 方法返回值
        DTOMetaData methodReturn;
        if (paginate) {
            // 需要分页，返回 PageResponse
            methodReturn = getPageResponse(basicConfig);
        } else if (!needReturnData) {
            // 不需要分页，不需要返回数据，返回 BaseResponse
            methodReturn = getBaseResponse(basicConfig);
        } else {
            // 不需要分页，需要返回数据，返回 XxxResp
            methodReturn = getReqOrRespDTOMetaData(methodName, false, basicConfig, Constants.ClassConfig.RESP_SUFFIX);
        }

        MethodMetaData methodMetaData = new MethodMetaData();
        methodMetaData.setMethodName(methodName);
        methodMetaData.setMethodDesc(methodDesc);
        methodMetaData.setPaginate(paginate);
        methodMetaData.setMethodParam(methodParam);
        methodMetaData.setMethodReturn(methodReturn);

        return methodMetaData;
    }

    private DTOMetaData getBaseResponse(BasicConfig basicConfig) {
        return getDefaultDTOMetaData(basicConfig, Constants.ClassConfig.BASE_RESPONSE);
    }

    private DTOMetaData getPageResponse(BasicConfig basicConfig) {
        return getDefaultDTOMetaData(basicConfig, Constants.ClassConfig.PAGE_RESPONSE);
    }

    private DTOMetaData getDefaultDTOMetaData(BasicConfig basicConfig, Constants.ClassConfig classConfig) {
        String packageName = getPackageName(basicConfig, classConfig);

        DTOMetaData dtoMetaData = new DTOMetaData();
        dtoMetaData.setPackageName(packageName);
        dtoMetaData.setClassName(classConfig.classSuffix);
        dtoMetaData.setPaginate(true);

        return dtoMetaData;
    }

    /**
     * 获取Request/Response的DTO元数据
     *
     * @param methodName  接口方法名
     * @param basicConfig 基本配置
     * @param classConfig 配置枚举
     * @return dto元数据
     */
    private DTOMetaData getReqOrRespDTOMetaData(String methodName, boolean paginate, BasicConfig basicConfig, Constants.ClassConfig classConfig) {
        // dto包名
        String packageName = getPackageName(basicConfig, classConfig);
        // dto类名
        String className = getClassName(methodName, classConfig);
        // 类文件目录路径
        String classDirPath = getClassDirPath(basicConfig.getFilePathPrefix(), packageName);
        // 类文件路径
        String classFilePath = getClassFilePath(classDirPath, className);

        DTOMetaData dtoMetaData = new DTOMetaData();
        dtoMetaData.setPackageName(packageName);
        dtoMetaData.setClassName(className);
        dtoMetaData.setPaginate(paginate);
        dtoMetaData.setDirPath(classDirPath);
        dtoMetaData.setFilePath(classFilePath);

        return dtoMetaData;
    }

    /**
     * 获取类文件路径
     *
     * @param filePathPrefix 路径前缀
     * @param packageName    包名
     * @param className      类名
     * @return 类文件路径
     */
    private String getClassFilePath(String filePathPrefix, String packageName, String className) {
        // 全类名
        String fullClassName = packageName + Constants.Symbol.DOT_EN + className;
        // 将类名中的 . 替换为 文件分隔符
        String fullClassNamePath = fullClassName.replaceAll("\\.", File.separator) + Constants.FileExtension.JAVA;

        return filePathPrefix + File.separator + Constants.CodePath.SRC_MAIN_JAVA + File.separator + File.separator + fullClassNamePath;
    }

    /**
     * 获取类文件路径
     *
     * @param classDirPath 类文件目录路径
     * @param className    类名
     * @return 类文件路径
     */
    private String getClassFilePath(String classDirPath, String className) {
        // 文件目录路径 + 类名
        return classDirPath + File.separator + className + Constants.FileExtension.JAVA;
    }

    /**
     * 获取类文件目录路径
     *
     * @param filePathPrefix 路径前缀
     * @param packageName    包名
     * @return 类文件目录路径
     */
    private String getClassDirPath(String filePathPrefix, String packageName) {
        // 将包名中的 . 替换为 文件分隔符
        String packageNamePath = packageName.replace(".", File.separator);

        return filePathPrefix + File.separator + Constants.CodePath.SRC_MAIN_JAVA + File.separator + packageNamePath;
    }

    /**
     * 获取类名
     *
     * @param namePrefix  接口方法名
     * @param classConfig 配置枚举
     * @return 类名
     */
    private String getClassName(String namePrefix, Constants.ClassConfig classConfig) {
        // 首字母转成大写
        String firstWord = namePrefix.substring(0, 1).toUpperCase();
        // 剩余的字母
        String otherWord = namePrefix.substring(1);


        // 接口类前缀
        String interfacePrefix = "";
        if (classConfig.interfaceClass) {
            // 如果是接口类，拼接前缀
            interfacePrefix = "I";
        }

        // 拼接，并加上后缀
        return interfacePrefix + firstWord + otherWord + classConfig.classSuffix;
    }

    /**
     * 获取包名
     *
     * @param basicConfig 基础配置
     * @param classConfig 配置枚举
     * @return 包名
     */
    private String getPackageName(BasicConfig basicConfig, Constants.ClassConfig classConfig) {
        String packageName = basicConfig.getPackageNamePrefix() +
                Constants.Symbol.DOT_EN + classConfig.packageSuffix;
        if (packageName.contains(Constants.Placeholder.$PROJECT_PREFIX)) {
            packageName = packageName.replace(Constants.Placeholder.$PROJECT_PREFIX, basicConfig.getProjectPrefix());
        }
        if (packageName.contains(Constants.Placeholder.$INTERFACE_PREFIX)) {
            packageName = packageName.replace(Constants.Placeholder.$INTERFACE_PREFIX, basicConfig.getInterfacePrefix());
        }

        return packageName;
    }

}
