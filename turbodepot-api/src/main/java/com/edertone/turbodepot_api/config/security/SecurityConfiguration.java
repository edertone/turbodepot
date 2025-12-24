package com.edertone.turbodepot_api.config.security;

import com.edertone.turbodepot_api.service.UserTokenService;
import com.edertone.turbodepot_api.support.mapper.UserTokenMapper;
import com.edertone.turbodepot_api.support.web.WebConstants;
import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import tools.jackson.databind.json.JsonMapper;

/**
 * Security configuration.
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    private final AuthenticationEntryPoint authTokenEntryPoint;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final UserTokenService userTokenService;
    private final UserTokenMapper userTokenMapper;
    private final AuthenticationConfiguration authConfig;
    private final JsonMapper jsonMapper;

    public SecurityConfiguration(
        AuthenticationEntryPoint authTokenEntryPoint,
        AuthenticationFailureHandler authenticationFailureHandler,
        UserTokenService userTokenService,
        UserTokenMapper userTokenMapper,
        AuthenticationConfiguration authConfig,
        JsonMapper jsonMapper
    ) {
        this.authTokenEntryPoint = authTokenEntryPoint;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.userTokenService = userTokenService;
        this.userTokenMapper = userTokenMapper;
        this.authConfig = authConfig;
        this.jsonMapper = jsonMapper;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .exceptionHandling(exception -> exception.authenticationEntryPoint(this.authTokenEntryPoint))
            .securityContext(securityContext -> securityContext.requireExplicitSave(true))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(WebConstants.SWAGGER_URLS).permitAll()
                .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                .anyRequest().authenticated())
            .addFilterBefore(new ApiAuthenticationFilter(
                authenticationManager(), userTokenService, authenticationFailureHandler, jsonMapper), AuthorizationFilter.class)
            .addFilterBefore(new ApiCheckTokenFilter(userTokenService, userTokenMapper, jsonMapper), AuthorizationFilter.class)
            .addFilterBefore(new ApiAuthorizationFilter(userTokenService), AuthorizationFilter.class)
            .logout(customizer -> customizer
                .logoutUrl(WebConstants.AUTH_LOGOUT_URL)
                .addLogoutHandler(new TokenClearingLogoutHandler(userTokenService, userTokenMapper))
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)))
            .build();
    }
}
