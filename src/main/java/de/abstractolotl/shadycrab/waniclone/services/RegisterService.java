package de.abstractolotl.shadycrab.waniclone.services;

import de.abstractolotl.shadycrab.waniclone.controller.dto.RegisterInfo;
import de.abstractolotl.shadycrab.waniclone.controller.dto.RegisterRequest;
import de.abstractolotl.shadycrab.waniclone.entities.CrabUser;
import de.abstractolotl.shadycrab.waniclone.repositories.CrabUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class RegisterService {

    @Autowired private UserService userService;
    @Autowired private PasswordService passwordService;

    public RegisterInfo createRegisterInfoFromOAuth(OAuth2AuthenticationToken token) {
        return new RegisterInfo(
            token.getAuthorizedClientRegistrationId(),
            token.getPrincipal().getAttribute("picture"),
            token.getPrincipal().getAttribute("name"),
            token.getPrincipal().getAttribute("email")
        );
    }

    private boolean compareRequestWithOAuth(RegisterRequest request, OAuth2AuthenticationToken token) {
        String oauthEmail = token.getPrincipal().getAttribute("email");
        if(request.getEmail().equals(oauthEmail)) {
            return false;
        }
        return true;
    }

    public void registerWithOAuth(RegisterRequest request, OAuth2AuthenticationToken token) {
        if(compareRequestWithOAuth(request, token)) {
            throw new RuntimeException("Request and OAuth doesn't match"); //TODO:
        }
        register(request);
    }

    public void register(RegisterRequest request) {
        String salt = passwordService.generateSalt();
        String password = passwordService.hashPasswordWithSalt(request.getPassword(), salt);

        CrabUser user = new CrabUser(request.getUsername(), password, salt, request.getEmail());
        //userService.addUser(user);

    }

}
