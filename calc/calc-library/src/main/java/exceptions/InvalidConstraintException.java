package exceptions;

public class InvalidConstraintException extends Exception {
    private final String message;

    public InvalidConstraintException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
