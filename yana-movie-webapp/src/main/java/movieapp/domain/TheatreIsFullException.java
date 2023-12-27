package movieapp.domain;

public class TheatreIsFullException extends RuntimeException {

    public TheatreIsFullException() {
        super("Theatre is full. Cannot buy ticket!");
    }
}
