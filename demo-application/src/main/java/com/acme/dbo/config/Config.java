package com.acme.dbo.config;

import com.acme.dbo.dao.AccountRepository;
import com.acme.dbo.dao.MapBackedAccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Configuration
@ComponentScan("com.acme.dbo")
@PropertySource("classpath:application.properties")
public class Config {
    @Bean
    @Scope("prototype")
    public AccountRepository accountRepository(@Value("${accounts.repo.init-capacity}") int initCapacity) {
        return new MapBackedAccountRepository(initCapacity);
    }
}
