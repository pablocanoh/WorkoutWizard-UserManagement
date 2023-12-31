package edu.uoc.workoutwizardusermanagement.exceptions;

public final class UserPasswordIncorrectFormatException extends RuntimeException{

    public UserPasswordIncorrectFormatException(String tooManyLoginAttempts) {
        super(tooManyLoginAttempts);
    }
}
