package de.abstractolotl.shadycrab.waniclone.controller.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RegisterRequest {

    @NotEmpty private String username; //TODO: Validate username (4 - 20 chars)
    @NotEmpty private String email; //TODO: Validate
    @NotEmpty private String password; //TODO: Validate

}
