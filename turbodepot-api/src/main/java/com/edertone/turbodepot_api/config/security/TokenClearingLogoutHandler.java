package com.edertone.turbodepot_api.config.security;

import com.edertone.turbodepot_api.service.UserTokenService;
import com.edertone.turbodepot_api.support.mapper.UserTokenMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

/**
 * {@link LogoutHandler} that clears the authentication token present in the request, if any.
 */
public class TokenClearingLogoutHandler implements LogoutHandler {

    private final Logger logger = LoggerFactory.getLogger(TokenClearingLogoutHandler.class);

    private final UserTokenService userTokenService;
    private final UserTokenMapper userTokenMapper;

    public TokenClearingLogoutHandler(UserTokenService userTokenService, UserTokenMapper userTokenMapper) {
        this.userTokenService = userTokenService;
        this.userTokenMapper = userTokenMapper;
    }

    @Override
    public void logout(
        HttpServletRequest request,
        HttpServletResponse response,
        @Nullable Authentication authentication
    ) {
        var token = userTokenMapper.toToken(request);

        if (StringUtils.isEmpty(token)) {
            logger.trace("There's not token in the request to perform the logout action");
        } else {
            userTokenService.deleteToken(token);
        }
    }
}
