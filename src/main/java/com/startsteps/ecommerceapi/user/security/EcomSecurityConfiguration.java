package com.startsteps.ecommerceapi.user.security;

import com.startsteps.ecommerceapi.user.security.jwt.AuthEntryPointJwt;
import com.startsteps.ecommerceapi.user.security.jwt.AuthTokenFilter;
import com.startsteps.ecommerceapi.user.service.EcomUserDetailService;
import jakarta.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class EcomSecurityConfiguration {

    private static final String[] AUTH_WHITELIST = {
            "/authenticate",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/api/v1/app/user/auth/",
            "/api/auth/signin/**",
            "swagger-resources/**", "/webjars/"
    };


    @Autowired
    EcomUserDetailService userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;
   @Autowired
   private AuthTokenFilter authTokenFilter;



    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/**").permitAll()
                                .requestMatchers("/api/**").permitAll()
                                .requestMatchers("register/**").permitAll()
                                .requestMatchers("/auth").permitAll()
                                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers("/user/**").hasAuthority("ROLE_USER")
                                .requestMatchers(AUTH_WHITELIST).permitAll()
                                .anyRequest().authenticated()
                );

        http.headers(headers -> headers.frameOptions(frameOption -> frameOption.sameOrigin()));
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
