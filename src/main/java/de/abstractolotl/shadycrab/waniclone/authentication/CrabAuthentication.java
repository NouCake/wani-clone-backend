package de.abstractolotl.shadycrab.waniclone.authentication;

import de.abstractolotl.shadycrab.waniclone.entities.CrabUser;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
public class CrabAuthentication implements Authentication {

    private Object credentials;
    private Object details;
    private CrabUser principal;
    private boolean authenticated;

    public CrabAuthentication(Object credentials, Object details, CrabUser principal) {
        this.credentials = credentials;
        this.details = details;
        this.principal = principal;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = authenticated;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }
}
