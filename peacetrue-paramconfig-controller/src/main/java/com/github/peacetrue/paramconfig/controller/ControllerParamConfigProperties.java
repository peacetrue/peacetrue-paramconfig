package com.github.peacetrue.paramconfig.controller;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 控制器配置
 *
 * @author xiayx
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "peacetrue.paramconfig")
public class ControllerParamConfigProperties {

    /** 参数配置模块链接 */
    private Urls urls;

    @Data
    public static class Urls {
        /** 新增地址 */
        private String add;
        /** 查询地址 */
        private String query;
        /** 查看地址 */
        private String get;
        /** 修改地址 */
        private String modify;
        /** 删除地址 */
        private String delete;
    }

}