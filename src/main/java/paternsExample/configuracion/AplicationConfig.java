package paternsExample.configuracion;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import paternsExample.interceptor.StopwatchInterceptor;

@Configuration
public class AplicationConfig  implements WebMvcConfigurer{ //extends WebMvcConfigurerAdapter

	@Autowired
	HandlerInterceptor stopwatchInterceptor;

	
	public AplicationConfig(StopwatchInterceptor stopwatchInterceptor) {		
		this.stopwatchInterceptor = stopwatchInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// TODO Auto-generated method stub
		WebMvcConfigurer.super.addInterceptors(registry);
		registry.addInterceptor(stopwatchInterceptor);
	}
	
}

