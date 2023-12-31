package edu.uoc.workoutwizardusermanagement.exceptions;

public final class UserAlreadyRegisteredException extends RuntimeException{

    public UserAlreadyRegisteredException(String tooManyLoginAttempts) {
        super(tooManyLoginAttempts);
    }
}
