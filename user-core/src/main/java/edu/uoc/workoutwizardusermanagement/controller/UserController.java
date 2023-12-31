package edu.uoc.workoutwizardusermanagement.controller;

import edu.uoc.workoutwizardusermanagement.configuration.JwtTokenUtil;
import edu.uoc.workoutwizardusermanagement.controller.dtos.*;
import edu.uoc.workoutwizardusermanagement.domain.User;
import edu.uoc.workoutwizardusermanagement.exceptions.ManyAttemptsException;
import edu.uoc.workoutwizardusermanagement.exceptions.UserAlreadyRegisteredException;
import edu.uoc.workoutwizardusermanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static edu.uoc.workoutwizardusermanagement.controller.dtos.LoginResponse.invalidCredentials;
import static edu.uoc.workoutwizardusermanagement.controller.dtos.LoginResponse.success;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public ResponseEntity<RegisterState> register(@RequestBody CreateUserRequest request) {
        try {
            userService.createUser(request.getUsername(), request.getPassword());

            return ResponseEntity.ok(RegisterState.SUCCESS);
        } catch (UserAlreadyRegisteredException e) {
            return ResponseEntity.ok(RegisterState.USERNAME_ALREADY_EXISTS);
        }
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody AuthenticationRequest request) {
        try {
            return userService.login(
                    request.getUsername(),
                    request.getPassword())
                    .map(user -> success(jwtTokenUtil.generateToken(user)))
                    .orElse(invalidCredentials());
        } catch (ManyAttemptsException e) {
            LoginResponse.loginBlockedForManyAttempts();
        }

        return null;
    }
}
