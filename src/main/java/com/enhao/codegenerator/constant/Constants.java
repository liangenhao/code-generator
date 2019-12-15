package com.enhao.codegenerator.constant;

import java.io.File;

/**
 * 常量类
 *
 * @author enhao
 */
public class Constants {

    /**
     * 编码格式
     */
    public interface Encoding {
        /**
         * utf-8
         */
        String UTF_8 = "UTF-8";
    }

    /**
     * 各种符号
     */
    public interface Symbol {

        /**
         * 英文点
         */
        String DOT_EN = ".";
    }

    /**
     * 文件扩展名
     */
    public interface FileExtension {
        /**
         * java源文件扩展名
         */
        String JAVA = ".java";
    }

    /**
     * 代码路径
     */
    public interface CodePath {

        String SRC_MAIN_JAVA = "src" + File.separator + "main" + File.separator + "java";
    }

    /**
     * 占位符
     */
    public interface Placeholder {

        /**
         * 项目名称前缀
         */
        String $PROJECT_PREFIX = "${projectPrefix}";

        /**
         * 接口前缀
         */
        String $INTERFACE_PREFIX = "${interfacePrefix}";
    }

    /**
     * 模板
     */
    public interface Template {
        String API = "interface/Api.ftl";
        String CONTROLLER = "interface/Controller.ftl";
        String SV = "interface/SV.ftl";
        String SV_IMPL = "interface/SVImpl.ftl";
        String REQUEST = "interface/Request.ftl";
        String RESPONSE = "interface/Response.ftl";
    }

    public interface Format {

        String YYYY_MM_dd_HH_mm = "YYYY/MM/dd HH:mm";
    }


    /**
     * 类配置
     */
    public enum ClassConfig {
        REQ_SUFFIX("Req", "common.dto.request." + Placeholder.$INTERFACE_PREFIX, false, "Request类"),
        RESP_SUFFIX("Resp", "common.dto.response." + Placeholder.$INTERFACE_PREFIX, false, "Response类"),
        API_SUFFIX("Api", "api", true, "Api类"),
        CONTROLLER_SUFFIX("Controller", Placeholder.$PROJECT_PREFIX + ".controller", false, "Controller类"),
        SV_SUFFIX("SV", Placeholder.$PROJECT_PREFIX + ".service.interfaces", true, "SV类"),
        SV_IMPL_SUFFIX("SVImpl", Placeholder.$PROJECT_PREFIX + ".service.impl", false, "SVImpl类"),
        PAGE_RESPONSE("PageResponse", "common.dto.model", false, "PageResponse类"),
        BASE_RESPONSE("BaseResponse", "common.dto.model", false, "BaseResponse类");


        public String classSuffix; // 类名后缀
        public String packageSuffix; // 包名后缀
        public boolean interfaceClass; // 是否是接口类：true 接口类；false 非接口类
        public String desc; // 描述

        ClassConfig(String classSuffix, String packageSuffix, boolean interfaceClass, String desc) {
            this.classSuffix = classSuffix;
            this.packageSuffix = packageSuffix;
            this.interfaceClass = interfaceClass;
            this.desc = desc;
        }
    }


}
