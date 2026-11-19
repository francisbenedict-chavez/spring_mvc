// package com.spring.spring_rest.security;

// import com.spring.spring_rest.service.CustomUserDetailsService;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.Customizer;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;

// /**
//  * Configures HTTP security, authentication management, and password encoding
//  * for the application.
//  */
// @EnableWebSecurity
// @Configuration
// public class SecurityConfig {

//   private CustomUserDetailsService userDetailsService;

//   /**
//    * Configures the security filter chain for HTTP requests.
//    *
//    * <p>
//    * Requests to {@code /api/auth/**} are permitted without
//    * authentication; all other requests require authentication.
//    * HTTP Basic authentication is enabled and CSRF protection is
//    * disabled.
//    * </p>
//    *
//    * @param security the HTTP security configuration
//    * @return the configured security filter chain
//    * @throws Exception if the security configuration cannot be built
//    */
//   @Bean
//   SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
//     return security
//         .csrf(AbstractHttpConfigurer::disable)
//         .authorizeHttpRequests(auth -> auth
//             .requestMatchers("/api/auth/**").permitAll()
//             .anyRequest().authenticated())
//         .httpBasic(Customizer.withDefaults())
//         .build();
//   }

//   /**
//    * Creates the password encoder used to hash passwords.
//    *
//    * @return a BCrypt password encoder
//    */
//   @Bean
//   BCryptPasswordEncoder passwordEncoder() {
//     return new BCryptPasswordEncoder();
//   }

//   /**
//    * Gets the authentication manager configured by Spring Security.
//    *
//    * @param authentication Spring Security's authentication configuration
//    * @return the configured authentication manager
//    * @throws Exception if the authentication manager cannot be obtained
//    */
//   @Bean
//   AuthenticationManager authenticationManager(
//       AuthenticationConfiguration authentication) throws Exception {
//     return authentication.getAuthenticationManager();
//   }

//   /**
//    * Creates the security configuration with the user-details service.
//    *
//    * @param userDetailsService service for loading user details
//    */
//   public SecurityConfig(CustomUserDetailsService userDetailsService) {
//     this.setUserDetailsService(userDetailsService);
//   }

//   public CustomUserDetailsService getUserDetailsService() {
//     return userDetailsService;
//   }

//   public void setUserDetailsService(
//       CustomUserDetailsService userDetailsService) {
//     this.userDetailsService = userDetailsService;
//   }
// }
