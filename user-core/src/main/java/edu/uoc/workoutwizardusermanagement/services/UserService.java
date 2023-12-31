package edu.uoc.workoutwizardusermanagement.services;

import edu.uoc.workoutwizardusermanagement.domain.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    List<User> findAll();

    User createUser(String username, String password);

    User deleteUser(UUID id);

    User getUser(UUID id);

    User login(String username, String password);
}
