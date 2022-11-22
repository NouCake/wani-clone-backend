package de.abstractolotl.shadycrab.waniclone.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class RegisterInfo {

    private String provider;
    private String picuterUrl;
    private String name;
    private String email;

}
