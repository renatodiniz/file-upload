package com.upload;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upload.service.StorageService;
import com.upload.service.properties.StorageProperties;

/**
 * The Class Application.
 */
@SpringBootApplication
@EnableOAuth2Sso
@RestController
@EnableConfigurationProperties(StorageProperties.class)
public class Application extends WebSecurityConfigurerAdapter {
	
	/** The oauth2 client context. */
	@Autowired
	OAuth2ClientContext oauth2ClientContext;
	
	/**
	 * Esse método retorna as informações de autenticação recebidas pelo oauth2.
	 *
	 * @param principal
	 *            the principal
	 * @return the map (nome do Facebook e id do Facebook)
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/user")
	public Map<String, Object> user(Principal principal) {
		OAuth2Authentication oauth = (OAuth2Authentication) principal;		
		LinkedHashMap<String, String> details = (LinkedHashMap<String, String>) oauth.getUserAuthentication().getDetails();
		
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("name", details.get("name"));
		map.put("id", details.get("id"));
		return map;
	}

	/**
	 * Método main que iniciará a aplicação SpringBoot.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	/**
	 * Esse método configura a autenticação da aplicação.
	 * Define as URLs que serão acessíveis sem precisar estar autenticado.
	 * Define para onde será redirecionada a aplicação após o logout.
	 *
	 * @param http
	 *            the http
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.antMatcher("/**")
				.authorizeRequests()
			.antMatchers("/", "/login**", "/webjars/**")
				.permitAll()
			.anyRequest()
				.authenticated()
			.and()
			.logout().logoutSuccessUrl("/")
				.permitAll();
		
		http.csrf().disable();
	}
	
	/**
	 * Esse método invoca a inicialização do serviço de armazenamento de arquivos.
	 *
	 * @param storageService
	 *            the storage service
	 * @return the command line runner
	 */
	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
            storageService.init();
		};
	}
}
