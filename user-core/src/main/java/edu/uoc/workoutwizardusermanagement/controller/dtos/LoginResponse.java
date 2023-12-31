package edu.uoc.workoutwizardusermanagement.controller.dtos;

import java.util.Optional;

public record LoginResponse(Optional<String> token, LoginState state) {

    public static LoginResponse success(String token) {
        return new LoginResponse(Optional.of(token), LoginState.SUCCESS);
    }


    public static LoginResponse invalidCredentials() {
        return new LoginResponse(Optional.empty(), LoginState.INVALID_CREDENTIALS);
    }

    public static LoginResponse loginBlockedForManyAttempts() {
        return new LoginResponse(Optional.empty(), LoginState.LOGIN_BLOCKED_FOR_MANY_ATTEMPTS);
    }
}
