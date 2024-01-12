package com.startsteps.ecommerceapi.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
@EnableWebSecurity
public class EcomSecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeHttpRequests()
                .and()
                .cors()
                .and()
                .csrf().disable();
        return httpSecurity.build();
    }

//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .httpBasic(Customizer.withDefaults())     // Default Basic auth config
//                .csrf(configurer -> configurer.disable()) // for POST requests via Postman
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers(HttpMethod.POST, "/register").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/test").hasRole("USER")
//                        .anyRequest().denyAll()
//                );
//
//        return http.build();
//    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
