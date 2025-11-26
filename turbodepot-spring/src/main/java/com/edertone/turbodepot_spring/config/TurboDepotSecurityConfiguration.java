package com.edertone.turbodepot_spring.config;

import com.edertone.turbodepot_spring.config.security.ApiAuthenticationFailureHelper;
import com.edertone.turbodepot_spring.config.security.ApiAuthenticationFilter;
import com.edertone.turbodepot_spring.service.UserTokenService;
import com.edertone.turbodepot_spring.support.web.WebConstants;
import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import tools.jackson.databind.json.JsonMapper;

@Configuration
@EnableMethodSecurity
@EnableScheduling
@Import(TurboDepotSecurityExtraConfiguration.class)
public class TurboDepotSecurityConfiguration {

    private final AuthenticationManager authenticationManager;
    private final UserTokenService userTokenService;
    private final ApiAuthenticationFailureHelper apiAuthenticationFailureHelper;
    private final AuthenticationEntryPoint authTokenEntryPoint;
    private final UserDetailsService userDetailsService;
    private final JsonMapper jsonMapper;

    public TurboDepotSecurityConfiguration(
        AuthenticationManager authenticationManager,
        UserTokenService userTokenService,
        ApiAuthenticationFailureHelper apiAuthenticationFailureHelper,
        AuthenticationEntryPoint authTokenEntryPoint,
        UserDetailsService userDetailsService,
        JsonMapper jsonMapper
    ) {
        this.authenticationManager = authenticationManager;
        this.userTokenService = userTokenService;
        this.apiAuthenticationFailureHelper = apiAuthenticationFailureHelper;
        this.authTokenEntryPoint = authTokenEntryPoint;
        this.userDetailsService = userDetailsService;
        this.jsonMapper = jsonMapper;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        var apiAuthenticationFilter = new ApiAuthenticationFilter(
            authenticationManager,
            userTokenService,
            apiAuthenticationFailureHelper,
            jsonMapper
        );

        return http
            .csrf(AbstractHttpConfigurer::disable)
            .exceptionHandling(exception -> exception.authenticationEntryPoint(authTokenEntryPoint))
            .securityContext(securityContext -> securityContext.requireExplicitSave(true))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .userDetailsService(userDetailsService)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(WebConstants.DEFAULT_SWAGGER_URLS).permitAll()
                .requestMatchers(WebConstants.DEFAULT_PUBLIC_URLS).permitAll()
                .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                .anyRequest().authenticated())
            .addFilterBefore(apiAuthenticationFilter, AuthorizationFilter.class)
            .build();
    }
}
