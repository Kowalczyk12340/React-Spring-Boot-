package com.example.backendblog.security.oauth2;

import com.example.backendblog.common.Exception.ApiException;
import com.example.backendblog.common.util.CookieUtils;
import com.example.backendblog.config.ApplicationProperties;
import com.example.backendblog.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static com.example.backendblog.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler
{
    private JwtUtil jwtUtil;

    private ApplicationProperties applicationProperties;

    @Autowired
    OAuth2AuthenticationSuccessHandler(JwtUtil jwtUtil,ApplicationProperties applicationProperties)
    {
        this.jwtUtil = jwtUtil;
        this.applicationProperties = applicationProperties;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request,response,authentication);

        if(response.isCommitted())
        {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request,response);
        getRedirectStrategy().sendRedirect(request,response,targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request,HttpServletResponse response,Authentication authentication)
    {
        Optional<String> redirectUri = CookieUtils.getCookie(request,REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get()))
        {
            throw new ApiException("Sorry! We have git Unauthorized Redirect URI and can not proceed with the authentication", HttpStatus.BAD_REQUEST);
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        String token = jwtUtil.createToken(authentication);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token",token)
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request,HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        HttpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestResponse(request,response);
    }

    private boolean isAuthorizedRedirectUri(String uri)
    {
        URI clientRedirectUri = URI.create(uri);

        return applicationProperties.getoAuth2().getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    // Only validate host and port. Let the clients use different paths if they want to
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    if(authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                    && authorizedURI.getPort() == clientRedirectUri.getPort()) {
                        return true;
                    }
                    return false;
                });
    }
}
