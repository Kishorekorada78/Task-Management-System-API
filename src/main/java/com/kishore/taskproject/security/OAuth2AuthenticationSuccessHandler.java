package com.kishore.taskproject.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.kishore.taskproject.entity.Users;
import com.kishore.taskproject.repository.UserRepositary;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2AuthenticationSuccessHandler
        extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserRepositary userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oauthUser =
                (OAuth2User) authentication.getPrincipal();

        String email =
                oauthUser.getAttribute("email");

        String name =
                oauthUser.getAttribute("name");

        Optional<Users> existingUser =
                userRepository.findByEmail(email);

        if (existingUser.isEmpty()) {

            Users user = new Users();

            user.setName(name);
            user.setEmail(email);

            // temporary password for Google users
            user.setPassword("GOOGLE_USER");

            userRepository.save(user);

            System.out.println("New Google User Created");
        }

        String token =
                jwtTokenProvider.generateTokenFromEmail(email);

        response.setContentType("application/json");

        response.getWriter().write(
                """
                {
                    "token":"%s",
                    "tokenType":"Bearer"
                }
                """.formatted(token)
        );
    }
}