package com.singleton.graphqllab.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.singleton.graphqllab.client")
public class FeignConfig {

}
