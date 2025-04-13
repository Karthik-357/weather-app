package com.weatherapp.weatherapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;



@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable CSRF for development (ensure to enable it for production)
                .authorizeRequests(auth -> auth
                        .requestMatchers("/", "/login", "/oauth2/**", "/webjars/**", "/css/**", "/images/**").permitAll() // Allow unauthenticated access to these paths
                        .anyRequest().authenticated() // All other requests need authentication
                )
                .oauth2Login(oauth -> oauth
                        .loginPage("/login") // Custom login page URL
                        .defaultSuccessUrl("/home", true) // Redirect after successful login
                        .failureUrl("/login?error=true") // Redirect on login failure
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // Specify the URL pattern for logout
                        .logoutSuccessUrl("/login?logout=true") // Redirect to login page after successful logout
                        .invalidateHttpSession(true) // Invalidate the session after logout
                        .deleteCookies("JSESSIONID") // Delete the session cookie
                );

        return http.build();
    }
}
