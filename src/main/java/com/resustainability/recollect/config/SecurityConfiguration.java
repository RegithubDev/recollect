package com.resustainability.recollect.config;

import com.resustainability.recollect.config.filter.JwtFilter;
import com.resustainability.recollect.service.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {
    private final String actuatorEndpoint;
    private final String swaggerUiEndpoint;
    private final String swaggerApiDocsEndpoint;
    private final JwtFilter jwtFilter;
    private final UserDetailsServiceImpl userDetailsService;

    private final List<String> allowedOrigins = List.of(
            "*",
            "https://appmint.resustainability.com/"
    );
    private final List<String> allowedHeaders = List.of(
            "Authorization",
            "Content-Type",
            "X-Requested-With",
            "Accept",
            "Origin",
            "Access-Control-Request-Method",
            "Access-Control-Request-Headers",
            "x-api-key"
    );
    private final List<String> allowedExposedHeaders = List.of(
            "Access-Control-Allow-Origin",
            "Access-Control-Allow-Credentials",
            "Content-Disposition"
    );
    private final List<String> allowedMethods = List.of(
            "GET",
            "POST",
            "PUT",
            "PATCH",
            "DELETE",
            "OPTIONS"
    );

    @Autowired
    public SecurityConfiguration(
            @Value("${management.endpoints.web.base-path}") String actuatorEndpoint,
            @Value("${springdoc.swagger-ui.path}") String swaggerUiEndpoint,
            @Value("${springdoc.api-docs.path}") String swaggerApiDocsEndpoint,
            JwtFilter jwtFilter,
            UserDetailsServiceImpl userDetailsService
    ) {
        this.actuatorEndpoint = actuatorEndpoint;
        this.swaggerUiEndpoint = swaggerUiEndpoint;
        this.swaggerApiDocsEndpoint = swaggerApiDocsEndpoint;
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.setAllowedHeaders(allowedHeaders);
        configuration.setExposedHeaders(allowedExposedHeaders);
        configuration.setAllowedMethods(allowedMethods);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecureRandom secureRandom() throws NoSuchAlgorithmException {
        return SecureRandom.getInstanceStrong();
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
        return http.cors(cors -> cors.configurationSource(corsConfigurationSource))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(requests -> requests.requestMatchers(
                        "/recollect/v1/auth/get-customer-token",
                        "/swagger-ui/**",
                        (actuatorEndpoint + "/**"),
                        (swaggerUiEndpoint + "/**"),
                        (swaggerApiDocsEndpoint + "/**")
                ).permitAll().anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }
}
