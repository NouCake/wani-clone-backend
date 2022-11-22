package de.abstractolotl.shadycrab.waniclone.repositories;

import de.abstractolotl.shadycrab.waniclone.entities.CrabUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CrabUserRepository extends CrudRepository<CrabUser, Long> {

    Optional<CrabUser> findCrabUserByGoogleOAuthIdentifier(String googleOAuthIdentifer);

}
