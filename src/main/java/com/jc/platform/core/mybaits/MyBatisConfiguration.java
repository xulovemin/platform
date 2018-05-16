package com.jc.platform.core.mybaits;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfiguration {
    @Bean
    public PageHelper pageHelper() {
        return new PageHelper();
    }
}
