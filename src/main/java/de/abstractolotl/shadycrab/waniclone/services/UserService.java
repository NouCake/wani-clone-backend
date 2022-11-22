package de.abstractolotl.shadycrab.waniclone.services;

import de.abstractolotl.shadycrab.waniclone.entities.CrabUser;
import de.abstractolotl.shadycrab.waniclone.repositories.CrabUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;

@Service
public class UserService {

    @Autowired private CrabUserRepository userRepo;

    public void createBasicUser(String username, String passwordHash, String salt, String email) {
        CrabUser user = new CrabUser(username, passwordHash, salt, email);
        try {
            userRepo.save(user);
        } catch (DataAccessException e) {
            checkDataAccesException(e);
        }
    }

    private void checkDataAccesException(DataAccessException e) {

    }


}
