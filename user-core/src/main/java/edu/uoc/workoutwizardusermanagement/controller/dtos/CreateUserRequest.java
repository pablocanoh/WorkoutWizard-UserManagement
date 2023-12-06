package edu.uoc.workoutwizardusermanagement.controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class CreateUserRequest {
    private String username;
    private String email;
    private String password;
}

