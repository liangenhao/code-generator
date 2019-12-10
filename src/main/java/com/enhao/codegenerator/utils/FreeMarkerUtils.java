package com.enhao.codegenerator.utils;

import com.enhao.codegenerator.constant.Constants;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.IOException;

/**
 * FreeMarker工具类
 *
 * @author enhao
 */
@Slf4j
public class FreeMarkerUtils {

    /**
     * 配置
     */
    private static Configuration config = null;

    static {
        config = new Configuration(Configuration.getVersion());
        // 设置模板路径
        String classPath = FreeMarkerUtils.class.getResource("/").getPath();
        try {
            config.setDirectoryForTemplateLoading(new File(classPath + "/templates/"));
        } catch (IOException e) {
            log.error("freeMarker初始化失败", e);
        }
        // 设置字符集
        config.setDefaultEncoding(Constants.Encoding.UTF_8);
    }

    /**
     * 处理模版转成字符串
     * @param templateName 模板
     * @param dateModel 数据
     * @return
     */
    public static String processTemplateIntoString(String templateName, Object dateModel) {
        String templateContent = "";
        try {
            Template template = config.getTemplate(templateName);
            templateContent = FreeMarkerTemplateUtils.processTemplateIntoString(template, dateModel);
        } catch (IOException e) {
            throw new RuntimeException("获取freemarker模板失败", e);
        } catch (TemplateException e) {
            throw new RuntimeException("处理模版失败", e);
        }

        return templateContent;
    }
}
