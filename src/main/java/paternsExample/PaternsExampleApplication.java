package paternsExample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.ResourceBundleMessageSource;

@SpringBootApplication//(scanBasePackages = {"paternsExample.configuracion","paternsExample.interceptor" })
//@EnableConfigurationProperties(AplicationConfig.class)
public class PaternsExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaternsExampleApplication.class, args);
	}

	@Bean
	@Description("Spring Message Resolver")
	public ResourceBundleMessageSource messageSource() {
	    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
	    messageSource.setBasename("messages");
	    return messageSource;
	}
	
}
