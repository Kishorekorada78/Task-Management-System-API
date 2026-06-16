package com.kishore.taskproject.securityconfigu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kishore.taskproject.security.JwtAuthenticationFilter;
import com.kishore.taskproject.security.OAuth2AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
  
	@Autowired
	private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)
	        throws Exception {

	    http
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers(
	                "/api/auth/**",
	                "/oauth2/**",
	                "/login/**"
	            ).permitAll()
	            .anyRequest()
	            .authenticated()
	        )
	        .oauth2Login(oauth ->
	        oauth.successHandler(oAuth2AuthenticationSuccessHandler)
	    );

	    http.addFilterBefore(
	            jwtAuthenticationFilter,
	            UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration authunticationConfiguration
			) throws Exception{
		return authunticationConfiguration.getAuthenticationManager();
	}
}
