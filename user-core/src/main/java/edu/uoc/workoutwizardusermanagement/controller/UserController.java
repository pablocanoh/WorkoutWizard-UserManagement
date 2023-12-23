package edu.uoc.workoutwizardusermanagement.controller;

import edu.uoc.workoutwizardusermanagement.configuration.JwtTokenUtil;
import edu.uoc.workoutwizardusermanagement.controller.dtos.AuthenticationRequest;
import edu.uoc.workoutwizardusermanagement.controller.dtos.CreateUserRequest;
import edu.uoc.workoutwizardusermanagement.domain.User;
import edu.uoc.workoutwizardusermanagement.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<User> register(@RequestBody CreateUserRequest request) {
        User newUser = userService.createUser(
                request.getUsername(),
                request.getPassword());
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthenticationRequest request) {
        User user = userService.login(
                request.getUsername(),
                request.getPassword());

        return jwtTokenUtil.generateToken(user);
    }
}
