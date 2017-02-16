package org.apiguard.commons;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:/test.properties")
public class TestConfiguration {
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertiesResolver() {
	    return new PropertySourcesPlaceholderConfigurer();
	}
}
