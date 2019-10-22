package com.github.peacetrue.paramconfig.controller;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author xiayx
 */
@Configuration
@EnableConfigurationProperties(ControllerParamConfigProperties.class)
@PropertySource("classpath:/application-paramconfig-controller.properties")
public class ControllerParamConfigAutoConfiguration {

    @Bean
    public ParamConfigController paramConfigController() {
        return new ParamConfigController();
    }
}
