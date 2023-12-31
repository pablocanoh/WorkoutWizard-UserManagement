package edu.uoc.workoutwizardusermanagement.services;

import edu.uoc.workoutwizardusermanagement.domain.User;
import edu.uoc.workoutwizardusermanagement.exceptions.ManyAttemptsException;
import edu.uoc.workoutwizardusermanagement.exceptions.UserAlreadyRegisteredException;
import edu.uoc.workoutwizardusermanagement.exceptions.UserPasswordIncorrectFormatException;
import edu.uoc.workoutwizardusermanagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@EnableScheduling
@Service
public class UserServiceImpl  implements UserService {

    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])" +         // at least 1 digit
                    "(?=.*[a-z])" +         // at least 1 lower case letter
                    "(?=.*[A-Z])" +         // at least 1 upper case letter
                    "(?=.*[@#$%^&+=])" +    // at least 1 special character
                    "(?=\\S+$).{8,20}$";    // no whitespace, length between 8 and 20 characters

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

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

        if (!isValidPassword(password)) {
            throw new UserPasswordIncorrectFormatException("Password is not valid");
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
    @Transactional
    public Optional<User> login(String username, String password) {
        return userRepository.findByUsername(username)
                .map(user -> {
                   if (user.getLoginAttempts() >= 3) {
                       throw new ManyAttemptsException("Too many login attempts");
                   }
                   return user;
                })
                .flatMap(user -> {
                    if (passwordEncoder.matches(password, user.getPassword())) {
                        userRepository.updateUserLoginAttemptsById(0, user.getId());
                        return Optional.of(user);
                    } else {
                        userRepository.updateUserLoginAttemptsById(user.getLoginAttempts() + 1, user.getId());
                        return Optional.empty();
                    }
                });
    }

    @Transactional
    @Scheduled(fixedRate = 3600000) // 3600000 milliseconds = 1 hour
    public void resetLoginAttempts() {
        userRepository.findAllByLoginAttemptsGreaterThan(0)
                .forEach(user ->
                        userRepository.updateUserLoginAttemptsById( 0, user.getId()));
    }

    public static boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        return pattern.matcher(password).matches();


    }