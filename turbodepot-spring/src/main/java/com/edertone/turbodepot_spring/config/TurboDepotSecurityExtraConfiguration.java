package com.edertone.turbodepot_spring.config;

import com.edertone.turbodepot_spring.config.security.ApiAuthenticationFailureHelper;
import com.edertone.turbodepot_spring.config.security.ApiUserDetailsService;
import com.edertone.turbodepot_spring.repository.AuthUserTokenRepository;
import com.edertone.turbodepot_spring.repository.OperationRoleRepository;
import com.edertone.turbodepot_spring.repository.UserRepository;
import com.edertone.turbodepot_spring.service.UserTokenService;
import com.edertone.turbodepot_spring.service.impl.UserTokenServiceImpl;
import com.edertone.turbodepot_spring.support.mapper.UserTokenMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import tools.jackson.databind.json.JsonMapper;

@Configuration
public class TurboDepotSecurityExtraConfiguration {

    private final AuthenticationConfiguration authConfig;
    private final JsonMapper jsonMapper;

    public TurboDepotSecurityExtraConfiguration(AuthenticationConfiguration authConfig, JsonMapper jsonMapper) {
        this.authConfig = authConfig;
        this.jsonMapper = jsonMapper;
    }

    /**
     * Register a BCrypt password encoder.
     *
     * @return the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserTokenMapper userTokenMapper() {
        return new UserTokenMapper() {};
    }

    @Bean
    public UserTokenService userTokenService(
        AuthUserTokenRepository authUserTokenRepository,
        UserTokenMapper userTokenMapper
    ) {
        return new UserTokenServiceImpl(authUserTokenRepository, userTokenMapper);
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public ApiAuthenticationFailureHelper authenticationFailureHelper() {
        return new ApiAuthenticationFailureHelper(jsonMapper);
    }

    @Bean
    public UserDetailsService apiUserDetailsService(
        UserRepository userRepository,
        OperationRoleRepository operationRoleRepository
    ) {
        return new ApiUserDetailsService(userRepository, operationRoleRepository);
    }
}
