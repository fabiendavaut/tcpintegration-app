package com.viadialog.tcpintegration.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.viadialog.tcpintegration")
public class FeignConfiguration {

}
