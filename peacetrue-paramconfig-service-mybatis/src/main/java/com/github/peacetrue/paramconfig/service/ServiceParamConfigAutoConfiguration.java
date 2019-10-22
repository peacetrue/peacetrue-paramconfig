package com.github.peacetrue.paramconfig.service;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Objects;

/**
 * @author xiayx
 */
@Configuration
@EnableConfigurationProperties(ServiceParamConfigProperties.class)
@MapperScan(basePackageClasses = ServiceParamConfigAutoConfiguration.class, annotationClass = Mapper.class)
@PropertySource("classpath:/application-paramconfig-service.properties")
public class ServiceParamConfigAutoConfiguration {

    private ServiceParamConfigProperties properties;

    public ServiceParamConfigAutoConfiguration(ServiceParamConfigProperties properties) {
        this.properties = Objects.requireNonNull(properties);
    }

    @Bean
    public ParamConfigService paramConfigService() {
        return new ParamConfigServiceImpl();
    }

}
