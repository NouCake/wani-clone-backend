package de.abstractolotl.shadycrab.waniclone.controller;

import de.abstractolotl.shadycrab.waniclone.controller.views.ProfileView;
import de.abstractolotl.shadycrab.waniclone.services.CrabAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired private CrabAuthService authService;

    @GetMapping("")
    public ProfileView user() {
        var user = authService.authUser();
        if(user.isPresent()) {
            return ProfileView.fromCrabUser(user.get());
        }

        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Authentication failed.");
    }

}
