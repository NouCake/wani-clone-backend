package de.abstractolotl.shadycrab.waniclone.services;

import de.abstractolotl.shadycrab.waniclone.authentication.CrabAuthentication;
import de.abstractolotl.shadycrab.waniclone.repositories.CrabUserRepository;
import de.abstractolotl.shadycrab.waniclone.entities.CrabUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class CrabAuthService {

    @Autowired private CrabUserRepository userRepo;

    public Optional<CrabUser> authUser() {
        Authentication authentication = getAuthentication();
        if(!authentication.getClass().equals(CrabAuthentication.class)) {
            return Optional.empty();
        }
        CrabUser user = ((CrabAuthentication) authentication).getPrincipal();
        return user == null ? Optional.empty() : Optional.of(user);
    }

    public Authentication getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        return authentication;
    }

    public OAuth2AuthenticationToken isOAuthAuthentication(Authentication authentication){
        if (authentication.getClass().equals(OAuth2AuthenticationToken.class)) {
            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
            return token;
        }
        return null;
    }

    public Optional<CrabUser> userFromGoogleOAuthTokenExists(OAuth2AuthenticationToken token) {
        String identifier = token.getPrincipal().getAttribute("sub"); //TODO: principal may be null
        if(identifier == null) {
            throw new RuntimeException("Todo: Internal Server Error");
        }
        return userRepo.findCrabUserByGoogleOAuthIdentifier(identifier);
    }

    public void upgradeAuthenticationWithUser(Authentication previous, CrabUser user) {
        CrabAuthentication auth = new CrabAuthentication(previous.getCredentials(), previous.getDetails(), user);
        setAuthentication(auth);
    }

    private void setAuthentication(Authentication authentication){
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
