package com.anthonyquere.companionapi.config;

import com.anthonyquere.companionapi.crud.companions.Companion;
import com.anthonyquere.companionapi.crud.message.Message;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class SpringDataRestConfig implements RepositoryRestConfigurer {
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(Companion.class);
        config.exposeIdsFor(Message.class);

        cors.addMapping("/**");
    }
}
