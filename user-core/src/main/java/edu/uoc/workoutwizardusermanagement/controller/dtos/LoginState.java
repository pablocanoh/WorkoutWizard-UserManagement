package edu.uoc.workoutwizardusermanagement.controller.dtos;

public enum LoginState {
    SUCCESS,
    INVALID_CREDENTIALS,
    LOGIN_BLOCKED_FOR_MANY_ATTEMPTS,
}
