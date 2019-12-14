package com.enhao.codegenerator.utils;

import com.enhao.codegenerator.constant.Constants;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.FileWriter;
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
            throw new RuntimeException("freeMarker初始化失败", e);
        }
        // 设置字符集
        config.setDefaultEncoding(Constants.Encoding.UTF_8);
    }

    /**
     * 处理模版转成字符串
     *
     * @param templateName 模板名
     * @param dateModel    数据
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

    /**
     * 处理模版转成文件
     *
     * @param templateName 模板名
     * @param dateModel    数据
     * @param filePath     文件路径
     * @return
     */
    public static File processTemplateInfoFile(String templateName, Object dateModel, String dirPath, String filePath) {
        File dirPathFile = new File(dirPath);
        if (!dirPathFile.exists()) {
            dirPathFile.mkdirs();
        }
        FileWriter out = null;
        File file = new File(filePath);
        try {
            out = new FileWriter(file);
            Template template = config.getTemplate(templateName);
            template.process(dateModel, out);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException("处理模版失败", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                log.error("关闭FileWriter失败", e);
            }
        }

        return file;
    }

}
