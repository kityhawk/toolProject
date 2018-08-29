package com.kityhawk.lvds.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {"com.kityhawk.lvds.service","com.kityhawk.lvds.dao"})
public class AppConfig {

}
