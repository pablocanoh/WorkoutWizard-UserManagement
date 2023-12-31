package edu.uoc.workoutwizardusermanagement.services;

import edu.uoc.workoutwizardusermanagement.domain.User;
import edu.uoc.workoutwizardusermanagement.exceptions.ManyAttemptsException;
import edu.uoc.workoutwizardusermanagement.exceptions.UserAlreadyRegisteredException;
import edu.uoc.workoutwizardusermanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final ConcurrentHashMap<String, Integer> userLoginAttempts = new ConcurrentHashMap<>();

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserAlreadyRegisteredException("Username already exists");
        }


        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .build();


        return userRepository.save(user);
    }

    @Override
    public User deleteUser(UUID id) {
        User user = userRepository.findById(id).orElseThrow();
        userRepository.delete(user);
        return user;
    }

    @Override
    public User getUser(UUID id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public Optional<User> login(String username, String password) {
        if (userLoginAttempts.getOrDefault(username, 0) >= 5) {
            throw new ManyAttemptsException("Too many login attempts");
        }

        final var user = userRepository
                .findByUsername(username)
                .orElseThrow();

        if (passwordEncoder.matches(password, user.getPassword())) {
            userLoginAttempts.put(username, 0);
            return Optional.of(user);
        } else {
            userLoginAttempts.put(username, userLoginAttempts.getOrDefault(username, 0) + 1);
            return Optional.empty();
        }
    }
}
