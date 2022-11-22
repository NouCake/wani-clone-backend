package de.abstractolotl.shadycrab.waniclone.authentication;

import de.abstractolotl.shadycrab.waniclone.services.CrabAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//TODO: https://stackoverflow.com/questions/39710526/how-to-apply-spring-boot-filter-based-on-url-pattern
@Component
@Order()
public class PostOAuthAuthenticationFilter extends OncePerRequestFilter {

    @Autowired private CrabAuthService authService;

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("invoked");
        if(new AntPathRequestMatcher("/register/**").matches(request)) {
            chain.doFilter(request, response);
            return;
        }

        Authentication authentication = authService.getAuthentication();
        if(authentication.getClass().equals(CrabAuthentication.class)) {
            chain.doFilter(request, response);
            return;
        }

        if(!authentication.getClass().equals(OAuth2AuthenticationToken.class)) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getOutputStream().println("Unknown Authentication");
            return;
        }

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        var user = authService.userFromGoogleOAuthTokenExists(token);

        if(user.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getOutputStream().println("No User. Please Register.");
            return;
        }

        authService.upgradeAuthenticationWithUser(token, user.get());
        chain.doFilter(request, response);
    }

}
