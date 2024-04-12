package com.oop.api.config;

import com.oop.api.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * This class represents the configuration for the application.
 * It provides beans for user details service, password encoder, authentication manager, and authentication provider.
 */

@Configuration
public class ApplicationConfiguration {
    private final UserRepository userRepository;

    public ApplicationConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
        * Returns an implementation of the UserDetailsService interface.
        * This method retrieves the user details from the UserRepository based on the provided username.
        * If the user is not found, it throws a UsernameNotFoundException.
        *
        * @return an implementation of the UserDetailsService interface
        * @throws UsernameNotFoundException if the user is not found
        */

    @Bean
    UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
        * Creates a new instance of the BCryptPasswordEncoder bean.
        * 
        * @return The BCryptPasswordEncoder bean.
        */

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Returns the authentication manager.
     *
     * @param config the authentication configuration
     * @return the authentication manager
     * @throws Exception if an error occurs while retrieving the authentication manager
     */

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Creates and configures the AuthenticationProvider bean.
     * The AuthenticationProvider is responsible for authenticating user credentials.
     * It uses a DaoAuthenticationProvider with a custom UserDetailsService and password encoder.
     *
     * @return The configured AuthenticationProvider bean.
     */
    
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
   }
}
