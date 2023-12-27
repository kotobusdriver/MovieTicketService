package movieapp.domain;

public class UnauthorizedDirectorException extends RuntimeException {
    public UnauthorizedDirectorException() {
        super("Director is not authorized to perform the requested operation.");
    }
}
