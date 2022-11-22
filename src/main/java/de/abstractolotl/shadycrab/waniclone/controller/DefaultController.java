package de.abstractolotl.shadycrab.waniclone.controller;

import de.abstractolotl.shadycrab.waniclone.controller.dto.RegisterInfo;
import de.abstractolotl.shadycrab.waniclone.controller.dto.RegisterRequest;
import de.abstractolotl.shadycrab.waniclone.entities.CrabUser;
import de.abstractolotl.shadycrab.waniclone.repositories.CrabUserRepository;
import de.abstractolotl.shadycrab.waniclone.services.CrabAuthService;
import de.abstractolotl.shadycrab.waniclone.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/register")
public class DefaultController {

    @Autowired private CrabAuthService authService;
    @Autowired private RegisterService registerService;

    @GetMapping("/info") //TODO: Change name/path
    public RegisterInfo registerInfo() {
        var token = authService.isOAuthAuthentication(authService.getAuthentication());
        if(token != null) {
            return registerService.createRegisterInfoFromOAuth(token);
        }

        return new RegisterInfo();
    }

    @PostMapping("")
    public String register(@Valid @RequestBody RegisterRequest request){
        if(authService.authUser().isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are already logged in.");
        }

        var token = authService.isOAuthAuthentication(authService.getAuthentication());
        if(token != null) {
            registerService.registerWithOAuth(request, token);
            return "ok";
        }

        registerService.register(request);
        return "you suck!";
    }

}
