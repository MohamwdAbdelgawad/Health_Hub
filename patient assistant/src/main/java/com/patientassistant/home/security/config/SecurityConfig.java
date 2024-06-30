package com.patientassistant.home.security.config;

import com.patientassistant.home.security.Repository.UserRepository;
import com.patientassistant.home.security.filter.JwtAuthenticationFilter;
import com.patientassistant.home.security.utils.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private UserRepository userRepository;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowCredentials(true);
        config.setExposedHeaders(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CORS configuration
        http.cors(corsCustomizer -> corsCustomizer.configurationSource(corsConfigurationSource()));

        // CSRF configuration
        http.csrf(csrf -> csrf.disable());

        // Stateless session management
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Authorization configuration
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers(
                        "/auth/register/patient",
                        "/auth/register/doctor",
                        "/oauth2/**",
                        "/auth/login",
                        "/auth/confirm",
                        "/auth/forgot-password",
                        "/auth/reset-password",
                        "/auth/refresh-token",
                        "/auth/register",
                        "/swagger-ui.html",
                        "/index.html").permitAll()
                .anyRequest().authenticated()
        );

        // Add JWT authentication filter
        http.addFilterAfter(new JwtAuthenticationFilter(jwtTokenUtils, userDetailsService), BasicAuthenticationFilter.class);

        // HTTP Basic authentication
        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new SecurityUserDetailsService(userRepository);
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new AuthenticationProviderService();
    }
}
