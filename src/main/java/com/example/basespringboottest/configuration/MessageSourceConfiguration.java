package com.example.basespringboottest.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import static com.example.basespringboottest.constant.CommonConstants.ENCODING_UTF_8;
import static com.example.basespringboottest.constant.CommonConstants.MESSAGE_SOURCE;
/**
 * Configuration class for setting up a MessageSource bean.
 * MessageSource is used for handling internationalization (i18n) and localization in a Spring Boot application.
 */
@Configuration
public class MessageSourceConfiguration {

    @Bean
    public MessageSource messageSource(){
        var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(MESSAGE_SOURCE);
        messageSource.setDefaultEncoding(ENCODING_UTF_8);
        return messageSource;
    }
}
