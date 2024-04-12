package com.oop.api.config;

import com.oop.api.model.Token;
import com.oop.api.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 * This class is responsible for handling the logout process for a user.
 * It implements the LogoutHandler interface and provides the logic to invalidate the user's token upon logout.
 */

@Configuration
public class CustomLogoutHandler implements LogoutHandler{

    private final TokenRepository tokenRepository;

    public CustomLogoutHandler(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    /**
     * Logs out the user by invalidating the token and marking it as logged out.
     *
     * @param request         the HttpServletRequest object
     * @param response        the HttpServletResponse object
     * @param authentication the Authentication object representing the current user's authentication
     */

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response,
                       Authentication authentication) {
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        String token = authHeader.substring(7);
        Token storedToken = tokenRepository.findByToken(token).orElse(null);

        if(storedToken != null) {
            storedToken.setLoggedOut(true);
            tokenRepository.save(storedToken);
        }
    }
}
