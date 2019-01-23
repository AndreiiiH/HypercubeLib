package andreiiih.hypercubelib.exceptions;

public class ArgumentTooLargeException extends Exception {

    public ArgumentTooLargeException(String name, int maxSize, int actualSize) {
        super(String.format("The given argument %s was too large! Max size %d, got %d",
                name, maxSize, actualSize));
    }
}
