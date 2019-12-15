package ${classMetaData.packageName};

import ${classMetaData.implementsInterface.packageName}.${classMetaData.implementsInterface.className};
<#if classMetaData.methodMetaDataList?size gt 0>
<#list classMetaData.methodMetaDataList as methodMetaData>
import ${methodMetaData.methodParam.packageName}.${methodMetaData.methodParam.className};
import ${methodMetaData.methodReturn.packageName}.${methodMetaData.methodReturn.className};
</#list>
</#if>
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类作用: ${basicConfig.interfaceDesc}
 * 项目名称: ${basicConfig.projectName}
 * 包: ${classMetaData.packageName}
 * 类名称: ${classMetaData.className}
 * 类描述: ${basicConfig.interfaceDesc}
 * 创建人: ${basicConfig.creatorName}
 * 创建时间: ${basicConfig.createTime}
 */
@RestController
public class ${classMetaData.className} implements ${classMetaData.implementsInterface.className} {

<#if classMetaData.methodMetaDataList?size gt 0>
<#list classMetaData.methodMetaDataList as methodMetaData>
    @Override
    public ${methodMetaData.methodReturn.className} ${methodMetaData.methodName}(@RequestBody @Validated ${methodMetaData.methodParam.className} req) throws Exception {
        return null;
    }

</#list>
</#if>
}