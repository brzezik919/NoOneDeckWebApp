package io.github.brzezik919;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.UrlTemplateResolver;


@SpringBootApplication
public class NoOneDeckWebAppApplication implements RepositoryRestConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(NoOneDeckWebAppApplication.class, args);
	}

	@Bean
	Validator validator(){
		return new LocalValidatorFactoryBean();
	}

	@Override
	public void configureValidatingRepositoryEventListener(ValidatingRepositoryEventListener validatingListener) {
		validatingListener.addValidator("beforeCreate", validator());
		validatingListener.addValidator("beforeSave", validator());
	}
}
