package com.spring.spring_rest.security;

import com.spring.spring_rest.service.CustomUserDetailsService;
import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * Configures HTTP security, authentication management, and password encoding
 * for the application.
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {

  private CustomUserDetailsService userDetailsService;

  /**
   * Configures the security filter chain for HTTP requests.
   *
   * <p>
   * Requests to {@code /api/auth/**} are permitted without
   * authentication; all other requests require authentication.
   * HTTP Basic authentication is enabled and CSRF protection is
   * disabled.
   * </p>
   *
   * @param security the HTTP security configuration
   * @return the configured security filter chain
   * @throws Exception if the security configuration cannot be built
   */
  @Bean
  SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
    return security
        .cors(cors -> cors.configure(security))
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/**")
            .permitAll()
            .requestMatchers(HttpMethod.OPTIONS, "/**")
            .permitAll()
            .requestMatchers("/api/students/**")
            .hasAnyAuthority("USER", "ADMIN")
            .anyRequest().authenticated())
        .httpBasic(Customizer.withDefaults())
        .build();
  }

  /**
   * Creates the password encoder used to hash passwords.
   *
   * @return a BCrypt password encoder
   */
  @Bean
  BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  /**
   * Gets the authentication manager configured by Spring Security.
   *
   * @param authentication Spring Security's authentication configuration
   * @return the configured authentication manager
   * @throws Exception if the authentication manager cannot be obtained
   */
  @Bean
  AuthenticationManager authenticationManager(
      AuthenticationConfiguration authentication) throws Exception {
    return authentication.getAuthenticationManager();
  }

  /**
   * Creates the security configuration with the user-details service.
   *
   * @param userDetailsService service for loading user details
   */
  public SecurityConfig(CustomUserDetailsService userDetailsService) {
    this.setUserDetailsService(userDetailsService);
  }

  /**
   * Creates the CORS configuration source for the application's allowed origins,
   * methods, and headers.
   *
   * @return the configured CORS configuration source
   */
  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    configuration.setAllowedOrigins(Arrays.asList(
        "http://localhost:3000",
        "http://127.0.0.1:3000"));

    configuration.setAllowedMethods(Arrays.asList(
        "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

    configuration.setAllowedHeaders(Arrays.asList(
        "Authorization", "Cache-Control", "Content-Type"));

    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = 
      new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  public CustomUserDetailsService getUserDetailsService() {
    return userDetailsService;
  }

  public void setUserDetailsService(
      CustomUserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }
}
