package edu.uoc.workoutwizardusermanagement.exceptions;

public final class ManyAttemptsException extends RuntimeException{

    public ManyAttemptsException(String tooManyLoginAttempts) {
        super(tooManyLoginAttempts);
    }
}
