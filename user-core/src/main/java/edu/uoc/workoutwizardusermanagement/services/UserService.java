package edu.uoc.workoutwizardusermanagement.services;

import edu.uoc.workoutwizardusermanagement.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<User> findAll();

    User createUser(String username, String email, String password);

    User deleteUser(UUID id);

    User getUser(UUID id);
}
