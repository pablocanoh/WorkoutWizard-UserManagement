package edu.uoc.workoutwizardusermanagement.services;

import edu.uoc.workoutwizardusermanagement.domain.User;
import edu.uoc.workoutwizardusermanagement.exceptions.ManyAttemptsException;
import edu.uoc.workoutwizardusermanagement.exceptions.UserAlreadyRegisteredException;
import edu.uoc.workoutwizardusermanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@EnableScheduling
@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        return userRepository.findByUsername(username)
                .map(user -> {
                   if (user.getLoginAttemps() >= 3) {
                       throw new ManyAttemptsException("Too many login attempts");
                   }
                   return user;
                })
                .flatMap(user -> {
                    if (passwordEncoder.matches(password, user.getPassword())) {
                        user.setLoginAttemps(0);
                        userRepository.updateUserById(user.getId(), user);
                        return Optional.of(user);
                    } else {
                        user.setLoginAttemps(user.getLoginAttemps() + 1);
                        userRepository.updateUserById(user.getId(), user);
                        return Optional.empty();
                    }
                });
    }

    @Scheduled(fixedRate = 3600000) // 3600000 milliseconds = 1 hour
    public void resetLoginAttempts() {
        userRepository.findAllByLoginAttempsGreaterThan(0)
                .forEach(user -> {
                    user.setLoginAttemps(0);
                    userRepository.updateUserById(user.getId(), user);
                });
    }

}