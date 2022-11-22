package de.abstractolotl.shadycrab.waniclone.controller.views;

import de.abstractolotl.shadycrab.waniclone.entities.CrabUser;
import lombok.Data;

@Data
public class ProfileView {

    private String username;

    public static ProfileView fromCrabUser(CrabUser user) {
        ProfileView view = new ProfileView();
        view.setUsername(user.getUsername());
        return view;
    }

}
