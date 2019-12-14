package com.enhao.codegenerator;

import com.enhao.codegenerator.model.BasicConfig;
import com.enhao.codegenerator.model.ClassMetaData;
import com.enhao.codegenerator.model.DTOMetaData;
import com.enhao.codegenerator.model.MethodMetaData;
import com.enhao.codegenerator.utils.FreeMarkerUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author enhao
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class GenerateCodeTest {

    @Test
    public void processTemplateIntoString() {
        Map<String, Object> dataModel = getDataModel();

        String content = FreeMarkerUtils.processTemplateIntoString("interface/Api.ftl", dataModel);
        System.out.println(content);
    }

    private static Map<String, Object> getDataModel() {
        BasicConfig basicConfig = new BasicConfig();
        basicConfig.setInterfaceDesc("这是接口描述");
        basicConfig.setProjectName("这是项目名称");
        basicConfig.setCreatorName("enhao");
        basicConfig.setCreateTime("2019/12/10 22:16");
        basicConfig.setInterfacePrefix("specialSettle");

        ClassMetaData classMetaData = new ClassMetaData();
        classMetaData.setPackageName("com.enhao.test");
        classMetaData.setClassName("TemplateTest");
        List<MethodMetaData> methodMetaDatas = new LinkedList<>();

        MethodMetaData methodMetaData1 = new MethodMetaData();
        methodMetaData1.setMethodName("test1");
        DTOMetaData methodParam = new DTOMetaData();
        methodParam.setClassName("Test1Req");
        methodMetaData1.setMethodParam(methodParam);
        DTOMetaData methodReturn = new DTOMetaData();
        methodReturn.setClassName("Test1Resp");
        methodMetaData1.setMethodReturn(methodReturn);
        methodMetaData1.setMethodDesc("这是方法描述");

        MethodMetaData methodMetaData2 = new MethodMetaData();
        methodMetaData2.setMethodName("test2");
        DTOMetaData methodParam2 = new DTOMetaData();
        methodParam2.setClassName("Test2Req");
        methodMetaData2.setMethodParam(methodParam2);
        DTOMetaData methodReturn2 = new DTOMetaData();
        methodReturn2.setClassName("Test2Resp");
        methodMetaData2.setMethodReturn(methodReturn2);
        methodMetaData2.setMethodDesc("这是方法描述2");

        methodMetaDatas.add(methodMetaData1);
        methodMetaDatas.add(methodMetaData2);
        classMetaData.setMethodMetaDataList(methodMetaDatas);


        return new HashMap<String, Object>() {{
            put("basicConfig", basicConfig);
            put("classMetaData", classMetaData);
        }};
    }
}
