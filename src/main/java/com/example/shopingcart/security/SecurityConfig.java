package com.example.shopingcart.security;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomeAuthenticationFilter customeAuthenticationFilter = new CustomeAuthenticationFilter(authenticationManagerBean());

        customeAuthenticationFilter.setFilterProcessesUrl("/api/login");

        http.cors().and().csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/api/login/**","/api/v1/products/{\\d+}", "/api/v1/products").permitAll();
        http.authorizeRequests().antMatchers(GET,"/api/v1/admin/products/**","/api/v1/admin/order","/api/v1/admin/user/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(POST,"/api/v1/admin/products/**","/api/v1/admin/order","/api/v1/admin/user/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(PUT,"/api/v1/admin/products/**","/api/v1/admin/order","/api/v1/admin/user/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(DELETE,"/api/v1/admin/products/**","/api/v1/admin/order/**","/api/v1/admin/user/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(GET,"/api/v1/admin/products/**","/api/v1/admin/order").hasAnyAuthority("USER");

        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customeAuthenticationFilter);
        http.addFilterBefore(new CustomeAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
