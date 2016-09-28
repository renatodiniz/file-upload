package com.upload;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upload.service.StorageProperties;
import com.upload.service.StorageService;
import com.upload.servlet.FileUploadServlet;

@SpringBootApplication
@EnableOAuth2Sso
@RestController
@EnableConfigurationProperties(StorageProperties.class)
public class Application extends WebSecurityConfigurerAdapter {
	
	@Autowired
	OAuth2ClientContext oauth2ClientContext;
	
	@RequestMapping("/user")
	public Principal user(Principal principal) {
		return principal;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/**")
			.authorizeRequests()
			.antMatchers("/", "/login**", "/webjars/**", "/jquery-file-upload/**", "/arquivo**", "/file.upload**")
			.permitAll().anyRequest().authenticated()
			.and().logout().logoutSuccessUrl("/").permitAll();
		
		http.csrf().disable();
	}
	
	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		return new ServletRegistrationBean(new FileUploadServlet(), "/file.upload");
	}
	
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
//            storageService.deleteAll();
            storageService.init();
		};
	}
}
