package ${dtoMetaData.packageName};

<#if dtoMetaData.paginate>
import ${basicConfig.packageNamePrefix}.common.dto.model.PageRequest;
</#if>
import lombok.Data;

/**
 * 类作用:
 * 项目名称: ${basicConfig.projectName}
 * 包: ${dtoMetaData.packageName}
 * 类名称: ${dtoMetaData.className}
 * 类描述:
 * 创建人: ${basicConfig.creatorName}
 * 创建时间: ${basicConfig.createTime}
 */
@Data
public class ${dtoMetaData.className} <#if dtoMetaData.paginate>extends PageRequest</#if> {


}