package com.edertone.turbodepot_api.service.impl;

import com.edertone.turbodepot_api.config.security.ApiUserDetails;
import com.edertone.turbodepot_api.config.security.UsernameTenantPasswordAuthenticationToken;
import com.edertone.turbodepot_api.model.dto.AuthResponseDto;
import com.edertone.turbodepot_api.model.rdb.UserToken;
import com.edertone.turbodepot_api.repository.AuthUserTokenRepository;
import com.edertone.turbodepot_api.service.UserTokenService;
import com.edertone.turbodepot_api.support.mapper.UserTokenMapper;
import com.edertone.turbodepot_api.support.util.RandomUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
public class UserTokenServiceImpl implements UserTokenService {

    private final Logger logger = LoggerFactory.getLogger(UserTokenServiceImpl.class);

    private final AuthUserTokenRepository authUserTokenRepository;
    private final UserDetailsService userDetailsService;
    private final UserTokenMapper userTokenMapper;

    public UserTokenServiceImpl(
        AuthUserTokenRepository authUserTokenRepository,
        UserDetailsService userDetailsService,
        UserTokenMapper userTokenMapper
    ) {
        this.authUserTokenRepository = authUserTokenRepository;
        this.userDetailsService = userDetailsService;
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
            .setToken(RandomUtils.generateUserToken())
            .setExpirationDate(Date.from(ZonedDateTime.now().plusDays(1L).toInstant()));
        authUserTokenRepository.save(authUserToken);

        return userTokenMapper.toAuthResponseDto(authentication, authUserToken.getToken());
    }

    @Override
    public Authentication getAuthentication(HttpServletRequest request) {
        var accessToken = userTokenMapper.toToken(request);

        if (accessToken == null || accessToken.isEmpty()) {
            return null;
        }

        try {
            var authTokenOptional = authUserTokenRepository.findByToken(accessToken);
            if (authTokenOptional.isEmpty()) {
                return null;
            }

            var authToken = authTokenOptional.get();
            if (authToken.getExpirationDate().before(new Date())) {
                return null;
            }

            var unauthenticatedToken = UsernameTenantPasswordAuthenticationToken
                .unauthenticated(authToken.getUser().getUsername(), null, authToken.getUser().getTenant().getName());
            var userDetail = (ApiUserDetails) userDetailsService.loadUserByUsername(unauthenticatedToken.getName());
            return new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
        } catch (Exception e) {
            logger.trace("Authentication token not valid. Cause: {}", e.getMessage());
        }

        return null;
    }

    @Scheduled(initialDelay = 1, fixedDelay = 10, timeUnit = TimeUnit.MINUTES)
    @Transactional
    @Override
    public void deleteExpiredTokens() {
        logger.debug("Deleting expired tokens");
        var deletedCount = authUserTokenRepository.deleteExpiredTokens();
        logger.debug("Deleted {} expired tokens", deletedCount);
    }

    @Transactional
    @Override
    public void deleteToken(String token) {
        logger.debug("Deleting token {}", token);
        authUserTokenRepository
            .findByToken(token)
            .ifPresent(authUserTokenRepository::delete);
    }
}
