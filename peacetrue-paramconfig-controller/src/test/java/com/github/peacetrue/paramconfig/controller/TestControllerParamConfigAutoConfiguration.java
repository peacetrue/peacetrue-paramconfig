package com.github.peacetrue.paramconfig.controller;

import com.github.peacetrue.paramconfig.service.TestServiceParamConfigAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.web.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author xiayx
 */
@Configuration
@ImportAutoConfiguration(classes = {
        TestServiceParamConfigAutoConfiguration.class,
        WebMvcAutoConfiguration.class,
        HttpMessageConvertersAutoConfiguration.class,
        ControllerParamConfigAutoConfiguration.class,
})
@PropertySource("classpath:application-paramconfig-controller-test.properties")
public class TestControllerParamConfigAutoConfiguration {
}
