package ${classMetaData.packageName};

<#list classMetaData.methodMetaDataList as methodMetaData>
import ${methodMetaData.methodParam.packageName}.${methodMetaData.methodParam.className}
import ${methodMetaData.methodReturn.packageName}.${methodMetaData.methodReturn.className}
</#list>
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 类作用: ${basicConfig.interfaceDesc}
 * 项目名称: ${basicConfig.projectName}
 * 包: ${classMetaData.packageName}
 * 类名称: ${classMetaData.className}
 * 类描述: ${basicConfig.interfaceDesc}
 * 创建人: ${basicConfig.creatorName}
 * 创建时间: ${basicConfig.createTime}
 */
@Api(value = "${basicConfig.interfaceDesc}", description = "${basicConfig.interfaceDesc}api", consumes = "application/json",
        tags = "${classMetaData.className}",  produces = "application/json")
@RequestMapping(value = "${basicConfig.interfacePrefix}")
public interface ${classMetaData.className} {

<#list classMetaData.methodMetaDataList as methodMetaData>
    @ApiOperation(value = "${methodMetaData.methodDesc}", notes = "${methodMetaData.methodDesc}", response = ${methodMetaData.methodReturn.className}.class)
    @ApiResponses(value = {@ApiResponse(code = 0, message = "成功", response = ${methodMetaData.methodReturn.className}.class)})
    @RequestMapping(value = "${methodMetaData.methodName}", method = RequestMethod.POST)
    ${methodMetaData.methodReturn.className} ${methodMetaData.methodName}(${methodMetaData.methodParam.className} req) throws Exception;

</#list>

}
