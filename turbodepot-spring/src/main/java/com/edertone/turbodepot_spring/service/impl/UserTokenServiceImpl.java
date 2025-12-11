package com.edertone.turbodepot_spring.service.impl;

import com.edertone.turbodepot_spring.config.security.ApiUserDetails;
import com.edertone.turbodepot_spring.model.dto.AuthResponseDto;
import com.edertone.turbodepot_spring.model.rdb.UserToken;
import com.edertone.turbodepot_spring.repository.AuthUserTokenRepository;
import com.edertone.turbodepot_spring.service.UserTokenService;
import com.edertone.turbodepot_spring.support.mapper.UserTokenMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Transactional(readOnly = true)
public class UserTokenServiceImpl implements UserTokenService {

    private final Logger logger = LoggerFactory.getLogger(UserTokenServiceImpl.class);

    private final AuthUserTokenRepository authUserTokenRepository;
    private final UserTokenMapper userTokenMapper;

    public UserTokenServiceImpl(
        AuthUserTokenRepository authUserTokenRepository,
        UserTokenMapper userTokenMapper
    ) {
        this.authUserTokenRepository = authUserTokenRepository;
        this.userTokenMapper = userTokenMapper;
    }

    @Transactional
    @Override
    public AuthResponseDto createToAuthResponse(Authentication authentication) {
        if (!(authentication.getPrincipal() instanceof ApiUserDetails apiUserDetails)) {
            throw new InternalAuthenticationServiceException("Authentication principal is not of type ApiUserDetails");
        }

        var authUserToken = new UserToken()
            .setUser(apiUserDetails.getUser())
            .setToken(UUID.randomUUID().toString())
            .setExpirationDate(Date.from(ZonedDateTime.now().plusDays(1L).toInstant()));
        authUserTokenRepository.save(authUserToken);

        return userTokenMapper.toAuthResponseDto(authentication, authUserToken);
    }

    @Scheduled(initialDelay = 1, fixedDelay = 10, timeUnit = TimeUnit.MINUTES)
    @Transactional
    @Override
    public void deleteExpiredTokens() {
        logger.debug("Deleting expired tokens");
        var deletedCount = authUserTokenRepository.deleteExpiredTokens();
        logger.debug("Deleted {} expired tokens", deletedCount);
    }
}
