package com.example.khalilovf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
//                .antMatchers(HttpMethod.GET,"/api/v1/product/*").hasAnyRole("SUPER_ADMIN","MODERATOR","OPERATOR")
//                .antMatchers(HttpMethod.POST,"/api/v1/.product").hasAnyRole("SUPER_ADMIN","MODERATOR")
//                .antMatchers(HttpMethod.PUT,"/api/v1/.product").hasAnyRole("SUPER_ADMIN","MODERATOR")
//                .antMatchers("/api/v1/product/**").hasAnyRole("SUPER_ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("super_admin")
                .password(passwordEncoder().encode("super_admin"))
                .roles("SUPER_ADMIN")
                .and()
                .withUser("moderator")
                .password(passwordEncoder().encode("moderator"))
                .roles("MODERATOR")
                .and()
                .withUser("operator")
                .password(passwordEncoder().encode("operator"))
                .roles("OPERATOR");

    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
