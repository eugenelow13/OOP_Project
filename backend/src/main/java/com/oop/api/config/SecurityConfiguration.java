package com.oop.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;

/**
 * This class represents the security configuration for the application.
 * It configures the security filters, authentication providers, and authorization rules.
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomLogoutHandler logoutHandler;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authenticationProvider,
                                 CustomLogoutHandler logoutHandler) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.logoutHandler = logoutHandler;
    }

    /**
     * Configures the security filter chain for the HTTP requests.
     *
     * @param http the HttpSecurity object used to configure the security filter chain
     * @return the configured SecurityFilterChain object
     * @throws Exception if an error occurs during configuration
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors((cors) -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(req ->
                        req.requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/events/**").permitAll()
                        .requestMatchers("/bookings/**").hasAnyRole("EVENT_MANAGER", "TICKETING_OFFICER", "CUSTOMER")
                        .requestMatchers("/customers/**").hasAnyRole("EVENT_MANAGER", "TICKETING_OFFICER", "CUSTOMER")
                        .requestMatchers("/tickets/**").hasAnyRole("EVENT_MANAGER", "TICKETING_OFFICER")
                        .requestMatchers("/users/**").hasAnyRole("EVENT_MANAGER", "TICKETING_OFFICER", "CUSTOMER")
                        .requestMatchers("/event_managers/**").hasAnyRole("EVENT_MANAGER")
                        .requestMatchers("/ticketing_officers/**").hasAnyRole("EVENT_MANAGER", "TICKETING_OFFICER")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );

        return http.build();
    }

    /**
     * A source for retrieving CORS (Cross-Origin Resource Sharing) configuration.
     * Implementations of this interface provide the necessary configuration for handling CORS requests.
     */
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of("http://localhost:8082"));
        configuration.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));
        configuration.addExposedHeader("Access-Control-Allow-Origin");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**",configuration);

        return source;
    }
}