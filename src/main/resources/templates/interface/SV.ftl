package ${classMetaData.packageName};

<#if classMetaData.methodMetaDataList?size gt 0>
<#list classMetaData.methodMetaDataList as methodMetaData>
import ${methodMetaData.methodParam.packageName}.${methodMetaData.methodParam.className};
import ${methodMetaData.methodReturn.packageName}.${methodMetaData.methodReturn.className};
</#list>
</#if>

/**
 * 类作用: ${basicConfig.interfaceDesc}
 * 项目名称: ${basicConfig.projectName}
 * 包: ${classMetaData.packageName}
 * 类名称: ${classMetaData.className}
 * 类描述: ${basicConfig.interfaceDesc}
 * 创建人: ${basicConfig.creatorName}
 * 创建时间: ${basicConfig.createTime}
 */
public interface ${classMetaData.className} {

<#if classMetaData.methodMetaDataList?size gt 0>
<#list classMetaData.methodMetaDataList as methodMetaData>
    /**
     * 〈${methodMetaData.methodDesc}〉.
     * 〈${methodMetaData.methodDesc}〉
     *
     * @param req
     * @return
     * @throws Exception
     */
     ${methodMetaData.methodReturn.className} ${methodMetaData.methodName}(${methodMetaData.methodParam.className} req) throws Exception;

</#list>
</#if>
}