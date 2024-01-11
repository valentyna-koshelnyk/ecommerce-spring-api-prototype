//package com.startsteps.ecommerceapi.user.security;
//
//import jakarta.servlet.DispatcherType;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
//import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
//import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
//import org.springframework.security.oauth2.core.OAuth2TokenValidator;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.jwt.JwtIssuerValidator;
//import org.springframework.security.oauth2.jwt.JwtTimestampValidator;
//import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
//import org.springframework.security.oauth2.jwt.SupplierJwtDecoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
///**
// * new Spring security 6.0 style provision of SecurityFilterChain bean with the security configuration
// */
//@Getter
//@Setter
//@Configuration
//@ConfigurationProperties("security.oauth2")
//@EnableWebSecurity
//public class WebSecurityConfig {
//
//    private String issuerValidatorUri;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .csrf(CsrfConfigurer::disable)
//                .oauth2ResourceServer(auth -> auth.jwt(Customizer.withDefaults()))
//                .authorizeHttpRequests(auth -> auth
//                        // next line needed (esp. DispatcherType.ERROR) to prevent interception of AuthorizationFilter in error dispatch
//                        .dispatcherTypeMatchers(DispatcherType.ASYNC, DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
//                        .requestMatchers(HttpMethod.POST, "/actuator/shutdown").permitAll()
//                        .requestMatchers(HttpMethod.GET, "/error").permitAll()
//                        .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
//                        .requestMatchers("/api/recipe/**").authenticated()
//                        .anyRequest().denyAll())
//                .build();
//    }
//
//    @Bean
//    public SupplierJwtDecoder jwtDecoder(OAuth2ResourceServerProperties properties) {
//        return new SupplierJwtDecoder(() -> {
//            if (Objects.isNull(issuerValidatorUri)) {
//                issuerValidatorUri = properties.getJwt().getIssuerUri();
//            }
//            List<OAuth2TokenValidator<Jwt>> validators = new ArrayList<>();
//            validators.add(new JwtTimestampValidator());
//            validators.add(new JwtIssuerValidator(issuerValidatorUri));
//            var oauth2TokenValidator = new DelegatingOAuth2TokenValidator<>(validators);
//
//            NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder
//                    .withIssuerLocation(properties.getJwt().getIssuerUri())
//                    .build();
//            jwtDecoder.setJwtValidator(oauth2TokenValidator);
//            return jwtDecoder;
//        });
//
