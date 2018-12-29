package com.joycity.intern.mafia.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;

@Configuration
@ComponentScan(basePackages = { "com.joycity.intern.mafia" }, excludeFilters = @Filter({ Controller.class }))
@Import({ DBConfig.class, JpaConfig.class })
public class ApplicationConfig {

}