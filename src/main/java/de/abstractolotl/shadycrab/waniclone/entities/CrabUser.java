package de.abstractolotl.shadycrab.waniclone.entities;

import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter @Setter @Entity
public class CrabUser {

    public CrabUser() {

    }

    public CrabUser(@NonNull String username, @NonNull String passwordHash, @NonNull String salt, @NonNull String email) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.email = email;
    }

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NonNull @NotNull @Column(unique = true) private String username;
    @NonNull @NotNull private String passwordHash;
    @NonNull @NotNull private String salt;

    @NonNull @NotNull @Column(unique = true) private String email;
    @Column(unique = true) private String googleOAuthIdentifier;

}
