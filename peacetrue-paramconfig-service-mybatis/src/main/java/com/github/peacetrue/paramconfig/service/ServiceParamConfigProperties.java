package com.github.peacetrue.paramconfig.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xiayx
 */
@Data
@ConfigurationProperties(prefix = "peacetrue.paramconfig")
public class ServiceParamConfigProperties {

}
