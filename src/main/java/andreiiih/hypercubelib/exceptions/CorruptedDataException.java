package andreiiih.hypercubelib.exceptions;

public class CorruptedDataException extends Exception {

    public CorruptedDataException(String message) {
        super(String.format("Corrupted data encountered: %s. This usually indicates a corrupted cache file on disk.", message));
    }
}
